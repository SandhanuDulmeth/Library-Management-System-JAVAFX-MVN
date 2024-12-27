package controller;

import dbConnection.DBConnection;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
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
    public JFXTextField txtId1;
    public JFXTextField txtAddress1;
    public JFXTextField txtName1;
    public JFXTextField txtSalary1;
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


   public void loadTable() {
       tableCustomer.getItems().clear();
      // Connection connection = DBConnection.getInstance().getConnection();
       //PreparedStatement stm = connection.prepareStatement("Insert into Customer Values(?,?,?,?)");
       List<Customer> customerList = new ArrayList<>();
       try {
           Connection connection = DBConnection.getInstance().getConnection();

           Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery("select * from customer");

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
       } catch (SQLException | ClassNotFoundException e) {
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
        txtId.setText(null);txtName.setText(null);txtAddress.setText(null);txtSalary.setText(null);
    }


    public void btnRemoveOnAction(ActionEvent actionEvent) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();

            PreparedStatement stm = connection.prepareStatement("DELETE FROM customer WHERE id = ?");
            stm.setObject(1,txtId1.getText());
            stm.executeUpdate();


        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        txtId1.setText(null);txtName1.setText(null);txtAddress1.setText(null);txtSalary1.setText(null);
        loadTable();
    }

    public void SearchOnKeyReleased(KeyEvent keyEvent) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();

            PreparedStatement stm = connection.prepareStatement("SELECT * FROM customer WHERE id = ?");
            stm.setObject(1,txtId1.getText());
            ResultSet resultSet = stm.executeQuery();
            if ( resultSet.next()){

                txtName1.setText( resultSet.getString(2));
                txtAddress1.setText( resultSet.getString(3));
                txtSalary1.setText( resultSet.getString(4));

            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }




    }
}
