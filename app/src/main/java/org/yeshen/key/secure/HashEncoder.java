package org.yeshen.key.secure;

public class HashEncoder implements KeyerEncoder{
	
	public HashEncoder(){}

	@Override
	public void refush(KeyerOption ops) {
		
	}
	
	@Override
	public String encode(String in) throws Exception {
		if(in == null || in.length() == 0){
			throw new IllegalArgumentException();
		}
		return String.valueOf(in.hashCode());
	}


	
	
	
}
