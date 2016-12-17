package org.yeshen.key.secure;

import java.security.MessageDigest;

public class MD5Encoder implements KeyerEncoder{
	
	public MD5Encoder(){
		
	}
	

	@Override
	public void refush(KeyerOption ops) {
		
	}
	
	@Override
	public String encode(String in) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");  
        md5.update(in.getBytes());  
        byte[] m = md5.digest();
        StringBuffer sb = new StringBuffer();  
        for(int i = 0; i < m.length; i ++){  
        	sb.append(m[i]);  
        }
        return sb.toString().replace("-", "");
	}

}
