package my.MCM;

import javax.swing.*;

import my.MCM.TollPlaza.Car;

import java.awt.*;
import java.awt.List;
import java.util.*;

/*
 * TODO:
 * 
 * toll booth object
 * 
 * function for merging two cars
 * Add traffic.java code 
 * 
 */


import java.math.*;


/*
 * TODO:
 * 
 * function for merging two cars
 * stop bouncing off walls
 * Add traffic.java code 
 * 
 */


class TollPlaza extends Thread {
	 	JFrame frame;
	    DrawPanel drawPanel;
	    int numLanes = 10;
	    int carsPerLane = 24;
	    int carDimen = 6; //Car is a square
	    int tollBoothDimen = 15;
	    int timePerFrame = 15;
	    
	    ArrayList<ArrayList> carList = new ArrayList<ArrayList>();
	    ArrayList<ArrayList> tollBoothList = new ArrayList<ArrayList>();
        public static final int YMULT = 100;
	    public static final int XMULT = 7;
	    public static final int frameLength = 500;
	    public static final int frameWidth = 500;
	    
	    class TollBooth {
	    	private int X;
	    	private int Y;
	    	private int lambda;
	    	
	    	
	    	private double getWaitTime(){
	    		return 3.0;//Math.log(1-Math.random())/(-lambda);
	    	}
	    	
	    	TollBooth(int x, int y, int l){
	    		X = x; Y = y; lambda = l;
	    	}
	    }
	    
	    class Car  {
	    	private int X;
		    private int Y;
		    private double V; //Car velocity
		    private boolean paid;
		    private int laneNum;
		    
		    boolean up = false;
		    boolean down = true;
		    boolean left = false;
		    boolean right = true;
		    
		    Car(int x, int y){
		    	X = x; Y = y; V = 1.0/timePerFrame;
		    }
		    
		    Car(int x, int y, double v){
		    	X = x; Y = y; V = v;
		    } 
	    }
	    
	    @Override
	    public void run(){
	    		moveIt(carList);
	    }
	    
	    TollPlaza(){
	    	
	    	for(int i = 0; i < numLanes; i++){
	    		carList.add(new ArrayList());
	    		tollBoothList.add(new ArrayList());
	    	}
	    	for(int i = 0; i < numLanes; i++){
	    		for(int j = 0; j < carsPerLane; j++){
	    			
	    			Car tmpCar = new Car(XMULT + 20*j, YMULT+20*i, 2.0/timePerFrame);
		    		tmpCar.laneNum = i;
		    		((ArrayList<Car>)carList.get(i)).add(tmpCar);	
	    		}
	    		
	    		TollBooth tmpToll1 = new TollBooth(frameWidth/2,  YMULT+20*i,3);
	    		TollBooth tmpToll2 = new TollBooth(frameWidth/2-50,  YMULT+20*i,3);
	    		TollBooth tmpToll3 = new TollBooth(frameWidth/2+50,  YMULT+20*i,3);
	    		
	    		tollBoothList.get(i).add(tmpToll1);
	    		tollBoothList.get(i).add(tmpToll2);
	    		tollBoothList.get(i).add(tmpToll3);
	    		
	    	}
	    }
	    
	    public void merge(){
	    	
	    }
	    
	    public void go() {
	        frame = new JFrame("Test");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        drawPanel = new DrawPanel();

	        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);

	        frame.setVisible(true);
	        frame.setResizable(false);
	        frame.setSize(500, 500);
	        frame.setLocation(375, 55);
	        
	        this.start();

	    }

	    class DrawPanel extends JPanel {
	        public void paintComponent(Graphics g) {
	        	
	        	//Get the current size of this component
	            Dimension d = this.getSize();
	        	//draw in black
	            g.setColor(Color.BLACK);
	            //draw a centered horizontal line
	            
	            g.setColor(Color.GREEN);
	            g.fillRect(0, 0, this.getWidth(), this.getHeight());
	            g.setColor(Color.ORANGE);
	            g.fillRect(3, 3, this.getWidth()-carDimen, this.getHeight()-carDimen);
	            g.setColor(Color.WHITE);
	            g.fillRect(6, 6, this.getWidth()-12, this.getHeight()-12);
	            g.setColor(Color.GRAY);
	            
	            for(int i = 0; i < carList.size(); i++){
		    		for(int j = 0; j < ((ArrayList)tollBoothList.get(i)).size(); j++){
		    			
		    			TollBooth tmpTollBooth = ((TollBooth) ((ArrayList)tollBoothList.get(i)).get(j));
		    			g.fillRect(tmpTollBooth.X-8, tmpTollBooth.Y-3, tollBoothDimen, tollBoothDimen);
		    			
		    		}
		    	}
	            
	            
	            g.setColor(Color.BLACK);
	            g.drawLine(this.getWidth()/2,0,this.getWidth()/2,this.getHeight());
                g.drawLine(this.getWidth()/2+50,0,this.getWidth()/2+50,this.getHeight());
                g.drawLine(this.getWidth()/2-50,0,this.getWidth()/2-50,this.getHeight());
                    
	            for(int i = 0; i < carList.size(); i++){
		    		for(int j = 0; j < ((ArrayList)carList.get(i)).size(); j++){
		    			Car tmpCar = ((Car)((ArrayList)carList.get(i)).get(j));
		    			g.fillRect(tmpCar.X, tmpCar.Y, carDimen, carDimen);
		    		}
		    	}
	        }
	    }

	    private void moveIt(ArrayList<ArrayList> carList) {
	    	ArrayList deleted = new ArrayList();
	        while(true){
	        	for(int i = 0; i < numLanes; i++){
	        		final int numCarsLane = ((ArrayList)carList.get(i)).size();
	        		for(int j = 0; j < numCarsLane; j++){
	        			ArrayList lane = (ArrayList)carList.get(i);
	        			Car car = (Car)lane.get(j);
	        			
	        			
			            if( car.X >= 480){
			            	car.X = -1;
			            	car.Y = -1;
			            	
			            	deleted.add(new int[]{i,j});
			            	
			            }
			            if(car.X <= 7){
			                car.right = true;
			                car.left = false;
			            }
			            if(car.Y >= 259){
			                car.up = true;
			                car.down = false;
			            }
			            if(car.Y <= 7){
			                car.up = false;
			                car.down = true;
			            }/*
			            if(up){
			                car.Y -= car.V*timePerFrame;
			            }
			            if(down){
			                car.Y += car.V*timePerFrame;
			            }*/
			            if(car.left){
			            	car.X -= car.V*timePerFrame;
			            }
			            if(car.right){
			            	car.X += car.V*timePerFrame;
			            }
	        		}    
	        	}
	        	for(int i= 0; i < deleted.size(); i++){
	        		int[] pair = (int[])deleted.get(i);
	        		ArrayList lane = (ArrayList)carList.get(pair[0]);
        			lane.set(pair[1], new Car(-1,-1));
	        		carList.set(pair[0],lane);
	        	}
	        		
	            try{
	                Thread.sleep(timePerFrame);
	            } catch (Exception exc){}
	            frame.repaint();
	        }
	    }
}

final public class Tester {

	
    public static void main(String[] args) {
        
        new TollPlaza().go();
    }

    
}