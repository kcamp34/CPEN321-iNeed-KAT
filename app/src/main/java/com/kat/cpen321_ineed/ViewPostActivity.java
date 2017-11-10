package com.kat.cpen321_ineed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.Profile;

public class ViewPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String name = getIntent().getStringExtra("Name");
        final String desc = getIntent().getStringExtra("Description");
        final String userID = getIntent().getStringExtra("UserId");
        final double price = getIntent().getDoubleExtra("Price", 0.0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView nameView = (TextView) findViewById(R.id.textViewName);
        nameView.setText(name);

        TextView priceView = (TextView) findViewById(R.id.textViewPrice);
        priceView.setText(new StringBuilder().append("$ ").append(String.format("%.2f", price)));

        TextView descView = (TextView) findViewById(R.id.textViewDescription);
        descView.setText(desc);

        Button offerButton = (Button) findViewById(R.id.buttonOffer);
        // If user id matches userID from intent then set the button to viewOffer
        offerButton.setText("Make an offer!");
        offerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent makeOfferIntent = new Intent(ViewPostActivity.this, MakeOfferActivity.class);
                makeOfferIntent.putExtra("postId", getIntent().getStringExtra("postId"));
                makeOfferIntent.putExtra("postName", name);
                makeOfferIntent.putExtra("recID", userID);
                startActivity(makeOfferIntent);
            }
        });

        if (userID.equals(Profile.getCurrentProfile().getId())) {
            Button editButton = (Button) findViewById(R.id.editPostButton);

            editButton.setVisibility(View.VISIBLE);

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent editPostIntent = new Intent(ViewPostActivity.this, PostActivity.class);
                    editPostIntent.putExtra("postId", getIntent().getStringExtra("postId"));
                    startActivity(editPostIntent);
                }
            });
        }

    }

}
