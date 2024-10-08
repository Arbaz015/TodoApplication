package com.example.todoapplication.adaptor;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapplication.R;
import com.example.todoapplication.model.Task;
import com.example.todoapplication.util.Utils;
import com.google.android.material.chip.Chip;

import java.util.List;

public class RecyclerViewAdaptor extends RecyclerView.Adapter<RecyclerViewAdaptor.ViewHolder> {

    private final OnTodoClickListener todoClickListener;
    private final List<Task> taskList;

    public RecyclerViewAdaptor( List<Task> taskList,OnTodoClickListener onTodoClickListener) {
        this.todoClickListener = onTodoClickListener;
        this.taskList = taskList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public AppCompatRadioButton radioButton;
        public AppCompatTextView task;
        public Chip todayChip;

        OnTodoClickListener onTodoClickListener;
        @SuppressLint("WrongViewCast")
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton=itemView.findViewById(R.id.todo_radio_button);
            task=itemView.findViewById(R.id.todo_row_todo);
            todayChip=itemView.findViewById(R.id.todo_row_chip);
            this.onTodoClickListener=todoClickListener;
            itemView.setOnClickListener(this);
            radioButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int id=v.getId();
            Task currentTask=taskList.get(getAdapterPosition());
            if (id==R.id.todo_row_layout)
            {

                onTodoClickListener.onTodoClick(currentTask);
            } else if (id==R.id.todo_radio_button) {

             onTodoClickListener.onTodoRadioClick(currentTask);
            }

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
            View view =LayoutInflater.from(parent.getContext())
            .inflate(R.layout.todo_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Task task=taskList.get(position);
        String formatted= Utils.formatDate(task.dueDate);

        ColorStateList colorStateList= new ColorStateList( new int[][]{
                new int[]{-android.R.attr.state_enabled},
                new int[]{android.R.attr.state_enabled}
        },
                new int[]{
                        Color.LTGRAY,// disabled
                        Utils.priorityColor(task)
                });
        holder.task.setText(task.getTask());
        holder.todayChip.setText(formatted);
        holder.todayChip.setTextColor(Utils.priorityColor(task));
        holder.todayChip.setChipIconTint(colorStateList);
        holder.radioButton.setButtonTintList(colorStateList);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }


}
