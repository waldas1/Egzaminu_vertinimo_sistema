package EgzaminoVertinimoSistema;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

import EgzaminoVertinimoSistema.Exam.ExamDataBase;

import EgzaminoVertinimoSistema.Users.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;


@ExtendWith({MockitoExtension.class})
class MainTest {

    @Mock
    private Main main;
    @Mock
    private User user;
    @Mock
    private DataBase dataBase;
    @Mock
    private ExamDataBase examDataBase;
    private UUID id;
    @Mock
    private Scanner scanner;
    @Mock
    private ObjectMapper obj;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
    }

    @Test
    void testPrisijungimasWhenThrowException() {
        Mockito.doThrow(InputMismatchException.class).when(scanner.nextLine());

        assertThrows(InputMismatchException.class, () -> main.prisijungimas(scanner, main, dataBase, examDataBase));
    }
    @Test
    void testRegistracijaWhenThrowException(){
        Mockito.doThrow(InputMismatchException.class).when(scanner.nextLine());

        assertThrows(InputMismatchException.class, () -> main.registracija(scanner,dataBase));
    }
    @Test
    void testGetAnswersWhenReturnEmptyList(){
        Mockito.when(main.getAnswers(scanner,0)).thenReturn(null);
       // boolean isListIsempty =
    }
}