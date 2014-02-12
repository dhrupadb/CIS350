/**
 * CIS 350 University of Pennsylvania Spring 2014
 * @author Dhrupad Bhardwaj (dhrupadb)
 * 
 * Implementation of the Point Class to represent 
 * an ordered pair of in the 2D coordinate System.
 * 
 */

package edu.upenn.cis350.graphics;

public class Point {
	
	
	// Private Global Float variables for the x,y coordinates.
	private float x;
	private float y;
	
	public Point(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	// Methods returning the values to ensure the values 
	// are not manipulated after initialization
	public float getX()
	{
		return x;
	}
	public float getY()
	{
		return y;
	}

}
