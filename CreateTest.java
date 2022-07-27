// CreateTest.java
// 7/23/2022
//   This program generates sample text files to be used for developing
//   the Arnold Cat map program in text mode.


import java.io.*;

public class CreateTest {
  public static void main(String[] args) {
	  int MatrixSize =101;
	  String MatrixSizeString = new String(String.valueOf(MatrixSize));
	  if (args.length != 1)
	 {
	    System.out.println("Usage: filename");
	    return;
	 }
	 try(FileOutputStream fout = new FileOutputStream(args[0]))
	 {
           for (int i=0; i < MatrixSizeString.length(); i++)
		   fout.write(MatrixSizeString.charAt(i));
	   fout.write('\n');
	   for (int i=0; i < MatrixSize; i++)
	   {
		for (int j=0; j < MatrixSize; j++)
		{
		   char c = '.';
		   fout.write(c);
		}
		fout.write('\n');
	   }
	 }
	 catch (IOException exc) {
	   System.out.println("IO Error: " + exc);
	 }

  }
}
