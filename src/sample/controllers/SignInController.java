package sample.controllers;
import sample.Main;
import sample.utils.RequestUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInController<Gson> {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button SignInButton;

    @FXML
    private TextField SignInloginfield;

    @FXML
    private PasswordField SgnInPassword;

    @FXML
    private Button SignUpButton;

    @FXML
    private Label LabelPassword;

    @FXML
    void initialize(URL url, ResourceBundle resources) {
    }

    /**
     * switch to sign up
     *
     * @param event event
     * @throws IOException java.io. i o exception
     */
    @FXML
    public void switchToSignUp(ActionEvent event) throws IOException {
        Parent enter_page = FXMLLoader.load(getClass().getResource("/views/SignUp.fxml"));
        Scene enter_page_scene = new Scene(enter_page);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(enter_page_scene);
        app_stage.show();

    }


    /**
     *  sign in action
     *
     * @param actionEvent actionEvent
     * @throws IOException java.io. i o exception
     */
    public void SignInAction(ActionEvent actionEvent) throws IOException {

        if (!SignInloginfield.getText().isBlank() && !SgnInPassword.getText().isBlank()) {
            String email = SignInloginfield.getText();
            String password = SgnInPassword.getText();
            Main.setUser(RequestUtil.auth(email, password));

            if (Main.getUser() != null) {
                Node node = (Node) actionEvent.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Scene scene = new Scene((FXMLLoader.load((getClass().getResource("/views/Account.fxml")))));
                Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                app_stage.setScene(scene);
                app_stage.show();
                //LabelPassword.setText("Неправильный email или пароль");
            }
            else{
                LabelPassword.setText("Неправильный email или пароль");
            }
        }
        else {
            LabelPassword.setText("Не все поля заполнены");
        }
    }
}
