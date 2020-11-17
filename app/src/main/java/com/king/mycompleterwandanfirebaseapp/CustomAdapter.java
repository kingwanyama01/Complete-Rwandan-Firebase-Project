package com.king.mycompleterwandanfirebaseapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;



public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Student> mUploads;
    private OnItemClickListener mListener;

    public CustomAdapter(Context mContext, List<Student> mUploads) {
        this.mContext = mContext;
        this.mUploads = mUploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_layout,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        final Student sudent = mUploads.get(position);

        holder.mTvName.setText(sudent.getName());
        holder.mTvPhone.setText(sudent.getPhone());
        holder.mTvAdmNo.setText(sudent.getAdm_no());
        holder.mTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("DELETING RECORD!!");
                builder.setMessage("Are you sure you want to delete this record?");
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Students/"+sudent.getKey());
                        ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(mContext, "Record deleted successfully", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(mContext, "Record deletion failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        TextView mTvName, mTvPhone,mTvAdmNo,mTvDelete;
        public ImageViewHolder(View itemView) {
            super(itemView);
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvPhone = itemView.findViewById(R.id.tv_phone);
            mTvAdmNo = itemView.findViewById(R.id.tv_adm_number);
            mTvDelete = itemView.findViewById(R.id.tv_delete);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mListener!=null){
                //Get the position of the clicked item
                int position = getAdapterPosition();
                if (position!=RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }
        // Handle Menu Items
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem doWhatever = menu.add(Menu.NONE, 1, 1,"Verify code");
            MenuItem delete = menu.add(Menu.NONE,2,2,"Delete");
            doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener!=null){
                //Get the position of the clicked item
                int position = getAdapterPosition();
                if (position!=RecyclerView.NO_POSITION){
                    switch (item.getItemId()){
                        case 1:
                            mListener.onVerifyClick(position);
                            return true;
                        case 2:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position);

        void onVerifyClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public void setSearchOperation(List<Student> newList){
        mUploads = new ArrayList<>();
        mUploads.addAll(newList);
        notifyDataSetChanged();
    }
}