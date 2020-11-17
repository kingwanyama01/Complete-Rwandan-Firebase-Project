package com.king.mycompleterwandanfirebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText mEdtMail, mEdtPass;
    Button mBtnLogin;
    TextView mTvReg, mTvForgotPassword;
    FirebaseAuth firebaseAuth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEdtMail = findViewById(R.id.edit_mail);
        mEdtPass = findViewById(R.id.edt_pass);
        mBtnLogin = findViewById(R.id.btn_register);
        mTvReg = findViewById(R.id.tv_login);
        mTvForgotPassword = findViewById(R.id.tv_forgot_password);

        mTvReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        }

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = mEdtMail.getText().toString().trim();
                password = mEdtPass.getText().toString().trim();

                if (email.isEmpty()){
                    mEdtMail.setError("Please enter email");
                }else if (password.isEmpty()){
                    mEdtPass.setError("Please enter password");
                }else if (password.length()<6){
                    mEdtPass.setError("Please enter a password with 6 or more charavters");
                }else {
                    dialog.show();
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            dialog.dismiss();
                            if (task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            }else {
                                Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        mTvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PasswordresetActivity.class));
            }
        });


    }
}
