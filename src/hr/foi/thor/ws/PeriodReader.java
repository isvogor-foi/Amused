package hr.foi.thor.ws;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import hr.foi.thor.entities.Period;
import hr.foi.thor.listeners.PeriodsReceivedListener;
import hr.foi.thor.logic.WebJsonReader;

public class PeriodReader extends WebJsonReader{
	
	PeriodsReceivedListener periodsRecievedListener;
	
	public PeriodReader(PeriodsReceivedListener periodsRecievedListener){
		this.periodsRecievedListener = periodsRecievedListener;
	}
	
	@Override
	protected void onPostExecute(String result) {
		ObjectMapper mapper = new ObjectMapper();	
		try {
			
			Period[] periodArray = {};
			periodArray = mapper.readValue(result, Period[].class);
			periodsRecievedListener.OnPeriodsReceived(periodArray);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
