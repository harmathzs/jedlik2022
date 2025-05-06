module com.example.realestategui {
	requires javafx.controls;
	requires javafx.fxml;


	opens com.example.realestategui to javafx.fxml;
	exports com.example.realestategui;
}
