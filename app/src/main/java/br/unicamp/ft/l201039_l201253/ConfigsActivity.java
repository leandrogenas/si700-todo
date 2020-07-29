package br.unicamp.ft.l201039_l201253;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import br.unicamp.ft.l201039_l201253.util.DatePickerFragment;

public class ConfigsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configs_activity);

        FloatingActionButton adc = findViewById(R.id.adicionar);
        adc.setBackgroundTintList(ContextCompat.getColorStateList(ConfigsActivity.this, R.color.colorSecundary));
        adc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConfigsActivity.this, NovoToDoActivity.class));
            }
        });

        FloatingActionButton lst = findViewById(R.id.listar);
        lst.setBackgroundTintList(ContextCompat.getColorStateList(ConfigsActivity.this, R.color.colorSecundary));
        lst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConfigsActivity.this, ListarToDoActivity.class));
            }
        });

        FloatingActionButton cfg = findViewById(R.id.configs);
        cfg.setBackgroundTintList(ContextCompat.getColorStateList(ConfigsActivity.this, R.color.colorFundo));
        cfg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConfigsActivity.this, ConfigsActivity.class));
            }
        });

    }

}