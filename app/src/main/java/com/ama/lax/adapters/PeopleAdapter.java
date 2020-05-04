package com.ama.lax.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ama.lax.R;
import com.ama.lax.models.People;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PeopleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<People> list;
    private Context context;

    public PeopleAdapter(Context context, List<People> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_people, parent, false);
        PeopleAdapter.ViewHolder viewHolder = new PeopleAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        People people = list.get(position);
        ((ViewHolder)holder).txtUserName.setText(people.getName());
        ((ViewHolder)holder).txtDesc.setText(people.getDesc());
        ((ViewHolder)holder).imgUser.setImageResource(R.drawable.app_icon35);
        //Glide.with(context).load(people.getImg_url()).into(((ViewHolder)holder).imgUser);

        ((ViewHolder)holder).cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtUserName;
        private TextView txtDesc;
        private CircleImageView imgUser;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.img_user);
            txtUserName = itemView.findViewById(R.id.txt_name);
            txtDesc = itemView.findViewById(R.id.txt_desc);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }




}