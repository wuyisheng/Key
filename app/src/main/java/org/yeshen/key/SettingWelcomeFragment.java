package org.yeshen.key;

import org.yeshen.key.common.Cache;
import org.yeshen.key.common.Constant;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SettingWelcomeFragment extends BaseFragment implements TextWatcher,OnClickListener{

	//out
	private void goToMainActivity(){
		Intent intent = new Intent(getActivity(), MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	
	public static BaseFragment newInstance(){
		SettingWelcomeFragment fragment = new SettingWelcomeFragment();
		Bundle bundle = new Bundle();
		fragment.setArguments(bundle);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	private EditText iUniqueEditView;
	private Button iUniqueButton;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View contain = inflater.inflate(R.layout.fragment_setting_welcome, container,false);
		iUniqueEditView = (EditText)contain.findViewById(R.id.setting_welcome_edit);
		iUniqueButton = (Button)contain.findViewById(R.id.setting_welcome_sure);
		return contain;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		if(iUniqueEditView != null && iUniqueButton != null){
			iUniqueEditView.addTextChangedListener(this);
			iUniqueButton.setOnClickListener(this);
			iUniqueButton.setClickable(false);
			
		    ActionBar actionBar = getActivity().getActionBar();
		    if(actionBar != null){
		    	actionBar.hide();		    	
		    }
		}
	}


	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {}
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {}

	@Override
	public void afterTextChanged(Editable s) {
		if(iUniqueButton != null){
			iUniqueButton.setClickable(s != null && s.length() > 0);
		}
	}

	@Override
	public void onClick(View v) {
		Cache cache = Cache.ins();
		Editable s = iUniqueEditView.getText();
		if(cache != null && s != null && s.length() > 0){
			cache.set(Constant.key.YUnique, s.toString());
			goToMainActivity();
		}
	}
	
}
