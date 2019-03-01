package cr.brainstation.bsfinalproject.services.impl;

import cr.brainstation.bsfinalproject.services.EmailService;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;

@Service("emailService")
@Transactional
public class EmailServiceImpl implements EmailService {

    private JavaMailSender javaMailSender;
    private VelocityEngine velocityEngine;

    public EmailServiceImpl() {
    }

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
        this.velocityEngine = new VelocityEngine();
        this.velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        this.velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        this.velocityEngine.init();
    }

    @Override
    public boolean sendEmail(String from, String to, String subject, String templateFilename, VelocityContext context) {
        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = null;

        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);

            StringWriter stringWriter = new StringWriter();
            velocityEngine.mergeTemplate(templateFilename, "UTF-8", context, stringWriter);

            mimeMessageHelper.setText(stringWriter.toString(), true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        if (mimeMessageHelper != null) {
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
            return true;
        }

        return false;
    }
}
