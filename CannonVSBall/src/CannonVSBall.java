//Jared Rohrbaugh, Carter Chinn. CET 350, Group 3, CannonVSBall.java
// Email roh2827@calu.edu, chi6709@calu.edu

import java.awt.*; 
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class CannonVSBall extends Frame implements ItemListener, MouseListener, MouseMotionListener, WindowListener, ComponentListener,ActionListener,AdjustmentListener,Runnable
{
		
	private static final long serialVersionUID = 10L;
	
	private Point m1 = new Point(0,0);// initial mouse position
	private Point m2 = new Point(0,0);// current mouse position
	
	private static int ScreenWidth; // drawing screen width
	private static int ScreenHeight; // drawing screen height
	
	private Rectangle Perimeter = new Rectangle(0,0,ScreenWidth,ScreenHeight);// bouncing perimeter
	private Rectangle ObjBoundary = new Rectangle();// create Rectangle ObjBoundary
	private Rectangle r = new Rectangle();// create Rectangle r
	private Rectangle t = new Rectangle();// create Rectangle t
	private Rectangle db = new Rectangle(); // create Rectangle db
	
	private final int WIDTH = 640; // initial frame width
	private final int HEIGHT = 400; // initial frame height
	private final int BUTTONH = 20; // button height 
	private final int BUTTONHS = 5; // button height spacing
	private int WinWidth = WIDTH; // frame width
	private int WinHeight = HEIGHT; // frame height
	private int WinTop = 10; // top of frame
	private int WinLeft = 10; // left side of frame
	private int BUTTONW = 50; // button width
	private int CENTER = (WIDTH/2); // screen center
	
	private final int MAXObj = 100; //max object size
	private final int MINObj =10; // min object size
	private final int ANGLE = 45; // initial SPEED
	private final int VELOCITY = 100;	
	private final int SBvisible = 10; //visible scroll bar
	private final int SBunit =1; //scroll bar unit step size
	private final int SBblock=10; // scroll bar block step size
	private final int SCROLLBARH = BUTTONH; //scroll bar height
	private final int SOBJ =60; // initial object width
	private int SObj=SOBJ; //initial object width
	private int CannonAngleMin =0; // connon angle scrollbar minimum value
	private int CannonAngleMax = 90+SBvisible; // cannon angle scrollbar minimum value with visible offset
	private int CannonAngleUnit = ANGLE; // SPEED scrollbar value
	private int VelocityMin = 100; // minimum velocity 
	private int VelocityMax = 1200+SBvisible; // max velocity
	private int VelocityUnit = VELOCITY; // starts at 100
	private int ScrollBarW; // scrollbar width
	
	private int delay = 3; //makes the delay
	private int SBEqualizer = 0;
	
	private Objc Obj; // object to draw 
	private Label YourScore = new Label("Your Score",Label.CENTER); // label for score
	private Label BallScore = new Label("Ball Score",Label.CENTER); // label for score
	private Label CannonAngle = new Label("Cannon Angle",Label.CENTER); // label for scrollbar
	private Label Velocity = new Label("Velocity",Label.CENTER); // label for scrollbar
	private Label Bounds = new Label("Bounds",Label.CENTER); // label for bounds
	Scrollbar CannonAngleScrollBar, VelocityScrollBar; // scroll bars
		
	private Insets I; // insets of frame
	private Thread theThread; // makes the thread theThread
	
	boolean run = true; // makes boolean run
	static boolean TimeIsPaused = false; // makes boolean TimeIsPaused                                                                            \
	boolean started = false; // makes boolean started
	boolean ok;// makes boolean ok
	boolean moveProjectile = false; // makes boolean moveProjectile
	
	private Panel sheet = new Panel(); // makes panel sheet
	private Panel control = new Panel(); // makes panel control
	
	//private TextArea EditArea; 
	TextField CannonAngleBox = new TextField(null); // creates a textfield called CannonAngleBox
	TextField VelocityBox = new TextField(null); // creates a textfield called VelocityBox
	static TextField YourScoreBox = new TextField(null); // creates a textfield called YourScoreBox
	static TextField BallScoreBox = new TextField(null); // creates a textfield called BallScoreBox
	static TextField BoundsInfo = new TextField(null); // textfield for bound info
	private MenuBar MMB; // menu bar
	private Menu CONTROL, PARAMETERS, ENVIRONMENT; // main items on menu bar
	private Menu SIZE, SPEED;// sub menu items under menue bar 
	private MenuItem PAUSE,RUN,RESTART;// sub menu items under menue bar
	private MenuItem QUIT;// menu item 
	private CheckboxMenuItem MERCURY,VENUS,EARTH,MOON,MARS,JUPITER,SATURN,URANUS,NEPTUNE,PLUTO; // checkbox menu items
	private CheckboxMenuItem XSMALL,SMALL,MEDIUM,LARGE,XLARGE; //checkbox menu items for size
	private CheckboxMenuItem XSLOW,SLOW,SMEDIUM,FAST,XFAST; //checkbox menu items for SPEED
			
	public CannonVSBall() // Bounce Constructor
	{
		setLayout(new BorderLayout()); // BorderLayout layout frame
		setVisible(true); // makes it visible
		MakeSheet(); // determines the size of the sheet
		try
		{
			initComponents(); // initializes the components
						
		}
		
		catch(Exception e) {e.printStackTrace();}
		
		SizeScreen(); // size of components on screen
		start(); // begins the thread
	}//end bounce
	
public static void main(String[] args)
	
	{
		new CannonVSBall(); //Creates a new Bounce object
	}


public void initComponents() throws Exception, IOException // initComponents method

{
	this.setBounds(Perimeter); //sets the bounds of the perimeter
	this.setVisible(true); // makes it visible
	
	double colWeight[] = {1,1,1,1,1,1,1,1,1,1,1,1,1};//sets the weight of the gridbag col
	double rowWeight[] = {1,1,1,1,1};//sets the weight of the gridbag row
	
	int colWidth[]= {1,1,1,1,1,1,1,1,1,1,1,1,1};//sets the size of the gridbag col
	int rowHeight[]= {1,1,1,1,1};//sets the size of the gridbag row
	
	
	GridBagConstraints gbc = new GridBagConstraints(); // makes new GridBagConstraints
	GridBagLayout gbl = new GridBagLayout(); // makes new GridBagLayout
	BorderLayout bl = new BorderLayout(); // makes new BorderLayout
	
	gbl.rowHeights = rowHeight; 
	gbl.columnWidths = colWidth;  // displays rowHeight, columnWidths, columnWeights, rowWeights
	gbl.columnWeights = colWeight;
	gbl.rowWeights = rowWeight;
			
	CannonAngleScrollBar= new Scrollbar(Scrollbar.HORIZONTAL); // creates scrollbar
	CannonAngleScrollBar.setMaximum(CannonAngleMax); // sets max SPEED
	CannonAngleScrollBar.setMinimum(CannonAngleMin); // sets min SPEED
	CannonAngleScrollBar.setUnitIncrement(SBunit); // sets the unit increment
	CannonAngleScrollBar.setBlockIncrement(SBblock); // sets the block increment
	CannonAngleScrollBar.setValue(CannonAngleUnit); // sets initial value
	CannonAngleScrollBar.setVisibleAmount(SBvisible); // sets the visible size
	CannonAngleScrollBar.setBackground(Color.gray); // sets the background color
	
	VelocityScrollBar = new Scrollbar(Scrollbar.HORIZONTAL); // creates scrollbar
	VelocityScrollBar.setMaximum(VelocityMax); // sets max velocity
	VelocityScrollBar.setMinimum(VelocityMin); // sets min velocity
	VelocityScrollBar.setUnitIncrement(SBunit); // sets the unit increment
	VelocityScrollBar.setBlockIncrement(SBblock); // sets the block increment
	VelocityScrollBar.setValue(VelocityUnit);  // sets initial value
	VelocityScrollBar.setVisibleAmount(SBvisible); // sets the visible size
	VelocityScrollBar.setBackground(Color.gray); // sets the background color
	
	Obj = new Objc(SObj, ScreenWidth, ScreenHeight); // creates the object
	Obj.setBackground(Color.white); // sets the background color
		
	add(CannonAngleScrollBar); // adds scrollbar to frame
	add(VelocityScrollBar); // adds scrollbar to frame
	add(CannonAngle); //adds label to the frame
	add(Velocity); //adds label to the frame
	add(Obj); // adds object to the frame
		
	m1.setLocation(0,0); //sets the m1 point location                                                       
	m2.setLocation(0,0); //sets the m2 point location  
	Perimeter.setBounds(0,0,ScreenWidth-1,ScreenHeight-1); // sets the bounds of the perimeter
	//Perimeter.grow(-1, -1); //grows the by 1
	setLayout(new BorderLayout()); // sets the border layout
	setBounds(WinLeft, WinTop, WIDTH, HEIGHT); // sets the borderlayout's bounds
	setBackground(Color.lightGray); // sets the color
	setVisible(true);// makes it visible
	
	control.setLayout(gbl); // sets controls layout to the gridbag
		
	gbc.weightx = 1; //sets the x weight
	gbc.weighty = 1; //sets the y weight
	gbc.gridwidth =1; //sets the size of the scrollbar
	gbc.gridheight =1; //sets the size of the scrollbar
	gbc.gridx=13; //sets location of gridx and gridy
	gbc.gridy=1;
	gbc.fill = GridBagConstraints.BOTH;
	gbl.setConstraints(CannonAngleScrollBar, gbc); // displays the scrollbar
	CannonAngleScrollBar.setVisible(true); // sets it visible
	control.add(CannonAngleScrollBar); //adds the Scrollbar to control
	
	gbc.weightx = 1; //sets the x weight
	gbc.weighty = 1; //sets the y weight
	gbc.gridwidth =1; //sets the size of the label
	gbc.gridheight =1; //sets the size of the label
	gbc.gridx=12; //sets location of gridx and gridy
	gbc.gridy=1;
	gbc.anchor = GridBagConstraints.CENTER;
	gbc.fill = GridBagConstraints.BOTH;
	gbl.setConstraints(CannonAngle, gbc); // displays the label
	CannonAngle.setVisible(true); // sets it visible
	control.add(CannonAngle); //adds the label to control
	
	gbc.weightx = 1; //sets the x weight
	gbc.weighty = 1; //sets the y weight
	gbc.gridwidth =1; //sets the size of the label
	gbc.gridheight =1; //sets the size of the label
	gbc.gridx=11; //sets location of gridx and gridy
	gbc.gridy=1;
	gbc.anchor = GridBagConstraints.CENTER;
	gbc.fill = GridBagConstraints.BOTH;
	gbl.setConstraints(CannonAngleBox, gbc); // displays the label
	control.add(CannonAngleBox); //adds the label to control
	CannonAngleBox.setEnabled(false);
	
	gbc.weightx = 1;//sets the x weight
	gbc.weighty = 1;//sets the y weight
	gbc.gridwidth =1; //sets the size of the scrollbar
	gbc.gridheight =1;
	gbc.gridx=13; //sets location of gridx and gridy
	gbc.gridy=4;
	gbc.fill = GridBagConstraints.BOTH;
	gbl.setConstraints(VelocityScrollBar, gbc); // displays the scrollbar
	VelocityScrollBar.setVisible(true); // sets it visible
	control.add(VelocityScrollBar); //adds the Scrollbar to control
	
	gbc.weightx = 1;//sets the x weight
	gbc.weighty = 1;//sets the y weight
	gbc.gridwidth =1; //sets the size of the label
	gbc.gridheight =1;
	gbc.gridx=12; //sets location of gridx and gridy
	gbc.gridy=4;
	gbc.anchor = GridBagConstraints.CENTER;
	gbc.fill = GridBagConstraints.BOTH;
	gbl.setConstraints(Velocity, gbc); // displays the label
	Velocity.setVisible(true); // sets it visible
	control.add(Velocity); // adds label to control
	
	gbc.weightx = 1; //sets the x weight
	gbc.weighty = 1; //sets the y weight
	gbc.gridwidth =1; //sets the size of the label
	gbc.gridheight =1; //sets the size of the label
	gbc.gridx=11; //sets location of gridx and gridy
	gbc.gridy=4;
	gbc.anchor = GridBagConstraints.CENTER;
	gbc.fill = GridBagConstraints.BOTH;
	gbl.setConstraints(VelocityBox, gbc); // displays the label
	control.add(VelocityBox); //adds the label to control
	VelocityBox.setEnabled(false);
	
	gbc.weightx = 1; //sets the x weight
	gbc.weighty = 1; //sets the y weight
	gbc.gridwidth =1; //sets the size of the label
	gbc.gridheight =1; //sets the size of the label
	gbc.gridx=1; //sets location of gridx and gridy
	gbc.gridy=1;
	gbc.anchor = GridBagConstraints.CENTER;
	gbc.fill = GridBagConstraints.BOTH;
	gbl.setConstraints(YourScore, gbc); // displays the label
	YourScore.setVisible(true); // sets it visible
	control.add(YourScore); //adds the label to control
	
	gbc.weightx = 1; //sets the x weight
	gbc.weighty = 1; //sets the y weight
	gbc.gridwidth =1; //sets the size of the text field 
	gbc.gridheight =1; //sets the size of the text field
	gbc.gridx=1; //sets location of gridx and gridy
	gbc.gridy=4;
	gbc.anchor = GridBagConstraints.CENTER;
	gbc.fill = GridBagConstraints.BOTH;
	gbl.setConstraints(YourScoreBox, gbc); // displays the label
	control.add(YourScoreBox); //adds the label to control
	YourScoreBox.setEnabled(false);
	
	gbc.weightx = 1; //sets the x weight
	gbc.weighty = 1; //sets the y weight
	gbc.gridwidth =1; //sets the size of the label
	gbc.gridheight =1; //sets the size of the label
	gbc.gridx=3; //sets location of gridx and gridy
	gbc.gridy=1;
	gbc.anchor = GridBagConstraints.CENTER;
	gbc.fill = GridBagConstraints.BOTH;
	gbl.setConstraints(BallScore, gbc); // displays the label
	BallScore.setVisible(true); // sets it visible
	control.add(BallScore); //adds the label to control
	
	gbc.weightx = 1; //sets the x weight
	gbc.weighty = 1; //sets the y weight
	gbc.gridwidth =1; //sets the size of the text field
	gbc.gridheight =1; //sets the size of the text field
	gbc.gridx=3; //sets location of gridx and gridy
	gbc.gridy=4;
	gbc.anchor = GridBagConstraints.CENTER;
	gbc.fill = GridBagConstraints.BOTH;
	gbl.setConstraints(BallScoreBox, gbc); // displays the label
	control.add(BallScoreBox); //adds the label to control
	BallScoreBox.setEnabled(false);	
		
	gbc.weightx = 1; //sets the x weight
	gbc.weighty = 1; //sets the y weight
	gbc.gridwidth =1; //sets the size of the label
	gbc.gridheight =1; //sets the size of the label
	gbc.gridx=7; //sets location of gridx and gridy
	gbc.gridy=1;
	gbc.anchor = GridBagConstraints.CENTER;
	gbc.fill = GridBagConstraints.BOTH;
	gbl.setConstraints(Bounds, gbc); // displays the label  
	Bounds.setVisible(true); // sets it visible
	control.add(Bounds); //adds the label to control
	
	gbc.weightx = 1; //sets the x weight
	gbc.weighty = 1; //sets the y weight
	gbc.gridwidth =1; //sets the size of the label
	gbc.gridheight =1; //sets the size of the label
	gbc.gridx=7; //sets location of gridx and gridy
	gbc.gridy=4;
	gbc.anchor = GridBagConstraints.CENTER;
	gbc.fill = GridBagConstraints.BOTH;
	gbl.setConstraints(BoundsInfo, gbc); // displays the label
	control.add(BoundsInfo); //adds the label to control
	BoundsInfo.setEnabled(false);
		
	control.setBackground(Color.lightGray); //sets the background color
	control.setSize(WIDTH, 2*BUTTONH);
	control.setVisible(true);
	
	sheet.setLayout(new BorderLayout(0,0)); // sheet boarder layout                 
	Obj = new Objc(SObj, ScreenWidth, ScreenHeight);// makes new Objc
	Obj.setBackground(Color.white); // sets the color
	sheet.add("Center", Obj); // adds the center to the sheet
	
	MMB= new MenuBar(); // create menu bar
	CONTROL= new Menu("CONTROL"); // create first menu entry for menu bar
	PAUSE= CONTROL.add(new MenuItem("Pause",new MenuShortcut(KeyEvent.VK_P))); // create menu item entry for control menu
	RUN= CONTROL.add(new MenuItem("Run",new MenuShortcut(KeyEvent.VK_R))); // create menu item entry for control menu
	RESTART= new MenuItem("Restart"); // create menu item entry for control menu
		
	CONTROL.add(PAUSE); // add menu new to Control menu
	CONTROL.addSeparator(); // add separator to Control menu
	CONTROL.add(RUN); // add menu new to file menu
	CONTROL.addSeparator(); // add separator to Control menu
	CONTROL.add(RESTART); // add menu new to file menu
	CONTROL.addSeparator(); // add separator to Control menu
	
	//add MenuItem Quit with short cut key to Control menu
	//QUIT=CONTROL.add(new MenuItem("Quit",new MenuShortcut(KeyEvent.VK_Q)));
	CONTROL.add(QUIT = new MenuItem("Quit",new MenuShortcut(KeyEvent.VK_Q)));
	
	PARAMETERS = new Menu("PARAMETERS"); // create second menu entry for menu bar
	SIZE = new Menu("Size"); // create menu item entry for Parameters menu
	SPEED = new Menu("Speed"); // create menu item entry for Parameters menu 
	
	SIZE.add(XSMALL = new CheckboxMenuItem("x-small")); // checkboxMenuItem size xsmall
	SIZE.addSeparator(); // add separator to size menu
	SIZE.add(SMALL = new CheckboxMenuItem("small")); // checkboxMenuItem size small
	SIZE.addSeparator(); // add separator to size menu
	SIZE.add(MEDIUM = new CheckboxMenuItem("medium")); // checkboxMenuItem size medium
	SIZE.addSeparator(); // add separator to size menu
	SIZE.add(LARGE = new CheckboxMenuItem("large")); // checkboxMenuItem size large
	SIZE.addSeparator(); // add separator to size menu
	SIZE.add(XLARGE = new CheckboxMenuItem("x-large")); // checkboxMenuItem size xlarge
	
	SPEED.add(XSLOW = new CheckboxMenuItem("x-slow")); // checkboxMenuItem SPEED xslow
	SPEED.addSeparator(); // add separator to SPEED menu
	SPEED.add(SLOW = new CheckboxMenuItem("slow")); // checkboxMenuItem SPEED slow
	SPEED.addSeparator(); // add separator to SPEED menu
	SPEED.add(SMEDIUM = new CheckboxMenuItem("medium")); // checkboxMenuItem SPEED medium
	SPEED.addSeparator(); // add separator to SPEED menu
	SPEED.add(FAST = new CheckboxMenuItem("fast")); // checkboxMenuItem SPEED fast
	SPEED.addSeparator(); // add separator to SPEED menu
	SPEED.add(XFAST = new CheckboxMenuItem("x-fast")); // checkboxMenuItem SPEED xfast
	
	MEDIUM.setState(true); // initialize Checkbox selection to medium
	PARAMETERS.add(SIZE); // add Size menu to Parameters Menu
	SMEDIUM.setState(true); // initialize Checkbox selection to medium
	PARAMETERS.add(SPEED); // add Size menu to Parameters Menu
	
	
	ENVIRONMENT= new Menu("ENVIRONMENT"); // create first menu entry for menu bar
	ENVIRONMENT.add(MERCURY = new CheckboxMenuItem("Mercury 12.14 ft/s/s")); // add menu new to Environment menu
	ENVIRONMENT.addSeparator(); // add separator to environment menu
	ENVIRONMENT.add(VENUS  = new CheckboxMenuItem("Venus 29.10 ft/s/s"));  // add menu new to Environment menu
	ENVIRONMENT.addSeparator(); // add separator to environment menu
	ENVIRONMENT.add(EARTH = new CheckboxMenuItem("Earth 32.14 ft/s/s")); // add menu new to Environment menu
	EARTH.setState(true); // initialize Checkbox selection to medium
	ENVIRONMENT.addSeparator(); // add separator to environment menu
	ENVIRONMENT.add(MOON = new CheckboxMenuItem("Moon 5.31 ft/s/s")); // add menu new to Environment menu
	ENVIRONMENT.addSeparator(); // add separator to environment menu
	ENVIRONMENT.add(MARS = new CheckboxMenuItem("Mars 12.17 ft/s/s")); // add menu new to Environment menu
	ENVIRONMENT.addSeparator(); // add separator to environment menu
	ENVIRONMENT.add(JUPITER = new CheckboxMenuItem("Jupiter 81.76 ft/s/s")); // add menu new to Environment menu
	ENVIRONMENT.addSeparator(); // add separator to environment menu
	ENVIRONMENT.add(SATURN = new CheckboxMenuItem("Saturn 34.25 ft/s/s")); // add menu new to Environment menu
	ENVIRONMENT.addSeparator(); // add separator to environment menu
	ENVIRONMENT.add(URANUS = new CheckboxMenuItem("Uranus 29.10 ft/s/s")); // add menu new to Environment menu
	ENVIRONMENT.addSeparator(); // add separator to environment menu
	ENVIRONMENT.add(NEPTUNE  = new CheckboxMenuItem("Neptune 36.58 ft/s/s")); // add menu new to Environment menu
	ENVIRONMENT.addSeparator(); // add separator to environment menu
	ENVIRONMENT.add(PLUTO = new CheckboxMenuItem("Pluto 1.90 ft/s/s")); // add menu new to Environment menu
	
	
	MMB.add(CONTROL); // add Control to menu bar 
	MMB.add(PARAMETERS); // add parameters to menu bar 
	MMB.add(ENVIRONMENT); // add environment to menu bar 
	
	PAUSE.addActionListener(this); // add listener for the file menu
	RUN.addActionListener(this); // add listener for the file menu
	RESTART.addActionListener(this); // add listener for the file menu
	QUIT.addActionListener(this); // add listener for the file menu
	
	MERCURY.addItemListener(this);// add listener
	VENUS.addItemListener(this);// add listener
	EARTH.addItemListener(this);// add listener
	MOON.addItemListener(this);// add listener
	MARS.addItemListener(this);// add listener
	JUPITER.addItemListener(this);// add listener
	SATURN.addItemListener(this);// add listener
	URANUS.addItemListener(this);// add listener
	NEPTUNE.addItemListener(this);// add listener
	PLUTO.addItemListener(this); // add listener
	
	XSMALL.addItemListener(this); // add listener for parameter size 
	SMALL.addItemListener(this); // add listener for parameter size 
	MEDIUM.addItemListener(this); // add listener for parameter size 
	LARGE.addItemListener(this); // add listener for parameter size 
	XLARGE.addItemListener(this); // add listener for parameter size 
	
	XSLOW.addItemListener(this); // add listener for parameter SPEED
	SLOW.addItemListener(this); // add listener for parameter SPEED
	SMEDIUM.addItemListener(this); // add listener for parameter SPEED
	FAST.addItemListener(this); // add listener for parameter SPEED
	XFAST.addItemListener(this); // add listener for parameter SPEED
	
	CannonAngleBox.addActionListener(this); //creates an action listener for CannonAngleBox
	VelocityBox.addActionListener(this); //creates an action listener for VelocityBox
	YourScoreBox.addActionListener(this); //creates an action listener for YourScoreBox
	BallScoreBox.addActionListener(this); //creates an action listener for BallScoreBox
	
	CannonAngleBox.setText("45");// sets initial text field value
	VelocityBox.setText("100");// sets initial text field value
	YourScoreBox.setText("0");// sets initial text field value
	BallScoreBox.setText("0");// sets initial text field value
	BoundsInfo.setText("not shot");// sets initial text field value
		
	setMenuBar(MMB); // add menu bar to frame
	setResizable(true);
	validate();
		
	sheet.setVisible(true); // sets it visible
	
	add("Center",sheet); // adds center to sheet
	add("South",control); // adds south to control
	validate();
	Obj.addMouseMotionListener(this); //adds MouseMotionListener
	Obj.addMouseListener(this); // adds MouseListener
	Obj.addComponentListener(this); //adds ComponentListener
	this.addWindowListener(this); //adds WindowListener
	this.addComponentListener(this); // adds ComponentListener
	
	CannonAngleScrollBar.addAdjustmentListener(this); //adds Scrollbar to the action listener
	VelocityScrollBar.addAdjustmentListener(this); //adds Scrollbar to the action listener	
}

private void MakeSheet() // gets the insets and adjusts the sizes                      
{
	I=getInsets();

	ScreenWidth = WinWidth -I.left - I.right; //sets screenwidth
	ScreenHeight = WinHeight-I.top-2*(BUTTONH + BUTTONHS)-I.bottom; //sets screenheight
	CENTER = (ScreenWidth/2); // determines the center of the screen 
	setSize(WinWidth, WinHeight); // sets the frame size
		
}

private void SizeScreen()
{
	Obj.setBounds(0,0,ScreenWidth,ScreenHeight); //sets the screen size
}

public void run() //run function   13:22
{
	theThread.setPriority(Thread.MAX_PRIORITY); // sets thread to max priority
	
	while(run == true) // starts a loop for to see if it is running
	{
		if(TimeIsPaused == false)//checks to see if it is not paused
		{
			started = true;
			
		
			try 
			{
				theThread.sleep(delay); //sleeps the thread for the delay time
			} 
			
			catch (InterruptedException e) {}
			
			
			Obj.repaint(); // repaints the frame
			Obj.move();//calls move function
			
			if(moveProjectile)
			{
				Obj.CannonBall();
			}
			
		}
		
		try 
		{
			Thread.sleep(1); // sleeps the thread
		}
		
		catch(InterruptedException e) {}
		
	}
	
}

public static int getScreenWidth()
{
	return ScreenWidth;
}

public static int getScreenHeight()
{
	return ScreenHeight;
}

public void start() // start method
{

	if(theThread == null) // checks if the thread is empty
	{
		theThread = new Thread(this); // creates new thread
		theThread.start(); // starts the thread 
		Obj.repaint(); // repaints the frame
	}	
}
		
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource(); // checks for which checkbox is pressed
		
		if(source == PAUSE) //pauses the game
		{
			try {
				Thread.sleep(1); // sleeps the thread		
			} catch (InterruptedException e1) {e1.printStackTrace();} 
			
			TimeIsPaused = true;
			
			theThread.interrupt();
		}
		
		if(source == RUN) // runs the game
		{
			try {
				Thread.sleep(1); // sleeps the thread		
			} catch (InterruptedException e1) {e1.printStackTrace();} 
			
			TimeIsPaused = false;
			
			theThread.interrupt();
		}
		
		if (source == QUIT) // quit button
		{
			stop(); // stops the program
		}
		
		if (source == RESTART) // restart button
		{
			Obj.Restart(); //calls restart function
		}
					
	}// action performed
		
		
	public void adjustmentValueChanged(AdjustmentEvent e) 
	{
	
		Scrollbar sb = (Scrollbar)e.getSource(); // gets the scroll bar that triggered the event
		
		if(sb==CannonAngleScrollBar) 
		{
	
			Obj.setAngle(sb.getValue()); // sets the angle to the scroll bar
			
			CannonAngleBox.setText(""+sb.getValue()); // sets the text filed to the scroll bar
		}
		
		if(sb==VelocityScrollBar) 
			
		{
			Obj.setVelocity(sb.getValue()); // sets the velocity to the scroll bar 
			
			VelocityBox.setText(""+sb.getValue()); // sets the text field to the scroll bar
		}
	}
	
	
	public void componentResized(ComponentEvent e) 
	{	
		WinWidth = getWidth(); // makes WinWidth = getWidth
		WinHeight = getHeight();//  makes WinHeight = getHeight
		MakeSheet(); // calls makesheet
		SizeScreen(); // calls sizescreen
		CheckSize(); // calls checksize
		Obj.reSize(ScreenWidth,ScreenHeight); // resizes the screen
		Perimeter.setBounds(0,0, ScreenWidth-1, ScreenHeight-1); // resizes the perimeter
		//Perimeter.grow(-1,-1); // grow the perimeter by 1
		Obj.reSize(ScreenWidth,ScreenHeight); // resizes the screen
		Obj.repaint(); // repaints the frame					
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) 
	{
		stop();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void CheckSize() // checksize method
	
	{
		int x = Obj.getx(); // creates x int
		int y = Obj.gety(); // creates y int
		int size = Obj.getsize(); // creates size int
				
		int bottom = y + (size -1)/2;	 //creates bottom int
		int right = x + (size -1)/2; // creates right int
		
		if(right > ScreenWidth); // checks if right is greater than ScreenWidth
		{
			Obj.setx(ScreenWidth - size - 1);	 // sets the x	
		}
		
		if(bottom > ScreenHeight) // checks if bottom is greater than ScreenHeight
			
		{
			Obj.sety(ScreenHeight - size -1); // sets the y
		}
		
	}
	

	public void stop() // stop method
	
	{
		run = false; // stops the running of the program 
		theThread.interrupt(); // interupts the thread
		
		CannonAngleScrollBar.removeAdjustmentListener(this); // removes all the lsteners
		VelocityScrollBar.removeAdjustmentListener(this);
		
		this.removeComponentListener(this);// removes the ComponentListener
		this.removeWindowListener(this); // // removes the WindowListener
		dispose();
		System.exit(0);
	}
	
	private Rectangle getDragBox(MouseEvent e)                    	
	
	{
		m2.setLocation(e.getPoint()); //sets the point m2 for the dragbox
		Rectangle MathRec = new Rectangle(); // makes a rectangle
		
		MathRec.setBounds(Math.min(m2.x,m1.x), Math.min(m1.y,m2.y), Math.max(m2.x - m1.x, m1.x - m2.x), Math.max(m1.y - m2.y, m2.y - m1.y)); // calculates which way to draw the dragbox
				
		return MathRec;
	}
	
	public void mousePressed(MouseEvent e) 
	{
		m1.setLocation(e.getPoint()); 	//sets the point m1 for the dragbox
	}
	
	@Override
	public void mouseDragged(MouseEvent e) 
	{
		db.setBounds(getDragBox(e));   //makes db = dragbox                        
		
		if(Perimeter.contains(db)) // if db is in the perimeter
			
		{
			
			Obj.setDragBox(db); // sets the dragbox
			Obj.repaint(); // paints the dragbox
			
		}
		
	}

	
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		ok = true;
		int i =0;
		
		Rectangle CannonBase = new Rectangle(CannonVSBall.getScreenWidth() -110, CannonVSBall.getScreenHeight() -110, 110,110); // make bigger to interact with mouse, use for ball hitting it 
				
		Point p = new Point(e.getPoint()); //makes the point p
		
		while((i < Obj.getWallSize()) && ok) //loops through the rectangle vector
		{
		
		r = Obj.getOne(i); // sets r = to getOne
				
		if(r.contains(p)) // if the rectangle is clicked remove that rectangle
		{
			Obj.removeOne(i); //removes a rectangle from the vector
			
		}
		
		else
			
		{
			i++; // increments i
		}
		
		}// end while
		
		if(CannonBase.contains(p)) // if cannon base contains the mouse 
		{
			moveProjectile = true; // set moveProjectile to true
			Obj.CannonBallGravity(); // call CannonBallGravity
		}
			Obj.repaint(); // repaints the canvas
		
	}

	@Override
	public void mouseReleased(MouseEvent e)                                                                                                                                                                                      
	{
		ok = true; // sets ok to true
		int i=0; // sets i to 0
		int half = (Obj.getsize()+1)/2; //sets half
		Rectangle ObjBoundary = new Rectangle(Obj.getx()-half,Obj.gety()-half,Obj.getsize(),Obj.getsize()); // sets the boundary around the ball
		Rectangle CannonBase = new Rectangle(CannonVSBall.getScreenWidth() -110, CannonVSBall.getScreenHeight() -110, 110,110); // sets the cannon base rectangle
		Rectangle getOne = new Rectangle(); //makes new rectangle getOne
				
		if(db.intersects(ObjBoundary) || db.intersects(CannonBase))// checks if ball is in the drawbox
			
		{
			ok = false; // sets ok to false
			db.setBounds(0,0,0,0); // set the db bounds to 0
			Obj.setDragBox(db); 
			Obj.repaint(); // repaint the db
		}
		
		while((i < Obj.getWallSize()) && ok) // loops through therectangle vector
		{	
					
		getOne = Obj.getOne(i); // sets getOne to Obj.getOne
			
		if(db.intersection(getOne).equals(db))//if a smaller rectangle is put on a bigger rectangle delete the smaller
		{
			ok = false;
			db.setBounds(0,0,0,0); // set the db bounds to 0
			Obj.setDragBox(db); 
			Obj.repaint(); // repaint the db
		}
		
		if(getOne.intersection(db).equals(getOne))//if a rectangle is covered by another delete the botttom rectangle
		{
			Obj.removeOne(i);	// remove rectangle		
		}
		
		else
		{
			i++; // increment i
		}
			
		}// end while
		
		if(ok) // if ok is true
			
		{
			Obj.addOne(db); //add a rectangle to the vector 
			
			db.setBounds(0,0,0,0); // set the db bounds to 0
			Obj.setDragBox(db); 
			Obj.repaint(); // repaint the db
			
			
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		Obj.repaint();	
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	{
		CheckboxMenuItem checkbox = (CheckboxMenuItem) e.getSource();
		
		if(checkbox == MERCURY || checkbox == VENUS || checkbox == EARTH || checkbox == MOON || checkbox == MARS || checkbox == JUPITER || checkbox == SATURN || checkbox == URANUS || checkbox == NEPTUNE || checkbox == PLUTO);
		{
			MERCURY.setState(false); // sets item checkboxes to false 
			VENUS.setState(false);
			EARTH.setState(false);
			MOON.setState(false);
			MARS.setState(false);
			JUPITER.setState(false);
			SATURN.setState(false);
			URANUS.setState(false);
			NEPTUNE.setState(false);
			PLUTO.setState(false);
			
			checkbox.setState(true);// sets the selected checkbox to true
			
			SetPlanet();// calls function
		}	
		
		if(checkbox == XSLOW || checkbox == SLOW || checkbox == SMEDIUM || checkbox == FAST || checkbox == XFAST)
		{
			XSLOW.setState(false); // sets item checkboxes to false 
			SLOW.setState(false);
			SMEDIUM.setState(false);
			FAST.setState(false);
			XFAST.setState(false);
			
			checkbox.setState(true);// sets the selected checkbox to true
			
			SetMenuSpeed();	// calls function
		}
				
		if(checkbox == XSMALL || checkbox == SMALL || checkbox == MEDIUM || checkbox == LARGE || checkbox == XLARGE)
		{
			
			XSMALL.setState(false);// sets item checkboxes to false 
			SMALL.setState(false);
			MEDIUM.setState(false);
			LARGE.setState(false);
			XLARGE.setState(false);

			checkbox.setState(true); // sets the selected checkbox to true
			
			SetMenuSize();	// calls function
		}

	}
	
	public void SetPlanet() 
	{
		if(MERCURY.getState() == true)// sets the gravity of the selected checkbox
		{
			Obj.setGravity(12.14);
		}
		
		if(VENUS.getState() == true)// sets the gravity of the selected checkbox
		{
			Obj.setGravity(29.10);
		}
		
		if(EARTH.getState() == true)// sets the gravity of the selected checkbox
		{
			Obj.setGravity(32.14);
		}
		
		if(MOON.getState() == true)// sets the gravity of the selected checkbox
		{
			Obj.setGravity(5.31);
		}
		
		if(MARS.getState() == true)// sets the gravity of the selected checkbox
		{
			Obj.setGravity(12.17);
		}
		
		if(JUPITER.getState() == true)// sets the gravity of the selected checkbox
		{
			Obj.setGravity(81.76);
		}
		
		if(SATURN.getState() == true)// sets the gravity of the selected checkbox
		{
			Obj.setGravity(34.25);
		}
		
		if(URANUS.getState() == true)// sets the gravity of the selected checkbox
		{
			Obj.setGravity(29.10);
		}
		
		if(NEPTUNE.getState() == true)// sets the gravity of the selected checkbox
		{
			Obj.setGravity(36.58);
		}
		
		if(PLUTO.getState() == true)// sets the gravity of the selected checkbox
		{
			Obj.setGravity(1.90);
		}
	}
	
	public void SetMenuSpeed()
	{
			
		if(XSLOW.getState() == true)
		{
			delay = 10; //determines the delay	
			theThread.interrupt(); // interupts the thread
		}
		
		if(SLOW.getState() == true)
		{
			delay = 8; //determines the delay
			theThread.interrupt(); // interupts the thread
		}
		
		if(SMEDIUM.getState() == true)
		{
			delay = 6; //determines the delay	
			theThread.interrupt(); // interupts the thread
		}
		
		if(FAST.getState() == true)
		{
			delay = 4; //determines the delay
			theThread.interrupt(); // interupts the thread
		}
		
		if(XFAST.getState() == true)
		{
			delay = 1; //determines the delay
			theThread.interrupt(); // interupts the thread
		}
		
	}
		
		public void SetMenuSize() 
		{
			int TS = 60;
			int half; 
			int i=0;
			
			Obj.MinMax(TS);//calls MinMax method
			TS= Obj.getsize(); // gets the value
			half = (TS-1)/2; // sets half to half the size of the ball
			ObjBoundary.setBounds(Obj.getx()-half-1,Obj.gety()-half-1,TS+2,TS+2); //sets the size of the boundary arounf the ball
						
									
				if(ObjBoundary.equals(Perimeter.intersection(ObjBoundary)))// if ball intersects with outside of the frame 
				{
					i=0;//
					ok=true;//
																		
					if(XSMALL.getState() == true)
					{
						TS = 20;
						Obj.MinMax(TS);
						half = (TS-1)/2; // sets half to half the size of the ball
						ObjBoundary.setBounds(Obj.getx()-half-1,Obj.gety()-half-1,TS+2,TS+2); //sets the size of the boundary arounf the ball
						
						
						while((i < Obj.getWallSize()) && ok)// loop to look through the rectangle vector
							
						{
							t = Obj.getOne(i);// get the ith rectankgle
							
							if(t.intersects(ObjBoundary)) //checks for intersection with ObjBoundary
								
							{
								ok = false; // sets okay to false
								TS= Obj.getsize();
								half = (TS-1)/2;
								ObjBoundary.setBounds(Obj.getx()-half-1,Obj.gety()-half-1,TS+2,TS+2); //sets the size of the boundary arounf the ball
							}
							
							else
							{
								i++;//increments i
															
							}
						}// end while
						
						if(Obj.CheckSizeX(TS) == true && Obj.CheckSizeY(TS) == true && ok == true) // prevents SObj from being increased if it will be outside of xmin, xmax, ymin, or ymax
						
							{
							Obj.update(TS); // change the size in the drawing object
							}
						else
						{
							TS = Obj.getsize();
							ButtonTest(TS);
						}
						}
					
					
					if(SMALL.getState() == true)
					{
						TS = 40;
						Obj.MinMax(TS);
						half = (TS-1)/2; // sets half to half the size of the ball
						ObjBoundary.setBounds(Obj.getx()-half-1,Obj.gety()-half-1,TS+2,TS+2); //sets the size of the boundary arounf the ball
						
						
						while((i < Obj.getWallSize()) && ok)// loop to look through the rectangle vector
							
						{
							t = Obj.getOne(i);// get the ith rectankgle
							
							if(t.intersects(ObjBoundary)) //checks for intersection with ObjBoundary
								
							{
								ok = false; // sets okay to false
								TS= Obj.getsize();
								half = (TS-1)/2;
								ObjBoundary.setBounds(Obj.getx()-half-1,Obj.gety()-half-1,TS+2,TS+2); //sets the size of the boundary arounf the ball
							}
							
							else
							{
								i++;//increments i
															
							}
						}// end while
						
						if(Obj.CheckSizeX(TS) == true && Obj.CheckSizeY(TS) == true && ok == true) // prevents SObj from being increased if it will be outside of xmin, xmax, ymin, or ymax
							
						{
						Obj.update(TS); // change the size in the drawing object
						
						}
						
						else
						{
							TS = Obj.getsize();
							ButtonTest(TS);
						}
					}
					
					if(MEDIUM.getState() == true)
					{
						TS = 60;
						Obj.MinMax(TS);
						half = (TS-1)/2; // sets half to half the size of the ball
						ObjBoundary.setBounds(Obj.getx()-half-1,Obj.gety()-half-1,TS+2,TS+2); //sets the size of the boundary arounf the ball
						
						
						while((i < Obj.getWallSize()) && ok)// loop to look through the rectangle vector
							
						{
							t = Obj.getOne(i);// get the ith rectankgle
							
							if(t.intersects(ObjBoundary)) //checks for intersection with ObjBoundary
								
							{
								ok = false; // sets okay to false
								TS= Obj.getsize();
								half = (TS-1)/2;
								ObjBoundary.setBounds(Obj.getx()-half-1,Obj.gety()-half-1,TS+2,TS+2); //sets the size of the boundary arounf the ball
							}
							
							else
							{
								i++;//increments i
															
							}
						}// end while
						
						if(Obj.CheckSizeX(TS) == true && Obj.CheckSizeY(TS) == true && ok == true) // prevents SObj from being increased if it will be outside of xmin, xmax, ymin, or ymax
							
						{
						Obj.update(TS); // change the size in the drawing object
						
						}
						
						else
						{
							TS = Obj.getsize();
							ButtonTest(TS);
						}
					}
					
					if(LARGE.getState() == true)
					{
						TS = 80;
						Obj.MinMax(TS);
						half = (TS-1)/2; // sets half to half the size of the ball
						ObjBoundary.setBounds(Obj.getx()-half-1,Obj.gety()-half-1,TS+2,TS+2); //sets the size of the boundary arounf the ball
						
						
						while((i < Obj.getWallSize()) && ok)// loop to look through the rectangle vector
							
						{
							t = Obj.getOne(i);// get the ith rectankgle
							
							if(t.intersects(ObjBoundary)) //checks for intersection with ObjBoundary
								
							{
								ok = false; // sets okay to false
								TS= Obj.getsize();
								half = (TS-1)/2;
								ObjBoundary.setBounds(Obj.getx()-half-1,Obj.gety()-half-1,TS+2,TS+2); //sets the size of the boundary arounf the ball
							}
							
							else
							{
								i++;//increments i
															
							}
						}// end while
						
						if(Obj.CheckSizeX(TS) == true && Obj.CheckSizeY(TS) == true && ok == true) // prevents SObj from being increased if it will be outside of xmin, xmax, ymin, or ymax
							
						{
						Obj.update(TS); // change the size in the drawing object
						
						}
						
						else
						{
							TS = Obj.getsize();
							ButtonTest(TS);
						}
					}
					
					if(XLARGE.getState() == true)
					{
						TS =100;
						Obj.MinMax(TS);
						half = (TS-1)/2; // sets half to half the size of the ball
						ObjBoundary.setBounds(Obj.getx()-half-1,Obj.gety()-half-1,TS+2,TS+2); //sets the size of the boundary arounf the ball
						
						
						while((i < Obj.getWallSize()) && ok)// loop to look through the rectangle vector
							
						{
							t = Obj.getOne(i);// get the ith rectankgle
							
							if(t.intersects(ObjBoundary)) //checks for intersection with ObjBoundary
								
							{
								ok = false; // sets okay to false
								TS= Obj.getsize();
								half = (TS-1)/2;
								ObjBoundary.setBounds(Obj.getx()-half-1,Obj.gety()-half-1,TS+2,TS+2); //sets the size of the boundary arounf the ball
							}
							
							else
							{
								i++;//increments i
															
							}
						}// end while
						
						if(Obj.CheckSizeX(TS) == true && Obj.CheckSizeY(TS) == true && ok == true) // prevents SObj from being increased if it will be outside of xmin, xmax, ymin, or ymax
							
						{
						Obj.update(TS); // change the size in the drawing object
						
						}
						else
						{
							TS = Obj.getsize();
							ButtonTest(TS);
						}
					}
				Obj.repaint(); // force a repaint	
		}//end if
					
		}// end method
		
		public void ButtonTest(int TS) // checks the ball size and fills in the appropriate checkbox                  
		{
			if(TS == 20) //checks the ball size
			{
				XSMALL.setState(false); // sets to false
				SMALL.setState(false);
				MEDIUM.setState(false);
				LARGE.setState(false);
				XLARGE.setState(false);	
				
				XSMALL.setState(true); //sets to true
			}
			
			if(TS == 40) //checks the ball size
			{
				XSMALL.setState(false);// sets to false
				SMALL.setState(false);
				MEDIUM.setState(false);
				LARGE.setState(false);
				XLARGE.setState(false);	
				
				SMALL.setState(true);//sets to true
			}
			
			
			if(TS == 60)//checks the ball size
			{
				XSMALL.setState(false);// sets to false
				SMALL.setState(false);
				MEDIUM.setState(false);
				LARGE.setState(false);
				XLARGE.setState(false);	
				
				MEDIUM.setState(true);//sets to true
			}
			
			if(TS == 80)//checks the ball size
			{
				XSMALL.setState(false);// sets to false
				SMALL.setState(false);
				MEDIUM.setState(false);
				LARGE.setState(false);
				XLARGE.setState(false);	
				
				LARGE.setState(true);//sets to true
			}
			
			if(TS == 100)//checks the ball size
			{
				XSMALL.setState(false);// sets to false
				SMALL.setState(false);
				MEDIUM.setState(false);
				LARGE.setState(false);
				XLARGE.setState(false);
				
				XLARGE.setState(true);	//sets to true
			}
		
		}
		
				
}// end class

class Objc extends Canvas // Objc class
{
	private static final long serialVersionUID = 11L; 
	
	private int ScreenWidth; // makes the the following ints and booleans
	private int ScreenHeight;
	private int SObj;
	private int x,y;
	private int dx;
	private int dy;
	private int xmin, xmax, ymin,ymax;
	private boolean rect = true;
	private boolean clear = false;
	private boolean down = false;
	private boolean right = false;
	private boolean ok;
	
	double angle = Math.toRadians(45); 
	double velocity=100;
	int referenceX;
	int referenceY;
	int a1X;
	int a1Y;
	int a2X;
	int a2Y;
	int c1X;
	int c1Y;
	int c2X;
	int c2Y;
	int ax;
	int ay;
	int ballScore=0;
	int yourScore=0; 
	
	double dt = 0.09;
	double VelocityX;
	double VelocityY;
	double gravity = 32.14;
	
	int delay = 3;
	int SBEqualizer = 6;
		
	int ProjectileX = CannonVSBall.getScreenWidth() -35;
	int ProjectileY =  CannonVSBall.getScreenWidth() -35;
	boolean draw = false;
	boolean destroyCannon = false;
	boolean destroyCannonBall = false;
	boolean InBounds = true;
	
	Rectangle MathRec = new Rectangle(); //makes rectangle MathRec
	
	Rectangle CannonBall = new Rectangle(); 
	
	Rectangle ObjBoundary = new Rectangle();
	
	Rectangle CannonBase = new Rectangle(); 
	
	Rectangle Respawn = new Rectangle(0,0, 110, 110); 
	
	Polygon poly = new Polygon();
		
	Image buffer; // makes an Image 
	Graphics g; // makes a graphic
		
	private Vector <Rectangle> Walls = new Vector <Rectangle>(); // makes a vector 
		
	public void addOne(Rectangle r) // adds a rectangle to the vector
	
	{
		Walls.addElement(new Rectangle(r));
			
	}
	
	public void removeOne(int i) // removes a rectangle from the vector
	
	{
		Walls.removeElementAt(i);
		
	}
	
	public Rectangle getOne(int i) // gets a rectangle from the vector
	
	{
		return Walls.elementAt(i);
	}
	
	public int getWallSize() // returns the size of the vector Walls which store the rectangles
	
	{
		return Walls.size();
	}
		
	private static final Rectangle ZERO = new Rectangle(0,0,0,0); //makes a zero rectangle
	{
				
		Rectangle r = new Rectangle(ZERO); //makes new rectangle zero
		Rectangle ObjBoundary = new Rectangle(x+1,y+1,SObj,SObj); // sets the boundary around the ball
		ObjBoundary.grow(1,1); // grows the boundary
		int i =0; // sets i
		
		while((i<Walls.size()) && ok)// loops throught the rectangle vector
			
		{
			r=Walls.elementAt(i); // sets r = to the element in the vector
			
			if(r.intersects(ObjBoundary)) // if the rectangle intersects the ball
			{
				ok = false;// sets ok to false
			}
			
			else
			{
				i++; // increments i
			}
		}
		
	}
	
	
	public Objc(int SB, int w, int h) // constructor
	{
		ScreenWidth=w; // assigns values the the following
		ScreenHeight=h;
		SObj = SB;
		rect = true;
		clear = false;
		y= ymin+1;
		x= xmin+1;
		
		dx = 1;
		dy = 1;
				
		down = true;
		right = true;
						
		MinMax(SObj); // calls MinMax
		
	}
	
	public void Restart() // resets the game 
	
	{
		Rectangle Perimeter = new Rectangle(0,0,ScreenWidth,ScreenHeight); // makes perimeter to get the screen size
		Rectangle getOne = new Rectangle(); // makes the getOne rectangle
		int i=0;
		x = 5+(SObj-1)/2; // sets the ball respawn spot
		y = 5+(SObj-1)/2;// sets the ball respawn spot
		
		while((i < getWallSize())) // loops through the rectangle vector
		{	
					
		getOne = getOne(i); // sets getOne to Obj.getOne
						
		if(getOne.intersects(Perimeter))//if a rectangle is covered by another delete the botttom rectangle
		{
			removeOne(i);	// remove rectangle		
		}
		
		else
		{
			i++; // increment i
		}
			
		}// end while
		
		yourScore = 0; // sets score to 0
		ballScore = 0; // sets score to 0
		CannonVSBall.YourScoreBox.setText("0"); // displays score 0
		CannonVSBall.BallScoreBox.setText("0");	// displays score 0
	}
	
	
	public void Intersects() // checks for intersections with the cannonball, cannon, etc
	
	{	
		int i=0;
		Rectangle getOne = new Rectangle(); // makes getOne rectangle
		
		CannonBall.setBounds(ProjectileX,ProjectileY, 20,20);  // sets cannonBall rectangle
		ObjBoundary.setBounds(x - (SObj-1)/2,y -(SObj -1)/2, getsize(), getsize()); // sets ObjBoundary rectangle
		CannonBase.setBounds(CannonVSBall.getScreenWidth() -90, CannonVSBall.getScreenHeight() -90, 90,90); // sets cannonBase rectangle
		
		if(ObjBoundary.intersects(CannonBall))  // if the ball and cannonball intersect
		{
			destroyCannonBall = true; // resets the ball and changes the scores 
			draw = false;
			ProjectileX = 10000;// removes the cannon ball
			ProjectileY = 10000;
		}
		
		else
			destroyCannonBall = false;
			
		
		if(ObjBoundary.intersects(CannonBase)) //|| CannonBall.intersects(CannonBase)
		{		
			destroyCannon = true; // resets the ball and changes the scores 
		}
		
		else
			destroyCannon = false;
		
		while((i < getWallSize())) // loops through therectangle vector
		{	
					
		getOne = getOne(i); // sets getOne to Obj.getOne
						
		if(getOne.intersects(CannonBall))//if a rectangle is covered by another delete the botttom rectangle
		{
			removeOne(i);	// remove rectangle	
			
			draw = false;
			ProjectileX = 10000; // removes the cannon ball
			ProjectileY = 10000;	
		}
		
		else
		{
			i++; // increment i
		}
			
		}// end while
		
		if(ProjectileX <= xmin || ProjectileY >= ymax) // checks if the cannonball will stay in play
		{
			CannonVSBall.BoundsInfo.setText("it's gone"); //changes the text
			
			InBounds = false; // says the ball is out of play
		}
		
		else
		{
			CannonVSBall.BoundsInfo.setText("it's good"); // changes the text
			
			InBounds = true; // says the ball is in play
		}
		
		if(CannonBall.intersects(CannonBase)) // if cannonball hits the cannon 
		{
			destroyCannon = true;  // resets the ball and changes the scores 
			draw = false;
			ProjectileX = 10000; // removes the cannonball
			ProjectileY = 10000;	
		}
	}
	
	public void setGravity(double planetGravity) //sets the gravity
	
	{
		gravity =  planetGravity;
	}
	
	public void setAngle(int angleValue) // sets the angle of the cannon
	
	{
		angle =  Math.toRadians(angleValue);
		
		
	}
	
	public void setVelocity(int velocityValue) // sets the velocity of the cannonball
	
	{
		velocity = velocityValue;
	}
	
	public void Cannon() // makes and does all the math for the cannon
	{		
		referenceX = (int)(80*Math.cos(angle)); // makes referenceX
		referenceY = (int)(80*Math.sin(angle)); // makes referenceY
		ax = CannonVSBall.getScreenWidth() -35; // makes ax
		ay = CannonVSBall.getScreenHeight() -35; // makes ay
		
				
		int cx = ax - referenceX; // makes cx
		int cy = ay - referenceY; // makes cy
		
		int x1 = (int)(20/2*Math.cos(angle)); //makes x1
		int x2 = x1; //makes x2
		
		int y1 = (int)(20/2*Math.sin(angle)); //makes y1
		int y2 = y1; //makes y2
			
		a1X = ax - y1; // sets a positions (bottom of cannon)
		a1Y = ay + x1;
		a2X = ax + y2;
		a2Y = ay - x2;
				
		c1X = cx + y1; //sets c positions (top of cannon)
		c1Y = cy - x1;
		c2X = cx - y2;
		c2Y = cy + x2;
		
		poly.addPoint(a1X,a1Y); // sets the points to the poly
		poly.addPoint(a2X,a2Y);
		poly.addPoint(c1X, c1Y);
		poly.addPoint(c2X, c2Y);
						
	}
	
	public void CannonBall() // sets the movement of the cannonball
	{
		VelocityY = VelocityY - gravity* dt; // sets VelocityY
		
		ProjectileX = (int) (ProjectileX - VelocityX*dt); // sets projectileX
		ProjectileY = (int) (ProjectileY - VelocityY*dt-0.5*gravity*(dt*dt));  // sets projectileY
				
	}
	
	public void CannonBallGravity() // changes the gravity of the cannon ball
	
	{
		if (InBounds == false)
		{
		SBEqualizer =10; 
		ProjectileX = c2X-12;
		ProjectileY = c1Y-12;
		
		draw = true;
		
		VelocityX = (velocity/SBEqualizer)*Math.cos(angle); //calculates the movement of cannonball
		VelocityY = (velocity/SBEqualizer)*Math.sin(angle);
		}	
	}
	
	
	public void setDragBox(Rectangle rec) // sets the dragbox size
	
	{
		MathRec.setBounds(rec.x,rec.y,rec.width,rec.height);
		
	}
	
		
	public int getx() // returns the x
	{
		
		
		return this.x;
		
	}
	
	public int gety() // returns the y
	{
		
		
		return this.y;
		
	}
	
	public int getsize() //gets the size
	{
		return this.SObj;
		
	}
	
	public void setx(int x) // sets the x
	
	{
		this.x = x;
	}
	
	public void sety(int y) // sets the y
	
	{
		this.y = y;
	}
			
	public void MinMax(int size) // MinMax method
	
	{
		xmin = (size + 1)/2 +2; // assigns values for xmin, xmax, ymin, and ymax
		xmax = (ScreenWidth - xmin);
		ymin = (size + 1)/2 +2;
		ymax = (ScreenHeight - ymin);
	}
	
	public boolean CheckSizeX(int size) // checkSizeX method
	{
		if(x +(size + 1)/2  < xmax && x - (size + 1)/2  > xmin) // checks if x + SObj is inside xmax and xmin
		{
		return true;
		}
		
		return false;
	}
	
	public boolean CheckSizeY(int size) // checkSizey method
	{
		if(y + (size + 1)/2 < ymax && y - (size + 1)/2 > ymin) // checks if y + SObj is inside xmax and xmin 
		{
		return true;
		}
		
		return false;
	}
	
	
						
	public boolean CheckX() // checkX method
	{
		if(x < xmax && x > xmin) // checks if x is inside xmax and xmin
		{
		return true;
		}
		
		return false;
	}
	
	
	public boolean CheckY() //checky method
	
	{
		if(y < ymax && y > ymin) // checks if y is inside the ymax and ymin
		{
		return true;
		}
		
		return false;
	}
	
	
	
	
	public void BounceRec() // bounces the ball off the rectangles
	{
		
		int i =0; //sets i
		int half = (SObj-1)/2;// sets half
		Rectangle ObjBoundary = new Rectangle(x-half,y-half,SObj,SObj); // sets the boundary for the ball
		
		
		
		for(i=0;i <  Walls.size(); i++) //loops throgh the rectangle vector
			
		{
									
		Rectangle Wall = Walls.elementAt(i); // sets wall rectangle = to the rectangle in the vector
		
		int ball_left = ObjBoundary.x; // makes ball left
        int ball_right = ball_left + SObj; // makes ball right
        int ball_top = ObjBoundary.y; // makes ball top
        int ball_bottom = ObjBoundary.y+ SObj; // makes ball bottom
        
        int wall_left = Wall.x; // makes wall left
        int wall_right = wall_left+Wall.width; // makes wall right
        int wall_top = Wall.y; // makes wall top
        int wall_bottom = wall_top+Wall.height; // makes wall bottom
                
        boolean BallOnLeft    = (ball_right   <=  wall_left+1); // checks if the ball is on the left side of the rectangle
        boolean BallOnRight   = (ball_left    >=  wall_right-1); // checks if the ball is on the right side of the rectangle
        boolean BallIsAbove   = (ball_bottom  <=  wall_top+1); // checks if the ball is on top of the rectangle
        boolean BallIsBelow   = (ball_top     >=  wall_bottom-1); // checks if the ball is below the rectangle
        
        if(ObjBoundary.intersects(Wall))  // checks if the objboundary intersects the rectangle
        {
        	
        if (BallOnLeft == true) // bounces the ball left
        {
        	MinMax(SObj); //calls minmax method
			
			dx = dx*-1; // moves the object
			
			x = dx+x; // moves the object
        } 
        
        if(BallOnRight == true) // bounces the ball right
        
        {
        	MinMax(SObj); //calls minmax method
        	
        	dx = dx*-1; // moves the object
			
			x = dx+x; // moves the object
        } 
        	
        if (BallIsAbove == true) // bounces the ball up
        {
        	MinMax(SObj); //calls minmax method
			
			dy =dy *-1; // moves the object
			
			y = dy + y; // moves the object
        } 
        
        if (BallIsBelow == true) // bounces the ball down
        {
        	MinMax(SObj); //calls minmax method
        	
        	dy =dy *-1; // moves the object
			
			y = dy + y; // moves the object
			
			
        }
        
        }//end if
        
	}//end for
		
	}
	
	
	
	public void move() // move method                                                
	
	{
		
		BounceRec();
				
		
		if(CheckX() == true) //checks if checkx is true
		{
			right =  true;  // assigns true to right 
		}
		
		else
		{
			right = false; // assigns false to right
		}
		
		
		if(CheckY() == true) // checks if Checky is true
		{
			down = true; //assigns true to down
		}
		
		else
		{
			down = false;// assigns false to down
		}
		
		
		if(right == true) // checks if right is true moving to the right
		{
			MinMax(SObj); //calls minmax method
			x+=dx; // moves the object
				
		}
		
		else
		{
			MinMax(SObj); //calls minmax method
			
			dx = dx*-1; // moves the object
			
			x = dx+x; // moves the object
			
		}
		
		if(down == true)
		{
			MinMax(SObj); //calls minmax method
			
			y+=dy; // moves the object
		}
		
		else
			
		{
			MinMax(SObj); //calls minmax method
			
			dy =dy *-1; // moves the object
			
			y = dy + y; // moves the object
			
		}
		
		
	}
	
	
	public void rectangle(boolean r)
	{
		rect =r; //sets rect to r
	}
	
	public void update(int NS)
	{
		SObj=NS; //sets SObj to NS
	}
	
	public void reSize(int w, int h)
	{
	ScreenWidth = w; //sets ScreenWidth to w
	ScreenHeight =h; // sets ScreenHeight to h
	y= ymin+1; // sets y = to a new value
	x= xmin+1; // sets x = to a new value 
	MinMax(SObj); // calls minmax method
	
	}
		
	public void Clear() //clear method
	{
		clear=true; // sets clear to true
	}
	
	public void paint(Graphics cg)
	{
		if(g != null) // if graphics isnt empty
		{
			g.dispose();
		}
		
		buffer= createImage (ScreenWidth, ScreenHeight); // buffers the image
		
		g= buffer.getGraphics();
		
		
		for(int i=0; i<Walls.size(); i++)    // loops throught the rectangle vector              
			
		{
			Rectangle MathRec = Walls.elementAt(i); // makes MathRec equal to rectangle in vector
			
			g.fillRect(MathRec.x,MathRec.y,MathRec.width,MathRec.height); // fills the rectangles with black
			
			
		}
			poly.reset();
			Cannon();
			Intersects();
			
			g.setColor(Color.red); // crates red outline on frame
			g.drawRect(0, 0, ScreenWidth-1, ScreenHeight-1); // crates red outline on frame
		
			g.setColor(Color.red); // draws the object and its outline
			g.fillOval(x-(SObj-1)/2,y-(SObj-1)/2,SObj,SObj);
			g.setColor(Color.black); // draws the object and its outline
			g.drawOval(x-(SObj-1)/2,y-(SObj-1)/2,SObj,SObj);				
			g.drawRect(MathRec.x,MathRec.y, MathRec.width, MathRec.height);
			
			g.setColor(Color.black);
			g.drawPolygon(poly);
			g.fillPolygon(poly);
			g.drawOval(CannonVSBall.getScreenWidth() -60, CannonVSBall.getScreenHeight() -60, 50,50);
			g.fillOval(CannonVSBall.getScreenWidth() -60, CannonVSBall.getScreenHeight() -60, 50,50);
			
			if(draw)
			{
				g.fillOval(ProjectileX,ProjectileY, 20, 20);
			}
								
			if(destroyCannon)
			{
				Rectangle getOne = new Rectangle();
				int i=0;
				x = 5+(SObj-1)/2;
				y = 5+(SObj-1)/2;
				
				while((i < getWallSize())) // loops through therectangle vector
				{	
							
				getOne = getOne(i); // sets getOne to Obj.getOne
								
				if(getOne.intersects(Respawn))//if a rectangle is covered by another delete the botttom rectangle
				{
					removeOne(i);	// remove rectangle		
				}
				
				else
				{
					i++; // increment i
				}
					
				}// end while
				
				ballScore+=1;
				CannonVSBall.BallScoreBox.setText(""+ballScore);	
			}
			
			else if(destroyCannonBall)
			{
				Rectangle getOne = new Rectangle();
				int i=0;
				x = 5+(SObj-1)/2;
				y = 5+(SObj-1)/2;
				
				while((i < getWallSize())) // loops through the rectangle vector
				{	
							
				getOne = getOne(i); // sets getOne to Obj.getOne
								
				if(getOne.intersects(Respawn))//if a rectangle is covered by another delete the botttom rectangle
				{
					removeOne(i);	// remove rectangle		
				}
				
				else
				{
					i++; // increment i
				}
					
				}// end while
				
				yourScore+=1; // adds to yourscore and displays it
				CannonVSBall.YourScoreBox.setText(""+yourScore);
				
				
			}
						
			cg.drawImage(buffer,0,0,null);
		
	}
	
	public void update(Graphics g)
	{
						
	paint(g);
		
	}
	
}//end Objc class

