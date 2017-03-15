package com.xmartlabs.doapp.ui;

import android.support.annotation.MainThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xmartlabs.doapp.model.Task;
import com.xmartlabs.template.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by scasas on 3/15/17.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {
  private List<Task> tasks = new ArrayList<>();

  @MainThread
  public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
    notifyDataSetChanged();
  }

  @Override
  public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View view = layoutInflater.inflate(R.layout.list_item_task, parent, false);
    return new TaskHolder(view);
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

    public TaskHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void bind(Task task) {
      titleTextView.setText(task.getTitle());
      if (task.getDescription().isEmpty()) {
        descriptionTextView.setVisibility(View.GONE);
      } else {
        descriptionTextView.setVisibility(View.VISIBLE);
        descriptionTextView.setText(task.getDescription());
      }
      if (task.isFinished()) {
        //noinspection deprecation
        colorView.setBackgroundColor(itemView.getResources().getColor(R.color.seafoam_blue_two));
        //noinspection deprecation
        doneImageView.setImageDrawable(itemView.getResources().getDrawable(R.drawable.done));
        //noinspection deprecation
        doneImageView.setAlpha(255);
      } else {
        //noinspection deprecation
        colorView.setBackgroundColor(itemView.getResources().getColor(R.color.white));
        //noinspection deprecation
        doneImageView.setImageDrawable(itemView.getResources().getDrawable(R.drawable.undo));
        //noinspection deprecation
        doneImageView.setAlpha((int) (0.5 * 255));
//        doneImageView.setAlpha((int) (0.3 * 255));
      }
    }
  }
}
