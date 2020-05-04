package com.ama.lax.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ama.lax.R;
import com.ama.lax.ui.activities.ExercisePreviewActivity;
import com.ama.lax.models.Exercise;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ExerciseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Exercise> list;
    private Context context;

    public ExerciseAdapter(Context context, List<Exercise> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise, parent, false);
        ExerciseAdapter.ViewHolder viewHolder = new ExerciseAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Exercise exercise = list.get(position);
        ((ViewHolder)holder).txtName.setText(exercise.getName());
        ((ViewHolder)holder).imgExercise.setImageResource(R.drawable.app_icon10);

        ((ExerciseAdapter.ViewHolder)holder).cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ExercisePreviewActivity.class);
            intent.putExtra("name", exercise);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName;
        private CircleImageView imgExercise;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgExercise = itemView.findViewById(R.id.img_exercise);
            txtName = itemView.findViewById(R.id.txt_name);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }




}
