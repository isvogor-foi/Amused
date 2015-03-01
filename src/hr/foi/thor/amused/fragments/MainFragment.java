package hr.foi.thor.amused.fragments;

import hr.foi.thor.amused.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainFragment extends Fragment {
	
	public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main_fragment, container, false);
	}
	
	
	public void onViewCreated(View view, android.os.Bundle savedInstanceState) {
		
		Button btnAuthors = (Button) getActivity().findViewById(R.id.btnAuthor);
		Button btnPieces = (Button) getActivity().findViewById(R.id.btnPiece);
		Button btnPeriods = (Button) getActivity().findViewById(R.id.btnPeriod);
		Button btnAbout = (Button) getActivity().findViewById(R.id.btnAbout);

		btnAuthors.setOnClickListener(onButtonClicked);
		btnPieces.setOnClickListener(onButtonClicked);
		btnPeriods.setOnClickListener(onButtonClicked);
		btnAbout.setOnClickListener(onButtonClicked);
		
	};
	
	
	OnClickListener onButtonClicked = new OnClickListener() {
		@Override
		public void onClick(View v) {
			FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
			transaction.setCustomAnimations(R.anim.enter_right, R.anim.exit_left, R.anim.enter_left, R.anim.exit_right);

			switch(v.getId()){
			case R.id.btnAuthor:
				transaction.replace(R.id.fragment_container, new AuthorListFragment());
				break;
			case R.id.btnPeriod:
				transaction.replace(R.id.fragment_container, new PeriodListFragment());
				break;
			case R.id.btnPiece:
				transaction.replace(R.id.fragment_container, new PieceListFragment());
				break;
			case R.id.btnAbout:
				transaction.replace(R.id.fragment_container, new AboutFragment());
				break;
			}
			transaction.addToBackStack(null);
			transaction.commit();
		}
	};
	
}
