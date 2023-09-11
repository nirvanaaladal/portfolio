package proj_2_2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class Average extends Thread {
	private Socket client;
	private Scanner fromNet = null;
	private Formatter toNet = null;
	
	public Average(Socket client)
	{
		this.client = client;
		//this.start();
	}
	
	

	    

	    public double cellNeighborsAverage(int[][] matrix,int row, int col)
	    {
	        // Ignore center cell
	        int sum = matrix[row - 1][col - 1] + matrix[row - 1][col]
	                + matrix[row - 1][col + 1] + matrix[row][col - 1]
	                + matrix[row][col + 1] + matrix[row + 1][col - 1]
	                + matrix[row + 1][col] + matrix[row + 1][col + 1];
	        return sum / 8;
	    }
	
	public void run() {
		try {
			int row1,col1,row2, col2,  Matrix1[][],Matrix2[][];
			
			fromNet = new Scanner(client.getInputStream());
			toNet = new Formatter(client.getOutputStream());
			DataInputStream dis = new DataInputStream(client.getInputStream());
			DataOutputStream dos = new DataOutputStream(client.getOutputStream());
			
			
			//System.out.println(" Enter a number of rows for kernel matrix [2 or 3]\n:");

			dos.writeUTF(" Enter a number of rows for the first matrix \n:");
			int row = dis.readInt();

			
			dos.writeUTF(" Enter same number for columns for the first  matrix \n:");
			int col = dis.readInt();

					
			int inmat[][] = new int[row][col];
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					inmat[i][j] = dis.readInt();
				}
			}
			
			
			dos.writeUTF("  Enter a number of rows for second  matrix  \n:");
			int krow = dis.readInt();

			
			dos.writeUTF("  Enter same number for columns for second matrix \n:");
			int kcol = dis.readInt();
			
			
			
			int kmat[][] = new int[krow][kcol];
			for (int i = 0; i < krow; i++) {
				for (int j = 0; j < kcol; j++) {
					kmat[i][j] = dis.readInt();
				}
			}
			
			
			
		    double[][] computedMatrix = new double[row][col];
		    	
		    for (int i = 1; i < row - 1; i++)
		        {
		            for (int j = 1; j < col - 1; j++)
		            {
		                computedMatrix[i][j] = cellNeighborsAverage(inmat,i, j);
		            }
		        }
		        
		     
		        
           double[][] computedMatrix2 = new double[krow][kcol];
		    	
		        for (int i = 1; i < row - 1; i++)
		        {
		            for (int j = 1; j < col - 1; j++)
		            {
		                computedMatrix2[i][j] = cellNeighborsAverage(kmat,i, j);
		            //
		            }
		        }    
		           
		           dos.writeInt(computedMatrix.length);
			       dos.writeInt(computedMatrix[0].length);
				   for (int i = 0; i < computedMatrix.length; i++) {
				   for (int j = 0; j < computedMatrix[0].length; j++) {
                   dos.writeUTF(computedMatrix[i][j] + " ");
					}
					dos.writeUTF("\n");
					}
				   
				   
				   dos.writeInt(computedMatrix2.length);
			       dos.writeInt(computedMatrix2[0].length);
				   for (int i = 0; i < computedMatrix2.length; i++) {
				   for (int j = 0; j < computedMatrix2[0].length; j++) {
                   dos.writeUTF(computedMatrix2[i][j] + " ");
					}
					dos.writeUTF("\n");
					}
				   
				   
				   
				   double maxValue = 0;
			        for (int j = 0; j < computedMatrix.length; j++) {
			            for (int i = 0; i < computedMatrix[j].length; i++) {
			                if (computedMatrix[j][i] > maxValue) {
			                    maxValue = computedMatrix[j][i];
			                }
			            }
			        }
			        
			        for (int j = 0; j < computedMatrix2.length; j++) {
			            for (int i = 0; i < computedMatrix2[j].length; i++) {
			                if (computedMatrix2[j][i] > maxValue) {
			                    maxValue = computedMatrix2[j][i];
			                }
			            }
			        }
			        
			        
					dos.writeUTF("Max Average is "+maxValue);

			        
				   
				   
				   
				   
				   
				   
				   
				   
		    
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
