package org.yeshen.key.views;

import org.yeshen.key.R;
import org.yeshen.key.entity.SettingItem;
import org.yeshen.key.entity.SettingTeam;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingTeamView extends LinearLayout{
	
	private SettingTeam iData;
	
	public OnClickListener iListener;
	public OnLongClickListener iLongListener;
	private TextView iTitleView;
	private LinearLayout iContainView;
	
	public SettingTeamView(Context context){
		super(context);
		inflate(context, R.layout.view_setting_team, this);
		iTitleView = (TextView)findViewById(R.id.view_setting_team_title);
		iContainView = (LinearLayout)findViewById(R.id.view_setting_team_contain);
	}
	
	public void bind(SettingTeam data,boolean editbale){
		this.iData = data;
		if(iTitleView != null && iContainView != null && iData != null){
			iTitleView.setText(this.iData.iTitle);
			iContainView.removeAllViews();
			if(SettingTeam.Type.buttonLine.equals(data.iType)){
				for(SettingItem s : data.iMember){
					SettingItemViewLine view = new SettingItemViewLine(getContext());
					view.bind(s);
					view.setOnClickListener(iListener);
					view.setOnLongClickListener(iLongListener);
					iContainView.addView(view);
				}
				
			}else if(SettingTeam.Type.edit.equals(data.iType)){
				for(SettingItem s : data.iMember){
					SettingItemViewEdit view = new SettingItemViewEdit(getContext());
					view.editable = editbale;
					view.bind(s);
					iContainView.addView(view);
				}
				
			}else if(SettingTeam.Type.button.equals(data.iType)){
				
				for(int i = 0; i < data.iMember.size(); i=i+6){
					LinearLayout lay = new LinearLayout(getContext());
					for(int j = i ; j < Math.min(data.iMember.size(), i+6); j++){
						SettingItem s = data.iMember.get(j);
						SettingItemViewBt view = new SettingItemViewBt(getContext());
						view.editable = editbale;
						view.bind(s);
						lay.addView(view);
					}
					iContainView.addView(lay);
				}
			}
		}
	}

	
}
