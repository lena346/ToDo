package sample.controllers;

import javafx.fxml.FXML;

public abstract class PersonController<T> {
    @FXML
    protected T title ;
    @FXML
    protected T text;
    @FXML
    protected T completeDate;


    public T getTitle() {
        return title;
    }

    public void setTitle(T title) {
        this.title = title;
    }

    public T getText() {
        return text;
    }

    public void setText(T text) {
        this.text = text;
    }

    public T getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(T completeDate) {
        this.completeDate = completeDate;
    }

}
