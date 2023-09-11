package proj_2_2;



import java.io.IOException;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class SumAboveBelowDiagonal extends Thread {

	private Socket client;

	private Formatter toNet  = null ;
	private Scanner fromNet= null;

	public SumAboveBelowDiagonal(Socket client) {
		this.client=client;
	}

	public void run() {

		try {
			fromNet = new Scanner(client.getInputStream());
			toNet = new Formatter(client.getOutputStream());

			int numRow = fromNet.nextInt();
			int numCol = fromNet.nextInt();
			int mat[][] = new int[numRow][numCol];
			for (int i = 0; i < numRow; i++) {
				for (int j = 0; j < numCol; j++) {
					mat[i][j] = fromNet.nextInt();
				}
			}

			//to calculate sum of elements above diagonal.
			int sumAbove=0;
			for (int j = 1; j < numCol; j++) {
				for (int i=j-1 ; i>=0 ; i--) {
					sumAbove= sumAbove + mat[i][j];
				}

			}
			
			//to calculate sum of elements below diagonal.
			int sumbelow=0;
			for (int i = 1; i < numRow; i++) {
				for (int j=i-1 ; j>=0 ; j--) {
					sumbelow= sumbelow + mat[i][j];
				}
			}
			
			toNet.format("%d\n",sumAbove).flush();
			toNet.format("%d\n",sumbelow).flush();
			
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
