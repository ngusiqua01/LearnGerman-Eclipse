package com.ufo.learngerman.utils;


import android.util.Base64;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

public final class Decode2
{
  byte[] a;
  SecretKey b;
  private Cipher mCipher;
  private KeySpec mKeySpec;
  private String e;
  private String f;
  private SecretKeyFactory g;
  
 
  public Decode2(String paramString)
    throws Exception
  {
    this.e = paramString;
    this.f = "DESede";
    this.a = this.e.getBytes("UTF8");
    this.mKeySpec = new DESedeKeySpec(this.a);
    this.g = SecretKeyFactory.getInstance(this.f);
    this.mCipher = Cipher.getInstance(this.f);
    this.b = this.g.generateSecret(this.mKeySpec);
  }
  
  public final String decode(String str)
  {
    try
    {
      this.mCipher.init(2, this.b);
      byte [] temp = Base64.decode(str, 0);
      String outstr = new String(this.mCipher.doFinal(temp));
//      System.out.println("Decode2.decode() "+outstr);
      return outstr;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
}
