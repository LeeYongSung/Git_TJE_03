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
	static List<Board> boardList = new ArrayList<Board>();
	static BoardService boardService = new BoardServiceImpl();
	
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
		
		boardList = boardService.list();
		
		ObservableList<Board> list = FXCollections.observableArrayList(boardList);
		
		colBoardNo.setCellValueFactory( new PropertyValueFactory<>("BoardNo") );
		colTitle.setCellValueFactory( new PropertyValueFactory<>("Title") );
		colWriter.setCellValueFactory( new PropertyValueFactory<>("Writer") );
		colRegDate.setCellValueFactory( new PropertyValueFactory<>("RegDate") );
		colUpdDate.setCellValueFactory( new PropertyValueFactory<>("UpdDate") );

        boardTableView.setItems(list);

        boardTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                // 더블 클릭 이벤트
                if (event.getClickCount() == 2) {
                	
                    int index = boardTableView.getSelectionModel().getSelectedItem().getBoardNo();
    
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource(UI.READ.getPath()));
                        Parent root = loader.load();
                        ReadController readController = loader.getController();
                        readController.boardRead(index);
                        
                        SceneUtil.getInstance().switchScene(event, UI.READ.getPath(), root);
                    } catch (IOException e) {
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
