/**
 * Main activity for application; starts on application boot.
 * @author Kristin Ivarson
 */
package recipe_reader.view;


import recipe_reader.model.Settings;
import uwcse403.recipe_reader.R;

import android.support.v4.app.ActionBar;
import android.support.v4.app.ActionBar.Tab;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;


public class RecipeReaderActivity extends FragmentActivity {
	
	public static Settings settings;
	
    
    @Override
    /** @inheritDoc */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // exit if there is no internet connection
        if(!isNetworkAvailable()) {
        	noNetwork(this);
        }
        
        settings = new Settings();
        setUpTabs();
    }
    
    /**
     * @author aosobov
     * Inflates the options menu when it is first opened
     */
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_rr, menu);
	    return true;
	}
	
    /**
     * @author aosobov
     * Called when an item is selected from the options menu
     * @param item the menu item selected
     */
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.help) {
			// inflate the help dialog from xml and display it
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setTitle("Help and Instructions")
    		       .setCancelable(false)
    		       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    		           public void onClick(DialogInterface dialog, int id) {
    		        	   dialog.cancel();
    		           }
    		       });
    		builder.setView(LayoutInflater.from(this).inflate(R.menu.help_dialog, null));
    		AlertDialog alert = builder.create();
    		alert.show();
		}
	    return true;
	}
	
	/**
	 * @author aosobov
	 * @return true if a network connection is available, false otherwise
	 */
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null;
	}
	
	/**
	 * @author aosobov
	 * Pop up a dialog informing the user that this app cannot run without a network connection
	 * and force the user to close the app
	 * @param cur the parent activity
	 */
	private void noNetwork(final Activity cur) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Sorry, you must have a network connection to use Recipe Reader")
		       .setCancelable(false)
		       .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   cur.finish();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
    
    /**
     * @return Settings object associated with this app.
     */
    public Settings getSettings() {
    	return settings;
    }
    
    /**
     * Add tabs to action bar, add listeners for each tab, and show bar.
     */
    private void setUpTabs() {
    	ActionBar bar = getSupportActionBar();
    	bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setDisplayShowTitleEnabled(true);
        bar.setDisplayShowHomeEnabled(true);
        BarTabListener tabListener = new BarTabListener<HomeTab>(this, "Home", HomeTab.class);
        Tab tab = bar.newTab().setIcon(R.drawable.home).setTabListener(tabListener);
        bar.addTab(tab);
        
        tabListener = new BarTabListener<PowerSearchTab>(this, "Settings", PowerSearchTab.class);
        tab = bar.newTab().setIcon(R.drawable.search).setTabListener(tabListener);
        bar.addTab(tab);
        
        tabListener = new BarTabListener<FavoritesTab>(this, "Favorites", FavoritesTab.class);
        tab = bar.newTab().setIcon(R.drawable.star).setTabListener(tabListener);
        bar.addTab(tab);
        
        tabListener = new BarTabListener<SettingsTab>(this, "Settings", SettingsTab.class);
        tab = bar.newTab().setIcon(R.drawable.settings).setTabListener(tabListener);
        bar.addTab(tab);
        bar.show();
    }
    
    /**
     * Listener for switching between fragments when user selects different tab.
     */
    private class BarTabListener<T extends Fragment> implements ActionBar.TabListener {
        private Fragment fragment;
        private final Activity activity;
        private final String tag;
        private final Class<T> tabClass;

        private BarTabListener(Activity activity, String tag, Class<T> tabClass) {
            this.activity = activity;
            this.tag = tag;
            this.tabClass = tabClass;
        }

        /** @inheritDoc */
        public void onTabSelected(Tab tab, FragmentTransaction ft) {
        	// Create fragment if uninitialized
            if (fragment == null) {
                fragment = Fragment.instantiate(activity, tabClass.getName());
            }

            FragmentManager fragMgr = getSupportFragmentManager();
            ft = fragMgr.beginTransaction();
            ft.replace(android.R.id.content, fragment);
            ft.commit();
        }

        /** @inheritDoc */
        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        	// Do nothing: old fragment will be removed when new fragment
        	// is added via FragmentTransaction.replace()
        }

        /** @inheritDoc */
        public void onTabReselected(Tab tab, FragmentTransaction ft) {
            // Do nothing if user selects current tab
        }
    }
}

