// Main required packages for File I/O and the Custom Database package
import java.io.*;
import java.util.*;
import Database.*;

public class Oscar {
	
	public static void main(String[] args)throws IOException,NullPointerException
	{
		//Exit in case of Incorrect number of arguments
		if(args.length < 2)
		{
			System.out.println("Insufficient Arguments, Please make sure both Input Data File and Log File paths are given as arguments");
			System.out.println("Usage: java Oscar dataFilepath logFilePath");
			System.exit(1);
		}
		
		// Get's the Filename from the File paths
		String dataFileName = args[0].substring(args[0].lastIndexOf('/') > 0 ? args[0].lastIndexOf('/') +1 : 0);
		String logFileName = args[1].substring(args[1].lastIndexOf('/') > 0 ? args[1].lastIndexOf('/') +1 : 0);
		
		// Creates File for Data 
		File dataFile = new File(args[0]);
		
		//Creates Reader Buffer
		BufferedReader br = null;
		
		// Checks for Errors pertaining to Input Data File - Existence, Readability
		if(!dataFile.exists())
		{
			System.out.println("The Data File: \""+dataFileName+"\" mentioned does not exist.");
			System.exit(1);
		}
		else
		{
			br = new BufferedReader(new FileReader(dataFile));
		}
		
		if(!dataFile.canRead())
		{
			System.out.println("Error Reading Data File \""+dataFileName+"\"");
			System.exit(1);
		}
		
		// Creates File for User Logs
		File logFile = new File(args[1]);
		
		// Creates Writer Buffer
		BufferedWriter bw = null;
		
		// Checks for Errors pertaining to Input Data File - Existence, Readability etc. Initializes Buffer Accordingly
		if(logFile.exists())
		{
			bw = new BufferedWriter(new FileWriter(args[1],true));
		}
		else
		{
			bw = new BufferedWriter(new FileWriter(logFile));
			bw.write("User Menu Choice\tUser Input\tTimestamp\n"); // Headers for the new Log File
		}
		
		if(!logFile.canWrite())
		{
			System.out.println("Error Writing to Log File \""+logFileName+"\"");
			System.exit(1);
		}
		
		DataBase db = new DataBase(); //Initializes new instance of the DataBase class
		
		if(db.ReadData(br)) // Error handling pertaining to data processing
		{
			System.out.println("Data Successfully Read");
			System.out.println("Welcome to the Oscars Database!\n");
		}
		else
		{
			System.out.println("An Error Occured while Reading in the Data from "+dataFileName+". Program Terminated");
			System.exit(1);
		}
		
		br.close(); // Closes input buffer
		int yr = 0;
		
		while(true)
		{
		char input = getUserInput(bw);
			switch (input) 
			{
			case '1': yr = getYear(bw);  // Best Picture 
					  String winner = db.getBestPicture(yr);
					  if(winner.equals("No Winner in "+yr))
					  {
						  System.out.println("There was no winner in "+yr);
					  }
					  else
					  {
						  System.out.println("The winner in "+yr+" was "+winner);
					  }
					  writeToLogFile(bw,true,"");
					  break;
			case '2': yr = getYear(bw);  // All pictures for a year
					  HashSet<Movie> nominees = db.getAllMovies(yr);
					  while(nominees.isEmpty())
					  {
						  System.out.println("There are no nominees for "+yr+". Re-Enter the year");
						  yr = getYear(bw);
					  }
					  System.out.println("Here are the nominees for "+yr+": ");
					  for(Movie m: nominees)
					  {
						  System.out.println(m.getName());
					  }
					  writeToLogFile(bw,true,"");
					  break;
			case '3': getAwards(db,bw);  // Awards pertaining to a name
					  writeToLogFile(bw,true,"");
					  break;
			case 'q': System.out.println("Good Bye");  // Quit
					  writeToLogFile(bw,true,"\t");
					  bw.close();
					  System.exit(1);
					  break;
			default: System.out.println("Invalid Selection");	//Default Case  
			}
		}	
		
	}

