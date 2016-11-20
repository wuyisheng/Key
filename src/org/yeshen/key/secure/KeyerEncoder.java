package org.yeshen.key.secure;

public interface KeyerEncoder {
	
	public void refush(KeyerOption ops);
	public String encode(String in) throws Exception;
	
}
