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

	@FXML
	public ResultSet rsAllSaved;
	@FXML
	public ResultSet rsNamesSaved;
	@FXML
	public HashSet<String> sellerNamesSet;
	@FXML
	public ArrayList<String> sellerNames;
	@FXML
	public String selectedName;
	@FXML
	public int selectedSellerId;

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
			//rsNamesSaved.close();
			//stmt.close();


			ObservableList<String> names = FXCollections.observableArrayList(sellerNames);
			sellerNamesListview.setItems(names);

			sellerNamesListview.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> {
					try {
						onSellerSelected();
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
				}
			);

			//conn.close();
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

	@FXML
	protected void onSellerSelected() throws SQLException {
		selectedName = sellerNamesListview.getSelectionModel().getSelectedItem();
		if (selectedName != null) {
			// Now you can use this.sellerNamesListview and other fields
			ResultSet rs = rsAllSaved;
			while (rs.next()) {
				String sellerName = rs.getString("name");
				if (sellerName == selectedName) {
					selectedSellerId = rs.getInt("seller.id");

					eladoNeveLabel.setText(sellerName);
					eladoTelefonszamaLabel.setText(rs.getString("phone"));
				}
			}
		}
	}
}
