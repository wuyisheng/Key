package org.yeshen.key.secure;

import android.annotation.SuppressLint;
import android.util.Log;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;

import javax.crypto.Cipher;

public class RsaEncoder implements KeyerEncoder{
	
	public RsaEncoder(){}
	
	@Override
	public void refush(KeyerOption ops) {
		
	}
	
	@Override
	public String encode(String in) throws Exception {
		RSAPublicKey pubKey = getPublicKey(module, pubkey); 
        return encryptByPublicKey(in, pubKey);
	} 
	
	private final String pubkey = "65537";
	
	/*
	private final String prikey = 
			  "66344528384035416141202256116356340438269066093858"
			+ "27118553709335695410675140143345949779803533014413"
			+ "90555141889471252567385654974196439506851704776121"
			+ "37516365777887027965449267073160095886785210616139"
			+ "32797280229895843053972951628970836745841785578687"
			+ "55919398661116190658880690341096734236403059496658"
			+ "13033921";
	*/
	
	private final String module = 
			  "14491955326815748650621512045787572853724093539290"
			+ "03605868261336311269304458419406271075289084902061"
			+ "94089965450155909340761769389874052781257008218886"
			+ "99987113207680224409068136768951552250888155194733"
			+ "97968816243854617095334112189755389079086204370946"
			+ "43985202319068217619139398100423723022955742040486"
			+ "603642731";
	
	@SuppressWarnings("unused")
	private void Usage() throws Exception{
        HashMap<String, Object> map = getKeys();  
        RSAPublicKey publicKey = (RSAPublicKey) map.get("public");  
        RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");  
        
        String modulus = publicKey.getModulus().toString();  
        String public_exponent = publicKey.getPublicExponent().toString();  
        String private_exponent = privateKey.getPrivateExponent().toString();
        Log.e("public_exponent", public_exponent);
        Log.e("private_exponent", private_exponent);        

        String ming = "123456789";  
        RSAPublicKey pubKey = getPublicKey(modulus, public_exponent);  
        RSAPrivateKey priKey = getPrivateKey(modulus, private_exponent);  
        Log.e("ming", ming);
        String mi = encryptByPublicKey(ming, pubKey);
		ming = decryptByPrivateKey(mi, priKey); 
		Log.e("mi", mi);
	}
	
	
	/** 
	 * 
     * @throws NoSuchAlgorithmException  
     * 
     */  
    @SuppressLint("TrulyRandom")
    private HashMap<String, Object> getKeys() throws NoSuchAlgorithmException{  
        HashMap<String, Object> map = new HashMap<String, Object>();  
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");  
        keyPairGen.initialize(1024);  
        KeyPair keyPair = keyPairGen.generateKeyPair();  
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();  
        map.put("public", publicKey);  
        map.put("private", privateKey);  
        return map;  
    }
    
    /** 
     * RSA 
     * RSA/None/PKCS1Padding Android RSA 
     * /None/NoPadding
     * @param modulus 
     * @param exponent
     * @return 
     */ 
    private RSAPublicKey getPublicKey(String modulus, String exponent) {  
        try {  
            BigInteger b1 = new BigInteger(modulus);  
            BigInteger b2 = new BigInteger(exponent);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);  
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
  
    /** 
     * RSA
     * RSA/None/PKCS1Padding Android RSA 
     * /None/NoPadding
     * @param modulus 
     * @param exponent 
     * @return 
     */  
    private RSAPrivateKey getPrivateKey(String modulus, String exponent) {  
        try {  
            BigInteger b1 = new BigInteger(modulus);  
            BigInteger b2 = new BigInteger(exponent);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);  
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
  
    /** 
     * @param data 
     * @param publicKey 
     * @return 
     * @throws Exception 
     */  
    private String encryptByPublicKey(String data, RSAPublicKey publicKey)  
            throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int key_len = publicKey.getModulus().bitLength() / 8;
        String[] datas = splitString(data, key_len - 11);
        String mi = "";
        for (String s : datas) {
            mi += bcd2Str(cipher.doFinal(s.getBytes()));
        }
        return mi;
    }
  
    /** 
     * @param data 
     * @param privateKey 
     * @return 
     * @throws Exception 
     */  
    private String decryptByPrivateKey(String data, RSAPrivateKey privateKey)  
            throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        int key_len = privateKey.getModulus().bitLength() / 8;
        byte[] bytes = data.getBytes();
        byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
        String ming = "";
        byte[][] arrays = splitArray(bcd, key_len);
        for(byte[] arr : arrays){
            ming += new String(cipher.doFinal(arr));
        }
        return ming;  
    }  
    /** 
     * ASCII to BCD
     *  
     */  
    private byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
        byte[] bcd = new byte[asc_len / 2];
        int j = 0;
        for (int i = 0; i < (asc_len + 1) / 2; i++) {
            bcd[i] = asc_to_bcd(ascii[j++]);
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
        }
        return bcd;
    }
    
    private byte asc_to_bcd(byte asc) {  
        byte bcd;  
  
        if ((asc >= '0') && (asc <= '9'))  
            bcd = (byte) (asc - '0');  
        else if ((asc >= 'A') && (asc <= 'F'))  
            bcd = (byte) (asc - 'A' + 10);  
        else if ((asc >= 'a') && (asc <= 'f'))  
            bcd = (byte) (asc - 'a' + 10);  
        else  
            bcd = (byte) (asc - 48);  
        return bcd;  
    }
    
    private String bcd2Str(byte[] bytes) {  
        char temp[] = new char[bytes.length * 2], val;  
  
        for (int i = 0; i < bytes.length; i++) {  
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);  
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');  
  
            val = (char) (bytes[i] & 0x0f);  
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');  
        }  
        return new String(temp);  
    }
    
    private String[] splitString(String string, int len) {  
        int x = string.length() / len;  
        int y = string.length() % len;  
        int z = 0;  
        if (y != 0) {  
            z = 1;  
        }  
        String[] strings = new String[x + z];  
        String str = "";  
        for (int i=0; i<x+z; i++) {  
            if (i==x+z-1 && y!=0) {  
                str = string.substring(i*len, i*len+y);  
            }else{  
                str = string.substring(i*len, i*len+len);  
            }  
            strings[i] = str;  
        }  
        return strings;  
    }
    
    private byte[][] splitArray(byte[] data,int len){  
        int x = data.length / len;  
        int y = data.length % len;  
        int z = 0;  
        if(y!=0){  
            z = 1;  
        }  
        byte[][] arrays = new byte[x+z][];  
        byte[] arr;  
        for(int i=0; i<x+z; i++){  
            arr = new byte[len];  
            if(i==x+z-1 && y!=0){  
                System.arraycopy(data, i*len, arr, 0, y);  
            }else{  
                System.arraycopy(data, i*len, arr, 0, len);  
            }  
            arrays[i] = arr;  
        }  
        return arrays;  
    } 
}
