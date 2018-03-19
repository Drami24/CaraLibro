import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Evento implements Serializable, Comparable<Evento>{
    private int idEvento;
    private String nome;
    private String lugar = "Non definido";
    private LocalDateTime dataDeCreacion;
    private LocalDateTime dataDoEvento;
    private Perfil autor;
    private ArrayList<Perfil> asistentes = new ArrayList<Perfil>();

    public Evento(Perfil autor, String nome) {
        dataDeCreacion = LocalDateTime.now();
        this.idEvento = new CaraLibroBD().getEventos().size() +1;
        this.autor = autor;
        this.nome = nome;
        this.asistentes.add(autor);
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setDataDoEvento(LocalDateTime dataDoEvento) {
        this.dataDoEvento = dataDoEvento;
    }

    public ArrayList<Perfil> getAsistentes() {
        return asistentes;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public Perfil getAutor() {
        return autor;
    }

    public LocalDateTime getDataDeCreacion() {
        return dataDeCreacion;
    }

    public LocalDateTime getDataDoEvento() {
        return dataDoEvento;
    }

    public String getLugar() {
        return lugar;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }



    @Override
    public int compareTo(Evento o) {
        if(o.dataDoEvento != null && dataDoEvento != null){
            if (dataDoEvento.isAfter(o.dataDoEvento)) {
                return -1;
            }
            if (dataDoEvento.isBefore(o.dataDoEvento)) {
                return 1;
            }
        } else {
            if (dataDeCreacion.isAfter(o.dataDeCreacion)) {
                return -1;
            }
            if (dataDeCreacion.isBefore(o.dataDeCreacion)) {
                return 1;
            }
        }

        return 0;
    }
}
