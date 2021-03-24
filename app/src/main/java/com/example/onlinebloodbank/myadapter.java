package com.example.onlinebloodbank;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadapter extends FirebaseRecyclerAdapter <model,myadapter.myviewholder>


{

    public myadapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model)
    {

        holder.name.setText(model.getFullname());
        holder.email.setText(model.getEmail());
        holder.phone.setText(model.getPhone());
        holder.blood.setText(model.getBloodgrp());
        holder.div.setText(model.getDivision());
        holder.dis.setText(model.getDistrict());
        holder.uni.setText(model.getUnion());
        holder.cty.setText(model.getCtycorp());


    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)

    {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);

       return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {

        CircleImageView img;
        TextView name,email,phone,blood,div,dis,uni,cty;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);

            img=(CircleImageView)itemView.findViewById(R.id.img1);
            name=(TextView)itemView.findViewById(R.id.nametext);
            email=(TextView)itemView.findViewById(R.id.emailtext);
            phone=(TextView)itemView.findViewById(R.id.phonetext);
            blood=(TextView)itemView.findViewById(R.id.bloodtext);
            div=(TextView)itemView.findViewById(R.id.divisiontext);
            dis=(TextView)itemView.findViewById(R.id.districttext);
            uni=(TextView)itemView.findViewById(R.id.uniontext);
            cty=(TextView)itemView.findViewById(R.id.citycrptext);

        }
    }
}
