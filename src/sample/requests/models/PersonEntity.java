package sample.requests.models;

import sample.models.Person;
import sample.utils.DateUtil;

public class PersonEntity {
    private Integer id;
    private String title;
    private String  text;
    private String completeDate;

    public PersonEntity(Integer id, String title, String text, String completeDate) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.completeDate = completeDate;
    }

    public PersonEntity(Person person) {

        id = person.getId();
        title = person.getTitle();
        text = person.getText();
        completeDate = DateUtil.format(person.getCompleteDate());
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    @Override
    public String toString() {
        return "PersonReg{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", completeDate='" + completeDate + '\'' +
                '}';
    }
}
