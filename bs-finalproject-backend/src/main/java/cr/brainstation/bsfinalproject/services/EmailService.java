package cr.brainstation.bsfinalproject.services;

import org.apache.velocity.VelocityContext;

public interface EmailService {

   boolean sendEmail(String from, String to, String subject, String templateFilename, VelocityContext context);

}
