package Database;

//Implementation of a Movie

public class Movie
{
	private String name;
	private boolean win;
	
	
	public Movie() // Default constructor
	{
		name = "";
		win = false;
	}
	
	public Movie(String name, boolean win)
	{
		this.name = name;
		this.win = win;
	}

	// As the Database only handles the Best Picture category. This is hard Coded.
	// Easily modifiable for other categories as well.
	public String getCategory() {
		return "Best Picture";
	}

	
	public String getName() {
		return name;
	}

	
	public boolean didTheyWin() {
		return win;
	}
	
}
