package com.example.todoapplication.util;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.todoapplication.model.Task;
import com.example.todoapplication.model.TaskViewModel;

public class AlertMessage implements DialogInterface.OnClickListener {

        private Context context;
        Task task;
        public AlertMessage(Context context,Task task ) {
            this.context = context;
            this.task=task;
        }

        public static void showAlertDialog(Context context, String message,Task task) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Delete Confirmation");
            builder.setMessage(message);
            builder.setPositiveButton("Yes", new AlertMessage(context,task));
            builder.setNegativeButton("No", null);


            AlertDialog alertDialog= builder.create();
            alertDialog.show();
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            // Handle the user's button click here
            if (which == DialogInterface.BUTTON_POSITIVE) {
                TaskViewModel.delete(task);
            }
        }
    }


