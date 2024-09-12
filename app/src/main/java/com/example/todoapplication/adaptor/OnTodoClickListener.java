package com.example.todoapplication.adaptor;

import com.example.todoapplication.model.Task;

public interface OnTodoClickListener {

    void onTodoClick( Task task);
    void onTodoRadioClick(Task task);
}
