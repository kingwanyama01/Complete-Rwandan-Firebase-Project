package com.king.mycompleterwandanfirebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {
    EditText mEdtName, mEdtAdmNo,mEdtPhone;
    Button mBtnLogout, mBtnSave, mBtnView;
    FirebaseAuth firebaseAuth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mBtnLogout = findViewById(R.id.btn_logout);
        mEdtName = findViewById(R.id.edt_name);
        mEdtAdmNo = findViewById(R.id.edt_adm_number);
        mEdtPhone = findViewById(R.id.edt_phone);
        mBtnSave = findViewById(R.id.btn_save);
        mBtnView = findViewById(R.id.btn_view);

        firebaseAuth = FirebaseAuth.getInstance();

        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        dialog = new ProgressDialog(this);
        dialog.setTitle("Saving");
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, admission_number,phone_number;
                long time;
                time = System.currentTimeMillis();
                name = mEdtName.getText().toString().trim();
                admission_number = mEdtAdmNo.getText().toString().trim();
                phone_number = mEdtPhone.getText().toString().trim();

                if (name.isEmpty()){
                    mEdtName.setError("Please enter name");
                }else if (admission_number.isEmpty()){
                    mEdtAdmNo.setError("Please enter admission number");
                }else if (phone_number.isEmpty()){
                    mEdtPhone.setError("Please enter phone number");
                }else if (phone_number.length()<10){
                    mEdtPhone.setError("Please enter a phone number with 10 or more characters");
                }else {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Students/"+time);
                    Student student = new Student(name,admission_number,phone_number,String.valueOf(time));
                    dialog.show();
                    ref.setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            dialog.dismiss();
                            if (task.isSuccessful()){
                                Toast.makeText(HomeActivity.this, "Saving successful", Toast.LENGTH_SHORT).show();
                                mEdtName.setText("");
                                mEdtAdmNo.setText("");
                                mEdtPhone.setText("");
                            }else {
                                Toast.makeText(HomeActivity.this, "Saving failed :-(", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        mBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ViewstudentsActivity.class));
            }
        });
    }
}
