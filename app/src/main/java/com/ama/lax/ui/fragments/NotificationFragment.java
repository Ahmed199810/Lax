package com.ama.lax.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ama.lax.R;
import com.ama.lax.adapters.NotificationAdapter;
import com.ama.lax.models.Notification;

import java.util.ArrayList;
import java.util.List;


public class NotificationFragment  extends Fragment {


    private View view;
    private Activity context;
    private NotificationAdapter adapter;
    private List<Notification> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProgressBar progressBar;



    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notifications, container, false);
        context = getActivity();
        initViews(view);

        getNotifications();
        return view;
    }

    private void initViews(View view) {

        recyclerView = view.findViewById(R.id.recycler);
        progressBar = view.findViewById(R.id.progress_bar);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void getNotifications() {
        progressBar.setVisibility(View.VISIBLE);
        list.clear();
        for (int i = 0; i < 3; i++){
            Notification notification = new Notification(
                    "",
                    "Today Exercises",
                    "askhgdjhassad ahjsdasjvd asjygjasd aysgdjhagyguasd asugyashgsad sjyfjhasaj",
                    0
            );
            list.add(notification);
        }
        adapter = new NotificationAdapter(context, list);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }


}