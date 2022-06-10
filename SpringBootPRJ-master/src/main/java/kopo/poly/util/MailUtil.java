package kopo.poly.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

@Slf4j
public class MailUtil {

    public static int sendAuthEmail(String email, String title, String body){

        log.info("메일 전송 시작");


        HtmlEmail email1 = new HtmlEmail();
        email1.setHostName("smtp.naver.com");
        email1.setSmtpPort(465);
        String pass = "ejkimk";
        String word = "9004";
        email1.setAuthentication("dodo0207k@naver.com", pass + word);

        email1.setSSLOnConnect(true);
        email1.setStartTLSEnabled(true);

        int res = 0;

        try{
            email1.setFrom("dodo0207k@naver.com", "ModuCalendar", "utf-8");
            email1.addTo(email, "이름", "utf-8");
            email1.setSubject(title);

            StringBuffer msg = new StringBuffer();

            msg.append("<a href='" + body + "'>Modu Calendar -resetPassword-</a>");


            email1.setHtmlMsg(msg.toString());
            email1.send();
            res = 1;
        } catch (EmailException e) {
            e.printStackTrace();
        }

        log.info("메일 전송 완료");
        return  res;
    }
}
