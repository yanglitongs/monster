package com.ylt.wechat.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ����У�鹤����
 * @author pengsong
 * @date 2016.01.19
 */
public class SignUtil implements Comparator{
	//��΢�������еĵ�Tokenһ��
	private static String token  = "ylt";
	public int compare(Object pFirst, Object pSecond) {
		return 0;
	}
	public static boolean checkSignature(String signature, String timestamp,
			String nonce) {
		String[] arra = new String[]{token,timestamp,nonce};
		//��signature,timestamp,nonce�����������ֵ�����
		Arrays.sort(arra);
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<arra.length;i++){
			sb.append(arra[i]);
		}
		MessageDigest md = null;
		String stnStr = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(sb.toString().getBytes());
			stnStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//�ͷ��ڴ�
		sb = null;
		//��sha1���ܺ���ַ�����signature�Աȣ���ʶ��������Դ��΢��
		return stnStr!=null?stnStr.equals(signature.toUpperCase()):false;
	}
	/**
	 * ���ֽ�����ת����ʮ�������ַ���
	 * @param digestArra
	 * @return
	 */
	private static String byteToStr(byte[] digestArra) {
		// TODO Auto-generated method stub
		String digestStr = "";
		for(int i=0;i<digestArra.length;i++){
			digestStr += byteToHexStr(digestArra[i]);
		}
		return digestStr;
	}
	/**
	 * ���ֽ�ת����Ϊʮ�������ַ���
	 */
	private static String byteToHexStr(byte dByte) {
		// TODO Auto-generated method stub
		char[] Digit = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		char[] tmpArr = new char[2];
		tmpArr[0] = Digit[(dByte>>>4)&0X0F];
		tmpArr[1] = Digit[dByte&0X0F];
		String s = new String(tmpArr);
		return s;
	}
	
	public static void main(String[] args) {
		/*byte dByte = 'A';
		System.out.println(byteToHexStr(dByte));*/
		Map<String,String> map = new ConcurrentHashMap<String, String>();
		map.put("4", "zhangsan");
		map.put("100", "lisi");
		Set set = map.keySet();
		Iterator iter = set.iterator();
		while(iter.hasNext()){
//			String keyV = (String) iter.next();
			String key =(String)iter.next();
			System.out.println(map.get(key));
//			System.out.println(map.get(iter.next()));
		}
		/*for(int i=0;i<map.size();i++){
			
		}*/
	}
}
