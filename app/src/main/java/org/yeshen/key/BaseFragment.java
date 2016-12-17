package org.yeshen.key;

import android.support.v4.app.Fragment;
import android.view.KeyEvent;

public abstract class BaseFragment extends Fragment{
	
	protected boolean onKeyDown(int keyCode,KeyEvent event){
		return false;
	}
	
}
