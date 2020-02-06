package com.example.jhon.calculator;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.jhon.calculator.db.HistoryData;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Jhon on 2020/1/29.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{

    private List<HistoryData> myDataList;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        EditText historyInput;
        EditText historyResult;
        Button deleteHistoryButton;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            historyInput=(EditText)itemView.findViewById(R.id.history_input);
            historyResult=(EditText)itemView.findViewById(R.id.history_result);
            deleteHistoryButton=(Button)itemView.findViewById(R.id.delete_history_button);
        }
    }

    public HistoryAdapter(List<HistoryData> dataList)
    {
        myDataList=dataList;
    }

    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final HistoryAdapter.ViewHolder holder, final int position) {
        HistoryData data=myDataList.get(position);
        holder.historyInput.setText("输入："+data.getInput());
        holder.historyResult.setText("输出："+data.getResult());
        holder.deleteHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.delete(HistoryData.class,myDataList.get(position).getId());
                myDataList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return myDataList.size();
    }
}
