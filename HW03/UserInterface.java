import java.io.*;

public interface UserInterface {
	
	public String getInput(int inputCode, LoggerSubject log);
	
	public void showPrompt();
	
	public void displayResult(String s);
}
