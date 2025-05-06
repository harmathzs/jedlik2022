package com.example.realestategui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.*;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class HelloController implements Initializable {
	@FXML
	public ListView<String> sellerNamesListview;

	@FXML
	private Label welcomeText;

	public ResultSet rsSaved;
	public HashSet<String> sellerNames;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ingatlan", "root", "");

			Statement stmt = conn.createStatement();
			rsSaved = stmt.executeQuery(
				"""
					SELECT sellers.id, sellers.name, sellers.phone, realestates.id, realestates.sellerid
					FROM realestates
					INNER JOIN sellers ON realestates.sellerid = sellers.id
				""");
			ResultSet rs = rsSaved;
			sellerNames = new HashSet<>(){};
			while (rs.next()) {
				String sellerName = rs.getString("name");
				sellerNames.add(sellerName);
			}
			rs.close();
			stmt.close();


			ObservableList<String> names = FXCollections.observableArrayList(sellerNames);
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
