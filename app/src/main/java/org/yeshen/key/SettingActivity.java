package org.yeshen.key;

import java.io.Serializable;

import org.yeshen.key.common.Constant;
import org.yeshen.key.secure.KeyerOption;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

public class SettingActivity extends FragmentActivity{
	
	//out
	private void displayWelComeFragment(){
		quto = SettingWelcomeFragment.newInstance();
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.setting_frame,quto);
		transaction.commit();
	}
	
	public void displayAllSettingFragment(){
		quto = SettingAllFragment.newInstance();
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.setting_frame,quto);
		transaction.commit();
	}
	
	public void displayDetailsSettingFragment(KeyerOption option){
		quto = SettingAllFragment.newInstance(option);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.setting_frame,quto);
		transaction.commit();
	}
	
	private BaseFragment quto;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		Intent intent = this.getIntent();
		String code = intent.getStringExtra(Constant.key.YCode);
		if(code == null){code = Constant.SettingCode.All;}
		
		if(Constant.SettingCode.WELCOME.equals(code)){
			displayWelComeFragment();
			
		}else if(Constant.SettingCode.All.equals(code)){
			displayAllSettingFragment();
			
		}else if(Constant.SettingCode.DETAILS.equals(code)){
			Serializable serializable = intent.getSerializableExtra(Constant.key.YOption);
			KeyerOption option = null;
			if(serializable != null && serializable instanceof KeyerOption){
				option = (KeyerOption)serializable;
			}else{
				option = new KeyerOption();
			}
			displayDetailsSettingFragment(option);
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent event){
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(quto != null){
				if(quto.onKeyDown(keyCode, event)){
					return true;
				}
			}
		}
		return super.onKeyDown(keyCode, event);
		
	}
	
	
	
}
