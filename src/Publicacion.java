import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Publicacion implements Serializable, Comparable<Publicacion> {

	LocalDateTime data;
	String texto;
	Perfil autor;
	int idPublicacion;
	ArrayList<Perfil> likes = new ArrayList<Perfil>();
	ArrayList<Comentario> comentarios = new ArrayList<Comentario>();
	
	public Publicacion(Perfil autor, String texto) {
		this.autor = autor;
		this.texto = texto;
		data = LocalDateTime.now();
	}
	
	public byte engadirMeGusta(Perfil autor) {
		if (autor != this.autor){
			if (!likes.contains(autor)){
			    likes.add(autor);
			    return 0;
            }else {
                return 1;
            }
		} else {
            return 2;
        }
	}
	
	public void engadirComentario(Comentario comentario) {
		comentarios.add(comentario);
	}

    @Override
    public int compareTo(Publicacion o) {
	    if (data.isAfter(o.data)) {
            return -1;
        }
        if (data.isBefore(o.data)) {
            return 1;
        }
        return 0;
    }
}