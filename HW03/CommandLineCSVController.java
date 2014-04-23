import java.io.*;

public class CommandLineCSVController extends Controller {

	public CommandLineCSVController(String s1, String s2)
			throws FileNotFoundException {
		start(s1, s2);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected UserInterface createUI() {
		return new CommandLineI(System.out, System.in);
	}

	@Override
	protected Reader createDataReader(BufferedReader br, File dataFile) {
		return new CSVReader(br, dataFile);
	}

}
