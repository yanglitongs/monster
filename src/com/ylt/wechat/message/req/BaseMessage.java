package com.ylt.wechat.message.req;
/**
 * 消息基类 （普通用户-公众号）
 * @author pengsong
 * @date 2015.01.19
 */
public class BaseMessage {
	
	//开发者微信号
	private String ToUserName;
	//发送方账号（一个openId）
	private String FromUserName;
	//消息创建时间（整型）
	private long CreateTime;
	//消息类型（text/image/location/link...）
	private String MsgType;
	//消息id 64位整型
	private String MsgId;
	
	public BaseMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public BaseMessage(String toUserName, String fromUserName, long createTime,
			String msgType, String msgId) {
		super();
		ToUserName = toUserName;
		FromUserName = fromUserName;
		CreateTime = createTime;
		MsgType = msgType;
		MsgId = msgId;
	}
	
	public String getToUserName() {
		return ToUserName;
	}
	
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	
	public String getFromUserName() {
		return FromUserName;
	}
	
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public String getMsgId() {
		return MsgId;
	}
	
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
}
