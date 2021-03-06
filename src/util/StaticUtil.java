package util;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**mettiamo qui metodi statici che possono essere utili un po' ovunque*/
public class StaticUtil {
	
	/**stringa esadecimale del colore*/
	public static String hexColor(Color c) {
		return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());  
	}
	
	/**restituisce una stringa ordinata per il colore rgb*/
	public static String colorToRgbString(Color c) {
		String colorStr = "(r:"+c.getRed()+", g:"+c.getGreen()+"; b:"+c.getBlue()+")";
		return colorStr;
	}
	
	/**restituisce il colore di colors piu' vicino a c*/
	public static Color closestColor(Color c, Color ... colors) {
		if(colors.length == 0) return null;
		int answ = 0;
		double dist = colorDist(c, colors[0]);
		for(int i=1; i<colors.length; i++) {
			double d = colorDist(c, colors[i]);
			if(d < dist) {
				dist = d;
				answ = i;
			}
		}
		return colors[answ];
	}
	
	/**restituisce il colore di colors piu' lontano a c*/
	public static Color farthestColor(Color c, Color ... colors) {
		if(colors.length == 0) return null;
		int answ = 0;
		double dist = colorDist(c, colors[0]);
		for(int i=1; i<colors.length; i++) {
			double d = colorDist(c, colors[i]);
			if(d > dist) {
				dist = d;
				answ = i;
			}
		}
		return colors[answ];
	}
	
	/**restituisce uno span html che descrive il colore passato*/
	public static String getHtmlColorSpan(Color c) {
		String hex = hexColor(c);
		String text = colorToRgbString(c);
		String hex2 = hexColor(farthestColor(c, Color.BLACK, Color.WHITE));
		String htm = "<span style=\"background: "+hex+"; color:"+hex2+";\">"+text+"</span>";
		return htm;
	}
	
	/**restituisce distanza tra due colori*/
	public static double colorDist(Color c, Color c2) {
		return Math.sqrt( Math.pow(c.getRed() - c2.getRed(), 2) + Math.pow(c.getGreen() - c2.getGreen(), 2) + Math.pow(c.getBlue() - c2.getBlue(), 2));
	}
	
	public static void errorDialog(String message) {
		JOptionPane.showMessageDialog(new JFrame(), message, "Error!", JOptionPane.ERROR_MESSAGE);
	}
}
