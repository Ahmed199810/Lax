package com.ama.lax.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.ama.lax.R;
import com.ama.lax.databinding.ActivityRegisterBinding;
import com.ama.lax.models.User;
import com.ama.lax.repositories.LoginData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private DatabaseReference database;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        auth = FirebaseAuth.getInstance();

        binding.btnRegister.setOnClickListener(v -> {
            String user = binding.txtUserName.getText().toString();
            String pass = binding.txtPass.getText().toString();
            String mail = binding.txtEmail.getText().toString();
            String phone = binding.txtPhone.getText().toString();

            if (TextUtils.isEmpty(mail)) {
                binding.txtEmail.setError("Required !");
                return;
            }
            if (TextUtils.isEmpty(user)) {
                binding.txtUserName.setError("Required !");
                return;
            }
            if (TextUtils.isEmpty(pass)) {
                binding.txtPass.setError("Required !");
                return;
            }
            if (TextUtils.isEmpty(phone)) {
                binding.txtPhone.setError("Required !");
                return;
            }

            RegisterActivity.this.registerUser(user, pass, mail, phone);

        });

        binding.txtLoginPage.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });

    }

    private void registerUser(String user, String pass, String mail, String phone) {
        showProgressBar();
        auth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                auth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()){
                            User u = new User(
                                    auth.getCurrentUser().getUid(),
                                    user,
                                    mail,
                                    phone,
                                    "img",
                                    "id_num",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "0"
                            );
                            pushUserData(u);
                    }else {

                    }
                    hideProgressBar();
                });


            }else {
                Toast.makeText(RegisterActivity.this, "Error Check your Data !", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void pushUserData(User user) {
        database = FirebaseDatabase.getInstance().getReference().child("users");
        database.child(user.getId()).setValue(user).addOnCompleteListener(task -> {
            LoginData.saveData(RegisterActivity.this, user);
            LoginData.saveSkipLogin(RegisterActivity.this, true);
            startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
            finish();
        });
    }

    private void showProgressBar() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        binding.progressBar.setVisibility(View.GONE);
    }

}