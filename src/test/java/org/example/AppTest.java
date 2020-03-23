package org.example;

import domain.Student;
import domain.Tema;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import static org.junit.Assert.assertTrue;

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

    @Test
    public void addAssignmentCorrectDeadline() {
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

        Tema tema = new Tema("1292512322","Tema1", 14, 10);
        int assCount = 0;
        for (Tema value : temaXMLRepository.findAll()) {
            assCount++;
        }
        service.addTema(tema);
        int assCountResult = 0;
        for (Tema value : temaXMLRepository.findAll()) {
           assCountResult++;
        }
        assert assCount + 1 == assCountResult;
        service.deleteNota("1292512322");

    }

    @Test
    public void addAssignmentIncorrectDeadline() {
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

        Tema tema = new Tema("12923i2","Tema2", 19, 10);
        int assCount = 0;
        for (Tema ignored : temaXMLRepository.findAll()) {
            assCount++;
        }
        try {
            service.addTema(tema);
        }
        catch (ValidationException e) {
            int assCountResult = 0;
            for (Tema ignored : temaXMLRepository.findAll()) {
                assCountResult++;
            }
            assert assCount == assCountResult;
            service.deleteNota("12923i2");
        }
    }
}
