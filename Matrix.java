//-----------------------------------------------------------------------------
// Matrix.java
// Implementation of Matrix ADT
//
// Yona Edell
// CruzID: yedell
// CMPS 101
// PA3
// 02/01/2017
//-----------------------------------------------------------------------------

public class Matrix {
	
	// Entry Class Definition
	private class Entry {
		int col; // The column of the Entry
		double value; // The value/data of the Entry
		
		Entry(int c, double v) {
			col = c;
			value = v;
		}
		
		public boolean equals(Object x) {
			boolean eq = false;
			Entry that;
			
			if(x instanceof Entry) {
				that = (Entry) x;
				eq = (value == that.value && col == that.col);
			}
			return eq;
		}
		
		public String toString() {
			return "(" + col + ", " + value + ")";
		}
	}
	
	// Fields
	private int n; // number of rows and columns
	private List [] row; // array is rows of lists of entries
	private int NNZ; // number of non-zero entries
	
	// Constructor
	Matrix(int n) { // Makes a new n x n zero Matrix. pre: n>=1
		if(n < 1) {
			throw new RuntimeException("Matrix Error: cannot construct Matrix with \"" + n + "\" rows/columns");
		}
		this.n = n;
		row = new List[n+1];
		for(int i = 1; i < n+1; i++) {
			row[i] = new List();
		}
	}
	
	// Access functions--------------------------------------------------------------------------------
	
	int getSize() { // Returns n, the number of rows and columns of this Matrix
		return n;
	}
	
	int getNNZ() { // Returns the number of non-zero entries in this Matrix
		return NNZ;
	}
	
	public boolean equals(Object x) { // overrides Object's equals() method

//		Matrix b;
//		
//		if(x instanceof Matrix) {
//			b = (Matrix) x;
//			if(getSize() == b.getSize() && NNZ == b.getNNZ()) { // Compares sizes and NNZs
//				for(int i = 1; i <= getSize(); i++) { // Loops through rows i.e. lists in row[i]
//					if(!(row[i].equals(b.row[i]))) { // Compares row[i] List using List's equals() method
//						return false;
//					}
//				}
//				return true;
//			}
//		}
//		return false;
		
		Matrix b;
		Entry aEntry = null, bEntry = null;
		
		if(x instanceof Matrix) {
			b = (Matrix) x;
			if(getSize() == b.getSize() && NNZ == b.getNNZ()) { // Compares sizes and NNZs
				for(int i = 1; i <= getSize(); i++) { // Loops through rows i.e. lists in row[i]
					row[i].moveFront(); // Move cursor to front of both Lists
					b.row[i].moveFront();
					while(row[i].index() != -1 || b.row[i].index() != -1) { // Compares row[i] Lists
						aEntry = (Entry) row[i].get();
						bEntry = (Entry) b.row[i].get();
						if(aEntry.value != bEntry.value) { // If value of any Entries are unequal, returns false
							return false;
						}
						row[i].moveNext();
						b.row[i].moveNext();
					}
				}
				return true;
			}
		}
		return false;
	} 
	
	// Manipulation procedures-------------------------------------------------------------------------
	
	void makeZero() { // sets this Matrix to the zero state
		for(int i = 1; i <= getSize(); i++) {
			row[i].clear();
		}
		NNZ = 0;
	}
	
	Matrix copy() { // returns a new Matrix having the same entries as this Matrix
		Matrix copy = new Matrix(getSize());
		Entry temp = null;
		
		for(int i = 1; i <= getSize(); i++) { // Loops through rows in row[i]
			row[i].moveFront(); // Moves cursor to front of list
			while(row[i].index() != -1) { // Loops through entries in row[i]
				temp = (Entry) row[i].get();
				copy.changeEntry(i, temp.col, temp.value); // Adding current entry to copy
				row[i].moveNext();
			}
		}
		
		return copy;
	} 
	
	void changeEntry(int i, int j, double x) { // changes ith row, jth column of this Matrix to x
		if(i < 1 || i > getSize()) 
			throw new RuntimeException("Matrix Error: calling \"changeEntry()\" with invalid ith row");
		if(j < 1 || j > getSize()) 
			throw new RuntimeException("Matrix Error: calling \"changeEntry()\" with invalid jth column");
		
		row[i].moveFront(); // Move cursor to front of row List
		Entry temp = null;
		
		while (row[i].index() != -1) { // Traverse every column/entry in row[i]
			temp = (Entry) row[i].get();
			if (temp.col == j) { // If temp column/entry equals j
				if (x == 0) { // If x is zero, delete the column/entry
					row[i].delete();
					NNZ--;
					return;
				} else { // If x is nonzero, insert before temp column/entry and delete
					row[i].insertBefore(new Entry(j, x));
					row[i].delete();
					return;
				}
			} 
			else if (j < temp.col && x != 0) { // If j < temp column/entry and x is nonzero, insert before
				row[i].insertBefore(new Entry(j, x));
				NNZ++;
				return;
			}
			row[i].moveNext(); // Advance cursor to next column/entry
		}

		if (row[i].index() == -1 && x != 0) { // If cursor falls off row[i] and x is nonzero, append
			row[i].append(new Entry(j, x));
			NNZ++;
		}
	}
	 
	Matrix scalarMult(double x) { // returns a new Matrix that is the scalar product of this Matrix with x
		Matrix sMult = new Matrix(getSize());
		Entry temp = null;
		
		for(int i = 1; i <= getSize(); i++) { // Loops through rows in row[i]
			row[i].moveFront(); // Moves cursor to front of list
			while(row[i].index() != -1) { // Loops through entries in row[i] list
				temp = (Entry) row[i].get();
				sMult.changeEntry(i, temp.col, x*temp.value); // Stores new entry in sMult
				row[i].moveNext(); // Advance to next entry
			}
		}
		
		return sMult;
	}
	
