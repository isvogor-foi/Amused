package hr.foi.thor.amused.fragments;

import hr.foi.thor.amused.MainActivity;
import hr.foi.thor.amused.R;

import java.lang.reflect.InvocationTargetException;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("SetJavaScriptEnabled")
public class PieceDetailsFragment extends Fragment {

	Bundle data;
	String mimeType = "text/html; charset=utf-8";
	String encoding = "UTF-8";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.piece_details_fragment, container,
				false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		data = getArguments();
		
		Button btnAboutAuthor = ((Button)getView().findViewById(R.id.pieceAboutAuthorButton));
		btnAboutAuthor.setOnClickListener(authorDetailsButtonClick);

		TextView name = ((TextView) getView().findViewById(R.id.pieceDetailsName));
		name.setText(Html.fromHtml(data.getString("name")));
		
		TextView pieceAuthorName = ((TextView) getView().findViewById(R.id.pieceAuthorName));
		if(data.getString("authorName") != null)
			pieceAuthorName.setText(Html.fromHtml(data.getString("authorName") + " " + data.getString("authorLastname")));
		else 
			pieceAuthorName.setText("");

		WebView pieceWebDetails = ((WebView) getView().findViewById(R.id.pieceWebDetails));
		pieceWebDetails.setWebChromeClient(new WebChromeClient());
		pieceWebDetails.setWebViewClient(new WebViewClient());
		pieceWebDetails.getSettings().setJavaScriptEnabled(true);
		pieceWebDetails.loadData(data.getString("description"), mimeType, encoding);
		
		TextView pieceMusicShape = ((TextView) getView().findViewById(R.id.pieceMusicShape));
		pieceMusicShape.setText(Html.fromHtml(data.getString("musicShape")));
		TextView pieceMusicType = ((TextView) getView().findViewById(R.id.pieceMusicType));
		pieceMusicType.setText(Html.fromHtml(data.getString("musicType")));
		TextView pieceExecution = ((TextView) getView().findViewById(R.id.pieceExecution));
		pieceExecution.setText(Html.fromHtml(data.getString("execution")));
		TextView pieceTempo = ((TextView) getView().findViewById(R.id.pieceTempo));
		pieceTempo.setText(Html.fromHtml(data.getString("tempo")));
		TextView pieceDynamics = ((TextView) getView().findViewById(R.id.pieceDynamics));
		pieceDynamics.setText(Html.fromHtml(data.getString("dynamics")));
		TextView pieceMood = ((TextView) getView().findViewById(R.id.pieceMood));
		pieceMood.setText(Html.fromHtml(data.getString("mood")));
		
		WebView pieceWebFunFacts = ((WebView) getView().findViewById(R.id.pieceWebFunFacts));
		pieceWebFunFacts.setWebChromeClient(new WebChromeClient());
		pieceWebFunFacts.setWebViewClient(new WebViewClient());
		pieceWebFunFacts.getSettings().setJavaScriptEnabled(true);
		
		// for image links...
		String funFactsRaw = data.getString("funFacts");
		funFactsRaw = funFactsRaw.replace("src=\"/amused", "src=\"" + getActivity().getResources().getString(R.string.rootSite)+"/amused");
		
		pieceWebFunFacts.loadData(funFactsRaw, mimeType, encoding);
		
	}
	
	@Override
	public void onPause() {
		super.onPause();
		WebView pieceWebDetails = ((WebView) getView().findViewById(R.id.pieceWebDetails));
		WebView pieceWebFunFacts = ((WebView) getView().findViewById(R.id.pieceWebFunFacts));
		try {
			Class.forName("android.webkit.WebView").getMethod("onPause", (Class[]) null).invoke(pieceWebDetails, (Object[]) null);
			Class.forName("android.webkit.WebView").getMethod("onPause", (Class[]) null).invoke(pieceWebFunFacts, (Object[]) null);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	OnClickListener authorDetailsButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			AuthorDetailsFragment adf = new AuthorDetailsFragment();
			adf.setArguments(data);

			FragmentTransaction transaction = ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction();
			transaction.setCustomAnimations(R.anim.enter_right, R.anim.exit_left, R.anim.enter_left, R.anim.exit_right);
			transaction.replace(R.id.fragment_container, adf);
			transaction.addToBackStack(null);
			transaction.commit();
		}
	};
}
