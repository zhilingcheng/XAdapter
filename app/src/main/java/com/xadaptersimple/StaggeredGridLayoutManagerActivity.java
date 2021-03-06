package com.xadaptersimple;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xadapter.adapter.XBaseAdapter;
import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.holder.XViewHolder;
import com.xadapter.widget.BaseRefreshHeader;
import com.xadapter.widget.XFooterLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2016/11/17
 */

public class StaggeredGridLayoutManagerActivity extends AppCompatActivity {
    private XRecyclerViewAdapter<MainBean> xRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_layout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        List<MainBean> mainBeen = new ArrayList<>();
        DataUtils.getData(mainBeen);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        xRecyclerViewAdapter = new XRecyclerViewAdapter<>();
        recyclerView.setAdapter(
                xRecyclerViewAdapter
                        .initXData(mainBeen)
                        .setLayoutId(R.layout.item)
                        .addRecyclerView(recyclerView)
                        .addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header_1, (ViewGroup) findViewById(android.R.id.content), false))
                        .addFooterView(LayoutInflater.from(this).inflate(R.layout.item_footer_1, (ViewGroup) findViewById(android.R.id.content), false))
                        .onXBind(new XBaseAdapter.OnXBindListener<MainBean>() {
                            @Override
                            public void onXBind(XViewHolder holder, int position, MainBean mainBean) {
                                holder.setTextView(R.id.tv_name, mainBean.getName());
                                holder.setTextView(R.id.tv_age, mainBean.getAge() + "");
                            }
                        })
                        .setLoadingListener(new XBaseAdapter.LoadingListener() {
                            @Override
                            public void onRefresh() {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        xRecyclerViewAdapter.refreshComplete(BaseRefreshHeader.STATE_DONE);
                                    }
                                }, 1500);
                            }

                            @Override
                            public void onLoadMore() {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        xRecyclerViewAdapter.loadMoreComplete(XFooterLayout.STATE_NOMORE);
                                    }
                                }, 1500);
                            }
                        })
        );
    }
}
