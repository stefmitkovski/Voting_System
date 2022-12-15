package com.example.voting_system;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PollResultAdapter extends RecyclerView.Adapter<PollResultAdapter.ViewHolder> {
    private static List<String> myList;
    private int rowLayout;
    private Context mContext;
    private String username;
    private String type;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    public PollResultAdapter(List<String> myList, int rowLayout, Context context, String username, String type){
        this.myList = myList;
        this.rowLayout = rowLayout;
        this.mContext = context;
        this.username = username;
        this.type = type;
    }

    @NonNull
    @Override
    public PollResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new PollResultAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PollResultAdapter.ViewHolder holder, int position) {
        String entry = myList.get(position);
        holder.title.setText(entry);

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,SurveyResultActivity.class);
                intent.putExtra("title",entry);
                intent.putExtra("username", username);
                intent.putExtra("type", type);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myList == null ? 0 : myList.size();
    }
}
