package proj_2_2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
	ServerSocket server;
	Socket client;
	int port;

	public Server(int port) {
		this.port = port;
	}

	public void run() {
		try {
			server = new ServerSocket(port);
			
			while (true) {
				client = server.accept();
				System.out.println("A new client is connected : " + client);
				switch (port) {
				case 2000:
					new MaxMin(client).run();
					break;
				case 3000:
					new Multiplication(client).run();
					break;
				case 4000:
					new SortMatrixService(client).run();
					break;
				case 5000:
					new SumAboveBelowDiagonal(client).run();
					break;
				case 6000:
					new Convolution(client).run();
					break;
				case 7000:
					new Replace(client).run();
					break;
				case 8000:
					new Average(client).run();
					break;
				default:
					System.exit(0);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Server(2000).start();
		new Server(3000).start();
		new Server(4000).start();
		new Server(5000).start();
		new Server(6000).start();
		new Server(7000).start();
		new Server(8000).start();
	}
}
