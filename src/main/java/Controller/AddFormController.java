package Controller;

import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddFormController implements Initializable {

    @FXML
    private TableView<Customer> tableCustomer;

    @FXML
    private TableColumn tblAddress;

    @FXML
    private TableColumn tblId;

    @FXML
    private TableColumn tblName;

    @FXML
    private TableColumn tblSalary;

    List<Customer> customerList = new ArrayList<>();
   public void loadTable() {

       try {
           Connection connection = DriverManager.getConnection(
                   "jdbc:mysql://localhost:3306/thogakade2",
                   "root",
                   "1234");
           Statement statement = connection.createStatement();
statement.executeQuery("select *from customer");
           System.out.println(connection);
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
       ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
       customerObservableList.addAll(customerList);
       tableCustomer.setItems(customerObservableList);

}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tblSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        loadTable();
    }
}
