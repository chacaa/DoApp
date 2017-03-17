package com.xmartlabs.doapp.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.xmartlabs.doapp.controller.TaskController;
import com.xmartlabs.doapp.model.Task;
import com.xmartlabs.template.R;

import org.threeten.bp.LocalDate;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.SingleSubscriber;
import timber.log.Timber;

/**
 * Created by santiago on 3/15/2017.
 */
@FragmentWithArgs
public class TasksListFragment extends BaseFragment {
  @BindView(R.id.task_recycler_view)
  RecyclerView recyclerView;
  @BindView(R.id.new_task)
  LinearLayout newTaskView;
  @BindView(R.id.title_edit_text)
  EditText titleView;
  @BindView(R.id.description_edit_text)
  EditText description;
  @BindView(R.id.fab_button)
  FloatingActionButton fabButtonView;
  @BindView(R.id.header_title)
  TextView headerTitleView;

  @Inject
  TaskController taskController;

  private TaskAdapter adapter;

  @LayoutRes
  @Override
  protected int getLayoutResId() {
    return R.layout.list_tasks_fragment;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setupRecyclerView();
    getTasks();
    newTaskView.setVisibility(View.GONE);
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
  }

  @OnClick(R.id.add_task)
  void onClickedAddTextView() {
    Task task = Task.builder()
        .title(titleView.getText().toString().trim())
        .description(description.getText().toString().trim())
        .date(LocalDate.now())
        .isFinished(false)
        .build();
    insertTask(task);
    closeNewTaskView();
  }

  private void setFieldsEmpty() {
    titleView.setText(null);
    description.setText(null);
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
            Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
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
            Snackbar.make(getView(), R.string.task_added, Snackbar.LENGTH_SHORT).show();
          }

          @Override
          public void onError(Throwable error) {
            Timber.e(error);
          }
        });
  }

  private void getTasks() {
    taskController.getTasks()
        .subscribe(new SingleSubscriber<List<Task>>() {
          @Override
          public void onSuccess(List<Task> tasks) {
            if (!tasks.isEmpty()) {
              adapter.setTasks(tasks);
            }
          }

          @Override
          public void onError(Throwable error) {
            Timber.e(error);
          }
        });
  }
}
