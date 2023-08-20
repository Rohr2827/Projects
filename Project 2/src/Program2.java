//Jared Rohrbaugh, Carter Chinn, And Joseph Graczyk. CET 350, Group 3, Program2.java,
// Email roh2827@calu.edu, chi6709@calu.edu, gra4315@calu.edu


import java.io.*;
import java.util.*;


class Program2 {
public static void main(String[] args)  {
       
BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));// creating bufferedreader and files

PrintWriter out = null;
File InF;
InF = null;

File OutF;
OutF = null;

File OutOld;
OutOld = null;

File Back;
Back = null;

int check = 0; // condition checker
String input = "";
String output = "";


// new function

while (InF == null && check< 2 || !InF.exists() && check< 2) //while loop for input
{
   try
 {
System.out.println("Enter input file name");
input = stdin.readLine();
InF = new File(input);

check ++;




 }//end try
 catch (IOException ex)
   
   {
	 System.out.println(ex);
   }
   
}//end while

if (check ==2 && InF.exists()) 
	
	check =1;




//Start of OutF

while (OutF == null && check < 2) //while loop for output
{
try
{
     System.out.println("Enter initial output file name");
     output = stdin.readLine();
     
     OutF = new File(output);
          
}//end try

catch (IOException ex)
{
	System.out.println(ex);
	
	check = 2;
	
	
}


}//end while
     
while(check ==1 ) // condition checker
	
{
	
try
     {

if(OutF.exists()) // condition to check if file exists
{
check = 0;
System.out.println("This file exists.");
System.out.println("Do you want to 1. Enter a new name? 2. Back up existing output? 3.Overwrite the existing output? 4. Quit?");
int option = Integer.parseInt(stdin.readLine());

if(option == 1)// menu
	
{
	try
    {
	
	System.out.println("What is the new output file name?");
	output = stdin.readLine();
	OutF = new File(output);
	
	if(OutF.exists())
	{
		System.out.println("");
		
	}
	
	else
	{
	check =2;
	System.out.println("You did not enter an output file name. Program closing");

	}
	
    }//end try
	catch (IOException ex)
	{
		System.out.println(ex);
	}
	
	check = 1;
	
}

else if(option == 2)
{
    System.out.println("Lets backup the existing output.");//file backup
    
    OutOld = new File(output);
    Back = new File("backup.txt");
    
    if(FileExist("backup.txt"))
    {
    	Back.delete();
    }	
    OutOld.renameTo(Back);
    
    OutF = new File(output);
	
	OutF.createNewFile();
	
	System.out.println("Finished backup your file.");
	
	check = 0;
}

else if(option == 3) 
{
	
	System.out.println("Lets overwrite the existing output.");//file overwrite
	
	OutOld= new File(output);
	
	OutOld.delete();
	
	OutF = new File(output);
	
	OutF.createNewFile();
	
	
	System.out.println("Finished Overwriting your file.");
	
	check =0;
	
}

else if(option == 4)
{
	System.out.println("Goodbye");// exit 
	
	check =2;
}

}//end if
else
{
check = 2;
System.out.println("You did not enter an output file name. Program closing");
}
}//end try
catch (IOException ex)
{
	System.out.println(ex);
}

}//end while



// new function
BufferedReader in = null;// creating arrays and buffered reader

int temp =0;

String Array[];
Array = new String[101];

int Array2[];
Array2 = new int[101];

int sum = 0;

boolean Numeric=true;

int tokenNum = 0;
try
{

in = new BufferedReader(new FileReader(input));
String inbuffer;


while((inbuffer = in.readLine()) != null && check < 2) 

{
	
StringTokenizer inline;

inline = new StringTokenizer(inbuffer, "\t\n\r ");

while(inline.hasMoreTokens() && check < 2) // loop for reading file 
{
	inbuffer = inline.nextToken();
	
	try
	{
		int num = Integer.parseInt(inbuffer);
		
		sum = sum+num;
	}
	catch(NumberFormatException ex)
	{
	Numeric = false;
	}
	
	if(Numeric == false)
		
	{
		
	
	for(int i = 0;i<tokenNum;i++)
		
	{
				
		if(inbuffer.equals(Array[i]))
		{
			temp=1;
			
			Array2[i]++;
			
			}
		
		
		
	}// end for loop
	
	if(temp==0)
		
	{
	
	Array[tokenNum] = inbuffer;
	Array2[tokenNum] = 1;
		
	
	tokenNum++;
	}
		
	temp=0;
	Numeric = true;
	
	}//end if boolean	 
}// end while
}//end while

System.out.println(tokenNum); // printing to file 

for(int j = 0;j<tokenNum;j++)
{		
System.out.println(Array[j]);  	
System.out.println(Array2[j]);
}

System.out.println(sum);

}catch (IOException ex)

{System.out.println(ex);}


// File writing

PrintWriter outw = null;

try
{
 outw = new PrintWriter(new FileWriter(output));

 outw.println("Token:   Count:   ");
for(int k=0;k<tokenNum;k++)
	
{
	outw.printf("  %s\t\t%s\n", Array[k], Array2[k]); // printing to file 
}
	
outw.printf("\nToken Num:%d\n", tokenNum);
outw.printf("Sum:%d",sum);
 
 
outw.close();

}

catch (IOException ex)

{
System.out.println(ex);
}



}//end main

private static boolean FileExist(String string) {
	
	return false;
}

}// end class program

