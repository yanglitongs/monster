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
 * 解耦，使控制层与业务逻辑层分离开来，主要处理请求，响应
 * @author 
 * @date 2016.01.19
 */
public class YltService {
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		//默认返回的文本消息类容
		String respContent = "系统故障，暂时无法服务,	期待你们的反馈！";
		String fromUserName="";
		String toUserName ="";
		String msgType ="";
		try {
			//xml请求解析
			Map<String,String> requestMap = MessageUtil.pareXml(request);
			
			//发送方账号（open_id）
			fromUserName = requestMap.get("FromUserName");
			//公众账号
			toUserName = requestMap.get("ToUserName");
			//消息类型
			msgType = requestMap.get("MsgType");
			String fromContent=requestMap.get("Content");
			String userName="";
			System.out.println("fromUserName:"+fromUserName);
			System.out.println("toUserName:"+toUserName);
			System.out.println("msgType:"+msgType);
			System.out.println("fromContent:"+fromContent);
			
			//订阅
			String eventTypeSub = requestMap.get("Event");

			
			if((MessageUtil.EVENT_TYPE_SUBSCRIBE).equals(eventTypeSub)){
				respContent = "欢迎您关注我，如果您有什么不懂得，请随时问我\n回复“帮助”,呼出系统菜单\n回复“生成二维码+内容”，将生成二维码\n";
				respContent+="回复“归属地+手机号码”,查找号码归属地信息\n回复“(头条,社会,国内,国际,娱乐,体育,军事,科技,财经,时尚)+新闻”,推送你感兴趣的新闻\n回复“IP查询+IP地址”,查找指定的IP详细信息\n";
				respContent+="若有什么问题都可以问我！";
				FileUtil.writeTxtToFile(new OperatorUtil().getUserInfo(fromUserName)+respContent);
			}
			//文本消息
			else if(msgType.equals(MessageUtil.REQ_MESSSAGE_TYPE_TEXT)){
				if(fromContent.contains("用户名绑定")){
					userName=fromContent.substring(5).trim();
					respContent=new OperatorUtil().bindAccount(fromUserName,userName);
				}
				else if(fromContent.contains("解除绑定")){
					userName=fromContent.substring(4).trim();
					if("oS-GywW5Aljk6V5v1JGDiUAOMdX0".equals(fromUserName)){
						respContent=new OperatorUtil().unBindAccount(userName);
					}else{
						respContent="您不具备管理员权限";
					}
					
				}
				else if(fromContent.contains("归属地")){
					String key = fromContent.replace("归属地", "");
					if(!StringUtils.isNumeric(key))
					{
						respContent = "您输入的手机号码”"+key+"“不是数字\n 输入格式”归属地+手机号码“";
					}
					else
					{			
						respContent = new OperatorUtil().Guishudi(key);
						JSONObject obj = JSONObject.fromObject(respContent);
				    	JSONObject obj2 = obj.getJSONObject("showapi_res_body");
				    	String ret_code =obj2.getString("ret_code");
				    	if(ret_code.equals("0"))
				    	{ 
							respContent = "查询成功！“"+key+"”的结果如下\n";
							respContent += "所在省份："+obj2.getString("prov")+"\n";
							respContent += "所在城市："+obj2.getString("city")+"\n";
							respContent += "所属号段："+obj2.getString("num")+"\n";
							respContent += "所在区号："+obj2.getString("areaCode")+"\n";
							respContent += "运营商名："+obj2.getString("name")+"\n";
							respContent += "地区邮编："+obj2.getString("postCode")+"\n";
							respContent += "此地区身份证号开头6位："+obj2.getString("provCode")+"\n";
				    	}
				    	else
				    		respContent ="查询失败！请联系管理员。";
					}
				}
				else if("行程添加".equals(fromContent)){
					respContent=new OperatorUtil().addTravel(fromUserName);
				}
				else if("行程修改".equals(fromContent)){
					respContent=new OperatorUtil().editTravel(fromUserName);
				}
				else if("帮助".equals(fromContent)){
					respContent="欢迎您关注我，如果您有什么不懂得，请随时问我哦\n回复“帮助”,呼出系统菜单\n回复“生成二维码+内容”，将生成二维码\n";
					respContent+="回复“归属地+手机号码”,查找号码归属地信息\n回复“(头条,社会,国内,国际,娱乐,体育,军事,科技,财经,时尚)+新闻”,推送你感兴趣的新闻\n回复“IP查询+IP地址”,查找指定的IP详细信息\n";
					respContent+="若有什么问题都可以问我！";
				}
				else if(fromContent.contains("新闻"))
				{
					String key = fromContent.replace("新闻", "").replace("：", "");
					if(key.contains("社会"))
					{
						key = "shehui";
					}
					else if(key.contains("国内"))
					{
						key = "guonei";
					}
					else if(key.contains("国际"))
					{
						key = "guoji";
					}
					else if(key.contains("娱乐"))
					{
						key = "yule";
					}
					else if(key.contains("体育"))
					{
						key = "tiyu";
					}
					else if(key.contains("军事"))
					{
						key = "junshi";
					}
					else if(key.contains("科技"))
					{
						key = "keji";
					}
					else if(key.contains("财经"))
					{
						key = "caijing";
					}
	
					else if(key.contains("时尚"))
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
					respContent="主人：\n";
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
						query+="标题:"+strTitle+"\n";
						query+="日期:"+strDate+"\n";
						query+="分类:"+strCategory+"\n";
						query+="作者:"+strAuthor_name+"\n";
						query+="链接:"+strUrl+"\n";
						query+="封面:"+strThumbnail_pic_s+"\n";
						query+="图一:"+strThumbnail_pic_s02+"\n";
						query+="图二:"+strThumbnail_pic_s03+"\n";
						System.out.println(i+query);
						respContent+=query;
						if(i>1)
							break;
					}
				}
				else if(fromContent.contains("IP查询"))
				{
					String key = fromContent.replace("IP查询", "").replace("：", "");
					
					respContent = "你查询的内容："+key;
				}
				else if(fromContent.contains("生成二维码"))
				{
					String key = fromContent.replace("生成二维码", "").replace("：", "");
					QRCodeUtil.encode(key, "C:/monster/1.png", "C:/inetpub/wwwroot/abc/image/"+fromUserName+".png", false,500);
					respMessage = getReplyPicMessage(fromUserName,toUserName,"https://yanglitong.com:8081/image/"+fromUserName+".png");
					return respMessage;
				}
				else if(fromContent.contains("查看二维码"))
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
						respContent = "主人:\n    "+respContent;
					//}
					String relquestion = obj2.getString("relquestion").trim();
					if(!relquestion.equals(""))
					{
						respContent=respContent+"小i悄悄告诉您："+obj2.getString("relquestion")+"\n";
					}
				}
			}
			//图片消息
			else if(msgType.equals(MessageUtil.REQ_MESSSAGE_TYPE_IMAGE)){
				respContent = "您发送的是图片消息！";
			}
			//地理位置
			else if(msgType.equals(MessageUtil.REQ_MESSSAGE_TYPE_LOCATION)){
				respContent = "您发送的是地理位置消息！";
			}
			//链接消息
			else if(msgType.equals(MessageUtil.REQ_MESSSAGE_TYPE_LINK)){
				respContent = "您发送的是链接消息！";
			}
			//音频消息
			else if(msgType.equals(MessageUtil.REQ_MESSSAGE_TYPE_VOICE)){
				respContent = "您发送的是音频消息！";
			}
			//事件推送
			else if(msgType.equals(MessageUtil.REQ_MESSSAGE_TYPE_EVENT)){
				//事件类型
				String eventType = requestMap.get("Event");
				//订阅
				if(eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)){
					respContent += "谢谢关注！";
				}
				//取消订阅
				else if(eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)){
					//
					System.out.println("取消订阅");
				}
				else if(eventType.equals(MessageUtil.EVENT_TYPE_CLICK)){
					//自定义菜单消息处理
					System.out.println("自定义菜单消息处理");
				}
			}
			respMessage=("<xml><ToUserName><![CDATA["+requestMap.get("FromUserName")+
					"]]></ToUserName>"+"<FromUserName><![CDATA["+requestMap.get("ToUserName")
					+"]]></FromUserName><CreateTime>"+System.currentTimeMillis()+"</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA["+respContent+"]]></Content></xml>");
		} catch (Exception e) {
			respMessage=("<xml><ToUserName><![CDATA["+fromUserName+
					"]]></ToUserName>"+"<FromUserName><![CDATA["+toUserName
					+"]]></FromUserName><CreateTime>"+System.currentTimeMillis()+"</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA["+"主人:\n    报错啦"+"]]></Content></xml>");
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
        item.setTitle("二维码");
        String respContent="";
        respContent = "主人；"+respContent;
        item.setDescription(respContent + "您的二维码生成啦\n点击阅读全文查看");
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