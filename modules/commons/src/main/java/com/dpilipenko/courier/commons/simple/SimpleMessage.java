package com.dpilipenko.courier.commons.simple;

import java.io.IOException;
import java.io.ObjectInputStream;

import com.dpilipenko.courier.commons.IMessage;

public class SimpleMessage implements IMessage<String> {
	
	private static final long serialVersionUID = 88416998440217846L;
	
	private String body;
	private String subject;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	protected void validate() throws IllegalArgumentException {
		validateBody(body);
		validateSubject(subject);
	}
	
	protected void validateBody(String body) throws IllegalArgumentException {
		if (body == null) {
			throw new IllegalArgumentException("Body must be non-null. Empty string is acceptable.");
		}
	}
	
	protected void validateSubject(String subject) throws IllegalArgumentException {
		if (subject == null) {
			throw new IllegalArgumentException("Subject must be non-null. Empty subject is acceptable.");
		}
	}
	
	private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
		aInputStream.defaultReadObject();
		validate();
	}
}
