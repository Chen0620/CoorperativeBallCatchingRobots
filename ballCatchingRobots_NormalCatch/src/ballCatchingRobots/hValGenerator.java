package ballCatchingRobots;


import java.io.PrintStream;
import java.util.ArrayList;

public class hValGenerator 
{
	int[][] hval =new int[130][70];
	boolean[][] visited = new boolean[130][70];
	int[][] counter = new int[130][70];
	ArrayList<Integer> xCoord = new ArrayList<>(130);
	ArrayList<Integer> yCoord = new ArrayList<>(130);
	
	static final int totalSpace = 70*130-1;
	int count=0;
	int obs1_startX = 300, obs1_startY = 100, obs1_width=500, obs1_height=300;
	int presetArea = (obs1_width*obs1_height)/100;
	int obs2_startX = 830, obs2_startY = 400, obs2_width=100, obs2_height=250;
	int presetArea2 = (obs2_width*obs2_height)/100;
	int leng = 1, leng2 = 0, total = totalSpace - presetArea - presetArea2;
	
	public hValGenerator(int x, int y, String direction)
	{
		iniArrays();
		setFP(x/10, y/10);
		obs(obs1_startX,obs1_startY,obs1_width,obs1_height);
		obs(obs2_startX,obs2_startY,obs2_width,obs2_height);
		switch (direction) {
		case "right":
			rightRefCSpace(obs1_startX,obs1_startY,obs1_width,obs1_height);
			rightRefCSpace(obs2_startX,obs2_startY,obs2_width,obs2_height);
			presetArea = 2*obs1_height/10+obs1_width/10 + 1;
			presetArea2 = 2*obs2_height/10+obs2_width/10 + 1;
			total = total - presetArea - presetArea2;
			//System.out.println("right"+ total+" "+presetArea+" "+presetArea2);
			break;
		case "left":
			leftRefCSpace(obs1_startX,obs1_startY,obs1_width,obs1_height);
			leftRefCSpace(obs2_startX,obs2_startY,obs2_width,obs2_height);
			presetArea = 2*obs1_height/10+obs1_width/10 + 1;
			presetArea2 = 2*obs2_height/10+obs2_width/10 + 1;
			total = total - presetArea - presetArea2;
			//System.out.println("left"+ total+" "+presetArea+" "+presetArea2);
			break;
		case "top":
			//System.out.println("zero "+noOfFalse());
			topRefCSpace(obs1_startX,obs1_startY,obs1_width,obs1_height);
			//System.out.println("fir "+noOfFalse());
			topRefCSpace(obs2_startX,obs2_startY,obs2_width,obs2_height);
			//System.out.println("sec "+noOfFalse());
			presetArea = 2*(obs1_height/10+1)+obs1_width/10;
			presetArea2 =2*(obs2_height/10+1)+obs2_width/10;
			total = total - presetArea - presetArea2;
			//System.out.println("top"+ total+" "+presetArea+" "+presetArea2);
			break;

		default:
			break;
		}
		if(!direction.equals("none"))
			createHVal(10);
	}
	
	public void iniArrays()
	{
		for(int i=0;i<130;i++)
		{
			for(int j=0;j<70;j++)
			{
				visited[i][j] = false;
				hval[i][j] = 0;
				counter[i][j]=0;
			}
		}		
	}
	
	public void obs(int obs_startX, int obs_startY,int obs_width, int obs_height)
	{
		for(int i=obs_startX; i<obs_startX+obs_width;i+=10)
		{
			for(int j=obs_startY;j<obs_startY+obs_height;j+=10)
			{
				visited[i/10][j/10] = true;
				hval[i/10][j/10] = -1;
			}
		}
	}
	
