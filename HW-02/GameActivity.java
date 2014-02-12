/**
 * CIS 350 University of Pennsylvania Spring 2014
 * @author Dhrupad Bhardwaj (dhrupadb)
 * 
 * Game Activity class which initializes the Game's
 * runtime environment including the scoreboard, display etc.
 * Initializes a GameView where the game is played.
 * Acts as a bridge between the GameView and TextView scoreboard.
 * 
 */

package edu.upenn.cis350.graphics;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class GameActivity extends Activity {

	@Override
	
	// Initializes the GameView and TextView to setup the game environment
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Passes a reference of the TextView to GameView. 
		TextView score = (TextView)findViewById(R.id.scoreboard);
		GameView gv = (GameView)findViewById(R.id.gameView);
		gv.setTextView(score);
	}

	// Ends GameActivity on being called. sets the result flag
	public void endActivityClick(GameView gv)
	{
		Intent i = new Intent();
		setResult(RESULT_OK, i);
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
