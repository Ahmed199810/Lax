package com.ama.lax.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.ama.lax.R;
import com.ama.lax.adapters.ExerciseAdapter;
import com.ama.lax.databinding.ActivityActivitiesViewBinding;
import com.ama.lax.models.Exercise;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class ActivitiesView extends AppCompatActivity {

    private ExerciseAdapter adapter;
    private DatabaseReference reference;
    private FirebaseAuth auth;
    private List<Exercise> list = new ArrayList<>();
    private ActivityActivitiesViewBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_activities_view);

        initRecyclerView();
        getExercises();
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recycler.setLayoutManager(layoutManager);
    }


    private void getExercises() {
        binding.progressBar.setVisibility(View.VISIBLE);
        list.clear();
        for (int i = 0; i < 10; i++){
            Exercise exercise = new Exercise(
                    "",
                    "Today Exercises",
                    0
            );
            list.add(exercise);
        }
        adapter = new ExerciseAdapter(this, list);
        binding.recycler.setAdapter(adapter);
        binding.progressBar.setVisibility(View.GONE);
    }
}
