package proj_2_2;



import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Scanner;

public class SortMatrixService extends Thread{
	private Socket client;

	private Formatter toNet  = null ;
	private Scanner fromNet= null;

	public SortMatrixService(Socket client) {
		this.client=client;

	}

	public void run() {

		try {
			
			fromNet = new Scanner(client.getInputStream());
			toNet = new Formatter(client.getOutputStream());
		
			int numRow = fromNet.nextInt();
			int numCol = fromNet.nextInt();
			int mat[][] = new int[numRow][numCol];
			int array[] = new int[numRow * numCol];
			for (int i = 0; i < numRow; i++) {
				for (int j = 0; j < numCol; j++) {
					mat[i][j] = fromNet.nextInt();
				}
			}

			int k = 0;
			for (int i = 0; i < numRow; i++)
				for (int j = 0; j < numCol; j++)
					array[k++] = mat[i][j];
			Arrays.sort(array);
			int choosen = fromNet.nextInt();


			if(choosen == 1) {
				k = 0;
				for (int i = 0; i < numRow; i++)
					for (int j = 0; j < numCol; j++)
						mat[i][j] = array[k++];
				for (int i = 0; i <numRow; i++)
					for (int j = 0; j < numCol; j++)
						toNet.format("%d\n",mat[i][j]).flush();
			}
			else if (choosen == 2) {
				k = array.length-1;
				for (int i = 0; i <numRow; i++)
					for (int j = 0; j < numCol; j++)
						mat[i][j] = array[k--];
				for (int i = 0; i <numRow; i++)
					for (int j = 0; j < numCol; j++)
						toNet.format("%d\n",mat[i][j]).flush();
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
