package br.unicamp.ft.l201039_l201253.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import br.unicamp.ft.l201039_l201253.ToDo;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper instancia;

    @SingleValueAnnotation("Lista")
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "TODOS";

    private DatabaseHelper(@Nullable Context context) {
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
        db.execSQL("CREATE TABLE todos (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, atividade Text, categoria Text, notificar Text);");
    }

    public ArrayList<ToDo> getToDos(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<ToDo> todos = new ArrayList<>();
        String query = "SELECT * FROM todos";
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            todos.add(new ToDo(
                cursor.getString(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("atividade")),
                cursor.getString(cursor.getColumnIndex("categoria")),
                cursor.getString(cursor.getColumnIndex("notificar"))
            ));
        }
        return todos;
    }

    public void limpar()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("todos", "1", null);
    }

    public void excluirId(String id)
    {
        this.getWritableDatabase().delete("todos", "id="+id, null);
    }

    @Override
    @MarkerInterface
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    public static DatabaseHelper getInstancia(Context context)
    {
        if(instancia != null)
            return instancia;

        instancia = new DatabaseHelper(context);

        return instancia;
    }

}