	public void rightRefCSpace(int obs_startX, int obs_startY,int obs_width, int obs_height)
	{
		//hval around obs
		for(int i=obs_startY;i<obs_startY+obs_height;i+=10)
		{
			visited[(obs_startX+obs_width)/10+1][i/10] = visited[(obs_startX+obs_width)/10][i/10]=true;
			hval[(obs_startX+obs_width)/10+1][i/10] = hval[(obs_startX+obs_width)/10][i/10] = Integer.MAX_VALUE;
		}
		for(int i=obs_startX;i<=obs_startX+obs_width;i+=10)
		{
			visited[i/10][(obs_startY+obs_height)/10] = true;
			hval[i/10][(obs_startY+obs_height)/10] = Integer.MAX_VALUE;
		}
	}
	
	public void leftRefCSpace(int obs_startX, int obs_startY,int obs_width, int obs_height)
	{
		//hval around obs
		count = 0;
		for(int i=obs_startY;i<obs_startY+obs_height;i+=10)
		{
			visited[(obs_startX)/10-2][i/10] = visited[(obs_startX)/10-1][i/10]=true;
			hval[(obs_startX)/10-2][i/10] = hval[(obs_startX)/10-1][i/10] = Integer.MAX_VALUE;
		}
		for(int i=obs_startX;i<obs_startX+obs_width;i+=10)
		{
			visited[i/10][(obs_startY+obs_height)/10] = true;
			hval[i/10][(obs_startY+obs_height)/10] = Integer.MAX_VALUE;
		}
		visited[obs_startX/10-1][(obs_startY+obs_height)/10] = true;
		hval[obs_startX/10-1][(obs_startY+obs_height)/10] = Integer.MAX_VALUE;
	}
	
	public void topRefCSpace(int obs_startX, int obs_startY,int obs_width, int obs_height)
	{
		//hval around obs
		count = 0;
		for(int i=obs_startY-10;i<obs_startY+obs_height;i+=10)
		{
			count += 2 ;
			visited[(obs_startX)/10-1][i/10] =	visited[(obs_startX+obs_width)/10][i/10]=true;
			hval[(obs_startX)/10-1][i/10] = hval[(obs_startX+obs_width)/10][i/10] = Integer.MAX_VALUE;
		}
		for(int i=obs_startX;i<obs_startX+obs_width;i+=10)
		{
			count ++ ;
			visited[i/10][obs_startY/10-1] = true;
			hval[i/10][obs_startY/10-1] = Integer.MAX_VALUE;
		}
	}
	
	public int[][] getHValArray()
	{
		return this.hval;
	}
	
	public void setFP(int x, int y)
	{
		visited[x][y] = true;
		xCoord.add(x);
		yCoord.add(y);
	}
	
	public int noOfFalse()
	{
		int c = 0;
		for(int i=0; i<130;i++)
		{
			for(int j = 0; j<70;j++)
			{
				if(visited[i][j] != true)
					c++;
			}
		}
		return c;
	}
	
	public boolean check() 
	{
		total = total - leng2;
		//if(total != 60)
		//System.out.println(total+ " " + leng2 +" count="+count);
			//System.out.println(noOfFalse());
		//if(total == 60) return true;
		if(total == 0) return true;
		else return false;
	}
	
	public void clearAll()
	{
		xCoord.removeAll(xCoord);
		yCoord.removeAll(yCoord);
		iniArrays();
		leng = 1; leng2 = 0; total = 70*130-1-200;
	}
	
	public void printArray()
	{
		//PrintStream out = null;
		//System.setOut(out);
		for(int i=0; i<130;i++)
		{
			for(int j = 0; j<70;j++)
				System.out.print("(["+i+","+j +"]," + (hval[i][j] == -1 || hval[i][j] == Integer.MAX_VALUE)+")");
			System.out.println();
		}
	}
	
