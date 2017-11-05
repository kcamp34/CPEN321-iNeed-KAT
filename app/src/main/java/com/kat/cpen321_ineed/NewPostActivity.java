package com.kat.cpen321_ineed;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.addButtonClick();
    }

    /**
     * When the confirm button is clicked, create a post and go back to the list of postings
     */
    private void addButtonClick(){
        Button newPostButton = (Button) findViewById(R.id.CreatePostButton);
        newPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToScrolling = new Intent(NewPostActivity.this, ScrollingActivity.class);
                EditText description = (EditText) findViewById(R.id.DescriptionField) ;
                EditText price = (EditText) findViewById(R.id.PriceField);
                EditText name = (EditText) findViewById(R.id.NameField);

                if (description.getText().length() == 0 || price.getText().length() == 0 || name.getText().length() == 0) {
                    description.setHint("Must enter a description");
                    name.setHint("Must enter a name");
                    price.setHint("Must enter a price");
                } else {
                    // Uncomment if price and description data are needed elsewhere
                    //intentToScrolling.putExtra("Price", Double.parseDouble(price.getText().toString()));
                    //intentToScrolling.putExtra("Description", description.getText().toString());

                    Post newPost = new Post(name.getText().toString(),
                            Double.parseDouble(price.getText().toString()),
                            description.getText().toString());
                    newPost.commitDB();

                    startActivity(intentToScrolling);
                }
            }
        });
    }

}
