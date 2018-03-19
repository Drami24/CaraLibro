import java.io.Serializable;
import java.util.ArrayList;

public class Perfil implements Serializable{

	int idPerfil;
	String nome;
	String contrasinal;
	String estado = "Dispoñible";
	ArrayList<Publicacion> publicacions = new ArrayList<Publicacion>();
	ArrayList<Perfil> amigos = new ArrayList<Perfil>();
	ArrayList<Perfil> solicitudesAmizade = new ArrayList<Perfil>();
	ArrayList<Mensaxe> mensaxes = new ArrayList<Mensaxe>();
	private ArrayList<Evento> invitacionsAEventos = new ArrayList<Evento>();
	
	public Perfil(String nome, String contrasinal) {
		this.nome = nome;
		this.contrasinal = contrasinal;
	}
	
	public void engadirAmigo(Perfil perfil) {
	    this.amigos.add(perfil);
	    perfil.amigos.add(this);
	}
	
	public void engadirPublicacion(Publicacion publicacion) {
		publicacion.idPublicacion = publicacions.size() +1;
		publicacions.add(publicacion);
	}
	
	public byte engadirSolicitudeDeAmizade(Perfil perfilSolicitante) {
	    if (!amigos.contains(perfilSolicitante)) {
            if (!solicitudesAmizade.contains(perfilSolicitante)){
                solicitudesAmizade.add(perfilSolicitante);
                return 0;
            } else {
                return 1;
            }
        } else {
	        return 2;
        }
    }

    public ArrayList<Evento> getInvitacionsAEventos() {
        return invitacionsAEventos;
    }

    public void aceptarSolicitudeAmizade(Perfil perfilSolicitante) {
		solicitudesAmizade.remove(perfilSolicitante);
		engadirAmigo(perfilSolicitante);
	}
	
	public void rexeitarSolicitudeAmizade(Perfil perfilSolicitante) {
		solicitudesAmizade.remove(perfilSolicitante);
	}
	
	public void engadirMensaxePrivada(Mensaxe mensaxe) {
		mensaxe.idMensaxe = mensaxes.size()+1;
		this.mensaxes.add(mensaxe);
	}
	
	public void eliminarMensaxe(Mensaxe mensaxe) {
		this.mensaxes.remove(mensaxe);
	}
}