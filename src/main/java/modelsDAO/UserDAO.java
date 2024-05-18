package modelsDAO;

import connections.Conector;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

public class UserDAO {

    /**
     * Método utilizado para crear el susuario que hace login.
     * @author Ricardo
     * @param con
     * @param resultSet
     * @return Devuelve el usuario.
     * @throws SQLException
     */
    public static User createUser(Connection con, ResultSet resultSet) throws SQLException {
        User user = new User();

        if (con != null) {
            user.setId(resultSet.getInt("id"));
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM user_obj WHERE id = ?");
            preparedStatement.setInt(1, user.getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setUserType(resultSet.getString("user_type"));
                user.setName(resultSet.getString("user_name"));
                user.setId_school(resultSet.getInt("school_id"));
                user.setId_course((Integer) resultSet.getObject("course_id"));
            }
        }

        return user;
    }

    /**
     * Inserta un registro nuevo en la tabla de Credentials.
     * @author Ricardo
     * @param con
     * @param userEmail	email que se recoge en el registro.
     * @param userPass	contraseña que se recoge en el registro.
     */
    public static void insertCredentials(Connection con, String userEmail, String userPass) {
        // No cerramos la conexión porque este metodo se utiliza dentro de otro que si la cierra
        if (con != null) {
            try (PreparedStatement ps = con.prepareStatement("call insertUserCredentials(?,?)")) {
                ps.setString(1, userEmail);
                ps.setString(2, userPass);
                int linesModified = ps.executeUpdate();
                if (linesModified > 0);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Inserta un usuario nuevo en la BD.
     * @author Ricardo
     * @param con
     * @param name	nombre recogido en el registro.
     * @param lastName	apellidos recogidos en el registro.
     * @param birthDate	fecha de nacimiento recogida en el registro.
     * @param dnie	DNI o NIE recogido en el registro.
     * @param email email recogido en el registro.
     * @param pass	contraseña recogida en el registro.
     * @param schoolId	ID del colegio seleccionado en el registro.
     * @param courseId	ID del módulo seleccionado en el registro.
     */
    public static void insertUserInDb(Connection con, String name, String lastName, String birthDate, String dnie, String email, String pass, String schoolId, String courseId) {
        // No cerramos la conexión porque este metodo se utiliza dentro de otro que si la cierra
        if (con != null) {
            try {
                PreparedStatement ps = con.prepareStatement("call insertUser(?,?,?,?,?,?,?,?,?);");
                ps.setString(1, name);
                ps.setString(2, lastName);
                ps.setString(3, birthDate);
                ps.setString(4, dnie);
                ps.setString(5, "01");
                ps.setString(6, email);
                ps.setString(7, pass);
                // Preguntarnos si los ids la mejor forma de tratarlos sería con Strings y no ints.
                ps.setInt(8, Integer.parseInt(schoolId));
                ps.setInt(9, Integer.parseInt(courseId));
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Método utilizado en el datosPersonales.jsp devuelve los datos del usuario y los utiliza para imprimirlos.
     * @author Ricardo
     * @param id
     * @return	List con los datos del alumno ordenados.
     */
    public static List<String> getUserInfo(int id) {
        List<String> usuario = new ArrayList<>();
        Connection con = null;

        try {
            con = new Conector().getMySqlConnection();
            if (con != null) {
                PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM user_obj WHERE id = ?");
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    usuario.add(resultSet.getString("user_name"));
                    usuario.add(resultSet.getString("user_surname"));
                    usuario.add(resultSet.getString("dnie"));
                    usuario.add(resultSet.getString("birthDate"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return usuario;
    }

    /*Función provisional. Falta ver cómo avanza la bbdd para poder avanzar*/
    public static List<User> getStudentsFromTeacher (int profesorId){
        List<User> alumnos = new ArrayList<>();

        //Añadir sentencia sql para buscar los alumnos en función del id del profesor pasado como parámentro

        return alumnos;
    }
    
    public static void setNewPass (int id, String newPass){
    	Connection con = null;
    	String hashedPass = BCrypt.hashpw(newPass, BCrypt.gensalt());
    	try {
    		con = new Conector().getMySqlConnection();
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE credentials SET pass = ? WHERE id = ?");
            preparedStatement.setString(1, hashedPass);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
    	} catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
}
    }
