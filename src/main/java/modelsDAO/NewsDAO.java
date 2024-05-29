package modelsDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import connections.Conector;
import models.News;

public class NewsDAO {
	
	 /**
     * Método estático utilizado para sacar las noticias de la base de datos
     * @author Miguel
     * @return	Lista de las noticias
     */
    public static List<News> getAllNewsBySchoolId(int id_school) {
        List<News> news = new ArrayList<>();
        Connection con = null;

        try {
            con = new Conector().getMySqlConnection();
            try {
                PreparedStatement ps = con.prepareStatement("select id, title, caption, img_url, caption_img, content, date_new  from news where id_school = ? order by date_new desc;");
                
                ps.setInt(1, id_school);
                
                ResultSet result = ps.executeQuery();
                
                while (result.next()) {
                	int id = result.getInt("id");
                	String title = result.getString("title");
                	String image_URL = result.getString("caption");
                	String imagen = result.getString("img_url");
                	String captionImagen = result.getString("caption_img");
                	String content = result.getString("content");
                	Date fecha = result.getDate("date_new");
                
                    
                	Locale esp = new Locale("es", "ES");
                	SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", esp);
                	String fechaNueva = dateFormat.format(fecha);
                    news.add(new News(id, title, image_URL, imagen, captionImagen, content, fechaNueva));
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
                    e.printStackTrace();
                }
            }
        }

        return news;
    }
	

}
