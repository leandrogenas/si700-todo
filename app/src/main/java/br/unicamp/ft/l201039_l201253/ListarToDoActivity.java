package br.unicamp.ft.l201039_l201253;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import br.unicamp.ft.l201039_l201253.firebase.ToDoFirebaseAdapter;
import br.unicamp.ft.l201039_l201253.sqlite.DatabaseHelper;
import br.unicamp.ft.l201039_l201253.sqlite.ToDoSQLiteAdapter;

public class ListarToDoActivity extends AppCompatActivity {

    private TextView mTextView;

    private RecyclerView recyclerView;

    private DatabaseReference mFirebaseDatabaseReference;
    private ToDoFirebaseAdapter toDoFirebaseAdapter;

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

//        FloatingActionButton cfg = findViewById(R.id.configs);
//        cfg.setBackgroundTintList(ContextCompat.getColorStateList(ListarToDoActivity.this, R.color.colorSecundary));
//        cfg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(ListarToDoActivity.this, ConfigsActivity.class));
//            }
//        });


        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        DatabaseReference auxRef = mFirebaseDatabaseReference.child("todos");
        SnapshotParser<ToDo> parser = new SnapshotParser<ToDo>() {
            @NonNull
            @Override
            public ToDo parseSnapshot(@NonNull DataSnapshot snapshot) {
                Object notificar = snapshot.child("notificar").getValue();
                return new ToDo(
                    snapshot.getKey(),
                    snapshot.child("atividade").getValue().toString(),
                    snapshot.child("categoria").getValue().toString(),
                    (notificar != null) ? notificar.toString() : ""
                );
            }
        };
        FirebaseRecyclerOptions<ToDo> options =
                new FirebaseRecyclerOptions.Builder<ToDo>()
                        .setQuery(auxRef, parser)
                        .build();
        toDoFirebaseAdapter = new ToDoFirebaseAdapter(options);

        recyclerView.setAdapter(toDoFirebaseAdapter);

    }

    @Override
    public void onResume(){
        super.onResume();
        toDoFirebaseAdapter.startListening();
    }

    @Override
    public void onPause(){
        super.onPause();
        toDoFirebaseAdapter.stopListening();
    }

}