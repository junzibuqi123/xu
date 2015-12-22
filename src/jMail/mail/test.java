package jMail.mail;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class test {
    private static final String PASSWORD = "qrrieuemljmitxbl";

    private static final String USER_NAME = "junzibuqi123@163.com";
    // private static Properties getProp() throws IOException {
    // // 获得文件存储路径
    // ClassLoader classLoader = Thread.currentThread()
    // .getContextClassLoader();
    // InputStream inputStream = classLoader
    // .getResourceAsStream("xx.properties");
    // Properties prop = new Properties();
    // prop.load(inputStream);
    // return prop;
    // }

    public static void main(String arg[])
            throws UnsupportedEncodingException, MessagingException {
        sendMail();

    }

    public static void sendMail()
            throws MessagingException, UnsupportedEncodingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.163.com");
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props,
            new SimpleAuthenticator(USER_NAME, PASSWORD));

        Message message = new MimeMessage(session);
        message.setContent("Hello", "text/plain");// 非文本信息内容
        message.setText("Hello");// 纯文本信息内容
        message.setSubject("First");// 设置邮件主题
        message.setSentDate(new Date());// 设置邮件发送日期

        Address address = new InternetAddress("junzibuqi123@163.com", "mark"); // 带名字的邮件地址
        message.setFrom(address);// 设置发信人
        message.addRecipient(Message.RecipientType.TO, address);
        Transport.send(message);
    }

    public static class SimpleAuthenticator extends Authenticator {

        private String username;

        private String password;

        public SimpleAuthenticator(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(this.username, this.password);

        }

    }
}
