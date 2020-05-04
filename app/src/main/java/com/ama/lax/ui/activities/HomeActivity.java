package com.ama.lax.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ama.lax.MainActivity;
import com.ama.lax.R;
import com.ama.lax.adapters.NavPagerAdapter;
import com.ama.lax.databinding.ActivityHomeBinding;
import com.ama.lax.models.User;
import com.ama.lax.repositories.LoginData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private NavPagerAdapter navPagerAdapter;
    private FirebaseAuth auth;
    private boolean is_msg_open = false;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        binding.toolbar.setTitle("Activities");

        Animation anim_open = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fab_open);
        Animation anim_close = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fab_close);

        // firebase
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("messages").child(auth.getCurrentUser().getUid());

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.drawer_open, R.string.drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(menuItem -> {

            switch(menuItem.getItemId()) {
                case R.id.nav_profile:
                    Intent intent = new Intent(HomeActivity.this, UserProfileActivity.class);
                    intent.putExtra("user", LoginData.getData(this));
                    startActivity(intent);
                    break;
                case R.id.nav_about:
                    Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_settings:
                    Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_logout:
                    endUserSession();
                    break;
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });


        binding.btnMessage.setOnClickListener(v -> {
            if (is_msg_open){
                binding.chatContainer.startAnimation(anim_close);
                binding.chatContainer.setVisibility(View.GONE);
                binding.chatContainer.setClickable(false);
                binding.txtMessage.setClickable(false);
                binding.txtMessage.setEnabled(false);
                is_msg_open = false;
            }else {
                binding.chatContainer.startAnimation(anim_open);
                binding.chatContainer.setVisibility(View.VISIBLE);
                binding.chatContainer.setClickable(true);
                binding.txtMessage.setClickable(true);
                binding.txtMessage.setEnabled(true);
                is_msg_open = true;
                getAllMessages();
            }
        });

        binding.btnSend.setOnClickListener(v -> {
            String msg = binding.txtMessage.getText().toString();

            if(!TextUtils.isEmpty(msg)){
                sendMsg(msg);
                binding.txtMessage.setText("");
            }
        });


        navPagerAdapter = new NavPagerAdapter(getSupportFragmentManager());
        binding.viewPager.setAdapter(navPagerAdapter);


        binding.btnExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewPager.setCurrentItem(0);
            }
        });

        binding.btnNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewPager.setCurrentItem(1);
            }
        });

        binding.btnPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewPager.setCurrentItem(2);
            }
        });

        binding.btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewPager.setCurrentItem(3);
            }
        });

        binding.btnGifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewPager.setCurrentItem(4);
            }
        });

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTaps(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void getAllMessages() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                binding.msgSentLayout.removeAllViews();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if (snapshot.child("id").getValue().equals(auth.getCurrentUser().getUid())){
                        addMsgBox(snapshot.child("msg").getValue().toString(), 0);
                    }else {
                        addMsgBox(snapshot.child("msg").getValue().toString(), 1);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendMsg(String msg) {
        Map<String, String> map = new HashMap<>();
        map.put("id", auth.getCurrentUser().getUid());
        map.put("msg", msg);
        reference.push().setValue(map);

        addMsgBox(msg, 0);
    }

    private void addMsgBox(String msg, int type) {
        TextView txt = new TextView(this);
        txt.setText(msg);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        txt.setLayoutParams(lp);
        txt.setPadding(10,10,10,10);
        if (type == 0){
            txt.setBackground(getResources().getDrawable(R.drawable.btn_login_style));
            txt.setTextColor(getResources().getColor(R.color.colorWhite));
            lp.gravity = Gravity.RIGHT;
        }else {
            txt.setBackground(getResources().getDrawable(R.drawable.input_text));
            txt.setTextColor(getResources().getColor(R.color.colorBlack));
            lp.gravity = Gravity.LEFT;
        }
        binding.msgSentLayout.addView(txt);
        binding.scroll.scrollTo(0, binding.scroll.getBottom() + 1);
    }

    private void changeTaps(int position) {

        if(position == 0){
            binding.toolbar.setTitle("Activities");
            binding.btnExercises.setBackgroundResource(R.drawable.app_icon1);
            binding.btnNavigation.setBackgroundResource(R.drawable.app_icon22);
            binding.btnPeople.setBackgroundResource(R.drawable.app_icon2);
            binding.btnNotification.setBackgroundResource(R.drawable.app_icon23);
            binding.btnGifts.setBackgroundResource(R.drawable.app_icon3);
        }
        if(position == 1){
            binding.toolbar.setTitle("Explore");
            binding.btnExercises.setBackgroundResource(R.drawable.app_icon_light);
            binding.btnNavigation.setBackgroundResource(R.drawable.app_icon24);
            binding.btnPeople.setBackgroundResource(R.drawable.app_icon2);
            binding.btnNotification.setBackgroundResource(R.drawable.app_icon23);
            binding.btnGifts.setBackgroundResource(R.drawable.app_icon3);
        }
        if(position == 2){
            binding.toolbar.setTitle("Community");
            binding.btnExercises.setBackgroundResource(R.drawable.app_icon_light);
            binding.btnNavigation.setBackgroundResource(R.drawable.app_icon22);
            binding.btnPeople.setBackgroundResource(R.drawable.app_icon25);
            binding.btnNotification.setBackgroundResource(R.drawable.app_icon23);
            binding.btnGifts.setBackgroundResource(R.drawable.app_icon3);
        }
        if(position == 3){
            binding.toolbar.setTitle("Notifications");
            binding.btnExercises.setBackgroundResource(R.drawable.app_icon_light);
            binding.btnNavigation.setBackgroundResource(R.drawable.app_icon22);
            binding.btnPeople.setBackgroundResource(R.drawable.app_icon2);
            binding.btnNotification.setBackgroundResource(R.drawable.app_icon23_dark);
            binding.btnGifts.setBackgroundResource(R.drawable.app_icon3);
        }
        if(position == 4){
            binding.toolbar.setTitle("Gifts");
            binding.btnExercises.setBackgroundResource(R.drawable.app_icon_light);
            binding.btnNavigation.setBackgroundResource(R.drawable.app_icon22);
            binding.btnPeople.setBackgroundResource(R.drawable.app_icon2);
            binding.btnNotification.setBackgroundResource(R.drawable.app_icon23);
            binding.btnGifts.setBackgroundResource(R.drawable.app_icon3_dark);
        }

    }

    @Override
    public void onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    private void endUserSession() {
        auth.signOut();
        User user = null;
        LoginData.saveData(this, user);
        LoginData.saveSkipLogin(this, false);
        startActivity(new Intent(HomeActivity.this, MainActivity.class));
        finish();
    }

}