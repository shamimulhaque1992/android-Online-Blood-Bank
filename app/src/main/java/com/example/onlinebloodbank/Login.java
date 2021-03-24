package com.example.onlinebloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText vedtmaillog,vedtpasslog;
    Button vbtnlogin,vbtngtore;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        vedtmaillog=findViewById(R.id.edtmaillog);
        vedtpasslog=findViewById(R.id.edtpasslog);
        vbtnlogin=findViewById(R.id.btnlogin);
        vbtngtore=findViewById(R.id.btngtore);
        fAuth=FirebaseAuth.getInstance();




        vbtnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=vedtmaillog.getText().toString().trim();

                String password=vedtpasslog.getText().toString().trim();
                if (TextUtils.isEmpty(email)){
                    vedtmaillog.setError("Email is Required.");
                    return;
                }



                if (TextUtils.isEmpty(password)){
                    vedtpasslog.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    vedtpasslog.setError("Password must be more then six characters.");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){
                           Toast.makeText(Login.this, "Logged in Successfully!", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(getApplicationContext(),Search.class));
                       }
                       else{
                           Toast.makeText(Login.this, "Error!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                       }
                    }
                });
            }
        });

        vbtngtore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });




    }
}