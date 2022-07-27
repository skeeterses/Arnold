/*  TextTransform.java
 *    Scott Stinson
 *    The purpose of this program is to test the Arnold's Cat Transform
 *      algorithm using (x+y, x+2y) mod 10 on a 10x10 matrix containing
 *      ascii characters.
 */
// 7/22/2022 -- ReWrite the code to do the modulo operation on the fly
// 		rather than precomputing the values and putting them in a
// 		Matrix.
// 7/27/2022 -- Done.
//		I can optimize this later on by precomputing and storing the transformation values
//		into the Transform list, but for now it works.
//		The fix for the messy transformations was to initialize
//		the TempMatrix values to '.' and then to copy the TempMatrix
//		pixels to the TestMatrix pixels one by one.


import java.io.*;
import java.util.*;

public class TextTransform {
   public static void main(String args[])
	   throws IOException
   {
// Create our starter matrix with a # at the origin, Xs and Ys in the
// top right corner, and a triangle in the middle containing A, BBB,
// and CCCCC.
   char[][] TestMatrix;
   char[][] TempMatrix;
   Coordinate[][] Transform;

   // This is the number of letters in the matrix.  It will be initialized
   // when reading the file and can be used for comparison to the transforms.
   // If the number of letters in the later matrices don't match, then the
   // program has a bug.
   int[] MasterList = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

   int MatrixSize;
   char currentChar = '0';

   // Look at page 294 of Java, The Complete Reference
  FileReader MatrixFile;
   try {
      MatrixFile = new FileReader(args[0]);
   } catch(FileNotFoundException e) {
     System.out.println("File Not Found");
     return;
   } catch(ArrayIndexOutOfBoundsException e) {
	   System.out.println("Usage: TextTransform File");
	   return;
   }

// First, get the size of the vector.
   StringBuilder currentLine = new StringBuilder(80);
   currentLine.setLength(80);
   int currentInt = 0;
// Do a char conversion,
// then put the character into the String and use while loop to check
// for \n.
// Then Use the Integer class to decode the string into the MatrixSize.   
   int stringPosition = 0;
   do
   {
     currentInt = MatrixFile.read();
     currentChar = (char) currentInt; 
     currentLine.setCharAt(stringPosition, currentChar);
     stringPosition++;
   }
//   while (currentInt != 13);
   while ( (currentInt >= 48) && (currentInt <= 57) ); 

   Integer dummyInteger = 0;
   MatrixSize = 
	   dummyInteger.parseInt(currentLine.substring(0,stringPosition-1));

   TestMatrix = new char[MatrixSize][MatrixSize];
   TempMatrix = new char[MatrixSize][MatrixSize];
//   Transform = new Coordinate[MatrixSize][MatrixSize];

   //Initialize the values of the Transform array so that the program
   //can do the shearing transformations on each pixel of the TestMatrix
   //bitmap.
/*
   for (int row=0; row<MatrixSize; row++)
   {
	   for (int column=0; column<MatrixSize; column++)
	   {
               int x = (row + column) % MatrixSize;
	       int y = (column + (2 * row)) % MatrixSize;
	       Transform[column][row] = new Coordinate(x, y);
	   }
   }
*/
   for (int row=0; row<MatrixSize; row++)
   {
           while (currentInt < 33)
		   currentInt = MatrixFile.read();
	   currentChar = (char) currentInt;
	   TestMatrix[0][row] = currentChar;
	   
	   for (int column=1; column<MatrixSize; column++)
	   {
              currentInt = MatrixFile.read();
	      currentChar = (char) currentInt;
	      TestMatrix[column][row] = currentChar;
	   }
           currentInt = MatrixFile.read();
   }


Scanner keyboard = new Scanner(System.in);
String inputString;
while (currentChar != 'x')
{
   // Print out the matrix
   for (int row=0; row<MatrixSize; row++)
   {
      for (int column=0; column<MatrixSize; column++)
      {
         System.out.print(TestMatrix[column][row]);
      }
      System.out.println(" ");
   }

   System.out.println("Size of matrix is " + MatrixSize);

   // Main Menu
   System.out.println("Hit the t button for Transform");
   System.out.println("Hit the x button for Exit");
   System.out.println("Hit m for Multiple Transforms");
   System.out.println("Hit s to print out the stats");
   inputString = keyboard.nextLine();
   int numTimes = 1;
   currentChar = inputString.charAt(0);

   if (currentChar == 'm')
   {
     System.out.println("Enter the number of transforms");
     inputString = keyboard.nextLine();
     numTimes = 
        dummyInteger.parseInt(inputString);

   }

   // Perform the transform
   if ( (currentChar == 't') || (currentChar == 'm'))
   {
      while (numTimes > 0)
      {
         for (int i =0; i< MatrixSize; i++)
		 for (int j=0; j< MatrixSize; j++)
			 TempMatrix[i][j] = '.';

      for (int row=0; row < MatrixSize; row++)
      {
         for (int column=0; column < MatrixSize; column++)
	 {
//            int xPosition = Transform[column][row].XCoordinate();
//	    int yPosition = Transform[column][row].YCoordinate();

      //      if (Character.isLetter(TestMatrix[column][row]))
	//    {
            int xPosition = (row + column) % MatrixSize;
	    int yPosition = (column + 2* row) % MatrixSize;
            TempMatrix[xPosition][yPosition] = TestMatrix[column][row];
	  //  }
	 }
      }
        for (int i=0; i<MatrixSize; i++)
		for (int j=0; j<MatrixSize; j++)
        TestMatrix[i][j] = TempMatrix[i][j];
      
      numTimes--;

      }
   }

// Print the stats
   if (currentChar == 's')
   {
      for (int i = 0; i < MatrixSize; i++)
	      for (int j = 0; j < MatrixSize; j++)
	      {
                if ((TestMatrix[i][j] >= 97) && (TestMatrix[i][j] <= 122))
			MasterList[ TestMatrix[i][j] - 97 ]++;
	      }
      for (int i = 0; i < 26 ; i++)
      {
	      char letter = (char) (i+97);
	      System.out.print(letter);
	      System.out.println( " : " + MasterList[i]);

	      // Reset the masterlist back to 0.
	      MasterList[i] = 0;
      }
   }

}

   }
}
