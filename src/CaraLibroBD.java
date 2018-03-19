import java.util.ArrayList;
import java.util.Iterator;

public class CaraLibroBD {

    private static ArrayList<Perfil> perfis = new ArrayList<Perfil>();
    private static ArrayList<Evento> eventos = new ArrayList<>();

    public ArrayList<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(ArrayList<Perfil> perfis) {
        CaraLibroBD.perfis = perfis;
    }

    public static ArrayList<Evento> getEventos() {
        return eventos;
    }

    public Perfil obterPerfil(String nome, String contrasinal) {
        Iterator<Perfil> it = perfis.iterator();
        while (it.hasNext()) {
            Perfil perfilActual = it.next();
            if (perfilActual.nome.equals(nome) && perfilActual.contrasinal.equals(contrasinal)) {
                return perfilActual;
            }
        }
        return null;
    }

    public Perfil buscarPerfil(String nome) {
        for (Perfil perfil : perfis) {
            if (perfil.nome.equals(nome)) {
                return perfil;
            }
        }
        return null;
    }

    public boolean engadirPerfil(Perfil novoPerfil) {
    	if (!perfis.contains(novoPerfil)) {
    		novoPerfil.idPerfil = perfis.size() + 1;
    		perfis.add(novoPerfil);
    		return true;
    	} else {
    		return false;
    	}
    }

    public void engadirEvento(Evento evento){
        eventos.add(evento);
    }

    public ArrayList<Evento> conseguirEventosDeUsuario (Perfil perfil) {
        ArrayList<Evento> eventosDeUsuario = new ArrayList<Evento>();
        for(Evento evento : eventos){
            if(evento.getAsistentes().contains(perfil)) {
                eventosDeUsuario.add(evento);
            }
        }
        return eventosDeUsuario;
    }

    public void eliminarEvento (Evento evento){
        eventos.remove(evento);
    }
}