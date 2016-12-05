package com.danielzou.somarketplace;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ui.ResultCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.firebase.ui.auth.ui.AcquireEmailHelper.RC_SIGN_IN;

/**
 * Contains four fragments: home fragment, feed fragment, cart fragment, and profile fragment.
 */
public class MainActivity extends AppCompatActivity {

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // already signed in
        } else {
            // not signed in
            startActivityForResult(
                    // Get an instance of AuthUI based on the default app
                    AuthUI.getInstance().createSignInIntentBuilder().build(),
                    RC_SIGN_IN);
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        /*
      The {@link android.support.v4.view.PagerAdapter} that will provide
      fragments for each of the sections. We use a
      {@link FragmentPagerAdapter} derivative, which will keep every
      loaded fragment in memory. If this becomes too memory intensive, it
      may be best to switch to a
      {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        /*
      The {@link ViewPager} that will host the section contents.
     */
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    /**
     * This inner class represents the home screen, which contains a grid view of product listings.
     */
    public static class HomeFragment extends Fragment {
        private ListingAdapter mListingAdapter;
        private GridView mGridView;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public HomeFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static HomeFragment newInstance(int sectionNumber) {
            HomeFragment fragment = new HomeFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);

            List<InventoryItem> listings = new ArrayList<>();
            mListingAdapter = new ListingAdapter(this.getActivity(), InventoryItem.class, R.layout.grid_item, FirebaseDatabase.getInstance().getReference().child("inventoryItems"), listings);
            mGridView = (GridView) rootView.findViewById(R.id.grid_view);
            mGridView.setAdapter(mListingAdapter);
            /**
             * Clicking on a listing brings up additional product information.
             */
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    InventoryItem item = mListingAdapter.getItem(position);
                    Intent intent = new Intent(getContext(), DetailsActivity.class)
                            .putExtra("item", item);
                    startActivity(intent);
                }
            });

            return rootView;
        }
    }

    /**
     * This inner class represents the social feed, which contains past purchasing activity from
     * all users.
     */
    public static class FeedFragment extends Fragment {
        private SocialFeedAdapter mSocialFeedAdapter;
        private ListView mSocialFeedListView;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public FeedFragment() {
        }
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static FeedFragment newInstance(int sectionNumber) {
            FeedFragment fragment = new FeedFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_feed, container, false);

            mSocialFeedAdapter = new SocialFeedAdapter(this.getActivity(), PurchasedItem.class, R.layout.social_feed_list_item, FirebaseDatabase.getInstance().getReference().child("allPurchasedItems"));
            mSocialFeedListView = (ListView) rootView.findViewById(R.id.social_feed_list_view);
            mSocialFeedListView.setAdapter(mSocialFeedAdapter);

            /**
             * Local copy of inventory items.
             */
            final Map<String, InventoryItem> inventoryItems = new HashMap<>();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference();
            ref.child("inventoryItems").addChildEventListener(new ChildEventListener() {
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
            /**
             * Clicking a feed item will bring the user to detailed product information.
             */
            mSocialFeedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    PurchasedItem item = mSocialFeedAdapter.getItem(position);

                    InventoryItem inventoryItem = inventoryItems.get(item.getItemId());
                    Intent intent = new Intent(getContext(), DetailsActivity.class)
                            .putExtra("item", inventoryItem);
                    startActivity(intent);
                }
            });

            return rootView;
        }
    }

    /**
     * This fragment contains a list of items that the user has placed into his cart.
     */
    public static class CartFragment extends Fragment {
        private CartAdapter mCartAdapter;
        private ListView mCartListView;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public CartFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static CartFragment newInstance(int sectionNumber) {
            CartFragment fragment = new CartFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_cart, container, false);

            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            final FirebaseUser user = firebaseAuth.getCurrentUser();
            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            final List<CartItem> cartItemList = new ArrayList<>();

            final String uid = user.getUid();
            mCartAdapter = new CartAdapter(this.getActivity(), CartItem.class, R.layout.cart_list_item, FirebaseDatabase.getInstance().getReference().child("cartItems").child(uid));
            mCartListView = (ListView) rootView.findViewById(R.id.cart_list_view);
            mCartListView.setAdapter(mCartAdapter);
            /**
             * Clicking a cart item will remove it from the cart.
             */
            mCartListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    DatabaseReference itemRef = mCartAdapter.getRef(position);
                    itemRef.removeValue();
                }
            });

            /**
             * Stores a local copy of cartItems.
             */
            ref.child("cartItems").child(uid).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    CartItem cartItem = dataSnapshot.getValue(CartItem.class);
                    cartItemList.add(cartItem);
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

            final Button purchaseCart = (Button) rootView.findViewById(R.id.purchase_cart);
            /**
             * Pressing the purchase cart will move items in the database from cartItems to purchasedItems
             * and allPurchasedItems, and start the transactionComplete activity.
             */
            purchaseCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<PurchasedItem> purchasedItems = new ArrayList<>();
                    for (CartItem cartItem: cartItemList) {
                        PurchasedItem purchasedItem = new PurchasedItem(cartItem.getItemId(), cartItem.getComment(), user.getDisplayName());
                        purchasedItems.add(purchasedItem);
                    }
                    for (PurchasedItem purchasedItem: purchasedItems) {
                        ref.child("purchasedItems").child(uid).push().setValue(purchasedItem);
                        ref.child("allPurchasedItems").push().setValue(purchasedItem);
                    }
                    ref.child("cartItems").child(uid).removeValue();
                    Intent intent = new Intent(getContext(), TransactionComplete.class);
                    startActivity(intent);
                }
            });

            return rootView;
        }
    }

    /**
     * This activity contains a list of items that the user has purchased.
     */
    public static class ProfileFragment extends Fragment {
        private PurchasedItemAdapter mPurchasedAdapter;
        private ListView mPurchasedListView;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public ProfileFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ProfileFragment newInstance(int sectionNumber) {
            ProfileFragment fragment = new ProfileFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
            Button signOut = (Button) rootView.findViewById(R.id.sign_out);
            signOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == R.id.sign_out) {
                        AuthUI.getInstance()
                                .signOut(getActivity())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    public void onComplete(@NonNull Task<Void> task) {
                                        // user is now signed out
                                        startActivity(new Intent(getActivity(), MainActivity.class));
                                        getActivity().finish();
                                    }
                                });
                    }
                }
            });
            final Map<String, InventoryItem> inventoryItems = new HashMap<>();
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser user = firebaseAuth.getCurrentUser();
            final String uid = user.getUid();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference();

            ref.child("inventoryItems").addChildEventListener(new ChildEventListener() {
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
            mPurchasedAdapter = new PurchasedItemAdapter(this.getActivity(), PurchasedItem.class, R.layout.purchased_list_item, FirebaseDatabase.getInstance().getReference().child("purchasedItems").child(uid));
            mPurchasedListView = (ListView) rootView.findViewById(R.id.purchased_items_list_view);
            mPurchasedListView.setAdapter(mPurchasedAdapter);
            mPurchasedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    PurchasedItem item = mPurchasedAdapter.getItem(position);

                    InventoryItem inventoryItem = inventoryItems.get(item.getItemId());
                    Intent intent = new Intent(getContext(), DetailsActivity.class)
                            .putExtra("item", inventoryItem);
                    startActivity(intent);
                }
            });
            TextView profileTextView = (TextView) rootView.findViewById(R.id.profile_text_view);
            Resources res = getResources();
            profileTextView.setText(String.format(res.getString(R.string.x_purchased_items), user.getDisplayName()));
            return rootView;
        }
    }

    public static class BlankFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public BlankFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static MainActivity.HomeFragment newInstance(int sectionNumber) {
            MainActivity.HomeFragment fragment = new MainActivity.HomeFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_blank, container, false);
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a FeedFragment (defined as a static inner class below).
//            return FeedFragment.newInstance(position + 1);
            switch (position) {
                case 0:
                    return HomeFragment.newInstance(position + 1);
                case 1:
                    return FeedFragment.newInstance(position + 1);
                case 2:
                    return CartFragment.newInstance(position + 1);
                case 3:
                    return ProfileFragment.newInstance(position + 1);
            }
            return BlankFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Home";
                case 1:
                    return "Feed";
                case 2:
                    return "Cart";
                case 3:
                    return "Profile";
            }
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // user is signed in!
            startActivity(new Intent(this, MainActivity.class));
            user =  firebaseAuth.getCurrentUser();
            finish();
            return;
        }

        // Sign in canceled
        if (resultCode == RESULT_CANCELED) {
            Snackbar.make(findViewById(android.R.id.content), R.string.sign_in_cancelled, Snackbar.LENGTH_LONG)
                    .show();
            return;
        }

        // No network
        if (resultCode == ResultCodes.RESULT_NO_NETWORK) {
            Snackbar.make(findViewById(android.R.id.content), R.string.no_internet_connection, Snackbar.LENGTH_LONG)
                    .show();
            return;
        }

        // User is not signed in. Maybe just wait for the user to press
        // "sign in" again, or show a message.
    }

}
