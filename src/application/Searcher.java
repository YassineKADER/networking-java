package application;


import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Searcher extends Thread{
	public static List<String> ip_collection = new ArrayList<String>();
	private int first, end;
	private String host;
	public Searcher(int first, int end, String ip) {
		this.first = first;
		this.end = end;
		this.host = ip;
	}
	
	private synchronized void addtolist(String s) {
		this.ip_collection.add(s);
	}

	@Override
	public void run(){
		try {
			checkHosts(this.host);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void clear() {
		ip_collection.clear();
	}
	
	public void checkHosts(String subnet) throws IOException {
		int timeout = 300;
		for (int i = this.first; i <= this.end; i++) {
			String host = subnet + "." + i;
			if (InetAddress.getByName(host).isReachable(timeout)) {
				addtolist(host);
			}
		}
		
	}
}
