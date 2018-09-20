package com.ylt.wechat.service;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.ylt.wechat.utils.FileUtil;
import com.ylt.wechat.utils.JsonUtil;
import com.ylt.wechat.utils.MessageUtil;
import com.ylt.wechat.utils.OperatorUtil;
import com.ylt.wechat.utils.QRCodeUtil;

/**
 * ���ʹ���Ʋ���ҵ���߼�����뿪������Ҫ����������Ӧ
 * @author 
 * @date 2016.01.19
 */
public class YltService {
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		//Ĭ�Ϸ��ص��ı���Ϣ����
		String respContent = "ϵͳ���ϣ���ʱ�޷�����,	�ڴ����ǵķ�����";
		String fromUserName="";
		String toUserName ="";
		String msgType ="";
		try {
			//xml�������
			Map<String,String> requestMap = MessageUtil.pareXml(request);
			
			//���ͷ��˺ţ�open_id��
			fromUserName = requestMap.get("FromUserName");
			//�����˺�
			toUserName = requestMap.get("ToUserName");
			//��Ϣ����
			msgType = requestMap.get("MsgType");
			String fromContent=requestMap.get("Content");
			String userName="";
			System.out.println("fromUserName:"+fromUserName);
			System.out.println("toUserName:"+toUserName);
			System.out.println("msgType:"+msgType);
			System.out.println("fromContent:"+fromContent);
			
			//����
			String eventTypeSub = requestMap.get("Event");

			
			if((MessageUtil.EVENT_TYPE_SUBSCRIBE).equals(eventTypeSub)){
				respContent = "��ӭ����ע�ң��������ʲô�����ã�����ʱ����\n�ظ���������,����ϵͳ�˵�\n�ظ������ɶ�ά��+���ݡ��������ɶ�ά��\n";
				respContent+="�ظ���������+�ֻ����롱,���Һ����������Ϣ\n�ظ���(ͷ��,���,����,����,����,����,����,�Ƽ�,�ƾ�,ʱ��)+���š�,���������Ȥ������\n�ظ���IP��ѯ+IP��ַ��,����ָ����IP��ϸ��Ϣ\n";
				respContent+="����ʲô���ⶼ�������ң�";
				FileUtil.writeTxtToFile(new OperatorUtil().getUserInfo(fromUserName)+respContent);
			}
			//�ı���Ϣ
			else if(msgType.equals(MessageUtil.REQ_MESSSAGE_TYPE_TEXT)){
				if(fromContent.contains("�û�����")){
					userName=fromContent.substring(5).trim();
					respContent=new OperatorUtil().bindAccount(fromUserName,userName);
				}
				else if(fromContent.contains("�����")){
					userName=fromContent.substring(4).trim();
					if("oS-GywW5Aljk6V5v1JGDiUAOMdX0".equals(fromUserName)){
						respContent=new OperatorUtil().unBindAccount(userName);
					}else{
						respContent="�����߱�����ԱȨ��";
					}
					
				}
				else if(fromContent.contains("������")){
					String key = fromContent.replace("������", "");
					if(!StringUtils.isNumeric(key))
					{
						respContent = "��������ֻ����롱"+key+"����������\n �����ʽ��������+�ֻ����롰";
					}
					else
					{			
						respContent = new OperatorUtil().Guishudi(key);
						JSONObject obj = JSONObject.fromObject(respContent);
				    	JSONObject obj2 = obj.getJSONObject("showapi_res_body");
				    	String ret_code =obj2.getString("ret_code");
				    	if(ret_code.equals("0"))
				    	{ 
							respContent = "��ѯ�ɹ�����"+key+"���Ľ������\n";
							respContent += "����ʡ�ݣ�"+obj2.getString("prov")+"\n";
							respContent += "���ڳ��У�"+obj2.getString("city")+"\n";
							respContent += "�����ŶΣ�"+obj2.getString("num")+"\n";
							respContent += "�������ţ�"+obj2.getString("areaCode")+"\n";
							respContent += "��Ӫ������"+obj2.getString("name")+"\n";
							respContent += "�����ʱࣺ"+obj2.getString("postCode")+"\n";
							respContent += "�˵������֤�ſ�ͷ6λ��"+obj2.getString("provCode")+"\n";
				    	}
				    	else
				    		respContent ="��ѯʧ�ܣ�����ϵ����Ա��";
					}
				}
				else if("�г����".equals(fromContent)){
					respContent=new OperatorUtil().addTravel(fromUserName);
				}
				else if("�г��޸�".equals(fromContent)){
					respContent=new OperatorUtil().editTravel(fromUserName);
				}
				else if("����".equals(fromContent)){
					respContent="��ӭ����ע�ң��������ʲô�����ã�����ʱ����Ŷ\n�ظ���������,����ϵͳ�˵�\n�ظ������ɶ�ά��+���ݡ��������ɶ�ά��\n";
					respContent+="�ظ���������+�ֻ����롱,���Һ����������Ϣ\n�ظ���(ͷ��,���,����,����,����,����,����,�Ƽ�,�ƾ�,ʱ��)+���š�,���������Ȥ������\n�ظ���IP��ѯ+IP��ַ��,����ָ����IP��ϸ��Ϣ\n";
					respContent+="����ʲô���ⶼ�������ң�";
				}
				else if(fromContent.contains("����"))
				{
					String key = fromContent.replace("����", "").replace("��", "");
					if(key.contains("���"))
					{
						key = "shehui";
					}
					else if(key.contains("����"))
					{
						key = "guonei";
					}
					else if(key.contains("����"))
					{
						key = "guoji";
					}
					else if(key.contains("����"))
					{
						key = "yule";
					}
					else if(key.contains("����"))
					{
						key = "tiyu";
					}
					else if(key.contains("����"))
					{
						key = "junshi";
					}
					else if(key.contains("�Ƽ�"))
					{
						key = "keji";
					}
					else if(key.contains("�ƾ�"))
					{
						key = "caijing";
					}
	
					else if(key.contains("ʱ��"))
					{
						key = "shishang";
					}
					else
					{
						key = "top";
					}
					respContent=new OperatorUtil().Xinwentoutiao(key);
					JSONObject obj2= JsonUtil.jsonStringToList(respContent,"result");
					JSONArray res = obj2.getJSONArray("data");
					String strUniquekey="";
					String strTitle="";
					String strDate="";
					String strCategory="";
					String strAuthor_name="";
					String strUrl="";
					String strThumbnail_pic_s="";
					String strThumbnail_pic_s02="";
					String strThumbnail_pic_s03="";
					respContent="���ˣ�\n";
					String query="";
					for (int i = 0; i < res.size(); i++) 
					{
						strUniquekey= res.getJSONObject(i).containsKey("uniquekey")?res.getJSONObject(i).getString("uniquekey"):"";
						strTitle= res.getJSONObject(i).containsKey("title")?res.getJSONObject(i).getString("title"):"";
						strDate= res.getJSONObject(i).containsKey("date")?res.getJSONObject(i).getString("date"):"";
						strCategory= res.getJSONObject(i).containsKey("category")?res.getJSONObject(i).getString("category"):"";
						strAuthor_name= res.getJSONObject(i).containsKey("author_name")?res.getJSONObject(i).getString("author_name"):"";
						strUrl= res.getJSONObject(i).containsKey("url")?res.getJSONObject(i).getString("url"):"";
						strThumbnail_pic_s= res.getJSONObject(i).containsKey("thumbnail_pic_s")?res.getJSONObject(i).getString("thumbnail_pic_s"):"";
						strThumbnail_pic_s02= res.getJSONObject(i).containsKey("thumbnail_pic_s02")?res.getJSONObject(i).getString("thumbnail_pic_s02"):"";
						strThumbnail_pic_s03= res.getJSONObject(i).containsKey("thumbnail_pic_s03")?res.getJSONObject(i).getString("thumbnail_pic_s03"):"";
						query="key:"+strUniquekey+"\n";
						query+="����:"+strTitle+"\n";
						query+="����:"+strDate+"\n";
						query+="����:"+strCategory+"\n";
						query+="����:"+strAuthor_name+"\n";
						query+="����:"+strUrl+"\n";
						query+="����:"+strThumbnail_pic_s+"\n";
						query+="ͼһ:"+strThumbnail_pic_s02+"\n";
						query+="ͼ��:"+strThumbnail_pic_s03+"\n";
						System.out.println(i+query);
						respContent+=query;
						if(i>1)
							break;
					}
				}
				else if(fromContent.contains("IP��ѯ"))
				{
					String key = fromContent.replace("IP��ѯ", "").replace("��", "");
					
					respContent = "���ѯ�����ݣ�"+key;
				}
				else if(fromContent.contains("���ɶ�ά��"))
				{
					String key = fromContent.replace("���ɶ�ά��", "").replace("��", "");
					QRCodeUtil.encode(key, "C:/monster/1.png", "C:/inetpub/wwwroot/abc/image/"+fromUserName+".png", false,500);
					respMessage = getReplyPicMessage(fromUserName,toUserName,"https://yanglitong.com:8081/image/"+fromUserName+".png");
					return respMessage;
				}
				else if(fromContent.contains("�鿴��ά��"))
				{
					respMessage = getReplyPicMessage(fromUserName,toUserName,"https://yanglitong.com:8081/image/"+fromUserName+".png");
					return respMessage;
				}
				else
				{
					respContent = new OperatorUtil().ZhinengHuida(fromContent);
					JSONObject obj2= JsonUtil.jsonStringToList(respContent,"result");
					respContent = obj2.getString("content")+"\n";
					//if(fromUserName.equals("o2MZawvWMXXIUawV9KWQ0sz9ms2A"))
					//{
						respContent = "����:\n    "+respContent;
					//}
					String relquestion = obj2.getString("relquestion").trim();
					if(!relquestion.equals(""))
					{
						respContent=respContent+"Сi���ĸ�������"+obj2.getString("relquestion")+"\n";
					}
				}
			}
			//ͼƬ��Ϣ
			else if(msgType.equals(MessageUtil.REQ_MESSSAGE_TYPE_IMAGE)){
				respContent = "�����͵���ͼƬ��Ϣ��";
			}
			//����λ��
			else if(msgType.equals(MessageUtil.REQ_MESSSAGE_TYPE_LOCATION)){
				respContent = "�����͵��ǵ���λ����Ϣ��";
			}
			//������Ϣ
			else if(msgType.equals(MessageUtil.REQ_MESSSAGE_TYPE_LINK)){
				respContent = "�����͵���������Ϣ��";
			}
			//��Ƶ��Ϣ
			else if(msgType.equals(MessageUtil.REQ_MESSSAGE_TYPE_VOICE)){
				respContent = "�����͵�����Ƶ��Ϣ��";
			}
			//�¼�����
			else if(msgType.equals(MessageUtil.REQ_MESSSAGE_TYPE_EVENT)){
				//�¼�����
				String eventType = requestMap.get("Event");
				//����
				if(eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)){
					respContent += "лл��ע��";
				}
				//ȡ������
				else if(eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)){
					//
					System.out.println("ȡ������");
				}
				else if(eventType.equals(MessageUtil.EVENT_TYPE_CLICK)){
					//�Զ���˵���Ϣ����
					System.out.println("�Զ���˵���Ϣ����");
				}
			}
			respMessage=("<xml><ToUserName><![CDATA["+requestMap.get("FromUserName")+
					"]]></ToUserName>"+"<FromUserName><![CDATA["+requestMap.get("ToUserName")
					+"]]></FromUserName><CreateTime>"+System.currentTimeMillis()+"</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA["+respContent+"]]></Content></xml>");
		} catch (Exception e) {
			respMessage=("<xml><ToUserName><![CDATA["+fromUserName+
					"]]></ToUserName>"+"<FromUserName><![CDATA["+toUserName
					+"]]></FromUserName><CreateTime>"+System.currentTimeMillis()+"</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA["+"����:\n    ������"+"]]></Content></xml>");
		}
		return respMessage;
	}
	private static String getReplyPicMessage(String fromUserName,String toUserName,String path){
		ReplyPicMessage we = new ReplyPicMessage();        
        Articles articles = new Articles();
        Item item = new Item();
        we.setMessageType("news");
        we.setCreateTime(new Long(new Date().getTime()).toString());
        we.setToUserName(fromUserName);  
        we.setFromUserName(toUserName);
        we.setFuncFlag("0");
        we.setArticleCount(1);
        item.setTitle("��ά��");
        String respContent="";
        respContent = "���ˣ�"+respContent;
        item.setDescription(respContent + "���Ķ�ά��������\n����Ķ�ȫ�Ĳ鿴");
        item.setPicUrl("https://yanglitong.com:8081/image/14.png");
        item.setUrl(path);        
        articles.setItem(item);
        we.setArticles(articles);
        XStream xstream = new XStream(new DomDriver()); 
        xstream.alias("xml", ReplyPicMessage.class);
        xstream.aliasField("ToUserName", ReplyPicMessage.class, "toUserName");
        xstream.aliasField("FromUserName", ReplyPicMessage.class, "fromUserName");
        xstream.aliasField("CreateTime", ReplyPicMessage.class, "createTime");
        xstream.aliasField("MsgType", ReplyPicMessage.class, "messageType");
        xstream.aliasField("Articles", ReplyPicMessage.class, "Articles");
        xstream.aliasField("ArticleCount", ReplyPicMessage.class, "articleCount");
        xstream.aliasField("FuncFlag", ReplyPicMessage.class, "funcFlag");
        xstream.aliasField("item", Articles.class, "item");
        xstream.aliasField("Title", Item.class, "title");
        xstream.aliasField("Description", Item.class, "description");
        xstream.aliasField("PicUrl", Item.class, "picUrl");
        xstream.aliasField("Url", Item.class, "url");
        String xml =xstream.toXML(we);
        return xml;
	}
}

