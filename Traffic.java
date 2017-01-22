package my.MCM;
import java.text.*;

/*
 * Java for Scientists and Engineers Ch11.7 Traffic Flow p236
 * 
 * http://160592857366.free.fr/joe/ebooks/Electronics%20and%20Electrical%20Engineering%20Collection/HAHN,%20B.%20D.%20(2002).%20Essential%20Java%20for%20Scientists%20and%20Engineers/Essential_Java_for_Scientists_and_Engineers.pdf
 * 
 * 
 * In this example we look at a very small part of the problem: how to simulate the flow of a 
 * single line of traffic through one set of traffic lights.We make the following assumptions 
 * (you can make additional or different ones if like):
 * 
 * 	1. Traffic travels straight, without turning.
 * 	2. The probability of a car arriving at the lights in a particular second is independent of what happened
 * 		during the previous second. This is called a Poisson process. This probability (call it p) may be
 * 		estimated by watching cars at the intersection and monitoring their arrival pattern. In this simulation
 * 		we take p = 0.3.
 * 	3. When the lights are green, assume the cars move through at a steady rate of, say, eight every ten
 * 		seconds.
 * 	4. In the simulation, we will take the basic time period to be ten seconds, so we want a display showing
 * 		the length of the queue of traffic (if any) at the lights every ten seconds.
 * 	5. We will set the lights red or green for variable multiples of ten seconds
 * 
 * 
 * 
 */

public class Traffic {

	private int cars = 0; //number of cars in queue
	private int greenFor; //period lights are green
	int greenTimer = 0; //timer for green lights
	private String lights = "R"; //colour of lights
	private double probOfCar; //probability of a car arriving
	private int redFor; // period lights are red
	int redTimer = 0; //timer for red lights
	
	public Traffic( double p, int gF, int rF )
	{
	probOfCar = p;
	greenFor = gF;
	redFor = rF;
	}
	
	public void displayQueue()
	{
	System.out.print( " " + lights + " " );
	for (int i = 1; i <= cars; i++) // display * for each car
	System.out.print( "*" );
	System.out.println(); // new line
	}
	public void go()
	{
	greenTimer++; //advance green timer
	cars -= 8; // let 8 cars through
	if (cars < 0) // ... there may have been < 8
	cars = 0;
	displayQueue();
	if (greenTimer == greenFor) // check if lights need to change
	{
	lights = "R";
	greenTimer = 0;
	}
	}
	public void stop()
	{
	redTimer++; //advance red timer
	displayQueue();
	if (redTimer == redFor) // check if lights need to change
	{
	lights = "G";
	redTimer = 0;
	}
	}
	
	public void carsArriveFor( int numberOfPeriods )
	{
	//for each 10-sec period:
	for (int period = 1; period <= numberOfPeriods; period++)
	{
		for (int second = 1; second <= 10; second++)
			if (Math.random() < probOfCar)
				cars++; //cars arriving in 10 seconds
		DecimalFormat df = new DecimalFormat( "00" );
		System.out.print( df.format(period) );
		if (lights.equals("G"))
			go();
		else
			stop();
	}
	}
	
	public static void main(String [] args)
	{
	Traffic mainRd = new Traffic( 0.3, 2, 4 );
	mainRd.carsArriveFor( 24 );
	}

}
