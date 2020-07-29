package br.unicamp.ft.l201039_l201253;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import br.unicamp.ft.l201039_l201253.sqlite.DatabaseHelper;
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

        dbHelper       = DatabaseHelper.getInstancia(this);
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

//        FloatingActionButton cfg = findViewById(R.id.configs);
//        cfg.setBackgroundTintList(ContextCompat.getColorStateList(NovoToDoActivity.this, R.color.colorSecundary));
//        cfg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(NovoToDoActivity.this, ConfigsActivity.class));
//            }
//        });

        categorias = findViewById(R.id.categorias);
        atividade = findViewById(R.id.atividade);
        notificar = findViewById(R.id.notificar);
        notificarEm = findViewById(R.id.notificarEm);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.todo_category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorias.setAdapter(adapter);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("fcm", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = token;
                        Log.w("fcm", msg);
                        Toast.makeText(NovoToDoActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        final NovoToDoActivity that = this;
        notificar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b) {
                    notificarEm.setVisibility(View.INVISIBLE);
                    return;
                }

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


        createNotificationChannel();

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ToDoList";
            String description = "appzao da massa";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("IDTODO", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void novoToDo()
    {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("ultId");
        DatabaseReference r1 = database.getReference("todos").push();
        r1.setValue(new ToDo(
                String.valueOf(r1.getKey()),
                atividade.getText().toString(),
                categorias.getSelectedItem().toString(),
                (dataLembrete != null) ? dataLembrete.getTime().toString() : ""
        ));

//        ContentValues contentValues = new ContentValues();
//        contentValues.put("atividade", atividade.getText().toString());
//        contentValues.put("categoria", categorias.getSelectedItem().toString());
//        contentValues.put("notificar", (dataLembrete != null ) ? dataLembrete.getTime().toString() : "");
//
//        sqLiteDatabase.insert("todos", null, contentValues);

        atividade.setText("");
        notificar.setChecked(false);

        Toast.makeText(this, "ToDo salvo!", Toast.LENGTH_SHORT).show();

    }

    public void setData(int ano, int mes, int dia)
    {
        dataLembrete = Calendar.getInstance();
        dataLembrete.set(ano, mes, dia, 0, 0, 0);
        notificar.setChecked(true);

        final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        notificarEm.setText("Notificar em: " + df.format(dataLembrete.getTime()));
        notificarEm.setVisibility(View.VISIBLE);
    }

}