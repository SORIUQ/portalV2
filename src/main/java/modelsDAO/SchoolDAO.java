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
        int idSchoolConstr=idSchool;
        String nombreSchool="";
        String tlfSchool= "";
        String email= "";
        String scheduleSchool= "";
        String locSchool="";
        Conector conector = new Conector();
        Connection con = null;

        try {
            con = conector.getMySqlConnection();
            if (con != null) {
                String sql = "SELECT * FROM school where id="+idSchool;
                Statement sentencia = con.createStatement();

                try (ResultSet rs = sentencia.executeQuery(sql)) {
                    while (rs.next()) {
                        idSchoolConstr=rs.getInt(1);
                        nombreSchool=rs.getString(2);
                        tlfSchool= rs.getString(3);
                        email= rs.getString(4);
                        scheduleSchool= rs.getString(5);
                        locSchool=rs.getString(6);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
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

        return new School(idSchoolConstr, nombreSchool, tlfSchool, email, scheduleSchool, locSchool);
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
                    schools.add(new School(id, name, telephone, email, secretarySchedule, location));
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
