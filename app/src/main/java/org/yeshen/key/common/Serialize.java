package org.yeshen.key.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.util.Base64;

public class Serialize {
	
	public static String toString(Serializable object){
		String serial = "";
		try{
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(out);
			oo.writeObject(object);
			oo.writeInt(000);
			oo.flush();
			oo.close();
			serial = android.util.Base64.encodeToString(out.toByteArray(),Base64.DEFAULT);
		}catch(Exception e){
			e.printStackTrace();
		}
		return serial;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T toObject(String input,T defValue){
		if(input == null || input.length() == 0){
			return defValue;
		}
		T target = null;
        try {
        	byte[] array = android.util.Base64.decode(input.trim(), Base64.DEFAULT);
        	InputStream in = new ByteArrayInputStream(array);
        	ObjectInputStream oi = new ObjectInputStream(in);   
            target = (T) oi.readObject();
            oi.close();   
        }catch (Exception e){ 
        	e.printStackTrace();
        	//ignore it
        }
        if(target != null){
        	return target;
        }
        return defValue;
	}
	
}
