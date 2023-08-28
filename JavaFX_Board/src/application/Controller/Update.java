package application.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import application.DTO.Board;
import application.Service.BoardService;
import application.Service.BoardServiceImpl;
import application.Util.SceneUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Update {
	static BoardService boardService = new BoardServiceImpl();
	List<String> boardList = new ArrayList<>();
	Board board;
	
	@FXML
	private TextField Writer;
	@FXML
	private TextField Title;
    @FXML
    private TextArea Content;
    @FXML
    private Button boardBack;
    @FXML
    private Button updateCreate;
    
    private Stage stage;
    private Scene scene; 
    
    
    public void boardUpdate(int index, String title, String writer, String content) {
		board = boardService.select(index);
		
		String tiTle = title;
    	String wriTer = writer;
    	String conTent = content;
    	
    	Title.setPromptText(tiTle);
    	Writer.setPromptText(wriTer);
    	Content.setPromptText(conTent);
		
	}
    public void updataCreate(ActionEvent event) throws IOException {
//    	System.out.println("눌림");
    	int index = board.getBoardNo();
    	String title = Title.getText();
    	String writer = Writer.getText();
    	String content = Content.getText();
    	
    	Board board = new Board(title, writer, content);
    	board.setBoardNo(index);
    	
    	int result = boardService.update(board);
		
		if (result > 0 ) {
			System.out.println("게시글이 수정되었습니다.");
		}
		
		SceneUtil.getInstance().switchScene(event, UI.MAIN.getPath());
    	
    }
}
