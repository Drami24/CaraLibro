import java.io.Serializable;
import java.time.LocalDateTime;

public class Comentario implements Serializable, Comparable<Comentario>{

	LocalDateTime data;
	String texto;
	Perfil autor;
	
	public Comentario(Perfil autor, String texto) {
		this.autor = autor;
		this.texto = texto;
		this.data = LocalDateTime.now();
	}

    @Override
    public int compareTo(Comentario o) {
        if (data.isAfter(o.data)) {
            return -1;
        }
        if (data.isBefore(o.data)) {
            return 1;
        }
        return 0;
    }
	
}