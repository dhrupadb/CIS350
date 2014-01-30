package Database;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.*;

public class DatabaseTest {

	@Test
	public void MovieTest() {
		Movie mov1 = new Movie();
		Movie mov2 = new Movie("Lord of the Flies", true);
		
		assertFalse(mov1.didTheyWin());
		assertTrue(mov2.didTheyWin());
		assertEquals(mov2.getCategory(),"Best Picture");
		assertEquals(mov2.getName(),"Lord of the Flies");
	}
	
	@Test
	public void IndividualTest() {
		Individual ind1 = new Individual();
		Individual ind2 = new Individual("Best Actor",1993,true);
		
		assertFalse(ind1.didTheyWin());
		assertTrue(ind2.didTheyWin());
		assertEquals(ind2.getCategory(),"Best Actor");
		assertEquals(ind2.getYear(),1993);
	}
	
	@Test
	public void GetMoviesTest() {
		Movie mov1 = new Movie();
		Movie mov2 = new Movie("Lord of the Flies", true);
		Movie mov3 = new Movie("Lord of the Rings", false);
		Movie mov4 = new Movie("Star Wars 2", false);
		
		HashMap<Integer,HashSet<Movie>> movies = new  HashMap<Integer,HashSet<Movie>>();
		HashMap<String,HashSet<Individual>> people = new  HashMap<String,HashSet<Individual>>();
		HashSet<Movie> set_1995 = new HashSet<Movie>();
		set_1995.add(mov1);
		HashSet<Movie> set_2000 = new HashSet<Movie>();
		set_2000.add(mov2);
		set_2000.add(mov3);
		set_2000.add(mov4);
		
		movies.put(1995, set_1995);
		movies.put(2000, set_2000);
		
		HashSet<Movie> test_95 = new HashSet<Movie>();
		test_95.add(mov1);
			
		DataBase db = new DataBase(movies,people);
		
		assertEquals(db.getBestPicture(1993),"No Movies found for 1993");
		assertTrue(db.getAllMovies(1993).isEmpty());
		assertEquals(db.getBestPicture(1995), "No Winner in 1995");
		assertEquals(db.getAllMovies(1995), test_95);
		assertEquals(db.getBestPicture(2000), "Lord of the Flies");
	}
	
	@Test
	public void GetIndividualTest() {
		String actor1 = "Jack Nicholson";
		String actor2 = "Hugh Jackman";
		String actor3 = "R215";
		String actor4 = "Justin Beiber";
		
		HashMap<Integer,HashSet<Movie>> movies = new  HashMap<Integer,HashSet<Movie>>();
		HashMap<String,HashSet<Individual>> people = new  HashMap<String,HashSet<Individual>>();
		
		HashSet<Individual> empty = new HashSet<Individual>();
		people.put(actor4, empty);
		
		HashSet<Individual> set2 = new HashSet<Individual>();
		
		set2.add(new Individual("Best Actor", 2004, true));
		set2.add(new Individual("Supporting Actor", 1998, false));
		HashSet<Individual> jack = new HashSet<Individual>();
		jack.add(new Individual("Best Actor", 1995, true));
		
		people.put(actor2, set2);
		people.put(actor1, jack);
		
		HashSet<Individual> set3 = new HashSet<Individual>();
		set3.add(new Individual("Supporting Actor", 2006, false));
		set3.add(new Individual("Supporting Actor", 2008, false));
		
		people.put(actor3, set3);	
		DataBase db = new DataBase(movies,people);
		
		assertTrue(db.getAllAwards("Michael").isEmpty());
		assertTrue(db.getAllAwards(actor2).get(actor2).size() == 2);
		assertTrue(db.getAllAwards("JaCk").size() == 2);
		assertEquals(db.getAllAwards(actor3).get(actor3), set3);
	}
	
	
	

}
