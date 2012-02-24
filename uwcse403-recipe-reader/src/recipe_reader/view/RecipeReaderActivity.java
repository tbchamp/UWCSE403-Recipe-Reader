/**
 * Main activity for application; starts on application boot.
 * @author Kristin Ivarson
 */
package recipe_reader.view;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import recipe_reader.model.Category;
import recipe_reader.model.Directions;
import recipe_reader.model.Ingredient;
import recipe_reader.model.Recipe;
import recipe_reader.model.Settings;
import uwcse403.recipe_reader.R;

import android.support.v4.app.ActionBar;
import android.support.v4.app.ActionBar.Tab;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;


public class RecipeReaderActivity extends FragmentActivity {
	
	public static Settings settings;
	
    
    @Override
    /** @inheritDoc */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = new Settings();
        try {
			settings.signIn("Jeremy Lin", "Linsanity");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        setUpTabs();
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

