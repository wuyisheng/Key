package org.yeshen.key.common;

import java.io.Serializable;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Cache {
	
	private static Cache inner;
	public static Cache ins(){
		return inner;
	}
	
	public static void init(Context context){
		if(inner == null){
			inner = new Cache(context);
		}
	}
	
	private final String TAG = "yeshen_cache";
	private final String TAG_User = "user_cache";
	private final String TAG_User_Header = "user_";
	
	private SharedPreferences sharePreferences,sharePreferencesUser;
	
	private Cache(Context context){
		sharePreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
		sharePreferencesUser = context.getSharedPreferences(TAG_User, Context.MODE_PRIVATE);
	}
	
	public void keepGenRecord(String input,int currentOptionId){
		Editor editor = sharePreferencesUser.edit();
		editor.putInt(TAG_User_Header + input, currentOptionId);
		editor.commit();
	}
	
	//if not found return -1;
	public int matchGenRecord(String input){
		return sharePreferencesUser.getInt(TAG_User_Header + input, -1);
	}
	
	public void set(String key,Object object){
		if(object == null){ return;}
		
		Editor editor = sharePreferences.edit();
		if(object instanceof String){
			editor.putString(key, (String)object);
		}else if(object instanceof Integer){
			editor.putInt(key, (Integer)object);
		}else if(object instanceof Long){
			editor.putLong(key, (Long)object);
		}else if(object instanceof Boolean){
			editor.putBoolean(key, (Boolean)object);	
		}else if(object instanceof Serializable){
			String serial = Serialize.toString((Serializable)object);
			editor.putString(key, serial);
		}else{
			//ignore it
		}
		editor.commit();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String key,T defValue){
		if(defValue == null){ return null; }
		
		if(defValue instanceof String){
			return (T)sharePreferences.getString(key, (String)defValue);
		}else if(defValue instanceof Integer){
			return (T)Integer.valueOf(sharePreferences.getInt(key, (Integer)defValue));
		}else if(defValue instanceof Long){
			return (T)Long.valueOf(sharePreferences.getLong(key, (Long)defValue));
		}else if(defValue instanceof Boolean){
			return (T)Boolean.valueOf(sharePreferences.getBoolean(key, (Boolean)defValue));
		}else{
			String obj = sharePreferences.getString(key,"");
			return Serialize.toObject(obj, defValue);
		}
	}
	
}
