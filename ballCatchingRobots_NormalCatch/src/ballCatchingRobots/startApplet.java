package ballCatchingRobots;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.applet.Applet;
import java.io.FileNotFoundException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class startApplet extends Applet implements Runnable
{	
	Thread t;
	int xTop = 710, yTop=310, xLeft=xTop-50, xRight=xTop+50, yLeft=yTop+50, yRight=yTop+50,xCenter = xTop+1, yCenter = yTop +31;
	int xfp=-1, yfp=-1;
	ArrayList<Fallpoint> fp = new ArrayList<Fallpoint>(10);
	int[][] visited = new int[150][80]; int[][] hvalue = new int[150][80];
	Robot top = new Robot(xTop, yTop, this.getGraphics());
	Robot left = new Robot(xLeft, yLeft, this.getGraphics());
	Robot right = new Robot(xRight, yRight, this.getGraphics());
	hValGenerator hg;
	
	public void start()  
	{  
		t = new Thread(this);  
		t.start();  
	}
	
	public void init()
	{
		
	}
	
	public void paint(Graphics g)
	{	
		Image gridBcg = createGrid();
		g.drawImage(gridBcg, 0, 0, this);
		
		Image obstacleImg = createObstacle();
		g.drawImage(obstacleImg, 0, 0, this);
		
		Image fallpointImg = createFallpoint();
		g.drawImage(fallpointImg, 0, 0, this);
	}
	
	public void update(Graphics g)
	{
		g.setColor(Color.magenta);
		if(xfp != -1 && yfp != -1)
			g.fillRect((xfp+2), (yfp+2), 7, 7);
	}
	
	public void catchBallComplete(Graphics g)
	{
		g.setColor(Color.green);
		g.fillRect(xCenter,yCenter, 8, 8);
	}
	
	private Image createGrid()
	{
		BufferedImage grid = new BufferedImage(1400,710,BufferedImage.TYPE_INT_ARGB);
		Graphics g = grid.getGraphics();
      
		g.setColor(Color.lightGray);
		for(int x=0;x<=1300;x+=10)
		{
			g.drawLine(0, x, 1300, x);
			g.drawLine(x, 0, x, 700);
		}
		
		g.setColor(Color.magenta);
		g.fillRect(1320, 40, 10, 10);
		g.setColor(Color.red);
		g.fillRect(1320, 70, 10, 10);
		g.setColor(Color.lightGray);
		g.fillRect(1320, 130, 10, 10);
		g.setColor(Color.blue);
		g.fillRect(1320, 160, 10, 10);
		g.setColor(Color.green);
		g.fillRect(1320, 260, 10, 10);
		g.setColor(Color.black);
		g.fillRect(1320, 100, 10, 10);
		Font myFont = new Font("helvetica",Font.BOLD,9);
        g.setFont(myFont);
		g.drawString("Color Block", 1320, 20);
		g.drawString("Fall Point", 1340, 50);
		g.drawString("Obstacle", 1340, 80);
		g.drawString("Robot", 1340, 110);
		g.drawString("Net Coverage", 1340, 140);
		g.drawString("Net Center", 1340,170);
		g.drawString("Status", 1320,240);
		g.drawString("Caught", 1340, 270);
		return grid;
	}
	
	private Image createObstacle()
	{
		BufferedImage obstacle = new BufferedImage(1400, 710, BufferedImage.TYPE_INT_ARGB);
		Graphics g = obstacle.getGraphics();
		//obstacle
		//g.setColor(Color.red);
		//int[] xPoints = {300,400,400,300};
		//int[] yPoints = {300,300,500,500};
		//int nPoints = 4;
		//g.fillPolygon(xPoints, yPoints, nPoints);
		
		//test
		hg = new hValGenerator(xfp, yfp,"none");
		int[] xp = {hg.obs1_startX,hg.obs1_startX+hg.obs1_width,hg.obs1_startX+hg.obs1_width,hg.obs1_startX};
		int[] yp = {hg.obs1_startY, hg.obs1_startY, hg.obs1_startY+hg.obs1_height,hg.obs1_startY+hg.obs1_height};
		int[] xp2 = {hg.obs2_startX,hg.obs2_startX+hg.obs2_width,hg.obs2_startX+hg.obs2_width,hg.obs2_startX};
		int[] yp2 = {hg.obs2_startY, hg.obs2_startY, hg.obs2_startY+hg.obs2_height,hg.obs2_startY+hg.obs2_height};
		int n = 4;
		g.setColor(Color.orange);
		g.fillPolygon(xp, yp, n);g.fillPolygon(xp2, yp2, n);
		
		return obstacle;
	}
	
	private Image createFallpoint()
	{
		BufferedImage obstacle = new BufferedImage(1400, 710, BufferedImage.TYPE_INT_ARGB);
		Graphics g = obstacle.getGraphics();
		return obstacle;
	}
	
	public boolean withinCanvas ( int x, int y, int robot)
	{
		if(robot == 1)//right
			return x/10 >= 2 && x/10 < 130 && y/10 >= 2 && y/10 < 70;
		if(robot == 2)//left
			return x/10 >= 0 && x/10 < 128 && y/10 >= 2 && y/10 < 70;
		if(robot == 3)//top
			return x/10 >= 1 && x/10 < 129 && y/10 >= 0 && y/10 < 69;
		return false;
	}
	
	public void updateRobots ()
	{
		xLeft=xTop-10; 
		xRight=xTop+10; 
		yLeft=yTop+10; 
		yRight=yTop+10;
	}
	
	public void drawUpdatedRobot()
	{
		top.setXCur(xTop);top.setYCur(yTop);
		right.setXCur(xRight);right.setYCur(yRight);
		left.setXCur(xLeft);left.setYCur(yLeft);
		top.drawRobot(getGraphics());
		left.drawRobot(getGraphics());
		right.drawRobot(getGraphics());
		update(getGraphics());
	}
	
	public void catchBall ()
	{
		top.setXCur(xTop);top.setYCur(yTop);
		right.setXCur(xRight);right.setYCur(yRight);
		left.setXCur(xLeft);left.setYCur(yLeft);
		top.drawRobot(getGraphics());
		left.drawRobot(getGraphics());
		right.drawRobot(getGraphics());
		update(getGraphics());
	}
	
	public void eraseRobots()
	{
		top.eraseRobot(getGraphics());
		right.eraseRobot(getGraphics());
		left.eraseRobot(getGraphics());
		update(getGraphics());
	}
	
	public void eraseFp()
	{
		this.getGraphics().clearRect(xfp+2, yfp+2, 7, 7);
	}
	
	public String farthestRobot()
	{
		String res = "";
		
		if(hvalue[xLeft/10][yLeft/10] >= hvalue[xRight/10][yRight/10] && hvalue[xLeft/10][yLeft/10] >= hvalue[xTop/10][yTop/10])
			res = "left";
		else if(hvalue[xRight/10][yRight/10] >= hvalue[xLeft/10][yLeft/10] && hvalue[xRight/10][yRight/10] >= hvalue[xTop/10][yTop/10])
			res = "right";
		else if(hvalue[xTop/10][yTop/10] >= hvalue[xRight/10][yRight/10] && hvalue[xTop/10][yTop/10] >= hvalue[xLeft/10][yLeft/10])
			res = "top";
		
		return res;
	}
	
	public void hval(String direction) 
	{
		hg = new hValGenerator(xfp, yfp,direction);
		hvalue = hg.getHValArray();
	}
	
	public void drawCSpace() 
	{
		Graphics g= this.getGraphics();
		for(int i=0; i<130;i++)
		{
			for(int j = 0; j<70;j++)
			{
				if(hvalue[i][j] == Integer.MAX_VALUE)
				{
					g.setColor(Color.BLUE);
					g.fillRect(i*10+1, j*10+1, 8, 8);
				}
				if(hvalue[i][j] == -1)
				{
					g.setColor(Color.green);
					g.fillRect(i*10+1, j*10+1, 8, 8);
				}
			}
			System.out.println();
		}
	}
	
	public boolean withinObs(int obs_startX, int obs_startY,int obs_width, int obs_height)
	{
		return (xTop >= obs_startX && xTop <= obs_startX+obs_width && yTop >= obs_startY && yTop <= obs_startY+obs_height) 
				|| (xRight >= obs_startX && xRight <= obs_startX+obs_width && yRight >= obs_startY && yRight <= obs_startY+obs_height)
				|| (xLeft >= obs_startX && xLeft <= obs_startX+obs_width && yLeft >= obs_startY && yLeft <= obs_startY+obs_height)
				|| (xfp >= obs_startX && xfp <= obs_startX+obs_width && yfp >= obs_startY && yfp <= obs_startY+obs_height);
	}
	
	public void skipCase()
	{
		String str = "Skip this case!!";
		Graphics g = this.getGraphics();
		g.setColor(Color.RED);
		g.drawString(str, 1320, 300);
	}
	
	public void eraseSkipCase()
	{
		Graphics g = this.getGraphics();
		g.clearRect(1310, 280, 130, 30);
	}
	
	@Override
	public void run() 
	{	
		try 
		{
			int firstTime =1, skip = 0;
			for(int i=0;i<20;i++)
			{
				Thread.sleep(2000);
				if(firstTime!=1 && skip == 0)
				{
					eraseRobots();eraseFp();
				}
				Random rand = new Random();
				xfp = rand.nextInt(1289)/10*10+10;
				yfp = rand.nextInt(699)/10*10;
				if(i==1) yfp = 0;
				if(i==2) xfp = 0;
				if(i==3) xfp=1290;
				if(i==4) yfp = 690;
				xTop = rand.nextInt(1290)/10*10; yTop=rand.nextInt(300)/10*10;
				if(i==6)
				{
					xfp = 600; yfp = 550;
					xTop = 1100; yTop = 200;
				}
				updateRobots(); 
				if(!withinObs(hg.obs1_startX,hg.obs1_startY,hg.obs1_width,hg.obs1_height) && !withinObs(hg.obs2_startX,hg.obs2_startY,hg.obs2_width,hg.obs2_height))
				{
					firstTime = 0;skip = 0;
					//System.out.println(i+ " fp:"+xfp+","+yfp+" robottop:"+xTop+","+yTop);
					
					//top, right, bottom, left -> clockwise
					if((xfp == 0 && yfp == 0) || (xfp == 1290 && yfp == 690) || (xfp == 0 && yfp == 690) || (xfp == 1290 && yfp == 0))
					{
						//do nothing
					}
					else 
					{
						if(xfp == 0) // fall point on left edge
						{
							String dir = "left";
							hval(dir);
							update(this.getGraphics());
							drawUpdatedRobot();
							int cur = hvalue[xLeft/10][yLeft/10];
							//drawCSpace();
							while(cur != 10)
							{
								if(withinCanvas(xLeft, yLeft,2))
								{
									eraseRobots();
									if(withinCanvas(xLeft, yLeft-10,2) && hvalue[xLeft/10][yLeft/10-1] == cur-10) // top
									{
										yTop -= 10;
									}
									else if(withinCanvas(xLeft+10, yLeft,2) && hvalue[xLeft/10+1][yLeft/10] == cur-10) // right
									{
										xTop += 10; 
									}
									else if(withinCanvas(xLeft, yLeft+10,2) && hvalue[xLeft/10][yLeft/10+1] == cur-10) // bottom
									{
										yTop += 10; 
									}
									else if(withinCanvas(xLeft-10, yLeft,2) && hvalue[xLeft/10-1][yLeft/10] == cur-10) // left
									{
										xTop -= 10; 
									}
									updateRobots();drawUpdatedRobot();Thread.sleep(50);
									cur -= 10;
								}
								else break;
							}
							
							if(yLeft == yfp)
							{
								eraseRobots();
								yLeft+=10;xTop-=10;
								catchBall();Thread.sleep(100);
								eraseRobots();
								xLeft-=10;xTop-=10;
								catchBall();Thread.sleep(100);
							}
							else if(xLeft == xfp && yLeft < yfp)
							{
								eraseRobots();
								yLeft+=10;xTop-=10;
								catchBall();Thread.sleep(100);
								eraseRobots();
								yLeft+=10;yTop+=10;
								catchBall();Thread.sleep(100);
							}
							else 
							{
								eraseRobots();
								yTop-=10;
								catchBall();Thread.sleep(100);
								eraseRobots();
								xTop-=10;
								catchBall();Thread.sleep(100);
							}
						}
						else if(yfp == 0) //fall point on top edge
						{
							String dir = "top";
							hval(dir);
							update(this.getGraphics());
							drawUpdatedRobot();
							int cur = hvalue[xTop/10][yTop/10];
							//drawCSpace();
							while(cur != 10)
							{
								if(withinCanvas(xTop, yTop,3))
								{
									eraseRobots();
									if(withinCanvas(xTop, yTop-10,3) && hvalue[xTop/10][yTop/10-1] == cur-10) // top
									{
										yTop -= 10;
									}
									else if(withinCanvas(xLeft+10, yTop,3) && hvalue[xTop/10+1][yTop/10] == cur-10) // right
									{
										xTop += 10; 
									}
									else if(withinCanvas(xLeft, yTop+10,3) && hvalue[xTop/10][yTop/10+1] == cur-10) // bottom
									{
										yTop += 10; 
									}
									else if(withinCanvas(xLeft-10, yTop,3) && hvalue[xTop/10-1][yTop/10] == cur-10) // left
									{
										xTop -= 10; 
									}
									updateRobots();drawUpdatedRobot();Thread.sleep(50);
									cur -= 10;
									//System.out.println(cur+ " fp:" + xfp + ","+ yfp +" right: " +xRight+"," + yRight);
								}
								else break;
							}
							//catch ball
							if(xTop == xfp)
							{
								eraseRobots();
								xTop-=10;yRight-=10;
								catchBall();Thread.sleep(100);
								eraseRobots();
								yTop-=10;yRight-=10;
								catchBall();Thread.sleep(100);
							}
							else if(yTop == yfp && xTop < xfp)
							{
								eraseRobots();
								xRight+=10;xTop-=10;
								catchBall();Thread.sleep(100);
								eraseRobots();
								yRight-=10;
								catchBall();Thread.sleep(100);
							}
							else 
							{
								eraseRobots();
								xLeft-=10;xRight-=10;
								catchBall();Thread.sleep(100);
								eraseRobots();
								yLeft-=10;
								catchBall();Thread.sleep(100);
							}
						}
						else//(xfp == 1290) // fall point on right edge, reference point: right robot
						{
							String dir = "right";
							hval(dir);
							update(this.getGraphics());
							drawUpdatedRobot();//draw robots
							int cur = hvalue[xRight/10][yRight/10];
							//drawCSpace();
							//System.out.println(cur+ " fp:" + xfp + ","+ yfp +" right: " +xRight+"," + yRight);
							while(cur != 10)
							{
								if(withinCanvas(xRight, yRight,1))
								{
									eraseRobots();
									if(withinCanvas(xRight, yRight-10,1) && hvalue[xRight/10][yRight/10-1] == cur-10) // top
									{
										yTop -= 10;
									}
									else if(withinCanvas(xRight+10, yRight,1) && hvalue[xRight/10+1][yRight/10] == cur-10) // right
									{
										xTop += 10; 
									}
									else if(withinCanvas(xRight, yRight+10,1) && hvalue[xRight/10][yRight/10+1] == cur-10) // bottom
									{
										yTop += 10; 
									}
									else if(withinCanvas(xRight-10, yRight,1) && hvalue[xRight/10-1][yRight/10] == cur-10) // left
									{
										xTop -= 10; 
									}
									updateRobots();drawUpdatedRobot();Thread.sleep(50);
									cur -= 10;
									//System.out.println(cur+ " fp:" + xfp + ","+ yfp +" right: " +xRight+"," + yRight);
								}
								else break;
							}
							
							//catch ball
							if(yRight== yfp && xRight < xfp)
							{
								eraseRobots();
								yRight+=10;xTop+=10;
								catchBall();Thread.sleep(100);
								eraseRobots();
								xRight+=10;xTop+=10;
								catchBall();Thread.sleep(100);
							}
							else if(xRight == xfp && yRight < yfp)
							{
								if( xfp == 1290)
								{
									eraseRobots();
									yRight+=10;xTop+=10;
									catchBall();Thread.sleep(100);
									eraseRobots();
									yRight+=10;
									catchBall();Thread.sleep(100);
								}
								else
								{
									eraseRobots();
									yLeft+=10;xRight+=10;
									catchBall();Thread.sleep(100);
									eraseRobots();
									yRight+=10;
									catchBall();Thread.sleep(100);
								}
							}
							else if(xRight == xfp && yRight > yfp)
							{
								eraseRobots();
								xRight+=10;
								catchBall();Thread.sleep(100);
								eraseRobots();
								yRight-=10;
								catchBall();Thread.sleep(100);
							}
						}
					}	
				}
				else 
				{
					skip = 1;
					skipCase();
					Thread.sleep(1500);
					eraseSkipCase();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
