package com.kat.cpen321_ineed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.Profile;

public class MakeOfferActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_offer);

        this.addButtonClick();

        final String offerId = getIntent().getStringExtra("offerID");
        if (offerId != null) {


            ((EditText) findViewById(R.id.mOfferPriceField)).setText(Double.toString(getIntent().getDoubleExtra("Price", 0.0)));
            ((EditText) findViewById(R.id.mOfferMessageField)).setText(getIntent().getStringExtra("Message"));

        }
    }

    private void addButtonClick() {
        Button makeOffer = (Button) findViewById(R.id.makeOfferButton);
        makeOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scrollingIntent = new Intent(MakeOfferActivity.this, ScrollingActivity.class);
                EditText price = (EditText) findViewById(R.id.mOfferPriceField);
                EditText message = (EditText) findViewById(R.id.mOfferMessageField);

                if (message.getText().length() == 0 || price.getText().length() == 0) {
                    message.setHint("Must enter a message");
                    price.setHint("Must enter a price");
                } else {

                    Offer newOffer = new Offer(Profile.getCurrentProfile().getId(),
                            getIntent().getStringExtra("ReceiverID"),
                            Double.parseDouble(price.getText().toString()),
                            message.getText().toString(),
                            getIntent().getStringExtra("postID"),
                            getIntent().getStringExtra("postName"));

                    String offerId = getIntent().getStringExtra("offerID");
                    if (offerId == null) {
                        newOffer.commitDB();
                    } else {
                        newOffer.setId(offerId);
                        Offer.editOfferDB(newOffer, offerId);
                    }

                    startActivity(scrollingIntent);
                }
            }
        });
    }
}
