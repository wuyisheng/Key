package org.yeshen.key.views;

import org.yeshen.key.R;
import org.yeshen.key.common.YsColor;
import org.yeshen.key.entity.SettingItem;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingItemViewLine extends LinearLayout {
	
	private SettingItem iData;
	private ImageView iIcon;
	private TextView iTitle;
	
	public SettingItemViewLine(Context context) {
		super(context);
		inflate(context, R.layout.view_setting_item_line, this);
		this.iIcon = (ImageView)findViewById(R.id.setting_item_line_icon);
		this.iTitle = (TextView)findViewById(R.id.setting_item_line_title);
	}
	
	public void bind(SettingItem item){
		this.iData = item;
		if(iData != null){
			if(SettingItem.Type.creat.equals(iData.iType)
					|| SettingItem.Type.about.equals(iData.iType)){
				
				this.iIcon.setVisibility(View.GONE);
				this.iTitle.setText(iData.iTitle);
			}else{
				this.iIcon.setVisibility(View.VISIBLE);
				int resId = 0;
				if(iData.iIsSelect){
					resId = YsColor.getResCircularSel(iData.iColor);
				}else{
					resId = YsColor.getResCircular(iData.iColor);
				}
				this.iIcon.setBackgroundResource(resId);
				this.iTitle.setText(iData.iTitle);	
			}
		}
	}
	
	public SettingItem getQuto(){
		return iData;
	}

}
