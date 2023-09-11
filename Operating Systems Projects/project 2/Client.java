package proj_2_2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class Client {
	private Socket server;
	private Scanner fromUser =new Scanner(System.in);
	private Scanner fromNet = null;
	private Formatter toNet = null;

	public Client() {
		
		while(true) {
			System.out.println();
			menu();
			System.out.println();
			int select = fromUser.nextInt();
			
			if (select ==0){
				return;
			}
			
			else if (select == 1){
				Multiplication();
			}
			
			else if (select == 2){
				Convolution();
			}
			
			else if(select == 3) {
				SumAboveBelow();
			}
			
			else if(select == 4) {
				MaxMin();
			}
		
			else if(select == 5) {
				Sort();
			}
			
			else if(select == 6) {
				Average();
			}
			
			else if(select == 7) {
				Replace();
			}
			System.out.println();
			
		}
	}

	private void MaxMin() {
		// TODO Auto-generated method stub
		try {
			int row,col;
			server = new Socket("localhost",2000);
			fromNet = new Scanner(server.getInputStream());
			toNet = new Formatter(server.getOutputStream());
			DataOutputStream dos = new DataOutputStream(server.getOutputStream());
			DataInputStream dis = new DataInputStream(server.getInputStream());
			
			System.out.println(dis.readUTF());
			row = fromUser.nextInt();
			dos.writeInt(row);
	        
			System.out.println(dis.readUTF());
			col = fromUser.nextInt();
			dos.writeInt(col);
			
			System.out.println("enter the matrix elements: ");

			 int[][] amat = new int[row][col];
			 
			 
			 for (int i = 0; i < row; i++) {
					for (int j = 0; j < col; j++) {
						amat[i][j] = fromUser.nextInt();
						dos.writeInt(amat[i][j]);
					}
				}
			 
			 System.out.println(dis.readUTF());
			 System.out.println(dis.readUTF());

			 
			 
			 
			 

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			server = null;
		}
		
	}

	private void Multiplication() {
		// TODO Auto-generated method stub
		try {
		int brow,bcol,row,col;
		server = new Socket("localhost",3000);
		fromNet = new Scanner(server.getInputStream());
		toNet = new Formatter(server.getOutputStream());
		DataOutputStream dos = new DataOutputStream(server.getOutputStream());
		DataInputStream dis = new DataInputStream(server.getInputStream());


		System.out.println(dis.readUTF());
		row = fromUser.nextInt();
		dos.writeInt(row);
        
		System.out.println(dis.readUTF());
		col = fromUser.nextInt();
		dos.writeInt(col);
		
		System.out.println("enter the matrix elements: ");

		 int[][] amat = new int[row][col];
		 
		 
		 for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					amat[i][j] = fromUser.nextInt();
					dos.writeInt(amat[i][j]);
				}
			}
		 
		 
		 System.out.println(dis.readUTF());
			brow = fromUser.nextInt();
			dos.writeInt(brow);
         
			System.out.println(dis.readUTF());

			bcol = fromUser.nextInt();
			dos.writeInt(bcol);
			
			
			System.out.println("enter the matrix elements: ");

			int[][] bmat = new int[brow][bcol];
			 
			 
			 for (int i = 0; i < brow; i++) {
					for (int j = 0; j < bcol; j++) {
						bmat[i][j] = fromUser.nextInt();
						dos.writeInt(bmat[i][j]);
					}
				}
			 
			 
			 System.out.println("Multiplication of 2 Matrix: ");
             
				int rrow = dis.readInt();
				int rcol = dis.readInt();
				
				

				 for (int i = 0; i < rrow; i++) {
					 for (int j = 0; j < rcol; j++) {
						 System.out.print(dis.readUTF());
				     }
					 System.out.print(dis.readUTF());
				 }
			 
			 
		 
		 
		 
		
		
		
		
		
		
		
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			server = null;
		}
		
	}

	public void menu() {
		System.out.println("Choose from the menu:\n"
				+ "0. for exit\n"
				+ "1. matrix multiplication \n"
				+ "2. matrix convolution \n"
				+ "3. Sum numbers below the diagonal line and above the diagonal line\n"
				+ "4. Find the maximum and minimum elements in a matrix\n"
				+ "5. Sort the elements of a matrix\n"
				+ "6. Get the average of two matrices and find the maximum average\n"
				+ "7. Find a number in the matrix and replace it with 0 or 1");
	}



	public int EnterMatrixAndSendToServer() {
		System.out.println("enter the row and colum: ");
		int numRow = fromUser.nextInt();
		int numCol = fromUser.nextInt();
		while(numRow != numCol) {
			System.out.println("enter the row and colum again to be smilar n x n: ");
			numRow = fromUser.nextInt();
			numCol = fromUser.nextInt();
		}

		System.out.println("enter the matrix: ");
		int mat[][] = new int[numRow][numCol];

		for (int i = 0; i < numRow; i++) {
			for (int j = 0; j < numCol; j++) {
				mat[i][j] = fromUser.nextInt();
			}
		}

		toNet.format("%d\n", numRow);
		toNet.format("%d\n", numCol);
		for (int i = 0; i <numRow; i++)
			for (int j = 0; j < numCol; j++)
				toNet.format("%d\n",mat[i][j]).flush();
		return numRow;
	}


	public void Sort() {
		try {
			server = new Socket("localhost",4000);
			fromNet = new Scanner(server.getInputStream());
			toNet = new Formatter(server.getOutputStream());

			int numMat = EnterMatrixAndSendToServer();

			System.out.println("The sort is ascending or descending? 1 for ascending, 2 for descending");
			int choosen = fromUser.nextInt();
			while(choosen!=1 && choosen !=2) {
				System.out.println("\nplease enter 1 for ascending, 2 for descending ");
				choosen = fromUser.nextInt();
			}			
			toNet.format("%d\n", choosen).flush();

			int [][]matResult = new int[numMat][numMat];
			for (int i = 0; i < numMat; i++)
			{
				for (int j = 0; j < numMat; j++)
				{
					matResult[i][j] = fromNet.nextInt();
					System.out.print(matResult[i][j] + " ");
				}
				System.out.println();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			server = null;
		}
	}

	public void SumAboveBelow() {
		try {
			server = new Socket("localhost",5000);
			fromNet = new Scanner(server.getInputStream());
			toNet = new Formatter(server.getOutputStream());

			EnterMatrixAndSendToServer();
			String sumAbove = fromNet.next();
			String sumBelow = fromNet.next();
			if(Integer.parseInt(sumAbove)> Integer.parseInt(sumBelow)) {
				System.out.println("Sum Above is grater than Below: "+sumAbove+" > "+sumBelow+"\n");
			}else
				System.out.println("Sum Below is grater than Above: "+sumBelow+" > "+sumAbove+"\n");
			System.out.printf("The sum Above: %s\nThe sum Below: %s", sumAbove,sumBelow);

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			server = null;
		}
	}
	
	
	
	public void Average() {
		try {
			//server = new Socket("localhost",8000);

			
			int krow,kcol,row,col;
			server = new Socket("localhost",8000);
			fromNet = new Scanner(server.getInputStream());
			toNet = new Formatter(server.getOutputStream());
			DataOutputStream dos = new DataOutputStream(server.getOutputStream());
			DataInputStream dis = new DataInputStream(server.getInputStream());


			System.out.println(dis.readUTF());
			row = fromUser.nextInt();
			dos.writeInt(row);
            
			System.out.println(dis.readUTF());

			col = fromUser.nextInt();
			dos.writeInt(col);

			
			
			System.out.println("enter the matrix elements: ");

			 int[][] kernel = new int[row][col];
			 
			 
			 for (int i = 0; i < row; i++) {
					for (int j = 0; j < col; j++) {
						kernel[i][j] = fromUser.nextInt();
						dos.writeInt(kernel[i][j]);
					}
				}
			 
		
			 
			 
			    System.out.println(dis.readUTF());
				krow = fromUser.nextInt();
				dos.writeInt(krow);
	            
				System.out.println(dis.readUTF());

				kcol = fromUser.nextInt();
				dos.writeInt(kcol);
				
				System.out.println("enter the matrix elements: ");

				int[][] input = new int[krow][kcol];
				 
				 
				 for (int i = 0; i < krow; i++) {
						for (int j = 0; j < kcol; j++) {
							input[i][j] = fromUser.nextInt();
							dos.writeInt(input[i][j]);
						}
					}
				 
				 
				 System.out.println("Average First Matrix : ");
	                
					int rrow = dis.readInt();
					int rcol = dis.readInt();
					
					

					 for (int i = 0; i < rrow; i++) {
						 for (int j = 0; j < rcol; j++) {
							 System.out.print(dis.readUTF());
					     }
						 System.out.println(dis.readUTF());
					 }
					 
					 
					 
					 System.out.println("Average scond Matrix : ");
		                
						 rrow = dis.readInt();
						 rcol = dis.readInt();
						
						

						 for (int i = 0; i < rrow; i++) {
							 for (int j = 0; j < rcol; j++) {
								 System.out.print(dis.readUTF());
						     }
							 System.out.println(dis.readUTF());
						 }
				 
						 
						 
						 System.out.println(dis.readUTF());
				 
				 
			
			
			 // System.out.printf("Maximum average of the 2 matrices: ", avg1 , avg2);

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			server = null;
		}
	}
	
	public void Replace() {
		try {
			
			int row,col;
			server = new Socket("localhost",7000);
			fromNet = new Scanner(server.getInputStream());
			toNet = new Formatter(server.getOutputStream());
			DataOutputStream dos = new DataOutputStream(server.getOutputStream());
			DataInputStream dis = new DataInputStream(server.getInputStream());
			
			System.out.println(dis.readUTF());
			row = fromUser.nextInt();
			dos.writeInt(row);
	        
			System.out.println(dis.readUTF());
			col = fromUser.nextInt();
			dos.writeInt(col);
			
			System.out.println("enter the matrix elements: ");

			 int[][] amat = new int[row][col];
			 
				System.out.println(dis.readUTF());

			    for (int i = 0; i < row; i++) {
					for (int j = 0; j < col; j++) {
						amat[i][j] = fromUser.nextInt();
						dos.writeInt(amat[i][j]);
					}
				}
			 
			    System.out.println(dis.readUTF());
				int one = fromUser.nextInt();
				dos.writeInt(one);
				
				System.out.println(dis.readUTF());
				int tow = fromUser.nextInt();
				dos.writeInt(tow);
				
				
				

				 for (int i = 0; i < row; i++) {
					 for (int j = 0; j < col; j++) {
						 System.out.print(dis.readUTF());
				     }
					 System.out.println(dis.readUTF());
				 }
				 
				
				
				
				
				
			 
			 
			 
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			server = null;
		}
	}
	
	public void Convolution() {
		try {
			int krow,kcol,row,col;
			server = new Socket("localhost",6000);
			fromNet = new Scanner(server.getInputStream());
			toNet = new Formatter(server.getOutputStream());
			DataOutputStream dos = new DataOutputStream(server.getOutputStream());
			DataInputStream dis = new DataInputStream(server.getInputStream());


			System.out.println(dis.readUTF());
			row = fromUser.nextInt();
			dos.writeInt(row);
            
			System.out.println(dis.readUTF());

			col = fromUser.nextInt();
			dos.writeInt(col);

			
			System.out.println("enter the matrix elements: ");


			 int[][] kernel = new int[row][col];
			 
			 
			 for (int i = 0; i < row; i++) {
					for (int j = 0; j < col; j++) {
						kernel[i][j] = fromUser.nextInt();
						dos.writeInt(kernel[i][j]);
					}
				}
			 
		
			 
			 
			    System.out.println(dis.readUTF());
				krow = fromUser.nextInt();
				dos.writeInt(krow);
	            
				System.out.println(dis.readUTF());

				kcol = fromUser.nextInt();
				dos.writeInt(kcol);
				
				System.out.println("enter the matrix elements: ");

				int[][] input = new int[krow][kcol];
				 
				 
				 for (int i = 0; i < krow; i++) {
						for (int j = 0; j < kcol; j++) {
							input[i][j] = fromUser.nextInt();
							dos.writeInt(input[i][j]);
						}
					}
				 
				 
				 
					System.out.println("Convulation Matrix: ");
                   
					int rrow = dis.readInt();
					int rcol = dis.readInt();
					
					

					 for (int i = 0; i < rrow; i++) {
						 for (int j = 0; j < rcol; j++) {
							 System.out.print(dis.readUTF());
					     }
						 System.out.println(dis.readUTF());
					 }
					 
				 
				 
			 
			 
			 
			 
			 
			 
			 
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			server = null;
		}
	}
	
	
/*	public void Multiplication(){
		
	}
	
	public void MaxMin(){
		
	}
*/	
	
	public static void main(String[] args) throws IOException {
		new Client();
	}
}
