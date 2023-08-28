module JavaFX_Board {
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	
	opens application to javafx.graphics, javafx.fxml;
	opens application.Controller to javafx.fxml;
	opens application.DTO to javafx.base;
	opens application.Service to javafx.base, favafx,fxml;
	opens application.Util to javafx.base;
	opens application.DAO to java.sql;
}
