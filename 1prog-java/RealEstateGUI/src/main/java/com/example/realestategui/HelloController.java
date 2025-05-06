package com.example.realestategui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
	@FXML
	public ListView<String> sellerNamesListview;

	@FXML
	private Label welcomeText;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ingatlan", "root", "");

//			Statement stmt = conn.createStatement();
//			ResultSet rs = stmt.executeQuery("SELECT id, name, phone FROM sellers");
//			while (rs.next()) {
//				String name = rs.getString("name");
//				int age = rs.getInt("age");
//				System.out.println(name + ", " + age);
//			}
//			rs.close();
//			stmt.close();


			ObservableList<String> names = FXCollections.observableArrayList(
				"Julia", "Ian", "Sue", "Matthew", "Hannah", "Stephan", "Denise"
			);
			sellerNamesListview.setItems(names);

			conn.close();
		} catch (SQLException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@FXML
	protected void onHelloButtonClick() {
		welcomeText.setText("Welcome to JavaFX Application!");
	}

	@FXML
	protected void onHirdetesekBetolteseButtonClick() {
		int debugger = 0;
	}
}
