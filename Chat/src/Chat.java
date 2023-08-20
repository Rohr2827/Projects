//Jared Rohrbaugh, Carter Chinn. CET 350, Group 3, Chat.java
// Email roh2827@calu.edu, chi6709@calu.edu

import java.net.*;
import java.awt.*; 
import java.awt.event.*;
import java.io.*;

public class Chat implements Runnable, ActionListener, WindowListener

{
	private static final long serialVersionUID = 1L; 
	
	BufferedReader br; // makes BufferedReader
	PrintWriter pw; // makes PrintWriter
	
	Button ChangePortButton = new Button("Change Port");// makes button
	Button SendButton = new Button("Send");// makes button
	Button ServerButton = new Button("Start Server");// makes button
	Button ClientButton = new Button("Connect");// makes button
	Button DisconnectButton = new Button("Disconnect");// makes button
	Button ChangeHostButton = new Button("Change Host");// makes button
	
	Label PortLabel = new Label("Port:");// makes label
	Label HostLabel = new Label("Host:");// makes label
	
	TextField ChatText = new TextField(70);// makes textfield
	TextField PortText = new TextField(10);// makes textfield
	TextField HostText = new TextField(10);// makes textfield
	
	Frame DisFrame; // makes frame
	Thread TheThread;// makes thread
	
	TextArea DialogScreen = new TextArea ("", 10,80); //makes new text area
	TextArea MessageScreen = new TextArea ("",3,80);//makes new text area
	
	Socket client; // makes a socket
	Socket server; // makes a socket
	
	ServerSocket listen_socket; // makes a server socket
	
	String host = ""; 
	
	final int DEFAULT_PORT = 44004; 
	
	int port = DEFAULT_PORT;
	
	int service = 0; 
	int timeout = 1000;
	boolean more = true;
	protected final static boolean auto_flush = true; // makes a boolean and assigns it a true value
	
	
	public static void main(String[] args)// main method
	
	{
		new Chat(10); //Creates a new chat object
	}
	
	public Chat(int SocketWaits) // constructor
	{
	
		try
		{
			initComponents(); // initializes the components
						
		}
		
		catch(Exception e) {e.printStackTrace();}
	}
	
