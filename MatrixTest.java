//-----------------------------------------------------------------------------
// MatrixTest.java
// A file for testing Matrix ADT functions
//
// Yona Edell
// CruzID: yedell
// CMPS 101
// PA3
// 02/01/2017
//-----------------------------------------------------------------------------

public class MatrixTest {
	public static void main(String[] args){
		int n = 100000;
		Matrix A = new Matrix(n);
		Matrix B = new Matrix(n);

		A.changeEntry(3, 2, 8);
		B.changeEntry(2, 2, -1);
		A.changeEntry(2, 3, 6);
		B.changeEntry(2, 3, 0);
		A.changeEntry(2, 2, 5);
		A.changeEntry(1, 3, 3);
		B.changeEntry(1, 3, -1);
		A.changeEntry(2, 1, 4);
		B.changeEntry(2, 1, 0);
		A.changeEntry(1, 2, 2);
		B.changeEntry(1, 2, 0);
		A.changeEntry(3, 1, 7);
		B.changeEntry(3, 1, -2);
		A.changeEntry(3, 3, 9);
		B.changeEntry(3, 3, -4);
		B.changeEntry(3, 2, -3);
		A.changeEntry(1, 1, 1);
		B.changeEntry(1, 1, 1);
		
		System.out.println("Matrix A non-zero entries: " + A.getNNZ());
		System.out.println("Matrix A created from column-unordered changeEntry:");
		System.out.println(A);
		
		System.out.println("Matrix B non-zero entries: " + B.getNNZ());
		System.out.println("Matrix B created from column-unordered changeEntry:");
		System.out.println(B);

		System.out.println("Clearing A & B\n");
		A.makeZero();
		B.makeZero();
		
		A.changeEntry(1,1,1); B.changeEntry(1,1,1);
	    A.changeEntry(1,2,2); B.changeEntry(1,2,0);
	    A.changeEntry(1,3,3); B.changeEntry(1,3,-1);
	    A.changeEntry(2,1,4); B.changeEntry(2,1,0);
	    A.changeEntry(2,2,5); B.changeEntry(2,2,-1);
	    A.changeEntry(2,3,6); B.changeEntry(2,3,0);
	    A.changeEntry(3,1,7); B.changeEntry(3,1,-2);
	    A.changeEntry(3,2,8); B.changeEntry(3,2,-3);
	    A.changeEntry(3,3,9); B.changeEntry(3,3,-4);
	    
	    System.out.println("Matrix A non-zero entries: " + A.getNNZ());
	    System.out.println("Matrix A size: " + A.getSize());
		System.out.println("Matrix A created from column-ordered changeEntry:");
		System.out.println(A);
		
		System.out.println("Matrix B non-zero entries: " + B.getNNZ());
		System.out.println("Matrix B size: " + B.getSize());
		System.out.println("Matrix B created from column-ordered changeEntry:");
		System.out.println(B);
		
		Matrix C = B.copy();
		System.out.println("C = B.copy():");
		System.out.println("Matrix C non-zero entries: " + C.getNNZ());
		System.out.println(C);

		System.out.println("A*(-5.5):");
		System.out.println(A.scalarMult(-5.5));
		
		System.out.println("B*0:");
		System.out.println(B.scalarMult(0));
		
		System.out.println("A+C:");
		System.out.println(A.add(C));
		
		System.out.println("C+A:");
		System.out.println(C.add(A));

		System.out.println("A-C:");
		System.out.println(A.sub(C));
		
		System.out.println("C-A:");
		System.out.println(C.sub(A));
		
		System.out.println("C-C:");
		System.out.println(C.sub(C));

		System.out.println("Transpose(C-A):");
		System.out.println((C.sub(A)).transpose());
		
		System.out.println("Transpose(Transpose(C-A)):");
		System.out.println(((C.sub(A)).transpose()).transpose());

		System.out.println("A Matrix:");
		System.out.println(A);
		
		System.out.println("C Matrix:");
		System.out.println(C);
		
		System.out.println("A*C:");
		System.out.println(A.mult(C));
		
		Matrix H = new Matrix(n);
		H.changeEntry(1,1,2);
		H.changeEntry(1,2,1);
		H.changeEntry(1,3,1);
		H.changeEntry(2,1,3);
		H.changeEntry(2,2,2);
		H.changeEntry(2,3,1);
		H.changeEntry(3,1,2);
		H.changeEntry(3,2,1);
		H.changeEntry(3,3,2);
		
		System.out.println("Matrix H:");
		System.out.println(H);
		
		Matrix I = new Matrix(n);
		I.changeEntry(1,1,3);
		I.changeEntry(1,2,-1);
		I.changeEntry(1,3,-1);
		I.changeEntry(2,1,-4);
		I.changeEntry(2,2,2);
		I.changeEntry(2,3,1);
		I.changeEntry(3,1,-1);
		I.changeEntry(3,2,0);
		I.changeEntry(3,3,1);
		
		System.out.println("Matrix I:");
		System.out.println(I);
		
		System.out.println("H*I: -> H * Inverse = Identity Matrix");
		System.out.println(H.mult(I));

		System.out.print("A == A.copy(): 		");
		System.out.println(A.equals(A.copy()));
		
		System.out.print("A.copy() == A: 		");
		System.out.println(A.copy().equals(A));
		
		System.out.print("A == A: 		");
		System.out.println(A.equals(A));
		
		System.out.print("A.copy() == A.copy(): 	");
		System.out.println(A.copy().equals(A.copy()));

		System.out.print("A == B: 		");
		System.out.println(A.equals(B));
		
		System.out.print("B == A: 		");
		System.out.println(B.equals(A) +"\n");

		System.out.println("After calling A.makeZero(): ");
		A.makeZero();
		System.out.println(A);
	}
}
