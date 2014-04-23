import java.io.*;

public abstract class Controller {
	
	public void start(String inputFile, String loggingFile) throws FileNotFoundException
	{
		//Exit in case of Incorrect number of arguments
				
				// Get's the Filename from the File paths
				String dataFileName = inputFile.substring(inputFile.lastIndexOf('/') > 0 ? inputFile.lastIndexOf('/') +1 : 0);
				String logFileName = loggingFile.substring(loggingFile.lastIndexOf('/') > 0 ? loggingFile.lastIndexOf('/') +1 : 0);
				
				// Creates File for Data 
				File dataFile = new File(inputFile);
				
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
				
				
				Reader db = createDataReader(br, dataFile); //Initializes new instance of the Reader class
				UserInterface ui = createUI();
				LoggerSubject log = new LoggerSubject();
				FileLogger logInstance = FileLogger.getInstance(logFileName);
				log.addObserver(logInstance);
				
				int yr = 0;
				
				while(true)
				{
				ui.showPrompt();
				String userInput = ui.getInput(0, log);
				char input = userInput.charAt(0);
					switch (input) 
					{
					case '1': yr = Integer.parseInt(ui.getInput(2,log));  // Best Picture 
							  String winner = db.getBestPicture(yr);
							  if(winner.equals("No Winner in "+yr))
							  {
								  ui.displayResult("There was no winner in "+yr);
							  }
							  else
							  {
								  ui.displayResult(winner);
							  }
							  log.logWithTimestamp("");
							  break;
					case '2': yr = Integer.parseInt(ui.getInput(2,log));  // All pictures for a year
							  String winners = db.getAllMovies(yr);
							  while(winners.length()==0)
							  {
								  ui.displayResult("There are no nominees for "+yr+".");
								  yr = Integer.parseInt(ui.getInput(2, log));
								  winners = db.getAllMovies(yr);
							  }
							  ui.displayResult("Here are the nominees for "+yr+": \n");
							  ui.displayResult(winners);
							  log.logWithTimestamp("");
							  break;
					case '3': Comparator c = new NameComparator();
						      String name = ui.getInput(1, log);
							  String awardees = db.getAllAwards(name,c);  // Awards pertaining to a name
							  while(awardees.length() == 0)
							  {
								  ui.displayResult("There are no awardees by the name : "+name+".");
								  name = ui.getInput(1, log);
								  awardees = db.getAllAwards(name,c);
							  }
							  ui.displayResult("Here are the awardees with name matching "+name+": \n");
							  ui.displayResult(awardees);
							  log.logWithTimestamp("");
							  break;
					case 'q': System.out.println("Good Bye");  // Quit
							  log.logWithTimestamp("\t");
							  log.closeLog();
							  System.exit(1);
							  break;
					default: System.out.println("Invalid Selection");	//Default Case  
					}
				}	
	}
	
	
	protected abstract UserInterface createUI();
	
	protected abstract Reader createDataReader(BufferedReader br, File dataFile);

}