	public void initComponents() throws Exception, IOException // initComponents method
	{
		GridBagConstraints gbc = new GridBagConstraints(); // creates gridbag
		GridBagLayout displ = new GridBagLayout();
		double colWeight[] = {1,1,1,1,1,1,1,1};               //sets the weight of the gridbag col
		double rowWeight[] = {1,1,1,1,1,1,1,1};         //sets the weight of the gridbag row
		int colWidth[]= {1,1,1,1,1};                    //sets the size of the gridbag col
		int rowHeight[]= {1,1,1,1,1,1,1,1};             //sets the size of the gridbag row
		displ.rowHeights = rowHeight;
		displ.columnWidths = colWidth;  // displays rowHeight, columnWidths, columnWeights, rowWeights
		displ.columnWeights = colWeight;
		displ.rowWeights = rowWeight;
		
		service = 0;// sets service to 0
		more = true;// sets more to true
				
		DisFrame = new Frame(); // assigns a value to disframe
		
		DisFrame.setBounds(0,0,700,400); // sets the frame size
		DisFrame.setLayout(displ);
		gbc.anchor = GridBagConstraints.CENTER; // anchors the gridbag to center
		gbc.weightx=1;
		gbc.weighty=1;                
		gbc.gridwidth =1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH; 
		
		gbc.weightx = 8; //sets the x weight
		gbc.weighty = 1; //sets the y weight
		gbc.gridwidth =8; //sets the size 
		gbc.gridheight =1; //sets the size 
		gbc.gridx=2; //puts the button at spot gridx and gridy
		gbc.gridy=1;
		gbc.fill = GridBagConstraints.BOTH;
		displ.setConstraints(DialogScreen, gbc); // displays the textarea 
		DialogScreen.setVisible(true); // sets it visible
		DialogScreen.setEnabled(false);
		DisFrame.add(DialogScreen); //adds the textarea to the frame
		
		gbc.weightx = 1; //sets the x weight
		gbc.weighty = 1; //sets the y weight
		gbc.gridwidth =1; //sets the size of the button
		gbc.gridheight =1; //sets the size of the button
		gbc.gridx=8; //puts the button at spot gridx and gridy
		gbc.gridy=2;
		gbc.fill = GridBagConstraints.BOTH;
		displ.setConstraints(SendButton, gbc); // displays 
		SendButton.setVisible(true); // sets it visible
		SendButton.setEnabled(false);
		DisFrame.add(SendButton); //adds to frame
		
		gbc.weightx = 1; //sets the x weight
		gbc.weighty = 1; //sets the y weight
		gbc.gridwidth =1; //sets the size of the button
		gbc.gridheight =1; //sets the size of the button
		gbc.gridx=8; //puts the button at spot gridx and gridy
		gbc.gridy=3;
		gbc.fill = GridBagConstraints.BOTH;
		displ.setConstraints(ServerButton, gbc); // displays 
		ServerButton.setVisible(true); // sets it visible
		ServerButton.setEnabled(true);
		DisFrame.add(ServerButton); //adds to frame
		
		gbc.weightx = 1; //sets the x weight
		gbc.weighty = 1; //sets the y weight
		gbc.gridwidth =1; //sets the size of the button
		gbc.gridheight =1; //sets the size of the button
		gbc.gridx=8; //puts the button at spot gridx and gridy
		gbc.gridy=4;
		gbc.fill = GridBagConstraints.BOTH;
		displ.setConstraints(ClientButton, gbc); // displays
		ClientButton.setVisible(true); // sets it visible
		ClientButton.setEnabled(false);
		DisFrame.add(ClientButton); //adds to frame
		
		gbc.weightx = 1; //sets the x weight
		gbc.weighty = 1; //sets the y weight
		gbc.gridwidth =1; //sets the size of the button
		gbc.gridheight =1; //sets the size of the button
		gbc.gridx=8; //puts the button at spot gridx and gridy
		gbc.gridy=5;
		gbc.fill = GridBagConstraints.BOTH;
		displ.setConstraints(DisconnectButton, gbc); // displays 
		DisconnectButton.setVisible(true); // sets it visible
		DisconnectButton.setEnabled(false);
		DisFrame.add(DisconnectButton); //adds to frame
		
		gbc.weightx = 1; //sets the x weight
		gbc.weighty = 1; //sets the y weight
		gbc.gridwidth =1; //sets the size of the button
		gbc.gridheight =1; //sets the size of the button
		gbc.gridx=7; //puts the button at spot gridx and gridy
		gbc.gridy=3;
		gbc.fill = GridBagConstraints.BOTH;
		displ.setConstraints(ChangeHostButton, gbc); // displays 
		ChangeHostButton.setVisible(true); // sets it visible
		ChangeHostButton.setEnabled(true);
		DisFrame.add(ChangeHostButton); //adds to frame
		
		gbc.weightx = 1; //sets the x weight
		gbc.weighty = 1; //sets the y weight
		gbc.gridwidth =1; //sets the size 
		gbc.gridheight =1; //sets the size 
		gbc.gridx=7; //puts the button at spot gridx and gridy
		gbc.gridy=4;
		gbc.fill = GridBagConstraints.BOTH;
		displ.setConstraints(ChangePortButton, gbc); // displays the button 
		ChangePortButton.setVisible(true); // sets it visible
		ChangePortButton.setEnabled(true);
		DisFrame.add(ChangePortButton); //adds to frame
		
		gbc.weightx = 6; //sets the x weight
		gbc.weighty = 1; //sets the y weight
		gbc.gridwidth =6; //sets the size 
		gbc.gridheight =1; //sets the size 
		gbc.gridx=2; //puts the button at spot gridx and gridy
		gbc.gridy=2;
		gbc.fill = GridBagConstraints.BOTH;
		displ.setConstraints(ChatText, gbc); // displays the button 
		ChatText.setVisible(true); // sets it visible
		ChatText.setEnabled(false);
		DisFrame.add(ChatText); //adds to frame
		
		gbc.weightx = 3; //sets the x weight
		gbc.weighty = 1; //sets the y weight
		gbc.gridwidth =3; //sets the size 
		gbc.gridheight =1; //sets the size 
		gbc.gridx=3; //puts the button at spot gridx and gridy
		gbc.gridy=3;
		gbc.fill = GridBagConstraints.BOTH;
		displ.setConstraints(HostText, gbc); // displays the button 
		HostText.setVisible(true); // sets it visible
		HostText.setEnabled(true);
		DisFrame.add(HostText); //adds to frame
		
		gbc.weightx = 1; //sets the x weight
		gbc.weighty = 1; //sets the y weight
		gbc.gridwidth =1; //sets the size 
		gbc.gridheight =1; //sets the size 
		gbc.gridx=2; //puts the button at spot gridx and gridy
		gbc.gridy=3;
		gbc.fill = GridBagConstraints.BOTH;
		displ.setConstraints(HostLabel, gbc); // displays 
		HostLabel.setVisible(true); // sets it visible
		DisFrame.add(HostLabel); //adds to frame
		
		gbc.weightx = 1; //sets the x weight
		gbc.weighty = 1; //sets the y weight
		gbc.gridwidth =1; //sets the size 
		gbc.gridheight =1; //sets the size 
		gbc.gridx=2; //puts the button at spot gridx and gridy
		gbc.gridy=4;
		gbc.fill = GridBagConstraints.BOTH;
		displ.setConstraints(PortLabel, gbc); // displays 
		PortLabel.setVisible(true); // sets it visible
		DisFrame.add(PortLabel); ///adds to frame
		
		gbc.weightx = 3; //sets the x weight
		gbc.weighty = 1; //sets the y weight
		gbc.gridwidth =3; //sets the size
		gbc.gridheight =1; //sets the size 
		gbc.gridx=3; //puts the button at spot gridx and gridy
		gbc.gridy=4;
		gbc.fill = GridBagConstraints.BOTH;
		displ.setConstraints(PortText, gbc); // displays 
		PortText.setVisible(true); // sets it visible
		PortText.setEnabled(true);
		DisFrame.add(PortText); //adds to frame
		PortText.setText(String.valueOf(port));
		
		gbc.weightx = 8; //sets the x weight
		gbc.weighty = 1; //sets the y weight
		gbc.gridwidth =8; //sets the size 
		gbc.gridheight =1; //sets the size 
		gbc.gridx=2; //puts the button at spot gridx and gridy
		gbc.gridy=7;
		gbc.fill = GridBagConstraints.BOTH;
		displ.setConstraints(MessageScreen, gbc); // displays 
		MessageScreen.setVisible(true); // sets it visible
		MessageScreen.setEnabled(false);
		DisFrame.add(MessageScreen); //adds to frame
		
		ChangePortButton.addActionListener(this); // adds actionlistener
		SendButton.addActionListener(this); // adds actionlistener
		ServerButton.addActionListener(this); // adds actionlistener
		ClientButton.addActionListener(this); // adds actionlistener
		DisconnectButton.addActionListener(this); // adds actionlistener
		ChangeHostButton.addActionListener(this); // adds actionlistener
		
		ChatText.addActionListener(this);  // adds actionlistener
		PortText.addActionListener(this); // adds actionlistener
		HostText.addActionListener(this); // adds actionlistener
				
		DisFrame.setVisible(true);  // makes it so all the labels and buttons can be seen
		DisFrame.addWindowListener(this);
		DisFrame.validate();
	}
		

