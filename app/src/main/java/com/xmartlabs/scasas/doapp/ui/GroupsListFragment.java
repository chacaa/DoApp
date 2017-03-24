package com.xmartlabs.scasas.doapp.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.annimon.stream.Stream;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import com.hannesdorfmann.fragmentargs.bundler.ParcelerArgsBundler;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;
import com.rey.material.widget.Spinner;
import com.xmartlabs.scasas.doapp.R;
import com.xmartlabs.scasas.doapp.controller.GroupController;
import com.xmartlabs.scasas.doapp.controller.TaskController;
import com.xmartlabs.scasas.doapp.model.Group;
import com.xmartlabs.scasas.doapp.model.Task;
import com.xmartlabs.scasas.doapp.model.User;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.SingleSubscriber;
import timber.log.Timber;

/**
 * Created by scasas on 3/20/17.
 */
@FragmentWithArgs
public class GroupsListFragment extends BaseFragment {
  @BindView(R.id.groups_spinner)
  Spinner groupsSpinnerView;
  @BindView(R.id.fab_button)
  FloatingActionButton fabButtonView;
  @BindView(R.id.new_task)
  LinearLayout newTaskView;
  @BindView(R.id.title_edit_text)
  EditText titleView;
  @BindView(R.id.description_edit_text)
  EditText descriptionView;
  @BindView(R.id.shop_items)
  TextView shopItemsView;
  @BindView(R.id.work_items)
  TextView workItemsView;
  @BindView(R.id.health_items)
  TextView healthItemsView;
  @BindView(R.id.travel_items)
  TextView travelItemsView;
  @BindView(R.id.bills_items)
  TextView billsItemsView;
  @BindView(R.id.auto_items)
  TextView autoItemsView;
  @BindView(R.id.circle_chart)
  DecoView cricleChartView;
  @BindView(R.id.portcentage)
  TextView porcentageView;
  @BindView(R.id.main_toolbar)
  Toolbar toolbar;

  @Inject
  GroupController groupController;
  @Inject
  TaskController taskController;

  @Arg(bundler = ParcelerArgsBundler.class)
  User user;

  private List<Group> groups = new ArrayList<Group>();

  @Override
  protected int getLayoutResId() {
    return R.layout.list_groups_fragment;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
//    toolbar.setTitle(R.string.do_it);
    getGroups();
    newTaskView.setVisibility(View.GONE);
    String[] items = getResources().getStringArray(R.array.group_array);
    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, items);
    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
    groupsSpinnerView.setAdapter(adapter);
    adjustSpinnerView(groupsSpinnerView);
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

  @OnClick(R.id.shop_linear)
  void onClikedShopGroup() {
    Group group = getGroup(R.string.shop);
    goToListOfTasksFragment(group);
  }

  @OnClick(R.id.work_linear)
  void onClikedWorkGroup() {
    Group group = getGroup(R.string.work);
    goToListOfTasksFragment(group);
  }

  @OnClick(R.id.health_linear)
  void onClikedHealthGroup() {
    Group group = getGroup(R.string.health);
    goToListOfTasksFragment(group);
  }

  @OnClick(R.id.travel_linear)
  void onClikedTravelGroup() {
    Group group = getGroup(R.string.travel);
    goToListOfTasksFragment(group);
  }

  @OnClick(R.id.bills_linear)
  void onClikedBillsGroup() {
    Group group = getGroup(R.string.bills);
    goToListOfTasksFragment(group);
  }

  @OnClick(R.id.auto_linear)
  void onClikedAutoGroup() {
    Group group = getGroup(R.string.auto);
    goToListOfTasksFragment(group);
  }

