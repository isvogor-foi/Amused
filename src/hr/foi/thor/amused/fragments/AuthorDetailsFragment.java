package hr.foi.thor.amused.fragments;

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
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class AuthorDetailsFragment extends ListFragment implements AuthorReceivedListener, PieceReceivedListener{
	
	Author mAuthor;
	List<Entity> mPieces;
	String mimeType = "text/html; charset=utf-8";
	String encoding = "UTF-8";
	int piecesLoaded = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
		return inflater.inflate(R.layout.author_details_fragment, container, false);
	}
	
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		mAuthor = new Author();
		mPieces = new ArrayList<Entity>();
		
		if(!getArguments().containsKey("authorName")){
			//request
			WebJsonReader wjr = new AuthorReader(this);
			wjr.execute(getResources().getString(R.string.authors_show_url) + getArguments().getInt("authorId") + ".json");
		}
		else{
			//load data
			loadAuthorBundleData();
			loadAuthorData();
		}
		
	}

	private void loadAuthorBundleData() {
		mAuthor.name = getArguments().getString("authorName");
		mAuthor.lastname = getArguments().getString("authorLastname");
		mAuthor.image = getArguments().getString("authorImage");
		mAuthor.biography =  getArguments().getString("authorBiography");
		mAuthor.interestingFacts = getArguments().getString("authorInterestingFacts");
		mAuthor.pieceIdArray = getArguments().getIntArray("authorPieceIdArray");
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	private void loadAuthorData(){
		
		TextView name = ((TextView) getView().findViewById(R.id.authorName));
		name.setText(mAuthor.getName());
		
		//authorWebImage
		WebView pieceWebDetails = ((WebView) getView().findViewById(R.id.authorWebImage));
		pieceWebDetails.setWebChromeClient(new WebChromeClient());
		pieceWebDetails.setWebViewClient(new WebViewClient());
		pieceWebDetails.getSettings().setJavaScriptEnabled(true);
		pieceWebDetails.setPadding(0, 0, 0, 0);
		pieceWebDetails.setInitialScale(99);
		String image = "<p align=\"center\"><img src=\"" + getActivity().getResources().getString(R.string.rootSite) +  
				"" + mAuthor.image + "\" width=\"180px\" height=\"250px\" /></p>"; //width=\"180px\" height=\"250px\"
		pieceWebDetails.loadData(image, mimeType, encoding);
		
				
		TextView authorBiography = ((TextView) getView().findViewById(R.id.authorBiography));
		authorBiography.setText(Html.fromHtml(mAuthor.biography));
		
		TextView authorInterestingFacts = ((TextView) getView().findViewById(	R.id.authorInterestingFacts));
		authorInterestingFacts.setText(Html.fromHtml(mAuthor.interestingFacts));
		
		for(int pieceId : mAuthor.getPieceIdArray()){
			WebJsonReader wjr = new PieceReader(this);
			wjr.execute(getResources().getString(R.string.pieces_show_url) + pieceId + ".json");
		}
	}


	@Override
	public void OnAuthorReceived(Author[] result) {
		mAuthor = result[0];
		loadAuthorData();
	}


	@Override
	public void OnPiecesReceived(Piece[] result) {
		mPieces.add(result[0]);
		piecesLoaded++;
	
		if(piecesLoaded == mAuthor.getPieceIdArray().length){
			setListAdapter(new EntityAdapter(getActivity(), mPieces));
		}
	}




}
