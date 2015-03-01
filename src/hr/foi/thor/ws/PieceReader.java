package hr.foi.thor.ws;

import hr.foi.thor.entities.Piece;
import hr.foi.thor.listeners.PieceReceivedListener;
import hr.foi.thor.logic.WebJsonReader;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PieceReader extends WebJsonReader{
	
	PieceReceivedListener piecesRecievedListener;
	
	public PieceReader(PieceReceivedListener piecesRecievedListener){
		this.piecesRecievedListener = piecesRecievedListener;
	}

	@Override
	protected void onPostExecute(String result) {
		
		ObjectMapper mapper = new ObjectMapper();	
		try {
			Piece[] pieceArray;
			// multiple
			if(result.startsWith("[")){ // multiple
				pieceArray = mapper.readValue(result, Piece[].class);
			}
			else {	// single
				Piece piece;
				piece = mapper.readValue(result, Piece.class);
				pieceArray = new Piece[1];
				pieceArray[0] = piece;
			}
			piecesRecievedListener.OnPiecesReceived(pieceArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
