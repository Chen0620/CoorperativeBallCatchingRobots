package ballCatchingRobots;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Robot extends Applet
{
	int xOrigin, yOrigin, xCur, yCur;
	Graphics g;
	
	public Robot (int xBegin, int yBegin, Graphics g)
	{
		this.xOrigin = xBegin;
		this.yOrigin = yBegin;
		this.xCur = xBegin;
		this.yCur = yBegin;
	}
	
	public int getXCur()
	{
		return this.xCur;
	}
	
	public int getYCur()
	{
		return this.yCur;
	}
	
	public void setXCur(int x)
	{
		this.xCur = x;
	}
	
	public void setYCur ( int y)
	{
		this.yCur = y;
	}
	
	public void drawRobot(Graphics g)
	{
		g.fillRect(getXCur()+1, getYCur()+1, 8, 8);
	}
	
	public void eraseRobot(Graphics g)
	{
		g.clearRect(getXCur()+1, getYCur()+1, 8, 8);
	}
	
	public void drawNetCenter(Graphics g, int topX, int topY, int leftX, int leftY, int rightX, int rightY)
	{
		g.setColor(Color.blue);
		g.fillRect((leftX+rightX)/2/10*10+1, (topY+10+rightY)/2/10*10+1, 8, 8);
	}
	
	public void eraseNetCenter(Graphics g, int topX, int topY, int leftX, int leftY, int rightX, int rightY)
	{
		g.setColor(Color.blue);
		g.clearRect((leftX+rightX)/2/10*10+1, (topY+10+rightY)/2/10*10+1, 8, 8);
	}
	
	public void drawCoverArea(Graphics g, int topX, int topY, int leftX, int leftY, int rightX, int rightY)
	{
		g.setColor(Color.gray);
		int startX , startY;
		for(startX = leftX+10; startX<=rightX; startX+=10)
		{
			for(startY = topY+10; startY<=rightY; startY+=10)
			{
				if(isInCoverArea(startX, startY, topX, topY, leftX, leftY, rightX, rightY))
					g.fillRect(startX+1, startY+1, 8, 8);
			}
		}
	}
	
	public boolean isInCoverArea(int x, int y, int topX, int topY, int leftX, int leftY, int rightX, int rightY)
	{
		return x>=leftX && x<=rightX && y>=topY && y<=rightY && x+(topY-topX) < y && -x+(topY+topX) < y;
	}
	
	public void eraseCoverArea(Graphics g,int topX, int topY, int leftX, int leftY, int rightX, int rightY)
	{
		int startX,startY;
		for(startX = leftX+10; startX<=rightX; startX+=10)
		{
			for(startY = topY+10; startY<=rightY; startY+=10)
			{
				if(isInCoverArea(startX, startY, topX, topY, leftX, leftY, rightX, rightY))
					g.clearRect(startX+1, startY+1, 8, 8);
			}
		}
		
	}
	
	public int randomDirection (int xD)
	{
		int dir = 0;
		Random rand = new Random();
		int x = rand.nextInt(3) - 1; // [0,3) - 1 = [-1,2) -> x = -1 or 0 or 1
		if(x == -1)
			dir = -10;
		else if(x == 0)
			dir = 0;
		else if(x == 1)
			dir = 10;
		return dir;
	}
}
