package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoard {

    public void btnLoginOnAction(ActionEvent actionEvent) {
        try {
            Stage oldStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            oldStage.close();


            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/AddForm.fxml"))));
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
