package com.ylt.wechat.utils;

import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ylt.wechat.message.resp.Article;
import com.ylt.wechat.message.resp.MusicMessage;
import com.ylt.wechat.message.resp.NewsMessage;
import com.ylt.wechat.message.resp.TextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * ������Ϣ�Ĵ����� 
 * @author pengsong
 * @date 2016.01.19
 */

public class MessageUtil {
	/**
	 * ������Ϣ���ͣ��ı�
	 */
	public static final String  RESP_MESSSAGE_TYPE_TEXT = "text";
	/**
	 * ������Ϣ���ͣ�����
	 */
	public static final String  RESP_MESSSAGE_TYPE_MUSIC = "music";
	/**
	 * ������Ϣ���ͣ�ͼ��
	 */
	public static final String  RESP_MESSSAGE_TYPE_NEWS = "news";
	/**
	 * ������Ϣ���ͣ��ı�
	 */
	public static final String  REQ_MESSSAGE_TYPE_TEXT = "text";
	/**
	 * ������Ϣ���ͣ�ͼƬ
	 */
	public static final String  REQ_MESSSAGE_TYPE_IMAGE = "image";
	/**
	 * ������Ϣ���ͣ�����
	 */
	public static final String  REQ_MESSSAGE_TYPE_LINK = "link";
	/**
	 * ������Ϣ���ͣ�����λ��
	 */
	public static final String  REQ_MESSSAGE_TYPE_LOCATION = "location";
	/**
	 * ������Ϣ���ͣ���Ƶ
	 */
	public static final String  REQ_MESSSAGE_TYPE_VOICE = "voice";
	/**
	 * ������Ϣ���ͣ�����
	 */
	public static final String  REQ_MESSSAGE_TYPE_EVENT = "event";
	/**
	 * �¼����ͣ�subscribe�����ģ�
	 */
	public static final String  EVENT_TYPE_SUBSCRIBE = "subscribe";
	/**
	 * �¼����ͣ�unsubscribe��ȡ�����ģ�
	 */
	public static final String  EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	/**
	 * �¼����ͣ�click���Զ���˵�����¼���
	 */
	public static final String  EVENT_TYPE_CLICK= "CLICK";
	
	/**
	 * �¼����ͣ�view���Զ���˵�����¼�,����url��
	 */
	public static final String  EVENT_TYPE_VIEW= "VIEW";
	/**
	 * ����΢�ŷ��������� XML 
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,String> pareXml(HttpServletRequest request) throws Exception {
		
		//�������Ľ���洢��HashMap��
		Map<String,String> reqMap = new HashMap<String, String>();
		
		//��request��ȡ��������
		InputStream inputStream = request.getInputStream();
		//��ȡ������
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		//�õ�xml��Ԫ��
		Element root = document.getRootElement();
		//�õ���Ԫ�ص������ӽڵ�
		List<Element> elementList = root.elements();
		//�������е��ӽڵ�ȡ����Ϣ����
		for(Element elem:elementList){
			reqMap.put(elem.getName(),elem.getText());
		}
		//�ͷ���Դ
		inputStream.close();
		inputStream = null;
		
		return reqMap;		
	}
	/**
	 * ��Ӧ��Ϣת����xml����
	 * �ı���Ϣ����ת����xml
	 */
	public  static String textMessageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	/**
	 * ������Ϣ�Ķ����ת����xml
	 * 
	 */
	public  static String musicMessageToXml(MusicMessage musicMessage) {
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}
	/**
	 * ͼ����Ϣ�Ķ���ת����xml
	 * 
	 */
	public  static String newsMessageToXml(NewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}
	/**
	 * ��չxstream��ʹ��֧��CDATA��
	 * 
	 */
	private static XStream xstream = new XStream(new XppDriver(){
		public HierarchicalStreamWriter createWriter(Writer out){
			return new PrettyPrintWriter(out){
				//�����е�xml�ڵ��ת��������CDATA���
				boolean cdata = true;
				
				@SuppressWarnings("unchecked")
				public void startNode(String name,Class clazz){
					super.startNode(name,clazz);
				}
				
				protected void writeText(QuickWriter writer,String text){
					if(cdata){
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					}else{
						writer.write(text);
					}
				}
			};
		}
	});
	
}

