package com.mylhyl.swiperefreshLayout.sample.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mylhyl.rslayout.OnListLoadListener;
import com.mylhyl.rslayout.SwipeRefreshListView;
import com.mylhyl.swiperefreshLayout.sample.R;

import java.util.ArrayList;
import java.util.List;


public class ListViewXmlFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnListLoadListener {
    private SwipeRefreshListView swipeRefreshListView;
    private ArrayAdapter<String> adapter;
    private List<String> objects = new ArrayList<>();
    private int index;
    private int footerIndex = 20;

    public ListViewXmlFragment() {
    }

    public static ListViewXmlFragment newInstance() {
        ListViewXmlFragment fragment = new ListViewXmlFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_view_xml, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshListView = (SwipeRefreshListView) view.findViewById(R.id.swipeRefresh);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        swipeRefreshListView.setOnListLoadListener(this);
        swipeRefreshListView.setOnRefreshListener(this);

        for (int i = 0; i < footerIndex; i++) {
            objects.add("数据 = " + i);
        }
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, objects);
        swipeRefreshListView.setAdapter(adapter);
        swipeRefreshListView.setEmptyText("数据呢？");
    }


    @Override
    public void onRefresh() {
        swipeRefreshListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                objects.add(0, "下拉 = " + (--index));
                adapter.notifyDataSetChanged();
                swipeRefreshListView.setRefreshing(false);
            }
        }, 1000);
    }

    @Override
    public void onListLoad() {
        swipeRefreshListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                int count = footerIndex + 5;
                for (int i = footerIndex; i < count; i++) {
                    objects.add("上拉 = " + i);
                }
                footerIndex = count;
                adapter.notifyDataSetChanged();
                swipeRefreshListView.setLoading(false);
            }
        }, 2000);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        objects.clear();
        adapter.notifyDataSetChanged();
        return super.onOptionsItemSelected(item);
    }
}
