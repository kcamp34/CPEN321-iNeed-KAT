package com.kat.cpen321_ineed;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

public class ViewPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String name = getIntent().getStringExtra("Name");
        String desc = getIntent().getStringExtra("Description");
        long userID = getIntent().getLongExtra("id", 0);
        double price = getIntent().getDoubleExtra("Price", 0.0);

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

    }

}
