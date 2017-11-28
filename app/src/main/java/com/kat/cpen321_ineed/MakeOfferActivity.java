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

        Button makeOffer = (Button) findViewById(R.id.makeOfferButton);
        makeOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText price = (EditText) findViewById(R.id.mOfferPriceField);
                EditText message = (EditText) findViewById(R.id.mOfferMessageField);

                Offer offer = new Offer(Profile.getCurrentProfile().getId(),
                        getIntent().getStringExtra("recID"),
                        Double.parseDouble(price.getText().toString()),
                        message.getText().toString(),
                        getIntent().getStringExtra("postId"),
                        getIntent().getStringExtra("postName"),
                        getIntent().getStringExtra("status"));

                offer.commitDB();

                Intent scrollingIntent = new Intent(MakeOfferActivity.this, ScrollingActivity.class);
                startActivity(scrollingIntent);
            }
        });
    }


}
