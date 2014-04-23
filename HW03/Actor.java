import java.util.*;


public class Actor implements AwardRecipient {

	private List<Movie> awards = new LinkedList<Movie>();
	
	public void addAward(Movie m)
	{
		awards.add(m);
	}
	
	public String getWinner() {
		String result = "";
		for(Movie m : awards)
		{
			if(m.didTheyWin())
				result = result + m.getAward()+"\n";
		}
		return result;
	}
	
	@Override
	public String getAward() {
		String result = "";
		for(Movie m : awards)
			result = result + m.getAward()+"\n";
		
		return result;
	}

}
