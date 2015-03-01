package hr.foi.thor.amused.fragments;

import hr.foi.thor.amused.MainActivity;
import hr.foi.thor.amused.R;
import hr.foi.thor.entities.Author;
import hr.foi.thor.entities.Entity;
import hr.foi.thor.listeners.AuthorReceivedListener;
import hr.foi.thor.logic.EntityAdapter;
import hr.foi.thor.logic.WebJsonReader;
import hr.foi.thor.ws.AuthorReader;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class AuthorListFragment extends ListFragment implements AuthorReceivedListener{
	private List<Entity> mAuthorItems;
	ProgressDialog progress;
	int authorsLoaded = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.author_list_fragment, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		if(authorsLoaded == 0){
			WebJsonReader wjr = new AuthorReader(this);
			wjr.execute(getResources().getString(R.string.authors_url)+"?offset=0&max=30");
			
			// show progress bar
			progress = new ProgressDialog(getActivity());
			progress.setTitle(getResources().getString(R.string.loading));
			progress.setMessage(getResources().getString(R.string.pleaseWait));
			progress.show();
		}
	}	
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Author item = (Author) mAuthorItems.get(position);
		
		Bundle args = new Bundle();
	
		args.putInt("authorId", item.id);
		args.putString("authorName", item.name);
		args.putString("authorLastname", item.lastname);
		args.putString("authorBiography", item.biography);
		args.putString("authorInterestingFacts", item.interestingFacts);
		args.putString("authorImage", item.image);
		args.putIntArray("authorPieceIdArray", item.getPieceIdArray());
		
		AuthorDetailsFragment adf = new AuthorDetailsFragment();
		adf.setArguments(args);
		
		FragmentTransaction transaction = ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction();
		transaction.setCustomAnimations(R.anim.enter_right, R.anim.exit_left, R.anim.enter_left, R.anim.exit_right);
		transaction.replace(R.id.fragment_container, adf);
		transaction.addToBackStack(null);
		transaction.commit();
	}
	

	@Override
	public void OnAuthorReceived(Author[] result) {
		mAuthorItems = new ArrayList<Entity>();
		for(Entity e : result){
			mAuthorItems.add(e);
		}
		authorsLoaded++;
		//mPeriodItems = Arrays.asList(result);
		setListAdapter(new EntityAdapter(getActivity(), mAuthorItems));
		// dismiss progress
		progress.dismiss();
		
	}

}
