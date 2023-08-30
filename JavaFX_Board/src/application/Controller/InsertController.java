package application.Controller;
import java.io.IOException;

import application.DAO.BoardDAO;
import application.DTO.Board;
import application.Service.BoardService;
import application.Service.BoardServiceImpl;
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
     			
    BoardDAO boardDAO = new BoardDAO();	// 데이터베이스 연결 및 쿼리 실행 객체 생성
    BoardService boardservice = new BoardServiceImpl();
    Board writeBoard;	// 새로운 게시판 객체 생성 (아직 초기화되지 않음)
    Stage stage;		// 스테이지 변수 선언 (아직 초기화되지 않음)
    Scene scene;		// 씬 변수 선언 (아직 초기화되지 않음)


    /**
     * 글쓰기 버튼 클릭 이벤트 처리 메서드
     * @param event
     * @throws IOException
     */
    public void clickWrite(ActionEvent event) throws IOException {
        writeBoard = new Board();								// 새로운 보드 객체 초기화
        writeBoard.setTitle(textFieldTitles.getText());			// 제목 필드의 텍스트를 가져와 Board 객체에 설정   	
        writeBoard.setContent(textFieldContent.getText());		// 내용 필드의 텍스트를 가져와 Board 객체에 설정
        writeBoard.setWriter(textFieldWriter.getText());		// 작성자 필드의 텍스트를 가져와 Board 객체에 설정
        boardservice.insert(writeBoard);							// 생성한 게시글 정보가 담긴 Board 객체를 DB에 저장하는 DAO 메소드 호출

        /* SceneUtil을 사용하여 이벤트 발생 시 (글 작성 후 '작성' 버튼 클릭) 
        MAIN 화면으로 전환. UI.MAIN.getPath()는 MAIN 화면의 경로를 반환 */
        SceneUtil.getInstance().switchScene(event, UI.MAIN.getPath());
    }

    /**
     * 목록 버튼 클릭 이벤트 처리 메서드
     * @param event
     * @throws IOException
     */
    public void clickList(ActionEvent event) throws IOException {
    	 SceneUtil.getInstance().switchScene(event, UI.MAIN.getPath());
    	 /* 목록 버튼을 클릭했을 때도 MAIN 화면으로 전환. 
  	   		사용자가 글 작성을 중단하고 목록으로 돌아갈 수 있도록 함 */
    }

}