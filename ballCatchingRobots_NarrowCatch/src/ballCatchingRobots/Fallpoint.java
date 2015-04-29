package ballCatchingRobots;

import java.awt.Graphics;
import java.util.Random;

public class Fallpoint {
	private int xF,yF;
	
	public Fallpoint ()
	{
		setCoord(randXCoordGen(), randYCoordGen());
	}
	
	public int getXF()
	{
		return this.xF;
	}
	
	public int getYF()
	{
		return this.yF;
	}
	
	public void setCoord(int xf,int yf)
	{
		this.xF = xf;
		this.yF = yf;
	}
	
	public int randXCoordGen ()
	{
		Random rand = new Random();
		int coord = rand.nextInt(1200);
		coord = coord / 10 * 10 + 30; 
		return coord;
	}
	
	public int randYCoordGen ()
	{
		Random rand = new Random();
		int coord = rand.nextInt(650);
		coord = coord / 10 * 10 + 30; 
		return coord;
	}
	
	public void iniHeuristicValue(Graphics g, int x, int y,int hVal)
	{
		
	}
}
