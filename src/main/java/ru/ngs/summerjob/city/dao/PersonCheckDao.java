package ru.ngs.summerjob.city.dao;

import ru.ngs.summerjob.city.domain.PersonRequest;
import ru.ngs.summerjob.city.domain.PersonResponse;
import ru.ngs.summerjob.city.exception.PersonCheckException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonCheckDao {
    private static final String SQL_REQUEST = """
            SELECT temporal FROM cr_address_person ap
            INNER JOIN cr_person p on p.person_id = ap.person_id
            INNER JOIN cr_address a on a.address_id = ap.address_id
            WHERE\s
            UPPER(p.sur_name) = UPPER(?)\s
            and UPPER(p.given_name) = UPPER(?)\s
            and UPPER(p.patronymic_name) = UPPER(?)
            and p.date_of_birth = ?
            and a.street_code = ? and UPPER(a.building) = UPPER(?)\s
            and UPPER(a.extension) = UPPER(?) and UPPER(a.apartment) = UPPER(?)""";

    public PersonResponse checkPerson(PersonRequest request) throws PersonCheckException {
        PersonResponse response = new PersonResponse();

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_REQUEST)) {
            ResultSet rs = stmt.executeQuery();//выполняем запрос
            if (rs.next()) {
                response.setRegistered(true);
                response.setTemporal(rs.getBoolean("temporal"));
            }

        } catch (SQLException e) {
            throw new PersonCheckException(e);
        }

        return response;
    }

    private Connection getConnection() throws SQLException {
        return ConnectionBuilder.getConnection();
    }

}
