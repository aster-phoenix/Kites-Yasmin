package com.asterphoenix.kites.yasmin.helper;

import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import android.util.Log;

import com.asterphoenix.roxy.RoxyAsymmetric;
import com.asterphoenix.roxy.RoxySymmetric;

public class CryptoHelper {
	
	private static CryptoHelper instance;

	public static CryptoHelper getInstance() {
		if (null == instance) {
			instance = new CryptoHelper();
		}
		return instance;
	}

	private CryptoHelper() {
		try {
			rsa = new RoxyAsymmetric();
			keyPair = rsa.generateKeyPair();
			keyFactory = KeyFactory.getInstance("RSA");

			aes = new RoxySymmetric();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public String getPublicAsString() {
		return new String(Base64.encodeBase64(keyPair.getPublic().getEncoded()));
	}

	public void setPublicKeyFromString(String keyString) {
		try {
			byte[] keyBytes = keyString.getBytes("ISO-8859-1");
			X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
			remotePK = keyFactory.generatePublic(spec);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setSecretKeyFromString(String keyString) {
		byte[] keyBytes = null;
		try {
			keyBytes = keyString.getBytes("ISO-8859-1");
			sercetKey = rsa.unwrapSecretKey(keyBytes, keyPair.getPrivate());
		} catch (Exception e) {
			Log.i("zzzzzzzzzzzzz", "f");
			Log.i("zzzzzzzzzzzzz", e.getMessage());
		}
	}
	
	public void setIvFromString(String ivs) {
		try {
			iv = new IvParameterSpec(ivs.getBytes("ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			Log.i("zzzzzzzzzzzzz", "f");
			Log.i("zzzzzzzzzzzzz", e.getMessage());
		}
	}

	public String encodeRSAPublic(String msg) {
		return new String(rsa.encryptWithPublic(msg.getBytes(), remotePK));
	}

	public String encodeRSAPrivate(String msg) {
		return new String(rsa.encryptWithPrivate(msg.getBytes(), keyPair.getPrivate()));
	}

	public String decodeRSAPublic(String msg) {
		return new String(rsa.decryptWithPublic(msg.getBytes(), remotePK));
	}

	public String decodeRSAPrivate(String msg) {
		return new String(rsa.decryptWithPrivate(msg.getBytes(), keyPair.getPrivate()));
	}

	public String encodeAES(String msg) {
		try {
			return new String(aes.encrypt(msg, sercetKey, iv), "ISO-8859-1");
		} catch (Exception e) {
			Log.i("zzzzzzzzzzzzz", "f");
			Log.i("zzzzzzzzzzzzz", e.getMessage());
			return null;
		}
	}

	public String decodeAES(String msg) {
		try {
			return new String(aes.decrypt(msg.getBytes("ISO-8859-1"), sercetKey, iv));
		} catch (UnsupportedEncodingException e) {
			Log.i("zzzzzzzzzzzzz", "f");
			Log.i("zzzzzzzzzzzzz", e.getMessage());
			return null;
		}
	}

	private RoxyAsymmetric rsa;
	private KeyPair keyPair;
	private KeyFactory keyFactory;
	private PublicKey remotePK;

	private RoxySymmetric aes;
	private SecretKey sercetKey;
	private IvParameterSpec iv;
}
