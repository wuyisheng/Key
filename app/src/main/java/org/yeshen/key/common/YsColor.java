package org.yeshen.key.common;

import org.yeshen.key.R;

public class YsColor {
	
	public static final int[] color = new int[]{
		0xFFF1F1F1,0xFFFFF6EF,0xFFF0FDF0,0xFFFbF6FC,0xFFF7F7E8
	};
	
	public static final int[] deep = new int[]{
		0xFF666666,0xFFDB7D6D,0xFF709A7B,0xFF548BB6,0xFFB88940
	};
	
	public static final int[] drawableCircularSelect = new int[]{
		R.drawable.color_circular_sel_1,R.drawable.color_circular_sel_2,
		R.drawable.color_circular_sel_3,R.drawable.color_circular_sel_4,
		R.drawable.color_circular_sel_5
	};
	
	public static final int[] drawableCircular = new int[]{
		R.drawable.color_circular_1,R.drawable.color_circular_2,
		R.drawable.color_circular_3,R.drawable.color_circular_4,
		R.drawable.color_circular_5
	};
	
	public static final int[] drawableBtn = new int[]{
		R.drawable.color_btn_1,R.drawable.color_btn_2,
		R.drawable.color_btn_3,R.drawable.color_btn_4,
		R.drawable.color_btn_5
	};
	
	
	public static int get(int index){
		int s = index % color.length;
		return color[s];
	}
	
	public static int getDeepColor(int c){
		for(int i = 0; i < 5 ; i++){
			if(c==color[i]){
				return deep[i];
			}
		}
		return deep[4];
	}
	
	public static int getResCircularSel(int c){
		for(int i = 0; i < 5 ; i++){
			if(c==color[i]){
				return drawableCircularSelect[i];
			}
		}
		return drawableCircularSelect[4];
	}
	
	public static int getResCircular(int c){
		for(int i = 0; i < 5 ; i++){
			if(c==color[i]){
				return drawableCircular[i];
			}
		}
		return drawableCircular[4];
	}
	
	public static int getResBtn(int c){
		for(int i = 0; i < 5 ; i++){
			if(c==color[i]){
				return drawableBtn[i];
			}
		}
		return drawableBtn[4];
	}
	
	
}
