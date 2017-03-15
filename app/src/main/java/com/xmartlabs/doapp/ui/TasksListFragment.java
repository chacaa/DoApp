package com.xmartlabs.doapp.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.xmartlabs.doapp.model.Task;
import com.xmartlabs.template.R;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by santiago on 3/15/17.
 */
@FragmentWithArgs
public class TasksListFragment extends BaseFragment {
  @BindView(R.id.task_recycler_view)
  RecyclerView recyclerView;

  @BindView(R.id.new_task)
  LinearLayout new_task;

  @BindView(R.id.close)
  ImageView close;

  @BindView(R.id.fab_button)
  FloatingActionButton fab_button;

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
    generateTasks();
    adapter.setTasks(tasks);
    new_task.setVisibility(View.GONE);
    return view;
  }

  private void setpuRecyclerView() {
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerView.setHasFixedSize(true);
    adapter = new TaskAdapter();
    recyclerView.setAdapter(adapter);
  }

  @OnClick(R.id.fab_button)
  void onClickedFabButton() {
    fab_button.setVisibility(View.GONE);
    new_task.setVisibility(View.VISIBLE);
  }

  @OnClick(R.id.close)
  void onClickedCloseImageView() {
    fab_button.setVisibility(View.VISIBLE);
    new_task.setVisibility(View.GONE);
  }

  private void generateTasks() {
    List<Task> taskList = new ArrayList<>();
    for (int i=0; i < 100; i++){
      boolean isFinished = i%7 == 0 ? true : false;
      String description = i%21 == 0 ? "" : "Descrption task #"+i;
      Task task = Task.builder()
          .title("Task #"+i)
          .description(description)
          .date(LocalDate.now())
          .isFinished(isFinished)
          .build();
      taskList.add(task);
    }
    tasks = taskList;
  }

}
