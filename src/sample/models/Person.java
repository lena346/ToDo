package sample.models;

import javafx.beans.property.*;
import sample.requests.models.PersonEntity;
import sample.utils.DateUtil;

import java.time.LocalDate;

public class Person {
    private final Integer id;
    private final StringProperty title;
    private final StringProperty text;
    private final ObjectProperty<LocalDate> completeDate;

    public Person(Integer id, String title, String text, LocalDate completeDate) {
        this.id = id;
        this.title = new SimpleStringProperty(title);
        this.text = new SimpleStringProperty(text);
        this.completeDate = new SimpleObjectProperty<>(completeDate);
    }

    public Person(PersonEntity personEntity) {
        this(personEntity.getId(),
                personEntity.getTitle(),
                personEntity.getText(),
                DateUtil.parse(personEntity.getCompleteDate()));
    }

    public Person() {
        this(null,null, null, null);
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() { return title.get(); }

    public StringProperty getTitleProperty() { return title; }

    public String getText() { return text.get(); }

    public StringProperty getTextProperty() { return text; }

    public ObjectProperty<LocalDate> getCompleteDateProperty() { return completeDate; }

    public LocalDate getCompleteDate() { return completeDate.get(); }

    public void setTitle(String title) { this.title.set(title); }

    public void setText(String text) { this.text.set(text); }

    public void setCompleteDate(LocalDate completeDate) { this.completeDate.set(completeDate); }

}
