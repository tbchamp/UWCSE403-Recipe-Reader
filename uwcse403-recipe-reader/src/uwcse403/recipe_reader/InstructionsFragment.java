package uwcse403.recipe_reader;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

public class InstructionsFragment extends ListFragment {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        setListAdapter(ArrayAdapter.createFromResource(this.getActivity().getApplicationContext(),
                R.array.instructions, R.layout.instruction_item));
    }
}