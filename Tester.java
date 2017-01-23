package my.MCM;

import javax.swing.*;
import java.awt.*;
import java.util.*;

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
	    int numLanes = 12;
	    int carsPerLane = 14;
	    int carDimen = 6; //Car is a square
	    int frameDimen = 600;
	    ArrayList carList = new ArrayList();
	    
	    class Car  {
	    	private int X;
		    private int Y;
		    private int V; //Car velocity
		    private int laneNum;
		    
		    boolean up = false;
		    boolean down = true;
		    boolean left = false;
		    boolean right = true;
		    
		    Car(int x, int y){
		    	X = x; Y = y; V = 1;
		    }
		    
		    Car(int x, int y, int v){
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
	    	}
	    	for(int i = 0; i < numLanes; i++){
	    		for(int j = 0; j < carsPerLane; j++){
	    			Car tmpCar = new Car(7 + 20*j, 25+20*i);
		    		tmpCar.laneNum = i;
		    		((ArrayList)carList.get(i)).add(tmpCar);
	    		}
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
	        frame.setSize(frameDimen, frameDimen);
	        frame.setLocation(375, 55);
	        
	        this.start();

	    }

	    class DrawPanel extends JPanel {
	        public void paintComponent(Graphics g) {
	        	
	        	//Get the current size of this component
	            Dimension d = this.getSize();
	        
	            g.setColor(Color.BLUE);
	            g.fillRect(0, 0, this.getWidth(), this.getHeight());
	            g.setColor(Color.RED);
	            g.fillRect(3, 3, this.getWidth()-carDimen, this.getHeight()-carDimen);
	            g.setColor(Color.WHITE);
	            g.fillRect(6, 6, this.getWidth()-12, this.getHeight()-12);
	            g.setColor(Color.BLACK);
	            
	            //draw a centered horizontal line
	            g.drawLine(this.getWidth()/2,0,this.getWidth()/2,this.getHeight());
	            g.drawLine(this.getWidth()/2 + 50,0,this.getWidth()/2 + 50,this.getHeight());
	            g.drawLine(this.getWidth()/2 - 50,0,this.getWidth()/2 - 50,this.getHeight());
	            
	            
	            for(int i = 0; i < numLanes; i++){
		    		for(int j = 0; j < carsPerLane; j++){
		    			Car tmpCar = ((Car)((ArrayList)carList.get(i)).get(j));
		    			g.fillRect(tmpCar.X, tmpCar.Y, carDimen, carDimen);
		    		}
		    	}
	        }
	    }

	    private void moveIt(ArrayList carList) {
	        while(true){
	        	for(int i = 0; i < numLanes; i++){
	        		for(int j = 0; j < ((ArrayList)carList.get(i)).size(); j++){
	        			Car car = ((Car)((ArrayList)carList.get(i)).get(j));
			            if( car.X >= (frame.getWidth()-17)){
			            	car.X = 0;
			            	car.Y = 25+20*car.laneNum;
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
			                car.Y--;
			            }
			            if(down){
			                car.Y++;
			            }*/
			            if(car.left){
			                car.X--;
			            }
			            if(car.right){
			            	car.X++;
			            }
	        		}    
	        	}
	            try{
	                Thread.sleep(15);
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