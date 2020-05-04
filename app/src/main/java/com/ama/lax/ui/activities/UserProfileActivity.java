package com.ama.lax.ui.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.ama.lax.R;
import com.ama.lax.databinding.ActivityUserProfileBinding;
import com.ama.lax.models.User;
import com.ama.lax.repositories.LoginData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {

    private ActivityUserProfileBinding binding;
    private FirebaseAuth auth;
    private User user;
    private StorageReference storageReference;
    private int GALLERY_REQUEST = 100;
    private Uri imgUri = null;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile);

        // firebase
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid());
        storageReference = FirebaseStorage.getInstance().getReference().child("users_imgs");

        List<String> list_diseases = Arrays.asList(getResources().getStringArray(R.array.diseases));
        List<String> list_gender = Arrays.asList(getResources().getStringArray(R.array.gender));

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");

        binding.txtUserName.setText(user.getUser());
        binding.txtEmail.setText(user.getEmail());
        binding.txtPhone.setText(user.getPhone());
        binding.txtIdNum.setText(user.getId_num());
        binding.txtWeight.setText(user.getWeight());
        binding.txtHeight.setText(user.getHeight());
        binding.txtDate.setText(user.getBirth_date());

        binding.spinnerGender.setSelection(list_gender.indexOf(user.getGender()));
        binding.spinnerDiseases.setSelection(list_diseases.indexOf(user.getDisease()));


        Picasso.get().load(user.getImg()).into(binding.imgProfile);

        binding.btnProfileChange.setOnClickListener(v -> {
            changeProfileImg();
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChangesToDB();
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

    }

    private void showDateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
        LayoutInflater inflater = LayoutInflater.from(UserProfileActivity.this);
        View view = inflater.inflate(R.layout.dialog_date_select, null);
        builder.setView(view);
        DatePicker datePicker = view.findViewById(R.id.date_select);

        builder.setPositiveButton("Select", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();
                SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy");
                Date d = new Date(year, month, day);
                String strDate = dateFormatter.format(d);
                binding.txtDate.setText(strDate);
            }
        });

        builder.show();
    }

    private void saveChangesToDB() {

        String name = binding.txtUserName.getText().toString();
        String email = binding.txtEmail.getText().toString();
        String phone = binding.txtPhone.getText().toString();
        String id_num = binding.txtIdNum.getText().toString();
        String birth_date = binding.txtDate.getText().toString();
        String height = binding.txtHeight.getText().toString();
        String weight = binding.txtWeight.getText().toString();
        String disease = binding.spinnerDiseases.getSelectedItem().toString();
        String gender = binding.spinnerGender.getSelectedItem().toString();


        if (TextUtils.isEmpty(name)){
            binding.txtUserName.setError("Required ! ");
            return;
        }
        if (TextUtils.isEmpty(email)){
            binding.txtEmail.setError("Required ! ");
            return;
        }
        if (TextUtils.isEmpty(phone)){
            binding.txtPhone.setError("Required ! ");
            return;
        }
        if (TextUtils.isEmpty(id_num)){
            binding.txtIdNum.setError("Required ! ");
            return;
        }

        binding.progressBar.setVisibility(View.VISIBLE);

        if (imgUri != null){
            StorageReference file_path = storageReference.child(imgUri.getLastPathSegment());
            file_path.putFile(imgUri).continueWithTask(task -> {
                if (!task.isSuccessful()){
                    throw task.getException();
                }
                return file_path.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Uri downUri = task.getResult();
                    user.setImg(downUri.toString());
                    user.setUser(name);
                    user.setEmail(email);
                    user.setPhone(phone);
                    user.setId_num(id_num);
                    user.setBirth_date(birth_date);
                    user.setGender(gender);
                    user.setHeight(height);
                    user.setWeight(weight);
                    user.setDisease(disease);
                    reference.setValue(user).addOnCompleteListener(task2 -> {
                        if (task2.isSuccessful()){
                            Toast.makeText(UserProfileActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                            LoginData.saveData(UserProfileActivity.this, user);
                        }else {
                            Toast.makeText(UserProfileActivity.this, "Update Error", Toast.LENGTH_SHORT).show();
                        }
                        binding.progressBar.setVisibility(View.GONE);
                    });
                }
            });

        }else {
            user.setUser(name);
            user.setEmail(email);
            user.setPhone(phone);
            user.setId_num(id_num);
            user.setBirth_date(birth_date);
            user.setGender(gender);
            user.setHeight(height);
            user.setWeight(weight);
            user.setDisease(disease);
            reference.setValue(user).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Toast.makeText(UserProfileActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    LoginData.saveData(UserProfileActivity.this, user);
                }else {
                    Toast.makeText(UserProfileActivity.this, "Update Error", Toast.LENGTH_SHORT).show();
                }
                binding.progressBar.setVisibility(View.GONE);
            });
        }

    }


    private void changeProfileImg() {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i , GALLERY_REQUEST);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST){
            imgUri = data.getData();
            binding.imgProfile.setImageURI(imgUri);
        }

    }


}