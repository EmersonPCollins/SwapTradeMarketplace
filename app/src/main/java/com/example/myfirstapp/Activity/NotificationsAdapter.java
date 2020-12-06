package com.example.myfirstapp.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.R;
import com.example.myfirstapp.domain.Good;
import com.example.myfirstapp.domain.RequestNotification;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class NotificationsAdapter extends FirebaseRecyclerAdapter<RequestNotification, RecyclerView.ViewHolder> {

    private static final String TAG = "Adapter";
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public NotificationsAdapter(@NonNull FirebaseRecyclerOptions<RequestNotification> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position, @NonNull RequestNotification model) {
        if(getItemViewType(position) == 0) {
            NotifiedViewHolder notifiedViewHolder = (NotifiedViewHolder) holder;
            notifiedViewHolder.goodName.setText(model.getGoodTitle());
            notifiedViewHolder.goodLocation.setText(model.getLocation());

            View v = notifiedViewHolder.itemView;
            ImageView photo = v.findViewById(R.id.goodImage);
            getImage(position, photo);

            final String id = model.getId();
            final int p = position;

            notifiedViewHolder.acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(TAG, "position: " + p + " id: " + id);
                    deleteRequest(id, p);
                }
            });

            notifiedViewHolder.declineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(TAG, "position: " + p + " id: " + id);
                    deleteRequest(id, p);
                }
            });
        }
        else {
            RequestedViewHolder requestedViewHolder = (RequestedViewHolder) holder;
            requestedViewHolder.goodName.setText(model.getGoodTitle());
            requestedViewHolder.goodLocation.setText(model.getLocation());

            View v = requestedViewHolder.itemView;
            ImageView photo = v.findViewById(R.id.goodImage);
            getImage(position, photo);
        }

        if(!model.getNotifiedEmail().equals(NotificationsActivity.storedEmail()) && !model.getRequestingEmail().equals(NotificationsActivity.storedEmail())) {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }

    }

    private void deleteRequest(String id, final int position) {
        FirebaseDatabase.getInstance().getReference("requests").child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    notifyItemRemoved(position);
                    notifyDataSetChanged();
                }
                else{
                    Log.d("Delete notification", "Could not be deleted");
                }
            }
        });
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;

        if(viewType == 0) {
            view = layoutInflater.inflate(R.layout.accept_decline_row_item, parent, false);
            return new NotifiedViewHolder(view);
        }

            view = layoutInflater.inflate(R.layout.requested_row_item, parent, false);
            return new RequestedViewHolder(view);
    }



    // you need to return an integer that refers to the type of row you want at that position
    @Override
    public int getItemViewType(int position) {
        // accept-decline itemview = 0, requested itemview = 1
        // whenever the storedEmail is equal to notifiedEmail, itemView = 0
        // whenever the storedEmail is equal to requestingEmail, itemView = 1
        RequestNotification model = getItem(position);
        if(NotificationsActivity.storedEmail().equals(model.getNotifiedEmail())) {
            return 0;
        }
        return 1;
    }

    private void getImage(int position, final ImageView imageView) {
        String goodID = getItem(position).getGoodID();
        final ArrayList<String> str= new ArrayList<String>();
        DatabaseReference ref = (DatabaseReference) FirebaseDatabase.getInstance().getReference("goods").child(goodID);
        ref.child("image_url").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.getValue().equals("")) {
                    str.add(dataSnapshot.getValue(String.class));
                    StorageReference sRef = (StorageReference) FirebaseStorage.getInstance().getReference().child("images").child(str.get(0));

                    final long ONE_MEGABYTE = 1024 * 1024;
                    sRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            imageView.setImageBitmap(bmp);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    class NotifiedViewHolder extends RecyclerView.ViewHolder {
        TextView goodName, goodLocation;
        ImageView goodImage;
        Button acceptButton, declineButton;

        public NotifiedViewHolder(@NonNull View itemView) {
            super(itemView);
            goodName = (TextView) itemView.findViewById(R.id.goodName);
            goodLocation = (TextView) itemView.findViewById(R.id.locationText);
            goodImage = (ImageView) itemView.findViewById(R.id.goodImage);
            acceptButton = (Button) itemView.findViewById(R.id.acceptButton);
            declineButton = (Button) itemView.findViewById(R.id.declineButton);
        }
    }

    class RequestedViewHolder extends RecyclerView.ViewHolder {
        TextView goodName, goodLocation;
        ImageView goodImage;
        Button requestedButton;

        public RequestedViewHolder(@NonNull View itemView) {
            super(itemView);
            goodName = (TextView) itemView.findViewById(R.id.goodName);
            goodLocation = (TextView) itemView.findViewById(R.id.locationText);
            goodImage = (ImageView) itemView.findViewById(R.id.goodImage);
            requestedButton = (Button) itemView.findViewById(R.id.requestedButton);
        }
    }
}
