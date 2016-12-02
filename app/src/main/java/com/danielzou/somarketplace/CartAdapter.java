package com.danielzou.somarketplace;

import android.app.Activity;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by daniel on 12/1/16.
 */

public class CartAdapter extends FirebaseListAdapter<CartItem> {

    private List<CartItem> cartItems;
    Map<String, InventoryItem> inventoryItems = new HashMap<>();

    public CartAdapter(Activity activity, Class<CartItem> modelClass, int modelLayout, Query ref, List<CartItem> cartItems) {
        super(activity, modelClass, modelLayout, ref);
        this.cartItems = cartItems;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("inventoryItems");

//        final CountDownLatch writeSignal = new CountDownLatch(1);

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                InventoryItem item = dataSnapshot.getValue(InventoryItem.class);
                inventoryItems.put(item.getItemId(), item);
//                writeSignal.countDown();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        try {
//            writeSignal.await(1, TimeUnit.SECONDS);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void populateView(View v, CartItem model, int position) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final String uid = firebaseAuth.getCurrentUser().getUid();

        inventoryItems.get(model.getItemId());
        InventoryItem inventoryItem = inventoryItems.get(model.getItemId());

        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Reference to an image file in Firebase Storage
        StorageReference storageReference = storage.getReferenceFromUrl("gs://somarketplace-f1028.appspot.com");
        StorageReference imageRef = storageReference.child(inventoryItem.getImageRef());

        // ImageView in your Activity
        ImageView imageView = (ImageView) v.findViewById(R.id.cart_item_image);

        // Load the image using Glide
        Glide.with(v.getContext())
                .using(new FirebaseImageLoader())
                .load(imageRef)
                .into(imageView);

        TextView cartItemHeader = (TextView) v.findViewById(R.id.cart_item_header);
        TextView cartItemComment = (TextView) v.findViewById(R.id.cart_item_comment);
        cartItemHeader.setText(inventoryItem.getName() + " - " + inventoryItem.getPrice());
        cartItemComment.setText(model.getComment());
    }

}
