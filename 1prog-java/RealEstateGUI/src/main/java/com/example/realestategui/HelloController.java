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
				hirdetesekSzamaLabel.setText(String.valueOf(cnt));
				break;
			}
		}
	}

	@FXML
	protected void onSellerSelected() throws SQLException {
		selectedName = sellerNamesListview.getSelectionModel().getSelectedItem();
		if (selectedName != null) {
			// Now you can use this.sellerNamesListview and other fields
			Statement stmtAll = conn.createStatement();
			rsAllSaved = stmtAll.executeQuery(
				"""
					SELECT sellers.id AS SELLERIDFIELD, sellers.name AS SELLERNAMEFIELD, sellers.phone AS SELLERPHONEFIELD, realestates.id AS ADIDFIELD, realestates.sellerid AS ADSELLERIDFIELD
					FROM realestates
					INNER JOIN sellers ON realestates.sellerid = sellers.id
					ORDER BY sellers.name ASC
				""");

			ResultSet rs = rsAllSaved;
			while (rs.next()) {
				String sellerName = rs.getString("SELLERNAMEFIELD");
				if (sellerName.contains(selectedName)) {
					selectedSellerId = rs.getInt("SELLERIDFIELD");

					eladoNeveLabel.setText(sellerName);
					eladoTelefonszamaLabel.setText(rs.getString("SELLERPHONEFIELD"));

					break;
				}
			}
		}
	}
}
