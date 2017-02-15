//-----------------------------------------------------------------------------
// Sparse.java
// Gets text from input file and creates and writes Matrix to output file
// using Matrix ADT
//
// Yona Edell
// CruzID: yedell
// CMPS 101
// PA3
// 02/01/2017
//-----------------------------------------------------------------------------
import java.util.Scanner;
import java.io.*;

public class Sparse {
	public static void main(String[] args) throws IOException {
		// Check if two args go through
		if(args.length != 2) { 
			System.err.println("Usage: Sparse <infile> <outfile>");
			System.exit(1);
		}
		
		// Variables
		Scanner input = new Scanner(new File(args[0]));
		PrintWriter output = new PrintWriter(new File(args[1]));
		int size = Integer.parseInt(input.next()), aNNZ = Integer.parseInt(input.next()), bNNZ = Integer.parseInt(input.next());
		Matrix A = new Matrix(size);
		Matrix B = new Matrix(size);
		int k = 0;

		input.nextLine();

		while(k < aNNZ) {
			int i = Integer.parseInt(input.next()), j = Integer.parseInt(input.next());
		    double value = Double.parseDouble(input.next());
		    A.changeEntry(i, j, value);
		    input.nextLine();
		    k++;
		}

		input.nextLine();
		k = 0;

		while(k < bNNZ) {
			int i = Integer.parseInt(input.next()), j = Integer.parseInt(input.next());
		    double value = Double.parseDouble(input.next());
		    B.changeEntry(i, j, value);
		    input.nextLine();
		    k++;
		}

		output.println("A has " + A.getNNZ() + " non-zero entries:");
		output.println(A);

		output.println("B has " + B.getNNZ() + " non-zero entries:");
		output.println(B);
		
		output.println("(1.5)*A =");
		output.println(A.scalarMult(1.5));

		output.println("A+B =");
		output.println(A.add(B));

		output.println("A+A =");
		output.println(A.add(A));

		output.println("B-A =");
		output.println(B.sub(A));

		output.println("A-A =");
		output.println(A.sub(A));

		output.println("Transpose(A) =");
		output.println(A.transpose());

		output.println("A*B =");
		output.println(A.mult(B));

		output.println("B*B =");
		output.println(B.mult(B));

		input.close();
		output.close();
	}	
}
