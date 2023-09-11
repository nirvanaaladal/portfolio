package proj_2_2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class MaxMin {
	private Socket client;
	private Scanner fromNet = null;
	private Formatter toNet = null;
	
	public MaxMin(Socket client) {
		this.client = client;
	}
	public void run() {
		try {
			fromNet = new Scanner(client.getInputStream());
			toNet = new Formatter(client.getOutputStream());
			DataInputStream dis = new DataInputStream(client.getInputStream());
			DataOutputStream dos = new DataOutputStream(client.getOutputStream());
			
			dos.writeUTF(" Enter a number of rows for the matrix \n:");
			int arow = dis.readInt();

			
			dos.writeUTF(" Enter same number for columns for the matrix \n:");
			int acol = dis.readInt();
			
			
			int amat[][] = new int[arow][acol];
			for (int i = 0; i < arow; i++) {
				for (int j = 0; j < acol; j++) {
					amat[i][j] = dis.readInt();
				}
			}
			
			
			
			dos.writeUTF("  The Maximum is  : "+getMaxValue(amat));
			
			dos.writeUTF("  The Minimum is  : "+getMinValue(amat));
			
			
			
			
			
			
			
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
	
	 public static int getMaxValue(int[][] numbers) {
	        int maxValue = numbers[0][0];
	        for (int j = 0; j < numbers.length; j++) {
	            for (int i = 0; i < numbers[j].length; i++) {
	                if (numbers[j][i] > maxValue) {
	                    maxValue = numbers[j][i];
	                }
	            }
	        }
	        return maxValue;
	    }

	    public static int getMinValue(int[][] numbers) {
	        int minValue = numbers[0][0];
	        for (int j = 0; j < numbers.length; j++) {
	            for (int i = 0; i < numbers[j].length; i++) {
	                if (numbers[j][i] < minValue ) {
	                    minValue = numbers[j][i];
	                }
	            }
	        }
	        return minValue ;
	    }
}
