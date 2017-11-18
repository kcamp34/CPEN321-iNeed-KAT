package com.kat.cpen321_ineed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.Profile;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.addButtonClick();

        final String postId = getIntent().getStringExtra("postId");
        if (postId != null) {

            ((EditText) findViewById(R.id.NameField)).setText(getIntent().getStringExtra("Name"));
            ((EditText) findViewById(R.id.DescriptionField)).setText(getIntent().getStringExtra("Description"));
            ((EditText) findViewById(R.id.PriceField)).setText(Double.toString(getIntent().getDoubleExtra("Price", 0.0)));
        }

    }

    /**
     * When the confirm button is clicked, create or edit a post and go back to the list of postings
     */
    private void addButtonClick() {
        Button newPostButton = (Button) findViewById(R.id.CreatePostButton);
        newPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToScrolling = new Intent(PostActivity.this, ScrollingActivity.class);
                EditText description = (EditText) findViewById(R.id.DescriptionField);
                EditText price = (EditText) findViewById(R.id.PriceField);
                EditText name = (EditText) findViewById(R.id.NameField);

                if (description.getText().length() == 0 || price.getText().length() == 0 || name.getText().length() == 0) {
                    description.setHint("Must enter a description");
                    name.setHint("Must enter a name");
                    price.setHint("Must enter a price");
                } else {
                    Post newPost = new Post(name.getText().toString(),
                            Double.parseDouble(price.getText().toString()),
                            description.getText().toString(),
                            Profile.getCurrentProfile().getId());

                    String postId = getIntent().getStringExtra("postId");
                    if (postId == null) {
                        newPost.commitDB();
                    } else {
                        newPost.setPostID(postId);
                        Post.editPostDB(newPost, postId);
                    }

                    startActivity(intentToScrolling);
                }
            }
        });
    }

}
