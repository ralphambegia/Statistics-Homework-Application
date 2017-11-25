package Main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;

public class studentMarkListController {

    @FXML
    Button toSPageButton;

    @FXML
    VBox stuMarksBox;
    @FXML
    Label stuMLLabel;

    private Student student;

    public void initStudent(Student stu) {
        student = stu;
        loadStudentMarks();
    }

    public void loadStudentMarks() {
        double totalPoints = 0.0;
        boolean attempted = false;
        for(String assgnName : student.getCompletedAssignNames()) {
            attempted = true;
            String marks = student.getAssignmentMarksStr(assgnName);
            String bestMark = student.getStrBestMarkFor(assgnName);
            totalPoints += student.getBestMarkFor(assgnName);
            TextFlow tf = new TextFlow();
            Label markPt1 = new Label(assgnName + ": ");
            markPt1.setPrefWidth(170);
            markPt1.setFont(Font.font("System", 15));
            Label markPt2 = new Label(marks);
            markPt2.setPrefWidth(150);
            markPt2.setFont(Font.font("System", 15));
            Label markPt3 = new Label("Best:  " + bestMark + "%");
            markPt3.setFont(Font.font("System", 15));
            tf.getChildren().addAll(markPt1, markPt2, markPt3);
            stuMarksBox.getChildren().add(tf);
        }
        if(attempted) {
            double finalMark = totalPoints/student.getCompletedAssignNames().size();
            stuMLLabel.setText("Current average: " + String.format("%.1f", finalMark) + "%");
        } else {
            stuMLLabel.setText("There are no marks to show.");
        }
    }

    public void toStudentPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../StudentGUI/studentPage.fxml"));
        Parent root = loader.load();
        Stage stage  = (Stage) toSPageButton.getScene().getWindow();
        stage.setScene(new Scene(root, 650, 400));
        studentPageController controller = loader.<studentPageController>getController();
        controller.passStudent(student);
        stage.show();
    }
}
