package project.spring.helper;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * @fileName : MailHelper.java
 * @author : 홍준성
 * @description : 메일 발송 기능을 간결화 하기 위한 Helper 클래스
 * @lastUpdate : 2019-04-15
 */
public class MailHelper {
    /**
     * ===== root-context.xml에 의해서 주입되는 객체를 생성자로 받는다. =====
     */
    // -> import org.springframework.mail.javamail.JavaMailSender;
    JavaMailSender mailSender;
    
    public MailHelper(JavaMailSender mailSender) {
        super();
        this.mailSender = mailSender;
    }

    /** ===== 발송 기능을 구현할 메서드 정의 ===== */
    /**
     * 메일을 발송한다.
     * @param sender - 발송자 메일 주소
     * @param reveiver - 수신자 메일 주소
     * @param subject - 제목
     * @param content - 내용
     * @throws MessagingException
     */
    // -> import javax.mail.MessagingException;
    public void sendMail(String sender, String receiver, String subject, String content)
        throws MessagingException {
        /**
         * ===== 메일 발송 기능의 재구성 =====
         * - Spring AOP는 횡단관심사(메일서버 인증 정보 설정)을 자동으로 처리하여,
         *     개발자로 하여금 비즈니스로직(메일발송)에 집중할 수 있게 해 준다.
         */
        // -> import javax.mail.internet.MimeMessage;
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setSubject(subject);
        helper.setText(content, true);
        helper.setFrom(new InternetAddress(sender));
        helper.setTo(new InternetAddress(receiver));
        mailSender.send(message);
    } // End sendMail Method
}
