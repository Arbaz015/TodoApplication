package com.example.todoapplication.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.todoapplication.model.Task;
import com.example.todoapplication.util.TaskRoomDataBase;

import java.util.List;

public class TodoAppRepository {

    private final TaskDao taskDao;
    private final LiveData<List<Task>> allTasks;

    public TodoAppRepository(Application application) {
        TaskRoomDataBase dataBase= TaskRoomDataBase.getInstance( application);
        taskDao = dataBase.taskDao();
        allTasks = taskDao.getTasks();
    }

    public  LiveData<List<Task>> getAllTasks(){
        return allTasks;
    }
    public void insert(Task task){
        TaskRoomDataBase.databaseWriterExecutor.execute(()->taskDao.insertTask(task));
    }
    public void update(Task task){
        TaskRoomDataBase.databaseWriterExecutor.execute(()->taskDao.update(task));
    }
    public void delete(Task task){
        TaskRoomDataBase.databaseWriterExecutor.execute(()->taskDao.delete(task));
    }
    public LiveData<Task> get(long id){return  taskDao.getTask(id);}
}