	public void start() // start method
	{
		if(TheThread == null)// checks if thread is null
		{
			TheThread = new Thread(this); // sets the thread
			TheThread.start(); // starts the thread
		}
	}
	
	public void run() // run method
	{
		TheThread.setPriority(Thread.MAX_PRIORITY); // sets thread to max priority
		
		MessageScreen.append("Chat Started:\n"); // sends message to the messagescreen
		ChatText.setEnabled(true);// sets textfield to true
		
		while(more)// while loop for reading messages between the client and server
		{
			try 
			{
				String line = br.readLine(); // making line and giving it a value
				
				if(line != null) // checking if line is not null
				{
					DialogScreen.append("in:"+line+"\n"); // sends message to the dialog screen
					
				}
				
				else
				{
					more = false; // sets more to false if line is null
				}
			}
			catch(IOException e) {}	
		}
		
		if(service == 1) // checks the value of service
		{
			MessageScreen.append("Server: Server disconnected\n"); // sends message to the messagescreen
		}
		
		if(service == 2) // checks the value of service
		{
			MessageScreen.append("Client: Client disconnected\n"); // sends message to the messagescreen
		}
		
		close(); // calls the close method
	}
	
	
	public void stop() // stop method
	
	{
		if(TheThread != null) // checks if the thread is not null 
		{
		TheThread.setPriority(Thread.MIN_PRIORITY); // sets thread to min priority
		}
		
		ChatText.removeActionListener(this); // removes the actionlisteners 
		PortText.removeActionListener(this);
		HostText.removeActionListener(this);
		ChangePortButton.removeActionListener(this);
		SendButton.removeActionListener(this);
		ServerButton.removeActionListener(this);
		ClientButton.removeActionListener(this);
		DisconnectButton.removeActionListener(this);
		ChangeHostButton.removeActionListener(this);
		DisFrame.removeWindowListener(this);// removes the WindowListener
		DisFrame.dispose();
		System.exit(0); // exits the program
	}
	
