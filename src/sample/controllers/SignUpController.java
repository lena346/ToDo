package sample.controllers;

import sample.utils.RequestUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jdk.jfr.Event;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class SignUpController {
    @FXML
    private URL location;

    @FXML
    private TextField SignUpFirstName;

    @FXML
    private TextField SignUpSecondName;

    @FXML
    private TextField SignUpLoginfield;


    @FXML
    private Button SignUpButton;

    @FXML
    private TextField SignUpPassword;

    @FXML
    private Label SignUpLabel;

    @FXML
    void initialize() {

    }

    /**
     *  sign up action
     *
     * @param event event
     * @throws IOException java.io. i o exception
     */
    @FXML
    public void SignUpAction(Event event) throws IOException {
        if (!SignUpFirstName.getText().isBlank() && !SignUpSecondName.getText().isBlank() &&
                !SignUpLoginfield.getText().isBlank()  &&
                !SignUpPassword.getText().isBlank()) {
            //отправление данных на сервер для записывания в бд
            String first_name = SignUpFirstName.getText();
            String second_name = SignUpSecondName.getText();
            String email = SignUpLoginfield.getText();
            String password = SignUpPassword.getText();
            Map<String, Object> regResult = RequestUtil.registration(first_name, second_name, email, password);

            //после регистрации переход на вход
            if (regResult.containsValue(null)){
                SignUpLabel.setText("Вы ввели некорректные данные");
            }
            else {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Scene scene = new Scene((FXMLLoader.load((getClass().getResource("/views/SignIn.fxml")))));
                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                app_stage.setScene(scene);
                app_stage.show();
            }
        }
        else {
            SignUpLabel.setText("Не все поля заполнены");
        }

    }
    /**
     *  entre action
     *
     * @param actionEvent actionEvent
     * @throws IOException java.io. i o exception
     */
    @FXML
    public void EntreAction(Event actionEvent) throws IOException{
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene((FXMLLoader.load((getClass().getResource("/views/SignIn.fxml")))));
        Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        app_stage.setScene(scene);
        app_stage.show();
    }

}
