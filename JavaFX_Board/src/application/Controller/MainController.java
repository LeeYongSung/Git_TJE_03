package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.DTO.Board;
import application.Service.BoardService;
import application.Service.BoardServiceImpl;
import application.Util.SceneUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainController implements Initializable {
	static List<Board> boardList = new ArrayList<Board>();		// 게시글 리스트를 저장할 static List 선언
	static BoardService boardService = new BoardServiceImpl();	// 게시글 서비스를 수행할 BoardService 인터페이스의 구현체인 BoardServiceImpl 객체 생성
	
	@FXML
	private TableView<Board> boardTableView;
	@FXML
	private TableColumn<Board, Integer> colBoardNo;
	@FXML
	private TableColumn<Board, String> colTitle;
	@FXML
	private TableColumn<Board, String> colWriter;
	@FXML
	private TableColumn<Board, String> colRegDate;
	@FXML
	private TableColumn<Board, String> colUpdDate;
	Stage stage;
	Scene scene;
	Parent root;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		 
		/* 게시글 서비스로부터 모든 게시글 정보를 가져와서 
        	boardList에 저장합니다. */
		boardList = boardService.list();
		
		/* ObservableList에 방금 가져온 데이터를 넣어서 
           TableView가 변화를 감지하고 화면을 업데이트 할 수 있게 합니다. */
		ObservableList<Board> list = FXCollections.observableArrayList(boardList);
		
		
		/* 각각의 테이블 컬럼에 대한 셀 값 설정을 위한 팩토리 설정.
           "setCellFactory()" 메소드로 각 셀에 어떤 데이터가 들어갈지 지정합니다. */
		colBoardNo.setCellValueFactory( new PropertyValueFactory<>("BoardNo") );
		colTitle.setCellValueFactory( new PropertyValueFactory<>("Title") );
		colWriter.setCellValueFactory( new PropertyValueFactory<>("Writer") );
		colRegDate.setCellValueFactory( new PropertyValueFactory<>("RegDate") );
		colUpdDate.setCellValueFactory( new PropertyValueFactory<>("UpdDate") );

		 /* 테이블 뷰에 Observable 리스트 설정.
        	이제 테이블 뷰는 list의 내용을 화면에 보여줍니다. */
        boardTableView.setItems(list);

        /* 마우스 클릭 이벤트 핸들러 설정.
           사용자가 테이블 뷰의 한 행을 더블 클릭하면 해당 게시글의 상세 내용을 보여줍니다. */
        boardTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                // 더블 클릭 이벤트
                if (event.getClickCount() == 2) {  // 더블 클릭 이벤트 처리. 사용자가 테이블의 행을 더블 클릭했을 때 실행됩니다.
                	
                    int index = boardTableView.getSelectionModel().getSelectedItem().getBoardNo();  // 선택한 행의 게시글 번호를 가져옵니다.
    
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource(UI.READ.getPath())); // 게시글 읽기 화면의 FXML을 로드합니다.
                        Parent root = loader.load();
                        ReadController readController = loader.getController(); // 로더로부터 ReadController 인스턴스를 가져옵니다.
                        readController.boardRead(index); // ReadController의 boardRead 메소드를 호출하여 해당 게시글을 읽어옵니다.
                        
                        
                        /* SceneUtil을 사용하여 이벤트 발생 시 
                        	(게시글 더블클릭 시) READ 화면으로 전환 */
                        SceneUtil.getInstance().switchScene(event, UI.READ.getPath(), root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
	}
	
	/**
	 * 글쓰기" 버튼 클릭 이벤트 처리 메서드. 
     *  "글쓰기" 버튼 클릭 시 INSERT 화면으로 전환됩니다.
	 * @param event
	 * @throws IOException
	 */
	public void clickWrite(ActionEvent event) throws IOException {
		SceneUtil.getInstance().switchScene(event, UI.INSERT.getPath());
	}

}
