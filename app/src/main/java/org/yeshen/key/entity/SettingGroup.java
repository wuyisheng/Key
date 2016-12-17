package org.yeshen.key.entity;

import java.util.ArrayList;
import java.util.List;

import org.yeshen.key.R;
import org.yeshen.key.common.Res;
import org.yeshen.key.common.YsColor;
import org.yeshen.key.secure.Keyer;
import org.yeshen.key.secure.KeyerOption;

public class SettingGroup {

	public List<SettingTeam> iMember;
	public boolean iIsOperatable;
	public int iId;
	public int iColor;
	
	public SettingGroup(){
		this.iId = -1;
		this.iColor = YsColor.get(0);
		this.iIsOperatable = false;
		this.iMember = new ArrayList<SettingTeam>();
	}
	
	public int count(){
		return iMember.size();
	}
	
	public SettingTeam get(int index){
		return iMember.get(index);
	}
	
	public void load(List<KeyerOption> options,int selectId){
		SettingTeam team = null;
		SettingItem item = null;
		
		team = new SettingTeam(Res.get(R.string.setting),SettingTeam.Type.buttonLine);
		for(int i = 0 ; i < options.size(); i++){
			KeyerOption s = options.get(i);
			item = new SettingItem();
			item.iTitle = s.iTitle;
			item.iColor = s.iColor;
			item.iId = s.iId;
			item.iIsSelect = s.iId == selectId;
			item.iType = SettingItem.Type.option;
			team.append(item);
		}
		if(options.size() < 5){
			item = new SettingItem();
			item.iTitle = Res.get(R.string.creat_custom);
			item.iType = SettingItem.Type.creat;
			team.append(item);
		}
		iMember.add(team);
		
		team = new SettingTeam(Res.get(R.string.more),SettingTeam.Type.buttonLine);
		item = new SettingItem();
		item.iTitle = Res.get(R.string.about_us);
		item.iType = SettingItem.Type.about;
		team.append(item);
		iMember.add(team);
	}
	
	public void load(KeyerOption option){
		this.iId = option.iId;
		this.iColor = option.iColor;
		this.iIsOperatable = !option.isMmult;
		SettingTeam team = null;
		SettingItem item = null;
		team = new SettingTeam(Res.get(R.string.title),SettingTeam.Type.edit);
		item = new SettingItem();
		item.iTitle = option.iTitle;
		item.iEdit = option.iTitle;
		team.append(item);
		iMember.add(team);
		
		team = new SettingTeam(Res.get(R.string.pass_len),SettingTeam.Type.edit);
		item = new SettingItem();
		item.iEdit = String.valueOf(option.iOutputLength);
		team.append(item);
		iMember.add(team);
		
		team = new SettingTeam(Res.get(R.string.pass_contain),SettingTeam.Type.button);
		team.isSingleSelect = false;
		char[] p = KeyerOption.OPTION_TABLE_ALL;
		
		String table = option.iTable == null ? "" : new String(option.iTable);
		for(char s:p){
			item = new SettingItem();
			String ch = String.valueOf(s);
			item.iTitle = ch;
			item.iIsSelect = table.contains(ch);
			team.append(item);
		}
		iMember.add(team);
		
		team = new SettingTeam(Res.get(R.string.level),SettingTeam.Type.button);
		item = new SettingItem();
		item.iTitle = Res.get(R.string.simple);
		item.iIsSelect = Keyer.Level.simple.equals(option.iLevel);
		team.append(item);
		iMember.add(team);
		
		team = new SettingTeam(Res.get(R.string.lucky_num),SettingTeam.Type.edit);
		item = new SettingItem();
		item.iEdit = String.valueOf(option.iLuckNum);
		team.append(item);
		iMember.add(team);
	}
	
	public KeyerOption get(){
		KeyerOption option = new KeyerOption();
		option.iId = this.iId;
		option.iColor = this.iColor;
		for(SettingTeam team:iMember){
			if(team.iMember != null && !team.iMember.isEmpty()){
				if(Res.get(R.string.title).equals(team.iTitle)){
					option.iTitle = team.iMember.get(0).iEdit;					
					
				}else if(Res.get(R.string.pass_len).equals(team.iTitle)){
					try{
						option.iOutputLength = Integer.parseInt(team.iMember.get(0).iEdit);						
					}catch(NumberFormatException e){}				
					
				}else if(Res.get(R.string.pass_contain).equals(team.iTitle)){
					StringBuilder builder = new StringBuilder();
					for(SettingItem s:team.iMember){
						if(s.iIsSelect){
							builder.append(s.iTitle);
						}
					}
					option.iTable = builder.toString().toCharArray();
					
				}else if(Res.get(R.string.level).equals(team.iTitle)){
					option.iLevel = team.iMember.get(0).iIsSelect ? Keyer.Level.simple : Keyer.Level.normal;
					
				}else if(Res.get(R.string.lucky_num).equals(team.iTitle)){
					try{
						option.iLuckNum = Integer.parseInt(team.iMember.get(0).iEdit);						
					}catch(NumberFormatException e){}
					
				}
			}
		}
		return option;
	}
}
