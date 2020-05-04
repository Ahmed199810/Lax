package com.ama.lax.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.ama.lax.R;
import com.ama.lax.databinding.ActivityLoginBinding;
import com.ama.lax.models.User;
import com.ama.lax.repositories.LoginData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private DatabaseReference database;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        auth = FirebaseAuth.getInstance();

        binding.btnLogin.setOnClickListener(v -> {
            String user = binding.txtUserName.getText().toString();
            String pass = binding.txtPass.getText().toString();

            if (TextUtils.isEmpty(user)){
                binding.txtUserName.setError("Required !");
                return;
            }
            if (TextUtils.isEmpty(pass)){
                binding.txtPass.setError("Required !");
                return;
            }


            loginUser(user, pass);

        });

        binding.txtRegisterPage.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });

    }

    private void loginUser(String user, String pass) {
        showProgressBar();
        auth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                LoginData.saveSkipLogin(LoginActivity.this, true);
                getUserData();
            }else {
                Toast.makeText(LoginActivity.this, "Error Check your Data !", Toast.LENGTH_SHORT).show();
                hideProgressBar();
            }
        });
    }

    private void getUserData() {
        database = FirebaseDatabase.getInstance().getReference().child("users");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.child(auth.getCurrentUser().getUid()).getValue(User.class);
                LoginData.saveData(LoginActivity.this, user);
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
                hideProgressBar();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void showProgressBar() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        binding.progressBar.setVisibility(View.GONE);
    }

}