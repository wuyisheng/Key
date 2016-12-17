package org.yeshen.key.secure;

import java.util.HashMap;
import android.os.AsyncTask;

public class Keyer {
	
	public enum Level{
		simple,normal
	}
	public enum EncoderType{
		md5,rsa,pass,hash
	}
	
	public static Keyer ins;
	
	public interface AsyncBack{
		void onSuccess(String aPassword);
		void onFail(String aReason);
	}
	
	public static synchronized void gen(KeyerOption option,AsyncBack aDelegate){
		if(ins == null){ ins = new Keyer(); }
		ins.iDelegate = aDelegate;
		ins.innerEncode(option);
	}
	
	public AsyncBack iDelegate;
	public Keyer(){
		encoderMap = new HashMap<EncoderType, KeyerEncoder>(4);
		encoderMap.put(EncoderType.hash, new HashEncoder());
		encoderMap.put(EncoderType.md5, new MD5Encoder());
		encoderMap.put(EncoderType.rsa, new RsaEncoder());
		encoderMap.put(EncoderType.pass, new PasswordEncoder());
	}
	
	public void innerEncode(KeyerOption option){
		if(encoderTask != null){
			encoderTask.cancel(true);
			encoderTask = null;
		}
		encoderTask = new AsyncTask<KeyerOption, Boolean, Boolean>(){
			private KeyerOption quto;
			private StringBuffer error;
			@Override
			protected Boolean doInBackground(KeyerOption... params) {
				error = new StringBuffer();
				if(params == null || params.length == 0 ){
					error.append("ill input");
					return false;
				}
				KeyerOption ops = params[0];
				this.quto = ops;
				if(!ops.isVaild()){
					error.append("ill input");
					return false;
				}
				if(encoderMap == null){
					error.append("encoder not ready");
					return false;
				}
				StringBuilder sb = new StringBuilder();
				
				write(EncoderType.md5,sb,KeyerOption.getUnique());
				
				if(ops.iExtraKey != null && ops.iExtraKey.length > 0){
					int length = ops.iExtraKey.length;
					
					write(EncoderType.rsa,sb,ops.iExtraKey[0]);
					if(length > 1){
						for(int i = 1; i < length; i++){
							write(EncoderType.hash,sb,ops.iExtraKey[i]);
						}
					}
				}
				
				KeyerEncoder enco = encoderMap.get(EncoderType.pass);
				if(enco != null){
					enco.refush(ops);
					try{
						ops.iOut = enco.encode(sb.toString());
					}catch(Exception e){
						if(e != null && error != null){
							error.append(e.getMessage());							
						}
					}
				}
				
				if(ops.iOut == null || ops.iOut.length() == 0){
					return false;					
				}
				return true;
			}
			
			public boolean write(EncoderType type,StringBuilder output,String input){
				KeyerEncoder enco = encoderMap.get(type);
				if(enco != null){
					try{
						output.append(enco.encode(input));	
					}catch(Exception e){
						if(e != null && error != null){
							error.append(e.getMessage());							
						}
						return false;
					}
				}
				return true;
			}
			
			@Override
			protected void onPostExecute(Boolean sccuess){
				super.onPostExecute(sccuess);
				if( sccuess != null && this.quto != null && Keyer.this.iDelegate != null){
					String password = this.quto.iOut;
					if(sccuess && password != null){
						Keyer.this.iDelegate.onSuccess(password);
					}else{
						if(error == null || error.length() == 0){
							error = new StringBuffer("unknow error");
						}
						Keyer.this.iDelegate.onFail(password);
					}					
				}
			}
		};
		encoderTask.execute(option);
	}
	
	private HashMap<EncoderType,KeyerEncoder> encoderMap;
	private AsyncTask<KeyerOption, Boolean, Boolean> encoderTask;
	
}
