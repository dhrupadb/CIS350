package Database;

// Implementation of an Individual [Actor Actress etc.]

public class Individual
{
	private int year;
	private String category;
	private boolean win;
	
	
	public Individual() // Default constructor
	{
		year = 0000;
		category = "";
		win = false;
	}
	
	public Individual(String category, int year, boolean win)
	{
		this.year = year;
		this.category = category;
		this.win = win;
	}
	
	public String getCategory() {
		return category;
	}

	
	public int getYear() {
		return year;
	}

	
	public boolean didTheyWin() {
		return win;
	}

}
