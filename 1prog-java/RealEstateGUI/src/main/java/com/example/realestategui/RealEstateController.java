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

public class RealEstateController implements Initializable {
	@FXML
	public ListView<String> sellerNamesListview;
	@FXML
	public Label eladoNeveLabel;
	@FXML
	public Label eladoTelefonszamaLabel;
	@FXML
	public Label hirdetesekSzamaLabel;

	@FXML
	public Label welcomeText;

	public Connection conn;

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

	private static Boolean isRunningTest = null;

	public static boolean isRunningTest() {
		if (isRunningTest == null) {
			try {
				Class.forName("org.junit.Test");
				isRunningTest = true;
			} catch (ClassNotFoundException e) {
				isRunningTest = false;
			}
		}
		return isRunningTest;
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ingatlan", "root", "");


			Statement stmtNames = conn.createStatement();
			rsNamesSaved = stmtNames.executeQuery(
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
			if (!isRunningTest()) sellerNamesListview.setItems(names);

			if (!isRunningTest()) sellerNamesListview.getSelectionModel().selectedItemProperty().addListener(
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
		if (!isRunningTest()) welcomeText.setText("Welcome to JavaFX Application!");
	}

	@FXML
	protected void onHirdetesekBetolteseButtonClick() throws SQLException {
		PreparedStatement stmtCount = conn.prepareStatement("""
			SELECT COUNT(*) AS CNT
			FROM realestates
			WHERE sellerid = ?
		""");
		stmtCount.setInt(1, selectedSellerId);
		ResultSet rsCount = stmtCount.executeQuery();

		while (rsCount.next()) {
			int cnt = rsCount.getInt("CNT");
			if (cnt>=0) {
				if (!isRunningTest()) hirdetesekSzamaLabel.setText(String.valueOf(cnt));
				break;
			}
		}
	}

	@FXML
	protected void onSellerSelected() throws SQLException {
		if (!isRunningTest()) selectedName = sellerNamesListview.getSelectionModel().getSelectedItem();
		if (selectedName != null) {
			// 1. Külön lekérdezzük az eladó adatait
			PreparedStatement sellerStmt = conn.prepareStatement("""
				SELECT id, phone
				FROM sellers
				WHERE name = ?
				LIMIT 1
			""");
			sellerStmt.setString(1, selectedName);
			ResultSet sellerRs = sellerStmt.executeQuery();

			if (sellerRs.next()) {
				selectedSellerId = sellerRs.getInt("id");
				if (!isRunningTest()) eladoNeveLabel.setText(selectedName);
				if (!isRunningTest()) eladoTelefonszamaLabel.setText(sellerRs.getString("phone"));

				// 2. esetleg, Frissítjük a hirdetések számát
				//onHirdetesekBetolteseButtonClick();
			}
//			sellerRs.close();
//			sellerStmt.close();
		}
	}

}
