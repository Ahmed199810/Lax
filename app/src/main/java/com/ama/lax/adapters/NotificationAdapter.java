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
import com.ama.lax.models.Notification;
import com.ama.lax.ui.activities.ExercisePreviewActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Notification> list;
    private Context context;

    public NotificationAdapter(Context context, List<Notification> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_people, parent, false);
        NotificationAdapter.ViewHolder viewHolder = new NotificationAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Notification notification = list.get(position);
        ((ViewHolder)holder).txtTitle.setText(notification.getName());
        ((NotificationAdapter.ViewHolder)holder).txtDesc.setText(notification.getDesc());
        ((ViewHolder)holder).img.setImageResource(R.drawable.app_icon11);

        ((NotificationAdapter.ViewHolder)holder).cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ExercisePreviewActivity.class);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTitle;
        private TextView txtDesc;
        private CircleImageView img;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_user);
            txtTitle = itemView.findViewById(R.id.txt_name);
            txtDesc = itemView.findViewById(R.id.txt_desc);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }




}