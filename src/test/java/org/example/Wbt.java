package org.example;

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

public class Wbt {

    Service service;

    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
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
    public void addAssignmentInvalidId() {
        Tema tema = new Tema(null, "Cea mai tema", 12, 10);
        try {
            service.addTema(tema);
            assert false;
        }
        catch (ValidationException e) {
            assert e.getMessage().equals("Numar tema invalid!");
        }
    }

    @Test
    public void addAssignmentInvalidDescription() {
        Tema tema = new Tema(UUID.randomUUID().toString(), "", 12, 10);
        try {
            service.addTema(tema);
            assert false;
        }
        catch (ValidationException e) {
            assert e.getMessage().equals("Descriere invalida!");
        }
    }

    @Test
    public void addAssignmentInvalidDeadline() {
        Tema tema = new Tema(UUID.randomUUID().toString(), "Descriere valida", 15, 10);
        try {
            service.addTema(tema);
            assert false;
        }
        catch (ValidationException e) {
            assert e.getMessage().equals("Deadlineul trebuie sa fie intre 1-14.");
        }
    }

    @Test
    public void addAssignmentInvalidReceivingDate() {
        Tema tema = new Tema(UUID.randomUUID().toString(), "Descriere valida", 12, 15);
        try {
            service.addTema(tema);
            assert false;
        }
        catch (ValidationException e) {
            assert e.getMessage().equals("Saptamana primirii trebuie sa fie intre 1-14.");
        }
    }

    @Test
    public void addValidAssignment() {
        Tema tema = new Tema(UUID.randomUUID().toString(), "Descriere valida", 13, 10);
        int assCount = 0;
        for (Tema ignored : service.getAllTeme()) {
            assCount++;
        }
        service.addTema(tema);

        int resultCount = 0;
        for (Tema ignored : service.getAllTeme()) {
            resultCount++;
        }
        assert resultCount == assCount + 1;
    }

    @Test
    public void addExistingAssignment() {
        Tema tema = new Tema("25", "Descriere valida", 13, 10);
        int assCount = 0;
        for (Tema ignored : service.getAllTeme()) {
            assCount++;
        }
        service.addTema(tema);

        int resultCount = 0;
        for (Tema ignored : service.getAllTeme()) {
            resultCount++;
        }
        assert resultCount == assCount;
    }
}
