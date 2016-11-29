package com.danielzou.somarketplace;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ui.ResultCodes;
//import com.firebase.ui.auth.ui.email.AcquireEmailHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.firebase.ui.auth.ui.AcquireEmailHelper.RC_SIGN_IN;
//import static com.firebase.ui.auth.ui.email.AcquireEmailHelper.RC_SIGN_IN;

public class MainActivity extends BottomNavigationBarActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    FirebaseAuth.AuthStateListener mAuthListener;
    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;


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
        startActivity(new Intent(getApplicationContext(), Main2Activity.class));

//        Button signOut = (Button) findViewById(R.id.sign_out);
//        signOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (v.getId() == R.id.sign_out) {
//                    AuthUI.getInstance()
//                            .signOut(MainActivity.this)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    // user is now signed out
//                                    startActivity(new Intent(MainActivity.this, MainActivity.class));
//                                    finish();
//                                }
//                            });
//                }
//            }
//        });
//        Button addToDatabase = (Button) findViewById(R.id.add_to_database);
//        addToDatabase.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (view.getId() == R.id.add_to_database) {
//                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
//                    CartItem cartItem = new CartItem("database test", "database test");
//                    ref.push().setValue(cartItem);
//                }
//            }
//        });
//        GridView gridview = (GridView) findViewById(R.id.gridview);
//        gridview.setAdapter(new ListAdapter() {
//            @Override
//            public boolean areAllItemsEnabled() {
//                return false;
//            }
//
//            @Override
//            public boolean isEnabled(int i) {
//                return false;
//            }
//
//            @Override
//            public void registerDataSetObserver(DataSetObserver dataSetObserver) {
//
//            }
//
//            @Override
//            public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
//
//            }
//
//            @Override
//            public int getCount() {
//                return 0;
//            }
//
//            @Override
//            public Object getItem(int i) {
//                return null;
//            }
//
//            @Override
//            public long getItemId(int i) {
//                return 0;
//            }
//
//            @Override
//            public boolean hasStableIds() {
//                return false;
//            }
//
//            @Override
//            public View getView(int i, View view, ViewGroup viewGroup) {
//                return null;
//            }
//
//            @Override
//            public int getItemViewType(int i) {
//                return 0;
//            }
//
//            @Override
//            public int getViewTypeCount() {
//                return 0;
//            }
//
//            @Override
//            public boolean isEmpty() {
//                return false;
//            }
//        });
//
//        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//                Toast.makeText(MainActivity.this, "" + position,
//                        Toast.LENGTH_SHORT).show();
//            }
//        });

        //        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    // User is signed in
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//                } else {
//                    // User is signed out
//                    Log.d(TAG, "onAuthStateChanged:signed_out");
//                }
//                // ...
//            }
//        };
        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
//        mDemoCollectionPagerAdapter =
//                new DemoCollectionPagerAdapter(
//                        getSupportFragmentManager());
        //mViewPager = (ViewPager) findViewById(R.id.pager);
        //mViewPager.setAdapter(mDemoCollectionPagerAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // user is signed in!
            startActivity(new Intent(this, MainActivity.class));
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

