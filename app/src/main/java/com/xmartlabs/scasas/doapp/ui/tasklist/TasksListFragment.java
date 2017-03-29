package com.xmartlabs.scasas.doapp.ui.tasklist;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annimon.stream.Objects;
import com.annimon.stream.Optional;
import com.annimon.stream.function.Consumer;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.hannesdorfmann.fragmentargs.bundler.ParcelerArgsBundler;
import com.xmartlabs.scasas.doapp.GroupEnum;
import com.xmartlabs.scasas.doapp.controller.TaskController;
import com.xmartlabs.scasas.doapp.model.Group;
import com.xmartlabs.scasas.doapp.model.Task;
import com.xmartlabs.scasas.doapp.R;
import com.xmartlabs.scasas.doapp.model.User;
import com.xmartlabs.scasas.doapp.ui.BaseFragment;

import org.threeten.bp.LocalDate;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.SingleSubscriber;
import timber.log.Timber;

/**
 * Created by santiago on 3/15/17.
 */
@FragmentWithArgs
public class TasksListFragment extends BaseFragment {
  @BindView(R.id.main_collapsing)
  CollapsingToolbarLayout collapsingToolbarView;
  @BindView(R.id.description_edit_text)
  EditText descriptionView;
  @BindView(R.id.fab_button)
  FloatingActionButton fabButtonView;
  @BindView(R.id.header_image)
  ImageView headerImageView;
  @BindView(R.id.new_task)
  LinearLayout newTaskView;
  @BindView(R.id.no_task_text)
  TextView noTaskTextView;
  @BindView(R.id.task_recycler_view)
  RecyclerView recyclerView;
  @BindView(R.id.main_toolbar)
  Toolbar toolbarView;
  @BindView(R.id.title_edit_text)
  EditText titleView;

  @Inject
  TaskController taskController;

  @Arg(bundler = ParcelerArgsBundler.class)
  Group group;
  @Arg(bundler = ParcelerArgsBundler.class)
  User user;

  private TaskAdapter adapter;

  @LayoutRes
  @Override
  protected int getLayoutResId() {
    return R.layout.list_tasks_fragment;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setupToolbar();
    setupHeader();
    setupRecyclerView();
    getTasks();
    newTaskView.setVisibility(View.GONE);
  }

  private void setupToolbar() {
    AppCompatActivity activity = (AppCompatActivity) getActivity();
    activity.setSupportActionBar(toolbarView);
    Optional.ofNullable(activity.getSupportActionBar())
        .ifPresent(actionBar -> actionBar.setDisplayHomeAsUpEnabled(true));
    //noinspection deprecation
    collapsingToolbarView.setExpandedTitleColor(getResources().getColor(android.R.color.white));
  }

  private void setupRecyclerView() {
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerView.setHasFixedSize(true);
    adapter = new TaskAdapter(this::onTappedTask);
    recyclerView.setAdapter(adapter);
  }

  @OnClick(R.id.fab_button)
  void onClickedFabButton() {
    fabButtonView.setVisibility(View.GONE);
    newTaskView.setVisibility(View.VISIBLE);
  }

  @OnClick(R.id.close)
  void onClickedCloseImageView() {
    closeNewTaskView();
    hideKeyboard();
  }

  @OnClick(R.id.add_task)
  void onClickedAddTextView() {
    Task task = Task.builder()
        .title(titleView.getText().toString().trim())
        .description(descriptionView.getText().toString().trim())
        .date(LocalDate.now())
        .isFinished(false)
        .user(user)
        .group(group)
        .build();
    insertTask(task);
    hideKeyboard();
    closeNewTaskView();
  }

  private void setFieldsEmpty() {
    titleView.setText(null);
    descriptionView.setText(null);
  }

  private void closeNewTaskView() {
    fabButtonView.setVisibility(View.VISIBLE);
    newTaskView.setVisibility(View.GONE);
    setFieldsEmpty();
  }

  private void onTappedTask(Task task) {
    updateTaskState(task);
  }

  private void updateTaskState(Task task) {
    taskController.changeTaskState(task)
        .subscribe(new SingleSubscriber<Task>() {
          @Override
          public void onSuccess(Task task) {
            int message = task.isFinished() ? R.string.good_job : R.string.cmon_you_can_do_this;
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
          }

          @Override
          public void onError(Throwable error) {
            Timber.e(error);
          }
        });
  }

  private void insertTask(Task task) {
    taskController.insertTask(task)
        .subscribe(new SingleSubscriber<Task>() {
          @Override
          public void onSuccess(Task task) {
            adapter.addTask(task);
            if (isAdded()) {
              noTaskTextView.setVisibility(View.GONE);
              Toast.makeText(getContext(), R.string.task_added, Toast.LENGTH_SHORT).show();
            }
          }

          @Override
          public void onError(Throwable error) {
            Timber.e(error);
            Toast.makeText(getContext(), R.string.failed_add_task, Toast.LENGTH_SHORT).show();
          }
        });
  }

  private void getTasks() {
    taskController.getTasks(user, group)
        .subscribe(new SingleSubscriber<List<Task>>() {
          @Override
          public void onSuccess(List<Task> tasks) {
            if (!tasks.isEmpty()) {
              adapter.setTasks(tasks);
              if (isAdded()) {
                noTaskTextView.setVisibility(tasks.size() == 0 ? View.VISIBLE : View.GONE);
              }
            }
          }

          @Override
          public void onError(Throwable error) {
            Timber.e(error);
          }
        });
  }

  private void setupHeader() {
    if (Objects.equals(group.getName(), GroupEnum.Shop.toString())) {
      headerImageView.setImageDrawable(getDrawable(R.drawable.food_image));
      collapsingToolbarView.setTitle(GroupEnum.Shop.toString());
      return;
    }
    if (Objects.equals(group.getName(), GroupEnum.Work.toString())) {
      headerImageView.setImageDrawable(getDrawable(R.drawable.work));
      collapsingToolbarView.setTitle(GroupEnum.Work.toString());
      return;
    }
    if (Objects.equals(group.getName(), GroupEnum.Health.toString())) {
      headerImageView.setImageDrawable(getDrawable(R.drawable.healthy));
      collapsingToolbarView.setTitle(GroupEnum.Health.toString());
      return;
    }
    if (Objects.equals(group.getName(), GroupEnum.Travel.toString())) {
      headerImageView.setImageDrawable(getDrawable(R.drawable.travels));
      collapsingToolbarView.setTitle(GroupEnum.Travel.toString());
      return;
    }
    if (Objects.equals(group.getName(), GroupEnum.Bills.toString())) {
      headerImageView.setImageDrawable(getDrawable(R.drawable.bills));
      collapsingToolbarView.setTitle(GroupEnum.Bills.toString());
      return;
    }
    headerImageView.setImageDrawable(getDrawable(R.drawable.cars));
    collapsingToolbarView.setTitle(GroupEnum.Auto.toString());
  }

  private Drawable getDrawable(@DrawableRes int image) {
    //noinspection deprecation
    return getResources().getDrawable(image);
  }
}
