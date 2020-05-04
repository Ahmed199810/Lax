package com.ama.lax.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ama.lax.R;
import com.ama.lax.ui.activities.ActivitiesView;

public class ExercisesFragment extends Fragment {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private View view;
    private Activity context;

    private LinearLayout btnGym;



    public ExercisesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exercises, container, false);
        context = getActivity();

        initViews(view);

        btnGym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivitiesView.class);
                intent.putExtra("type", "gym");
                startActivity(intent);
            }
        });

        return view;
    }

    private void initViews(View view) {
        btnGym = view.findViewById(R.id.btn_gym);
    }


}