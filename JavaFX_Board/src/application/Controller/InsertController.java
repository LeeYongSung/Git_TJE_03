package application.Controller;
import java.io.IOException;

import application.DAO.BoardDAO;
import application.DTO.Board;
import application.Util.SceneUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InsertController {
    @FXML
    private TextField textFieldWriter;

    @FXML
    private TextField textFieldBoardNo;

    @FXML
    private TextField textFieldTitles;

    @FXML
    private TextArea textFieldContent;
     			      



    BoardDAO boardDAO = new BoardDAO();

    Board writeBoard;
    Stage stage;
    Scene scene;

    // 글쓰기 버튼 클릭
    public void clickWrite(ActionEvent event) throws IOException {
        writeBoard = new Board();
        writeBoard.setTitle(textFieldTitles.getText());        
        writeBoard.setContent(textFieldContent.getText());
        writeBoard.setWriter(textFieldWriter.getText());
        boardDAO.insert(writeBoard);

        SceneUtil.getInstance().switchScene(event, UI.MAIN.getPath());
    }

    // 목록 버튼 클릭
    public void clickList(ActionEvent event) throws IOException {
    	 SceneUtil.getInstance().switchScene(event, UI.MAIN.getPath());
    }

}