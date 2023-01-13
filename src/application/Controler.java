package application;

import javafx.event.ActionEvent;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class Controler {
	@FXML
	private TextField ip_feild;
	@FXML
	private ListView<String> port_list;

	public void test(ActionEvent event) throws InterruptedException, ExecutionException {
		port_list.getItems().clear();
	    final ExecutorService es = Executors.newFixedThreadPool(20);
	    final String ip = ip_feild.getText();
	    final int timeout = 400;
	    final List<Future<ScanResult>> futures = new ArrayList<>();
	    for (int port = 1; port <= 65535; port++) {
	        // for (int port = 1; port <= 80; port++) {
	        futures.add(portIsOpen(es, ip, port, timeout));
	    }
	    es.awaitTermination(200L, TimeUnit.MILLISECONDS);
	    int openPorts = 0;
	    for (final Future<ScanResult> f : futures) {
	        if (f.get().isOpen()) {
	            openPorts++;
	            port_list.getItems().add(f.get().getPort().toString());
	        }
	    }
	    System.out.println("There are " + openPorts + " open ports on host " + ip + " (probed with a timeout of "
	            + timeout + "ms)");
	}

	public static Future<ScanResult> portIsOpen(final ExecutorService es, final String ip, final int port,
	        final int timeout) {
	    return es.submit(new Callable<ScanResult>() {
	        @Override
	        public ScanResult call() {
	            try {
	                Socket socket = new Socket();
	                socket.connect(new InetSocketAddress(ip, port), timeout);
	                socket.close();
	                return new ScanResult(port, true);
	            } catch (Exception ex) {
	                return new ScanResult(port, false);
	            }
	        }
	    });
	}
	
}
