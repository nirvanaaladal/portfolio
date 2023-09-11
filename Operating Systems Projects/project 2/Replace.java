package proj_2_2;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class Replace extends Thread {
	
	private Socket client;
	private Scanner fromNet = null;
	private Formatter toNet = null;
	
	public Replace(Socket client)
	{
		this.client = client;
		//this.start();
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
			
			dos.writeUTF(" Enter Element of the matrix \n:");

			int amat[][] = new int[arow][acol];
			for (int i = 0; i < arow; i++) {
				for (int j = 0; j < acol; j++) {
					amat[i][j] = dis.readInt();
				}
			}
			
			
			dos.writeUTF(" Enter the number that the you  wants to replace \n:");
			int one = dis.readInt();

			
			dos.writeUTF(" Enter the number to be replaced with. \n:");
			int tow = dis.readInt();
			
		    for (int row = 0 ; row < arow ; row++) {
		        for (int col = 0 ; col < acol ; col++) {
		            int d = col-row;
		            if (amat[row][col] == one) {
		                amat[row][col] = tow;
		            }
		        }
		    }
		    
		    
		    

			for (int i = 0; i < amat.length; i++) {

			for (int j = 0; j < amat[0].length; j++) {

             dos.writeUTF(amat[i][j] + " ");

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
}
