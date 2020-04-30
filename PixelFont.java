package othellogui.pixelfont;

import java.util.Hashtable;
import java.io.InputStream;
import java.io.IOException;


import javax.microedition.lcdui.Graphics;

/**
 * PixelFont is a class for loading and displaying pixelfonts. The fonts are loaded from a 
 * pxf file.
 * 
 * @author omega237
 * 
 */
public class PixelFont implements FontMetrics
{
	/**
	 * left aligns the text in the bounding rectangle
	 */
	public static final int ALIGN_LEFT = 1;
	
	/**
	 * centers the text in the bounding rectangle
	 */
	public static final int ALIGN_CENTER = 2;
	
	/**
	 * right aligns the text in the bounding rectangle
	 */
	public static final int ALIGN_RIGHT = 3;
	
	/**
	 * top aligns the text in the bounding rectangle
	 */
	public static final int ALIGN_TOP = 4;
	
	/**
	 * middle aligns the text in the bounding rectangle
	 */
	public static final int ALIGN_MIDDLE = 5;
	
	/**
	 * bottom aligns the text in the bounding rectangle
	 */
	public static final int ALIGN_BOTTOM = 6;
	
	/**
	 * makes the text or the background fully opaque
	 */
	public static final int ALPHA_OPAQUE = 0xff000000;
	
	/**
	 * makes the text or the background semi transparent
	 */
	public static final int ALPHA_SEMI = 0x80000000;
	
	/**
	 * makes the text or the background fully transparent
	 */
	public static final int ALPHA_TRANSPARENT = 0x00000000;
	
	private int fontwidth;
	private int fontheight;
	private int charspacing;
	private int color;
	private int backgroundcolor;
	private int halign;
	private int valign;
	private int foregroundalpha;
	private int backgroundalpha;
	private Hashtable letters;
	
	/* 
	 * creates an array containing the the raw pixel descriptions of a string
	 */
	private int[] createLetterArray(String letterstring) throws PxfMalformedException
	{
		int[] letter = new int[letterstring.length()];
		for(int i=0; i<letterstring.length(); i++)
			letter[i] = Integer.parseInt(String.valueOf(letterstring.charAt(i)));
		return letter;
	}
	
	/*
	 * parses and loads a pxf file into the class hashtable
	 */
	private void parsePxf(String pxfcontent) throws PxfMalformedException
	{
		char c;
		int pos = 0;
		StringBuffer sb = new StringBuffer();
		
		letters = new Hashtable();
		
		// read font width
		while(true)
		{
			c = pxfcontent.charAt(pos);
			if(c != '\r' && c != '\n')
				sb.append(c);
			else
				break;
			pos++;
		}
		fontwidth = Integer.parseInt(sb.toString());
		sb.delete(0, sb.length());
		if(pxfcontent.charAt(pos+1) == '\r' || pxfcontent.charAt(pos+1) == '\n')
			pos += 2;
		else 
			pos++;
		
		// read font height
		while(true)
		{
			c = pxfcontent.charAt(pos);
			if(c != '\r' && c != '\n')
				sb.append(c);
			else
				break;
			pos++;
		}
		fontheight = Integer.parseInt(sb.toString());
		sb.delete(0, sb.length());
		if(pxfcontent.charAt(pos+1) == '\r' || pxfcontent.charAt(pos+1) == '\n')
			pos += 2;
		else 
			pos++;
		
		// read each letter and corresponding data
		try{
			while(true)
			{
				while(true)
				{
					c = pxfcontent.charAt(pos);
					if(c != '\r' && c != '\n')
						sb.append(c);
					else
						break;

					pos++;
				}
				if(pxfcontent.charAt(pos+1) == '\r' || pxfcontent.charAt(pos+1) == '\n')
					pos += 2;
				else 
					pos++;
				letters.put(String.valueOf(sb.charAt(0)), createLetterArray(sb.toString().substring(1)));
				sb.delete(0, sb.length());
			}
		}
		catch(IndexOutOfBoundsException e)
		{
			letters.put(String.valueOf(sb.charAt(0)), createLetterArray(sb.toString().substring(1)));
			sb.delete(0, sb.length());
		}
	}
	
	// assembles an rgb array by changing 1's to foregroundcolor and 0's to backgroundcolor
	private int[] assembleRgbArray(int[] letter)
	{
		int temp[] = new int[letter.length];
		for(int i=0; i<letter.length; i++)
		{
			if(letter[i] == 0)
				temp[i] = backgroundalpha|backgroundcolor;
			else if(letter[i] == 1)
				temp[i] = foregroundalpha|color;
		}
		return temp;
	}
	
	/**
	 * explicit constructor for PixelFonts
	 * 
	 * @param is InputStream pointing to the pxf file
	 * @throws NullPointerException Is thrown if is is null
	 * @throws IOException Is thrown if an error occurs while reading from is
	 * @throws PxfMalformedException Is thrown if a parsing error occurs
	 */
	public PixelFont(InputStream is) throws NullPointerException, IOException, PxfMalformedException
	{
		fontwidth = 0;
		fontheight = 0;
		charspacing = 1;
		color = 0x000000;
		backgroundcolor = 0xffffff;
		halign = ALIGN_CENTER;
		valign = ALIGN_MIDDLE;
		foregroundalpha = ALPHA_OPAQUE;
		backgroundalpha = ALPHA_TRANSPARENT;
		
	    int c;
	    StringBuffer sb = new StringBuffer();
	    
	    if(is == null)
	    	throw new NullPointerException();
	    
		// reads the pixelfont file into memory
	    while ((c = is.read()) != -1)
	    	sb.append((char)c);
	    
	    is.close();
	    parsePxf(sb.toString());
	    System.gc();
	}
	
