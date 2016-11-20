package org.yeshen.key.entity;

public class SettingItem {
	public enum Type{
		about,option,creat,item
	}
	
	public Type iType;
	public String iTitle;
	public int iColor;
	public Boolean iIsSelect;
	public String iEdit;
	public int iId;
	
	public SettingItem(){
		iType = Type.item;
	}
	
	public void setSelect(Boolean isSelect){
		this.iIsSelect = isSelect;
	}
	
	public String getEdit(){
		return iEdit == null ? "" : iEdit;
	}
	
	
}
