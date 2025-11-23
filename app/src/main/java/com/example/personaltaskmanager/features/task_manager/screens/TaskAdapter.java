package com.example.personaltaskmanager.features.task_manager.screens;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personaltaskmanager.R;
import com.example.personaltaskmanager.features.task_manager.data.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter hiển thị danh sách Task trong RecyclerView.
 * - Sử dụng ViewHolder pattern.
 * - Hỗ trợ click vào từng item để mở chi tiết.
 *
 * Note:
 *  Đây chỉ là adapter cho UI, không chứa logic load DB.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList = new ArrayList<>();
    private OnTaskClickListener listener;

    public interface OnTaskClickListener {
        void onTaskClick(Task task);
    }

    public TaskAdapter(OnTaskClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<Task> tasks) {
        this.taskList = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feature_task_manager_item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        holder.textTitle.setText(task.getTitle());
        holder.textDeadline.setText(task.getDescription()); // tạm dùng desc làm deadline

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onTaskClick(task);
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        ImageView imgTask;
        TextView textTitle, textDeadline;
        CheckBox checkboxTask;
        ImageButton btnDelete;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            imgTask = itemView.findViewById(R.id.imgTask);
            textTitle = itemView.findViewById(R.id.textTaskTitle);
            textDeadline = itemView.findViewById(R.id.textTaskDeadline);
            checkboxTask = itemView.findViewById(R.id.checkboxTask);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
