
public class Movie implements AwardRecipient {

	private String awardCategory;
	private boolean winner;
	private String name;
	private int year;
	
	@Override
	public String getAward() {
		if(winner)
			return name+" -- won "+awardCategory+" in "+year+getEdition(year);
		else
			return name+" -- nominated for "+awardCategory+" in "+year+getEdition(year);
	}
	
	public Movie() // Default constructor
	{
		name = "";
		winner = false;
	}
	
	public Movie(String name, boolean win, int year, String category)
	{
		this.name = name;
		this.winner = win;
		this.year = year;
		this.awardCategory = category;
	}

	public String getCategory() {
		return awardCategory;
	}

	
	public String getName() {
		return name;
	}

	
	public boolean didTheyWin() {
		return winner;
	}
	
	private static String getEdition(int year) // Get's the Edition of the Oscars in which the awards were won.
	{
		int edition = year - 1927;
		String stringEdition = edition + "";
		if(stringEdition.charAt(stringEdition.length()-1) == '1')
			return " ("+edition+"st)";
		else if(stringEdition.charAt(stringEdition.length()-1) == '2')
			return " ("+edition+"nd)";
		else if(stringEdition.charAt(stringEdition.length()-1) == '3')
			return " ("+edition+"rd)";
		else
			return " ("+edition+"th)";	
	}

}
