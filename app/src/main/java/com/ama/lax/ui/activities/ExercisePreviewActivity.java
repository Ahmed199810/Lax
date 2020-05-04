package com.ama.lax.ui.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ama.lax.R;
import com.ama.lax.databinding.ActivityExercisePreviewBinding;
import com.ama.lax.models.User;
import com.ama.lax.repositories.LoginData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ExercisePreviewActivity extends AppCompatActivity {

    private ActivityExercisePreviewBinding binding;
    private boolean isStart = false;
    private DatabaseReference reference;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exercise_preview);

        // firebase
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid());

        CountDownTimer timer = new CountDownTimer(10000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                binding.btnDone.setText(millisUntilFinished / 1000 + "");
            }

            @Override
            public void onFinish() {
                binding.btnDone.setText("Done");
                binding.btnDone.setEnabled(true);
                binding.btnDone.setClickable(true);
                calculateScore();
            }
        };

        binding.btnDone.setOnClickListener(v -> {
            if (isStart){
                isStart = false;
            }else {
                timer.start();
                binding.btnDone.setEnabled(true);
                binding.btnDone.setClickable(true);
                isStart = true;
            }
        });



    }

    private void calculateScore() {
        int score = Integer.parseInt(LoginData.getData(ExercisePreviewActivity.this).getScore()) + 5;
        reference.child("score").setValue(score+"").addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                User user = LoginData.getData(ExercisePreviewActivity.this);
                user.setScore(score + "");
                LoginData.saveData(ExercisePreviewActivity.this, user);
                showScoreDialog(score);
            }
        });


    }

    private void showScoreDialog(int score) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ExercisePreviewActivity.this);
        LayoutInflater inflater = LayoutInflater.from(ExercisePreviewActivity.this);
        View view = inflater.inflate(R.layout.dialog_score, null);
        builder.setView(view);
        TextView txtScore = view.findViewById(R.id.txt_score);
        txtScore.setText(score + "");
        builder.show();
    }
}
