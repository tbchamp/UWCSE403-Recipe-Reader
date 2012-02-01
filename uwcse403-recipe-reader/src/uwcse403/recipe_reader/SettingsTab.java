/**
 * Fragment for settings/preferences tab.
 * Kristin Ivarson (kristini@cs)
 */
package uwcse403.recipe_reader;


import android.os.Bundle;
import android.preference.PreferenceFragment;


public class SettingsTab extends PreferenceFragment {
	
	@Override
	/** Inflate XML on fragment creation */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.preferences);
    }

}

