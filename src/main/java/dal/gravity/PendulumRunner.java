package dal.gravity;

import java.text.NumberFormat;

/** 
 * compares the values of a simple pendulum using the harmonic motion equation
 * versus the Euler algorithm approximation
 */
public class PendulumRunner {

	/** gravitational constants */
    public static final double EARTH_GRAVITY = 9.80665;
    public static final double JUPITER_GRAVITY = 24.79;
	
    public static void main (String [] args) {
		NumberFormat nf = NumberFormat.getInstance ();
		nf.setMaximumFractionDigits (3);
	
		double delta = (args.length == 0) ? .1 : Double.parseDouble (args[0]);
		double sLen = 10, pMass = 10, theta0 = Math.PI/30;
		
		GravityModel modelEarth = new GravityConstant(EARTH_GRAVITY);
		GravityModel modelJupiter = new GravityConstant(JUPITER_GRAVITY);
		
		RegularPendulum rp = new RegularPendulum (sLen, pMass, theta0, modelEarth, delta);
		SimplePendulum sp = new SimplePendulum (sLen, pMass, theta0, modelEarth);
		RegularPendulum rpCoarse = 
		    new RegularPendulum (sLen, pMass, theta0, modelEarth, .1);
	
		
		// USING Earth's gravity 
		// print out difference in displacement in 1 second intervals
		// for 20 seconds
		int iterations = (int) (1/delta);
		System.out.println("Earth's gravitational field");
		System.out.println ("analytical vs. numerical displacement (fine, coarse)");
		for (int second = 1; second <= 20; second++) {
		    for (int i = 0; i < iterations; i++) rp.step ();
		    for (int i = 0; i < 10; i++) rpCoarse.step (); 
		    System.out.println ("t=" + second + "s: \t" + 
					nf.format (Math.toDegrees (sp.getTheta (second))) 
					+ "\t" +
					nf.format (Math.toDegrees (rp.getLastTheta ()))
					+ "\t" + 
					nf.format (Math.toDegrees (rpCoarse.getLastTheta ())));
		}
		
		// USING Jupiter's gravity 
		// print out difference in displacement in 1 second intervals
		// for 20 seconds
		iterations = (int) (1/delta);
		sp.setGravityModel(modelJupiter);
		rp.setGravityModel(modelJupiter);
		rpCoarse.setGravityModel(modelJupiter);
		
		System.out.println("\n\nJupiter's gravitational field");
		System.out.println ("analytical vs. numerical displacement (fine, coarse)");
		for (int second = 1; second <= 20; second++) {
		    for (int i = 0; i < iterations; i++) rp.step ();
		    for (int i = 0; i < 10; i++) rpCoarse.step (); 
		    System.out.println ("t=" + second + "s: \t" + 
					nf.format (Math.toDegrees (sp.getTheta (second))) 
					+ "\t" +
					nf.format (Math.toDegrees (rp.getLastTheta ()))
					+ "\t" + 
					nf.format (Math.toDegrees (rpCoarse.getLastTheta ())));
		}
    }
}

