import java.io.FileNotFoundException;


public class Oscars {
	
	public static void main(String[] args) throws FileNotFoundException
	{
		if(args.length < 2)
		{
			System.out.println("Insufficient Arguments, Please make sure both Input Data File and Log File paths are given as arguments");
			System.out.println("Usage: java Oscar dataFilepath logFilePath");
			System.exit(1);
		}
		
		CommandLineCSVController c = new CommandLineCSVController(args[0], args[1]);
	}

}
