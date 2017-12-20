package com.PasswordManage.MD5;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	
	public static String getMD5(String str)throws NoSuchAlgorithmException, UnsupportedEncodingException{
		
		char hexDigits[] = {
	            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
	    };
		
		byte[] btInput=str.getBytes();
		//���MD5ժҪ�㷨�� MessageDigest����
		MessageDigest mdInst=MessageDigest.getInstance("MD5");
		//ʹ��ָ�����ֽڸ���ժҪ
		mdInst.update(btInput);
		//�������
        byte[] md=mdInst.digest();
        //������ת����ʮ�����Ƶ��ַ�����ʽ
        int j=md.length;
        char key[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            key[k++] = hexDigits[byte0 >>> 4 & 0xf];
            key[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(key);
	}
}
