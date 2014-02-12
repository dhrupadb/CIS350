/**
 * CIS 350 University of Pennsylvania Spring 2014
 * @author Dhrupad Bhardwaj (dhrupadb)
 * 
 * Main Activity class which encapsulates
 * all the handlers for Launching/ Quitting the Application
 * 
 * Initializes the main page and the actions for the button clicks
 * Launches the game
 * 
 */


package edu.upenn.cis350.graphics;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	// Default ID state
	public static final int GameActivity_ID = 1;
	
	@Override
	// Set's up the home screen environment 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launch_activity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	// Launches a new Game when the Launch button is clicked.
	public void onLaunchButtonClick(View v)
	{
		Intent i = new Intent(this, GameActivity.class);
		startActivityForResult(i,GameActivity_ID);
	}
	
	// Exits the app when the Quit button is pressed. 
	public void onFinishButtonClick(View v)
	{
		Intent i = new Intent();
		setResult(RESULT_OK, i);
		finish();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		super.onActivityResult(requestCode, resultCode, intent);
		setContentView(R.layout.launch_activity);
	}
	

}
