package com.danielzou.somarketplace;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * The listing adapter populates the grid view of listings on the home fragment.
 */
class ListingAdapter extends FirebaseListAdapter<InventoryItem> {

    public ListingAdapter(Activity activity, Class<InventoryItem> modelClass, int modelLayout, Query ref, List<InventoryItem> listings) {
        super(activity, modelClass, modelLayout, ref);
        List<InventoryItem> listings1 = listings;
    }

    @Override
    protected void populateView(View v, InventoryItem model, int position) {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Reference to an image file in Firebase Storage
        StorageReference storageReference = storage.getReferenceFromUrl("gs://somarketplace-f1028.appspot.com");
        StorageReference imageRef = storageReference.child(model.getImageRef());

        // ImageView in your Activity
        ImageView imageView = (ImageView) v.findViewById(R.id.item_image);

        // Load the image using Glide
        Glide.with(v.getContext())
                .using(new FirebaseImageLoader())
                .load(imageRef)
                .into(imageView);

        TextView itemPrice = (TextView) v.findViewById(R.id.item_price);
        TextView itemName = (TextView) v.findViewById(R.id.item_name);
        itemName.setText(model.getName());
        itemPrice.setText("$" + (model.getPrice()) + ".00");
    }

}
