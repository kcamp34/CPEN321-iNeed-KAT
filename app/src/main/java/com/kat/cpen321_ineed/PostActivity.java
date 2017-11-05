package com.kat.cpen321_ineed;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.addButtonClick();

        String postId = getIntent().getStringExtra("postId");
        if (postId != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            DocumentReference postDocRef = db.collection("Posts").document(postId);

            postDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        Post post = task.getResult().toObject(Post.class);

                        ((EditText) findViewById(R.id.NameField)).setText(post.getName());
                        ((EditText) findViewById(R.id.DescriptionField)).setText(post.getMessage());
                        ((EditText) findViewById(R.id.PriceField)).setText(String.valueOf(post.getPrice()));
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }
    }

    /**
     * When the confirm button is clicked, create or edit a post and go back to the list of postings
     */
    private void addButtonClick(){
        Button newPostButton = (Button) findViewById(R.id.CreatePostButton);
        newPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToScrolling = new Intent(PostActivity.this, ScrollingActivity.class);
                EditText description = (EditText) findViewById(R.id.DescriptionField) ;
                EditText price = (EditText) findViewById(R.id.PriceField);
                EditText name = (EditText) findViewById(R.id.NameField);

                // Uncomment if price and description data are needed elsewhere
                //intentToScrolling.putExtra("Price", Double.parseDouble(price.getText().toString()));
                //intentToScrolling.putExtra("Description", description.getText().toString());

                Post newPost = new Post(name.getText().toString(),
                                        Double.parseDouble(price.getText().toString()),
                        description.getText().toString(),
                        Profile.getCurrentProfile().getId());

                String postId = getIntent().getStringExtra("postId");
                if (postId == null)
                    newPost.commitDB();
                else
                    Post.editPostDB(newPost, postId);

                startActivity(intentToScrolling);
            }
        });
    }

}
