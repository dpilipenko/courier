package com.dpilipenko.courier.commons;

import java.io.Serializable;

public interface IMessage<T> extends Serializable {
	public T getBody();
	public void setBody(T body);
	public String getSubject();
	public void setSubject(String subject);
}
