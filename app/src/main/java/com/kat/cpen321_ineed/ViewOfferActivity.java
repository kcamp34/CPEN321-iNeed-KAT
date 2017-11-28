package com.kat.cpen321_ineed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.Profile;

public class ViewOfferActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final String name = getIntent().getStringExtra("Name");
        final String message = getIntent().getStringExtra("Message");
        final String senderID = getIntent().getStringExtra("SenderID");
        final String receiverID = getIntent().getStringExtra("ReceiverID");
        final double price = getIntent().getDoubleExtra("Price", 0.0);
        final boolean accepted = getIntent().getBooleanExtra("Accepted", false);
        final boolean rejected = getIntent().getBooleanExtra("Rejected", false);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_offer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView nameView = (TextView) findViewById(R.id.textViewName);
        nameView.setText(name);

        TextView priceView = (TextView) findViewById(R.id.textViewPrice);
        priceView.setText(new StringBuilder().append("$ ").append(String.format("%.2f", price)));

        TextView descView = (TextView) findViewById(R.id.textViewDescription);
        descView.setText(message);

        if (accepted) {
            ((TextView) findViewById(R.id.textView_offer_status)).setText("Offer Accepted");
        } else if (rejected) {
            ((TextView) findViewById(R.id.textView_offer_status)).setText("Offer Rejected");
        }

        Button leftButton = (Button) findViewById(R.id.buttonOfferLeft);
        Button rightButton = (Button) findViewById(R.id.buttonOfferRight);
        // If receiver ID matches the current profile then offer was received. Choose to accept or reject
        // If sender ID matches the current profile then offer was sent. Choose to edit or cancel
        if (senderID != null && senderID.equals(Profile.getCurrentProfile().getId())) {
            senderButtonSetup(leftButton, rightButton);
        } else if (receiverID != null && receiverID.equals(Profile.getCurrentProfile().getId())) {
            offerButtonSetup(leftButton, rightButton);
        }
    }

    private void offerButtonSetup(Button leftButton, Button rightButton) {
        final String postId = getIntent().getStringExtra("postID");
        leftButton.setText("Accept");
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Offer newOffer = new Offer(getIntent().getStringExtra("SenderID"),
                        getIntent().getStringExtra("ReceiverID"),
                        getIntent().getDoubleExtra("Price", 0.0),
                        getIntent().getStringExtra("Message"),
                        getIntent().getStringExtra("postID"),
                        getIntent().getStringExtra("postName"));
                newOffer.setAccepted(true);
                newOffer.setId(getIntent().getStringExtra("offerID"));

                Offer.editOfferDB(newOffer, newOffer.getId());

                Intent acceptIntent = new Intent(ViewOfferActivity.this, ScrollingActivity.class);
                acceptIntent.putExtra("Available", false);
                acceptIntent.putExtra("postID", postId);
                startActivity(acceptIntent);
            }
        });

        rightButton.setText("Reject");
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Offer newOffer = new Offer(getIntent().getStringExtra("SenderID"),
                        getIntent().getStringExtra("ReceiverID"),
                        getIntent().getDoubleExtra("Price", 0.0),
                        getIntent().getStringExtra("Message"),
                        getIntent().getStringExtra("postID"),
                        getIntent().getStringExtra("postName"));
                newOffer.setRejected(true);
                newOffer.setId(getIntent().getStringExtra("offerID"));

                Offer.editOfferDB(newOffer, newOffer.getId());

                Intent rejectIntent = new Intent(ViewOfferActivity.this, ScrollingActivity.class);
                startActivity(rejectIntent);
            }
        });
    }

    private void senderButtonSetup(Button leftButton, Button rightButton) {
        leftButton.setText("Edit");
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(ViewOfferActivity.this, MakeOfferActivity.class);

                editIntent.putExtra("offerID", getIntent().getStringExtra("offerID"));
                editIntent.putExtra("Message", getIntent().getStringExtra("Message"));
                editIntent.putExtra("Price", getIntent().getDoubleExtra("Price", 0));
                editIntent.putExtra("postID", getIntent().getStringExtra("postID"));
                editIntent.putExtra("SenderID", getIntent().getStringExtra("SenderID"));
                editIntent.putExtra("ReceiverID", getIntent().getStringExtra("ReceiverID"));
                editIntent.putExtra("postName", getIntent().getStringExtra("postName"));
                startActivity(editIntent);
            }
        });

        rightButton.setText("Cancel");
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelIntent = new Intent(ViewOfferActivity.this, ScrollingActivity.class);

                Offer newOffer = new Offer(Profile.getCurrentProfile().getId(),
                        getIntent().getStringExtra("ReceiverID"),
                        getIntent().getDoubleExtra("Price", 0.0),
                        getIntent().getStringExtra("Message"),
                        getIntent().getStringExtra("postID"),
                        getIntent().getStringExtra("postName"));
                newOffer.setActive(false);
                newOffer.setId(getIntent().getStringExtra("offerID"));

                Offer.editOfferDB(newOffer, newOffer.getId());

                startActivity(cancelIntent);
            }
        });
    }

}
