package com.example.meetyourteacher;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {
    private EditText textMail, textPassword, textFullName, textUsername;
    private Button BtnRegister;
    private RadioButton radioGenderMale, radioGenderFemale;
    private ProgressDialog progressDialog;


    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);


        mAuth = FirebaseAuth.getInstance();

        textMail = findViewById(R.id.txtEmail);
        textPassword = findViewById(R.id.txtPassword);
        textFullName = findViewById(R.id.txtFullName);
        textUsername = findViewById(R.id.txtUserName);

        databaseReference = FirebaseDatabase.getInstance().getReference("MYT_User");

        BtnRegister = findViewById(R.id.btnRegister);

        progressDialog = new ProgressDialog(this);

        BtnRegister.setOnClickListener(this);

    }

    private void userRegister() {
        //Obtencion de email y password (trim -> elimina espacios al inicio y al final del texto)
        String email = textMail.getText().toString().trim();
        String password = textPassword.getText().toString().trim();
        String fullName = textFullName.getText().toString().trim();
        String username = textUsername.getText().toString().trim();
        String gender = "";

        if (radioGenderMale.isChecked()) gender = "Male";
        if (radioGenderFemale.isChecked()) gender = "Female";

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(RegisterUser.this, "You must fill the email box.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(RegisterUser.this, "You must fill the password box.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(fullName)) {
            Toast.makeText(RegisterUser.this, "You must fill the Full Name box.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(RegisterUser.this, "You must fill the Username box.", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Please Wait a Second...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterUser.this, "User successfully registered.", Toast.LENGTH_LONG).show();
                            Intent intentMain= new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intentMain);
                        } else {
                            progressDialog.dismiss();
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {

                                Toast.makeText(RegisterUser.this, "User is already registered.", Toast.LENGTH_LONG).show();
                            } else {Toast.makeText(RegisterUser.this, "User couldn't be registered.", Toast.LENGTH_LONG).show();}
                        }
                    }
                });
    }


    public void onClick(View view) {
        userRegister();
    }
}