	public void createHVal(int cur)
	{
		leng2=0;
		for(int i = 0; i< leng; i++)
		{
			int x = xCoord.get(0), y = yCoord.get(0);
			
			//boundary check
			if(x == 0)
			{
				if(!visited[x+1][y])//right
				{
					hval[x+1][y] = cur;
					visited[x+1][y] = true;
					counter[x+1][y]++;
					xCoord.add(x+1);yCoord.add(y);leng2++;
				}
				if(y >= 0 && y < 69)
				{
					if(!visited[x][y+1])//down
					{
						hval[x][y+1] = cur;
						visited[x][y+1] = true;
						counter[x][y+1]++;
						xCoord.add(x);yCoord.add(y+1);leng2++;
					}
				}
				if(y > 0 && y <= 69)
				{
					if(!visited[x][y-1])//up
					{
						hval[x][y-1] = cur;
						visited[x][y-1] = true;
						counter[x][y-1]++;
						xCoord.add(x);yCoord.add(y-1);leng2++;
					}
				}
			}
			else if(x == 129)
			{
				if(!visited[x-1][y])//left
				{
					hval[x-1][y] = cur;
					visited[x-1][y] = true;
					counter[x-1][y]++;
					xCoord.add(x-1);yCoord.add(y);leng2++;
				}
				if(y >= 0 && y < 69)
				{
					if(!visited[x][y+1])//down
					{
						hval[x][y+1] = cur;
						visited[x][y+1] = true;
						counter[x][y+1]++;
						xCoord.add(x);yCoord.add(y+1);leng2++;
					}
				}
				if(y > 0 && y <= 69)
				{
					if(!visited[x][y-1])//up
					{
						hval[x][y-1] = cur;
						visited[x][y-1] = true;
						counter[x][y-1]++;
						xCoord.add(x);yCoord.add(y-1);leng2++;
					}
				}
			}
			else if(y==0)
			{
				if(!visited[x][y+1])//down
				{
					hval[x][y+1] = cur;
					visited[x][y+1] = true;
					counter[x][y+1]++;
					xCoord.add(x);yCoord.add(y+1);leng2++;
				}
				if(x > 0 && x<=129)
				{
					if(!visited[x-1][y])//left
					{
						hval[x-1][y] = cur;
						visited[x-1][y] = true;
						counter[x-1][y]++;
						xCoord.add(x-1);yCoord.add(y);leng2++;
					}
				}
				if(x >=0 && x< 129)
				{
					if(!visited[x+1][y])//right
					{
						hval[x+1][y] = cur;
						visited[x+1][y] = true;
						counter[x+1][y]++;
						xCoord.add(x+1);yCoord.add(y);leng2++;
					}
				}
			}
			else if(y==69)
			{
				if(!visited[x][y-1])//up
				{
					hval[x][y-1] = cur;
					visited[x][y-1] = true;
					counter[x][y-1]++;
					xCoord.add(x);yCoord.add(y-1);leng2++;
				}
				if(x > 0 && x<=129)
				{
					if(!visited[x-1][y])//left
					{
						hval[x-1][y] = cur;
						visited[x-1][y] = true;
						counter[x-1][y]++;
						xCoord.add(x-1);yCoord.add(y);leng2++;
					}
				}
				if(x >=0 && x< 129)
				{
					if(!visited[x+1][y])//right
					{
						hval[x+1][y] = cur;
						visited[x+1][y] = true;
						counter[x+1][y]++;
						xCoord.add(x+1);yCoord.add(y);leng2++;
					}
				}
			}
			else
			{
				if(!visited[x-1][y])//left
				{
					hval[x-1][y] = cur;
					visited[x-1][y] = true;
					counter[x-1][y]++;
					xCoord.add(x-1);yCoord.add(y);leng2++;
				}
				if(!visited[x+1][y])//right
				{
					hval[x+1][y] = cur;
					visited[x+1][y] = true;
					counter[x+1][y]++;
					xCoord.add(x+1);yCoord.add(y);leng2++;
				}
				if(!visited[x][y+1])//down
				{
					hval[x][y+1] = cur;
					visited[x][y+1] = true;
					counter[x][y+1]++;
					xCoord.add(x);yCoord.add(y+1);leng2++;
				}
				if(!visited[x][y-1])//up
				{
					hval[x][y-1] = cur;
					visited[x][y-1] = true;
					counter[x][y-1]++;
					xCoord.add(x);yCoord.add(y-1);leng2++;
				}
			}
			xCoord.remove(0); yCoord.remove(0);
		}
		leng = leng2;
		if(check())
		{
			//printArray();
			return;
		}
		else createHVal(cur+10);
		return;
	}
}
