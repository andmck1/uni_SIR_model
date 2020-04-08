/*	
	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	==============================================================
	--------------------------------------------------------------
	MODELLING AND VISUALISATION IN PHYSICS:
	CHECKPOINT 1

	@author	:	Alastair Hamilton 

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
import java.io.*;

/*	
	==============================================================
	--------------------------------------------------------------
	CLASSES
	--------------------------------------------------------------
	==============================================================
*/

public class runSIRS {

	public runSIRS(int size, int time_res, double p1, double p2, double p3, String doing) {

		// ----------------------------------------------
		/*	INITIALISE CONSTANTS & VARIABLES */
		final double PROB_RES = 0.05;
		final int EQUIL = 100;
		final int LONG_WAIT = 1000 + EQUIL;
		final double P2_PROB = 0.5;
		final int PLOT_RES = 1;
		final int SIZE = size;
		final double POINTS = (LONG_WAIT - EQUIL) / PLOT_RES;
		final int AXIS_SIZE = (int) (1.0/PROB_RES) + 1;
		final int VAR_POINTS = 5;

		// ----------------------------------------------
		/*	VISUALISATION */
		if (doing.equals("visualise")) {

			// Initialise view
			visualisation view = new visualisation(size, size);

			// Initialise single simulation to visualise
			modelSIRS sirs = new modelSIRS(size, p1, p2, p3);
			for (int i = 0; true; i++) {

				sirs.step();
				if ((i%time_res) == 0) {

					view.visualise(sirs.cells);
				}
			}
		}

		// ----------------------------------------------
		/*	PLOTTING */
		// ----------------------------------------------

		if (doing.equals("plot")) {

			/* LOCAL VARIABLES AND CLASSES*/
			double var_inf = 0.0;
			double currAv_inf = 0.0;
			double currSqav_inf = 0.0;
			double av_sq = 0.0;
			double sq_av =0.0;
			double prob1 = 0.0;
			double prob3 = 0.0;
			modelSIRS sirs;

			/*	SET UP WRITING TO FILE */
			// Initialise writer object for writing to file
			PrintWriter writer_var = null;

			// If no errors opening file then set writers, else exit
			try {
				writer_var = new PrintWriter("var_infsites.dat");
			} catch (FileNotFoundException e) {
				System.out.printf("FAILED TO WRITE TO FILE\n");
				System.exit(1);
			}	

			/* WRITE AVERAGE NUMBER OF INFECTED TO FILE FOR ALL p1 AND p2 */

			// For all p1 and p3
			for (int i=0; i<AXIS_SIZE; i++) {

				// (Re)initialise model
				sirs = new modelSIRS(size, prob1, P2_PROB, prob3);
				for (int j=0; j<AXIS_SIZE; j++) {

					// Print location on screen
					System.out.printf("p1: %.2f p3: %.2f\n", prob1, prob3);

					// Reset values
					var_inf = 0.0;

					for (int l=0; l<VAR_POINTS; l++) {

						// (Re)initialise model
						sirs = new modelSIRS(size, prob1, P2_PROB, prob3);
						
						// Reset averages
						currAv_inf = 0.0;
						currSqav_inf = 0.0;

						// For all steps up until LONG_WAIT
						for (int k=0; k<LONG_WAIT; k++) {

							// If absorbing state then break
							if (sirs.infected() == 0) {
								break;
							}

							// Step model
							sirs.step();

							// If past equilibrium and on a sweep to plot on
							if ((k >= EQUIL) && (k%PLOT_RES == 0)) {

								// Add number of infected to av_inf and infected squared to avsq_inf
								currAv_inf += (double)sirs.infected();
								currSqav_inf += Math.pow((double)sirs.infected(),2.0);
							}

						}
						
						// Average out totals
						currSqav_inf = currSqav_inf / ((double)POINTS);
						currAv_inf = currAv_inf / ((double)(POINTS));				

						// Variance
						av_sq = currAv_inf * currAv_inf;
						sq_av = currSqav_inf;
						var_inf += (sq_av - av_sq)/((double)(SIZE * SIZE * SIZE * SIZE));	//(<I^2> - <I>^2)/N	
					}

					// Average out variance
					var_inf /= VAR_POINTS;

					// Write point to files
					writer_var.printf("%.5f %.5f %.5f\n",(double)(i*PROB_RES), (double)(j*PROB_RES), var_inf);
					
					// Increment p3
					prob3 += PROB_RES;
				}

				// Increment p1
				prob1 += PROB_RES;

				// Reset p3
				prob3 = 0.0;

				// Format file for plotting
				writer_var.printf("\n");
			}

			// Close file
			writer_var.close();

		 }
		
	}
}