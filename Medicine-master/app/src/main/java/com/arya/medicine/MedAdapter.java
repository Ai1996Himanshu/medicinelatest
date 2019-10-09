package com.arya.medicine;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MedAdapter extends RecyclerView.Adapter<MedAdapter.MyViewHolder> {

    private List<med_detail> med_detailsList;
    Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView medImage;
        TextView medName, medQua;

        public MyViewHolder(View view) {
            super(view);
            medImage = view.findViewById(R.id.med_image);
            medName = view.findViewById(R.id.med_name);
            medQua = view.findViewById(R.id.med_qua);
        }
    }


    public MedAdapter(List<med_detail> med_detailsList, Context context) {
        this.med_detailsList = med_detailsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final med_detail med = med_detailsList.get(position);
        Glide.with(context).load(med.getMedUrl()).into(holder.medImage);
        holder.medName.setText(med.getDetail());
        holder.medQua.setText(med.getQuantity());
    }

    @Override
    public int getItemCount() {
        return med_detailsList.size();
    }
}