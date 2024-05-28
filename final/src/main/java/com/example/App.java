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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;

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
    public ObservableList<Contact> row;

    @Override
    public void start(Stage stage) throws IOException {
        // make stage
        Scene scene = new Scene(new Group());

        // size, name, font
        stage.setTitle("Contacts");
        stage.setWidth(950);
        stage.setHeight(800);
        final Label label = new Label("Contacts");
        label.setFont(new Font("Arial", 20));
        input.setVisible(false);
        // text input

        row = FXCollections.observableArrayList();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> saveDataToCSV("C:\\test.csv")));
        
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
        popupLayout.getChildren().addAll(popupLabel, closeButton);
        popup.getContent().add(popupLayout);

        // click logic
        add.setOnAction(e -> {
            input.clear();
            submit.setVisible(true);
            input.setVisible(true);
            input.setPromptText("enter new contact information");
            submit.setOnAction(event -> {
                String[] added = input.getText().split(",");
                if (added.length == 7) {
                    Contact contact = new Contact(added[0], added[1], added[2], added[3], added[4], added[5], added[6]);
                    row.add(contact);
                    input.clear();
                    input.setPromptText(null);
                    submit.setVisible(false);
                    input.setVisible(false);
                }
            });
        });

        delete.setOnAction(e -> {
            input.clear();
            submit.setVisible(true);
            input.setVisible(true);
            input.setPromptText("enter row you want to delete");
            submit.setOnAction(event -> {
                String deleteRow = input.getText();
                if (onlyDigits(deleteRow, deleteRow.length())) {
                    int toDelete = Integer.valueOf(deleteRow);
                    row.remove(toDelete - 1);
                    input.clear();
                    input.setPromptText(null);
                    submit.setVisible(false);
                    input.setVisible(false);
                } else {
                    popup.show(stage);
                }
            });
        });

        edit.setOnAction(e -> {
            input.clear();
            submit.setVisible(true);
            input.setVisible(true);
            input.setPromptText("enter row you want to edit");
            submit.setOnAction(event -> {
                String editRow = input.getText();
                if (onlyDigits(editRow, editRow.length())) {
                    input.clear();
                    int toEdit = Integer.valueOf(editRow);
                    if (toEdit > 0 && toEdit <= row.size()) {
                        Contact contact = row.get(toEdit - 1);
                        row.remove(toEdit - 1);
                        String contactInfo = contact.getFirstName() + "," + contact.getLastName() + ","
                                + contact.getPhoneNumber() + "," + contact.getEmail() + ","
                                + contact.getAddress() + "," + contact.getPostalCode() + "," + contact.getActivity();
                        input.setText(contactInfo);
                        submit.setOnAction(event2 -> {
                            String[] added = input.getText().split(",");
                            if (added.length == 7) {
                                Contact newContact = new Contact(added[0], added[1], added[2], added[3], added[4],
                                        added[5], added[6]);
                                row.add(newContact);
                                input.clear();
                                input.setPromptText(null);
                                submit.setVisible(false);
                                input.setVisible(false);
                            }
                        });
                    }
                }
            });
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

        TableColumn<Contact, String> activityCol = new TableColumn<>("Activity");
        activityCol.setMinWidth(100);
        activityCol.setCellValueFactory(new PropertyValueFactory<>("activity"));
        activityCol.setCellFactory(col -> new ActivityCell());
        activityCol.setOnEditCommit(event -> event.getRowValue().setActivity(event.getNewValue()));

        table.getColumns().addAll(firstNameCol, lastNameCol, phoneCol, emailCol, addressCol, postalCodeCol,
                activityCol);
        table.setItems(row);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, add, edit, delete, submit);
        vbox.getChildren().add(input);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        String line;
        String splitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\test.csv"))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitBy);
                if (data.length == 7) {
                    Contact contact = new Contact(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
                    row.add(contact);
                }
            }
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
        // Traverse the string
        for (int i = 0; i < n; i++) {

            // Check if character
            if (str.charAt(i) < '0'
                    || str.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    private static class ActivityCell extends TableCell<Contact, String> {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setBackground(null);
            } else {
                Contact contact = (Contact) getTableRow().getItem();
                setText(item);
                if (contact != null && contact.getActivity() != null) {
                    if (contact.getActivity().contains("online")) {
                        setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                    } else if (contact.getActivity().contains("offline")) {
                        setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                    } else {
                        setBackground(null);
                    }
                } else {
                    setBackground(null);
                }
            }
        }
    }
    private void saveDataToCSV(String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(fileName), true)
        )) {
            for (Contact person : row) { // Assuming 'row' is your ObservableList<Contact>
                bw.write(String.format("%s,%s,%s,%s,%s,%s,%s%n",
                        person.getFirstName(),
                        person.getLastName(),
                        person.getPhoneNumber(),
                        person.getEmail(),
                        person.getAddress(),
                        person.getPostalCode(),
                        person.getActivity()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

