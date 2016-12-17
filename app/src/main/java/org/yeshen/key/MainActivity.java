package org.yeshen.key;

import org.yeshen.key.common.Cache;
import org.yeshen.key.common.Constant;
import org.yeshen.key.common.KeyerOptionManager;
import org.yeshen.key.common.Res;
import org.yeshen.key.common.YsColor;
import org.yeshen.key.common.Constant.key;
import org.yeshen.key.secure.Keyer;
import org.yeshen.key.secure.KeyerOption;
import org.yeshen.key.secure.Keyer.AsyncBack;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener,TextWatcher{
	
	//out
	private void goToSettingActivity(String code){
		Intent intent = new Intent(MainActivity.this, SettingActivity.class);
		intent.putExtra(Constant.key.YCode, code);
		if(Constant.SettingCode.WELCOME.equals(code)){
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);			
		}
		startActivity(intent);
	}
	
	private View iRoot;
	private TextView iHeader,iMessage,iOutput;
	private EditText iTargetView,iTargetExtraView;
	private Button iGenButton;
	private int iSelectedOptionId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try{
			ActionBar actionBar = getActionBar();
			if(actionBar != null){
				actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);			
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		
		iRoot = findViewById(R.id.main_root);
		iHeader = (TextView)findViewById(R.id.main_header);
		iMessage = (TextView)findViewById(R.id.main_msg);
		iOutput = (TextView)findViewById(R.id.main_output);
		iTargetView = (EditText)findViewById(R.id.main_traget);
		iTargetExtraView = (EditText)findViewById(R.id.main_target_extra);
		iTargetView.addTextChangedListener(this);
		iTargetExtraView.addTextChangedListener(this);
		iGenButton = (Button)findViewById(R.id.main_gen);
		iGenButton.setOnClickListener(this);
		
		String iUnique = Cache.ins().get(key.YUnique, "");
		if(iUnique.length() == 0){
			goToSettingActivity(Constant.SettingCode.WELCOME);
		}
		
		KeyerOptionManager mgr = KeyerOptionManager.ins();
		if(mgr != null){
			iSelectedOptionId = mgr.getSelectId();
			changeColor(mgr.getDefaultOption().iColor);
		}
	}
	
	@Override  
	public boolean onCreateOptionsMenu(Menu menu) {  
		MenuInflater inflater = getMenuInflater();  
		inflater.inflate(R.menu.mainmenu, menu);  
		return true;  
	}
	
	@Override  
	public boolean onOptionsItemSelected(MenuItem item) {  
		switch (item.getItemId()){
		case R.id.action_setting: 
			goToSettingActivity(Constant.SettingCode.All);			
			break;
		default:
			break;
		}
		return true;
	}
	
	@Override
	public void onResume(){
		super.onResume();
		check();
	}


	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.main_gen){
			onGen();
		}
	}
	
	private void check(){
		KeyerOptionManager mgr = KeyerOptionManager.ins();
		if(mgr != null){
			int id  = mgr.getSelectId();
			if(id != iSelectedOptionId){
				changeColor(mgr.getDefaultOption().iColor);
				iSelectedOptionId = id;
			}
		}
	}
	
	private void changeColor(int color){
		iRoot.setBackgroundColor(color);
		int deepColor = YsColor.getDeepColor(color);
		iHeader.setTextColor(deepColor);
		iGenButton.setTextColor(deepColor);
		iGenButton.setBackgroundResource(YsColor.getResBtn(color));
		print("");
		clearPassword();
	}
	
	public void onGen(){
		String params1 = iTargetView.getText().toString().trim();
		String params2 = iTargetExtraView.getText().toString().trim();
		if(params1.length() == 0 && params2.length() == 0){
			print(Res.get(R.string.empty_input));
			return;
		}
		
		KeyerOptionManager mgr = KeyerOptionManager.ins();
		if(mgr == null){
			print(Res.get(R.string.init_error));
			return;
		}
		KeyerOption ops = mgr.getDefaultOption();
		Cache ins = Cache.ins();
		if(ins != null){
			ins.keepGenRecord(params1 + "|" + params2 , ops.iId);
		}
		ops.iExtraKey = new String[]{params1,params2};
		Keyer.gen(ops, new AsyncBack() {
			
			@Override
			public void onSuccess(String aPassword) {
				StringBuilder builder = new StringBuilder();
				builder.append(getString(R.string.password));
				print(builder.toString());

				setPassword(aPassword);
				CopyToClipBoard(aPassword);
			}
			
			@Override
			public void onFail(String aReason) {
				StringBuilder builder = new StringBuilder();
				builder.append(getString(R.string.error));
				builder.append("\t");
				builder.append(aReason);
				builder.append("\n");
				builder.append(getString(R.string.contact_developer));
				print(builder.toString());
				clearPassword();
			}
		});	
	}

	private void print(String text){iMessage.setText(text);}
	private void clearPassword(){iOutput.setText("");}
	private void setPassword(String pass){  iOutput.setText(pass);}
	private void CopyToClipBoard(String aPassword){
		ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
		ClipData clip = ClipData.newPlainText("simple text",aPassword);
		clipboard.setPrimaryClip(clip);
		Toast.makeText(MainActivity.this,getString(R.string.copy_to_clipboard),Toast.LENGTH_SHORT).show();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {}

	@Override
	public void afterTextChanged(Editable s) {
		String params1 = iTargetView.getText().toString().trim();
		String params2 = iTargetExtraView.getText().toString().trim();
		Cache ins = Cache.ins();
		KeyerOptionManager mgr = KeyerOptionManager.ins();
		if(ins != null && mgr != null){
			int id = ins.matchGenRecord(params1 + "|" + params2);
			if(id >= 0){
				if(iSelectedOptionId != id){
					mgr.setSelectId(id);
					check();
				}
			}
		}
	}
	
	
}
