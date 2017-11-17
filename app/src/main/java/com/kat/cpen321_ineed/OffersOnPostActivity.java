package com.kat.cpen321_ineed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import static android.content.ContentValues.TAG;

public class OffersOnPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_on_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getAllOffers();
    }

    private void getAllOffers() {
        final Context that = this;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Offers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                final Offer offer = document.toObject(Offer.class);
                                final Button tempButton = new Button(that);

                                tempButton.setText(offer.getMessage());
                                if (offer.getPostID().equals(getIntent().getStringExtra("postId"))) {
                                    tempButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent viewOfferIntent = new Intent(OffersOnPostActivity.this, ViewOfferActivity.class);
                                            viewOfferIntent.putExtra("Name", offer.getPostName());
                                            viewOfferIntent.putExtra("Message", offer.getMessage());
                                            viewOfferIntent.putExtra("SenderID", offer.getSenderID());
                                            viewOfferIntent.putExtra("ReceiverID", offer.getReceiverID());
                                            viewOfferIntent.putExtra("Price", offer.getPrice());
                                            startActivity(viewOfferIntent);
                                        }
                                    });
                                    ((LinearLayout) findViewById(R.id.offers_on_post_lin_layout)).addView(tempButton);
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}