	/* (non-Javadoc)
	 * @see othellogui.pixelfont.FontMetrics#getWidth(java.lang.String)
	 */
	public int getWidth(String s)
	{
		return (s.length()*fontwidth)+((s.length()-1)*charspacing);
	}
	
	/* (non-Javadoc)
	 * @see othellogui.pixelfont.FontMetrics#getWidth()
	 */
	public int getWidth()
	{
		return fontwidth;
	}
	
	/* (non-Javadoc)
	 * @see othellogui.pixelfont.FontMetrics#getHeight()
	 */
	public int getHeight()
	{
		return fontheight;
	}
	
	/* (non-Javadoc)
	 * @see othellogui.pixelfont.FontMetrics#getCharspacing()
	 */
	public int getCharspacing()
	{
		return charspacing;
	}

	/* (non-Javadoc)
	 * @see othellogui.pixelfont.FontMetrics#setCharspacing(int)
	 */
	public void setCharspacing(int charspacing)
	{
		this.charspacing = charspacing;
	}

	/**
	 * Returns the current text color
	 * 
	 * @return current text color
	 */
	public int getColor()
	{
		return color;
	}

	/**
	 * Sets the current text color
	 * 
	 * @param color the new text color
	 */
	public void setColor(int color)
	{
		this.color = color;
	}

	/**
	 * Returns the current background color
	 * 
	 * @return current background color
	 */
	public int getBackgroundcolor()
	{
		return backgroundcolor;
	}

	/**
	 * Sets the current background color
	 * 
	 * @param backgroundcolor the new background color
	 */
	public void setBackgroundcolor(int backgroundcolor)
	{
		this.backgroundcolor = backgroundcolor;
	}

	/**
	 * Gets the horizontal alignemt value
	 * 
	 * @return horizontal alignement value
	 */
	public int getHalign()
	{
		return halign;
	}

	/**
	 * Sets the horizontal alignment value
	 * 
	 * @param halign the alignment value
	 */
	public void setHalign(int halign)
	{
		this.halign = halign;
	}
	
	/**
	 * Gets the vertical alignment value
	 * 
	 * @return vertical alignment value
	 */
	public int getValign()
	{
		return valign;
	}

	/**
	 * Sets the vertical alignment value
	 * 
	 * @param valign the alignment value
	 */
	public void setValign(int valign)
	{
		this.valign = valign;
	}

	/**
	 * Gets the foreground alpha value
	 * 
	 * @return returns the foregroundalpha value
	 */
	public int getForegroundalpha()
	{
		return foregroundalpha;
	}
	
	/**
	 * Sets the foregroundalpha value
	 * 
	 * @param foregroundalpha the foregroundalpha to set
	 */
	public void setForegroundalpha(int foregroundalpha)
	{
		this.foregroundalpha = foregroundalpha;
	}
	
	/**
	 * Gets the backgroundalpha value
	 * 
	 * @return returns the backgroundalpha.
	 */
	public int getBackgroundalpha()
	{
		return backgroundalpha;
	}

	/**
	 * Sets the backgroundalpha value
	 * 
	 * @param backgroundalpha the backgroundalpha to set
	 */
	public void setBackgroundalpha(int backgroundalpha)
	{
		this.backgroundalpha = backgroundalpha;
	}



	/**
	 * Draws a string to a Graphics object.
	 * 
	 * @param g 		target Graphics object to draw on
	 * @param text		text to be drawn
	 * @param x			left coordinate of bounding rectangle
	 * @param y			top coordinate of bounding rectangle
	 * @param width		width of bounding rectangle
	 * @param height	height of bounding rectangle
	 */
	public void drawString(Graphics g, String text, int x, int y, int width, int height) throws CharacterNotFoundException, BoundingRectTooSmallException
	{
		int txtx = 0;
		int txty = 0;
		int letter[] = null;
		int spacing[] = null;

		if(width < getWidth(text) || height < fontheight)
			throw new BoundingRectTooSmallException();
		
		if(text.length() > 0 && width > 0 && height > 0)
		{
			switch(halign)
			{
			case ALIGN_LEFT:
				txtx = 0;
				break;
			case ALIGN_CENTER:
				txtx = (width-getWidth(text))/2;
				break;
			case ALIGN_RIGHT:
				txtx = width-getWidth(text);
				break;
			}
			switch(valign)
			{
			case ALIGN_TOP:
				txty = 0;
				break;
			case ALIGN_MIDDLE:
				txty = (height-fontheight)/2;
				break;
			case ALIGN_BOTTOM:
				txty = height-fontheight;
			}
		}

		// set clip region
		g.setClip(txtx,txty,getWidth(text),fontheight);
		
		if(text.length() == 1)
		{
			letter = (int[])letters.get(text);
			if(letter == null)
				throw new CharacterNotFoundException();
			letter = assembleRgbArray(letter);
			g.drawRGB(letter,0,fontwidth,txtx,txty,fontwidth,fontheight,true);
		}
		else
		{
			spacing = new int[fontheight*charspacing];
			for(int i=0; i<fontheight*charspacing; i++)
				spacing[i] = backgroundalpha|backgroundcolor;
			
			for(int j=0; j<text.length(); j++)
			{
				letter = (int[])letters.get(String.valueOf(text.charAt(j)));
				if(letter == null)
					throw new CharacterNotFoundException();
				letter = assembleRgbArray(letter);
				g.drawRGB(letter,0,fontwidth,txtx,txty,fontwidth,fontheight,true);
				g.drawRGB(spacing, 0, charspacing,txtx+fontwidth,txty,charspacing,fontheight,true);
				txtx += fontwidth+charspacing;  
			}
		}
		
		// reset clip rectangle
		g.clipRect(0,0,0,0);
		
	}
}
