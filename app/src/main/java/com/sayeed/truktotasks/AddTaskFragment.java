package com.sayeed.truktotasks;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddTaskFragment extends Fragment {


    @BindView(R.id.task)
    TextInputEditText task;
    @BindView(R.id.input_task)
    TextInputLayout inputTask;
    @BindView(R.id.button_save)
    Button buttonSave;
    DataPassListener mCallback;


    public AddTaskFragment() {
        // Required empty public constructor
    }

    public interface DataPassListener {
        public void passData(String gameID);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (DataPassListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement DataPassListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frg_add_task, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.button_save)
    public void onViewClicked() {
        if (TextUtils.isEmpty(task.getText())) {
            inputTask.setError(getResources().getString(R.string.empty_not_saved));
        } else if (task.getText() != null) {
            String taskDescription = task.getText().toString();
            mCallback.passData(taskDescription);

        }
    }
}
