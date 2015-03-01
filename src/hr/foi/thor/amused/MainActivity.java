package hr.foi.thor.amused;

import hr.foi.thor.amused.fragments.MainFragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		// if offline warn!
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		
		if (ni == null) {
		    this.finish();
		    Toast.makeText(this, "Ne mogu se povezati na Internet!", Toast.LENGTH_LONG).show();
		}
		else{
			FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
			transaction.add(R.id.fragment_container, new MainFragment());
			transaction.commit();	
		}

	}
}


