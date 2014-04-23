import java.io.*;
import java.util.*;


public class CSVReader implements Reader {

	private HashMap<Integer,Actor> movies; // HashMap which maps each Year to it's respective Movie stored as an Actor
	private HashMap<String,Actor> people; // HashMap which maps each Actor/Actresses name to a instance Actor with their awards
	
	public CSVReader(BufferedReader br, File dataFile) // Default - Initializes the HashMaps
	{
		movies = new HashMap<Integer,Actor>();
		people = new HashMap<String,Actor>();
		readData(br, dataFile);
	}
	
	// Default - Initializes the HashMaps
	public CSVReader(HashMap<Integer,Actor> movies, HashMap<String,Actor> people) 
	{
		this.movies = movies;
		this.people = people;
	}
	
	public String readData(BufferedReader br, File dataFile)
	{
		if(!dataFile.canRead())
		{
			 return "Error Reading Data File \""+dataFile.getName()+"\"";
		}
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
					Movie ind = new Movie(nameKey, win, year, category);
					if(people.containsKey(nameKey)) // Creates an instance of Movie for each valid nomination/award
					{								// In case the name already exists, adds it to the respective List
						(people.get(nameKey)).addAward(ind); 
					}
					else
					{
						Actor newActor = new Actor(); //If name doesn't exist. Creates a new Actor
						newActor.addAward(ind);
						people.put(nameKey, newActor);
					}
					
				}
				else
				{
					String category = lineParts[1];
					String name = lineParts[2];
					boolean win = lineParts[3].equalsIgnoreCase("YES") ? true : false;
					Movie newMovie = new Movie(name, win, year, category);
					if(movies.containsKey(year)) // Same thing as Actor awards - Composite structure
					{
						(movies.get(year)).addAward(newMovie);
					}
					else
					{
						Actor newYear = new Actor();
						newYear.addAward(newMovie);
						movies.put(year, newYear);
					}
				}
			}
			br.close();
			return "Data Read";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "Error Reading Data";
		}
	}
	
	public String getBestPicture(int key)
	{
		Actor year = movies.get(key);
		if(year == null)
			return "No Movies found for year "+key;
		
		if(!year.getWinner().equals(""))
			return year.getWinner();
		
		return "No Winner in "+year;
	}
	
	public String getAllMovies(int year) // Returns entire set for that year
	{
		return movies.get(year) == null ? "" : movies.get(year).getAward();		
	}
	
	public String getAllAwards(String name, Comparator c)
	{
		Set<String> stars = people.keySet();
		String result = "";
			for(String star: stars)
			{
				
				if(c.equals(star, name)) // If the search query is contained some or any part of the													// Actors name, the actor is included in the return HashMap.
				{
					String awards = people.get(star).getAward();
					result += awards+"\n\n";
				}
			}
		
		return result;
	}

}
