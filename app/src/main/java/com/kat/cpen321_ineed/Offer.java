package com.kat.cpen321_ineed;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Offer {
    private String id;
    private String postName;
    private String senderID;
    private String receiverID;
    private double price;
    private String message;
    private String postID;
    private boolean active;
    private boolean accepted = false;
    private boolean rejected = false;


    private Offer() {
    }

    public Offer(String senderID, String receiverID, double price, String message, String postID, String postName) {
        this.id = "";
        this.postName = postName;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.price = price;
        this.message = message;
        this.postID = postID;
        this.active = true;
    }

    public void setId(String s) {
        this.id = s;
    }

    public String getId() {
        return this.id;
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

    public void setPostName(String s) {
        this.postName = s;
    }
    public String getPostName() {
        return this.postName;
    }

    public void setActive(boolean b) {
        this.active = b;
    }

    ;

    public boolean getActive() {
        return this.active;
    }

    ;

    public void setAccepted(boolean b) {
        this.accepted = b;
    }

    ;

    public boolean getAccepted() {
        return this.accepted;
    }

    ;

    public void setRejected(boolean b) {
        this.rejected = b;
    }

    ;

    public boolean getRejected() {
        return this.rejected;
    }

    ;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("id", id);
        map.put("postName", postName);
        map.put("active", active);
        map.put("price", price);
        map.put("message", message);
        map.put("postID", postID);
        map.put("senderID", senderID);
        map.put("receiverID", receiverID);
        map.put("accepted", accepted);
        map.put("rejected", rejected);

        return map;
    }

    public void commitDB() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Add a new document with a generated ID
        final Offer that = this;
        db.collection("Offers")
                .add(this)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        String newId = documentReference.getId();
                        that.setId(newId);
                        documentReference.update(that.toMap());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public static void editOfferDB(Offer offer, String offerId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference postRef = db.collection("Offers").document(offerId);

        postRef.update(offer.toMap());

    }
}
