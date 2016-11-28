package com.danielzou.somarketplace;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.firebase.ui.auth.ui.AcquireEmailHelper.RC_SIGN_IN;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseTests {
    @Test
    public void addToCart() throws Exception {
        final String user = "user1";
        final String item = "item1";
        final String comment = "This is a comment.";
        final CartItem cartItem = new CartItem(item, comment);

        final CountDownLatch writeSignal = new CountDownLatch(1);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("cartItems");

        ref.child(user).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                assertEquals(cartItem, dataSnapshot.getValue(CartItem.class));
                writeSignal.countDown();
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

        writeSignal.await(10, TimeUnit.SECONDS);


        ref.child(user).push().setValue(cartItem, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    System.out.println("Data could not be saved. " + databaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully.");
                }
            }
        });
    }

    @Test
    public void purchaseCart() throws Exception {
        final String user = "user2";
        final String item = "item1";
        final String comment = "This is great!";
        final PurchasedItem purchasedItem = new PurchasedItem(item, comment);

        final CountDownLatch writeSignal = new CountDownLatch(1);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("purchasedItems");

        ref.child(user).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                assertEquals(purchasedItem, dataSnapshot.getValue(PurchasedItem.class));
                writeSignal.countDown();
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

        writeSignal.await(10, TimeUnit.SECONDS);

        ref.child(user).push().setValue(purchasedItem);
    }

    @Test
    public void addInventoryItem() throws Exception {
        final String itemId = "item1";
        final String name = "ripped jeans";
        final String imageRef = "/images/wala.jpg";
        final int price = 5000;
        final String description = "good jeans";
        final InventoryItem inventoryItem = new InventoryItem(name, imageRef, price, description);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("inventoryItems");

        final CountDownLatch writeSignal = new CountDownLatch(1);

        ref.child(itemId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                assertEquals(inventoryItem, dataSnapshot.getValue(InventoryItem.class));
                writeSignal.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        writeSignal.await(10, TimeUnit.SECONDS);

        ref.child(itemId).setValue(inventoryItem);
    }
}
