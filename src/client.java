import java.net.*;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class client extends JFrame{

	Socket socket;

	BufferedReader br2;
	PrintWriter pr;
	
	//Delcare component
	private JLabel heading = new JLabel("Client Area");
	private JTextArea messageArea = new JTextArea();
	private JTextField messageInput = new JTextField();
	private Font font = new Font("Roboto",Font.PLAIN,20);

	public client() {
		// TODO Auto-generated constructor stub
		try {
			
			System.out.println("Sending request");
			socket = new Socket("127.0.0.1", 8887);
			System.out.println("connection done...");

			br2 = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			pr = new PrintWriter(socket.getOutputStream());

			
	//		startWriting();
			createGUI();
			handleEvents();
			startReading();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void handleEvents() {
		// TODO Auto-generated method stub
		messageInput.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			//	System.out.println("key released "+e.getKeyCode());
				if(e.getKeyCode() == 10) {
					System.out.println("you have pass enter button");
					String contentTosend = messageInput.getText();
					messageArea.append("ME :"+contentTosend+"\n");
					System.out.println(contentTosend);
					System.out.flush();
					messageInput.setText("");
					messageInput.requestFocus();
				}
				 
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	private void createGUI() {
		// TODO Auto-generated method stub
		this.setTitle("Client Messager[END]");
		this.setSize(700,700);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//coding for component
		heading.setFont(font);
		messageArea.setFont(font);
		messageInput.setFont(font);
		
		heading.setHorizontalAlignment(SwingConstants.CENTER);
		heading.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));
		
		messageInput.setHorizontalAlignment(SwingConstants.CENTER);
		
		//frame layout
		this.setLayout(new BorderLayout());
		
		//adding component to frame
		this.add(heading,BorderLayout.NORTH);
		this.add(messageArea,BorderLayout.CENTER);
		this.add(messageInput,BorderLayout.SOUTH);
		
		this.setVisible(true);
	}

	private void startWriting() {
		// TODO Auto-generated method stub
		// thread-writing
		Runnable r2 = () -> {
			System.out.println("writer started...");
			while (!socket.isClosed()) {
				try {
					BufferedReader b1 = new BufferedReader(new InputStreamReader(System.in));
					String content = b1.readLine();
					System.out.println(content);
					System.out.flush();
					if (content.equals("exit")) {
						socket.close();
						break;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
					String msg = br2.readLine();
					if (msg.equals("exit")) {
						System.out.println("server terminate the chat...");
						JOptionPane.showMessageDialog(this, "server terminated chat");
						messageInput.setEnabled(false);
						socket.close();
						break;
					}
					//System.out.println("server : " + msg);
					messageArea.append("server : " +msg+"\n");
				} }catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		};
		new Thread(r1).start();

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("this is cilent server...");
		new client();
	}

}
