package org.yeshen.key.common;

import java.util.ArrayList;
import java.util.List;

import org.yeshen.key.R;
import org.yeshen.key.common.Constant.key;
import org.yeshen.key.secure.Keyer;
import org.yeshen.key.secure.KeyerOption;

import android.content.Context;

public class KeyerOptionManager {
	
	public static void init(Context context){
		if(iInner == null){
			iInner = new KeyerOptionManager();
			iInner.readFromCache(context);
		}
	}
	public static KeyerOptionManager ins(){
		return iInner;
	}
	
	private static KeyerOptionManager iInner; 
	private List<KeyerOption> iMenber;
	private int iCurrentId;
	
	public KeyerOption getDefaultOption(){
		if(iMenber != null ){
			KeyerOption ops = find(iCurrentId);
			if(ops != null){
				return ops;
			}
		}
		//error
		return createOption();
	}
	
	public KeyerOption find(int id){
		if(iMenber != null){
			for(KeyerOption s: iMenber){
				if(id == s.iId){
					return s;
				}
			}
		}
		return null;
	}
	
	public void updata(KeyerOption ops){
		for(KeyerOption s: iMenber){
			if(ops.iId == s.iId){
				s.iTitle = ops.iTitle;
				s.iLevel = ops.iLevel;
				s.iTable = ops.iTable;
				s.iOutputLength = ops.iOutputLength;
				s.iLuckNum = ops.iLuckNum;
				return;
			}
		}
		iMenber.add(ops);
	}
	
	public synchronized KeyerOption createOption(){
		KeyerOption ops = new KeyerOption();
		ops.defaultInit(Res.get(R.string.custom) ,YsColor.get(iMenber.size()));
		ops.iColor = YsColor.get(iMenber.size());
		ops.iId = iMenber.size();
		return ops;
	}
	
	public List<KeyerOption> getOptions(){
		return iMenber;
	}
	
	public int getSelectId(){
		return iCurrentId;
	}
	
	public void setSelectId(int id){
		this.iCurrentId = id;
		Cache ins = Cache.ins();
		if(ins != null){
			ins.set(key.YCurrentSelectId, id);
		}
	}
	
	public boolean save(){
		if(iMenber != null && iMenber.size() > 2){
			ArrayList<KeyerOption> customList = new ArrayList<KeyerOption>();
			customList.addAll(iMenber.subList(2, iMenber.size()));
			Cache ins = Cache.ins();
			if(ins != null){
				ins.set(key.YCustomOptionList, customList);
			}
		}
		return true;
	}
	
	private KeyerOptionManager(){
		iMenber = new ArrayList<KeyerOption>(4);
	}
	
	private void readFromCache(Context conetxt){
		iMenber.clear();
		iMenber.addAll(getImmutable(conetxt));
		Cache ins = Cache.ins();
		if(ins != null){
			iCurrentId = ins.get(key.YCurrentSelectId, 0);
			ArrayList<KeyerOption> cac = ins.get(key.YCustomOptionList, new ArrayList<KeyerOption>(0));
			if(cac != null && !cac.isEmpty()){
				iMenber.addAll(cac);
			}
		}
	}
	
	private List<KeyerOption> getImmutable(Context conetxt){
		List<KeyerOption> array = new ArrayList<KeyerOption>(2);
		KeyerOption option = new KeyerOption();
		option.iTitle = Res.get(R.string.default_option);
		option.iColor = YsColor.get(0);
		option.iLevel = Keyer.Level.simple;
		option.iOutputLength = 10;
		option.iLuckNum = 0;
		option.iTable = KeyerOption.OPTION_TABLE_ALL;
		option.isMmult = true;
		option.iId = 0;
		array.add(option);
		
		option = new KeyerOption();
		option.iTitle = Res.get(R.string.bank);
		option.iColor = YsColor.get(1);
		option.iLevel = Keyer.Level.normal;
		option.iOutputLength = 6;
		option.iLuckNum = 0;
		option.iTable = KeyerOption.OPTION_TABLE_NUM;
		option.isMmult = true;
		option.iId = 1;
		array.add(option);
		
		return array;
	}
	
}
