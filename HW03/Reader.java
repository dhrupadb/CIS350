import java.io.*;


public interface Reader {
	
	public String readData(BufferedReader br, File dataFile);
	
	public String getAllAwards(String name, Comparator c);
	
	public String getAllMovies(int year);
	
	public String getBestPicture(int key);
	

}
