package com.xmartlabs.scasas.doapp.ui.grouplist;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annimon.stream.Objects;
import com.annimon.stream.Stream;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import com.hannesdorfmann.fragmentargs.bundler.ParcelerArgsBundler;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;
import com.rey.material.widget.Spinner;
import com.xmartlabs.scasas.doapp.GroupEnum;
import com.xmartlabs.scasas.doapp.R;
import com.xmartlabs.scasas.doapp.controller.GroupController;
import com.xmartlabs.scasas.doapp.controller.TaskController;
import com.xmartlabs.scasas.doapp.model.Group;
import com.xmartlabs.scasas.doapp.model.Task;
import com.xmartlabs.scasas.doapp.model.User;
import com.xmartlabs.scasas.doapp.ui.BaseFragment;
import com.xmartlabs.scasas.doapp.ui.Henson;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

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
  @BindView(R.id.auto_items)
  TextView autoItemsView;
  @BindView(R.id.bills_items)
  TextView billsItemsView;
  @BindView(R.id.circle_chart)
  DecoView cricleChartView;
  @BindView(R.id.description_edit_text)
  EditText descriptionView;
  @BindView(R.id.fab_button)
  FloatingActionButton fabButtonView;
  @BindView(R.id.groups_spinner)
  Spinner groupsSpinnerView;
  @BindView(R.id.health_items)
  TextView healthItemsView;
  @BindView(R.id.new_task)
  LinearLayout newTaskView;
  @BindView(R.id.month)
  TextView monthTextView;
  @BindView(R.id.portcentage)
  TextView percentageView;
  @BindView(R.id.shop_items)
  TextView shopItemsView;
  @BindView(R.id.title_edit_text)
  EditText titleView;
  @BindView(R.id.travel_items)
  TextView travelItemsView;
  @BindView(R.id.work_items)
  TextView workItemsView;
  @BindView(R.id.year)
  TextView yearTextView;

  @Inject
  GroupController groupController;
  @Inject
  TaskController taskController;

  @Arg(bundler = ParcelerArgsBundler.class)
  User user;

  public static final String MONTH_PATTERN = "MMMM";
  public static final String YEAR_PATTERN = "YYYY";

  private LocalDate date;
  private List<Group> groups = new ArrayList<>();

  @LayoutRes
  @Override
  protected int getLayoutResId() {
    return R.layout.list_groups_fragment;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setupCurrentDate();
    getGroups();
    newTaskView.setVisibility(View.GONE);
    setupGroupSpinnerView();
    setupDateFields();
  }

  private void setupGroupSpinnerView() {
    String[] items = getResources().getStringArray(R.array.group_array);
    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, items);
    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
    groupsSpinnerView.setAdapter(adapter);
    adjustSpinnerView(groupsSpinnerView);
  }

  private void setupCurrentDate() {
    LocalDate actualDate = LocalDate.now();
    date = LocalDate.of(actualDate.getYear(), actualDate.getMonth(), 1);
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

  @OnClick(R.id.shop_linear)
  void onClickedShopGroup() {
    Group group = getGroup(GroupEnum.Shop.toString());
    goToListOfTasksFragment(group);
  }

  @OnClick(R.id.work_linear)
  void onClickedWorkGroup() {
    Group group = getGroup(GroupEnum.Work.toString());
    goToListOfTasksFragment(group);
  }

  @OnClick(R.id.health_linear)
  void onClickedHealthGroup() {
    Group group = getGroup(GroupEnum.Health.toString());
    goToListOfTasksFragment(group);
  }

  @OnClick(R.id.travel_linear)
  void onClickedTravelGroup() {
    Group group = getGroup(GroupEnum.Travel.toString());
    goToListOfTasksFragment(group);
  }

  @OnClick(R.id.bills_linear)
  void onClickedBillsGroup() {
    Group group = getGroup(GroupEnum.Bills.toString());
    goToListOfTasksFragment(group);
  }

  @OnClick(R.id.auto_linear)
  void onClickedAutoGroup() {
    Group group = getGroup(GroupEnum.Auto.toString());
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
    hideKeyboard();
    setupCurrentDate();
    getFinishedPercentage();
    setupDateFields();
  }

  @OnClick(R.id.next_month)
  void onClickedNextMonthButton() {
    date = date.plusMonths(1);
    getFinishedPercentage();
    setupDateFields();
  }

  @OnClick(R.id.previous_month)
  void onClickedPrevMonthButton() {
    date = date.minusMonths(1);
    getFinishedPercentage();
    setupDateFields();
  }

  @OnClick(R.id.logout)
  void onClickedLogoutButton() {
    Intent intent = Henson.with(getContext())
        .gotoSignInActivity()
        .build()
        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    getContext().startActivity(intent);
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

  private void insertTask(Task task, Group group) {
    taskController.insertTask(task)
        .subscribe(new SingleSubscriber<Task>() {
          @Override
          public void onSuccess(Task task) {
            Toast.makeText(getContext(), R.string.task_added, Toast.LENGTH_SHORT).show();
            updateTaskCount(group);
            closeNewTaskView();
          }

          @Override
          public void onError(Throwable error) {
            Timber.e(error);
          }
        });
  }

  private void updateAll(List<Group> groups) {
    Stream.of(groups).forEach(this::updateTaskCount);
  }

  private void updateTaskCount(@NonNull Group group) {
    taskController.getTasksCount(user, group).subscribe(new SingleSubscriber<Long>() {
      @Override
      public void onSuccess(Long taskCount) {
        updateItemField(group.getName(), taskCount);
      }

      @Override
      public void onError(Throwable error) {
        Timber.e(error);
      }
    });
  }

  private void updateItemField(String groupName, Long itemCount) {
    if (Objects.equals(groupName, GroupEnum.Shop.toString())) {
      shopItemsView.setText(String.format(getString(R.string.item), itemCount));
      return;
    }
    if (Objects.equals(groupName, GroupEnum.Work.toString())) {
      shopItemsView.setText(String.format(getString(R.string.item), itemCount));
      return;
    }
    if (Objects.equals(groupName, GroupEnum.Health.toString())) {
      shopItemsView.setText(String.format(getString(R.string.item), itemCount));
      return;
    }
    if (Objects.equals(groupName, GroupEnum.Travel.toString())) {
      shopItemsView.setText(String.format(getString(R.string.item), itemCount));
      return;
    }
    if (Objects.equals(groupName, GroupEnum.Bills.toString())) {
      shopItemsView.setText(String.format(getString(R.string.item), itemCount));
      return;
    }
    autoItemsView.setText(String.format(getString(R.string.item), itemCount));
  }

  @Override
  public void onResume() {
    super.onResume();
    updateAll(groups);
    setupCurrentDate();
    setupChart();
    getFinishedPercentage();
    setupDateFields();
  }

  private void getFinishedPercentage() {
    taskController.getFinishPercentage(user, date).subscribe(new SingleSubscriber<Long>() {
      @Override
      public void onSuccess(Long percentage) {
        setupCurrentPercentage(percentage.intValue());
      }

      @Override
      public void onError(Throwable error) {
        Timber.e(error);
      }
    });
  }

  private void setupCurrentPercentage(int percentage) {
    //noinspection deprecation
    SeriesItem seriesItem = new SeriesItem.Builder(getResources().getColor(R.color.seafoam_blue_two))
        .setRange(0, 100, 0)
        .setInterpolator(new DecelerateInterpolator())
        .setSpinDuration(2000)
        .setLineWidth(15f)
        .setSpinClockwise(true)
        .build();
    seriesItem.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
      @Override
      public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
        if (isAdded()) {
          percentageView.setText(String.valueOf((int) currentPosition));
        }
      }

      @Override
      public void onSeriesItemDisplayProgress(float percentComplete) {
      }
    });
    int index = cricleChartView.addSeries(seriesItem);
    cricleChartView.executeReset();
    cricleChartView.addEvent(new DecoEvent.Builder(percentage)
        .setIndex(index)
        .setDelay(100)
        .build());
  }

  private void setupChart() {
    //noinspection deprecation
    cricleChartView.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
        .setRange(0, 100, 100)
        .setLineWidth(15f)
        .build());
  }

  private void setupDateFields() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MONTH_PATTERN);
    String stringDate = date.format(formatter);
    monthTextView.setText(stringDate);
    formatter = DateTimeFormatter.ofPattern(YEAR_PATTERN);
    stringDate = date.format(formatter);
    yearTextView.setText(stringDate);
  }
}
