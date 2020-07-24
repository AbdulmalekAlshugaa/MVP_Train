package com.example.trainsystem.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainsystem.R;
import com.example.trainsystem.activites.DisplayBookedTrip;
import com.example.trainsystem.activites.Tracking_detials;
import com.example.trainsystem.model.Tripmodel;
import com.example.trainsystem.model.tripInfo;

import java.util.ArrayList;

public class ListTrip extends RecyclerView.Adapter<ListTrip.ViewHolder> {

    private Context mCtxt;
    private ArrayList<Tripmodel> ListData;



    public ListTrip(Context mCtxt, ArrayList<Tripmodel> listData) {
        this.mCtxt = mCtxt;
        ListData = listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tripinfo, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Stauts.setText(ListData.get(position).getStauts());
        holder.DateandTime.setText(ListData.get(position).getDateAndTime());
        holder.From.setText(ListData.get(position).getFromPlace());
        holder.toPlace.setText(ListData.get(position).getToPlace());
        holder.Price.setText(ListData.get(position).getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Onclick Listener
                // get the adapter postion , pass the position with the values to next activity
                int position = holder.getAdapterPosition();
                Intent intent = new Intent(mCtxt, DisplayBookedTrip.class);
                intent.putExtra("Status", ListData.get(position).getStauts());
                intent.putExtra("Date", ListData.get(position).getDateAndTime());
                intent.putExtra("From", ListData.get(position).getFromPlace());
                intent.putExtra("To", ListData.get(position).getToPlace());
                intent.putExtra("Price", ListData.get(position).getPrice());

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                mCtxt.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return ListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView DateandTime,Price;
        private TextView Stauts,From,toPlace;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            DateandTime = itemView.findViewById(R.id.dateTrcking);
            Stauts = itemView.findViewById(R.id.StautsTracking);
            From = itemView.findViewById(R.id.From_Place);
            toPlace = itemView.findViewById(R.id.ToPointsTracking);
            Price = itemView.findViewById(R.id.Price);

        }
    }
}