	private static void getAwards(DataBase db, BufferedWriter bw) // Gets a HashMap of Award winners with names matching
	{															 // the user input as the key and Set of awards for each as values.
		String name = getName(bw);
		  HashMap<String,HashSet<Individual>> awards = db.getAllAwards(name);
		  if(awards.isEmpty())
		  {
			  System.out.println("No results found for "+name); // Accepts input again in case the list comes up empty.
			  getAwards(db,bw);
		  }
		  Set<String> awardees = awards.keySet();
		  for(String person: awardees)
		  {
			  HashSet<Individual> awardsPerPerson = awards.get(person);
			  for(Individual award: awardsPerPerson) // prints out the data in the correct format.
			  {
				  int year = award.getYear();
				  String edition = getEdition(year);
				  if(award.didTheyWin())
					  System.out.println(person+" won "+award.getCategory()+" in "+year+" "+edition);
				  else
					  System.out.println(person+" was nominated for "+award.getCategory()+" in "+year+" "+edition);
			  }
		  }
		
	}

	private static String getEdition(int year) // Get's the Edition of the Oscars in which the awards were won.
	{
		int edition = year - 1927;
		String stringEdition = edition + "";
		if(stringEdition.charAt(stringEdition.length()-1) == '1')
			return "("+edition+"st)";
		else if(stringEdition.charAt(stringEdition.length()-1) == '2')
			return "("+edition+"nd)";
		else if(stringEdition.charAt(stringEdition.length()-1) == '3')
			return "("+edition+"rd)";
		else
			return "("+edition+"th)";	
	}

	private static String getName(BufferedWriter bw) // Requests the user for an input name in case of selection no. 3
	{												 // Continues recursively to request for Input until a valid input is entered.
		  System.out.println("Please enter all or part of the person's name: ");
		  BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(System.in));
		  String in;
		try {
			in = inputBuffer.readLine();
		} catch (Exception e) {
			System.out.println("Error in processesing input, please try again");
			writeToLogFile(bw,false,"Error Occured During Read");
			return getName(bw);
		}

		if(in.length() == 0)
		{
			System.out.println(" Please enter a name");
			return getName(bw);
		}
			 
		  writeToLogFile(bw,false,in);
		  return in;

		
	}

	private static int getYear(BufferedWriter bw) // Requests the user for an input year in case of selection no. 1 or 2
	{											  // Continues recursively to request for Input until a valid input is entered.
		  System.out.println("Please enter the year: ");
		  BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(System.in));
		  String in;
		try {
			in = inputBuffer.readLine();
		} catch (Exception e) {
			System.out.println("Error in processesing input, please try again");
			writeToLogFile(bw,false,"Error Occured During Read");
			return getYear(bw);
		}
		  if(!in.matches("[0-9]+"))
		  {
			  System.out.println("That is not a valid year. Please Try again.");
			  writeToLogFile(bw,false,in);
			  return getYear(bw);
		  }
		  else if(Integer.parseInt(in) > 2010 || Integer.parseInt(in) <= 1927)
		  {
			  System.out.println("Sorry but the year is out of range. Please Try again.");
			  writeToLogFile(bw,false,in);
			  return getYear(bw);
		  }
		  else
		  {
			  writeToLogFile(bw,false,in);
			  return Integer.parseInt(in);
		  }
	}

	private static char getUserInput(BufferedWriter bw) {// Get's the user input for the initial menu choices.
														 // Continues recursively until valid input is entered.
		System.out.println("\nPlease make your selection:");
		System.out.println("1: Search for best picture award winner by year");
		System.out.println("2: Search for best picture award nominees by year");
		System.out.println("3: Search for actor/actress nominations by name");
		System.out.println("Q: Quit");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			String input = br.readLine();
			if(input.equalsIgnoreCase("1") || input.equalsIgnoreCase("2") || input.equalsIgnoreCase("3") || input.equalsIgnoreCase("Q"))
			{
				input = input.toLowerCase();
				writeToLogFile(bw,false,input);
				return input.charAt(0);
			}
			else
			{
				System.out.println("That is not a valid selection. Please Try again.");
				writeToLogFile(bw,true,input+"\t\t");
				return getUserInput(bw);
				
			}
		} catch (Exception e) {
			System.out.println("Error in processesing input, please try again");
			writeToLogFile(bw,true,"Error Occured During Read\t\t");
			return getUserInput(bw);
		}
		
	}
	
	private static void writeToLogFile(BufferedWriter bw, boolean useTimeStamp, String text) // Writes the data to the log file
	{																						 // Throws an error in case an error is encountered.
		try
		{
			if(useTimeStamp)
				bw.write(text+"\t\t\t"+System.currentTimeMillis()+"\n");
			else
				bw.write(text+"\t\t");
		}
		catch(Exception e)
		{
			System.out.println("An Error Occured while writing to the Log File.");
			e.printStackTrace();
			System.exit(1);
		}
	}

}
