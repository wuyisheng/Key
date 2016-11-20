package org.yeshen.key;

import java.io.Serializable;

import org.yeshen.key.common.Constant.SettingCode;
import org.yeshen.key.common.Constant.key;
import org.yeshen.key.common.Constant;
import org.yeshen.key.common.KeyerOptionManager;
import org.yeshen.key.common.Res;
import org.yeshen.key.entity.SettingGroup;
import org.yeshen.key.entity.SettingItem;
import org.yeshen.key.secure.KeyerOption;
import org.yeshen.key.views.SettingGroupAdapter;
import org.yeshen.key.views.SettingItemViewLine;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class SettingAllFragment extends BaseFragment implements OnClickListener,OnLongClickListener{
	
	private void goToAboutPage(){
		Intent intent = new Intent();        
        intent.setAction("android.intent.action.VIEW");    
        Uri content_url = Uri.parse("http://www.yeshen.org/app/key.html?version=1.0");   
        intent.setData(content_url);  
        startActivity(intent);
	}
	
	private void goToDetailsSetting(KeyerOption option){
		Intent intent = new Intent(getActivity(), SettingActivity.class);
		intent.putExtra(Constant.key.YCode, SettingCode.DETAILS);
		intent.putExtra(key.YOption, option);
		startActivityForResult(intent, 1);
	}
	
	public static SettingAllFragment newInstance(){
		SettingAllFragment fragment = new SettingAllFragment();
		Bundle bundle = new Bundle();
		bundle.putString(key.YCode, SettingCode.All);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	public static SettingAllFragment newInstance(KeyerOption option){
		SettingAllFragment fragment = new SettingAllFragment();
		Bundle bundle = new Bundle();
		bundle.putString(key.YCode, SettingCode.DETAILS);
		bundle.putSerializable(key.YOption, option);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	private SettingGroup group;
	private SettingGroupAdapter iAdapter;
	private LinearLayout iListView;
	private long clickTime;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		ScrollView view = new ScrollView(getActivity());
		iListView = new LinearLayout(getActivity());
		iListView.setOrientation(LinearLayout.VERTICAL);
		iListView.setBackgroundColor(0xFFF1F1F1);
		view.setBackgroundColor(0xFFF1F1F1);
		view.addView(iListView);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		init();
	}
	
	private void setTitle(String title){
		ActionBar actionBar = getActivity().getActionBar();
		if(actionBar != null){
			actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
			actionBar.setTitle(title);			
		}
	}
	
	private void attachFoot(){
		if(iListView == null){return;}
		
		iListView.setBackgroundColor(group.iColor);
		ViewParent parent = iListView.getParent();
		if(parent != null && parent instanceof View){
			((View)parent).setBackgroundColor(group.iColor);
		}
		
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View v = inflater.inflate(R.layout.view_setting_item_foot, iListView,false);
		iListView.addView(v);
	}
	
	private void init(){
		group = new SettingGroup();
		Bundle bundle = this.getArguments();
		if(bundle != null){
			String code = bundle.getString(key.YCode, SettingCode.All);
			if(SettingCode.All.equals(code)){
				setTitle(Res.get(R.string.setting));
				
				KeyerOptionManager mgr = KeyerOptionManager.ins();
				if(mgr != null){
					group.load(mgr.getOptions(), mgr.getSelectId());
				}
			}else if(SettingCode.DETAILS.equals(code)){
				KeyerOption option = null;
				Serializable serializable = bundle.getSerializable(key.YOption);
				if(serializable != null && serializable instanceof KeyerOption){
					option = (KeyerOption)serializable;
					
					String title = Res.get(R.string.setting) + " " + option.iTitle;
					setTitle(title);
				}
				group.load(option);
			}
		}
		
		iAdapter = new SettingGroupAdapter(getActivity(),group,this);
		if(iListView != null){
			iListView.removeAllViews();
			for(int i = 0; i< iAdapter.getCount(); i++){
				iListView.addView(iAdapter.getView(i, null, iListView));
			}
			attachFoot();
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK){
			init();
		}
	}

	@Override
	public boolean onLongClick(View v) {
		if(v != null){
			if(v instanceof SettingItemViewLine){
				SettingItem item = ((SettingItemViewLine)v).getQuto();
				if(item != null){
					SettingItem.Type type = item.iType;
					if(SettingItem.Type.option.equals(type)){
						KeyerOptionManager mgr = KeyerOptionManager.ins();
						if(mgr != null){
							KeyerOption ops = mgr.find(item.iId);
							if(ops != null){
								goToDetailsSetting(ops);								
							}
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public void onClick(View v) {
		if(v != null){
			if(v instanceof SettingItemViewLine){
				SettingItem item = ((SettingItemViewLine)v).getQuto();
				if(item != null){
					SettingItem.Type type = item.iType;
					if(SettingItem.Type.option.equals(type)){
						
						KeyerOptionManager mgr = KeyerOptionManager.ins();
						if(mgr != null){
							int oldId = mgr.getSelectId();
							if(oldId != item.iId){
								mgr.setSelectId(item.iId);
								//refresh
								init();						
							}
							long time = System.currentTimeMillis();
							if(time - clickTime < 200){
								Toast.makeText(getActivity(), 
										Res.get(R.string.selected_long_press_to_edit), 
										Toast.LENGTH_SHORT).show();
							}
							this.clickTime = time;
						}
					}else if(SettingItem.Type.about.equals(type)){
						goToAboutPage();
					}else if(SettingItem.Type.creat.equals(type)){
						KeyerOptionManager mgr = KeyerOptionManager.ins();
						if(mgr != null){
							goToDetailsSetting(mgr.createOption());							
						}
					}
				}
			}
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent event){
		if(group != null && group.iIsOperatable){
			KeyerOption ops = group.get();
			if(!ops.isVaildSetting()){
				Toast.makeText(getActivity(), Res.get(R.string.invaild_setting), Toast.LENGTH_LONG).show();
			}else{
				KeyerOptionManager mgr = KeyerOptionManager.ins();
				mgr.updata(ops);
				mgr.save();
				getActivity().setResult(Activity.RESULT_OK);
				getActivity().finish();
				return true;
			}
		}
		return false;
	}
	
}
