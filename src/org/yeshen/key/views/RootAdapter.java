package org.yeshen.key.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class RootAdapter extends BaseAdapter{
	
	private Context iContext;
	private LayoutInflater iInflater;
	
	public RootAdapter(Context context){
		this.iContext = context;
		this.iInflater = LayoutInflater.from(context);
	}
	
	public Context getContext(){
		return iContext;
	}
	
	public View getView(int resId,ViewGroup parent){
		return iInflater.inflate(resId, parent,false);
	}
	
	public String getString(int resId){
		return iContext.getString(resId);
	}
	
}
