package org.yeshen.key.views;

import org.yeshen.key.R;
import org.yeshen.key.entity.SettingItem;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;

public class SettingItemViewEdit extends LinearLayout implements TextWatcher{
	
	private SettingItem iData;
	public boolean editable = Boolean.TRUE;
	
	private EditText iEditText;
	
	public SettingItemViewEdit(Context context) {
		super(context);
		this.setBackgroundColor(0xFFFFFFFF);
		inflate(context, R.layout.view_setting_item_et, this);
		iEditText = (EditText)findViewById(R.id.setting_item_et);
		iEditText.addTextChangedListener(this);
	}
	
	public void bind(SettingItem item){
		iEditText.setEnabled(editable);
		this.iData = item;
		if(iEditText != null){
			iEditText.setText(item.iEdit);
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {}

	@Override
	public void afterTextChanged(Editable s) {
		if(iData != null){
			iData.iEdit = s.toString();
		}
	}

}
