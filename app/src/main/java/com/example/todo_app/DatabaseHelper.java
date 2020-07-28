package com.example.todo_app;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TODO_TABLE = "TODO_TABLE";
    public static final String COLUMN_TODO_ITEM = "TODO_ITEM";
    public static final String COLUMN_ID = "ID";

    public DatabaseHelper(@Nullable Context context){
        super(context, "todo.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "create table " + TODO_TABLE + " (" + COLUMN_ID +
                " integer primary key autoincrement ," + COLUMN_TODO_ITEM + " text)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(TodoModel todoModel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TODO_ITEM, todoModel.getItem());

        long insert = db.insert(TODO_TABLE, null, cv);

        return insert != -1;

    }

    public List<TodoModel> getAllTodos(){
        List<TodoModel> todoList = new ArrayList<>();

        String queryString = "SELECT * FROM " + TODO_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do {
                int todoId = cursor.getInt(0);
                String todoItem = cursor.getString(1);

                TodoModel newTodo = new TodoModel(todoId, todoItem);

                todoList.add(newTodo);
            } while (cursor.moveToNext());
        } else {

        }

        cursor.close();
        db.close();

        return todoList;
    }

    public void deleteAllTodos(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TODO_TABLE, null, null);

        db.close();
    }

    public void deleteOneTodo(TodoModel todoModel){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TODO_TABLE, COLUMN_ID + "=" + todoModel.getId(), null);

        db.close();
    }
}
