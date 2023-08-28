module JavaFX_Board {
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	
	opens application to javafx.graphics, javafx.fxml, java.sql, javafx.base;
	opens application.Controller to javafx.fxml, javafx.base, javafx.controls, java.sql;
	opens application.DTO to javafx.base, javafx.controls, javafx.fxml, java.sql;
	opens application.Service to javafx.base, javafx.controls, javafx.fxml, java.sql;
	opens application.Util to javafx.base, javafx.controls, javafx.fxml, java.sql;
	opens application.DAO to javafx.base, javafx.controls, javafx.fxml, java.sql;
}
