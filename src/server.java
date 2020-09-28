import java.net.*;
import java.io.*;

public class server {
	ServerSocket server;
	Socket socket;

	BufferedReader br;
	PrintWriter pr;

	public server() {
		// TODO Auto-generated constructor stub
		try {
			server = new ServerSocket(8887);
			System.out.println("server is ready to accept connection");
			System.out.println("Wating......");
			server.accept();

			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			pr = new PrintWriter(socket.getOutputStream());

			startReading();
			startWriting();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void startWriting() {
		// TODO Auto-generated method stub
		// thread-writing
		Runnable r2 = () -> {
			System.out.println("writer started...");
			
				try {
					while (!socket.isClosed()) {
					BufferedReader b1 = new BufferedReader(new InputStreamReader(System.in));
					String content = b1.readLine();
					System.out.println(content);
					System.out.flush();
					if(content.equals("exit")) {
						socket.close();
						break;
					}
				}}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		
		};
		new Thread(r2).start();
	}

	private void startReading() {
		// TODO Auto-generated method stub
		// thread-reading
		Runnable r1 = () -> {
			System.out.println("reader staretd...");
			
				try {
					while (true) {
					String msg = br.readLine();
					if (msg.equals("exit")) {
						System.out.println("client terminate the chat...");
						socket.close();
						break;
					}
					System.out.println("client : " + msg);
				} }catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		};
		new Thread(r1).start();
		}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("this is server..going to start server");
		new server();
	}

}
