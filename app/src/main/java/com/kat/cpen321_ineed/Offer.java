package com.kat.cpen321_ineed;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class Offer {
    private String postName;
    private String senderID;
    private String receiverID;
    private double price;
    private String message;
    private String postID;


    private Offer() {
    }

    public Offer(String senderID, String receiverID, double price, String message, String postID, String postName) {
        this.postName = postName;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.price = price;
        this.message = message;
        this.postID = postID;
    }

    public void setSenderID(String sendID) {
        this.senderID = sendID;
    }

    public String getSenderID() {
        return this.senderID;
    }

    public void setReceiverID(String recID) {
        this.receiverID = recID;
    }

    public String getReceiverID() {
        return this.receiverID;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getPostID() {
        return this.postID;
    }

    public String getPostName() {
        return this.postName;
    }

    public void commitDB() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Add a new document with a generated ID
        db.collection("Offers")
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
