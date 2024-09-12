package com.example.todoapplication.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.todoapplication.data.TaskDao;
import com.example.todoapplication.model.Task;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class},version = 1,exportSchema = false)
@TypeConverters({Convertor.class})
public abstract class TaskRoomDataBase extends RoomDatabase {

    public static final int NUMBER_OF_THREADS=4;
    public static final String DATABASE_NAME="todoapp_database";
    public static volatile TaskRoomDataBase INSTANCE;
    public static final ExecutorService databaseWriterExecutor
            = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static final RoomDatabase.Callback sRoomDataBaseCallback =
            new RoomDatabase.Callback()
            {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    databaseWriterExecutor.execute(()->{
                        TaskDao taskDao= INSTANCE.taskDao();
                        taskDao.deleteAll();

                    });
                }
            };
    public static TaskRoomDataBase getInstance(final Context context)
    {
        if (INSTANCE==null) {
            synchronized (TaskRoomDataBase.class)
            {
                if (INSTANCE==null)
                {
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                            TaskRoomDataBase.class,DATABASE_NAME)
                            .addCallback(sRoomDataBaseCallback).build();
                }
            }

        }
        return INSTANCE;

    }
    public abstract TaskDao taskDao();

}
