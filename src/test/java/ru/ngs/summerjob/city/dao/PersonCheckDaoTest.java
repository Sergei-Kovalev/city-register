package ru.ngs.summerjob.city.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.ngs.summerjob.city.domain.PersonRequest;
import ru.ngs.summerjob.city.domain.PersonResponse;
import ru.ngs.summerjob.city.exception.PersonCheckException;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PersonCheckDaoTest {

    @Test
    void checkPerson() throws PersonCheckException {
        PersonRequest pr = new PersonRequest();
        pr.setSurName("Vasiliev");
        pr.setGivenName("Pavel");
        pr.setPatronymic("Nikolaevich");
        pr.setDateOfBirth(LocalDate.of(1995, 3, 18));
        pr.setStreetCode(1);
        pr.setBuilding("10");
        pr.setExtension("2");
        pr.setApartment("121");

        PersonCheckDao dao = new PersonCheckDao();
        PersonResponse ps = dao.checkPerson(pr);

        Assertions.assertTrue(ps.isRegistered());
        Assertions.assertFalse(ps.isTemporal());
    }

    @Test
    void checkPerson2() throws PersonCheckException {
        PersonRequest pr = new PersonRequest();
        pr.setSurName("Vasilieva");
        pr.setGivenName("Irina");
        pr.setPatronymic("Petrovna");
        pr.setDateOfBirth(LocalDate.of(1997, 8, 21));
        pr.setStreetCode(1);
        pr.setBuilding("271");
        pr.setApartment("18");

        PersonCheckDao dao = new PersonCheckDao();
        PersonResponse ps = dao.checkPerson(pr);

        Assertions.assertTrue(ps.isRegistered());
        Assertions.assertFalse(ps.isTemporal());
    }
}