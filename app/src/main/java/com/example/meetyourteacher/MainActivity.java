package com.example.meetyourteacher;


import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

//LIBRERIAS VARIAS DE PROYECTO
//REGISTRO Y LOGIN DE USUARIOS

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText TextMail, TextPassword;
    private Button btnLogin, btnRegister;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        TextMail = findViewById(R.id.txtEmail);
        TextPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegistrar);

        progressDialog = new ProgressDialog(this);

        btnLogin.setOnClickListener(this);
    }

    public void login()
    {
        //Obtencion de email y password (trim -> elimina espacios al inicio y al final del texto)
        String email = TextMail.getText().toString().trim();
        String password = TextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(MainActivity.this, "You must fill the email box.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(MainActivity.this, "You must fill the password box.", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Please Wait a Second...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Welcome.", Toast.LENGTH_LONG).show();
                            playLocalAudio();
                            Intent intentProfile= new Intent(getApplicationContext(), Profile.class);
                            startActivity(intentProfile);
                        } else {
                            progressDialog.dismiss();
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {

                                Toast.makeText(MainActivity.this, "Sorry, something went wrong.", Toast.LENGTH_LONG).show();
                            } else {Toast.makeText(MainActivity.this, "Sorry, something went wrong.", Toast.LENGTH_LONG).show();}
                        }
                    }
                });
    }

    public void CreateAccount(View view) {
        Intent intCreate = new Intent(getApplicationContext(), RegisterUser.class);
        startActivity(intCreate);
    }

    public void VideoTrailer(View view)
    {
        Intent intTrailer = new Intent(getApplicationContext(), VideoTrailer.class);
        startActivity(intTrailer);
    }

    @Override
    public void onClick(View v)
    {
        login();
    }

    private void playLocalAudio()
    {
        mediaPlayer = MediaPlayer.create(this, R.raw.welcome);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.start();
    }
}
