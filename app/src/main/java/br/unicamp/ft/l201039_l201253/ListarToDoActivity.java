package br.unicamp.ft.l201039_l201253;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListarToDoActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_todo);

        mTextView = (TextView) findViewById(R.id.text);

        FloatingActionButton adc = findViewById(R.id.adicionar);
        adc.setBackgroundTintList(ContextCompat.getColorStateList(ListarToDoActivity.this, R.color.colorSecundary));
        adc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListarToDoActivity.this, NovoToDoActivity.class));
            }
        });

        FloatingActionButton lst = findViewById(R.id.listar);
        lst.setBackgroundTintList(ContextCompat.getColorStateList(ListarToDoActivity.this, R.color.colorFundo));
        lst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListarToDoActivity.this, ListarToDoActivity.class));
            }
        });
    }
}