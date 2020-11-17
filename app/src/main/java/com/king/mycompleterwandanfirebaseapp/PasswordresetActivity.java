package com.king.mycompleterwandanfirebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordresetActivity extends AppCompatActivity {
    EditText mEdtMail;
    Button mBtnReset;
    FirebaseAuth firebaseAuth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordreset);
        mEdtMail = findViewById(R.id.edit_mail);
        mBtnReset = findViewById(R.id.btn_reset);
        firebaseAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Reseting");
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        mBtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email;
                email = mEdtMail.getText().toString().trim();
                if (email.isEmpty()){
                    mEdtMail.setError("Please enter email");
                }else {
                    dialog.show();
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            dialog.dismiss();
                            if (task.isSuccessful()){
                                Toast.makeText(PasswordresetActivity.this, "Password resent link sent to "+email, Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(PasswordresetActivity.this, "Password reset failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
