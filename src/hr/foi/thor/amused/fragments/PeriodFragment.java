package hr.foi.thor.amused.fragments;

import hr.foi.thor.amused.MainActivity;
import hr.foi.thor.amused.R;
import hr.foi.thor.entities.Author;
import hr.foi.thor.entities.Piece;
import hr.foi.thor.listeners.AuthorReceivedListener;
import hr.foi.thor.listeners.PieceReceivedListener;
import hr.foi.thor.logic.AuthorPieceListAdapter;
import hr.foi.thor.logic.WebJsonReader;
import hr.foi.thor.ws.AuthorReader;
import hr.foi.thor.ws.PieceReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

public class PeriodFragment extends Fragment implements PieceReceivedListener, AuthorReceivedListener {

	private List<Piece> mPieceItems;
	private List<Author> mAuthorItems;
	private HashMap<Author, List<Piece>> mPiecesForList;
	private Bundle data;
	
	AuthorPieceListAdapter listAdapter;
	ExpandableListView expListView;
	
	int pieces = 0;
	int authors = 0;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.period_fragment, container, false);
		
	}

	@Override
	public void onStart() {
		super.onStart();
		
		Button more = (Button) getActivity().findViewById(R.id.piecesMore);
		more.setOnClickListener(onMoreButtonClick);
	}
	

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		data = getArguments();
		
		TextView name = ((TextView) getView().findViewById(	R.id.periodDetailsName));
		name.setText(data.getString("name"));
		
		mAuthorItems = new ArrayList<Author>();
		mPieceItems = new ArrayList<Piece>();
		authors = pieces = 0;

		getPeriodPieces(data);
	}

	private void getPeriodPieces(Bundle data) {
		int[] piecesIds = data.getIntArray("pieces");
		for (int i = 0; i < piecesIds.length; i++) {
			WebJsonReader wjr = new PieceReader(this);
			wjr.execute(getResources().getString(R.string.pieces_show_url) + "" + piecesIds[i] + ".json");
		}
	}

	@Override
	public void OnPiecesReceived(Piece[] result) {
		if(isAdded()){
			mPieceItems.add(result[0]);
			WebJsonReader wjr = new AuthorReader(this);
			wjr.execute(getResources().getString(R.string.authors_show_url) + "" + result[0].author.id + ".json");
			pieces++;
		}
	}

	@Override
	public void OnAuthorReceived(Author[] result) {
		if (!mAuthorItems.contains(result[0])) {
			mAuthorItems.add(result[0]);
		}
		authors++;
		// synchronized... all here!
		if (authors == pieces || isAdded()) {
			UpdateMap();

			try{
				expListView = (ExpandableListView) getActivity().findViewById(R.id.periodExList);
				listAdapter = new AuthorPieceListAdapter(getActivity(), mAuthorItems, mPiecesForList);
			
				expListView.setAdapter(listAdapter);
				expListView.setOnChildClickListener(childClick);
			}
			catch(Exception ex){};
		}
	}

	private void UpdateMap() {

		mPiecesForList = new HashMap<Author, List<Piece>>();
		for (Author author : mAuthorItems) {
			List<Piece> authorsPeaces = new ArrayList<Piece>();
			for (Piece piece : mPieceItems) {
				if (piece.author.id == author.id) {
					authorsPeaces.add(piece);
				}
			}
			mPiecesForList.put(author, authorsPeaces);
		}
	}

	private OnChildClickListener childClick = new OnChildClickListener() {
		@Override
		public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
			Author a = mAuthorItems.get(groupPosition);
			Piece p = ((Piece) mPiecesForList.get(mAuthorItems.get(groupPosition)).get(childPosition));
			
			
			PieceDetailsFragment pdf = new PieceDetailsFragment();
			Bundle pieceData = new Bundle();
			
			//piece
			pieceData.putString("name", p.name);
			pieceData.putString("description", p.description);
			pieceData.putString("musicShape", p.musicShape);
			pieceData.putString("musicType", p.musicType);
			pieceData.putString("execution", p.execution);
			pieceData.putString("tempo", p.tempo);
			pieceData.putString("dynamics", p.dynamics);
			pieceData.putString("mood", p.mood);
			pieceData.putString("funFacts", p.funFact);
			
			//author
			pieceData.putInt("authorId", a.id);
			pieceData.putString("authorName", a.name);
			pieceData.putString("authorLastname", a.lastname);
			pieceData.putString("authorBiography", a.biography);
			pieceData.putString("authorInterestingFacts", a.interestingFacts);
			pieceData.putString("authorImage", a.image);
			pieceData.putIntArray("authorPieceIdArray", a.getPieceIdArray());
			
			pdf.setArguments(pieceData);

			FragmentTransaction transaction = ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction();
			transaction.setCustomAnimations(R.anim.enter_right, R.anim.exit_left, R.anim.enter_left, R.anim.exit_right);
			transaction.replace(R.id.fragment_container, pdf);
			transaction.addToBackStack(null);
			transaction.commit();

			return false;
		}

	};

	
	private OnClickListener onMoreButtonClick =  new OnClickListener() {
		@Override
		public void onClick(View v) {
			PeriodDetailsFragment pdf = new PeriodDetailsFragment();
			pdf.setArguments(data);

			FragmentTransaction transaction = ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction();
			transaction.setCustomAnimations(R.anim.enter_right, R.anim.exit_left, R.anim.enter_left, R.anim.exit_right);
			transaction.replace(R.id.fragment_container, pdf);
			transaction.addToBackStack(null);
			transaction.commit();

		}
	};

}
