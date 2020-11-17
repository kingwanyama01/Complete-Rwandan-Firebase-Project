package com.king.mycompleterwandanfirebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewstudentsActivity extends AppCompatActivity {
    RecyclerView mRecyclerview;
    ArrayList<Student> students;
    CustomAdapter mAdapter;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewstudents);
        mRecyclerview = findViewById(R.id.recycler_students);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        students = new ArrayList<>();
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CustomAdapter(this,students);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Students");
        dialog.show();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                students.clear();
                for (DataSnapshot snap:dataSnapshot.getChildren()) {
                    Student student = snap.getValue(Student.class);
                    students.add(student);
                }
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewstudentsActivity.this, "Database locked, please contact your app developer for assistance", Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerview.setAdapter(mAdapter);
    }
}
