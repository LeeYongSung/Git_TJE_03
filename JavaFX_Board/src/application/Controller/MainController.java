package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.DAO.BoardDAO;
import application.DTO.Board;
import application.Util.SceneUtil;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
	BoardDAO boardDAO = new BoardDAO();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// 게시글 목록 DAO로 부터 불러와서 boardList에 담아 오기
		List<Board> boardList = (List<Board>) boardDAO.selectList();
		
		
		ObservableList<Board> list = FXCollections.observableArrayList(boardList);
		
		colBoardNo.setCellValueFactory( new PropertyValueFactory<>("BoardNo") );
		colTitle.setCellValueFactory( new PropertyValueFactory<>("Title") );
		colWriter.setCellValueFactory( new PropertyValueFactory<>("Writer") );
		colRegDate.setCellValueFactory( new PropertyValueFactory<>("RegDate") );
		colUpdDate.setCellValueFactory( new PropertyValueFactory<>("UpdDate") );

        // TableView 에 데이터 리스트를 지정
        // - 미리 매핑된 TableColumn 에 리스트의 요소 객체의 변수값이 지정됨
        boardTableView.setItems(list);

        boardTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                // 더블 클릭 이벤트
                if (event.getClickCount() == 2) {

                    int index = boardTableView.getSelectionModel().getSelectedItem().getBoardNo();
                    
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(UI.READ.getPath()));
                    try {
                    	root = loader.load(); 
                    } catch (IOException e) {
                    	e.printStackTrace();
                    }
                  
                    Read readController =  loader.getController();
                    
                    if( readController != null) {
   
                    	readController.boardRead(index);
                    }
                    try {
						SceneUtil.getInstance().switchScene(event, UI.READ.getPath());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }

            }
        });
	}
	public void clickWrite(ActionEvent event) throws IOException {
		SceneUtil.getInstance().switchScene(event, UI.INSERT.getPath());
	}

}
