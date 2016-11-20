package org.yeshen.key.secure;

import java.util.Locale;

public class PasswordEncoder implements KeyerEncoder{
	
	private char[] table;
	private int length;
	private int lucky;
	
	public PasswordEncoder(){
		//default
		this.table = KeyerOption.OPTION_TABLE_ALL;
		this.length = 9;
		this.lucky = 0;
	}
	
	@Override
	public void refush(KeyerOption ops) {
		this.table = ops.iTable;
		this.length = ops.iOutputLength;
		this.lucky = ops.iLuckNum;
	}
	
	@Override
	public String encode(String input) throws Exception{
		if(input==null || input.length() == 0)return "";
		input = input.toUpperCase(Locale.CHINESE);
		long[] output = new long[length];
		for(int i = 0; i < length; i++){
			output[i] = 0;
		}
		
		for(int i = 0; i < input.length() ; i++){
			int index = i %  length;
			char bcd = input.charAt(i);
	        if ((bcd >= '0') && (bcd <= '9')){
	        	output[index] += bcd - '0';
	        }else if ((bcd >= 'A') && (bcd <= 'F')){
	        	output[index] += bcd - 'A' + 10;
	        }
		}
		
		String luckInput = String.valueOf(Math.abs(String.valueOf(this.lucky).hashCode()));
		boolean odd = (this.lucky % 2) == 1;
		int luck = Math.min(lucky, luckInput.length());
		for(int i = 0; i < luck; i++){
			int index = (i + this.lucky) %  length;
			char bcd = luckInput.charAt(i);
			if(odd){
		        if ((bcd >= '0') && (bcd <= '9')){
		        	output[index] -= bcd - '0';
		        }else if ((bcd >= 'A') && (bcd <= 'F')){
		        	output[index] -= bcd - 'A' + 10;
		        }
			}else{
		        if ((bcd >= '0') && (bcd <= '9')){
		        	output[index] += bcd - '0';
		        }else if ((bcd >= 'A') && (bcd <= 'F')){
		        	output[index] += bcd - 'A' + 10;
		        }
			}
		}
		
		
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < length ; i++){
			int s = (int)(output[i] % table.length);
			builder.append(table[s]);
		}
		
		return builder.toString();
	}
}
