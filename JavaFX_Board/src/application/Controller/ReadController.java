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
		board = boardService.select(index); // 선택한 게시글의 정보를 가져옵니다.
		
		if( board == null ) {
			System.out.println("(조회되지 않는 글)");
			return;
		}
		
		// 각 필드에 게시글의 제목, 작성자, 내용을 설정합니다.
		boardTitle.setText( board.getTitle() );
		boardWriter.setText( board.getWriter() );
		boardContent.setText( board.getContent() );
	}
	@FXML
    public void boardUpdate(ActionEvent event) throws IOException {
    	
		
    	int index = board.getBoardNo(); // 현재 게시글 번호를 가져옵니다.
    	
    	// 수정할 제목, 작성자, 내용을 TextField에서 가져옵니다.
    	String title = boardTitle.getText();
    	String writer = boardWriter.getText();
    	String content = boardContent.getText();
    	
    	 // UPDATE 화면의 FXML을 로드합니다.
    	FXMLLoader loader = new FXMLLoader(getClass().getResource(UI.UPDATE.getPath()));
    	
    	try {
			root = loader.load();  // 로드된 FXML로부터 루트 노드를 얻습니다.
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    	 // 로더로부터 UpdateController 인스턴스를 가져옵니다.
		UpdateController updateController = loader.getController();
		
		if( updateController != null) {
			// UpdateController의 메소드 호출하여 게시글 업데이트
			updateController.boardUpdate(index, title, writer, content);
		}
		// UPDATE 화면으로 전환
		SceneUtil.getInstance().switchScene(event, UI.UPDATE.getPath(), root);
    }
    
    @FXML
    public void boardDelete(ActionEvent event) throws IOException {
    	int index = board.getBoardNo();	// 현재 게시글 번호를 가져옵니다.
    	
    	 /* Alert는 팝업 창을 생성하는 클래스입니다. 
        사용자에게 삭제 확인 메세지를 보여주고 확인/취소 버튼을 제공합니다. */
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("게시글 삭제");
		alert.setHeaderText("정말로 삭제하시겠습니까?");
		alert.setContentText("게시글이 삭제되어 복구 할 수 없습니다.");
		
		// [확인] 클릭 시
		if ( alert.showAndWait().get() == ButtonType.OK ) {
			/* 사용자가 '확인' 버튼을 클릭하면,
		       선택한 게시물을 데이터베이스에서 삭제하고 MAIN 화면으로 돌아갑니다. */
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
