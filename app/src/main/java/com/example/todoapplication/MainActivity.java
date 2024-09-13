package com.example.todoapplication;

import static com.example.todoapplication.R.string.app_name;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapplication.adaptor.OnTodoClickListener;
import com.example.todoapplication.adaptor.RecyclerViewAdaptor;
import com.example.todoapplication.model.Priority;
import com.example.todoapplication.model.SharedViewModel;
import com.example.todoapplication.model.Task;
import com.example.todoapplication.model.TaskViewModel;
import com.example.todoapplication.util.AlertMessage;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnTodoClickListener {
    private  TaskViewModel taskViewModel;
    public static final String TAG="ITEMS";
    private RecyclerView recyclerView;
    private RecyclerViewAdaptor recyclerViewAdaptor;
    private int counter;
    BottomSheetFragment bottomSheetFragment;
    AlertDialog.Builder builder;
    private SharedViewModel sharedViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar= findViewById(R.id.toolbar);
        counter=0;
        recyclerView=findViewById(R.id.recycler_view);
        bottomSheetFragment= new BottomSheetFragment();
        ConstraintLayout constraintLayout= findViewById(R.id.bottomSheet);



        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior= BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskViewModel=new ViewModelProvider.AndroidViewModelFactory(
                MainActivity.this.getApplication())
                .create(TaskViewModel.class);
        sharedViewModel= new ViewModelProvider(this).get(SharedViewModel.class);
        if (sharedViewModel.getSelectedItem().getValue()!=null)
        {
            Task task=sharedViewModel.getSelectedItem().getValue();
            Log.d("Tag","onViewCreated"+task.getTask());
        }
        taskViewModel.getAllTasks().observe(this, tasks -> {

//            for(Task task : tasks)t
//            {
//                Log.d(TAG,"onCreate"+task.taskId);
//            }
            recyclerViewAdaptor=new RecyclerViewAdaptor(tasks,this);
            recyclerView.setAdapter(recyclerViewAdaptor);

        });

        FloatingActionButton fab=findViewById(R.id.fab);
        fab.setOnClickListener(v->{

            
//            Task task= new Task("Task"+counter++, Priority.MEDIUM, Calendar.getInstance().getTime(),
//                    Calendar.getInstance().getTime(),false);
//
//            TaskViewModel.insert(task);
            
            showBottomSheetDialog();
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);

return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
int id=item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, AboutActivity.class));

        }
        return super.onOptionsItemSelected(item);
    }

    private void showBottomSheetDialog() {

        bottomSheetFragment.show(getSupportFragmentManager(),bottomSheetFragment.getTag() );

    }

    @Override
    public void onTodoClick(Task task) {
//        Log.d("Click","onTodoCLick"+task.getTask());
        sharedViewModel.selectItem(task);
        sharedViewModel.setIsEdit(true);
        showBottomSheetDialog();

    }

    @Override
    public void onTodoRadioClick(Task task) {

            AlertMessage.showAlertDialog(this,"Are You Sure",task);
            recyclerViewAdaptor.notifyDataSetChanged();
    }



}