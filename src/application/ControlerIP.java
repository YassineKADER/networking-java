package application;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ControlerIP {
	@FXML
	private TextField ip_feild;
	@FXML
	private  ListView<String> ip_list;
	@FXML
	private Label text;
	
	
	
	public void test() throws Exception {
		text.setText("Scanning...");
		ip_list.getItems().clear();
		Searcher s1 = new Searcher(1, 25, ip_feild.getText());
		Searcher s2 = new Searcher(26, 52, ip_feild.getText());
		Searcher s3 = new Searcher(53, 102, ip_feild.getText());
		Searcher s4 = new Searcher(103, 128, ip_feild.getText());
		Searcher s5 = new Searcher(129, 160, ip_feild.getText());
		Searcher s6 = new Searcher(161, 182, ip_feild.getText());
		Searcher s7 = new Searcher(203, 225, ip_feild.getText());
		Searcher s8 = new Searcher(226, 255, ip_feild.getText());
		
		s1.start();
		s2.start();
		s3.start();
		s4.start();
		s5.start();
		s6.start();
		s7.start();
		s8.start();
		
		System.out.println(Thread.activeCount());
		
		
		
		s8.join();
		ip_list.getItems().addAll(Searcher.ip_collection);
		Searcher.clear();
		text.setText("ip");
	}

	
	
}
