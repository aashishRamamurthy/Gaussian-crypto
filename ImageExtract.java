import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.math.BigDecimal;
import javax.swing.*;
import java.lang.Runtime;

public class ImageExtract
{
	BufferedImage img;
	private String name;
	private String path;
	private boolean orderflag;
	private int height;
	private int width;
	private String folder;
	private double matrix[][];
	public ImageExtract(BufferedImage img,String path,String image,String folder)
	{
		 this.img=img;
		 this.path=path;
		 this.name=image;
		 this.folder=folder;
		 this.height=img.getHeight();
       this.width=img.getWidth();
	
	}
	
	/*
		Check image width and height for perfect encryption
	*/	
	public boolean checkmatorder()
	{
		orderflag=false;
		if(height<width)
		{
			matrix=new double[height][width];
			orderflag=true;
		}
		else if(height>=width)
		{
			matrix=new double[width][height];
		}	
		return orderflag;
	}	
		
	
	/*
		Convert image to matrix and return pixel matrix 
	*/	
	public double[][] convert_img_mat()
	{
		if(orderflag)
		{
			putpixel_1();
		}
		else
		{
			//rotate the image and get the pixels as usual form	
    		putpixel_2();
		}
	return matrix;
	}
	
	/*
		Convert any pixel matrix to image
	*/	
	public void gen_encrypted_image(double mat[][])
	{
		if(orderflag)
		{
			getpixel_1(mat);
		}
		else
		{
			getpixel_2(mat);
		}
		try
		{
			ImageIO.write(img, "jpg", new File(folder+"Encrypted_"+name));
			Runtime.getRuntime().exec(folder+"Encrypted_"+name);
		}
		catch(IOException e)
		{
		}		
   }
	
	public void gen_decrypted_image(double mat[][])
	{
		if(orderflag)
		{
			getpixel_1(mat);
		}
		else
		{
			getpixel_2(mat);
		}
		
		try
		{
			ImageIO.write(img, "jpg", new File(folder+"Decrypted_"+name));
		}
		catch(IOException e)
		{
		}		
   }

	/*
		Returns the row size
	*/	
	public int getrow()
	{
		if(orderflag)
		{
			return height;
		}
		else
		{
			return width;
		}		
	}
	
	/*
		Returns the column size
	*/
	public int getcolumn()
	{
		if(orderflag)
		{
			return width;
		}
		else
		{
			return height;
		}
	}
	
	public void putpixel_1()
	{
			for(int w = 0; w < width ; w++)		
      	{			
           for(int h=0;h < height ; h++)			
           {				
				   // BufferedImage.getRGB() saves the colour of the pixel as a single integer.				
               // use Color(int) to grab the RGB values individually.				
               Color color = new Color(img.getRGB(w, h));								
               // use the RGB values to get their average.				
               int averageColor = ((color.getRed() + color.getGreen() + color.getBlue()) / 3);
               matrix[h][w]=(double)averageColor;						
			  }		
      	}
	}
	
	public void putpixel_2()
	{
			for (int h=0; h<height;h++)
			{      
            for (int w=0;w<width;w++)
				{
					Color color = new Color(img.getRGB(w, h));								
               // use the RGB values to get their average.				
               int averageColor = ((color.getRed() + color.getGreen() + color.getBlue()) / 3);
               matrix[w][h]=(double)averageColor;		
					
				}
			}		
	}
	
	public void getpixel_1(double mat[][])
	{
		for (int k=0;k<height;k++)
      {
           for(int l=0;l<width;l++)
           {
               BigDecimal d=new BigDecimal(mat[k][l]);
					int value=d.intValue();
					if(value<0)
					{
						value=0;
					}
					if(value>255)
					{
						value=255;
					}		
               
               Color avg = new Color(value,value,value);
               img.setRGB(l,k,avg.getRGB());	
           }
		}  
	}
	
	public void getpixel_2(double mat[][])
	{
		for (int k=0;k<width;k++)
      	{
           for(int l=0;l<height;l++)
           {
               BigDecimal d=new BigDecimal(mat[k][l]);
					int value=d.intValue();
					if(value<0)
					{
						value=0;
					}
					if(value>255)
					{
						value=255;
					}		
               Color avg = new Color(value,value,value);
               img.setRGB(k,l,avg.getRGB());	
           }
			}  

	}  
}	

