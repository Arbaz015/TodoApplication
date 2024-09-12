package com.example.todoapplication.util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.todoapplication.model.Priority;
import com.example.todoapplication.model.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String formatDate(Date date)
    {
        SimpleDateFormat simpleDateFormat =(SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern("EEE,MMM,d");

        return simpleDateFormat.format(date);
    }

    //Hide Soft Keyword
    public static void hidSoftKeyboard(View view)
    {
        InputMethodManager imm= (InputMethodManager) view.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    public static int priorityColor(Task task) {

        int color;
        if (task.getPriority()== Priority.HIGH)
        {
            color= Color.argb(200,201,21,23);
        }else if (task.getPriority()==Priority.MEDIUM)
        {
            color=Color.argb(200,155,179,0);
        }else
        {
            color=Color.argb(200,54,181,129);
        }
        return color;
    }
} 
