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
import com.ama.lax.adapters.PeopleAdapter;
import com.ama.lax.models.People;

import java.util.ArrayList;
import java.util.List;

public class PeopleFragment  extends Fragment {


    private View view;
    private Activity context;
    private PeopleAdapter adapter;
    private List<People> list = new ArrayList<>();

    private RecyclerView recyclerView;
    private ProgressBar progressBar;



    public PeopleFragment() {
        // Required empty public constructor
    }

    //private FragmentPeopleBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_people, container, false);
        context = getActivity();
        //binding = DataBindingUtil.setContentView(context, R.layout.fragment_people);

        initViews(view);

        getPeople();

        return view;
    }

    private void initViews(View view) {

        recyclerView = view.findViewById(R.id.recycler);
        progressBar = view.findViewById(R.id.progress_bar);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void getPeople() {
        progressBar.setVisibility(View.VISIBLE);
        list.clear();
        for (int i = 0; i < 4; i++){
            People people = new People(
                    "",
                    "Dr..Ahmed Mostafa",
                    "askhgdjhassad ahjsdasjvd asjygjasd aysgdjhagyguasd asugyashgsad sjyfjhasaj",
                    "http://asdhhkasgdjhsgjagsdjhsa.png"
            );
            list.add(people);
        }
        adapter = new PeopleAdapter(context, list);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }


}