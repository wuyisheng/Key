package org.yeshen.key.common;

import android.content.Context;

public class Res {
	private static Res iInner;
	public static void init(Context context){
		if(iInner == null){
			iInner = new Res(context);
		}
	}
	
	public static String get(int resId){
		if(iInner != null){
			return iInner.getString(resId);
		}
		return "";
	}
	
	private Context iContext;
	private Res(Context context){
		this.iContext = context;
	}
	
	public String getString(int resId){
		if(iContext != null){
			return iContext.getString(resId);
		}
		return "";
	}
	
	
}
