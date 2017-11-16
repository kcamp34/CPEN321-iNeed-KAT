package com.kat.cpen321_ineed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import static android.content.ContentValues.TAG;

public class ScrollingActivity extends AppCompatActivity {

    private void showAllPosts() {
        final Context that = this;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                final Post post = document.toObject(Post.class);
                                Button tempButton = new Button(that);
                                tempButton.setText(post.getName());
                                tempButton.setOnClickListener(new View.OnClickListener() {
                                                                  @Override
                                                                  public void onClick(View v) {
                                                                      Intent viewPostIntent = new Intent(ScrollingActivity.this, ViewPostActivity.class);
                                                                      viewPostIntent.putExtra("postId", post.getID());
                                                                      viewPostIntent.putExtra("UserId", post.getUserID());
                                                                      viewPostIntent.putExtra("Name", post.getName());
                                                                      viewPostIntent.putExtra("Description", post.getMessage());
                                                                      viewPostIntent.putExtra("Price", post.getPrice());
                                                                      startActivity(viewPostIntent);
                                                                  }
                                                              }
                                );
                                ((LinearLayout) findViewById(R.id.scrollingLinLayout)).addView(tempButton);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        showAllPosts();
        this.addPostClick();
        this.addLogoutClick();
        this.addOfferClick();
        this.addMyPostsClick();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * Opens the newPost view when the button is clicked
     */
    private void addPostClick() {
        Button newPostButton = (Button) findViewById(R.id.addPostButton);
        newPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newPostIntent = new Intent(ScrollingActivity.this, PostActivity.class);
                startActivity(newPostIntent);
            }
        });
    }

    private void addLogoutClick() {
        Button logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(ScrollingActivity.this, LoginActivity.class);
                LoginManager.getInstance().logOut();
                startActivity(loginIntent);
            }
        });
    }

    private void addOfferClick() {
        Button offerButton = (Button) findViewById(R.id.buttonViewOffer);
        offerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent offerIntent = new Intent(ScrollingActivity.this, AllOffersActivity.class);
                startActivity(offerIntent);
            }
        });
    }

    private void addMyPostsClick() {
        Button myPostsButton = (Button) findViewById(R.id.buttonMyPost);
        myPostsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myPostsIntent = new Intent(ScrollingActivity.this, MyPostsActivity.class);
                startActivity(myPostsIntent);
            }
        });
    }

}