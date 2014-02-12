/**
 * CIS 350 University of Pennsylvania Spring 2014
 * @author Dhrupad Bhardwaj (dhrupadb)
 * 
 * Main Game Environment.
 * Runs the game, handles the game logic and scoring
 * Also runs threads to handle touch events simultaneously
 * with animation events.
 * 
 */



package edu.upenn.cis350.graphics;

import java.util.LinkedList;

import android.content.Context;
import android.graphics.*;
import android.os.AsyncTask;
import android.util.*;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;

public class GameView extends View {
	
	// Global Variables required throughout the program
	// PaintBrush, PointList, image, score, TextView reference etc.
	
	protected Paint p;
	protected LinkedList<Point> pointList;
	protected int numPoints;
	protected Bitmap image;
	protected int image_x;
	protected int image_y;
	protected int vel_y;
	protected int vel_x;
	protected int unicornScore;
	protected int gameScore;
	protected boolean gameContinue;
	TextView scoreboard;
	protected GameView thisView = this;
	
	// Default constructors
	public GameView(Context context) {
		super(context);
		init();
	}

	public GameView(Context context, AttributeSet as) {
		super(context, as);
		init();
	}
	
	//Initialization function -> Gives each global a starting default value
	// Starts the first BackGround Thread to move the image
	private void init() {
			p = new Paint();
			p.setColor(Color.RED);
			p.setStrokeWidth(10);
			pointList = new LinkedList<Point>();
			numPoints = 0;
			setBackgroundResource(R.drawable.space);
			image = BitmapFactory.decodeResource(getResources(), R.drawable.unicorn);
			image = Bitmap.createScaledBitmap(image, 150, 150, false);
			image_x = -150;
			
			// Randomly Calculates angle and staritng position
			image_y = (int)(200 + 200*Math.random());
			vel_y = (int)(10 - 20*Math.random());
			
			unicornScore = 0;
			gameScore = 0;
			
			// Horizontal Velocity is a function of the 
			// number of kills.
			
			vel_x = (int)(gameScore*5 + 10);
			gameContinue = true;
			
			// First Thread to start the movement.
			new BackgroundTask().execute("Execute");
	}
	
	// Accepts a TextView object form the GameActivity Class 
	// Sets the Global to the received TextView object.
	// Links the GameView and the TextView.
	public void setTextView(TextView tv)
	{
		scoreboard = tv;
		tv.setText("0");
	}


	public void onDraw(Canvas c) {
		/* called each time this View is drawn */
		
		// Checks if the image has passed the screen more than 3 times
		if(unicornScore >= 3 && gameContinue) {
			// Resets counters
			gameContinue = false;
			resetImage(false);
			image_x = -200;
			//Displays Toast
			Toast endToast = Toast.makeText(getContext(), "Game Over : You Lost", Toast.LENGTH_LONG);
			endToast.show();	
			
			// Begins a delay thread to prevent navigation to the Main Activity
			// until the Toast disappears. 
			endThread.start();
		}
		else if(gameScore >= 5 && gameContinue) { // In the case user has gotten 5 or more kills
			// Resets counters
			gameContinue = false;
			resetImage(false);
			image_x = -200;
			//Displays Toast
			Toast endToast = Toast.makeText(getContext(), "Game Over : You Won!", Toast.LENGTH_LONG);
			endToast.show();
			
			// Begins a delay thread to prevent navigation to the Main Activity
						// until the Toast disappears.
			endThread.start();
		}
		c.drawBitmap(image,image_x,image_y, p);
		if(!gameContinue)
			return;
		
		// Draws the stroke if there are more than one point in the pointList
		if(numPoints > 1)
		{
			
			for(int i = 0; i < numPoints-1; i++)
			{
				Point p1 = pointList.get(i);
				Point p2 = pointList.get(i+1);
				c.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY(), p);
			}
		}
		
		// Reset's the image position in case it moves off the screen.
		if(image_x > this.getWidth() || image_y <= -150 || image_y >= this.getHeight())
			{
				resetImage(false);
			}

	}
	
	// Checks if any of the points on the user's stroke are within the bounding box of the image
	private boolean checkIntersect() {
		for(Point p : pointList)
		{
			float x = p.getX();
			float y = p.getY();
			if((x > image_x && x < image_x+150) && (y > image_y && y < image_y + 150))
				return true;
		}
		return false;
	}
	// Resets all variables associated with the image and updates the score accordingly
	// Takes a boolean variable which figured out if the reset was due to a kill
	// or due to the image moving off of the screen.
	private void resetImage(boolean dead) {
		image_x = -150;
		image_y = (int)(200 + 200*Math.random());
		vel_y = (int)(10 - 20*Math.random());
		vel_x = (int)(gameScore*3 + 10);
		if(dead) {
			gameScore++;
			pointList.clear();
			numPoints = 0;
			scoreboard.setText(gameScore+"");
		}
		else {
			unicornScore++;
		}
	}

	// Override method for Android's default onTouchEvent.
	// Checks for a stroke and then accordingly 
	// updates the pointsList of points contained in 
	// that stroke.
	public boolean onTouchEvent(MotionEvent e) {
		
		if(e.getAction() == MotionEvent.ACTION_DOWN) // Starts the stroke
		{
			Point newPoint = new Point(e.getX(),e.getY());
			pointList.add(newPoint);
			numPoints++;
			invalidate();
			return true;
		}
		if(e.getAction() == MotionEvent.ACTION_MOVE) // Appends new points to the stroke
		{
			Point newPoint = new Point(e.getX(),e.getY());
			pointList.add(newPoint);
			numPoints++;
			invalidate();
			return true;
		}
		if(e.getAction() == MotionEvent.ACTION_UP) // Ends the stroke
		{
			if(checkIntersect()) // In case there was a kill, resets counters.
			{
				resetImage(true);
				invalidate();
				return true;
			}		
			pointList.clear();
			numPoints = 0;
			invalidate();
			return true;
		}
		return true;
		
	}
	
	// A thread which sleeps for 3.5s in order for the Toast to pop up and 
	// complete it's message. 
	Thread endThread = new Thread () {
		public void run() {
			try {
                Thread.sleep(3500); 
                GameActivity ga = (GameActivity)(getContext());
				ga.endActivityClick(thisView);
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
	};
	
	// Backgorund task which moves the image's position and calls invalidate()
	// to make the screen re-draw.
	public class BackgroundTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try { Thread.sleep(20); } catch (Exception e) { }
			return arg0[0];
		}

		protected void onPostExecute(String result) {
			image_x += vel_x;
			image_y += vel_y;
			invalidate();
			if(gameContinue) {
				new BackgroundTask().execute("Execute");
			}
		}

	}

}
