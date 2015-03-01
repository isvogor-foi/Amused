package hr.foi.thor.amused.fragments;

import hr.foi.thor.amused.MainActivity;
import hr.foi.thor.amused.R;
import hr.foi.thor.entities.Author;
import hr.foi.thor.entities.Entity;
import hr.foi.thor.entities.Piece;
import hr.foi.thor.listeners.AuthorReceivedListener;
import hr.foi.thor.listeners.PieceReceivedListener;
import hr.foi.thor.logic.EntityAdapter;
import hr.foi.thor.logic.WebJsonReader;
import hr.foi.thor.ws.AuthorReader;
import hr.foi.thor.ws.PieceReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class PieceListFragment extends ListFragment implements PieceReceivedListener, AuthorReceivedListener{
	private List<Piece> mPieceItems;
	ProgressDialog progress;
	int numberOfPiecesLoaded = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.piece_list_fragment, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		if(numberOfPiecesLoaded == 0){
			
			WebJsonReader wjr = new PieceReader(this);
			wjr.execute(getResources().getString(R.string.pieces_url)+"?offset=0&max=30");
		
		
			// show progress bar
			progress = new ProgressDialog(getActivity());
			progress.setTitle(getResources().getString(R.string.loading));
			progress.setMessage(getResources().getString(R.string.pleaseWait));
			progress.show();
		}
	}	
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Piece item = (Piece) mPieceItems.get(position);
		
		Bundle args = new Bundle();
	
		args.putInt("pieceId", item.id);
		args.putInt("authorId", item.author.id);
		args.putString("authorName", item.author.name);
		args.putString("authorLastname", item.author.lastname);
		args.putString("authorImage", item.author.image);
		args.putString("authorBiography", item.author.biography);
		args.putString("authorInterestingFacts", item.author.interestingFacts);
		args.putIntArray("authorPieceIdArray", item.author.getPieceIdArray());
		
		args.putString("name", item.name);
		args.putString("description", item.description);
		args.putString("musicShape", item.musicShape);
		args.putString("musicType", item.musicType);
		args.putString("execution", item.execution);
		args.putString("tempo", item.tempo);
		args.putString("dynamics", item.dynamics);
		args.putString("mood", item.mood);
		args.putString("funFacts", item.funFact);
		
		PieceDetailsFragment pdf = new PieceDetailsFragment();
		pdf.setArguments(args);
		
		FragmentTransaction transaction = ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction();
		transaction.setCustomAnimations(R.anim.enter_right, R.anim.exit_left, R.anim.enter_left, R.anim.exit_right);
		transaction.replace(R.id.fragment_container, pdf);
		transaction.addToBackStack(null);
		transaction.commit();
	}
	

	@Override
	public void OnPiecesReceived(Piece[] result) {
		
		mPieceItems = Arrays.asList(result);
		
		List<Entity> entityPieceList = new ArrayList<Entity>();
		for(Piece p : mPieceItems){
			entityPieceList.add((Entity) p);
			WebJsonReader wjr = new AuthorReader(this);
			wjr.execute(getResources().getString(R.string.authors_show_url) + p.author.id + ".json");
		}
		
		setListAdapter(new EntityAdapter(getActivity(), entityPieceList));
		
	}

	@Override
	public void OnAuthorReceived(Author[] result) {
		// will come only one! - by design one piece = one author!
		for(Piece p : mPieceItems){
			if(p.author.id == result[0].id) p.author = result[0];
		}
		numberOfPiecesLoaded++;		
		if(numberOfPiecesLoaded == mPieceItems.size()){
			progress.dismiss();
		}
		
	}

}


