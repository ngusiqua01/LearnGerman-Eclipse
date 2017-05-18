package com.ufo.learngerman.utils;


import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public final class DecodeUtil
{
	public static final char[] KEY_GRAMMAR = { 49, 99, 99, 108, 117, 102, 72, 110, 121, 98, 74, 65, 119, 57, 77, 51, 84, 80, 122, 55, 73, 81, 61, 61 };
	public static final char[] KEY_CHAR_PHRASE =  new char[] { 'V', 'b', 'b', 'd', 'c', 'Z', 'J', 'l', 'E', '1', 'n', 'h', 'y', '3', 'A', 'Q', 'k', 'T', 'Y', '4', '3', 'w', '=', '=' };
	private Cipher mCipher = null;
	private SecretKey mSecretKey = null;
	public static String keynative = "abcdef123456ghijkl654321";
//	public static String keydecode_grammar = "fuckingyou";
	public static String KEY_STRING_PHRASE = "0okm9ijn";
  
  
 /* public DecodeUtil()
  {
    try
    {
    	System.out.println("DecodeUtil.DecodeUtil() key = "+new Native().getName());
    	keynative = new Native().getName();
      
      Decode2 mDecode2 = new Decode2(keynative);
      this.keycode_phrase = mDecode2.decode(String.valueOf(KEY_PHRASE));
      
      DESKeySpec localDESKeySpec = new DESKeySpec(keycode_phrase.getBytes("UTF8"));
      this.mSecretKey = SecretKeyFactory.getInstance("DES").generateSecret(localDESKeySpec);
      this.mCipher = Cipher.getInstance("DES");
      //return;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }*/
  
  public final String decode(String str)
  {
    byte [] array = Base64.decode(str, 0);
    try
    {
      this.mCipher.init(2, this.mSecretKey);
      String s = new String(this.mCipher.doFinal(array), "UTF8");
      return s;
    }
    catch (InvalidKeyException ie)
    {
      ie.printStackTrace();
      return null;
    }
    catch (IllegalBlockSizeException ibe)
    {
      ibe.printStackTrace();
      return null;
    }
    catch (BadPaddingException be)
    {
      be.printStackTrace();
      return null;
    }
    catch (UnsupportedEncodingException uee)
    {
      uee.printStackTrace();
    }
    return null;
  }
  /**
   * for LearnJapanese
   * */
  public static String decodeGrammar(String content){
	  try{
	      final byte[] bytes = "2bfb4874a00cb9c7".getBytes("UTF-8");
	      final byte[] decode = Base64.decode(content.getBytes(), 2);
	      final Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
	      instance.init(2, new SecretKeySpec(bytes, "AES"), new IvParameterSpec(new byte[16]));
	      return new String(instance.doFinal(decode));
      }catch(Exception e){
    	  System.out.println(e.toString());
    	  return null;
      }
  }
  
  /*
   * 
   * */
  
  public static String decodeFrance(String content){
	  try{
//		  Decode2 mDecode2 = new Decode2(keynative);
//		  keycode_phrase = mDecode2.decode(String.valueOf(KEY_PHRASE));
		  DESKeySpec localDESKeySpec = new DESKeySpec(KEY_STRING_PHRASE.getBytes("UTF8"));
		  SecretKey  mSecretKey = SecretKeyFactory.getInstance("DES").generateSecret(localDESKeySpec);
	      Cipher mCipher = Cipher.getInstance("DES");
	      byte [] array = Base64.decode(content, 0);
	      mCipher.init(2, mSecretKey);
	      String s = new String(mCipher.doFinal(array), "UTF8");
	      return s;
	  }catch (Exception e) {
			
			e.printStackTrace();
		}
	    return null;
	  
  }
  
}

