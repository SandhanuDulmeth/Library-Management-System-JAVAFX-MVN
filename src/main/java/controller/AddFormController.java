package controller;

import dbConnection.DBConnection;
import javafx.scene.control.Alert;
import model.Customer;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddFormController implements Initializable {

    public JFXTextField txtId;
    public JFXTextField txtAddress;
    public JFXTextField txtName;
    public JFXTextField txtSalary;
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
       tableCustomer.setItems(null);
       try {
           Connection connection = DriverManager.getConnection(
                   "jdbc:mysql://localhost:3306/thogakade2",
                   "root",
                   "1234");
           Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery("select *from customer");
           while (resultSet.next()){
               customerList.add(
                       new Customer(
                       resultSet.getString(1),
                               resultSet.getString(2),
                               resultSet.getString(3),
                               Double.parseDouble(resultSet.getString(4))
               ));
           }
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

    public void btnAddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("Insert into Customer Values(?,?,?,?)");
        stm.setObject(1, txtId.getText());
        stm.setObject(2, txtName.getText());
        stm.setObject(3, txtAddress.getText());
        stm.setObject(4, txtSalary.getText());
         stm.executeUpdate();
         new Alert(Alert.AlertType.INFORMATION ,"Added "+txtId.getText()).show();
        loadTable();
    }
}
