import java.io.*;

public class CommandLineI implements UserInterface {

	private PrintStream ps;
	private InputStream is;
	private BufferedReader br;
	@Override
	public String getInput(int inputCode, LoggerSubject log) {
		if(inputCode == 0) // Single Char input
		{
			try {
				String input = br.readLine();
				if(input.equalsIgnoreCase("1") || input.equalsIgnoreCase("2") || input.equalsIgnoreCase("3") || input.equalsIgnoreCase("Q"))
				{
					input = input.toLowerCase();
					log.logWithoutTimestamp(input);
					return input;
				}
				else
				{
					System.out.println("That is not a valid selection. Please Try again.");
					log.logWithTimestamp(input+"\t\t");
					return getInput(inputCode,log);
				}
			} catch (Exception e) {
				System.out.println("Error in processesing input, please try again");
				log.logWithTimestamp("Error Occured During Read\t\t");
				return getInput(inputCode,log);
			}
		}
		else if(inputCode == 1)
		{
			System.out.println("Please enter all or part of the person's name: ");
			  BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(is));
			  String in;
			try {
				in = inputBuffer.readLine();
			} catch (Exception e) {
				System.out.println("Error in processesing input, please try again");
				log.logWithoutTimestamp("Error Occured During Read");
				return getInput(inputCode,log);
			}

			if(in.length() == 0)
			{
				return getInput(inputCode,log);
			}
				 
			  log.logWithoutTimestamp(in);
			  return in;
		}
		else if(inputCode == 2)
		{
			System.out.println("Please enter the year: ");
			  BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(System.in));
			  String in;
			try {
				in = inputBuffer.readLine();
			} catch (Exception e) {
				System.out.println("Error in processesing input, please try again");
				log.logWithoutTimestamp("Error Occured During Read");
				return getInput(inputCode,log);
			}
			  if(!in.matches("[0-9]+"))
			  {
				  System.out.println("That is not a valid year. Please Try again.");
				  log.logWithoutTimestamp(in);
				  return getInput(inputCode,log);
			  }
			  else if(Integer.parseInt(in) > 2010 || Integer.parseInt(in) <= 1927)
			  {
				  System.out.println("Sorry but the year is out of range. Please Try again.");
				  log.logWithoutTimestamp(in);
				  return getInput(inputCode,log);
			  }
			  else
			  {
				  log.logWithTimestamp(in);
				  return in;
			  }
		}
		return null;
	}

	@Override
	public void showPrompt() {
		ps.println("\nPlease make your selection:");
		ps.println("1: Search for best picture award winner by year");
		ps.println("2: Search for best picture award nominees by year");
		ps.println("3: Search for actor/actress nominations by name");
		ps.println("Q: Quit");
		
	}
	
	public void startMessage()
	{
		ps.println("Data Successfully Read");
		ps.println("Welcome to the Oscars Database!\n");
	}


	@Override
	public void displayResult(String s) {
		ps.println(s);
		
	}
	
	public CommandLineI(PrintStream ps, InputStream is)
	{
		this.ps = ps;
		this.is = is;
		br = new BufferedReader(new InputStreamReader(is));
	}

}
