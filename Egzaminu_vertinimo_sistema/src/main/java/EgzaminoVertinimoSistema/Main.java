package EgzaminoVertinimoSistema;

import EgzaminoVertinimoSistema.Enum.ExamType;
import EgzaminoVertinimoSistema.Exam.Exam;
import EgzaminoVertinimoSistema.Exam.ExamDataBase;
import EgzaminoVertinimoSistema.Exam.Qeustions;
import EgzaminoVertinimoSistema.Exam.Exception.ExamException;
import EgzaminoVertinimoSistema.Exam.Exception.UserException;
import EgzaminoVertinimoSistema.Users.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


public class Main {
    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();
        Scanner scanner = new Scanner(System.in);
        Main main = new Main();
        DataBase dataBase = new DataBase();
        ExamDataBase examDataBase = new ExamDataBase();


        main.menuAction(main, scanner, dataBase, examDataBase);

    }

    public void menuAction(Main main, Scanner scanner, DataBase dataBase, ExamDataBase examDataBase) {
        String action;
        do {
            main.menu();
            action = scanner.nextLine();
            switch (action) {
                case "1" -> main.prisijungimas(scanner, main, dataBase, examDataBase);
                case "2" -> main.registracija(scanner, dataBase);
                case "3" -> System.out.println("Exisiting");
                default -> System.out.println("Neteisinga ivestis!");
            }
        } while (action.equals("3"));
    }

    public void prisijungimas(Scanner scanner, Main main, DataBase dataBase, ExamDataBase examDataBase) {
        try {
            System.out.print("Log in name: ");
            String logInName = scanner.nextLine();

            System.out.print("Slaptazodis: ");
            String logInPsw = scanner.nextLine();


            User user = dataBase.getUser(logInName, logInPsw);
            if (user.getRole().equals("studentas")) {
                System.out.println("prisijungete kaip studentas");
                studentMenuAction(scanner, main, dataBase, examDataBase, user);

            } else if (user.getRole().equals("destytojas")) {
                System.out.println("prisijungete kaip destytojas");
                lectureMenuAction(scanner, main, dataBase, examDataBase);

            }
        } catch (UserException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Neteisinga ivestis!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExamException e) {
            throw new RuntimeException(e);
        }
    }

    void registracija(Scanner scanner, DataBase dataBase) {

        Random random = new Random();
        int id = random.nextInt(1000) + 1;

        System.out.print("Login name: ");
        String logInName = scanner.nextLine();

        System.out.print("Slaptazodis: ");
        String psw = scanner.nextLine();

        System.out.print("Vardas: ");
        String name = scanner.nextLine();

        System.out.print("Pavarde: ");
        String surname = scanner.nextLine();

        System.out.print("Pasirinkite, ir parasykite: studentas ar destytojas: ");
        String role = scanner.nextLine();

        try {
            dataBase.addNewUser(new User(id, logInName, psw, name, surname, role, null, null, 0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (UserException e) {
            System.out.println(e.getMessage());
        }
    }

    private void studentMenuAction(Scanner scanner, Main main, DataBase dataBase, ExamDataBase examDataBase, User user) throws IOException, ExamException {

        String input = "";
        while (!(input.equals("4"))) {
            main.studentoMenu();
            input = scanner.nextLine();
            switch (input) {
                case "1" -> main.startEgzam(scanner, examDataBase, user);
                case "2" -> main.retakeEgzam(examDataBase, scanner, user);
                case "3" -> main.getGrade(examDataBase, user);
                case "4" -> {
                    System.out.println("Exisiting");
                    main.menuAction(main, scanner, dataBase, examDataBase);
                }
                default -> System.out.println("Neteisinga ivestis!");
            }
        }
    }

    private void startEgzam(Scanner scanner, ExamDataBase examDataBase, User user) throws ExamException {
        System.out.print("Koki egzamina norite laikyti? Iveskite jo pavadinima: ");
        String select = scanner.nextLine();

        Exam exam = examDataBase.selectExamByTitle(select);
        examDataBase.showQuestion(exam, scanner, user);
    }

    private void retakeEgzam(ExamDataBase examDataBase, Scanner scanner, User user) throws ExamException {
        List<User> usersgrades = examDataBase.getAllStudentGrade();
        LocalDateTime date = LocalDateTime.now().plusHours(48);

        for (User userTime : usersgrades) {
            if (date.equals(userTime.getDateStop())) {
                startEgzam(scanner, examDataBase, user);
            } else {
                System.out.println("Jus dar negalite perlaikyti egzamino! Dar nepraejo 48 valandos");
            }
        }
    }

    private void getGrade(ExamDataBase examDataBase, User user) {
        examDataBase.showGrade(user);
    }

    private void lectureMenuAction(Scanner scanner, Main main, DataBase dataBase, ExamDataBase examDataBase) throws IOException {
        String input = "";
        while (!input.equals("4")) {
            main.lectureMenu();
            input = scanner.nextLine();
            switch (input) {
                case "1" -> main.addNewEgzam(scanner, examDataBase);
                case "2" -> main.getStudentGrade(scanner, examDataBase);
                case "3" -> main.editEgzam();
                case "4" -> {
                    System.out.println("Exisiting");
                    main.menuAction(main, scanner, dataBase, examDataBase);
                }
                default -> System.out.println("Neteisinga ivestis!");
            }
        }
    }

    private void addNewEgzam(Scanner scanner, ExamDataBase examDataBase) throws IOException {
        Random random = new Random();
        int id = random.nextInt(1000) + 1;
        try {
            System.out.print("Egzamino pavadinimas: ");
            String title = scanner.nextLine();

            System.out.print("Egzamino tipas: ");
            String type = scanner.nextLine();

            System.out.print("Kiek bus klausymu? ");
            int number = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < number; i++) {
                System.out.printf("Iveskite %d klausyma: ", i + 1);
                String question = scanner.nextLine();

                System.out.print("Kiek bus atsakymu variantu? ");
                int answersCount = scanner.nextInt();
                scanner.nextLine();

                List<String> answers = getAnswers(scanner, answersCount);

                System.out.print("Koks teisingas atsakymas? ");
                String correctAnswer = scanner.nextLine();

                examDataBase.createNewExam(new Exam(id, title, ExamType.valueOf(type),List.of(new Qeustions(question,answers,correctAnswer)),null));
            }

        } catch (InputMismatchException e) {
            System.out.println("Neteisinga ivestis!");
        }

    }

    public List<String> getAnswers(Scanner scanner, int answersCount) {
        List<String> allAnswers = new ArrayList<>();
        try {
            for (int j = 0; j < answersCount; j++) {
                System.out.printf("Iveskite %d atsakyma: ", j + 1);
                String answers = scanner.nextLine();
                allAnswers.add(answers);
            }
        } catch (InputMismatchException e) {
            System.out.println("Neteisinga ivestis");
        }

        return allAnswers;
    }

    private void getStudentGrade(Scanner scanner, ExamDataBase examDataBase) {
        System.out.print("Iveskite studento varda: ");
        String studentName = scanner.nextLine();

        System.out.print("Iveskite studento pavarde: ");
        String studentSurname = scanner.nextLine();

        List<User> grades = examDataBase.getAllStudentGrade();
        for (User user : grades) {
            if (studentName.equals(user.getName()) && studentSurname.equals(user.getSurname())) {
                System.out.printf("%s %s studendo pazymis %s\n", user.getName(), user.getSurname(), user.getGrade());
            }
        }
    }

    private void editEgzam() {

    }

    private void menu() {
        System.out.println("""
                1 - Prisijungti
                2 - Registruotis
                3 - Exit
                """);
    }

    private void studentoMenu() {
        System.out.println("""
                1 - Egzamino laikymas
                2 - Perlaikymas
                3 - Perziurete ivertinimus
                4 - Exit
                 """);
    }

    private void lectureMenu() {
        System.out.println("""
                1 - Sukurti nauja egzamina
                2 - Perziureti studento ivertinimus
                3 - Redaguoti egzamina
                4 - Exit
                """);
    }
}
