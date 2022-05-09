package EgzaminoVertinimoSistema.Exam;

import EgzaminoVertinimoSistema.Exam.Exception.ExamException;
import EgzaminoVertinimoSistema.Users.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;


import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class ExamDataBase {
    private List<Exam> exams = new ArrayList<>();
    private List<User> grades = new ArrayList<>();
    private File file = new File("exams.json");
    private File gradeFile = new File("StudentGrades.json");
    private ObjectMapper mapper = new ObjectMapper();
    private SimpleModule module = new SimpleModule();
    private int countCorrectAnswers = 0;


    public void createNewExam(Exam exam) throws IOException {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        if (!file.exists()) {
            file.createNewFile();
        }
        exams.add(exam);
        mapper.writeValue(file, exams);
    }

    public Exam selectExamByTitle(String examName) throws ExamException {
        exams = getAllExams();
        return exams.stream().filter(e -> e.getTitle().equals(examName))
                .findFirst().orElseThrow(() -> new ExamException("Blogas egzamino pavadinimas"));

    }

    private List<Exam> getAllExams() {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.length() != 0) {
                return mapper.readValue(file, new TypeReference<>() {
                });
            }
        } catch (IOException e) {
            e.printStackTrace();//arba logger
        }

        return List.of();
    }

    public void showQuestion(Exam exam, Scanner scanner, User user) {

        List<Qeustions> questions = new ArrayList<>(exam.getExams());
        for (Qeustions qeustion : questions) {
            System.out.printf("%s\n", qeustion.getQuestion());
            for (String q : qeustion.getAnswers()) {
                System.out.println(q);
            }
            System.out.print("Jusu atsakymas: ");
            String answer = scanner.nextLine();
            if (qeustion.getCorrectAnswer().equals(answer)) {
                countCorrectAnswers++;
                user.setGrade(countCorrectAnswers);
            }
        }
        // grades.add(testTitle);
        addToListGrades(user);
    }

    private void addToListGrades(User user) {

        LocalDateTime dataStart = LocalDateTime.now();
        LocalDateTime dataStop = dataStart.plusHours(48);
        String dataNow = dataStart.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String dataAfter48H = dataStop.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        grades.add(new User(user.getId(), null, null, user.getName(), user.getSurname(),
                user.getRole(), dataNow, dataAfter48H, user.getGrade()));
        writeFileGrades();
    }

    public List<User> getAllStudentGrade() {
        try {
            if (gradeFile.length() != 0) {
                return mapper.readValue(gradeFile, new TypeReference<>() {
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return List.of();
    }

    private void writeFileGrades() {
        try {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            if (!gradeFile.exists()) {
                gradeFile.createNewFile();
            }
            mapper.writeValue(gradeFile, grades);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showGrade(User user) {
        grades = getAllStudentGrade();
        if (user.getGrade() < 4) {
            System.out.printf("Neislaikyta, jusu pazymis: %s \n", user.getGrade());
        } else {
            System.out.printf("jusu pazymis : %s \n", user.getGrade());
        }
    }
}

