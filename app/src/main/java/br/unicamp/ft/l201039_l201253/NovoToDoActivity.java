package br.unicamp.ft.l201039_l201253;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Calendar;

import br.unicamp.ft.l201039_l201253.util.DatePickerFragment;

public class NovoToDoActivity extends AppCompatActivity {

    public static int ano;

    protected Calendar dataLembrete;

    protected EditText atividade;
    protected Spinner categorias;
    protected Switch notificar;
    protected TextView notificarEm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.novo_todo);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

    }

    public void setData(int ano, int mes, int dia)
    {
        dataLembrete = Calendar.getInstance();
        dataLembrete.set(ano, mes, dia);
        notificar.setChecked(true);
        notificarEm.setText("Notificar em: " + dataLembrete.getTime());
        notificarEm.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}