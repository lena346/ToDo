package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.models.Person;
import sample.utils.DateUtil;

public class PersonEditDialogController extends PersonController<TextField> {

    private Stage dialStage;
    private Person person;
    private boolean isOkClicked = false;

    public void setDialStage(Stage dialStage) { this.dialStage = dialStage; }

    public void setPerson(Person person) {
        this.person = person;
        title.setText(person.getTitle());
        text.setText(person.getText());
        completeDate.setText(DateUtil.format(person.getCompleteDate()));
        completeDate.setPromptText("dd.mm.yyyy");
    }

    public boolean isOkClicked() { return isOkClicked; }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setTitle(title.getText());
            person.setText(text.getText());
            person.setCompleteDate(DateUtil.parse(completeDate.getText()));
            isOkClicked = true;
            dialStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";
        if (title.getText() == null || title.getText().length() == 0) {
            errorMessage += "No valid first name!\n";
        }
        if (text.getText() == null || text.getText().length() == 0) {
            errorMessage += "No valid last name!\n";
        }
        if (completeDate.getText() == null || completeDate.getText().length() == 0) {
            errorMessage += "No valid completeDate!\n";
        } else {
            if (!DateUtil.validDate(completeDate.getText())) {
                errorMessage += "No valid completeDate. Use the format dd.mm.yyyy!\n";
            }
        }
        if (errorMessage.length() == 0) { return true; }
        else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}