class Articles {
	private Item item;
    public Item getItem() {
        return item;
    }
    public void setItem(Item item) {
    	this.item = item;    
    }
}
class Item {
    private String title;
    private String description;
    private String picUrl;
    private String url;
    public String getTitle() {
       return title;
   }
   public void setTitle(String title) {
       this.title = title;
   }
   public String getDescription() {
       return description;
   }
   public void setDescription(String description) {
       this.description = description;
   }
   public String getPicUrl() {
       return picUrl;
   }
   public void setPicUrl(String picUrl) {
       this.picUrl = picUrl;
   }
   public String getUrl() {
       return url;
   }
   public void setUrl(String url) {
       this.url = url;
   }
}
class ReplyPicMessage {
    private String toUserName;
    private String fromUserName;
    private String createTime;
    private String messageType;
    private int articleCount;
    private Articles Articles;
    public String getToUserName() {
        return toUserName;
    }
    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }
    public String getFromUserName() {
        return fromUserName;
    }
    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getMessageType() {
        return messageType;
    }
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
    public int getArticleCount() {
        return articleCount;
    }
    public void setArticleCount(int articleCount) {
        this.articleCount = articleCount;
    }
    public Articles getArticles() {
        return Articles;
    }
    public void setArticles(Articles articles) {
        Articles = articles;
    }
    public String getFuncFlag() {
        return funcFlag;
    }
    public void setFuncFlag(String funcFlag) {
        this.funcFlag = funcFlag;
    }
    private String funcFlag;
}
class ReplyTextMessage {

    private String toUserName;
    private String fromUserName;
    private String createTime;
    private String messageType;
    private String content;
    public String getToUserName() {
       return toUserName;
   }
   public void setToUserName(String toUserName) {
       this.toUserName = toUserName;
   }
   public String getFromUserName() {
       return fromUserName;
   }
   public void setFromUserName(String fromUserName) {
       this.fromUserName = fromUserName;
   }
   public String getCreateTime() {
       return createTime;
   }
   public void setCreateTime(String createTime) {
       this.createTime = createTime;
   }
   public String getMessageType() {
       return messageType;
   }
   public void setMessageType(String messageType) {
       this.messageType = messageType;
   }
   public String getContent() {
       return content;
   }
   public void setContent(String content) {
       this.content = content;
   }
   public String getFuncFlag() {
       return funcFlag;
   }
   public void setFuncFlag(String funcFlag) {
       this.funcFlag = funcFlag;
   }
   private String funcFlag;
}