package com.example.JuniorWebite.userService.userIMPL;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Data
@Service
public class EmailSenderService {


    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendEmail(String toEmail,String subject,String Body)
    {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("oussamalabiadh111@gmail.com");
        message.setTo(toEmail);
        message.setText(Body);
        message.setSubject(subject);
        mailSender.send(message);
    }
}
