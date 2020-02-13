package rs.ac.uns.ftn.authentication_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.authentication_service.exceptions.BadRequestException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            message.setFrom("upp.2019.2020@gmail.com");

            emailSender.send(message);
        } catch (MailException exception) {
            throw new BadRequestException(exception.getMessage());
        }
    }
}
