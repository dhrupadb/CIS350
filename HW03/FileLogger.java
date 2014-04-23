import java.io.*;

public class FileLogger implements LogObserver{
	
	private static FileLogger logInstance = null;
	private static BufferedWriter bw = null;
	
	private FileLogger(String file)
	{
		try {
			bw = new BufferedWriter(new FileWriter(file,true));
			bw.write("Menu Choice\tUser Input\tTimestamp\n");
		} catch (IOException e) {
			System.out.println("An error occured while creating Log File. Program Terminating");
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	
	public static FileLogger getInstance(String file)
	{
		if(logInstance == null)
			logInstance = new FileLogger(file);
		return logInstance;
	}
	
	private void writeToFile(boolean useTimeStamp, String text) // Writes the data to the log file
	{															// Throws an error in case an error occurred.
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
	
	public void endLog()
	{
		try {
			bw.close();
		} catch (IOException e) {
			System.out.println("An error occured while creating Log File. Program Terminating");
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void notify(String s, boolean stampStatus) {
		writeToFile(stampStatus, s);
	}

}

