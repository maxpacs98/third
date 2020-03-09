package org.example;

import static org.junit.Assert.assertTrue;

import domain.Student;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import java.util.Iterator;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */

    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    public void addStudentCorrectGroup() {
//        StudentXMLRepo studentXMLRepository = new StudentXMLRepo("fisiere/Studenti.xml");
//        Service service = new Service()
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/Studenti.xml";
        String filenameTema = "fisiere/Teme.xml";
        String filenameNota = "fisiere/Note.xml";

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        Service service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        Student student = new Student("id1", "Mama", 931, "alexandrubodea@gmail.com");

        Student result = service.addStudent(student);
        assert result.getGrupa() == student.getGrupa();
    }

    @Test
    public void addStudentIncorrectGroup() {
//        StudentXMLRepo studentXMLRepository = new StudentXMLRepo("fisiere/Studenti.xml");
//        Service service = new Service()
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/Studenti.xml";
        String filenameTema = "fisiere/Teme.xml";
        String filenameNota = "fisiere/Note.xml";

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        Service service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        Student student = new Student("id1", "Mama", -931, "alexandrubodea@gmail.com");
        int studentCount = 0;
        for (Student value : studentXMLRepository.findAll()) {
            studentCount++;
        }
        try {
            service.addStudent(student);
        } catch (ValidationException e) {
            int studentCountResult = 0;
            for (Student value : studentXMLRepository.findAll()) {
                studentCountResult++;
            }
            assert studentCountResult == studentCount;
        }
    }
}
