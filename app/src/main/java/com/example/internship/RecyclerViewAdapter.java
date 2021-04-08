package com.example.internship;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Context context;
    List<Users> InfoList;
    public RecyclerViewAdapter(Context context, List<Users> list) {
        this.context=context;
        this.InfoList=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users user = InfoList.get(position);

        holder.StudentName.setText(user.getName());



    }

    @Override
    public int getItemCount() {
        return InfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView StudentName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            StudentName=(TextView)itemView.findViewById(R.id.ShowStudentNameTextView);
        }
    }
}
