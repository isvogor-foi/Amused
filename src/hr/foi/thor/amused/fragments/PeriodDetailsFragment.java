package hr.foi.thor.amused.fragments;

import hr.foi.thor.amused.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PeriodDetailsFragment extends Fragment  {


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.period_details_fragment, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		Bundle data = getArguments();

		TextView name = ((TextView) getView().findViewById(R.id.periodDetailsName));
		name.setText(data.getString("name"));

		TextView description = ((TextView) getView().findViewById(R.id.periodDetailsDescription));
		description.setText(Html.fromHtml(data.getString("description")));
	}








}
