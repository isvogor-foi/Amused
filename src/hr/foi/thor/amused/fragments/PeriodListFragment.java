package hr.foi.thor.amused.fragments;
import hr.foi.thor.amused.MainActivity;
import hr.foi.thor.amused.R;
import hr.foi.thor.entities.Entity;
import hr.foi.thor.entities.Period;
import hr.foi.thor.listeners.PeriodsReceivedListener;
import hr.foi.thor.logic.EntityAdapter;
import hr.foi.thor.logic.WebJsonReader;
import hr.foi.thor.ws.PeriodReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class PeriodListFragment extends ListFragment implements PeriodsReceivedListener{
	private List<Period> mPeriodItems;
	ProgressDialog progress;
	int periodsLoaded = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.period_list_fragmet, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		if(periodsLoaded == 0){
			WebJsonReader wjr = new PeriodReader(this);
			wjr.execute(getResources().getString(R.string.periods_url)+"?offset=0&max=30");
			// show progress bar
			progress = new ProgressDialog(getActivity());
			progress.setTitle(getResources().getString(R.string.loading));
			progress.setMessage(getResources().getString(R.string.pleaseWait));
			progress.show();
		}
	}	
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Period item = mPeriodItems.get(position);
		
		Bundle args = new Bundle();
		int[] pieceIds = new int[item.pieces.size()];
		
		for(int i = 0; i < item.pieces.size(); i++){
			pieceIds[i] = item.pieces.get(i).id;
		}
		
		args.putInt("id", item.id);
		args.putIntArray("pieces", pieceIds);
		args.putString("name", item.name);
		args.putString("description", item.description);
		
		PeriodFragment pf = new PeriodFragment();
		pf.setArguments(args);
		
		FragmentTransaction transaction = ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction();
		transaction.setCustomAnimations(R.anim.enter_right, R.anim.exit_left, R.anim.enter_left, R.anim.exit_right);
		transaction.replace(R.id.fragment_container, pf);
		transaction.addToBackStack(null);
		transaction.commit();
	}
	
	@Override
	public void OnPeriodsReceived(Period[] result) {
		mPeriodItems = Arrays.asList(result);
		periodsLoaded++;
		// sort...
		sortPeriod(mPeriodItems);
		List<Entity> itemsToDisplay = new ArrayList<Entity>();
		
		for(Entity e : mPeriodItems){
			itemsToDisplay.add(e);
		}
	
		setListAdapter(new EntityAdapter(getActivity(), itemsToDisplay));
		// dismiss progress
		progress.dismiss();
	}
	
	
	public void sortPeriod(List<Period> periods){
		Collections.sort(periods, new Comparator<Period>(){
			@Override
			public int compare(Period lhs, Period rhs) {				
				return (rhs.lastFrom < lhs.lastFrom) ? 1 : -1;
			}
		});
	}


}
