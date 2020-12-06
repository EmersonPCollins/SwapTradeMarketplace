package com.example.myfirstapp.Activity;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.R;
import com.example.myfirstapp.domain.RequestNotification;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/*
class NotificationsAdapter extends RecyclerView.ViewHolder {

    TextView goodName, goodDescription;
    ImageView goodImage;

    public NotificationsAdapter (@NonNull View itemView) {
        super(itemView);
        goodName = itemView.findViewById(R.id.goodName);
        goodDescription = itemView.findViewById(R.id.descriptionText);
        goodImage = itemView.findViewById(R.id.goodImage);
    }
}
 */

public class NotificationsAdapter extends FirebaseRecyclerAdapter<RequestNotification, NotificationsAdapter.NotificationsViewHolder> {

    private static final String TAG = "Adapter";
    private static String notifiedEmail, requestingEmail;
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
    protected void onBindViewHolder(@NonNull NotificationsViewHolder holder, int position, @NonNull RequestNotification model) {
        holder.goodName.setText(model.getGoodTitle());
        holder.goodLocation.setText(model.getLocation());

        //notifiedEmail = model.getNotifiedEmail();
        //requestingEmail = model.getRequestingEmail();
        if(!model.getNotifiedEmail().equals(NotificationsActivity.storedEmail()) && !model.getRequestingEmail().equals(NotificationsActivity.storedEmail())) {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }


    }

    @NonNull
    @Override
    public NotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;

        setEmails();


        if(viewType == 0) {
            view = layoutInflater.inflate(R.layout.accept_decline_row_item, parent, false);
            return new NotificationsAdapter.NotificationsViewHolder(view);
        }

        view = layoutInflater.inflate(R.layout.accept_decline_row_item, parent, false);
        return new NotificationsAdapter.NotificationsViewHolder(view);

        //return new NotificationsAdapter.NotificationsViewHolder(view);
    }



    // you need to return an integer that refers to the type of row you want at that position
    @Override
    public int getItemViewType(int position) {
        // accept-decline itemview = 0, requested itemview = 1
        // whenever the storedEmail is equal to notifiedEmail, itemView = 0
        // whenever the storedEmail is equal to requestingEmail, itemView = 1
        if(NotificationsActivity.storedEmail().equals(notifiedEmail)) {
            Log.i(TAG, "0 so storedEmail equals notifiedEmail, storedEmail: " + NotificationsActivity.storedEmail() + " notifiedEmail: " + notifiedEmail + " requestingEmail: " + requestingEmail);
            return 0;
        }
        Log.i(TAG, "1 so storedEmail equals requestingEmail, storedEmail: " + NotificationsActivity.storedEmail() + " notifiedEmail: " + notifiedEmail + " requestingEmail: " + requestingEmail);
        return 1;
    }

    private static void setEmails() {
        notifiedEmail = "hey";
    }


    public class NotificationsViewHolder extends RecyclerView.ViewHolder {

        TextView goodName, goodLocation;
        ImageView goodImage;

        public NotificationsViewHolder(@NonNull View itemView) {
            super(itemView);

            goodName = (TextView) itemView.findViewById(R.id.goodName);
            goodLocation = (TextView) itemView.findViewById(R.id.locationText);
            goodImage = (ImageView) itemView.findViewById(R.id.goodImage);
        }

    }
}
