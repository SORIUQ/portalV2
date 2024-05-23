package utils;

import connections.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import models.User;
import modelsDAO.UserDAO;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

import models.*;


public class Util {

	/**
	 * Este método se utiliza para realizar la lógica del LoginServlet. Comprueba si el usuario existe, en tal caso si
	 * la contraseña coincide con el correo y finalmente guarda algunos datos fundamentales.
	 * @author Ricardo
	 * @param req
	 * @param session
	 * @return Booleano si ha conseguido acceder.
	 */
    public static boolean loginProcess(HttpServletRequest req, HttpSession session) {
        boolean landing = false;
        Conector conector = new Conector();
        Connection con = null;
		String error = "";

        try {
            con = conector.getMySqlConnection();
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM credentials WHERE email = ?");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String hashedPassFromDB = resultSet.getString("pass");
                if(BCrypt.checkpw(password, hashedPassFromDB)) {
                    User user = UserDAO.createUser(con, resultSet);
                    session.setAttribute("user", user);
                    landing = true;
                }else
		    		error = "Contraseña incorrecta";
            } else
				error = "El usuario no existe";
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
        }

		if (!error.isEmpty())
			session.setAttribute("error",error);
		return landing;
    }


	/**
	 * Método utilizado para ver si un correo existe en la BD.
	 * @author Ricardo
	 * @param con
	 * @param email email que se quiere comprobar.
	 * @return devuelve un boolean en función de si existe o no el email.
	 */
    public static boolean checkIfEmailExist(Connection con, String email) {
        boolean res = false;

		// No cerramos la conexión porque este metodo se utiliza dentro de otro que si la cierra
		if (con != null) {
			try {
				PreparedStatement ps = con.prepareStatement("select * from credentials where email=?;");
				ps.setString(1, email);
				ResultSet rs = ps.executeQuery();
				if (rs.next())
					res = true;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}

        return res;
    }

	/**
	 * Método para comprobar si un DNI o NIE existe en la BD.
	 * @author Ricardo
	 * @param con
	 * @param dnie
	 * @return	Devuelve un booleano en función de si existe el DNIE o no.
	 */
    public static boolean checkIfDnieExist(Connection con, String dnie) {
        boolean res = false;

		if (con != null) {
			try {
				PreparedStatement ps = con.prepareStatement("select * from user_obj where dnie=?;");
				ps.setString(1, dnie);
				ResultSet rs = ps.executeQuery();
				if (rs.next())
					res = true;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}

        return res;
    }

	/**
	 *	Método utilizado para guardar la información necesaria en la tarjeta del usuario en el index.
	 * @author Ricardo
	 * @param activeUser
	 * @param centroUsuario
	 * @param crs
	 * @return	String con la información de dicho usuario.
	 */
	public static String getContentTarjetaIndex(User activeUser, String centroUsuario, Course crs) {
		String result = "";

		switch (activeUser.getUserType()) {
			case "01" -> {result = "Alumno de " + crs.getNameCourse();}
			case "02" -> {result = "Profesor " + centroUsuario;}
			case "03" -> {result = "Empleado/a de Accenture";}
		}

		return result;
	}

	/**
	 * Método que formatea la fecha tipo aaaa-dd-mm a dd/mm/aaaa
	 * Solo funciona si la fecha es tipo aaaa-dd-mm
	 * @author Óscar
	 * @param date - fecha a cambiar
	 * @return fecha cambiada
	 */
	public static String dateFormat(String date) {
		String fecha = "";
		String año = date.substring(0,4);
		String mes = date.substring(5, 7);
		String dia = date.substring(8,10);

		fecha = dia + "/" + mes + "/" + año;

		return fecha;
	}

	/**
	 * Método utilizado para poder definir la imagen correspondiente a cada centro
	 * @author Ricardo
	 * @param id ID del centro del usuario
	 * @return String con el link de la ruta de la imagen
	 */
    public static String defineImageIndex(Integer id){
    	String imagen = "";

    	switch(id) {
			case 1 -> {imagen = "./images/logos/LOGOTIPO-CESUR.png";}
			case 2 -> {imagen = "./images/logos/LOGOTIPO-IES-PABLO-PICASSO.png";}
			case 3 -> {imagen = "./images/logos/LOGOTIPO-IES-BELEN.png";}
			case 4 -> {imagen = "./images/logos/LOGOTIPO-ALAN-TURING.png";}
			case 5 -> {imagen = "./images/logos/LOGOTIPO-IES-SAN-JOSE.png";}
			default -> {imagen = "./images/logos/LOGOTIPO-ACCENTURE.png";}
		}

    	return imagen;
    }

	/**
	 * Método utilizado para definir la ruta al JSP de las noticias de cada centro
	 * @author Ricardo
	 * @param id
	 * @return	String con la ruta
	 */
	public static String defineID(int id){
    	String nombre="";

    	switch(id){
			case 1->{nombre="./jsp/noticiasCesur.jsp";}
			case 2->{nombre="./jsp/noticiasPabloPicasso.jsp";}
			case 3->{nombre="./jsp/noticiasBelen.jsp";}
			case 4->{nombre="./jsp/noticiasAlanTuring.jsp";}
			case 5->{nombre="./jsp/noticiasSanJose.jsp";}
    	}

    	return nombre;
    }

	/**
	 * Método utilizado para comprobar si la contraseña introducida en el cambio de contraseña es correcta
	 * @Author Ricardo
	 * @param id
	 * @param oldPass
	 * @return Booleano que muestra si la contraseña coincide o no
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static boolean checkPassDB (int id, String oldPass) {
        Connection con = null;
		String hashedPassFromDB = "";

        try {
			con = new Conector().getMySqlConnection();
			PreparedStatement preparedStatement = con.prepareStatement("SELECT pass FROM credentials WHERE id = " + id);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next())
				hashedPassFromDB = rs.getString(1);

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

        return BCrypt.checkpw(oldPass, hashedPassFromDB);
   }

	/**
	 * Método utilizado para mandar un mensaje al usuario cuando quiera cambiar la contraseña
	 * @author Ricardo
	 * @param ses
	 * @param id
	 * @param oldPass
	 * @param newPass
	 */
   public static void sendPassChangeMsg(HttpSession ses, int id, String oldPass, String newPass) {
	   if (Util.checkPassDB(id, oldPass)){
		   if (!oldPass.equals(newPass)) {
			   boolean changed = UserDAO.setNewPass(id, newPass);
			   if (changed) {
				   ses.setAttribute("okMsg", "¡Se ha cambiado la contraseña con éxito!");
				   ses.setAttribute("errorMsg", null);
			   } else {
				   ses.setAttribute("errorMsg", "No se ha podido cambiar la contraseña");
				   ses.setAttribute("okMsg", null);
			   }
		   } else {
			   ses.setAttribute("errorMsg", "No puedes cambiar a la misma contraseña");
			   ses.setAttribute("okMsg",null);
		   }
	   } else {
		   ses.setAttribute("errorMsg", "La contraseña antigua no coincide");
		   ses.setAttribute("okMsg",null);
	   }
   }
   
	/**
	 * Método utilizado para mandar un mensaje al usuario cuando quiera cambiar el correo de la cuenta
	 * @author Joseito
	 * @param ses
	 * @param id
	 * @param pass
	 * @param mail
	 */
  
public static void sendMailChangeMsg(HttpSession ses, int id, String pass, String mail) {
	String oldMail = UserDAO.getMail(id);
	if (Util.checkPassDB(id, pass)){
		if (!oldMail.equals(mail)){
			if(UserDAO.setNewMail(id, mail)) {
				   ses.setAttribute("okMsgMail", "¡Se ha cambiado el correo con éxito!");
				   ses.setAttribute("errorMsgMail", null);
			}else {
				   ses.setAttribute("errorMsgMail", "No se ha podido cambiar el correo");
				   ses.setAttribute("okMsgMail", null);
			}
		} else {
			   ses.setAttribute("errorMsgMail", "El nuevo correo no puede ser igual al anterior");
			   ses.setAttribute("okMsgMail",null);
		}
	}else {
		   ses.setAttribute("errorMsgMail", "Contraseña incorrecta");
		   ses.setAttribute("okMsgMail", null);
	}

	}
}

