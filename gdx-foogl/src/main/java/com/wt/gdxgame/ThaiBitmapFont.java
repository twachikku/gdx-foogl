
package com.wt.gdxgame;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.FloatArray;

public class ThaiBitmapFont extends BitmapFont{
	public static final char[] uChars={'ป','ฟ','ฝ','ฬ'}; 
	public static final char[] dChars={'ฎ','ฏ','ฐ','ญ'};
	public static final char[] vChars={3656,3657,3658,3659};  // วรรณยุกต์
	public static final char[] tChars={3636,3637,3638,3639,};  // สระบน อิ อี อึ อื
	public static final char[] oChars={3635};  // สระบน อำ
	public static final char[] gChars={3640,3641};  // สระล่าง อุ อู 
	
	static public void main(String args[]){
		System.out.println((int)'ุ');
		System.out.println((int)'ู');
	}
	
	public void computeGlyphAdvancesAndPositions (CharSequence str, FloatArray glyphAdvances, FloatArray glyphPositions) {
		
		//super.computeGlyphAdvancesAndPositions(str,glyphAdvances,glyphPositions);
		
	}
	
}
