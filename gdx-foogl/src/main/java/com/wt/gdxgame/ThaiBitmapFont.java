
package com.wt.gdxgame;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.FloatArray;

public class ThaiBitmapFont extends BitmapFont{
	public static final char[] uChars={'�','�','�','�'}; 
	public static final char[] dChars={'�','�','�','�'};
	public static final char[] vChars={3656,3657,3658,3659};  // ��ó�ء��
	public static final char[] tChars={3636,3637,3638,3639,};  // ��к� �� �� �� ��
	public static final char[] oChars={3635};  // ��к� ��
	public static final char[] gChars={3640,3641};  // �����ҧ �� �� 
	
	static public void main(String args[]){
		System.out.println((int)'�');
		System.out.println((int)'�');
	}
	
	public void computeGlyphAdvancesAndPositions (CharSequence str, FloatArray glyphAdvances, FloatArray glyphPositions) {
		
		//super.computeGlyphAdvancesAndPositions(str,glyphAdvances,glyphPositions);
		
	}
	
}
