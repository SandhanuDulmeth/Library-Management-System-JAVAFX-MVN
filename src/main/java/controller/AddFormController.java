package controller;

import dbConnection.DBConnection;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import model.Book;
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
    public JFXTextField txtAuthor;
    public JFXTextField txtTitle;
    public JFXTextField txtpublishedYear;
    public JFXTextField txtGenre;
    public JFXTextField txtPrice;
    public JFXTextField txtId1;
    public JFXTextField txtAuthor1;
    public JFXTextField txtTitle1;
    public JFXTextField txtpublishedYear1;
    public JFXTextField txtGenre1;
    public JFXTextField txtPrice1;
    public JFXTextField txtId2;
    public JFXTextField txtTitle2;
    public JFXTextField txtAuthor2;
    public JFXTextField txtpublishedYear2;
    public JFXTextField txtGenre2;
    public JFXTextField txtPrice2;



    @FXML
    private TableView<Book> tableCustomer;

    @FXML
    private TableColumn tblAuthor;

    @FXML
    private TableColumn tblId;

    @FXML
    private TableColumn tblTitle;

    @FXML
    private TableColumn tblPublishedYear;

    @FXML
    private TableColumn tblGenre;

    @FXML
    private TableColumn tblPrice;

    public void loadTable() {
        tableCustomer.getItems().clear();

        List<Book> bookList = new ArrayList<>();
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from book");

            while (resultSet.next()) {
                bookList.add(
                        new Book(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                Integer.parseInt(resultSet.getString(4)),
                                resultSet.getString(5),
                                Double.parseDouble(resultSet.getString(6))
                        ));
            }
            System.out.println(connection);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        ObservableList<Book> bookObservableList = FXCollections.observableArrayList();
        bookObservableList.addAll(bookList);
        tableCustomer.setItems(bookObservableList);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tblAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        tblPublishedYear.setCellValueFactory(new PropertyValueFactory<>("publishedYear"));
        tblGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        tblPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        loadTable();
    }

    public void btnAddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("Insert into book Values(?,?,?,?,?,?)");
        stm.setObject(1, txtId.getText());
        stm.setObject(2, txtTitle.getText());
        stm.setObject(3, txtAuthor.getText());
        stm.setObject(4, txtpublishedYear.getText());
        stm.setObject(5, txtGenre.getText());
        stm.setObject(6, txtPrice.getText());
        int i = stm.executeUpdate();
        if(i>0){
            new Alert(Alert.AlertType.INFORMATION, "Added " + txtId.getText()).show();
        }else{
            new Alert(Alert.AlertType.ERROR, "Not Added " + txtId.getText()).show();
        }

        loadTable();
        txtId.setText(null);
        txtTitle.setText(null);
        txtAuthor.setText(null);
        txtpublishedYear.setText(null);
        txtGenre.setText(null);
        txtPrice.setText(null);
    }
    public void SearchRemoveOnKeyReleased(KeyEvent keyEvent) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();

            PreparedStatement stm = connection.prepareStatement("SELECT * FROM book WHERE BookID = ?");
            stm.setObject(1, txtId1.getText());
            ResultSet resultSet = stm.executeQuery();
            if (resultSet.next()) {

                txtTitle1.setText(resultSet.getString(2));
                txtAuthor1.setText(resultSet.getString(3));
                txtpublishedYear1.setText(resultSet.getString(4));
                txtGenre1.setText(resultSet.getString(5));
                txtPrice1.setText(resultSet.getString(6));

            }else{

                txtTitle1.setText(null);
                txtAuthor1.setText(null);
                txtpublishedYear1.setText(null);
                txtGenre1.setText(null);
                txtPrice1.setText(null);
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void btnRemoveOnAction(ActionEvent actionEvent) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();

            PreparedStatement stm = connection.prepareStatement("DELETE FROM book WHERE BookID = ?");
            stm.setObject(1, txtId1.getText());
            stm.executeUpdate();


        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        new Alert(Alert.AlertType.INFORMATION, "Removed " + txtId.getText()).show();
        txtId1.setText(null);
        txtTitle1.setText(null);
        txtAuthor1.setText(null);
        txtpublishedYear1.setText(null);
        txtGenre1.setText(null);
        txtPrice1.setText(null);
        loadTable();
    }

    public void SearchOnKeyReleased(KeyEvent keyEvent) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();

            PreparedStatement stm = connection.prepareStatement("SELECT * FROM book WHERE BookID = ?");
            stm.setObject(1, txtId2.getText());
            ResultSet resultSet = stm.executeQuery();
            if (resultSet.next()) {

                txtTitle2.setText(resultSet.getString(2));
                txtAuthor2.setText(resultSet.getString(3));
                txtpublishedYear2.setText(resultSet.getString(4));
                txtGenre2.setText(resultSet.getString(5));
                txtPrice2.setText(resultSet.getString(6));

            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }


    }


    public void btnUpdateOnAction(ActionEvent actionEvent) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();

            PreparedStatement stm = connection.prepareStatement("UPDATE book SET Title = ?, Author = ?, PublishedYear = ?, Genre = ?, Price = ? WHERE BookID = ?");
            stm.setObject(6, txtId2.getText());
            stm.setObject(1, txtTitle2.getText());
            stm.setObject(2, txtAuthor2.getText());
            stm.setObject(3, txtpublishedYear2.getText());
            stm.setObject(4, txtGenre2.getText());
            stm.setObject(5, txtPrice2.getText());
            int i = stm.executeUpdate();
            if (i > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Updated " ).show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Not Updated " ).show();
            }


        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }


        txtId2.setText(null);
        txtTitle2.setText(null);
        txtAuthor2.setText(null);
        txtpublishedYear2.setText(null);
        txtGenre2.setText(null);
        txtPrice2.setText(null);
        loadTable();
    }


}
