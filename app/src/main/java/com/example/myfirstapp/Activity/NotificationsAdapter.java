package com.example.myfirstapp.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.R;


import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter {

    private static final String TAG = "NotificationsAdapter";
    List<String> notificationsList;

    public NotificationsAdapter(List<String> notificationsList) {
        this.notificationsList = notificationsList;
    }

    // you need to return an integer that refers to the type of row you want at that position
    @Override
    public int getItemViewType(int position) {
        // whenever the word iron appears, show the itemview with the image
        // so image itemview = 0, non-image itemview = 1
        // need to change this so that whenever something occurs, it calls the write type of view
        if(notificationsList.get(position).toLowerCase().contains("iron")) {
            return 0;
        }
        return 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;

        if(viewType == 0) {
            view = layoutInflater.inflate(R.layout.accept_decline_row_item, parent, false);
            return new ViewHolderOne(view);
        }

        view = layoutInflater.inflate(R.layout.requested_row_item, parent, false);
        return new ViewHolderTwo(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(notificationsList.get(position).toLowerCase().contains("iron")) {
           //bind viewholder one
            ViewHolderOne viewHolderOne = (ViewHolderOne) holder;
            viewHolderOne.goodName.setText(notificationsList.get(position));
            viewHolderOne.descriptionText.setText(String.valueOf(position));
        }
        else {
            //bind viewholder two
            ViewHolderTwo viewHolderTwo = (ViewHolderTwo) holder;
            viewHolderTwo.goodName.setText(notificationsList.get(position));
            viewHolderTwo.descriptionText.setText(String.valueOf(position));
        }
    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    // refers to accept/decline row item
    class ViewHolderOne extends RecyclerView.ViewHolder {

        TextView goodName, descriptionText;
        ImageView goodImage;
        Button acceptButton, declineButton;
        public ViewHolderOne(@NonNull View itemView) {
            super(itemView);

            goodName = itemView.findViewById(R.id.goodName);
            descriptionText = itemView.findViewById(R.id.descriptionText);
            goodImage = itemView.findViewById(R.id.goodImage);
            acceptButton = itemView.findViewById(R.id.acceptButton);
            declineButton = itemView.findViewById(R.id.declineButton);
        }
    }

    // refers to "requested" row item
    class ViewHolderTwo extends RecyclerView.ViewHolder {

        TextView goodName, descriptionText;
        ImageView goodImage;
        Button requestedButton;
        public ViewHolderTwo(@NonNull View itemView) {
            super(itemView);

            goodName = itemView.findViewById(R.id.goodName);
            descriptionText = itemView.findViewById(R.id.descriptionText);
            goodImage = itemView.findViewById(R.id.goodImage);
            requestedButton = itemView.findViewById(R.id.requestedButton);
        }
    }
}
