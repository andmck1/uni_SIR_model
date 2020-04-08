/*	
	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	==============================================================
	--------------------------------------------------------------
	MODELLING AND VISUALISATION IN PHYSICS:
	CHECKPOINT 1

	This class simulates the ising model using either Glauber or
	Kawasaki dynamics. The program will display a window showing
	a visual representation in a pixel format of spin where purple
	is "up" and white is "down".


	TERMINAL INPUT: Ising.java [size] [temperature: inclusive 0.1 - 100] [dynamics: 'Glauber' OR 'Kawasaki']

	@author	:	Alastair Hamilton 
				(inspired by work from Daniel V. Schroeder of 
				Weber State University: http://physics.weber.edu/thermal/isingJava.html)

	@date	: 	25/01/16

	--------------------------------------------------------------
	==============================================================
	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*/


/*	
	==============================================================
	--------------------------------------------------------------
	IMPORTS
	--------------------------------------------------------------
	==============================================================
*/


/*	
	==============================================================
	--------------------------------------------------------------
	CLASSES
	--------------------------------------------------------------
	==============================================================
*/

public class mainSIRS {

	public static void main(final String[] args) throws Exception  {

		// Get arguments and error check
		if (args.length != 6) throw new Exception("Arguments: size[length in pixels] time_res[int] p1[0.-1.] p2[0.-1.] p3[0.-1.] doing[plot OR visualise]");
		final int size = Integer.parseInt(args[0]);
		final int time_res = Integer.parseInt(args[1]);
		final double p1 = Float.parseFloat(args[2]);
		final double p2 = Float.parseFloat(args[3]);
		final double p3 = Float.parseFloat(args[4]);
		final String doing = args[5];

		// Run Ising model
		runSIRS mainRunSIRS = new runSIRS(size, time_res, p1, p2, p3, doing);

	}
}