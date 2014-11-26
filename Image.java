import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Scanner;
import java.text.DecimalFormat;
import java.lang.Math;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.filechooser.FileFilter;

public class Image
{	
	public static void main(String[] args) throws IOException
   {	
   		Scanner in=new Scanner(System.in);
    	  	double pixelData[][];
 	     
		  	//FILE CHOOSER GUI which is filtered for jpeg,jpg and png images   
			JFileChooser chooser = new JFileChooser();
			
		  	//Filtering the formats for file choosing
         FileFilter filter1 = new ExtensionFileFilter("JPG,JPEG,PNG", new String[] { "JPG", "JPEG", "PNG" });
         chooser.setFileFilter(filter1);			
			
			//Opening the file chooser dialog box
         int val=chooser.showOpenDialog(null);
			
         File f = chooser.getSelectedFile();
			
			if(val==JFileChooser.CANCEL_OPTION ||val==JFileChooser.ERROR_OPTION )
			//if(val==1) we can compare val with 1 also because showOpenDialog returns 1 for both CANCEl and ERROR
			{
				System.exit(0);
			}
			
         String img = f.getName();//Gets the image name and stores in img
			String path=f.getPath();//Gets image path and stores in path
			String folder=f.getParent();//Gets the parent folder and stores in folder
			folder=folder+"\\";//Concats folder path with '\\' so as to open and save the encrypted and decrypted image

			
			BufferedImage cat = ImageIO.read(new File(path));
			ImageExtract i=new ImageExtract(cat,path,img,folder);
			boolean check=i.checkmatorder();//Checks for the order of the matrix i.e whether m>n or m<n and swiches the pointers accordingly			

			//Reading image to matrix
			//long stcolor=System.currentTimeMillis();
			pixelData=i.convert_img_mat();
			//long endcolor=System.currentTimeMillis();
			System.out.println("Original image is ");
			Runtime runtime=Runtime.getRuntime();
			try 
			{
				Process process=runtime.exec("C:\\Windows\\explorer.exe "+folder+img);
			}
			catch(IOException h)
			{
				h.printStackTrace();
			}


			//Applying Gussian elimination
			System.out.println(i.getrow()+"::"+i.getcolumn());
			Gaussian g=new Gaussian(pixelData,i.getrow(),i.getcolumn());
			//Initialise the private key for the image
			long gus_start=System.currentTimeMillis();
			g.init_key();
			//Apply encryption 
			g.encrypt();
			long gus_end = System.currentTimeMillis();
			System.out.println("Encryptiontook " + (gus_end - gus_start) + " milliseconds \n");	
			
						
			double U[][]=g.getencryptedmatrix();
			//generating the encrypted matrix
			i. gen_encrypted_image(U);
			
			//Opens the encrypted image
			try 
			{
				Process process=runtime.exec("C:\\Windows\\explorer.exe "+folder+"Encrypted_"+img);
			}
			catch(IOException h)
			{
				h.printStackTrace();
			}
			
			System.out.println("DO YOU WISH TO DECRYPT THE ENCRYPTED IMAGE?::Yes/No");
			String decide_decode=in.next();	
			if(decide_decode.toLowerCase().equals("yes"))
			{
				long strgrey=System.currentTimeMillis();
				double V[][]=g.decrypt();
				System.out.println("\nDecryption successful..Image saved..");
				long endgrey = System.currentTimeMillis();	
				System.out.println("Decryption took " + (endgrey - strgrey) + " milliseconds");
				i.gen_decrypted_image(V);			
				//Opens the decrypted image
				try 
				{
						Process process=runtime.exec("C:\\Windows\\explorer.exe "+folder+"Decrypted_"+img);
				}
				catch(IOException h)
				{
					h.printStackTrace();
				}
		   }	
			else
			{
				System.exit(0);
			}	
	}
}	 
