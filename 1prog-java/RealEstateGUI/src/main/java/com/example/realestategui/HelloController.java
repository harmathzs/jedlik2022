package com.example.realestategui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
	@FXML
	public ListView<String> sellerNamesListview;
	@FXML
	public Label eladoNeveLabel;
	@FXML
	public Label eladoTelefonszamaLabel;
	@FXML
	public Label hirdetesekSzamaLabel;

	@FXML
	private Label welcomeText;

	public ResultSet rsAllSaved;
	public ResultSet rsNamesSaved;
	public HashSet<String> sellerNamesSet;
	public ArrayList<String> sellerNames;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ingatlan", "root", "");

			Statement stmt = conn.createStatement();
			rsAllSaved = stmt.executeQuery(
				"""
					SELECT sellers.id, sellers.name, sellers.phone, realestates.id, realestates.sellerid
					FROM realestates
					INNER JOIN sellers ON realestates.sellerid = sellers.id
					ORDER BY sellers.name ASC
				""");
			rsNamesSaved = stmt.executeQuery(
				"""
					SELECT DISTINCT name
					FROM sellers
					ORDER BY name ASC
				""");

			sellerNames = new ArrayList<>(){};
			while (rsNamesSaved.next()) {
				String sellerName = rsNamesSaved.getString("name");
				sellerNames.add(sellerName);
			}
			rsNamesSaved.close();
			stmt.close();


			ObservableList<String> names = FXCollections.observableArrayList(sellerNames);
			sellerNamesListview.setItems(names);

			conn.close();

			// Listen for selection changes
			sellerNamesListview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
				if (newSelection != null) {
					//System.out.println("Selected item: " + newSelection);
					// Handle the selected item here
					String selectedName = newSelection;
				}
			});
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
