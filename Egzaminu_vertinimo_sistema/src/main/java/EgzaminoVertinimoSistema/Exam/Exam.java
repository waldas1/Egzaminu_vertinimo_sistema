package EgzaminoVertinimoSistema.Exam;

import EgzaminoVertinimoSistema.Enum.ExamType;
import EgzaminoVertinimoSistema.Users.User;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Exam {
    private int id;
    private String title;
    private ExamType type;
    private List<Qeustions> exams;
    private User user;

    public Exam() {

    }

    public Exam(int id, String title, ExamType type, List<Qeustions> exams, User user) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.exams = exams;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ExamType getType() {
        return type;
    }

    public void setType(ExamType type) {
        this.type = type;
    }

    public List<Qeustions> getExams() {
        return exams;
    }

    public void setExams(List<Qeustions> exams) {
        this.exams = exams;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", tipas=" + type +
                ", exams=" + exams +
                ", user=" + user +
                '}';
    }
}