	public void close() // close method
	
	{
		try 
		{
			if(server!= null)  // checks if the server socket exist
			{
				if(pw != null) // checks if the pw exist			
				{
					pw.print(""); // send null to other devices	
					pw.close();// closes the pw and br
					br.close();
				}
								
				if(listen_socket != null) // checks if the listen_socket is not null
				{
				listen_socket.close(); // closes the listen_socket
				listen_socket = null; // nulls the listen socket
				}
			}
			
			if(client!=null)  // checks if the server socket exist
			{
				if(pw != null) // checks if the pw exist			
				{
					pw.print(""); // send null to other devices	
					pw.close(); // closes the pw and br
					br.close();
				}					
				
				if(listen_socket != null) // checks if the listen_socket is not null 
				{
				listen_socket.close(); // closes the listen_socket
				listen_socket = null; // nulls the listen_socket
				}
			}
		}
		
		catch(IOException e) {}
				
		ChatText.setEnabled(false);//sets the values of the buttons for the program
		DisconnectButton.setEnabled(false);
		SendButton.setEnabled(false);
		ServerButton.setEnabled(true);
		ClientButton.setEnabled(false);
		ChangeHostButton.setEnabled(true);
		ChangePortButton.setEnabled(true);
		DisFrame.setTitle("");// sets the title of the screen 
		service = 0; // sets service to 0
		TheThread = null;// nulls the thread
	}
	
	@Override
	public void windowOpened(WindowEvent e) 
	{
		ChatText.requestFocus(); // sets the typing to the chatText
		
	}

	@Override
	public void windowClosing(WindowEvent e) 
	{
		if(TheThread != null) // checks if the thread is not nul
		{
			MessageScreen.append("Closing\n"); // sends message to the messagescreen
			pw.println(""); // sets pw to ""
			TheThread.interrupt(); // interupts the thread
			close(); // calls the close method
		}
		
		stop(); // calls the stop method
	}

