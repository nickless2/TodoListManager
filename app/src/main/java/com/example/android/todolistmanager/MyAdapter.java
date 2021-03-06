package com.example.android.todolistmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<DataEntry> ToDoList;
    private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView task;
        public TextView date;

        public ViewHolder(ViewGroup v) {
            super(v);
            task = (TextView) v.findViewById(R.id.task);
            date = (TextView) v.findViewById(R.id.date);
        }
    }


    public MyAdapter(List<DataEntry> list) {
        this.ToDoList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        final ViewHolder vh = new ViewHolder(viewGroup);


        viewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(vh.task.getText());
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeAt(vh.getAdapterPosition());

                        Collections.sort(ToDoList, new Comparator<DataEntry>() {
                            @Override
                            public int compare(DataEntry o1, DataEntry o2) {
                                return o1.getDate().compareTo(o2.getDate());
                            }
                        });
                        notifyDataSetChanged();
                    }
                });
                String testText = vh.task.getText().toString();
                if (testText.startsWith("call") || testText.startsWith("Call")) {
                    builder.setNegativeButton("call", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                }
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.date.setText(format.format(ToDoList.get(position).getDate().getTime()));
        holder.task.setText(ToDoList.get(position).getTask());

        if (ToDoList.get(position).getDate().before(Calendar.getInstance())){
            holder.date.setTextColor(Color.RED);
            holder.task.setTextColor(Color.RED);
        } else {
            holder.date.setTextColor(Color.GRAY);
            holder.date.setTextColor(Color.GRAY);
        }
    }

    public void removeAt(int index){
        ToDoList.remove(index);
    }

    @Override
    public int getItemCount() {
        return ToDoList.size();
    }

}