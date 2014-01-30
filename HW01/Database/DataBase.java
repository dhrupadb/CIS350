package Database;

import java.io.*;
import java.util.*;


public class DataBase {
	
	private HashMap<Integer,HashSet<Movie>> movies; // HashMap which maps each Year to it's respective HashSet of Nominees/Winners in the movie category
	private HashMap<String,HashSet<Individual>> people; // HashMap which maps each Actor/Actresses name to a HashSet of their Awards, Nominations
	
	public DataBase() // Default - Initializes the HashMaps
	{
		movies = new HashMap<Integer,HashSet<Movie>>();
		people = new HashMap<String,HashSet<Individual>>();
	}
	
	// Default - Initializes the HashMaps
	public DataBase(HashMap<Integer,HashSet<Movie>> movies, HashMap<String,HashSet<Individual>> people) 
	{
		this.movies = movies;
		this.people = people;
	}
	
	public boolean ReadData(BufferedReader br)throws IOException
	{
		try{
			while(br.ready())
			{
				String line = br.readLine();
				String[] lineParts = line.split(","); // Splitting each line of the CSV file into the separate components
				if(lineParts.length != 4 || !(lineParts[0].substring(0,4)).matches("[0-9]+"))
					continue;
				int year = Integer.parseInt(lineParts[0].substring(0,4));
				
				if(year <= 1932) // From 1927 - 1932, the format changes. To account for that offset.
					year++;
				
				//Ignores all irrelevant categories - Reducing the size of the search data increases speed
				if(!(lineParts[1].equals("Actor -- Leading Role") || lineParts[1].equals("Actor -- Supporting Role") 
						|| lineParts[1].equals("Actress -- Leading Role") || lineParts[1].equals("Actress -- Supporting Role")
						|| lineParts[1].equals("Best Picture")))
				{
					continue;
				}
				else if(lineParts[1].equals("Actor -- Leading Role") || lineParts[1].equals("Actor -- Supporting Role") 
						|| lineParts[1].equals("Actress -- Leading Role") || lineParts[1].equals("Actress -- Supporting Role"))
				{
					String nameKey = lineParts[2];
					String category = lineParts[1];
					boolean win = lineParts[3].equalsIgnoreCase("YES") ? true : false;
					Individual ind = new Individual(category, year, win);
					if(people.containsKey(nameKey)) // Creates an instance of Individual for each valid nomination/award
					{								// In case the name already exists, adds it to the respective Set
						(people.get(nameKey)).add(ind); 
					}
					else
					{
						HashSet<Individual> newSet = new HashSet<Individual>(); //If name doesn't exist. Creates a set
						newSet.add(ind);
						people.put(nameKey, newSet);
					}
					
				}
				else
				{
					String name = lineParts[2];
					boolean win = lineParts[3].equalsIgnoreCase("YES") ? true : false;
					Movie newMovie = new Movie(name, win);
					if(movies.containsKey(year)) // Same thing as Individual awards, only this time it uses the Movie class
					{
						(movies.get(year)).add(newMovie);
					}
					else
					{
						HashSet<Movie> newSet = new HashSet<Movie>();
						newSet.add(newMovie);
						movies.put(year, newSet);
					}
				}
			}
			
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public String getBestPicture(int year)
	{
		HashSet<Movie> setOfMovies = movies.get(year);
		if(setOfMovies == null)
			return "No Movies found for "+year;
		
		for(Movie m: setOfMovies) // Iterates over set and returns the best picture
		{
			if(m.didTheyWin())
				return m.getName(); 
		}
		
		return "No Winner in "+year;
	}
	
	public HashSet<Movie> getAllMovies(int year) // Returns entire set for that year
	{
		return movies.get(year) == null ? new HashSet<Movie>() : movies.get(year);		
	}
	
	public HashMap<String,HashSet<Individual>> getAllAwards(String name)
	{
		Set<String> stars = people.keySet();
		
		HashMap<String, HashSet<Individual>> results = new HashMap<String, HashSet<Individual>>();
			for(String star: stars)
			{
				
				if(star.toLowerCase().contains(name.toLowerCase())) // If the search query is contained some or any part of the
																	// Actors name, the actor is included in the return HashMap.
				{
					results.put(star, people.get(star));
				}
			}
		
		return results;
	}
	
}
