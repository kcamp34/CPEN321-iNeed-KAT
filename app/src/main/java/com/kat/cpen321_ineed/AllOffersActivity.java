package com.kat.cpen321_ineed;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.facebook.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashSet;
import java.util.Set;

import static android.content.ContentValues.TAG;

public class AllOffersActivity extends AppCompatActivity {

    Set<Button> sentButtons = new HashSet<>();
    Set<Button> recButtons = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recOfferClick();
        sentOfferClick();
        getAllOffers();
    }

    private void sentOfferClick() {
        final Button sentButton = (Button) findViewById(R.id.buttonSentOffer);
        sentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showButtons(sentButtons);
                hideButtons(recButtons);
            }
        });
    }

    private void recOfferClick() {
        Button recButton = (Button) findViewById(R.id.buttonRecOffer);
        recButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showButtons(recButtons);
                hideButtons(sentButtons);
            }
        });
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
                                Button tempButton = new Button(that);
                                tempButton.setText(offer.getPostName());
                                tempButton.setVisibility(View.GONE);

                                // Block adds the offers relevent to the user to the correct sets in the form of buttons. Other offers are ignored
                                if (offer.getReceiverID() != null && offer.getReceiverID().equals(Profile.getCurrentProfile().getId())) {
                                    recButtons.add(tempButton);
                                    ((LinearLayout) findViewById(R.id.sentOfferLinLayout)).addView(tempButton);
                                } // TODO add else statement here if users are not able to make offers on their own posts
                                if (offer.getSenderID() != null && offer.getSenderID().equals(Profile.getCurrentProfile().getId())) {
                                    sentButtons.add(tempButton);
                                    ((LinearLayout) findViewById(R.id.sentOfferLinLayout)).addView(tempButton);
                                }
                            }
                            showButtons(recButtons);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void showButtons(Set<Button> buttons) {
        for (View view : buttons) {
            view.setVisibility(View.VISIBLE);
        }
    }

    private void hideButtons(Set<Button> buttons) {
        for (View view : buttons) {
            view.setVisibility(View.GONE);
        }
    }
}
