package codeartist.com.groseryshop;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import codeartist.com.groseryshop.fragments.AllCouponFragment;
import codeartist.com.groseryshop.fragments.AllProductFragment;
import codeartist.com.groseryshop.fragments.EnterCouponFragment;
import codeartist.com.groseryshop.fragments.EnterProductFragment;
import codeartist.com.groseryshop.fragments.FinalPriceFragment;
import codeartist.com.groseryshop.fragments.ShoppingListFragment;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private RelativeLayout content;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private NavigationView navigationView;
    private FragmentTransaction fragTransaction;
    private int timeDelay = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView =  findViewById(R.id.navigation_view);
        content = findViewById(R.id.content);
        fragTransaction = getSupportFragmentManager().beginTransaction();
        EnterProductFragment base = new EnterProductFragment();
        toolbar.setTitle(getResources().getString(R.string.app_name));
        fragTransaction.add(content.getId(), base, "uniqueTag");
        fragTransaction.commit();
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    //menuItem.setChecked(false);
                    drawerLayout.closeDrawers();
                    return true;
                }
                // else menuItem.setChecked(true);
                menuItem.setChecked(true);
                //Closing drawer on item click
                drawerLayout.closeDrawers();


                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.enter_product:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                fragTransaction = getSupportFragmentManager().beginTransaction();

                                Fragment fragment = fragmentManager.findFragmentByTag("uniqueTag");
                                Log.e("frag", fragment + "");
                                // If fragment doesn't exist yet, create one
                                if (fragment == null) {
                                    fragTransaction.replace(content.getId(), new EnterProductFragment(), "uniqueTag").commit();
                                } else { // re-use the old fragment
                                    fragTransaction.replace(content.getId(), fragment, "uniqueTag").commit();
                                }

                                toolbar.setTitle(getResources().getString(R.string.app_name));
                            }
                        }, timeDelay);
                        //Toast.makeText(getApplicationContext(), "Inbox Selected", Toast.LENGTH_SHORT).show();


                        return true;

                    // For rest of the options we just show a toast on click

                    case R.id.enter_coupon:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                fragTransaction = getSupportFragmentManager().beginTransaction();
                              //  FragmentTransaction fTransaction = fragmentManager.beginTransaction();
                                Fragment fragment = fragmentManager.findFragmentByTag("tag2");
                                Log.e("frag2", fragment + "");
                                // If fragment doesn't exist yet, create one
                                if (fragment == null) {
                                    fragment = new EnterCouponFragment();
                                    fragTransaction.replace(content.getId(), fragment, "tag2").commit();
                                } else { // re-use the old fragment
                                    fragTransaction.replace(content.getId(), fragment, "tag2").commit();
                                }
                                toolbar.setTitle(getResources().getString(R.string.app_name));
                            }
                        }, timeDelay);

                        return true;

                    case R.id.all_product:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                fragTransaction = getSupportFragmentManager().beginTransaction();
                               // FragmentTransaction fTransaction = fragmentManager.beginTransaction();
                                Fragment fragment = fragmentManager.findFragmentByTag("tag3");
                                Log.e("frag3", fragment + "");
                                // If fragment doesn't exist yet, create one                                    fragTransaction.replace(content.getId(), fragment, "tag3").commit();

                                if (fragment == null) {
                                    fragment = new AllProductFragment();
                                    fragTransaction.replace(content.getId(), fragment, "tag3").commit();
                                } else { // re-use the old fragment
                                    fragTransaction.replace(content.getId(), fragment, "tag3").commit();
                                }
                                toolbar.setTitle(getResources().getString(R.string.app_name));
                            }
                        }, timeDelay);

                        return true;

                    case R.id.all_coupon:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                fragTransaction = getSupportFragmentManager().beginTransaction();
                                // FragmentTransaction fTransaction = fragmentManager.beginTransaction();
                                Fragment fragment = fragmentManager.findFragmentByTag("tag4");
                                Log.e("frag3", fragment + "");
                                // If fragment doesn't exist yet, create one                                    fragTransaction.replace(content.getId(), fragment, "tag3").commit();

                                if (fragment == null) {
                                    fragment = new AllCouponFragment();
                                    fragTransaction.replace(content.getId(), fragment, "tag4").commit();
                                } else { // re-use the old fragment
                                    fragTransaction.replace(content.getId(), fragment, "tag4").commit();
                                }
                                toolbar.setTitle(getResources().getString(R.string.app_name));
                            }
                        }, timeDelay);

                        return true;

                    case R.id.shopping_list:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                fragTransaction = getSupportFragmentManager().beginTransaction();
                                // FragmentTransaction fTransaction = fragmentManager.beginTransaction();
                                Fragment fragment = fragmentManager.findFragmentByTag("tag6");
                                Log.e("frag3", fragment + "");
                                // If fragment doesn't exist yet, create one                                    fragTransaction.replace(content.getId(), fragment, "tag3").commit();

                                if (fragment == null) {
                                    fragment = new ShoppingListFragment();
                                    fragTransaction.replace(content.getId(), fragment, "tag6").commit();
                                } else { // re-use the old fragment
                                    fragTransaction.replace(content.getId(), fragment, "tag6").commit();
                                }
                                toolbar.setTitle(getResources().getString(R.string.app_name));
                            }
                        }, timeDelay);

                        return true;

                    case R.id.final_price:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                fragTransaction = getSupportFragmentManager().beginTransaction();
                                // FragmentTransaction fTransaction = fragmentManager.beginTransaction();
                                Fragment fragment = fragmentManager.findFragmentByTag("tag5");
                                Log.e("frag3", fragment + "");
                                // If fragment doesn't exist yet, create one                                    fragTransaction.replace(content.getId(), fragment, "tag3").commit();

                                if (fragment == null) {
                                    fragment = new FinalPriceFragment();
                                    fragTransaction.replace(content.getId(), fragment, "tag5").commit();
                                } else { // re-use the old fragment
                                    fragTransaction.replace(content.getId(), fragment, "tag5").commit();
                                }
                                toolbar.setTitle(getResources().getString(R.string.app_name));
                            }
                        }, timeDelay);

                        return true;

                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.app_name, R.string.action_settings) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

    }
}
