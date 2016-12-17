package org.yeshen.key.entity;

import java.util.ArrayList;
import java.util.List;

public class SettingTeam {
	
	public enum Type{
		edit,button,buttonLine
	}
	
	public Type iType;
	public String iTitle;
	public boolean isSingleSelect;
	public List<SettingItem> iMember;
	
	public SettingTeam(String title,Type type){
		this.iType = type;
		this.iTitle = title;
		this.isSingleSelect = true;
		this.iMember = new ArrayList<SettingItem>();
	}
	
	public void append(SettingItem item){
		this.iMember.add(item);
	}
	
	public int count(){
		return iMember.size();
	}
	
	public SettingItem get(int index){
		return iMember.get(index);
	}
}
