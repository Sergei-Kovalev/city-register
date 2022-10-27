package ru.ngs.summerjob.city.web;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ngs.summerjob.city.dao.PersonCheckDao;
import ru.ngs.summerjob.city.domain.PersonRequest;
import ru.ngs.summerjob.city.domain.PersonResponse;
import ru.ngs.summerjob.city.exception.PersonCheckException;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "CheckPersonServlet", urlPatterns = {"/checkPerson"})
public class CheckPersonServlet extends HttpServlet {

    private final static Logger logger = LoggerFactory.getLogger(CheckPersonServlet.class);

    private PersonCheckDao dao;



    @Override
    public void init() throws ServletException {
        logger.info("Servlet is created");
        dao = new PersonCheckDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        String surName = req.getParameter("surname");

        PersonRequest pr = new PersonRequest();
        pr.setSurName(surName);
        pr.setGivenName("Pavel");
        pr.setPatronymic("Nikolaevich");
        pr.setDateOfBirth(LocalDate.of(1995, 3, 18));
        pr.setStreetCode(1);
        pr.setBuilding("10");
        pr.setExtension("2");
        pr.setApartment("121");

        try {
            PersonResponse ps = dao.checkPerson(pr);
            if (ps.isRegistered()) {
                resp.getWriter().write("Registered!!!");
            } else {
                resp.getWriter().write("Not registered");
            }
        } catch (PersonCheckException e) {
            e.printStackTrace();
        }


    }
}
