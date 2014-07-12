package it.open.androidlab.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.security.Key;
import java.security.KeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import android.content.Context;
import android.util.Base64;

/**
 * 
 * @author c.luchessa
 *
 */
public final class CryptoUtils {
	private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
	private static final String ALGORITHM = "AES";

	public static final byte[] encrypt(String data, String password) {
		return encrypt(data.getBytes(), password.getBytes());
	}

	public static final String decrypt(byte[] data, String password) {
		return new String(decrypt(data, password.getBytes()));
	}

	public static final byte[] encrypt(byte[] data, byte[] password) {
		try {
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);

			Key key = new SecretKeySpec(password, 0, password.length, ALGORITHM);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			dos.writeInt(data.length);
			dos.write(data);

			cipher.init(Cipher.ENCRYPT_MODE, key);

			return cipher.doFinal(baos.toByteArray());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static final byte[] decrypt(byte[] encryptedData, byte[] password) {
		try {
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);

			Key key = new SecretKeySpec(password, 0, password.length, ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);

			ByteArrayInputStream bais = new ByteArrayInputStream(cipher.doFinal(encryptedData));
			DataInputStream dis = new DataInputStream(bais);
			int length = dis.readInt();
			byte[] data = new byte[length];
			dis.read(data, 0, length);

			return data;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * Fixed in 1.2
	 */
	public static final byte[] encrypt(String text,Context context) {
		return encrypt(text.getBytes(), Utils.md5(Utils.getDeviceID(context).getBytes()));
	}

	/*
	 * Fixed in 1.2
	 */
	public static final String decrypt(byte[] data,Context context) {
		return new String(decrypt(data, Utils.md5(Utils.getDeviceID(context).getBytes())));
	}
	
	/*
	 * @since CR 5.0
	 * */
	
	/*vodid="+channelId+"&rnd="+rnd+"&time="+time+�user_token="+user_token+�&streamstatus=�+streamstatus+�&network=�+network+�*/
	public static boolean verifyAliveParamsHashingLive(String channelId, String randomNum, String userToken, String streamStatus, String network, String hash, String vodId, String time) {
		boolean verified = false;
//		Utils.hardLong("channel id " + channelId + " rnd " + randomNum + " user_token " + userToken + " streamstatus " + streamStatus + " network " + network);
		String value = null;
		
		if(channelId != null) {
			value = "channelid=" + channelId + "&rnd=" + randomNum + "&user_token=" + userToken + "&streamstatus=" + streamStatus + "&network=" + network;
		} else {
			value = "vodid="+vodId+"&rnd="+randomNum+"&time="+time+"&user_token="+userToken+"&streamstatus="+streamStatus+"&network="+network;
		}
		String urlEncodedValue = value/*URLEncoder.encode(value, "UTF-8")*/;
//		Utils.hardLong("URL encoded string " + urlEncodedValue);
		
		MessageDigest cript;
		try {
			cript = MessageDigest.getInstance("SHA-1");
	        cript.reset();
	        cript.update(urlEncodedValue.getBytes());
	        
	        String hashedValue = new String(Utils.byteArrayToHexString(cript.digest())).toLowerCase();
	        String responseHash = hash.toLowerCase();
//	        Utils.hardLong("hashed value " + hashedValue);
//	        Utils.hardLong("alive response hash " + hash);
	        
	        verified = (responseHash).equals(hashedValue);
	        
		} catch (NoSuchAlgorithmException e) {
			Utils.printStackTrace(e);
		} 
		return verified;
	}
	
	public static boolean verifyAliveSignature(String data, String signatureBase64) {
		
//		Utils.hardLong("verify signature...");
		
//		Utils.hardLong("result object:\n " + data);
		
//		Utils.hardLong("signature:\n " + signatureBase64);
		
		boolean verified = false;
		
		String publicKeyBase64 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC73fuEDxg6UZsxxx8Ep5q2qeAJ RO4fcE5GkChw2612IsCvLhAdTq0BFF5NdPkhJSt4NKPc+H2T8sAvmW6x9WhQTb8T kyyyqXOqi31xWS/ncXBVRCFBHDoWTyU+oJoO6UvGLoIqFzZ5EPUyGnTLB8MN+z4/ KVm93CcAA3vqudAqIwIDAQAB";	
		PublicKey publicKey = null;
		try {
			byte[] publicKeyBytes = Base64.decode(publicKeyBase64, Base64.DEFAULT);
			byte[] signature = Base64.decode(signatureBase64, Base64.DEFAULT);
			publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyBytes));
			
			Signature sig = Signature.getInstance("SHA1withRSA");
			sig.initVerify(publicKey);
			sig.update(data.getBytes());
			
			verified = sig.verify(signature);
			
		} catch (InvalidKeySpecException e) {
			Utils.printStackTrace(e);
		} catch (NoSuchAlgorithmException e) {
			Utils.printStackTrace(e);
		} catch (SignatureException e) {
			Utils.printStackTrace(e);
		} catch (KeyException e) {
			Utils.printStackTrace(e);
		}
		return verified;
	}
	
	public static String getSHA1Value(String data) {
		String hash = "";
		
		MessageDigest cript;
		try {
			cript = MessageDigest.getInstance("SHA-1");
	        cript.reset();
	        cript.update(data.getBytes());
	        
	        hash = new String(Utils.byteArrayToHexString(cript.digest())).toLowerCase();
	        
		} catch (NoSuchAlgorithmException e) {
			Utils.printStackTrace(e);
		} 
		
		return hash;
	}
}
