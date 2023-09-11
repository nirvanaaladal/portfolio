package proj_2_2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class Multiplication {
	private Socket client;
	private Scanner fromNet = null;
	private Formatter toNet = null;
	
	public Multiplication(Socket client) {
		this.client = client;
	}
	
	
	public void run() {
		try {
			
			fromNet = new Scanner(client.getInputStream());
			toNet = new Formatter(client.getOutputStream());
			DataInputStream dis = new DataInputStream(client.getInputStream());
			DataOutputStream dos = new DataOutputStream(client.getOutputStream());
			
			dos.writeUTF(" Enter a number of rows for A matrix\n:");
			int arow = dis.readInt();

			
			dos.writeUTF(" Enter same number for columns for A matrix \n:");
			int acol = dis.readInt();
			
			
			int amat[][] = new int[arow][acol];
			for (int i = 0; i < arow; i++) {
				for (int j = 0; j < acol; j++) {
					amat[i][j] = dis.readInt();
				}
			}
			
			
			
			dos.writeUTF("  Enter a number of rows for B matrix size \\n:");
			int brow = dis.readInt();

			
			dos.writeUTF("  Enter same number for columns B for matrix size \\n:");
			int bcol = dis.readInt();
			
			
			
			int bmat[][] = new int[brow][bcol];
			for (int i = 0; i < brow; i++) {
				for (int j = 0; j < bcol; j++) {
					bmat[i][j] = dis.readInt();
				}
			}
			
			
			
	         int multiply[][] = new int[arow][bcol];
	         
	         int sum = 0;
	         for (int c = 0; c < arow; c++)
	         {
	            for (int d = 0; d < bcol; d++)
	            {  
	               for (int k = 0; k < brow; k++)
	               {
	                  sum = sum + amat[c][k]*bmat[k][d];
	               }
	 
	               multiply[c][d] = sum;
	               sum = 0;
	            }
	         }
	         
	         
	         
	         //return the multiplication to the client 
	         dos.writeInt(multiply.length);
		       dos.writeInt(multiply[0].length);
	         
	         for (int i = 0; i < multiply.length; i++) {

					for (int j = 0; j < multiply[0].length; j++) {

                  dos.writeUTF(multiply[i][j] + " ");

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
