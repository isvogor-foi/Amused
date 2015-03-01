package hr.foi.thor.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Author implements Entity{
	@JsonProperty("biography")
	public String biography;
	@JsonProperty("born")
	public String born;
	@JsonProperty("died")
	public String died;
	@JsonProperty("image")
	public String image;
	@JsonProperty("interestingFacts")
	public String interestingFacts;
	@JsonProperty("name")
	public String name;
	@JsonProperty("lastname")
	public String lastname;
	@JsonProperty("id")
	public int id;
	@JsonProperty("pieces")
	public List<Piece> pieces;
	
	public int[] pieceIdArray;
	
	public String getName(){
		return name + " " + lastname;
	}
	
	public Author(String name){
		this.name = name;
	}
	
	public Author() {}
	
	public int[] getPieceIdArray() {
		if(pieceIdArray == null){
			int[] pieceIdArray = new int[pieces.size()];
			for(int i = 0; i < pieces.size(); i++){
				pieceIdArray[i] = pieces.get(i).id;
			}
			return pieceIdArray;
		}
		else 
			return pieceIdArray;
	}
	
}
