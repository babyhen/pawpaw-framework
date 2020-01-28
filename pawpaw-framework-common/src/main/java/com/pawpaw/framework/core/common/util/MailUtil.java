package com.pawpaw.framework.core.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;


public class MailUtil {

    public static final String CHARSET = "UTF-8";

    private static final  Logger logger = LoggerFactory.getLogger(MailUtil.class);


    public static void sendMailBySmtp(String smtpHost, int smtpPort, String subject, String content, String from,
                                      String pwd, String fromName, Collection<String> to, Collection<String> cc, Collection<String> bcc) throws Exception {
        sendMailBySmtp(smtpHost, smtpPort, subject, content, "text/plain;charset=UTF-8", from, pwd, fromName, to, cc, bcc);
    }


    public static void sendMailBySmtp(String smtpHost, int smtpPort, String subject, String content, String mime, String from,
                                      String pwd, String fromName, Collection<String> to, Collection<String> cc, Collection<String> bcc)
            throws Exception {
        AssertUtil.notBlank(smtpHost, "smtp主机不能为空");
        logger.debug(content);

        Properties props = new Properties(); // 参数配置
        props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", smtpHost); // 发件人的邮箱的 SMTP
        // 需要请求认证
        if (pwd == null) {
            props.setProperty("mail.smtp.auth", "false");
        } else {
            props.setProperty("mail.smtp.auth", "true");
        }
        props.setProperty("mail.smtp.port", String.valueOf(smtpPort));

        Session session = Session.getDefaultInstance(props);
        // 设置为debug模式, 可以查看详细的发送 log
        session.setDebug(true);
        // 创建一封邮件
        MimeMessage message = createMail(session, subject, content, mime, from, fromName, to, cc, bcc);
        // 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();
        if (pwd == null) {
            transport.connect();
        } else {
            transport.connect(from, pwd);
        }

        // 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人,
        // 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());
        // 关闭连接
        transport.close();
    }

    public static MimeMessage createMail(Session session, String subject, String content, String mime, String from, String fromName,
                                         Collection<String> to, Collection<String> cc, Collection<String> bcc) throws Exception {

        AssertUtil.notBlank(from, "发件人不能为空");
        AssertUtil.notEmpty(to, "收件人不能为空");
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from, fromName, CHARSET));
        for (String t : to) {
            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(t, t, CHARSET));
        }
        if (cc != null) {
            for (String c : cc) {
                message.addRecipient(MimeMessage.RecipientType.CC, new InternetAddress(c, c, CHARSET));
            }

        }
        if (bcc != null) {
            for (String bc : bcc) {
                message.addRecipient(MimeMessage.RecipientType.BCC, new InternetAddress(bc, bc, CHARSET));
            }

        }
        message.setSubject(subject, CHARSET);
        message.setContent(content, mime);
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }


}
