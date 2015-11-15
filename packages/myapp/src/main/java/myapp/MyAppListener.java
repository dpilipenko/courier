package myapp;

import java.io.IOException;
import java.io.InputStream;

import org.subethamail.smtp.TooMuchDataException;

import com.dpilipenko.courier.postoffice.IListener;

public class MyAppListener implements IListener {

	@Override
	public boolean accept(String from, String recipient) {
		return true;
	}

	@Override
	public void deliver(String from, String recipient, InputStream data) throws TooMuchDataException, IOException {
		log("Received message from " + from + " intended to " + recipient);
	}
	
	private static void log(String msg) {
		System.out.println("[MyAppListener] - " + msg);
	}
}
