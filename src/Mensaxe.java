import java.io.Serializable;
import java.time.LocalDateTime;

public class Mensaxe implements Serializable, Comparable<Mensaxe>{
	
	LocalDateTime data;
	int idMensaxe;
	String texto;
	boolean lido = false;
	Perfil remitente;
	
	public Mensaxe(Perfil remitente, String texto) {
		this.remitente = remitente;
		this.texto = texto;
		this.data = LocalDateTime.now();
	}

    @Override
    public int compareTo(Mensaxe o) {
        if (data.isAfter(o.data)) {
            return -1;
        }
        if (data.isBefore(o.data)) {
            return 1;
        }
        return 0;
    }
}