package org.yeshen.key.views;

import org.yeshen.key.SettingAllFragment;
import org.yeshen.key.entity.SettingGroup;
import org.yeshen.key.entity.SettingTeam;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;

public class SettingGroupAdapter extends RootAdapter{
	
	private SettingGroup iData;
	private OnClickListener iListener;
	private OnLongClickListener iLongListener;
	
	public SettingGroupAdapter(Context context,SettingGroup data,SettingAllFragment obj){
		super(context);
		this.iData = data;
		this.iListener = obj;
		this.iLongListener = obj;
	}

	@Override
	public int getCount() {
		return iData.count();
	}

	@Override
	public SettingTeam getItem(int position) {
		return iData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SettingTeam team = getItem(position);
		if(convertView == null || !(convertView instanceof SettingTeamView)){
			SettingTeamView view = new SettingTeamView(getContext());
			view.iListener = this.iListener;
			view.iLongListener = this.iLongListener;
			view.bind(team,iData.iIsOperatable);
			convertView = view;
		}else{
			((SettingTeamView)convertView).iListener = this.iListener;
			((SettingTeamView)convertView).iLongListener = this.iLongListener;
			((SettingTeamView)convertView).bind(team,iData.iIsOperatable);
		}
		return convertView;
	}

}