	@Override
	public void windowClosed(WindowEvent e) 
	{
		ChatText.requestFocus();
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		ChatText.requestFocus();
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		ChatText.requestFocus();
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		ChatText.requestFocus();
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		ChatText.requestFocus();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) // actionperformed method
	{
		
		String data = ChatText.getText(); // sets the string data to a value
				
		Object source = e.getSource(); //sets the object source to a value
				
		if(source == ChatText || source == SendButton) // checks if you hit the send button 
		{
			if(!ChatText.getText().equals("")) // checks if the textfield is not empty
			{
				DialogScreen.append("out:"+data+"\n");// sends message to the dialogScreen
				pw.println(data); // gives the pw data
			
				ChatText.setText(""); // sets the textfield to ""
			}
		}
		
		if(source == ServerButton) // checks if you hit the serverbutton
			
		{
		
		MessageScreen.append("Server: listening on port "+PortText.getText()+"\n"); // sends message to the messagescreen
					
			try {
				
				ServerButton.setEnabled(false);//sets the server and client buttons to false
				ClientButton.setEnabled(false);//
				
				if(listen_socket != null) // checks if the listen_socket is not null
				{
				
				listen_socket.close(); // closes the listen_socket
				listen_socket = null;
				}
				
				listen_socket = new ServerSocket(port); // gives listen_socket a value
				MessageScreen.append("Server: timeout time set to "+10*timeout+" ms\n");// sends message to the messagescreen
				MessageScreen.append("Server: waiting for a request on "+PortText.getText()+"\n"); // sends message to the messagescreen
				
				listen_socket.setSoTimeout(10*timeout); // sets the timeout delay
				
				if(client != null) // checks if the client is not null
				{
					client.close();// closes the client
					client = null; 
				}
				
				try
				{
					client = listen_socket.accept(); // sets the client 
					DisFrame.setTitle("Server: connection from" + client.getInetAddress());// sets the title of the server screen
					MessageScreen.append("Server: connection from" + client.getInetAddress()+"\n");// sends message to the messagescreen
					
					DisconnectButton.setEnabled(true);// sets the values of the buttons
					SendButton.setEnabled(true);
					ChangeHostButton.setEnabled(false);
					ChangePortButton.setEnabled(false);
					
				try
				{
					br = new BufferedReader (new InputStreamReader (client.getInputStream()));//sets br
					pw = new PrintWriter(client.getOutputStream(), auto_flush);	// sets pw
					
					service = 1; // sets service to 1
					
					more = true;// sets ,ore to true
					
					start(); // calls the start method
					
				}catch(IOException er) 
				
				{
				MessageScreen.append("I/O error\n");// sends message to the messagescreen
				
				ServerButton.setEnabled(true);//sets the values of the buttons
				ClientButton.setEnabled(true);//
				close(); // calls the close method
				}
				
			} catch (SocketTimeoutException s) 
				
			{
				MessageScreen.append("Timed out\n"); // sends message to the messagescreen
				ServerButton.setEnabled(true);//sets the values of the buttons
				ClientButton.setEnabled(true);//
				close(); // calls the close method
				
			}
				
			} catch (IOException e1) 
			
			{
				
			MessageScreen.append("I/O error\n"); // sends message to the messagescreen
			
			ServerButton.setEnabled(true);//sets the values of the buttons
			ClientButton.setEnabled(true);//
			close(); // calls the close methods
			}
			
		}// end ServerButton
		
		
		if(source == ClientButton)//checks if you hit the client button 
		{
			MessageScreen.append("Connecting to "+HostText.getText()+":"+PortText.getText()+"\n");// sends message to the messagescreen
			
			try
			{
				ServerButton.setEnabled(false);//sets the values of the buttons
				ClientButton.setEnabled(false);//
				
				if(server != null) // checks if the server is not null
				{
					server.close(); // closes the server
					server = null;
				}
				
				server = new Socket();// sets the server
				server.setSoTimeout(timeout);
				
			try
			{
				server.connect(new InetSocketAddress(host,port));// connects the server
				DisFrame.setTitle("Client: connected to "+server.getInetAddress()+" at port "+PortText.getText());//sets the title of the client screen 
				MessageScreen.append("Client: connected to "+server.getInetAddress()+" at port "+PortText.getText()+"\n");// sends message to the messagescreen
				
			try
			{
				br = new BufferedReader (new InputStreamReader (server.getInputStream()));// sets the br
				pw = new PrintWriter (server.getOutputStream(), auto_flush);// sets the pw
				
				service = 2;// sets the service value
				more = true;// sets more to true
				
				ChatText.setEnabled(true);//sets the value of the buttons
				DisconnectButton.setEnabled(true);
				SendButton.setEnabled(true);
				ChangeHostButton.setEnabled(false);
				ChangePortButton.setEnabled(false);
				
				
				start();// calls start method
				MessageScreen.append("Client: Chat is running\n");// sends message to the messagescreen
				
			} catch(IOException er) 
			
			{
			MessageScreen.append("I/O error\n");// sends message to the messagescreen
			
			ServerButton.setEnabled(true);//sets the values of the buttons
			ClientButton.setEnabled(true);//
			close();// calls the close method
			}
				
				
			} catch(SocketTimeoutException s) 
			
			{
				MessageScreen.append("Timed out\n");// sends message to the messagescreen
				
				ServerButton.setEnabled(true);//sets the values of the buttons
				ClientButton.setEnabled(true);//
				close();// calls the close method
			}
				
			} catch(IOException er) 
			
			{
			MessageScreen.append("I/O error\n");// sends message to the messagescreen
			
			ServerButton.setEnabled(true);//sends the values of the buttons
			ClientButton.setEnabled(true);//
			close();// calls the close method
			}
			
		}// end ClientButton
		
		if(source == DisconnectButton) // checks if you click the disconnect button
		{
			if(TheThread != null)// checks if the thread is not null
			{
				if(service == 1) // checks if service is 1
				{
					MessageScreen.append("Server: Server disconnected\n");// sends message to the messagescreen
				}
				
				if(service == 2)// checks if service is 2 
				{
					MessageScreen.append("Client: Client disconnected\n");// sends message to the messagescreen
				}
			
			pw.println(""); 
			DisFrame.setTitle("");//sets the title 
	
			TheThread.interrupt();// interupts the thread
			close();// calls the close method 
			
			}
		}
		
		if(source == HostText || source == ChangeHostButton)// checks if you click the change host button
			
		{
			String HText =	HostText.getText();// sets the HText string 
			
			if(HText.isEmpty() == false)// checks if HText is empty
			{
				ClientButton.setEnabled(true); //sets the value of the button
			}
		}
		
		if(source == PortText || source == ChangePortButton)// checks if you click the change port button
		{
			String PText = PortText.getText();// sets the PText string 
			
			try
			{
				port = Integer.parseInt(PText);// sets port 
				
				if(host.isEmpty() == false) // checks if the host is empty
				{
				
				ClientButton.setEnabled(true); // sets the button value 
				}
					
			}catch(NumberFormatException a) {}
		}
		
		ChatText.requestFocus(); 
		
	}

	
		

}// end class
