package com.ylt.wechat.message.req;
/**
 * ������Ϣ
 * @author pengsong
 * @date 2016.01.16
 */
public class VoiceMessage extends BaseMessage{
	//ý��Id
	private String MediaId;
	//������ʽ
	private String Format;
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getFormat() {
		return Format;
	}
	public void setFormat(String format) {
		Format = format;
	}
	
}
