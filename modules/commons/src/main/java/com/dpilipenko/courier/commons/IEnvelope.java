package com.dpilipenko.courier.commons;

import java.io.Serializable;

public interface IEnvelope<T> extends Serializable {
	public String getFromAddress();
	public void setFromAddress(String fromAddress);
	public String getToAddress();
	public void setToAddress(String toAddress);
	public IMessage<T> getMessage();
	public void setMessage(IMessage<T> message);
}
