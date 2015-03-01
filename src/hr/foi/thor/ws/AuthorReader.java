package hr.foi.thor.ws;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import hr.foi.thor.entities.Author;
import hr.foi.thor.listeners.AuthorReceivedListener;
import hr.foi.thor.logic.WebJsonReader;

public class AuthorReader extends WebJsonReader{
	
	AuthorReceivedListener authorReceivedListener;
	
	public AuthorReader(AuthorReceivedListener authorReceivedListener){
		this.authorReceivedListener = authorReceivedListener;
	}

	@Override
	protected void onPostExecute(String result) {
		ObjectMapper mapper = new ObjectMapper();	
		try {
			Author[] authorArray;
			// multiple
			if(result.startsWith("[")){ // multiple
				authorArray = mapper.readValue(result, Author[].class);
			}
			else {	// single
				Author author;
				author = mapper.readValue(result, Author.class);
				authorArray = new Author[1];
				authorArray[0] = author;
			}
			authorReceivedListener.OnAuthorReceived(authorArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
