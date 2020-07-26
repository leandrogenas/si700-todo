package br.unicamp.ft.l201039_l201253;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import br.unicamp.ft.l201039_l201253.util.DatePickerFragment;

public class NovoToDoActivity extends AppCompatActivity {

    public static int ano;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    protected Calendar dataLembrete;

    protected EditText atividade;
    protected Spinner categorias;
    protected Switch notificar;
    protected TextView notificarEm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.novo_todo);

        dbHelper       = new DatabaseHelper(this);
        sqLiteDatabase = dbHelper.getReadableDatabase();

        FloatingActionButton adc = findViewById(R.id.adicionar);
        adc.setBackgroundTintList(ContextCompat.getColorStateList(NovoToDoActivity.this, R.color.colorFundo));
        adc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NovoToDoActivity.this, NovoToDoActivity.class));
            }
        });

        FloatingActionButton lst = findViewById(R.id.listar);
        lst.setBackgroundTintList(ContextCompat.getColorStateList(NovoToDoActivity.this, R.color.colorSecundary));
        lst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NovoToDoActivity.this, ListarToDoActivity.class));
            }
        });

        categorias = findViewById(R.id.categorias);
        atividade = findViewById(R.id.atividade);
        notificar = findViewById(R.id.notificar);
        notificarEm = findViewById(R.id.notificarEm);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.todo_category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorias.setAdapter(adapter);


        final NovoToDoActivity that = this;
        notificar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b)
                    return;

                DatePickerFragment newFragment = new DatePickerFragment(that);
                newFragment.show(getSupportFragmentManager(), "datePicker");

            }
        });

        findViewById(R.id.salvar)
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    novoToDo();
                }
            });

    }

    public void novoToDo()
    {
        Log.w("tag", "novo");
        ContentValues contentValues = new ContentValues();
        contentValues.put("atividade", atividade.getText().toString());
        contentValues.put("categoria", categorias.getSelectedItem().toString());

        sqLiteDatabase.insert("todos", null, contentValues);

        ArrayList<HashMap<String, String>> todos = dbHelper.getToDos();
        for(HashMap<String, String> todo: todos){
            Log.w("todo", todo.get("atividade"));
        }

    }

    public void setData(int ano, int mes, int dia)
    {
        dataLembrete = Calendar.getInstance();
        dataLembrete.set(ano, mes, dia);
        notificar.setChecked(true);
        notificarEm.setText("Notificar em: " + dataLembrete.getTime());
        notificarEm.setVisibility(View.VISIBLE);
    }

}