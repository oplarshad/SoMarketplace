package com.danielzou.somarketplace;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

/**
 * Adapter for purchased items in social feed.
 */

class SocialFeedAdapter extends FirebaseListAdapter<PurchasedItem> {

    /**
     * Local copy of inventory items.
     */
    private final Map<String, InventoryItem> inventoryItems = new HashMap<>();

    public SocialFeedAdapter(Activity activity, Class<PurchasedItem> modelClass, int modelLayout, Query ref) {
        super(activity, modelClass, modelLayout, ref);

        /**
         * Stores inventory items locally.
         */
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("inventoryItems");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                InventoryItem item = dataSnapshot.getValue(InventoryItem.class);
                inventoryItems.put(item.getItemId(), item);
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
    }

    /**
     * New items are added to the top of the list, instead of the bottom.
     * @param position
     * @return
     */
    @Override
    public PurchasedItem getItem(int position) {
        return super.getItem(getCount() - position - 1);
    }

    @Override
    protected void populateView(View v, PurchasedItem model, int position) {
        InventoryItem inventoryItem = inventoryItems.get(model.getItemId());

        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Reference to an image file in Firebase Storage
        StorageReference storageReference = storage.getReferenceFromUrl("gs://somarketplace-f1028.appspot.com");
        try {
            StorageReference imageRef = storageReference.child(inventoryItem.getImageRef());

            // ImageView in your Activity
            ImageView imageView = (ImageView) v.findViewById(R.id.social_feed_image);

            // Load the image using Glide
            Glide.with(v.getContext())
                    .using(new FirebaseImageLoader())
                    .load(imageRef)
                    .into(imageView);
            TextView cartItemHeader = (TextView) v.findViewById(R.id.social_feed_header);
            TextView cartItemComment = (TextView) v.findViewById(R.id.social_feed_comment);
            cartItemHeader.setText(model.getDisplayName() + " bought " + inventoryItem.getName() + " on " + model.getDateString());
            cartItemComment.setText(model.getComment());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
