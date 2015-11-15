package myapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.mail.MessagingException;

import com.dpilipenko.courier.post.Post;
import com.dpilipenko.courier.post.util.Conf;
import com.dpilipenko.courier.postoffice.PostOffice;

public class MyAppMain {

	private static PostOffice _postOffice;

	public static void main(String[] args) {

		// start up PostOffice
		_postOffice = new PostOffice();
		String ipAddress = getExternalIpAddress();
		int portNumber = getPortNumber();
		log("Going to connect to " + ipAddress + ":" + portNumber);
		_postOffice.run(portNumber, new MyAppListener());
		log("PostOffice running at " + ipAddress + ":" + portNumber);

		// start up Post
		Conf conf = new Conf();
		conf.setFrom("MyAppMain");
		conf.setHost(ipAddress);
		conf.setPort(String.valueOf(portNumber));
		conf.setProtocol("smtp"); // TODO this should be unnecessary...refactor conf

		// send a Post
		sendPostMessage(conf, "zzz@unknown.txt", "msg subj", "digimon. digital monster. digimon are the champions");
		// send a second Post
		sendPostMessage(conf, "TedSmith", "devient subversion", "wake up.");

	}

	public static String getExternalIpAddress() {
		String ipResolved = new String();
		String ipDefault = "127.0.0.1";
		try {			
			URL canihazip = new URL("http://canihazip.com/s");
			URLConnection canihazipConnection = canihazip.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(canihazipConnection.getInputStream()));
			String inputLine;
			while ((inputLine = reader.readLine()) != null) {
				ipResolved = inputLine;        
			}
			reader.close();
		} catch (Exception e) {
			log("Exception fetching IP Address. Going to use " + ipDefault + "\n" + e.getStackTrace().toString());
			ipResolved = ipDefault;
		}
		return ipResolved;
	}
	public static int getPortNumber() {
		return 32525;
	}

	public static void sendPostMessage(Conf conf, String to, String subject, String body) {
		try {
			log("Going to send a Post to " + to);
			new Post().email(conf, to, subject, body);
			log("Post sent");
		} catch (MessagingException e) {
			log("Post failed...\n"+e.getStackTrace().toString());
		}
	}

	public static void log(String msg) {
		System.out.println("[MyAppMain] - " + msg);
	}
}
