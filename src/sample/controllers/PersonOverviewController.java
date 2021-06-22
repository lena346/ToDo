package sample.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.Main;
import sample.models.Person;
import sample.requests.models.PersonEntity;
import sample.utils.DateUtil;
import sample.utils.RequestUtil;

import java.util.List;

public class PersonOverviewController extends PersonController<Label> {
    @FXML
    private TableView<Person> personTableView;
    @FXML
    private TableColumn<Person, String> completeDateColumn;
    @FXML
    private TableColumn<Person, String> titleColumn;

    private Main main;

    public PersonOverviewController() {}

    @FXML
    private void handleDeleteAction() {
        Person selectedPerson = personTableView.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            personTableView.getItems().remove(selectedPerson);

            PersonEntity personEntity = new PersonEntity(selectedPerson);
            RequestUtil requestUtil = new RequestUtil("/persons/" + personEntity.getId(), "DELETE");
            requestUtil.run();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Ничего не выбрано!");
            alert.setHeaderText("Пользователь не выбран!");
            alert.setContentText("Пожалуйста, кого-нибудь выберите!");
            alert.showAndWait();
        }
    }

    @FXML
    private void initialize() {
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());
        completeDateColumn.setCellValueFactory(cellData -> cellData.getValue().getCompleteDateProperty());
        showPersonDetail(null);
        personTableView.getSelectionModel().selectedItemProperty().addListener(
                ((observableValue, oldValue, newValue) -> showPersonDetail(newValue))
        );
    }

    private void showPersonDetail(Person person) {
        if (person != null) {
            title.setText(person.getTitle());
            text.setText(person.getText());
            completeDate.setText(DateUtil.format(person.getCompleteDate()));
        }
        else {
            title.setText("");
            text.setText("");
            completeDate.setText("");
        }
    }

    @FXML
    private void handleNewPerson() {
        Person tempPerson = new Person();
        boolean okClicked = main.showPersonEditDialog(tempPerson);
        if (okClicked) {
//            main.getPersonData().add(tempPerson);

            RequestUtil requestUtil = new RequestUtil("/tasks", "POST");
            PersonEntity personEntity = new PersonEntity(tempPerson);
            requestUtil.setJson(new Gson().toJson(personEntity));
            requestUtil.run();
            Gson gson = new Gson();

            PersonEntity personResp = gson.fromJson(requestUtil.getResponse(), PersonEntity.class);
            Person newPerson = new Person(personResp);

            main.getPersonData().add(newPerson);
        }
    }

    @FXML
    public void syncPersonData() {
        main.getPersonData().clear();

        RequestUtil requestUtil = new RequestUtil("/tasks", "GET");
        requestUtil.run();
        Gson gson = new Gson();
        List<PersonEntity> personEntities = gson.fromJson(requestUtil.getResponse(),
                TypeToken.getParameterized(List.class, PersonEntity.class).getType());

        if (personEntities != null) {
            for (PersonEntity personEntity : personEntities) {
                Person person = new Person(personEntity);
                main.getPersonData().add(person);
            }
        }
    }

    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTableView.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = main.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetail(selectedPerson);

                PersonEntity personEntity = new PersonEntity(selectedPerson);
                RequestUtil requestUtil = new RequestUtil("/tasks/" + personEntity.getId(), "PUT");
                requestUtil.setJson(new Gson().toJson(personEntity));
                requestUtil.run();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Ничего не выбрано!");
            alert.setHeaderText("Пользователь не выбран!");
            alert.setContentText("Пожалуйста, кого-нибудь выберите!");
            alert.showAndWait();
        }
    }

    public void setMainApp(Main main) {
        this.main = main;
        personTableView.setItems(this.main.getPersonData());
    }
}
