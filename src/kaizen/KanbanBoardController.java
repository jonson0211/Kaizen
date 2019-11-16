/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaizen;

import java.awt.Insets;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import kaizen.UserData.KaizenDatabase;
import static kaizen.UserData.KaizenDatabase.conn;

/**
 *
 * @author Raymond
 */
public class KanbanBoardController implements Initializable {

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
    private GridPane grid;

    KaizenDatabase KanbanDatabase = new KaizenDatabase();

//    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Loading Kanban board");

//        DISPLAY ITEMS IN GRIDPANE BASED ON DATABASE
        try {

            ResultSet rs = KanbanDatabase.getResultSet("SELECT * FROM TASKS WHERE DO_DATE = '2019-11-18'");
            makeElements(rs, 0);

            rs = KanbanDatabase.getResultSet("SELECT * FROM TASKS WHERE DO_DATE = '1990-11-19'");
            makeElements(rs, 1);

            rs = KanbanDatabase.getResultSet("SELECT * FROM TASKS WHERE DO_DATE = '2019-11-19'");
            makeElements(rs, 2);

            rs = KanbanDatabase.getResultSet("SELECT * FROM TASKS WHERE DO_DATE > '2019-11-19'");
            makeElements(rs, 3);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void makeElements(ResultSet rs, int num) throws SQLException {
        // Pane pane = new Pane();
        for (int i = 0; i < 7; i++) {
            Label taskLabel = new Label();
            taskLabel.setTextAlignment(TextAlignment.CENTER);
            grid.setHalignment(taskLabel, HPos.CENTER);

            try {
                if (rs.next()) {
                    taskLabel.setText((rs.getString("TITLE") + "\n" + "Do Date: " + rs.getString("DO_DATE") + "\n" + "Priority: " + rs.getString("PRIORITY")));
                    grid.add(taskLabel, num, i);
                    taskLabel.setOnDragDetected(new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent event) {
                            /* drag was detected, start a drag-and-drop gesture*/
 /* allow any transfer mode */
                            if (!"Drag Here".equals(taskLabel.getText())) {
                                Dragboard db = taskLabel.startDragAndDrop(TransferMode.ANY);

                                /* Put a string on a dragboard */
                                ClipboardContent content = new ClipboardContent();
                                content.putString(taskLabel.getText());
                                db.setContent(content);

                                event.consume();
                            }

                        }
                    });

                    taskLabel.setOnDragOver(new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            /* data is dragged over the target */
 /* accept it only if it is not dragged from the same node 
         * and if it has a string data */
                            if (event.getGestureSource() != taskLabel && event.getDragboard().hasString() && taskLabel.getText() == "Drag Here") {
                                /* allow for moving */
                                event.acceptTransferModes(TransferMode.MOVE);
                            }

                            event.consume();
                        }
                    });

                    taskLabel.setOnDragEntered(new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            /* the drag-and-drop gesture entered the target */
 /* show to the user that it is an actual gesture target */
                            if (event.getGestureSource() != taskLabel && event.getDragboard().hasString() && taskLabel.getText() == "Drag Here") {
                                taskLabel.setStyle("-fx-text-fill: #50E513");
                            } else{
                                taskLabel.setStyle("-fx-text-fill: #ff3333");
                            }
                            event.consume();
                        }
                    });

                    taskLabel.setOnDragExited(new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            /* mouse moved away, remove the graphical cues */
                            taskLabel.setStyle("-fx-text-fill: #000000");
                            event.consume();
                        }
                    });

                    taskLabel.setOnDragDropped(new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            /* data dropped */
 /* if there is a string data on dragboard, read it and use it */
                            Dragboard db = event.getDragboard();
                            String[] parts = db.getString().split("\n");
                            boolean success = false;
                            if (grid.getColumnIndex(taskLabel) == 2) {
                                //update the database entry where the task name = parts[0]
                                parts[1] = "Do Date: 2019-11-19";
                                
                                try {
                                    KanbanDatabase.insertStatement("UPDATE TASKS SET DO_DATE = '2019-11-19' WHERE TITLE = '" + parts[0] + "'");
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                
                            } else if (grid.getColumnIndex(taskLabel) == 0) {
                                parts[1] = "Do Date: 2019-11-18";
                                
                                 try {
                                    KanbanDatabase.insertStatement("UPDATE TASKS SET DO_DATE = '2019-11-18' WHERE TITLE = '" + parts[0] + "'");
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            } else if (grid.getColumnIndex(taskLabel) == 3){
                                parts[1] = "Do Date: 2019-11-25";
                                
                                 try {
                                    KanbanDatabase.insertStatement("UPDATE TASKS SET DO_DATE = '2019-11-25' WHERE TITLE = '" + parts[0] + "'");
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                            taskLabel.setText(parts[0] + "\n" + parts[1] + "\n" + parts[2]);
                            success = true;
                            event.setDropCompleted(success);
                            event.consume();
                        }
                    });

                    taskLabel.setOnDragDone(new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            /* the drag and drop gesture ended */
 /* if the data was successfully moved, clear it */
                            if (event.getTransferMode() == TransferMode.MOVE) {
                                taskLabel.setText("Drag Here");
                            }
                            event.consume();
                        }
                    });

                } else {
                    while (i < 7) {
                        Label taskLabel1 = new Label("Drag Here");
                        taskLabel1.setTextAlignment(TextAlignment.CENTER);
                        grid.setHalignment(taskLabel1, HPos.CENTER);
                        grid.add(taskLabel1, num, i);

                        taskLabel1.setOnDragDetected(new EventHandler<MouseEvent>() {
                            public void handle(MouseEvent event) {
                                /* drag was detected, start a drag-and-drop gesture*/
 /* allow any transfer mode */
                                if (!"Drag Here".equals(taskLabel1.getText())) {
                                    Dragboard db = taskLabel1.startDragAndDrop(TransferMode.ANY);

                                    /* Put a string on a dragboard */
                                    ClipboardContent content = new ClipboardContent();
                                    content.putString(taskLabel1.getText());
                                    db.setContent(content);

                                    event.consume();
                                }

                            }
                        });

                        taskLabel1.setOnDragOver(new EventHandler<DragEvent>() {
                            public void handle(DragEvent event) {
                                /* data is dragged over the target */
 /* accept it only if it is not dragged from the same node 
         * and if it has a string data */
                                if (event.getGestureSource() != taskLabel1 && event.getDragboard().hasString() && taskLabel1.getText() == "Drag Here") {
                                    /* allow for moving */
                                    event.acceptTransferModes(TransferMode.MOVE);
                                }

                                event.consume();
                            }
                        });

                        taskLabel1.setOnDragEntered(new EventHandler<DragEvent>() {
                            public void handle(DragEvent event) {
                                /* the drag-and-drop gesture entered the target */
 /* show to the user that it is an actual gesture target */
                                if (event.getGestureSource() != taskLabel1 && event.getDragboard().hasString() && taskLabel1.getText() == "Drag Here") {
                                    taskLabel1.setStyle("-fx-text-fill: #50E513");
                                } else {
                                    taskLabel1.setStyle("-fx-text-fill: #ff3333");
                                }

                                // if the the label exists in column 3, 
                                event.consume();
                            }
                        });

                        taskLabel1.setOnDragExited(new EventHandler<DragEvent>() {
                            public void handle(DragEvent event) {
                                /* mouse moved away, remove the graphical cues */
                                taskLabel1.setStyle("-fx-text-fill: #000000");
                                event.consume();
                            }
                        });

                        taskLabel1.setOnDragDropped(new EventHandler<DragEvent>() {
                            public void handle(DragEvent event) {
                                /* data dropped */
 /* if there is a string data on dragboard, read it and use it */
                                Dragboard db = event.getDragboard();
                                String[] parts = db.getString().split("\n");
                                boolean success = false;
                                if (grid.getColumnIndex(taskLabel1) == 2) {
                                    //update the database entry where the task name = 1st part of the string (title), setting date as XXX
                                    parts[1] = "Do Date: 2019-11-19";
                                        try {
                                    KanbanDatabase.insertStatement("UPDATE TASKS SET DO_DATE = '2019-11-19' WHERE TITLE = '" + parts[0] + "'");
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                } else if (grid.getColumnIndex(taskLabel1) == 0) {
                                    parts[1] = "Do Date: 2019-11-18";
                                    
                                     try {
                                    KanbanDatabase.insertStatement("UPDATE TASKS SET DO_DATE = '2019-11-18' WHERE TITLE = '" + parts[0] + "'");
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                    
                                } else if (grid.getColumnIndex(taskLabel1) == 3){
                                parts[1] = "Do Date: 2019-11-25";
                                
                                 try {
                                    KanbanDatabase.insertStatement("UPDATE TASKS SET DO_DATE = '2019-11-25' WHERE TITLE = '" + parts[0] + "'");
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                                taskLabel1.setText(parts[0] + "\n" + parts[1] + "\n" + parts[2]);
                                success = true;
                                event.setDropCompleted(success);
                                event.consume();
                            }
                        });

                        taskLabel1.setOnDragDone(new EventHandler<DragEvent>() {
                            public void handle(DragEvent event) {
                                /* the drag and drop gesture ended */
 /* if the data was successfully moved, clear it */
                                if (event.getTransferMode() == TransferMode.MOVE) {
                                    taskLabel1.setText("Drag Here");
                                }
                                event.consume();
                            }
                        });
                        i++;
                    }
                    break;

                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

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
    public void handleTimesheets(ActionEvent event) throws IOException {
        psh.switcher(event, "Timesheets.fxml");
    }

}
