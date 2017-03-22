package com.xmartlabs.scasas.doapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import com.hannesdorfmann.fragmentargs.bundler.ParcelerArgsBundler;
import com.rey.material.widget.Spinner;
import com.xmartlabs.scasas.doapp.R;
import com.xmartlabs.scasas.doapp.controller.GroupController;
import com.xmartlabs.scasas.doapp.controller.TaskController;
import com.xmartlabs.scasas.doapp.model.Group;
import com.xmartlabs.scasas.doapp.model.Task;
import com.xmartlabs.scasas.doapp.model.User;

import org.threeten.bp.LocalDate;

import java.util.List;
import java.util.function.Predicate;

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

    @Inject
    GroupController groupController;
    @Inject
    TaskController taskController;

    @Arg(bundler = ParcelerArgsBundler.class)
    User user;

    private List<Group> groups;

    @Override
    protected int getLayoutResId() {
        return R.layout.list_groups_fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        goToListOfTasksFragment();
    }

    @OnClick(R.id.add_task)
    void onClickedAddTextView() {
        Task task = Task.builder()
                .title(titleView.getText().toString().trim())
                .description(descriptionView.getText().toString().trim())
                .date(LocalDate.now())
                .isFinished(false)
                .build();
        insertTask(task);
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

    private void adjustSpinnerView(Spinner spinner) {
        TextView textView = (TextView) spinner.getSelectedView();
        //noinspection deprecation
        textView.setTextColor(getResources().getColor(R.color.white));
        textView.setTextSize(2, 14);
    }

    private void goToListOfTasksFragment() {
        Intent intent = Henson.with(getContext()).gotoTasksListActivity().build();
        getContext().startActivity(intent);
    }

    private void getGroups() {
        groupController.getGroups().subscribe(new SingleSubscriber<List<Group>>() {
            @Override
            public void onSuccess(List<Group> groupList) {
                groups = groupList;
            }

            @Override
            public void onError(Throwable error) {
                Timber.e(error);
            }
        });
    }

    private Group getGroup(String name) {
        return groups.stream()
                .findFirst()
                .filter(group -> name.equals(group.getName()))
                .get();
    }

    private void insertTask(Task task) {
        taskController.insertTask(task)
                .subscribe(new SingleSubscriber<Task>() {
                    @Override
                    public void onSuccess(Task task) {
                        Snackbar.make(getView(), R.string.task_added, Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable error) {
                        Timber.e(error);
                    }
                });
    }
}
