package org.yeshen.key.views;

import org.yeshen.key.R;
import org.yeshen.key.entity.SettingItem;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class SettingItemViewBt extends LinearLayout implements OnClickListener{
	
	public boolean editable = Boolean.TRUE;
	private SettingItem iData;

	private Button iButton;
	
	public SettingItemViewBt(Context context){
		super(context);
		inflate(context, R.layout.view_setting_item_bt, this);
		iButton = (Button)findViewById(R.id.view_setting_bt);
		if(iButton != null){
			iButton.setOnClickListener(this);
		}
	}
	
	public void bind(SettingItem item){
		this.iData = item;
		if(iButton != null){
			iButton.setText(item.iTitle);
			this.setCheck(item.iIsSelect);
		}
	}
	
	public Boolean isCheck(){
		if(iButton != null){
			Object tag = iButton.getTag();
			if(tag != null && tag instanceof Boolean){
				return (Boolean)tag;
			}
		}
		return Boolean.FALSE;
	}
	
	public void setCheck(Boolean isCheck){
		if(iButton != null){
			iButton.setTag(isCheck);
			if(isCheck.booleanValue()){
				iButton.setBackgroundResource(R.drawable.btn_bg_view_setting_item_sel);
			}else{
				iButton.setBackgroundResource(R.drawable.btn_bg_view_setting_item);
			}
		}
	}

	@Override
	public void onClick(View v) {
		if(editable){
			Boolean check = isCheck();
			if(check.booleanValue()){
				check = Boolean.FALSE;
			}else{
				check = Boolean.TRUE;
			}
			setCheck(check);
			if(iData != null){
				iData.iIsSelect = check;			
			}
		}
	}
	
	
	
}
