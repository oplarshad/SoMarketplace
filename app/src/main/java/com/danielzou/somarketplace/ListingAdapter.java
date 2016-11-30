package com.danielzou.somarketplace;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import java.util.List;

public class ListingAdapter extends FirebaseListAdapter<InventoryItem> {

    private List<InventoryItem> listings;

    public ListingAdapter(Activity activity, Class<InventoryItem> modelClass, int modelLayout, Query ref, List<InventoryItem> listings) {
        super(activity, modelClass, modelLayout, ref);
        this.listings = listings;
    }

    @Override
    protected void populateView(View v, InventoryItem model, int position) {
        ImageView itemImage = (ImageView) v.findViewById(R.id.item_image);
        TextView itemPrice = (TextView) v.findViewById(R.id.item_price);
        TextView itemName = (TextView) v.findViewById(R.id.item_name);
        itemName.setText(model.getName());
        itemPrice.setText(Integer.toString(model.getPrice()));
    }

}
