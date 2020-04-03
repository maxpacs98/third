package org.example;

import domain.Student;
import domain.Tema;
import org.junit.Before;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import java.util.UUID;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    private Service service;

    @Test
    public void shouldAnswerWithTrue() {
        System.out.println(UUID.randomUUID().toString());
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
        Student student = new Student(UUID.randomUUID().toString(), "Mama", 931, "alexandrubodea@gmail.com");
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
        Student student = new Student(UUID.randomUUID().toString(), "Mama", -931, "alexandrubodea@gmail.com");
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

        Tema tema = new Tema(UUID.randomUUID().toString(),"Tema1", 14, 10);
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

        Tema tema = new Tema(UUID.randomUUID().toString(),"Tema2", 19, 10);
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

    @Before
    public void setUp() {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/Studenti.xml";
        String filenameTema = "fisiere/Teme.xml";
        String filenameNota = "fisiere/Note.xml";

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        this.service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @Test
    public void addValidStudentRedundant() {
        Student student = new Student(UUID.randomUUID().toString(), "Bodea Alexandru", 931, "alexandrubodeag@gmail.com");
        Student res = service.addStudent(student);
        assert student == res;
    }

    @Test
    public void addValidStudent() {
        Student student = new Student(UUID.randomUUID().toString(), "Bodea Alexandru", 931, "alexandrubodeag@gmail.com");
        int studentCount = 0;
        for (Student ignored : service.getAllStudenti()) {
            studentCount++;
        }
        service.addStudent(student);

        int resultCount = 0;
        for (Student ignored : service.getAllStudenti()) {
            resultCount++;
        }
        assert resultCount == studentCount + 1;
    }

    @Test
    public void addStudentEmptyId() {
        Student student = new Student("", "Bodea Alexandru", 931, "alexandrubodeag@gmail.com");
        try {
            service.addStudent(student);
            assert false;
        }
        catch (ValidationException e) {
            assert e.getMessage().equals("Id incorect!");
        }
    }

    @Test
    public void addStudentNullId() {
        Student student = new Student(null, "Bodea Alexandru", 931, "alexandrubodeag@gmail.com");
        try {
            service.addStudent(student);
            assert false;
        }
        catch (ValidationException e) {
            assert e.getMessage().equals("Id incorect!");
        }
    }

    @Test
    public void addStudentNotUnique() {
        Student student = new Student("1", "Bodea Alexandru", 931, "alexandrubodeag@gmail.com");
        try {
            service.addStudent(student);
            assert false;
        }
        catch (ValidationException e) {
            assert e.getMessage().equals("Id not unique");
        }
    }

    @Test
    public void addStudentEmptyName() {
        Student student = new Student(UUID.randomUUID().toString(), "", 931, "alexandrubodeag@gmail.com");
        try {
            service.addStudent(student);
            assert false;
        }
        catch (ValidationException e) {
            assert e.getMessage().equals("Nume incorect!");
        }
    }

    @Test
    public void addStudentNullName() {
        Student student = new Student(UUID.randomUUID().toString(), null, 931, "alexandrubodeag@gmail.com");
        try {
            service.addStudent(student);
            assert false;
        }
        catch (ValidationException e) {
            assert e.getMessage().equals("Nume incorect!");
        }
    }

    @Test
    public void addStudentNullEmail() {
        Student student = new Student(UUID.randomUUID().toString(), "Bodea Alexandru", 931, null);
        try {
            service.addStudent(student);
            assert false;
        }
        catch (ValidationException e) {
            assert e.getMessage().equals("Email incorect!");
        }
    }

    @Test
    public void addStudentEmptyEmail() {
        Student student = new Student(UUID.randomUUID().toString(), "Bodea Alexandru", 931, "");
        try {
            service.addStudent(student);
            assert false;
        }
        catch (ValidationException e) {
            assert e.getMessage().equals("Email incorect!");
        }
    }

    @Test
    public void addStudentInvalidEmail() {
        Student student = new Student(UUID.randomUUID().toString(), "Bodea Alexandru", 931, "invalid.com");
        try {
            service.addStudent(student);
            assert false;
        }
        catch (ValidationException e) {
            assert e.getMessage().equals("Email is of incorrect form!");
        }
    }

}
