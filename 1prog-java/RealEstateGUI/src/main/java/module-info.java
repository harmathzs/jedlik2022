module com.example.realestategui {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires mysql.connector.j;


	opens com.example.realestategui to javafx.fxml;
	exports com.example.realestategui;
}
