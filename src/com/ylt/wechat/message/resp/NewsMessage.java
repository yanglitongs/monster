package com.ylt.wechat.message.resp;

import java.util.List;

/**
 * ��Ӧ�е�ͼ����Ϣ
 * @author pengsong
 * @2016.01.19
 */
public class NewsMessage extends BaseMessage{
	//ͼ����Ϣ�ĸ���������Ϊ10������
	private int ArticleCount;
	//����ͼ����Ϣ��Ϣ��Ĭ�ϵ�һ��itemΪ��ͼ
	private List<Article> Article;
	public int getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}
	public List<Article> getArticle() {
		return Article;
	}
	public void setArticle(List<Article> article) {
		Article = article;
	}
}
