package com.xmartlabs.doapp.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
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

import java.util.ArrayList;
import java.util.Collections;
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
  @BindView(R.id.task_recycler_view)
  RecyclerView recyclerView;
  @BindView(R.id.new_task)
  LinearLayout new_task;
  @BindView(R.id.title_edit_text)
  EditText title;
  @BindView(R.id.description_edit_text)
  EditText description;
  @BindView(R.id.fab_button)
  FloatingActionButton fab_button;
  @BindView(R.id.header_title)
  TextView headerTitle;

  @Inject
  TaskController taskController;

  private TaskAdapter adapter;
  private List<Task> tasks = Collections.emptyList();

  @LayoutRes
  @Override
  protected int getLayoutResId() {
    return R.layout.list_tasks_fragment;
  }

  @NonNull
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = super.onCreateView(inflater, container, savedInstanceState);
    setpuRecyclerView();
    //generateTasks();
    getTasks();
    new_task.setVisibility(View.GONE);
    return view;
  }

  private void setpuRecyclerView() {
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerView.setHasFixedSize(true);
    adapter = new TaskAdapter(this::onTappedTask);
    recyclerView.setAdapter(adapter);
  }

  @OnClick(R.id.fab_button)
  void onClickedFabButton() {
    fab_button.setVisibility(View.GONE);
    new_task.setVisibility(View.VISIBLE);
  }

  @OnClick(R.id.close)
  void onClickedCloseImageView() {
    closeNewTaskView();
  }

  @OnClick(R.id.add_task)
  void onClickedAddTextView() {
    Task task = Task.builder()
        .title(title.getText().toString().trim())
        .description(description.getText().toString().trim())
        .date(LocalDate.now())
        .isFinished(false)
        .build();
    insertTask(task);
    closeNewTaskView();
  }

  private void generateTasks() {
    List<Task> taskList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      boolean isFinished = i % 7 == 0;
      String description = i % 21 == 0 ? "" : "Descrption task #" + i;
      Task task = Task.builder()
          .title("Task #" + i)
          .description(description)
          .date(LocalDate.now())
          .isFinished(isFinished)
          .build();
      taskList.add(task);
    }
    tasks = taskList;
  }

  private void setFieldsEmpty() {
    title.setText(null);
    description.setText(null);
  }

  private void closeNewTaskView() {
    fab_button.setVisibility(View.VISIBLE);
    new_task.setVisibility(View.GONE);
    setFieldsEmpty();
  }

  private void onTappedTask(Task task) {
    updateTaskState(task);
  }

  private void updateTaskState(Task task) {
    taskController.changeTaskState(task).subscribe(new SingleSubscriber<Task>() {
      @Override
      public void onSuccess(Task task) {
        String message = task.isFinished() ? "Task done! Good job!" : "C'mon you can Do it!";
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
        updateRecyclerViewContent();
      }

      @Override
      public void onError(Throwable error) {
        //TODO
      }
    });
  }

  private void insertTask(Task task) {
    taskController.insertTask(task).subscribe(new SingleSubscriber<Task>() {
      @Override
      public void onSuccess(Task value) {
        tasks.add(task);
        updateRecyclerViewContent();
        Snackbar.make(getView(), "Task added", Snackbar.LENGTH_SHORT).show();
      }

      @Override
      public void onError(Throwable error) {
        //TODO
      }
    });
  }

  private void getTasks() {
    taskController.getTasks().subscribe(new SingleSubscriber<List<Task>>() {
      @Override
      public void onSuccess(List<Task> tasksList) {
        if (!tasksList.isEmpty()) {
          tasks = tasksList;
          updateRecyclerViewContent();
        }
      }

      @Override
      public void onError(Throwable error) {
        Timber.e(error);
        //TODO
      }
    });
  }

  private void updateRecyclerViewContent() {
    adapter.setTasks(tasks);
  }
}
