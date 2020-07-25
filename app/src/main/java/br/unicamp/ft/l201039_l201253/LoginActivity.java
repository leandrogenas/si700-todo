package br.unicamp.ft.l201039_l201253;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    protected EditText txtUsuario;
    protected EditText txtSenha;
    protected Button btnLogar;

    protected FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        txtUsuario = findViewById(R.id.txtSenha);
        txtSenha = findViewById(R.id.txtUsuario);
        btnLogar = findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();


    }

    

}
