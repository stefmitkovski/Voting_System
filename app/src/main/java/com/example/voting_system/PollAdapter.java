package com.example.voting_system;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PollAdapter extends RecyclerView.Adapter<PollAdapter.ViewHolder> {

    private static List<String> myList;
    private int rowLayout;
    private Context mContext;
    private int counter;
    private String username;
    private String type;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView text;
        public TextView timer_text;
        public CountDownTimer timer;

        public ViewHolder(View itemView){
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.title);
            timer_text = (TextView) itemView.findViewById(R.id.number);
        }
    }

    public PollAdapter(List<String> myList, int rowLayout, Context context, String username, String type){
        this.myList = myList;
        this.rowLayout = rowLayout;
        this.mContext = context;
        this.username = username;
        this.type = type;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String entry = myList.get(position);
        String[] array = entry.split(",");
        holder.text.setText(array[0]);
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent survey_intent = new Intent(mContext, SurveyVotingActivity.class);
                survey_intent.putExtra("username", username);
                survey_intent.putExtra("type", type);
                survey_intent.putExtra("title",array[0]);
                mContext.startActivity(survey_intent);
            }
        });
        counter = Integer.valueOf(array[1]);

        if(holder.timer != null){
            holder.timer.cancel();
        }
        holder.timer = new CountDownTimer(counter*1000,1000){
            @Override
            public void onTick(long l) {
                holder.timer_text.setText(String.valueOf(counter));
                counter--;
            }

            @Override
            public void onFinish() {
                myList.remove(entry);
                notifyItemChanged(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(),myList.size());
                holder.itemView.setVisibility(View.GONE);
                ((MainActivity)mContext).updateVisibility(array[0]);
            }
        }.start();
    }

    @Override
    public int getItemCount() {
        return myList == null ? 0 : myList.size();
    }

}
