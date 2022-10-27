package ru.ngs.summerjob.city.dao;

import ru.ngs.summerjob.city.domain.PersonRequest;
import ru.ngs.summerjob.city.domain.PersonResponse;
import ru.ngs.summerjob.city.exception.PersonCheckException;

import java.sql.*;

public class PersonCheckDao {
    private static final String SQL_REQUEST = """
            SELECT temporal FROM cr_address_person ap
            INNER JOIN cr_person p on p.person_id = ap.person_id
            INNER JOIN cr_address a on a.address_id = ap.address_id
            WHERE\s
            CURRENT_DATE >= ap.start_date AND (CURRENT_DATE <= ap.end_date OR ap.end_date is null)
            and UPPER(p.sur_name) = UPPER(?)\s
            and UPPER(p.given_name) = UPPER(?)\s
            and UPPER(p.patronymic_name) = UPPER(?)
            and p.date_of_birth = ?
            and a.street_code = ? and UPPER(a.building) = UPPER(?)\s""";

    public PersonCheckDao() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PersonResponse checkPerson(PersonRequest request) throws PersonCheckException {
        PersonResponse response = new PersonResponse();

        String sql = SQL_REQUEST;
        if (request.getExtension() != null) {
            sql += """
                    and UPPER(a.extension) = UPPER(?)\s""";
        } else {
            sql += """
                    and a.extension is null\s""";
        }
        if (request.getApartment() != null) {
            sql += """
                    and UPPER(a.apartment) = UPPER(?)""";
        } else {
            sql += """
                    and a.apartment is null""";
        }

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            int count = 1;
            stmt.setString(count++, request.getSurName());
            stmt.setString(count++, request.getGivenName());
            stmt.setString(count++, request.getPatronymic());
            stmt.setDate(count++, Date.valueOf(request.getDateOfBirth()));
            stmt.setInt(count++, request.getStreetCode());
            stmt.setString(count++, request.getBuilding());
            if (request.getExtension() != null) {
                stmt.setString(count++, request.getExtension());
            }
            if (request.getApartment() != null) {
                stmt.setString(count, request.getApartment());
            }

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