  @OnClick(R.id.add_task)
  void onClickedAddTextView() {
    Group group = getGroup(groupsSpinnerView.getSelectedItem().toString());
    Task task = Task.builder()
        .title(titleView.getText().toString().trim())
        .description(descriptionView.getText().toString().trim())
        .date(LocalDate.now())
        .isFinished(false)
        .group(group)
        .user(user)
        .build();
    insertTask(task, group);
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

  private void adjustSpinnerView(Spinner spinner) {
    TextView textView = (TextView) spinner.getSelectedView();
    //noinspection deprecation
    textView.setTextColor(getResources().getColor(R.color.white));
    textView.setTextSize(2, 14);
  }

  private void goToListOfTasksFragment(Group group) {
    Intent intent = Henson.with(getContext())
        .gotoTasksListActivity()
        .group(group)
        .user(user)
        .build();
    getContext().startActivity(intent);
  }

  private void getGroups() {
    groupController.getGroups(user).subscribe(new SingleSubscriber<List<Group>>() {
      @Override
      public void onSuccess(List<Group> groupList) {
        groups = groupList;
        updateAll(groupList);
      }

      @Override
      public void onError(Throwable error) {
        Timber.e(error);
      }
    });
  }

  private Group getGroup(@NonNull String groupName) {
    return Stream.of(groups)
        .filter(group -> Objects.equals(groupName, group.getName()))
        .findFirst()
        .get();
  }

  private Group getGroup(@StringRes int groupName) {
    String name = getResources().getString(groupName);
    return getGroup(name);
  }

  private void insertTask(Task task, Group group) {
    taskController.insertTask(task)
        .subscribe(new SingleSubscriber<Task>() {
          @Override
          public void onSuccess(Task task) {
            Snackbar.make(getView(), R.string.task_added, Snackbar.LENGTH_SHORT).show();
            updateItemCount(group.getName());
            closeNewTaskView();
          }

          @Override
          public void onError(Throwable error) {
            Timber.e(error);
          }
        });
  }

  private void updateItemCount(String groupName) {
    int group = getStringRes(groupName);
    if (group != 0) {
      updateItemCount(group);
    }
  }

  private void updateItemCount(@StringRes int groupName) {
    switch (groupName) {
      case R.string.shop:
        getTaskCount(R.string.shop);
        break;
      case R.string.work:
        getTaskCount(R.string.work);
        break;
      case R.string.health:
        getTaskCount(R.string.health);
        break;
      case R.string.travel:
        getTaskCount(R.string.travel);
        break;
      case R.string.bills:
        getTaskCount(R.string.bills);
        break;
      case R.string.auto:
        getTaskCount(R.string.auto);
        break;
      default:
        updateAll(groups);
    }
  }

  private void updateAll(List<Group> groups) {
    Stream.of(groups).forEach(this::getTaskCount);
  }

  private void getTaskCount(@StringRes int groupName) {
    Group group = getGroup(groupName);
    taskController.getTasksCount(user, group).subscribe(new SingleSubscriber<Long>() {
      @Override
      public void onSuccess(Long taskCount) {
        int groupName = getStringRes(group.getName());
        updateItemField(groupName, taskCount);
      }

      @Override
      public void onError(Throwable error) {
        Timber.e(error);
      }
    });
  }

  private void getTaskCount(Group group) {
    taskController.getTasksCount(user, group).subscribe(new SingleSubscriber<Long>() {
      @Override
      public void onSuccess(Long taskCount) {
        int groupName = getStringRes(group.getName());
        updateItemField(groupName, taskCount);
      }

      @Override
      public void onError(Throwable error) {
        Timber.e(error);
      }
    });
  }

  private String getTextFromStringRes(@StringRes int text) {
    return getResources().getString(text);
  }

  private int getStringRes(String text) {
    if (text.equals(getTextFromStringRes(R.string.shop))) {
      return R.string.shop;
    }
    if (text.equals(getTextFromStringRes(R.string.work))) {
      return R.string.work;
    }
    if (text.equals(getTextFromStringRes(R.string.health))) {
      return R.string.health;
    }
    if (text.equals(getTextFromStringRes(R.string.travel))) {
      return R.string.travel;
    }
    if (text.equals(getTextFromStringRes(R.string.bills))) {
      return R.string.bills;
    }
    if (text.equals(getTextFromStringRes(R.string.auto))) {
      return R.string.auto;
    }
    return 0;
  }

  private void updateItemField(@StringRes int groupName, long itemCount) {
    switch (groupName) {
      case R.string.shop:
        shopItemsView.setText(String.format(getString(R.string.item), itemCount));
        break;
      case R.string.work:
        workItemsView.setText(String.format(getString(R.string.item), itemCount));
        break;
      case R.string.health:
        healthItemsView.setText(String.format(getString(R.string.item), itemCount));
        break;
      case R.string.travel:
        travelItemsView.setText(String.format(getString(R.string.item), itemCount));
        break;
      case R.string.bills:
        billsItemsView.setText(String.format(getString(R.string.item), itemCount));
        break;
      case R.string.auto:
        autoItemsView.setText(String.format(getString(R.string.item), itemCount));
        break;
      default:
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    updateAll(groups);
    setupChart();
    LocalDate date = LocalDate.now();
    date.plusDays(-23);
    getFinishedPorcentage(date);
  }

  private void getFinishedPorcentage(LocalDate date) {
    taskController.getIntFinishPercentage(user, date).subscribe(new SingleSubscriber<Long>() {
      @Override
      public void onSuccess(Long percentage) {
        setupCurrentPercentage(percentage.intValue());
      }

      @Override
      public void onError(Throwable error) {

      }
    });
  }

  private void setupCurrentPercentage(int percentage) {
    //noinspection deprecation
    SeriesItem seriesItem1 = new SeriesItem.Builder(getResources().getColor(R.color.seafoam_blue_two))
        .setRange(0, 100, 0)
        .setInterpolator(new DecelerateInterpolator())
        .setSpinDuration(2000)
        .setLineWidth(15f)
        .setSpinClockwise(true)
        .build();

    seriesItem1.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
      @Override
      public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
        porcentageView.setText(String.valueOf((int) currentPosition));
      }

      @Override
      public void onSeriesItemDisplayProgress(float percentComplete) {

      }
    });

    int index = cricleChartView.addSeries(seriesItem1);

    cricleChartView.executeReset();

    cricleChartView.addEvent(new DecoEvent.Builder(percentage)
        .setIndex(index)
        .setDelay(500)
        .build());
  }

  private void setupChart() {
    //noinspection deprecation
    cricleChartView.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
        .setRange(0, 100, 100)
        .setLineWidth(15f)
        .build());

  }
}
