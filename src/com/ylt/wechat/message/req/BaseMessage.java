package com.ylt.wechat.message.req;
/**
 * ��Ϣ���� ����ͨ�û�-���ںţ�
 * @author pengsong
 * @date 2015.01.19
 */
public class BaseMessage {
	
	//������΢�ź�
	private String ToUserName;
	//���ͷ��˺ţ�һ��openId��
	private String FromUserName;
	//��Ϣ����ʱ�䣨���ͣ�
	private long CreateTime;
	//��Ϣ���ͣ�text/image/location/link...��
	private String MsgType;
	//��Ϣid 64λ����
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
