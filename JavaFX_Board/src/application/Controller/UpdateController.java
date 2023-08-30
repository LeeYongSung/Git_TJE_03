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
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class UpdateController {
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
    
        
    public void boardUpdate(int index, String title, String writer, String content) {
		
    	board = boardService.select(index);	// 선택한 게시글의 정보를 가져옵니다.
		
    	// 수정할 제목, 작성자, 내용을 파라미터에서 가져옵니다.
		String tiTle = title;
    	String wriTer = writer;
    	String conTent = content;
    	
    	// 각 TextField에 게시글의 제목, 작성자, 내용을 설정합니다.
    	Title.setPromptText(tiTle);
    	Writer.setPromptText(wriTer);
    	Content.setPromptText(conTent);
		
	}
    public void updataCreate(ActionEvent event) throws IOException {
//    	System.out.println("눌림");
    	int index = board.getBoardNo();	// 현재 게시글 번호를 가져옵니다.
    	
    	// 수정된 제목, 작성자, 내용을 TextField에서 가져옵니다.
    	String title = Title.getText();
    	String writer = Writer.getText();
    	String content = Content.getText();
    	
    	Board board = new Board(title, writer, content); // 새로운 Board 객체를 생성하고 수정된 정보를 설정합니다.
    	board.setBoardNo(index);	// 게시글 번호도 설정
    	
    	int result = boardService.update(board);	// DB에 업데이트 요청
		
		if (result > 0 ) {
			System.out.println("게시글이 수정되었습니다.");
		}
		
		SceneUtil.getInstance().switchScene(event, UI.MAIN.getPath()); // MAIN 화면으로 전환
    	
    }
    
    @FXML
    public void clickList(ActionEvent event) throws IOException {
   	 SceneUtil.getInstance().switchScene(event, UI.MAIN.getPath());
   }
}
