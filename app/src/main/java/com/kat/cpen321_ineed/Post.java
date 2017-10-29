package com.kat.cpen321_ineed;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;

/**
 * Created by Andrew on 2017-10-13.
 *
 * Post contains a price, message, and the id of the user who created it.
 */

public class Post {

    private String userID;
    private String name;
    private double price;
    private String message;
    private boolean available = true;


    public Post(){
        // TODO do something with user id
        this.message = "";
        this.price = 0;
    }

    public Post(String name, double dollars, String description){
        // TODO do something with user id
        this.name = name;
        this.message = description;
        this.price = dollars;
        Log.e("Post Created", this.name + " " + message + " "+ price +" "+ available +" "+ userID);
    }

    public double getPrice(){
        return this.price;
    }
    public void setPrice(double newPrice){
        this.price = newPrice;
    }

    public String getMessage(){
        return this.message;
    }
    public void setMessage(String newMessage){
        this.message = newMessage;
    }

    public String getName(){
        return this.name;
    }

    public String getUserID() {
        return this.userID;
    }

    public void setAvailable(boolean newAvailability){
        this.available = newAvailability;
    }
    public boolean getAvailable(){
        return this.available;
    }


    public void commitDB(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Add a new document with a generated ID
        db.collection("Posts")
                .add(this)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}
