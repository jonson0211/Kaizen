/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaizen;

import java.awt.Insets;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Raymond
 */
public class KanbanBoardController implements Initializable{
    
    
    PageSwitchHelper psh = new PageSwitchHelper();
    
    @FXML
    private Button signOutButton;
    @FXML
    private ToggleButton settingsButton;
    @FXML
    private ToggleButton kanbanBoard;
    @FXML
    private ToggleButton deepFocus;
    @FXML
    private ToggleButton taskTracker;
    @FXML
    private ToggleButton dailyLearnings;
    @FXML
    private ToggleButton about;
    @FXML
    private ToggleButton timeDashboard;
    @FXML
    private ToggleButton doDateView;
    @FXML
    private ToggleButton dueDateView;
    @FXML
    private Label welcome;
    @FXML
    private Label welcomeSubheading;
    @FXML
    private Label task;
    
    @FXML
    private GridPane grid;
    
    
     /*try {
            ResultSet lastRowRs = userDB.getResultSet("SELECT HOURS_SLEPT, DATE FROM HEALTH WHERE"
                    + " USER_NAME = '" + LoginScreenController.loggedInUsername + "' "
                    + "ORDER BY HEALTH_ID DESC LIMIT 1;"); */
//    
//
//    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        System.out.println("Loading Kanban board");
//        
//        for (int i = 0; i < 7; i++) {
            
//                Text text = new Text()  /*insert select statement where only today do date are selected*/;
//       text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
//       grid.add(text.getTitle, 0, i); // add tasks with today do date
            //display ArrayList of Tasks due today
//       // Drag/drop feature
//        b.setOnDragDetected(event -> {
//            if (getItem() == null) {
//                return;
            }
// 
//            Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
//            ClipboardContent content = new ClipboardContent();
//             
//            // Serialize the object
//            String cellStateSerialized = "";
//            try {
//                ByteArrayOutputStream bo = new ByteArrayOutputStream();
//                ObjectOutputStream so = new ObjectOutputStream(bo);
//                so.writeObject((ElementCellState)getItem());
//                so.flush();
//                cellStateSerialized = bo.toString();
//            } catch (Exception e) {
//                System.err.println(e);
//            }
// 
//            content.putString(cellStateSerialized);
// 
//            dragboard.setContent(content);
// 
//            event.consume();
//        });
// 
//        setOnDragOver(event -> {
//            if (event.getGestureSource() != thisCell &&
//                   event.getDragboard().hasString()) {
//                event.acceptTransferModes(TransferMode.MOVE);
//            }
// 
//            event.consume();
//        });
// 
//        setOnDragEntered(event -> {
//            if (event.getGestureSource() != thisCell &&
//                    event.getDragboard().hasString()) {
//                setOpacity(0.3);
//            }
//        });
// 
//        setOnDragExited(event -> {
//            if (event.getGestureSource() != thisCell &&
//                    event.getDragboard().hasString()) {
//                setOpacity(1);
//            }
//        });
// 
//        setOnDragDropped(event -> {
//            if (getItem() == null) {
//                return;
//            }
// 
//            Dragboard db = event.getDragboard();
//            boolean success = false;
// 
//            if (db.hasString()) {
//                ObservableList<ElementCellState> items = getListView().getItems();
// 
//                // Deserialize the object
//                ElementCellState cellState = null;
//                try {
//                    byte b[] = db.getString().getBytes(); 
//                    ByteArrayInputStream bi = new ByteArrayInputStream(b);
//                    ObjectInputStream si = new ObjectInputStream(bi);
//                    cellState = (ElementCellState) si.readObject();
//                } catch (Exception e) {
//                    System.err.println(e);
//                }
//                 
//                int draggedIdx = items.indexOf(cellState);
//                int thisIdx = items.indexOf(getItem());
//                 
//                items.set(draggedIdx, getItem());
//                items.set(thisIdx, cellState);
// 
//                List<ElementCellState> itemscopy = new ArrayList<>(getListView().getItems());
//                getListView().getItems().setAll(itemscopy);
// 
//                success = true;
//            }
//            event.setDropCompleted(success);
// 
//            event.consume();
//        });
// 
//        setOnDragDone(DragEvent::consume);
//    }
// 
//        }
//    
//
//   
//        Button chartTitle = new Button("Current Year");
//        chartTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
//        grid.add(chartTitle, 2, 0);
//        
//    }
//    
//     
    
    //method to change the scene from do date mode to due date mode
    @FXML
    public void handleDueDateView(ActionEvent event) throws IOException {
        psh.switcher(event, "KanbanBoardDueDateView.fxml");
    }
    
    //method to change the scene from due date mode back to the default do date mode
    @FXML
    public void handleDoDateView(ActionEvent event) throws IOException {
        psh.switcher(event, "KanbanBoardDoDateView.fxml");
    }
    
    //switch to about
    @FXML
    public void handleAboutScreen(ActionEvent event) throws IOException {
        psh.switcher(event, "AboutScreen.fxml");
    }
    
    //switch to daily learnings
    @FXML
    public void handleDailyLearnings(ActionEvent event) throws IOException {
        psh.switcher(event, "DailyLearnings.fxml");
    }
    
    //switch to deep focus mode
    @FXML
    public void handleDeepFocusMode(ActionEvent event) throws IOException {
        psh.switcher(event, "DeepFocusMode.fxml");
    }
    
    //switch to register screen
    @FXML
    public void handleSignOut(ActionEvent event) throws IOException {
        psh.switcher(event, "LoginScreen.fxml");
    }
    
    @FXML
    public void handleTaskTracker(ActionEvent event) throws IOException {
        psh.switcher(event, "TaskTracker.fxml");
    }
    //switch to time dashboard
    @FXML
    public void handleTimeDashboard(ActionEvent event) throws IOException {
        psh.switcher(event, "PieChart.fxml");
    }
    
    @FXML
    public void handleTimesheets(ActionEvent event) throws IOException{
        psh.switcher(event, "Timesheets.fxml");
    }
    
    @FXML
    public void initialize() {
        //for every entry in database, display data
        System.out.println("OAIdfjif");
    }
    
}
