package com.xmartlabs.doapp.ui;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xmartlabs.doapp.model.Task;
import com.xmartlabs.template.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.RequiredArgsConstructor;
import rx.functions.Action1;

/**
 * Created by scasas on 3/15/2017.
 */
@RequiredArgsConstructor
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {
  private static final int VALUE = 255;

  @NonNull
  private final Action1<Task> onTaskTapped;
  private List<Task> tasks = new ArrayList<>();

  @MainThread
  public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
    notifyDataSetChanged();
  }

  @MainThread
  public void addTask(@NonNull Task task) {
    tasks.add(task);
    notifyDataSetChanged();
  }

  @Override
  public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View view = layoutInflater.inflate(R.layout.list_item_task, parent, false);
    return new TaskHolder(view, onTaskTapped);
  }

  @Override
  public void onBindViewHolder(TaskHolder holder, int position) {
    Task task = tasks.get(position);
    holder.bind(task);
  }

  @Override
  public int getItemCount() {
    return tasks.size();
  }

  static class TaskHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.image_view)
    ImageView doneImageView;
    @BindView(R.id.color_view)
    View colorView;
    @BindView(R.id.description_task)
    TextView descriptionTextView;
    @BindView(R.id.title_task)
    TextView titleTextView;
    @BindView(R.id.task_linear_layout)
    LinearLayout taskLinearView;

    @NonNull
    private final Action1<Task> onTaskTapped;

    public TaskHolder(View itemView, @NonNull Action1<Task> onTaskTapped) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      this.onTaskTapped = onTaskTapped;
    }

    public void bind(@NonNull Task task) {
      titleTextView.setText(task.getTitle());
      if (task.getDescription().isEmpty()) {
        descriptionTextView.setVisibility(View.GONE);
      } else {
        descriptionTextView.setVisibility(View.VISIBLE);
        descriptionTextView.setText(task.getDescription());
      }
      setDoneState(task.isFinished());
      taskLinearView.setOnClickListener(v -> {
        setDoneState(!task.isFinished());
        onTaskTapped.call(task);
      });
    }

    private void setDoneState(boolean done) {
      if (done) {
        //noinspection deprecation
        taskLinearView.setBackgroundColor(itemView.getResources().getColor(R.color.seafoam_blue_two_light));
        //noinspection deprecation
        colorView.setBackgroundColor(itemView.getResources().getColor(R.color.seafoam_blue_two));
        //noinspection deprecation
        doneImageView.setImageDrawable(itemView.getResources().getDrawable(R.drawable.done));
        //noinspection deprecation
        doneImageView.setAlpha(VALUE);
      } else {
        //noinspection deprecation
        taskLinearView.setBackgroundColor(itemView.getResources().getColor(R.color.white));
        //noinspection deprecation
        colorView.setBackgroundColor(itemView.getResources().getColor(R.color.white));
        //noinspection deprecation
        doneImageView.setImageDrawable(itemView.getResources().getDrawable(R.drawable.undo));
        //noinspection deprecation
        doneImageView.setAlpha((int) (0.5 * VALUE));
      }
    }
  }
}
