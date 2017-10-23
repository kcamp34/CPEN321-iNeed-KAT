package com.kat.cpen321_ineed;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MakeOfferActivity extends AppCompatActivity {

    @Override
    protected void .

    onCreate(Bundle savedInstanceState) {

        EditText mMessage = (EditText) findViewById(R.id.mMessageField);
        EditText mPrice = (EditText) findViewById(R.id.mPriceField);

        Post offerPost = new Post(double.parseDouble(mPrice.getText().toString()), mMessage.getText().toString());
        offerPost.commitDB();
    }


}
