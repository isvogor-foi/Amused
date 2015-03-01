package hr.foi.thor.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Piece implements Entity{
	@JsonProperty("name")
	public String name;
	@JsonProperty("originalName")
	public String originalName;
	@JsonProperty("description")
	public String description;
	@JsonProperty("musicShape")
	public String musicShape;
	@JsonProperty("musicType")
	public String musicType;
	@JsonProperty("execution")
	public String execution;
	@JsonProperty("tempo")
	public String tempo;
	@JsonProperty("dynamics")
	public String dynamics;
	@JsonProperty("mood")
	public String mood;
	@JsonProperty("funFacts")
	public String funFact;
	@JsonProperty("id")
	public int id;
	@JsonProperty("author")
	public Author author;
	
	public String getName(){
		return name;
	}
	
	public Piece(String name){
		this.name = name;
	}
	
	public Piece() {} 

}
