package com.example.todo_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> todos = new ArrayList<>();
    private int itemIdToBeDeleted;

    private Button btn_deleteAll, btn_addTodo;

    private ArrayAdapter arrayAdapter;
    private DatabaseHelper databaseHelper;
    private ListView listView;

    private TimerTask timerTask;
    private Timer timer = new Timer();

    private int timerDelay = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_deleteAll = findViewById(R.id.btn_deleteAll);
        btn_addTodo = findViewById(R.id.btn_addTodo);

        ShowTodos(new DatabaseHelper(MainActivity.this));

        btn_deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper = new DatabaseHelper(MainActivity.this);

                databaseHelper.deleteAllTodos();

                ShowTodos(databaseHelper);
            }
        });

        btn_addTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayAddTodoActivity.class);
                startActivity(intent);

                ShowTodos(new DatabaseHelper(MainActivity.this));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        ShowTodos(new DatabaseHelper(MainActivity.this));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void ShowTodos(final DatabaseHelper databaseHelper) {
        arrayAdapter = new ArrayAdapter(this, R.layout.list_item, databaseHelper.getAllTodos());
        listView = findViewById(R.id.todoListView);
        listView.setAdapter(arrayAdapter);
//        listView.setOnTouchListener(new OnSwipeTouchListener(listView));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView = (TextView) view;
                textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                itemIdToBeDeleted = position;
                initializeTimerTask();
                Toast.makeText(MainActivity.this, "Todo will be deleted in " + timerDelay/1000 + "  seconds.", Toast.LENGTH_SHORT).show();
                timer.schedule(timerTask, timerDelay);

            }
        });
    }

    private void initializeTimerTask(){
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        databaseHelper = new DatabaseHelper(MainActivity.this);
                        TodoModel itemToDelete = (TodoModel) listView.getItemAtPosition(itemIdToBeDeleted);
                        databaseHelper.deleteOneTodo(itemToDelete);
                        onResume();
                    }
                });
            }
        };
    }

}