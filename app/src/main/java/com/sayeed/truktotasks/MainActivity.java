package com.sayeed.truktotasks;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.sayeed.truktotasks.data.Task;
import com.sayeed.truktotasks.data.TaskViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements AddTaskFragment.DataPassListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rcv_tasks)
    RecyclerView rcvTasks;
    @BindView(R.id.add_task)
    FloatingActionButton addTask;
    @BindView(R.id.fragment_content)
    FrameLayout fragmentContent;

    private TaskViewModel mTaskViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        final TaskListAdapter adapter = new TaskListAdapter(this);
        rcvTasks.setAdapter(adapter);
        rcvTasks.setLayoutManager(new LinearLayoutManager(this));
        mTaskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        mTaskViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable final List<Task> tasks) {
                // Update the cached copy of the tasks in the adapter.
                adapter.setTasks(tasks);
            }
        });

    }

    @Override
    public void onBackPressed() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (getSupportFragmentManager().getBackStackEntryCount() < 1) {
            super.onBackPressed();
        } else
            getSupportFragmentManager().popBackStack();
        hideKeyboard(MainActivity.this);
        fragmentContent.setVisibility(View.GONE);
        addTask.show();
    }

    @OnClick(R.id.add_task)
    public void onViewClicked(View view) {
        addTask.hide();
        Fragment mFragment = new AddTaskFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        fragmentContent.setVisibility(View.VISIBLE);
        ft.replace(R.id.fragment_content, mFragment, mFragment.getClass().getName());
        ft.addToBackStack(null);
        ft.commit();
    }


    @Override
    public void passData(String taskDescription) {
        getSupportFragmentManager().popBackStack();
        fragmentContent.setVisibility(View.GONE);
        hideKeyboard(MainActivity.this);
        Task task = new Task(taskDescription);
        mTaskViewModel.insert(task);
    }

    private static void hideKeyboard(Context context) {
        View view = ((Activity) context).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
