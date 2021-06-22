package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.controllers.PersonEditDialogController;
import sample.controllers.PersonOverviewController;
import sample.models.Person;
import sample.models.User;

import java.io.IOException;

public class Main extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private final ObservableList<Person> personData = FXCollections.observableArrayList();

    public BorderPane getRootLayout() { return rootLayout; }

    public Stage getPrimaryStage() { return primaryStage; }

    public ObservableList<Person> getPersonData() { return personData; }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Todo");
        showSignUpOverview();
        primaryStage.show();
        //this.primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));
        initRootLayout();
        showPersonOverview();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/RootLayout.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);

            primaryStage.setScene(scene);

            primaryStage.show();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public boolean showPersonEditDialog(Person person) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialStage = new Stage();
            dialStage.setTitle("Изменение пользователя");
            //dialStage.getIcons().add(new Image("file:resources/images/icon.png"));
            dialStage.initModality(Modality.WINDOW_MODAL);
            dialStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialStage.setScene(scene);

            PersonEditDialogController controller = loader.getController();
            controller.setDialStage(dialStage);
            controller.setPerson(person);
            dialStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showPersonOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/MainScene.fxml"));
            AnchorPane personOverview = loader.load();
            rootLayout.setCenter(personOverview);
            PersonOverviewController controller = loader.getController();

            //передаем данные из personData
            controller.setMainApp(this);
            controller.syncPersonData();

        } catch (IOException e) { e.printStackTrace(); }
    }
    private static User user;
    public static User getUser() {
        return user;
    }
    private static User[] users;
    public static User[] getUsers() {
        return users;
    }
    public void showSignUpOverview() throws IOException {
        Parent root = FXMLLoader.load(getClass()).getResource("/views/SignUp.fxml");
        Scene signUpScene = new Scene(root);
        primaryStage.setScene(signUpScene);
    }
    public static void setUser(User user) {
        Main.user = user;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
