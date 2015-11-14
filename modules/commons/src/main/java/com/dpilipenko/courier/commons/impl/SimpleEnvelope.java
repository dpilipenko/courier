package com.dpilipenko.courier.commons.impl;

import java.io.IOException;
import java.io.ObjectInputStream;

import com.dpilipenko.courier.commons.IEnvelope;
import com.dpilipenko.courier.commons.IMessage;

public class SimpleEnvelope implements IEnvelope<String> {

	private static final long serialVersionUID = 6720248800270235361L;

	private String fromAddress;
	private String toAddress;
	private SimpleMessage message;
	
	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public IMessage<String> getMessage() {
		return (IMessage<String>)message;
	}

	public void setMessage(IMessage<String> message) {
		this.message = (SimpleMessage)message;
	}
	
	protected void validate() throws IllegalArgumentException {
		validateFromAddress(fromAddress);
		validateToAddress(toAddress);
		validateMessage(message);
	}

	private void validateFromAddress(String fromAddress) throws IllegalArgumentException {
		if (fromAddress == null || fromAddress.equals("")) {
			throw new IllegalArgumentException("FromAddress must be non-null and non-empty.");
		}
	}

	private void validateToAddress(String toAddress) throws IllegalArgumentException {
		if (toAddress == null || toAddress.equals("")) {
			throw new IllegalArgumentException("ToAddress must be non-null and non-empty.");
		}
	}

	private void validateMessage(SimpleMessage message) throws IllegalArgumentException {
		message.validate();
	}

	private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
		aInputStream.defaultReadObject();
		validate();
	}
}
