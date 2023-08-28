package application.Controller;

import java.io.IOException;

import application.DTO.Board;
import application.Service.BoardService;
import application.Service.BoardServiceImpl;
import application.Util.SceneUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ReadController {
	static BoardService boardService = new BoardServiceImpl();
	private Board board;
    @FXML
    private TextArea boardContent;
    @FXML
    private TextField boardTitle;
    @FXML
    private TextField boardWriter;
    private Parent root;
    
	public void boardRead(int index) {
		board = boardService.select(index);
		
		if( board == null ) {
			System.out.println("(조회되지 않는 글)");
			return;
		}
		
		boardTitle.setText( board.getTitle() );
		boardWriter.setText( board.getWriter() );
		boardContent.setText( board.getContent() );
	}
	@FXML
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
		
		UpdateController updateController = loader.getController();
		
		if( updateController != null) {
			updateController.boardUpdate(index, title, writer, content);
		}
//		
		SceneUtil.getInstance().switchScene(event, UI.UPDATE.getPath(), root);
    }
    
    @FXML
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
    @FXML
    public void clickList(ActionEvent event) throws IOException {
   	 SceneUtil.getInstance().switchScene(event, UI.MAIN.getPath());
   }
}
