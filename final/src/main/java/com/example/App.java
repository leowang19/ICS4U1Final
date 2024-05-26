package com.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

// import javax.naming.ConfigurationException;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    private TableView table = new TableView();
    final TextField input = new TextField();

    // array list for row data
    public ObservableList<Object> row;
    public String test = "";

    @Override
    public void start(Stage stage) throws IOException {
        // make stage
        Scene scene = new Scene(new Group());

        // size, name, font
        stage.setTitle("Contacts");
        stage.setWidth(900);
        stage.setHeight(800);
        final Label label = new Label("Contacts");
        label.setFont(new Font("Arial", 20));
        input.setVisible(false);
        // text input

        row = FXCollections.observableArrayList();

        // button

        Button add = new Button("add");
        Button edit = new Button("edit");
        Button delete = new Button("delete");
        Button submit = new Button("submit");
        Button closeButton = new Button("Close");
        submit.setVisible(false);

        // popup
        Popup popup = new Popup();
        VBox popupLayout = new VBox(10);
        popupLayout.setPadding(new Insets(10));
        Label popupLabel = new Label("Please enter a valid number.");
        popupLayout.getChildren().addAll(popupLabel,closeButton);
        popup.getContent().add(popupLayout);

        // click logic
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // ideally use this for everything
                submit.setVisible(true);
                input.setVisible(true);

                input.setPromptText("enter new contact information");
                submit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        String added[] = new String[7];
                        for (int i = 0; i < added.length; i++) {
                            added = input.getText().split(",");
                        }
                        Contact contact = new Contact(added[0], added[1], added[2], added[3], added[4], added[5]);
                        row.add(contact);
                        input.clear();
                        input.setPromptText(null);
                        submit.setVisible(false);
                        input.setVisible(false);

                    }
                });

            }
        });
        // REMEMBER TO MAKE SURE THEY ARE FORCED TO SUBMIT AN INTEGER
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // open
                submit.setVisible(true);
                input.setVisible(true);

                input.setPromptText("enter row you want to delete");
                submit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        String deleteRow = input.getText();
                        if (onlyDigits(deleteRow, deleteRow.length())) {
                            int toDelete = Integer.valueOf(deleteRow);
                            System.out.println(toDelete);
                            row.remove(toDelete - 1);
                        } else {
                            popup.show(stage);
                        }
                        input.clear();
                        input.setPromptText(null);
                        submit.setVisible(false);
                        input.setVisible(false);

                    }
                });

            }
        });

        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // open

            }
        });

        closeButton.setOnAction(event -> popup.hide());

        table.setEditable(true);
        // name of columns
        TableColumn<Contact, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Contact, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Contact, String> phoneCol = new TableColumn<>("Phone Number");
        phoneCol.setMinWidth(100);
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        TableColumn<Contact, String> emailCol = new TableColumn<>("Email");
        emailCol.setMinWidth(300);
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Contact, String> addressCol = new TableColumn<>("Address");
        addressCol.setMinWidth(100);
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Contact, String> postalCodeCol = new TableColumn<>("Postal Code");
        postalCodeCol.setMinWidth(100);
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        table.getColumns().addAll(firstNameCol, lastNameCol, phoneCol, emailCol, addressCol, postalCodeCol);
        table.setItems(row);

        // where everything is made
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, add, edit, delete, submit);
        vbox.getChildren().add(input);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        String line = "";
        String splitBy = ",";
        Contact person;
        try {
            // parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader("C:\\test.csv"));
            while ((line = br.readLine()) != null) // returns a Boolean value
            {
                String[] data = line.split(",");
                if (data.length == 6) {
                    Contact contact = new Contact(data[0], data[1], data[2], data[3], data[4], data[5]);
                    row.add(contact);
                }
            }

            // use buffered file reader
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(scene);
        stage.show();

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static boolean onlyDigits(String str, int n) {
        // Traverse the string from
        // start to end
        for (int i = 0; i < n; i++) {

            // Check if character is
            // not a digit between 0-9
            // then return false
            if (str.charAt(i) < '0'
                    || str.charAt(i) > '9') {
                return false;
            }
        }
        // If we reach here, that means
        // all characters were digits.
        return true;
    }

}
