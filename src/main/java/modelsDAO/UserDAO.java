package modelsDAO;

import connections.Conector;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

public class UserDAO {

    /**
     * Método utilizado para crear el usuario que hace login.
     * @author Ricardo
     * @param con   Conexión con la BD.
     * @param resultSet Resultados de la
     * @return User (Usuario que ha hecho login)
     * @throws SQLException
     */
    public static User createUserLogin(Connection con, ResultSet resultSet) throws SQLException {
        User user = new User();

        if (con != null) {
            user.setId(resultSet.getInt("id"));
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM user_obj WHERE id = ?");
            preparedStatement.setInt(1, user.getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setUserType(resultSet.getString("user_type"));
                user.setName(resultSet.getString("user_name"));
                user.setSchool_id(resultSet.getInt("school_id"));
                user.setCourse_id((Integer) resultSet.getObject("course_id"));
            }
        }

        return user;
    }

    /**
     * Inserta un registro nuevo en la tabla de Credentials.
     * @author Ricardo
     * @param con   Conexión con la BD.
     * @param userEmail	email que se recoge en el registro.
     * @param userPass	contraseña que se recoge en el registro.
     */
    public static void insertCredentials(Connection con, String userEmail, String userPass) {
        // No cerramos la conexión porque este método se utiliza dentro de otro que si la cierra
        if (con != null) {
            try (PreparedStatement ps = con.prepareStatement("call insertUserCredentials(?,?)")) {
                ps.setString(1, userEmail);
                ps.setString(2, userPass);
                ps.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Inserta un usuario nuevo en la BD.
     * @author Ricardo
     * @param con   Conexión con la BD.
     * @param name	nombre recogido en el registro.
     * @param lastName	apellidos recogidos en el registro.
     * @param birthDate	fecha de nacimiento recogida en el registro.
     * @param dnie	DNI o NIE recogido en el registro.
     * @param email email recogido en el registro.
     * @param pass	contraseña recogida en el registro.
     * @param schoolId	Id del colegio seleccionado en el registro.
     * @param courseId	Id del módulo seleccionado en el registro.
     */
    public static void insertUserInDB(Connection con, String name, String lastName, String birthDate, String dnie, String email, String pass, String schoolId, String courseId) {
        // No cerramos la conexión porque este método se utiliza dentro de otro que si la cierra
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
                ps.setInt(8, Integer.parseInt(schoolId));
                ps.setInt(9, Integer.parseInt(courseId));
                ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Método utilizado en DatosPersonales.jsp. Devuelve los datos del usuario y los utiliza para imprimirlos.
     * @author Ricardo
     * @param id Id del alumno.
     * @return	HashMap<String,String> (Datos del alumno)
     */
    public static HashMap<String,String> getUserInfo(int id) {
        HashMap<String, String> userInfo = new HashMap<>();
        Connection con = null;

        try {
            con = new Conector().getMySqlConnection();
            if (con != null) {
                PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM user_obj WHERE id = ?");
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    userInfo.put("name",resultSet.getString("user_name"));
                    userInfo.put("surname",resultSet.getString("user_surname"));
                    userInfo.put("dnie",resultSet.getString("dnie"));
                    userInfo.put("birthDate",resultSet.getString("birthDate"));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return userInfo;
    }

    /**
     * Método que se utiliza para rescatar los estudiantes de un profesor a partir de su ID.
     * @author Ricardo
     * @param teacher Usuario del profesor.
     * @return List<User> (Lista de los estudiantes del profesor)
     */
    public static List<User> getStudentsFromTeacherAndSubjectId(User teacher, int subject_id) {
        Connection con = null;
        List<User> students = new ArrayList<>();

        try {
            con = new Conector().getMySqlConnection();
            if (con != null) {
                PreparedStatement ps = con.prepareStatement("SELECT distinct u.id,u.user_type,u.user_name,u.school_id,u.course_id " +
                                                                "FROM user_obj as u INNER JOIN grades as g ON u.id = g.student " +
                                                                "WHERE u.school_id = ? and g.subject_id = ? and u.user_type = '01'");
                ps.setInt(1,teacher.getSchool_id());
                ps.setInt(2,subject_id);

                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("u.id"));
                    user.setUserType(resultSet.getString("u.user_type"));
                    user.setName(resultSet.getString("u.user_name"));
                    user.setSchool_id(resultSet.getInt("u.school_id"));
                    user.setCourse_id((Integer) resultSet.getObject("u.course_id"));
                    students.add(user);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return students;
    }


    /**
     * Método que recupera todos los profesores existentes en la BD,
     * es decir todos los usuarios cuyo tipo de usuario sea "02"
     * @author Óscar
     * 
     * @return List<User> lista con todos los nombres completos de los maestros
     */

    public static List<User> getAllNameTeachers(int course_id, int school_id) {
        List<User> teachers = new ArrayList<>();
        Connection con = null;

        try {
            con = new Conector().getMySqlConnection();
            PreparedStatement ps = con.prepareStatement("SELECT distinct * FROM user_obj WHERE (user_type = '02' and course_id = ? and school_id = ?);");
            ps.setInt(1,course_id);
            ps.setInt(2,school_id);
            //Sentencia sql para traerme todos los profesores
            ResultSet rs = ps.executeQuery();
            //Se añaden los resultados a la lista de profesores
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("user_name") + " " + rs.getString("user_surname"));
                user.setUserType(rs.getString("user_type"));
                user.setSchool_id(rs.getInt("school_id"));
                user.setCourse_id(rs.getInt("course_id"));
                teachers.add(user);
            }
            ps.close();
            rs.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return teachers;
    }

    
    
    
    
    
    /**
     * Método que se utiliza para cambiar la contraseña de un usuario existente
     * @author Ricardo
     * @param id Id del usuario.
     * @param newPass Nueva contraseña que quiere guardar.
     * @return boolean (Booleano que representa si se ha cambiado o no)
     */
    public static boolean setNewPass (int id, String newPass){
        boolean changed = false;
    	Connection con = null;
    	String hashedPass = BCrypt.hashpw(newPass, BCrypt.gensalt());

    	try {
    		con = new Conector().getMySqlConnection();
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE credentials SET pass = ? WHERE id = " + id);
            preparedStatement.setString(1, hashedPass);
            int i = preparedStatement.executeUpdate();
            if (i == 1)
                changed = true;

    	} catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return changed;
    }
    

    public static String getCourseFromTeacherId(int teacherId) {
        Connection con = null;
        String query = "SELECT course_name FROM course WHERE id IN (SELECT course_id FROM user_obj WHERE id = ?)";
        String courseName = "";

        try {
            con = new Conector().getMySqlConnection();
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, teacherId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                courseName += resultSet.getString("course_name") + ";";
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return courseName.isEmpty() ? "" : courseName.substring(0, courseName.length() - 1); 
    }

    
    public static List<User> getStudentsFromTeacherId(int teacherId) {
        Connection con = null;
        String query = "SELECT * FROM user_obj WHERE id IN (SELECT student FROM grades WHERE teacher = ?)";

        List<User> estudiantes = new ArrayList<>();
        try {
            con = new Conector().getMySqlConnection();
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, teacherId);
            ResultSet resultSet = preparedStatement.executeQuery();
       
            while (resultSet.next()) {
                User user = new User();
            	user.setId(resultSet.getInt("id"));
				user.setUserType(resultSet.getString("user_type"));
                user.setName(resultSet.getString("user_name") + " " + resultSet.getString("user_surname"));
                user.setSchool_id(resultSet.getInt("school_id"));
                user.setCourse_id((Integer) resultSet.getObject("course_id"));      
                
                estudiantes.add(user);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

		return estudiantes;
    }

    /**
     * Método que recupera la asignatura de un profesor desde base de datos
     * @param id_teacher id del usuario asignado como profesor
     * @author Óscar
     * 
     * @return nombre de la asignatura del profesor
     */
    public static String getSubjectsTeacher(int id_teacher) {
      
      String subjects = "";
      Connection con = null;

      try {
          con = new Conector().getMySqlConnection();
          PreparedStatement ps = con.prepareStatement(
              "  select su.subject_name from _subject su\r\n"
              + "    inner join teacher_subject te on te.subject_id = su.id\r\n"
              + "    inner join user_obj us on us.id = te.user_id\r\n"
              + "    where te.user_id = ?; ");
          ps.setInt(1,id_teacher);
          ResultSet rs = ps.executeQuery();
          
          while (rs.next()) {
             subjects += (rs.getString(1) + ", ");
          }

      } catch (ClassNotFoundException | SQLException e) {
          e.printStackTrace();
      } finally {
          if (con != null) {
              try {
                  con.close();
              } catch (SQLException e) {
                  e.printStackTrace();
              }
          }
      }

      return subjects.substring(0,subjects.length() - 2);
  }
    
    
    /**
     * Método que recupera la escula de un profesor desde base de datos
     * @param id_teacher id del usuario asignado como profesor
     * @author Óscar
     * 
     * @return nombre de la escuela del profesor
     */
    public static String getNameSchoolTeacher(int id_teacher) {
      
      String school_name = "";
      Connection con = null;

      try {
          con = new Conector().getMySqlConnection();
          PreparedStatement ps = con.prepareStatement("select sc.school_name from school sc inner join user_obj us on us.school_id = sc.id where us.id = ?");
          
          ps.setInt(1,id_teacher);
          ResultSet rs = ps.executeQuery();

          while (rs.next()) {
             school_name = rs.getString(1);
          }

      } catch (ClassNotFoundException | SQLException e) {
          e.printStackTrace();
      } finally {
          if (con != null) {
              try {
                  con.close();
              } catch (SQLException e) {
                  e.printStackTrace();
              }
          }
      }

      return school_name;
  }
    
    public static String getMail(int id) {
		String mail = "";
		Connection con = null;

		try {
			con = new Conector().getMySqlConnection();
			PreparedStatement ps = con.prepareStatement("SELECT email from credentials WHERE id=" + id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				mail = rs.getString(1);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return mail;
	}
    
    public static boolean setNewMail(int id, String mail) {
		boolean changed = false;
		Connection con = null;

		try {
			con = new Conector().getMySqlConnection();
			PreparedStatement ps = con.prepareStatement("Update credentials SET email = ? WHERE id = " + id);
			ps.setString(1, mail);
			int i = ps.executeUpdate();
			if (i == 1) {
				changed = true;
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return changed;
	}

    public static String calificacionesByUserType(User u) {
        String path = "";
        if(u.getUserType().equals("01")) {
            path = "./jsp/calificacionesAlumno.jsp";
        } else if (u.getUserType().equals("02")) {
            path = "./jsp/calificaciones.jsp";
        } else {
            path = "./jsp/calificacionesAcc.jsp";
        }

        return path;
    }

    public static User getUserInfoById(int id) {
        User u = new User();
        Connection conn = null;
        try {
            conn = new Conector().getMySqlConnection();
            try (PreparedStatement ps = conn.prepareStatement("Select * from user_obj where id = ?;")) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        u.setId(rs.getInt(1));
                        u.setName(rs.getString(2));
                        u.setUserType(rs.getString(6));
                        u.setSchool_id(rs.getInt(7));
                        u.setCourse_id(rs.getInt(8));
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return u;
    }
}
