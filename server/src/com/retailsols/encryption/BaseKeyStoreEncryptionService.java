/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.retailsols.encryption;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.UUID;
import org.apache.commons.codec.binary.Base64;


public class BaseKeyStoreEncryptionService
  
{
 
  protected String providerName;
  protected String hashAlgorithmName = "SHA-256";
  public static final String PASSWORD_CHARSET = "UTF-8";

  public byte[] hash(byte[] clearText)
    throws Exception
  {
    byte[] hash=null;
    try
    {
      MessageDigest digest = MessageDigest.getInstance(getHashAlgorithmName());
      //assert (digest != null) : ("No such digest algorithm: " + getHashAlgorithmName());
      hash = digest.digest(clearText);
     
    }
    catch (Exception ex)
    {
     ex.printStackTrace();
    }
       return hash;
  }

  public byte[] superHash(byte[] value, String salt, boolean valueIsHash)
    throws Exception
  {
    String plainTextFirstHash = null;

    if (!valueIsHash)
    {
      plainTextFirstHash = getBase64encode(hash(value));
    }
    else
    {
      plainTextFirstHash = getUTF8Text(value);
    }

    String finalHash = plainTextFirstHash;
    for (int i = 0; i < 545; i++)
    {
      byte[] pwdSaltBytes = getUTF8Bytes(salt + finalHash);

      finalHash = getBase64encode(hash(pwdSaltBytes));
    }

    return hash(getUTF8Bytes(finalHash));
  }

  public String getRandomUniqueID()
    throws Exception
  {
    UUID uuid = UUID.randomUUID();
    return uuid.toString();
  }

  public byte[] getUTF8Bytes(String text)
    throws Exception
  {
    byte[] textByteArray = null;
    try
    {
      textByteArray = text.getBytes("UTF-8");
    }
    catch (UnsupportedEncodingException e)
    {
      textByteArray = text.getBytes();
    }

    return textByteArray;
  }

  public String getUTF8Text(byte[] textByteArray)
    throws Exception
  {
    String password = null;
    try
    {
      password = new String(textByteArray, "UTF-8");
    }
    catch (UnsupportedEncodingException e)
    {
      password = new String(textByteArray);
    }

    return password; 
  }

  public String getBase64encode(byte[] clearText)
    throws Exception
  {
    return new String(Base64.encodeBase64(clearText));
  }

  public byte[] getBase64decode(String encodedText)
    throws Exception
  {
    return Base64.decodeBase64(getUTF8Bytes(encodedText));
  }

  public String getHashAlgorithmName()
  {
    return this.hashAlgorithmName;
  }

  public void setHashAlgorithmName(String algorithmName)
  {
    this.hashAlgorithmName = algorithmName;
  }

  public String getProviderName()
  {
    return this.providerName;
  }

  public void setProviderName(String providerName)
  {
    this.providerName = providerName;
  }

  
}