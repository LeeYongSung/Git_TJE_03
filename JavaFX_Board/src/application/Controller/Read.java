package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.Controller.Update;
import application.DTO.Board;
import application.Service.BoardService;
import application.Service.BoardServiceImpl;
import application.Util.SceneUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Read {
	static BoardService boardService = new BoardServiceImpl();
	List<String> boardList = new ArrayList<>();
	Board board;


    @FXML
    private Button boardBack;
    @FXML
    private Button boardUpdate;
    @FXML
    private TextField boardTitle;
    @FXML
    private TextField boardWriter;
    @FXML
    private TextArea boardContent;
    
    private Stage stage;
    private Scene scene; 
    private Parent root;
	
	public void boardRead(int index) {
		board = boardService.select(index);
		
		boardList.add(board.getTitle());
		boardList.add(board.getWriter());
		boardList.add(board.getContent());
		
		if( boardList == null ) {
			System.out.println("(조회되지 않는 글)");
			return;
		}
		
		boardTitle.setText( boardList.get(0) );
		boardWriter.setText( boardList.get(1) );
		boardContent.setText( boardList.get(2) );
	}
    
    public void boardUpdate(ActionEvent event) throws IOException {
    	int index = board.getBoardNo();
    	
    	String title = boardTitle.getText();
    	String writer = boardWriter.getText();
    	String content = boardContent.getText();
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource(UI.UPDATE.getPath()));
    	
    	try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Update updateController = loader.getController();
		
		if( updateController != null) {
			updateController.boardUpdate(index, title, writer, content);
		}
		
		scene = new Scene(root);
		stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
    }
    
    public void boardDelete(ActionEvent event) throws IOException {
    	int index = board.getBoardNo();
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("게시글 삭제");
		alert.setHeaderText("정말로 삭제하시겠습니까?");
		alert.setContentText("게시글이 삭제되어 복구 할 수 없습니다.");
		
		// [확인] 클릭 시
		if ( alert.showAndWait().get() == ButtonType.OK ) {
			System.out.println("게시글을 삭제했습니다.");
			int result = boardService.delete(index);
			if( result > 0 ) System.out.println("게시글이 삭제되었습니다."); 
			SceneUtil.getInstance().switchScene(event, UI.MAIN.getPath());
		}    	
    }
}
