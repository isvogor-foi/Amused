package hr.foi.thor.listeners;

import hr.foi.thor.entities.Piece;

public interface PieceReceivedListener {
	public void OnPiecesReceived(Piece[] result);
}
