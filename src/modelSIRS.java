/*	
	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	==============================================================
	--------------------------------------------------------------
	MODELLING AND VISUALISATION IN PHYSICS:
	CHECKPOINT 1

	This model contains all logic to follow the ising model using
	monte-carlo and metropolis algorithms. 

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
import java.io.*;


/*	
	==============================================================
	--------------------------------------------------------------
	CLASSES
	--------------------------------------------------------------
	==============================================================
*/
public class modelSIRS {

    /*	
		--------------------------------------------------------------
		ATTRIBUTES
		--------------------------------------------------------------
	*/

	public int[][] cells;
	private int size;
	public double p1;
	public double p2;
	public double p3;

    /*	
		--------------------------------------------------------------
		CONSTRUCTORS
		--------------------------------------------------------------
	*/	

	// Empty Contructor
	public modelSIRS() {

	}

	// Contructor that takes in the size of spin grid as an integer
	public modelSIRS(int size, double p1, double p2, double p3) {

		// Initialise constants and variables
		this.size = size;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.cells = new int[size][size];            			// the 2D array of cells

		// Randonly initialise spins (to either 0, 1 or 2)
		double rand;	
	    for (int i=0; i < size; i++) {
	        for (int j=0; j < size; j++) {
	            rand = Math.random();
	            if (rand < (1.0/3.0)) {
	            	cells[i][j] = 0;
	            }
	            else if (rand < (2.0/3.0)) {
	            	cells[i][j] = 1;
	            }
	            else {
	            	cells[i][j] = 2;
	            }
	        }
	    }

	}

    /*	
		--------------------------------------------------------------
		METHODS
		--------------------------------------------------------------
	*/

	// Method that updates the spin array using the outlined algorithms
	public void step() {
		
		for (int k=0; k<(this.size * this.size); k++) {
	 	   	// Pick a random row and column
			int i = (int) (Math.random() * this.size);
	    	int j = (int) (Math.random() * this.size);
			

	    	// If susceptible (0) then if a neighbour is infected (1) switch to infected with probability p1
	    	if (cells[i][j] == 0) {

	    		// initialise neighbour states
	    		int leftC;
	    		int rightC;
	    		int topC;
	    		int bottomC;
		    	// periodic boundary conditions
		        if (i == 0) leftC = this.cells[this.size-1][j]; else leftC = this.cells[i-1][j];
		        if (i == size-1) rightC = this.cells[0][j]; else rightC = this.cells[i+1][j];
		        if (j == 0) topC = this.cells[i][this.size-1]; else topC = this.cells[i][j-1];
		        if (j == size-1) bottomC = this.cells[i][0]; else bottomC = this.cells[i][j+1];

		        // if any infected then switch selected cell to infected with prob p1
		        if ((leftC == 1) || (rightC == 1) || (topC == 1) || (bottomC == 1)) {
		        	cells[i][j] = (Math.random() < this.p1) ? 1 : 0;
		        }

	    	}

	    	// Else if infected then switch to recovered with probability p2
		    else if (cells[i][j] == 1) {
		    	cells[i][j] = (Math.random() < this.p2) ? 2 : 1;
		    }

		    // Else recovered (2) so switch to susceptible with probability p3
		    else {
		    	cells[i][j] = (Math.random() < this.p3) ? 0 : 2;
		    }			
		}


    }

    // Method to count the total number of infected cells
    public int infected() {

    	int total = 0;

    	for (int i=0; i<this.size; i++) {
    		for (int j=0; j<this.size; j++) {
    			if (this.cells[i][j] == 1) {
    				total += 1;
    			}
    		}
    	}

    	return total;
    }

    // Method to calculate the avereage
    double average(double[] data) {

    	// Initialise variables
    	int length = data.length;
    	double total = 0.0;

    	// Calculate average
    	for (int i=0; i<length; i++) {
    		total += data[i];
    	}

    	return (total / (double)length);
    }

    // Method to calculate the average of squares
    double sqAverage(double[] data) {

		// Initialise variables
		int length = data.length;
		double sq_total = 0.0;

		// Calculate average
		for (int i=0; i<length; i++) {
			sq_total += Math.pow(data[i], 2.0);
		}

		return (sq_total / (double)length);
    }

    // Method to calculate jackknife error
    double jerror(double[] data, double constant) {

    	// Initialise variables
    	int length = data.length;
    	double total = 0.0;

    	// Calculate average
    	for (int i=0; i<length; i++) {
    		total += Math.pow(data[i] - constant, 2.0);
    	}

    	return Math.pow(total, 0.5);
    }

  //   public double jackknife(double[] data, String doing) {

  //   	// Initialise Variables
  //   	int length = data.length;
  //   	int k = 0;
  //   	double[] cut_data = new double[length-1];
  //   	double[] calc_data = new double[length];
  //   	double error = 0.0;
  //   	double av_sq = 0.0;
  //   	double sq_av = 0.0;
  //   	double calc = 0.0;

  //   	// Calculate sus or heatC for the whole data set //
  //   	av_sq = Math.pow(average(data), 2.0);
  //   	sq_av = sqAverage(data);    	
		// // If doing == "sus" then get sus
		// if (doing.equals("sus")) {
		// 	calc = susceptibility(sq_av, av_sq);
		// }
		// // Else if doing == "heatC" then get heatC
		// else if (doing.equals("heatC")) {
		// 	calc = heat_capacity(sq_av, av_sq);
		// }


  //   	// Loop for length of data
  //   	for (int i=0; i<length; i++) {

  //   		// Reset counter
  //   		k = 0;

  //   		// Loop for length of data
  //   		for (int j=0; j<length; j++) {

  //   			// If i != j then set value for cut_data from data
  //   			if (j != k) {
  //  	    			cut_data[k] = data[j]; 		
  //  	    			k += 1;		
  //   			}

  //   		}

  //   		// Get average squared and average of squares for cut_data
  //   		av_sq = Math.pow(average(cut_data), 2.0);
  //   		sq_av = sqAverage(cut_data);

		// 	// If doing == "sus" then get sus
  //   		if (doing.equals("sus")) {
  //   			calc_data[i] = susceptibility(sq_av, av_sq);
  //   		}
  //   		// Else if doing == "heatC" then get heatC
  //   		else if (doing.equals("heatC")) {
  //   			calc_data[i] = heat_capacity(sq_av, av_sq);
  //   		}

  //   	}

		// // Calculate error (if too small then truncate to zero)
		// error = jerror(calc_data, calc);

		// // Return error
		// return error;

  //   }
}
