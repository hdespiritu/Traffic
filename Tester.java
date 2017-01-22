package my.MCM;

import javax.swing.*;
import java.awt.*;

/*
 * TODO:
 * 
 * function for merging two cars
 * stop bouncing off walls
 * Add traffic.java code 
 * 
 */


class TollPlaza {
	 	JFrame frame;
	    DrawPanel drawPanel;
	    int numLanes = 12;
	    int carsPerLane = 4;
	    Car[] carArr = new Car[numLanes*carsPerLane];
	    
	    class Car extends Thread {
	    	private int X;
		    private int Y;
		    
		    boolean up = false;
		    boolean down = true;
		    boolean left = false;
		    boolean right = true;
		    
		    Car(int x, int y){
		    	X = x; Y = y;
		    }
		    
		    @Override
		    public void run(){
		    	moveIt(this);
		    }
	    }
	    
	    
	    
	    TollPlaza(){
	    	
	    	for(int i= 0; i < numLanes; i++){
	    		for(int j = 0; j < carsPerLane; j++)
	    		carArr[i*4+j] = new Car(7 + 20*j, 25+20*i);
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
	        frame.setSize(300, 300);
	        frame.setLocation(375, 55);
	        
	        for(int i = 0; i < numLanes*carsPerLane; i++){
	        	carArr[i].start();
	        }

	    }

	    class DrawPanel extends JPanel {
	        public void paintComponent(Graphics g) {
	        	
	        	//Get the current size of this component
	            Dimension d = this.getSize();
	        	//draw in black
	            g.setColor(Color.BLACK);
	            //draw a centered horizontal line
	            
	            
	            g.setColor(Color.BLUE);
	            g.fillRect(0, 0, this.getWidth(), this.getHeight());
	            g.setColor(Color.RED);
	            g.fillRect(3, 3, this.getWidth()-6, this.getHeight()-6);
	            g.setColor(Color.WHITE);
	            g.fillRect(6, 6, this.getWidth()-12, this.getHeight()-12);
	            g.setColor(Color.BLACK);
	            
	            g.drawLine(this.getWidth()/2,0,this.getWidth()/2,this.getHeight());
	            
	            
	            for(int i = 0; i < numLanes*carsPerLane; i++){
	            	g.fillRect(carArr[i].X, carArr[i].Y, 6, 6);
	            }
	            
	        }
	    }

	    private void moveIt(Car car) {
	        while(true){
	            if(car.X >= 283){
	                car.right = false;
	                car.left = true;
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
	            try{
	                Thread.sleep(10);
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