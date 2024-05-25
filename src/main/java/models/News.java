package models;

public class News {

	private int id;
	private String title;
	private String caption;
	private String imagen;
	private String captionImagen;
	private String content;
	private String creationDate;

	/**
	 * Constructor de noticia
	 * 
	 * @param id            identificador de la noticia
	 * @param title         titulo de la noticia
	 * @param caption       subtitulo de la noticia
	 * @param imagen        url de la imagen de la noticia
	 * @param captionImagen descripcin de la imagen
	 * @param content       contenido noticia
	 * @param creationDate  fecha de publicacion de la noticia
	 */
	public News(int id, String title, String caption, String imagen, String captionImagen, String content,
				String creationDate) {
		super();
		this.id = id;
		this.title = title;
		this.caption = caption;
		this.imagen = imagen;
		this.captionImagen = captionImagen;
		this.content = content;
		this.creationDate = creationDate;
	}

	// Este constructor no estoy seguro de que sea necesario ya que podemos
	// insertarlo como null en el otro y ya
	/**
	 * Constructor de noticia sin imagen
	 * 
	 * @param id            identificador de la noticia
	 * @param title         titulo de la noticia
	 * @param caption       subtitulo de la noticia
	 * @param content       contenido noticia
	 * @param creationDate  fecha de publicacion de la noticia
	 */
	public News(int id, String title, String caption, String content, String creationDate) {
		super();
		this.id = id;
		this.title = title;
		this.caption = caption;
		this.content = content;
		this.creationDate = creationDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getCaptionImagen() {
		return captionImagen;
	}

	public void setCaptionImagen(String captionImagen) {
		this.captionImagen = captionImagen;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}


}
