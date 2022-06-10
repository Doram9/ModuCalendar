package kopo.poly.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

@Slf4j
public class MailUtil {

    public int sendAuthEmail(String email, String auth, String url){

        log.info(this.getClass().getName()+ "메일 전송 시작");


        HtmlEmail email1 = new HtmlEmail();
        email1.setHostName("smtp.naver.com");
        //email1.setSmtpPort(465);
        email1.setSmtpPort(587);
        String pass = "ejkimk";
        String word = "9004";
        email1.setAuthentication("dodo0207k@naver.com", pass + word);

        email1.setSSLOnConnect(true);
        email1.setStartTLSEnabled(true);

        int res = 0;


        String subject = "제목";
        String text = "인증번호는 "+ auth+ "입니다.<br/>";

//        String url = "http://localhost:8443/EmailAuthProc";

        String code = "?auth="+auth+"&email="+email;
        try{
            email1.setFrom("wnnahd112@naver.com", "관리자?", "utf-8");
            email1.addTo(email, "이름", "utf-8");
            email1.setSubject("제목");

            StringBuffer msg = new StringBuffer();

            msg.append("<p>I'm FreeFund manager.</p>");
            msg.append("<p>you can certify</p>");
            msg.append("<a href='" + url + code + "'>plz click this link</a>");


            email1.setHtmlMsg(msg.toString());
            email1.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }

        log.info(this.getClass().getName()+ "메일 전송 완료");
        return  res;
    }
}
