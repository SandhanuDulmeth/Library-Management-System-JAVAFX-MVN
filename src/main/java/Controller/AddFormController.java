package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class AddFormController implements Initializable {

    @FXML
    private TableView<?> tableCustomer;

    @FXML
    private TableColumn<?, ?> tblAddress;

    @FXML
    private TableColumn<?, ?> tblId;

    @FXML
    private TableColumn<?, ?> tblName;

    @FXML
    private TableColumn<?, ?> tblSalary;
   public void loadTable(){

       Connection connection= DriverManager.getConnection(
               "jdbc:mysql://localhost:3306"
       )
   }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
