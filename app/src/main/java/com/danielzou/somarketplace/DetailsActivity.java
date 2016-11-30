package com.danielzou.somarketplace;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class DetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        TextView itemDescription = (TextView) findViewById(R.id.item_details_description);
        Bundle extras = getIntent().getExtras();
        InventoryItem inventoryItem = extras.getParcelable("item");
        itemDescription.setText(inventoryItem.getDescription());
    }

}
