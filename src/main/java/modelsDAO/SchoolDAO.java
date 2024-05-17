package modelsDAO;

import connections.Conector;
import models.School;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SchoolDAO {

    /**
     * Método utilizado para crear un objeto School a partir de un ID
     * @author Ricardo
     * @param idSchool
     * @return School con los datos correspondientes al ID del parámetro
     */
    public static School createSchool(int idSchool){
        School school = new School();
        Conector conector = new Conector();
        Connection con = null;

        try {
            con = conector.getMySqlConnection();
            if (con != null) {
                PreparedStatement sentencia = con.prepareStatement("SELECT * FROM school where id="+idSchool);
                ResultSet rs = sentencia.executeQuery();
                while (rs.next()) {
                    school.setIdSchool(rs.getInt(1));
                    school.setSchoolName(rs.getString(2));
                    school.setTlfSchool(rs.getString(3));
                    school.setEmail(rs.getString(4));
                    school.setScheduleSchool(rs.getString(5));
                    school.setLocSchool(rs.getString(6));
                    school.setMapLink(rs.getString(7));
                }

            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return school;
    }

    /**
     * Método utilizado para enseñar todos los centros en el registro
     * @author Ricardo
     * @return	List con todos los School
     */
    public static List<School> getAllSchools() {
        List<School> schools = new ArrayList<>();
        Connection con = null;

        try {
            con = new Conector().getMySqlConnection();
            try {
                PreparedStatement ps = con.prepareStatement("select * from school");
                ResultSet result = ps.executeQuery();
                while (result.next()) {
                    int id = result.getInt("id");
                    String name = result.getString("school_name");
                    String telephone = result.getString("tel");
                    String email = result.getString("email");
                    String secretarySchedule = result.getString("secretarySchedule");
                    String location = result.getString("loc");
                    String mapLink = result.getString("mapLink");
                    schools.add(new School(id, name, telephone, email, secretarySchedule, location, mapLink));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return schools;
    }
}
