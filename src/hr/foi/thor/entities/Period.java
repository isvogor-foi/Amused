package hr.foi.thor.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Period implements Entity{
	
	@JsonProperty("name")
	public String name;
	@JsonProperty("description")
	public String description;
	@JsonProperty("lastFrom")
	public int lastFrom;
	@JsonProperty("lastTo")
	public int lastTo;
	@JsonProperty("id")
	public int id;
	@JsonProperty("pieces")
	public List<Piece> pieces;
	
	public String getName(){
		return name;
	}
	
	public Period(String name){
		this.name = name;
	}
	
	public Period() {} 

}
