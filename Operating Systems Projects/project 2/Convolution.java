package proj_2_2;



import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class Convolution extends Thread {
	private Socket client;
	private Scanner fromNet = null;
	private Formatter toNet = null;
	//int krow = fromNet.nextInt();
//	int kcol = fromNet.nextInt();
//	int row = fromNet.nextInt();
	//int col = fromNet.nextInt();

	public Convolution(Socket client) {
		this.client = client;
	}


	public void run() {
		try {
			
			fromNet = new Scanner(client.getInputStream());
			toNet = new Formatter(client.getOutputStream());
			DataInputStream dis = new DataInputStream(client.getInputStream());
			DataOutputStream dos = new DataOutputStream(client.getOutputStream());
			
			
			//System.out.println(" Enter a number of rows for kernel matrix [2 or 3]\n:");

			dos.writeUTF(" Enter a number of rows for kernel matrix [2 or 3]\n:");
			int row = dis.readInt();

			
			dos.writeUTF(" Enter same number for columns for kernel matrix [2 or 3]\n:");
			int col = dis.readInt();

					
			int inmat[][] = new int[row][col];
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					inmat[i][j] = dis.readInt();
				}
			}
			
			
			dos.writeUTF("  Enter a number of rows for matrix size [8 or 16]\\n:");
			int krow = dis.readInt();

			
			dos.writeUTF("  Enter same number for columns for matrix size [8 or 16]\\n:");
			int kcol = dis.readInt();
			
			
			
			int kmat[][] = new int[krow][kcol];
			for (int i = 0; i < krow; i++) {
				for (int j = 0; j < kcol; j++) {
					kmat[i][j] = dis.readInt();
				}
			}
			
			
			
			int convulationMat[][] = convolution2D2(kmat, inmat);
			//int[][] convulationMat = convolution2D(kmat, kcol, krow, inmat,

				//	col, row);
			       dos.writeInt(convulationMat.length);
			       dos.writeInt(convulationMat[0].length);

					for (int i = 0; i < convulationMat.length; i++) {

					for (int j = 0; j < convulationMat[0].length; j++) {

                     dos.writeUTF(convulationMat[i][j] + " ");

					}

					dos.writeUTF("\n");

					}
			
			
			
			
			
			
		

		
		} catch (IOException ioe) {
			System.err.println(ioe);
		} finally {
			try {
				if (client != null)
					client.close();
				if (fromNet != null)
					fromNet.close();
				if (toNet != null)
					toNet.close();
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		
		
		
		
	}
	
	
	public static int[][] convolution2D2(int[][] input,int[][] kernel) {
		int rows = input[0].length;
		int cols= input.length;
		int kCols= kernel[0].length;
        int kRows= kernel.length;
        int kCenterX = kCols / 2;
        int kCenterY = kRows / 2;
        int [][] out= new int [rows][cols];
         
        int mm;
        int ii;
        int jj;
        int nn;

        for(int i=0; i < rows; ++i)              // rows
        {
        	for(int j=0; j < cols; ++j)          // columns
        	{
        		for(int m=0; m < kRows; ++m)     // kernel rows
        		{
        			mm = kRows - 1 - m;      // row index of flipped kernel

        			for(int n=0; n < kCols; ++n) // kernel columns
        			{
        				nn = kCols - 1 - n;  // column index of flipped kernel

        				// index of input signal, used for checking boundary
        				ii = i + (kCenterY - mm);
        				jj = j + (kCenterX - nn);

        				// ignore input samples which are out of bound
        				if( ii >= 0 && ii < rows && jj >= 0 && jj < cols )
        					out[i][j] += input[ii][jj] * kernel[mm][nn];
        			}
        		}
        	}
        }return out;
	}
	
	public static int sConvolution(int[][] input, int x, int y, int[][] k, int kernelWidth,

			int kernelHeight) {

			int output = 0;

			for (int i = 0; i < kernelWidth; ++i) {

			for (int j = 0; j < kernelHeight; ++j) {

			output = output + (input[x + i][y + j] * k[i][j]);

			}

			}

			return output;

			}

			public static int[][] convolution2D(int[][] input, int width, int height, int[][] kernel, int kernelWidth,

			int kernelHeight) {

			int smallWidth = width - kernelWidth + 1;

			int smallHeight = height - kernelHeight + 1;

			int[][] output = new int[smallWidth][smallHeight];

			for (int i = 0; i < smallWidth; ++i) {

			for (int j = 0; j < smallHeight; ++j) {

			output[i][j] = 0;

			}

			}

			for (int i = 0; i < smallWidth; ++i) {

			for (int j = 0; j < smallHeight; ++j) {

			output[i][j] = sConvolution(input, i, j, kernel, kernelWidth, kernelHeight);

			}

			}

			return output;

			}
}
