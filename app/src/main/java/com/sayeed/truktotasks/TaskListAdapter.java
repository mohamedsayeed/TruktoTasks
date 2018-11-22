package com.sayeed.truktotasks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sayeed.truktotasks.data.Task;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

    private final Context context;

    class TaskViewHolder extends RecyclerView.ViewHolder {
        private final TextView taskDescription;
        private final LinearLayout lTask;

        private TaskViewHolder(View itemView) {
            super(itemView);
            taskDescription = itemView.findViewById(R.id.task_description);
            lTask = itemView.findViewById(R.id.l_task);
        }

        private void bind(Task task) {
            // Get the state
            boolean expanded = task.isExpanded();
            if (expanded)
                lTask.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            else
                lTask.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    private final LayoutInflater mInflater;
    private List<Task> mTasks; // Cached copy of tasks

    TaskListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.row_task, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TaskViewHolder holder, int position) {
        if (mTasks != null) {
            final Task current = mTasks.get(position);
            holder.bind(current);
            holder.taskDescription.setText(current.getTaskDescription());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the current state of the item
                    boolean expanded = current.isExpanded();
                    // Change the state
                    current.setExpanded(!expanded);
                    // Notify the adapter that item has changed
                    notifyItemChanged(holder.getAdapterPosition());
                }
            });
        } else {
            // Covers the case of data not being ready yet.
            holder.taskDescription.setText("No Tasks");
        }

    }

    void setTasks(List<Task> tasks) {
        mTasks = tasks;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTasks != null)
            return mTasks.size();
        else return 0;
    }
}