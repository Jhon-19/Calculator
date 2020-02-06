package com.example.jhon.calculator;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.jhon.calculator.db.HistoryData;

import org.litepal.crud.DataSupport;

import java.util.List;

public class History extends AppCompatActivity {

    private RecyclerView historyRecyclerView;
    private List<HistoryData> dataList;
    private SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        dataList= DataSupport.order("id desc")
                .find(HistoryData.class);
        historyRecyclerView=(RecyclerView)findViewById(R.id.history_list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        historyRecyclerView.setLayoutManager(layoutManager);
        final HistoryAdapter adapter=new HistoryAdapter(dataList);
        historyRecyclerView.setAdapter(adapter);
        refresh=(SwipeRefreshLayout)findViewById(R.id.refresh);
        refresh.setColorSchemeResources(R.color.colorPrimary);

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Snackbar.make(historyRecyclerView,"是否删除全部历史记录？",Snackbar.LENGTH_LONG)
                        .setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dataList.clear();
                        DataSupport.deleteAll(HistoryData.class);
                        adapter.notifyDataSetChanged();
                    }
                }).show();
                refresh.setRefreshing(false);
            }
        });
    }
}
