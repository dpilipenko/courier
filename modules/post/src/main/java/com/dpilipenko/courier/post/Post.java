package com.dpilipenko.courier.post;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message.RecipientType;

import com.dpilipenko.courier.post.util.Conf;
import com.dpilipenko.courier.post.util.MessageNoMessageID;
import com.dpilipenko.courier.commons.IEnvelope;
import com.dpilipenko.courier.commons.IMessage;
import com.dpilipenko.courier.commons.simple.SimpleEnvelope;
import com.dpilipenko.courier.commons.simple.SimpleMessage;

public class Post {
	
	public void email(String to, String subject, String body) throws MessagingException {
		IEnvelope<String> envelope = createEnvelope(to, subject, body);
		email(envelope);
	}
	
	public void email(Conf conf, String to, String subject, String body) throws MessagingException {
		IEnvelope<String> envelope = createEnvelope(to, subject, body);
		sendMessage(envelope, conf);
	}
	
	public void email(IEnvelope<?> envelope) throws MessagingException {
		Conf conf = Conf.fetchInstance();
		sendMessage(envelope, conf);
	}
	
	protected void sendMessage(IEnvelope<?> envelope, Conf conf) {
		final String to = envelope.getToAddress();
		final IMessage<?> message = envelope.getMessage();
		try {
			Session session = getSession(conf);
			MimeMessage mimeMessage = new MessageNoMessageID(session);
			mimeMessage.setFrom(new InternetAddress(conf.getFrom()));
			mimeMessage.addRecipient(RecipientType.TO, new InternetAddress(to)); // TODO add earlier validation to make sure to is a valid input (i.e. no whitespace)
			mimeMessage.setSubject(message.getSubject());
			mimeMessage.setText((String)message.getBody());
			Transport.send(mimeMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected Session getSession(final Conf c) {
		Properties properties = System.getProperties();
		properties.put("mail.transport.protocol", c.getProtocol());
		properties.setProperty("mail.smtp.host", c.getHost());
		properties.setProperty("mail.smtp.port", c.getPort());
		properties.setProperty("mail.smtp.starttls.enable", "true");
		Session session;
		if ("true".equalsIgnoreCase(c.getAuth())) {
			properties.setProperty("mail.smtp.auth", c.getAuth());
			Authenticator authenticator = new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
			        return new PasswordAuthentication(c.getUsername(), c.getPassword());
			    }
			};
			session = Session.getDefaultInstance(properties, authenticator);
		} else {
			session = Session.getDefaultInstance(properties);
		}
		return session;
	}
	
	private IEnvelope<String> createEnvelope(String to, String subject, String body) {
		SimpleMessage m = new SimpleMessage();
		m.setSubject(subject);
		m.setBody(body);
		SimpleEnvelope e = new SimpleEnvelope();
		e.setMessage(m);
		e.setToAddress(to);
		return e;
	}
}