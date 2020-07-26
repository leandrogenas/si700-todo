package br.unicamp.ft.l201039_l201253;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    @SingleValueAnnotation("Lista")
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "TODOS";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    @DatabaseDetails(
            value={"ativ1: atividade", "cat1: categoria"},
            version={1}
    )
    public void onCreate(SQLiteDatabase db) {
        /*
           Configurações iniciais do banco de dados.
         */
        db.execSQL("CREATE TABLE todos (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, atividade Text, categoria Text);");
    }

    public ArrayList<HashMap<String, String>> getToDos(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> todos = new ArrayList<>();
        String query = "SELECT * FROM todos";
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> todo = new HashMap<>();
            todo.put("atividade",cursor.getString(cursor.getColumnIndex("atividade")));
            todo.put("categoria",cursor.getString(cursor.getColumnIndex("categoria")));
            //user.put("location",cursor.getString(cursor.getColumnIndex(KEY_LOC)));
            todos.add(todo);
        }
        return todos;
    }

    @Override
    @MarkerInterface
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