	Matrix add(Matrix M) { // returns a new Matrix that is the sum of this Matrix with M
		if (getSize() != M.getSize()) {
			throw new RuntimeException("Matrix Error: calling \"add()\" on matrices with different sizes/dimensions");
		}
		
		Matrix result = new Matrix(getSize());
		
		for (int i = 1; i <= getSize(); i++) {
			List A = row[i];
			List B = M.row[i];
			
			if(A != M.row[i]) {
						
				A.moveFront();
				B.moveFront();

				while (A.index() != -1 || B.index() != -1) { // Loop through until index on one row falls off
					Entry aEntry = null;
					Entry bEntry = null;
					if (A.index() != -1) {
						aEntry = (Entry) A.get();
					}
					if (B.index() != -1) {
						bEntry = (Entry) B.get();
					}
					if (bEntry == null || aEntry != null && aEntry.col < bEntry.col) { // aEntry column < bEntry column
						result.changeEntry(i, aEntry.col, aEntry.value); // If aEntry column less, then bEntry must have a zero entry in place.
						A.moveNext();									 // So we store aEntry value - 0, which is just aEntry value
					} else if (aEntry == null || bEntry != null && bEntry.col < aEntry.col) { // If aEntry column greater, then it must have a zero entry in place
						result.changeEntry(i, bEntry.col, bEntry.value);					  // so we must store bEntry value
						B.moveNext();
					} else {
						result.changeEntry(i, aEntry.col, aEntry.value + bEntry.value); // aEntry column == bEntry column, add normally
						A.moveNext();
						B.moveNext();
					}
				}
			}
			else { // If A == M.row[i]
				for(A.moveFront(); A.index() != -1; A.moveNext()) {
					Entry temp = (Entry) A.get();
					result.changeEntry(i, temp.col, temp.value * 2);
				}
			}
        }
        return result;
	}
	 
	Matrix sub(Matrix M) { // returns a new Matrix that is the difference of this Matrix with M
		if (getSize() != M.getSize()) {
			throw new RuntimeException("Matrix Error: calling \"sub()\" on matrices with different sizes/dimensions");
		}
		Matrix result = new Matrix(getSize());
		
		if(this == M) {
			return new Matrix(getSize());
		}
		
		for (int i = 1; i <= getSize(); i++) {
			List A = row[i];
			List B = M.row[i];
			A.moveFront();
			B.moveFront();

			while (A.index() != -1 || B.index() != -1) { // Loop through until index on one row falls off
				Entry aEntry = null, bEntry = null;
				if (A.index() != -1) {
					aEntry = (Entry) A.get();
				}
				if (B.index() != -1) {
					bEntry = (Entry) B.get();
				}
				if (bEntry == null || aEntry != null && aEntry.col < bEntry.col) { // aEntry column < bEntry column
					result.changeEntry(i, aEntry.col, aEntry.value); // If aEntry column less, then bEntry must have a zero entry in place.
					A.moveNext();									 // So we store aEntry value - 0, which is just aEntry value
				} else if (aEntry == null || bEntry != null && aEntry.col > bEntry.col) { // aEntry column > bEntry column
					result.changeEntry(i, bEntry.col, -1 * bEntry.value); // If aEntry column greater, then it must have a zero entry in place
					B.moveNext();										  // so we must subtract bEntry.value from zero (or * by -1)
				} else {
					result.changeEntry(i, aEntry.col, aEntry.value - bEntry.value); // aEntry column == bEntry column, subtract normally
					A.moveNext();
					B.moveNext();
				}
			}
		}
		
		return result;
	}
	 
	Matrix transpose() { // returns a new Matrix that is the transpose of this Matrix
		Matrix result = new Matrix(getSize());
		
		for(int i = 1; i <= getSize(); i++) {
			row[i].moveFront();
			while(row[i].index() != -1) {
				Entry temp = (Entry) row[i].get();
				result.changeEntry(temp.col, i, temp.value); // Switching i and j (rows & columns)
				row[i].moveNext();
			}
		}
		
		return result;
	}
	 
	Matrix mult(Matrix M) { // returns a new Matrix that is the product of this Matrix with M
		if (getSize() != M.getSize()) {
			throw new RuntimeException("Matrix Error: calling \"mult()\" on matrices with different sizes/dimensions");
		}
		
		Matrix result = new Matrix(getSize());
		Matrix tPose = M.transpose();
		
		for(int i = 1; i <= getSize(); i++) { // Multipy rows of A by Columns of B
			if(row[i].length() != 0) {
				for(int j = 1; j <= getSize(); j++) {
					if(tPose.row[j].length() != 0) {
						result.changeEntry(i, j, dot(row[i], tPose.row[j]));
					}
					
				}
			}
			
		}
		
		return result;
	}

	// Other functions---------------------------------------------------------------------------------
	public String toString() { // overrides Object's toString() method
		StringBuffer sb = new StringBuffer();
		for(int i = 1; i <= getSize(); i++) {
			if(row[i].length() != 0) {
				sb.append(i + ": " + row[i] + "\n");
			}
		}
		return new String(sb);
	}
	
	private static double dot(List A, List B) { // Helps compute mult by multiplying two Lists
		double result = 0;
		A.moveFront();
		B.moveFront();
		
		while(A.index() != -1 && B.index() != -1) {
			Entry aEntry = (Entry) A.get();
			Entry bEntry = (Entry) B.get();
			if(aEntry.col > bEntry.col) {
				B.moveNext();
			}
			else if(aEntry.col < bEntry.col) {
				A.moveNext();
			} else {
				result += (aEntry.value * bEntry.value);
				A.moveNext();
				B.moveNext();
			}
		}
		return result;
	}
	
}