package com.ylt.common;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;

import java.security.Security;
import java.util.*;

import javax.activation.*;

import java.util.Properties;

public class MailUtils {
	private String host = ""; // smtp������
    private String from = ""; // �����˵�ַ
    private String to = ""; // �ռ��˵�ַ
    private String affix = ""; // ������ַ
    private String affixName = ""; // ��������
    private String user = ""; // �û���
    private String pwd = ""; // ����
    private String subject = ""; // �ʼ�����
    private String content = "";//�ʼ�����

    public void setAddress(String from, String to, String subject) {
        this.from = from;
        this.to = to;
        this.subject = subject;
    }

    public void setAffix(String affix, String affixName) {
        this.affix = affix;
        this.affixName = affixName;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public void send(String host, String user, String pwd) {
    	Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
    	String SSL_FACTORY="javax.net.ssl.SSLSocketFactory";
        this.host = host;
        this.user = user;
        this.pwd = pwd;

        Properties props = new Properties();

        // ���÷����ʼ����ʼ������������ԣ�����ʹ�����׵�smtp��������
        props.put("mail.smtp.host", "my@21cn.com");
        props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        // ��Ҫ������Ȩ��Ҳ�����л����������У�飬��������ͨ����֤��һ��Ҫ����һ����
        props.put("mail.smtp.auth", "true");

        // �øո����úõ�props���󹹽�һ��session
        Session session = Session.getDefaultInstance(props);

        // ������������ڷ����ʼ��Ĺ�������console����ʾ������Ϣ��������ʹ
        // �ã�������ڿ���̨��console)�Ͽ��������ʼ��Ĺ��̣�
        session.setDebug(true);

        // ��sessionΪ����������Ϣ����
        MimeMessage message = new MimeMessage(session);
        try {
            // ���ط����˵�ַ
            message.setFrom(new InternetAddress(from));
            // �����ռ��˵�ַ
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // ���ر���
            message.setSubject(subject);

            // ��multipart����������ʼ��ĸ����������ݣ������ı����ݺ͸���
            Multipart multipart = new MimeMultipart();

            // �����ʼ����ı�����
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setText(content);
            multipart.addBodyPart(contentPart);
            // ��Ӹ���
            BodyPart messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(affix);
            // ��Ӹ���������
            messageBodyPart.setDataHandler(new DataHandler(source));
            // ��Ӹ����ı���
            // �������Ҫ��ͨ�������Base64�����ת�����Ա�֤������ĸ����������ڷ���ʱ����������
            sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
            messageBodyPart.setFileName("=?GBK?B?"
                    + enc.encode(affixName.getBytes()) + "?=");
            if(affix != null && affix != "" && affixName != null && affixName != "")
            	multipart.addBodyPart(messageBodyPart);

            // ��multipart����ŵ�message��
            message.setContent(multipart);
            // �����ʼ�
            message.saveChanges();
            // �����ʼ�
            Transport transport = session.getTransport("smtp");
            // ���ӷ�����������
            transport.connect(host, user, pwd);
            // ���ʼ����ͳ�ȥ
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
