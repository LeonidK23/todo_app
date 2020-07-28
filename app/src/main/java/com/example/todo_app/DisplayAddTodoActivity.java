package com.example.todo_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DisplayAddTodoActivity extends AppCompatActivity {

    public static final String EXTRA_KEY = "TODO_ITEM";

    Button btn_createTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_add_todo);

        btn_createTodo = findViewById(R.id.createTodo);

        btn_createTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.todoItem);
                String todo = editText.getText().toString();

                TodoModel newTodo;

                try {
                    newTodo = new TodoModel(-1, todo);
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Error creating todo", Toast.LENGTH_SHORT).show();
                    newTodo = new TodoModel(-1, "");
                }

                DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());

                dbHelper.addOne(newTodo);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                setResult(2, intent);
                finish();
            }
        });
    }

}