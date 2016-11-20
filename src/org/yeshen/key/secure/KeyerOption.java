package org.yeshen.key.secure;

import java.io.Serializable;

import org.yeshen.key.common.Cache;
import org.yeshen.key.common.Constant.key;

public class KeyerOption implements Serializable{
	private static final long serialVersionUID = 3907L;
	
	public static final char[] OPTION_TABLE_ALL = 
			(",.~^*&?#$%"  
			+ "1234567890"
			+ "abcdefghijklmnopqrstuvwxyz"
			+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
	public static final char[] OPTION_TABLE_NUM = "1234567890".toCharArray();
	public static final char[] OPTION_TABLE_LTRRERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	public static final char[] OPTION_TABLE_SING = ",.~^*&?#$%".toCharArray();

	//display
	public int iId;
	public String iTitle;
	public int iColor;
	public boolean isMmult = false;
	
	//setting
	public Keyer.Level iLevel;
	public String[] iExtraKey;
	public int iLuckNum;
	public char[] iTable;
	public int iOutputLength;
	
	//out put
	public String iOut;
	
	public KeyerOption defaultInit(String title,int color){
		this.iTitle = title;
		this.iColor = color;
		this.iOutputLength = 8;
		this.iLevel = Keyer.Level.normal;
		this.iLuckNum = 0;
		this.iTable = new char[0];
		return this;
	}
	
	public void set(String[] extra){
		this.iExtraKey = extra;
	}
	
	public boolean isVaild(){
		if(iLevel == null || iExtraKey == null || iExtraKey.length == 0
				|| iTable == null || iTable.length == 0 || iOutputLength == 0){
			return false;
		}
		return true;
	}
	
	public boolean isVaildSetting(){
		return this.iLevel != null && iTitle != null && iTitle.length() > 0 
				&& iOutputLength > 0 && iLuckNum >= 0 
				&& iTable != null && iTable.length > 0;
	}
	
	public static String getUnique(){
		Cache ins = Cache.ins();
		if(ins != null){
			return ins.get(key.YUnique, "");
		}
		return "";
	}
	
}
