
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class XeradorMenus {

    public void mostrarMenuInicial() {
        recuperarBaseDeDatosGardada();
        byte eleccionMenu;
        do {
            System.out.println("Benvido á rede social CaraLibro\n" +
                    "1. Iniciar sesión\n" +
                    "2. Crear perfil\n" +
                    "3. Sair da aplicación");
            eleccionMenu = leerByteTeclado();
            switch (eleccionMenu) {
                case 1:
                    this.iniciarSesion();
                    break;
                case 2:
                    this.crearPerfil();
                    break;
                case 3:
                    this.saidaCaraLibro();
                    break;
                default:
                    System.err.println("Opción incorrecta, volves ao menu inicial");
            }
        } while (eleccionMenu != 3);
    }

    /**
     * Este método mostra o menu principal do perfil
     * @param perfil perfil do suario
     */
    public void mostrarMenuPrincipal(Perfil perfil) {
        byte eleccionMenu;
        do {
            System.out.println("MENU PRINCIPAL\n" +
                    "1. Estado actual\n" +
                    "2. Biografía\n" +
                    "3. Solicitude de amizade\n" +
                    "4. Lista de amigos\n" +
                    "5. Mensaxes\n" +
                    "6. Mostrar persoas que quizais coñezas\n" +
                    "7. Eventos\n" +
                    "8. Sair\n" +
                    "Usuario conectado: " + perfil.nome);

            eleccionMenu = leerByteTeclado();
            switch (eleccionMenu) {
                case 1:
                    this.menuEstado(perfil);
                    break;
                case 2:
                    this.menuBiografia(perfil, perfil);
                    break;
                case 3:
                    this.menuSolicitudesAmizade(perfil);
                    break;
                case 4:
                    this.administrarListaAmigos(perfil);
                    break;
                case 5:
                    menuMensaxes(perfil);
                    break;
                case 6:
                    mostrarAmigosDeAmigos(atoparAmigosDeAmigos(perfil));
                    break;
                case 7:
                    menuEventos(perfil);
                    break;
                case 8:
                    this.pecharSesion(perfil);
                    break;
                default:
                    System.err.println("Opción incorrecta, volves ao menu principal");
            }
        } while (eleccionMenu != 8);
    }

    private void menuEstado(Perfil perfil) {
        byte eleccionMenu;
        do {
            System.out.println("OPCIÓNS DE ESTADO\n" +
                    "1. Visualizar estado\n" +
                    "2. Cambiar estado\n" +
                    "3. Volver ao menu princial");
            eleccionMenu = leerByteTeclado();
            switch (eleccionMenu) {
                case 1:
                    this.visualizarEstado(perfil);
                    break;
                case 2:
                    this.cambiarEstado(perfil);
                    break;
                case 3:
                    break;
                default:
                    System.err.println("Opción incorrecta");
            }
        } while (eleccionMenu != 3);
    }

    private void menuBiografia(Perfil perfilUsuario, Perfil perfilBiografia) {
        byte eleccionMenu;
        int numeroPublicacion;
        do {
            System.out.println("BIOGRAFÍA\n" +
                    "1. Visualizar biografía\n" +
                    "2. Realizar unha publicación");
            System.out.println("3. Opcións dunha publicación");
            System.out.println("4. Volver atras");

            eleccionMenu = leerByteTeclado();
            switch (eleccionMenu) {
                case 1:
                    this.mostrarBiografia(perfilBiografia);
                    break;
                case 2:
                    this.escribirPublicacion(perfilUsuario, perfilBiografia);
                    break;
                case 3:
                    mostrarBiografia(perfilBiografia);
                    System.out.println("Publicación onde quere interactuar");
                    numeroPublicacion = leerIntTeclado();
                    Publicacion publicacionAEditar = perfilUsuario.publicacions.get(numeroPublicacion - 1);
                    this.menuPublicacion(perfilUsuario, publicacionAEditar);
                    break;
            }
        } while (eleccionMenu != 4);
    }

    private void menuSolicitudesAmizade(Perfil perfil) {
        byte eleccionMenu;
        do {
            int numeroDeSolicitudes = perfil.solicitudesAmizade.size();
            System.out.println("OPCIÓNS SOLICITUDES DE AMIZADE\n" +
                    "Tes " + numeroDeSolicitudes + " solicitudes de amizade pendentes\n" +
                    "1. Ver e administrar solicitudes\n" +
                    "2. Enviar solicitude de amizade\n" +
                    "3. Volver ao menu principal");
            eleccionMenu = leerByteTeclado();
            switch (eleccionMenu) {
                case 1:
                    this.administrarSolicitudes(perfil);
                    break;
                case 2:
                    Perfil posibleAmigo = this.buscarPosibleAmigo();
                    if (posibleAmigo != null) {
                        this.enviarSolicitudeAmizade(perfil, posibleAmigo);
                    } else {
                        System.err.println("O nome introducido non foi atopado");
                    }
                    break;
            }
        } while (eleccionMenu != 3);
    }

    private void menuPublicacion(Perfil perfil, Publicacion publicacionAEditar) {
        byte eleccionMenu;
        do {
            System.out.println("OPCIÓNS PUBLICACIÓN\n" +
                    "1. Visualizar comentarios\n" +
                    "2. Escribir un comentario\n" +
                    "3. Dar me gusta na publicación\n" +
                    "4. Volver á biografía");
            eleccionMenu = leerByteTeclado();
            switch (eleccionMenu) {
                case 1:
                    if (publicacionAEditar.comentarios.size() > 0) {
                        this.mostrarComentarios(publicacionAEditar);
                    } else {
                        System.out.println("A publicación aínda non ten comentarios");
                    }
                    break;
                case 2:
                    this.escribirComentario(perfil, publicacionAEditar);
                    break;
                case 3:
                    facerMeGusta(publicacionAEditar, perfil);
                    break;
            }
        } while (eleccionMenu != 4);

    }

    private void menuMensaxes(Perfil perfil) {
        byte eleccionMenu;
        do {
            if (perfil.mensaxes.size() > 0) {
                System.out.println("Tes " + contarMensaxesSenLeer(perfil.mensaxes) + " mensaxes sen leer");
                System.out.println("--------------------");
                mostrarMensaxes(perfil);
                System.out.println("MENU MENSAXES");
                System.out.println("1. Marcar mensaxe como lida");
                System.out.println("2. Eliminar mensaxe");
                System.out.println("3. Responder a mensaxe");
                System.out.println("4. Volver Atras");
                eleccionMenu = leerByteTeclado();
                Mensaxe mensaxeSelecionada;
                switch (eleccionMenu) {
                    case 1:
                        mensaxeSelecionada = selecionarMensaxe(perfil.mensaxes);
                        if (mensaxeSelecionada != null) {
                            marcarMensaxeComoLida(mensaxeSelecionada);
                            System.out.println("Mensaxe marcada como lida");
                        }
                        break;
                    case 2:
                        mensaxeSelecionada = selecionarMensaxe(perfil.mensaxes);
                        if (mensaxeSelecionada != null) {
                            perfil.eliminarMensaxe(mensaxeSelecionada);
                            System.out.println("Mensaxe eliminada correctamente");
                        }
                        break;
                    case 3:
                        mensaxeSelecionada = selecionarMensaxe(perfil.mensaxes);
                        if (mensaxeSelecionada != null) {
                            escribirMensaxes(perfil, mensaxeSelecionada.remitente);
                        }
                        break;
                }
            } else {
                System.out.println("Non ten mensaxes");
                eleccionMenu = 4;
            }
        } while (eleccionMenu != 4);
    }

    private void menuEventos(Perfil perfil) {
        byte eleccionMenu;
        do {
            System.out.println("EVENTOS\n" +
                    "1. Crear novo evento\n" +
                    "2. Administrar Eventos\n" +
                    "3. Invitacions a eventos\n" +
                    "4. Atras");
            eleccionMenu = leerByteTeclado();
            switch (eleccionMenu) {
                case 1:
                    new CaraLibroBD().engadirEvento(crearEvento(perfil));
                    break;
                case 2:
                    menuAdministrarEventos(perfil);
                    break;
                case 3:
                    administrarInvitacionsAEventos(perfil);
                    break;
            }
        } while (eleccionMenu != 4);
    }

    public void administrarInvitacionsAEventos(Perfil perfil) {
        System.out.println("INVITACIONS A EVENTOS");
        mostrarEventos(perfil.getInvitacionsAEventos());
        Evento eventoSelecionado = selecionarEvento(perfil.getInvitacionsAEventos());
        System.out.println("Que desexa facer co evento?");
        System.out.println("1. Aceptar invitacion");
        System.out.println("2. Rexeitar invitacion");
        System.out.println("3. Atras");
        Byte opcionEscollida = leerByteTeclado();
        switch (opcionEscollida) {
            case 1:
                eventoSelecionado.getAsistentes().add(perfil);
                perfil.getInvitacionsAEventos().remove(eventoSelecionado);
                System.out.println("Asistencia confirmada a " + eventoSelecionado.getNome());
                break;
            case 2:
                perfil.getInvitacionsAEventos().remove(eventoSelecionado);
                System.out.println("Evento " + eventoSelecionado.getNome() + " rexeitado");
                break;
            case 3:
                System.out.println("Volve ao menu");
                break;
        }
    }

    private void menuAdministrarEventos(Perfil perfil) {
        byte eleccionMenu;
        do {
            ArrayList<Evento> eventosDeUsuario = new CaraLibroBD().conseguirEventosDeUsuario(perfil);
            if (eventosDeUsuario.size() > 0) {
                System.out.println("Tes " + eventosDeUsuario.size() + " eventos aos que asistiras");
                mostrarEventos(new CaraLibroBD().conseguirEventosDeUsuario(perfil));
                System.out.println("MENU EVENTOS");
                System.out.println("1. Editar o evento");
                System.out.println("2. Eliminar evento");
                System.out.println("3. Invitar a amigos");
                System.out.println("4. Volver Atras");
                eleccionMenu = leerByteTeclado();
                Evento eventoSeleccionado;
                switch (eleccionMenu) {
                    case 1:
                        eventoSeleccionado = selecionarEvento(eventosDeUsuario);
                        if (eventoSeleccionado != null) {
                            menuModificacionEvento(eventoSeleccionado);
                        }
                        break;
                    case 2:
                        eventoSeleccionado = selecionarEvento(eventosDeUsuario);
                        if (eventoSeleccionado != null) {
                            eliminarEvento(perfil, eventoSeleccionado);
                        }
                        break;
                    case 3:
                        eventoSeleccionado = selecionarEvento(eventosDeUsuario);
                        if (eventoSeleccionado != null) {
                            invitarAmigoAEvento(perfil, eventoSeleccionado);
                        }
                        break;
                }
            } else {
                System.out.println("Non ten eventos");
                eleccionMenu = 4;
            }
        } while (eleccionMenu != 4);
    }

    private void menuModificacionEvento(Evento evento) {
        Byte eleccionMenu;
        do {
            System.out.println("Que atributo do evento desexa modificar?");
            System.out.println("1. Nome");
            System.out.println("2. Lugar");
            System.out.println("3. Data");
            System.out.println("4. Atras");
            eleccionMenu = leerByteTeclado();
            switch (eleccionMenu) {
                case 1:
                    System.out.println("Inserte o novo nome do evento");
                    evento.setNome(leerStringTeclado());
                    break;
                case 2:
                    System.out.println("Inserte o novo lugar");
                    evento.setLugar(leerStringTeclado());
                    break;
                case 3:
                    modificarDataDeEvento(evento);
                    break;
            }
        } while (eleccionMenu != 4);

    }

    public void modificarDataDeEvento(Evento evento) {
        System.out.println("Inserte unha nova fecha en formato d/m/yyyy hh:mm");
        boolean cambioCorrecto = false;
        do {
            try {
                evento.setDataDoEvento(insercionDeData(leerStringTeclado()));
                cambioCorrecto = true;
            } catch (Exception e) {
                System.err.println("A data introducida non e correcta");
                System.out.println("Volva introducir a unha data en formato d/m/yyyy hh:mm");
                cambioCorrecto = false;
            }
        } while (!cambioCorrecto);
    }

    public LocalDateTime insercionDeData(String fecha) {
        try {
            return LocalDateTime.parse(fecha, DateTimeFormatter.ofPattern("d/M/yyyy HH:mm"));
        } catch (Exception e) {
            return null;
        }
    }

    private void invitarAmigoAEvento(Perfil perfil, Evento evento) {
        mostrarListaAmigos(perfil);
        selecionarAmigo(perfil.amigos).getInvitacionsAEventos().add(evento);
    }

    private void eliminarEvento(Perfil perfil, Evento eventoSeleccionado) {
        if (eventoSeleccionado.getAutor().equals(perfil)) {
            new CaraLibroBD().eliminarEvento(eventoSeleccionado);
        } else {
            eventoSeleccionado.getAsistentes().remove(perfil);
        }
    }

    private Mensaxe selecionarMensaxe(ArrayList<Mensaxe> mensaxes) {
        System.out.println("Escriba o identificador da mensaxe");
        int idMensaxe = leerIntTeclado();
        for (Mensaxe mensaxe : mensaxes) {
            if (mensaxe.idMensaxe == idMensaxe) {
                return mensaxe;
            }
        }
        System.out.println("Mensaxe non atopado");
        return null;
    }

    private Evento selecionarEvento(ArrayList<Evento> eventos) {
        System.out.println("Escriba o identificador do evento");
        int idEvento = leerIntTeclado();
        for (Evento evento : eventos) {
            if (evento.getIdEvento() == idEvento) {
                return evento;
            }
        }
        System.out.println("Evento non atopado");
        return null;
    }

    private Perfil selecionarAmigo(ArrayList<Perfil> amigos) {
        System.out.println("Escriba o identificador do amigo");
        int idAmigo = leerIntTeclado();
        for (Perfil amigo : amigos) {
            if (amigo.idPerfil == idAmigo) {
                return amigo;
            }
        }
        System.out.println("Amigo non atopado");
        return null;
    }

    private int contarMensaxesSenLeer(ArrayList<Mensaxe> mensaxes) {
        int mensaxesSenLeer = 0;
        for (Mensaxe mensaxe : mensaxes) {
            if (!mensaxe.lido) {
                mensaxesSenLeer++;
            }
        }
        return mensaxesSenLeer;
    }

    public void mostrarBiografia(Perfil perfil) {
        System.out.println("PUBLICACIÓNS");
        for (Publicacion publicacion : ordenarPublicacionsPorFecha(perfil.publicacions)) {
            this.mostrarPublicacion(publicacion);
        }
    }

    public void mostrarEventos(ArrayList<Evento> eventos) {
        System.out.println("EVENTOS");
        ArrayList<Evento> eventosOrdenados = ordenarEventosPorFecha(eventos);
        for (Evento evento : eventosOrdenados) {
            this.mostrarEvento(evento);
        }
    }

    public void mostrarEvento(Evento evento) {
        System.out.println("Identificador do evento: " + evento.getIdEvento());
        System.out.println("Nome do evento: " + evento.getNome());
        System.out.println("Nome do creador do evento: " + evento.getAutor().nome);
        System.out.println("Data da creacion: " + formatearFecha(evento.getDataDeCreacion()));
        if (evento.getDataDoEvento() != null) {
            System.out.println("Data do evento: " + formatearFecha(evento.getDataDoEvento()));
        } else {
            System.out.println("Data do evento: Non dispoñible");
        }
        System.out.println("Lugar: " + evento.getLugar());
        System.out.println("----------------------");
    }

    public ArrayList<Publicacion> ordenarPublicacionsPorFecha(ArrayList<Publicacion> publicacions) {
        Collections.sort(publicacions);
        return publicacions;
    }

    public ArrayList<Evento> ordenarEventosPorFecha(ArrayList<Evento> eventos) {
        Collections.sort(eventos);
        return eventos;
    }

    public void mostrarPublicacion(Publicacion publicacion) {
        System.out.println("Identificador da publicación: " + publicacion.idPublicacion);
        System.out.println("Nome do autor: " + publicacion.autor.nome);
        System.out.println("Data da publicación: " + formatearFecha(publicacion.data));
        System.out.println("Texto da publicación: \n" + publicacion.texto);
        System.out.println("Numero de likes: " + publicacion.likes.size());
        System.out.println("----------------------");
    }

    /**
     * Formatea fechas para sacar por pantalla
     *
     * @param data Fecha a formatear
     * @return Fecha formateada lista para sair por pantalla
     */
    public String formatearFecha(LocalDateTime data) {
        return data.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }

    public void mostrarComentarios(Publicacion publicacion) {
        System.out.println("COMENTARIOS");
        for (Comentario comentario : ordenarComentariosPorFecha(publicacion.comentarios)) {
            this.mostrarComentario(comentario);
        }
    }

    public ArrayList<Comentario> ordenarComentariosPorFecha(ArrayList<Comentario> comentarios) {
        Collections.sort(comentarios);
        return comentarios;
    }

    public void mostrarComentario(Comentario comentario) {
        System.out.println("Nome do autor: " + comentario.autor.nome);
        System.out.println("Data do comentario " + formatearFecha(comentario.data));
        System.out.println("Texto do comentario: " + comentario.texto);
        System.out.println("----------------------");
    }

    public void mostrarSolicitudesDeAmizade(Perfil perfil) {
        ArrayList<Perfil> solicitudesDeAmizade = perfil.solicitudesAmizade;
        System.out.println("SOLICITUDES DE AMIZADE");
        for (Perfil posibleAmigo : solicitudesDeAmizade) {
            this.mostrarSolicitudeDeAmizade(posibleAmigo);
        }
    }

    private void mostrarSolicitudeDeAmizade(Perfil posibleAmigo) {
        System.out.println("Identificador: " + posibleAmigo.idPerfil);
        System.out.println(posibleAmigo.nome);
        System.out.println("--------------------------");
    }

    public void mostrarListaAmigos(Perfil perfil) {
        ArrayList<Perfil> amigos = perfil.amigos;
        System.out.println("LISTA DE AMIGOS");
        if (perfil.amigos.size() > 1)
        System.out.println(perfil.nome + " ten un total de " + perfil.amigos.size() + "amigos");
        for (Perfil amigo : amigos) {
            this.mostrarAmigo(amigo);
        }
    }

    private void administrarListaAmigos(Perfil perfil) {
        byte eleccionMenu;
        if (perfil.amigos.size() < 1) {
            System.out.println("Ainda non ten amigos, vaia a solicitudes para agregar algun");
        } else {
            do {
                this.mostrarListaAmigos(perfil);
                System.out.println("Quere seleccionar algun amigo?\n" +
                        "1. Si\n" +
                        "2. Non");
                eleccionMenu = leerByteTeclado();
                switch (eleccionMenu) {
                    case 1:
                        this.opcionsAmigo(perfil);
                        break;
                }
            } while (eleccionMenu != 2);
        }
    }

    private void opcionsAmigo(Perfil perfil) {
        int idAmigo;
        System.out.println("Escriba o número de usuario solicitante");
        idAmigo = leerIntTeclado();
        for (Perfil amigo : perfil.amigos) {
            if (amigo.idPerfil == idAmigo) {
                byte eleccionMenu;
                do {
                    System.out.println("¿Qué desexa facer?\n" +
                            "1. Enviar Mensaxe privado\n" +
                            "2. Ver biografia de amigo\n" +
                            "3. Volver");
                    eleccionMenu = leerByteTeclado();

                    switch (eleccionMenu) {
                        case 1:
                            escribirMensaxes(perfil, amigo);
                            break;
                        case 2:
                            menuBiografia(perfil, amigo);
                            break;
                    }
                } while (eleccionMenu != 3);
            }
        }
    }

    private void mostrarAmigo(Perfil perfil) {
        System.out.println("Identificador do perfil: " + perfil.idPerfil);
        System.out.println("Nome do perfil: " + perfil.nome);
        System.out.println("Estado actual: " + perfil.estado);
        System.out.println("----------------------");
    }

    public void mostrarMensaxes(Perfil perfil) {
        for (Mensaxe mensaxe : ordenarMensaxesPorFecha(perfil.mensaxes)) {
            mostrarMensaxe(mensaxe);
        }
    }

    public ArrayList<Mensaxe> ordenarMensaxesPorFecha(ArrayList<Mensaxe> mensaxes) {
        Collections.sort(mensaxes);
        return mensaxes;
    }


    public void saidaCaraLibro() {
        System.out.println("Cerrouse o programa CaraLibro");
        try {
            this.escrituraNoFicheiro().writeObject(new CaraLibroBD().getPerfis());
        } catch (NullPointerException e) {
            System.out.println("O arquivo no que intentou escribir non puido ser atopado");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void pecharSesion(Perfil perfil) {
        System.out.println("O usuario: " + perfil.nome + " cerrou sesión");
    }

    private void crearPerfil() {
        String nome;
        String contrasinal;
        String contrasinal2;
        System.out.println("CREACIÓN DO PERFIL");
        System.out.println("Nome de usario:");
        nome = leerStringTeclado();
        System.out.println("Contrasinal:");
        contrasinal = leerStringTeclado();
        System.out.println("Repita o contrasinal");
        contrasinal2 = leerStringTeclado();
        if (contrasinal.equals(contrasinal2)) {
            Perfil novoPerfil = new Perfil(nome, contrasinal);
            new CaraLibroBD().engadirPerfil(novoPerfil);
        } else {
            System.err.println("Os contrasinais non coinciden");
        }
    }

    private void iniciarSesion() {
        String nome;
        String contrasinal;
        System.out.println("INICIAR SESIÓN");
        System.out.println("Nome de usuario");
        nome = leerStringTeclado();
        System.out.println("Contrasinal");
        contrasinal = leerStringTeclado();
        Perfil perfilCorrecto = new CaraLibroBD().obterPerfil(nome, contrasinal);
        if (perfilCorrecto != null) {
            System.out.println(perfilCorrecto.nome + " iniciou sesión satifactoriamente");
            this.mostrarMenuPrincipal(perfilCorrecto);
        } else {
            System.err.println("As credenciales non coinciden con nigun usuario");
        }
    }

    private void administrarSolicitudes(Perfil perfil) {
        if (perfil.solicitudesAmizade.size() > 0) {
            byte eleccionMenu;
            do {
                if (perfil.solicitudesAmizade.size() == 0) {
                    return;
                }
                this.mostrarSolicitudesDeAmizade(perfil);
                System.out.println("Quere administrar as suas solicitudes?\n" +
                        "1. Si\n" +
                        "2. Non");
                eleccionMenu = leerByteTeclado();
                switch (eleccionMenu) {
                    case 1:
                        this.administrarSolicitude(perfil);
                }
            } while (eleccionMenu != 2);
        } else {
            System.out.println("Non tes solicitudes de amizade pendentes");
        }
    }

    private void administrarSolicitude(Perfil perfil) {
        int idPerfilSolicitude;
        System.out.println("Escriba o número de usuario solicitante");
        idPerfilSolicitude = leerIntTeclado();
        for (Perfil perfilSolicitante : perfil.solicitudesAmizade) {
            if (perfilSolicitante.idPerfil == idPerfilSolicitude) {
                byte eleccionMenu;
                do {
                    System.out.println("Que desexa facer?\n" +
                            "1. Aceptar solicitude\n" +
                            "2. Rexeitar solicitude\n" +
                            "3. Volver");
                    eleccionMenu = leerByteTeclado();

                    switch (eleccionMenu) {
                        case 1:
                            perfil.aceptarSolicitudeAmizade(perfilSolicitante);
                            return;
                        case 2:
                            perfil.rexeitarSolicitudeAmizade(perfilSolicitante);
                            return;
                    }
                } while (eleccionMenu != 3);
            }
        }
    }

    private Perfil buscarPosibleAmigo() {
        String nome;
        System.out.println("Nome de usuario a buscar");
        nome = leerStringTeclado();
        return new CaraLibroBD().buscarPerfil(nome);
    }

    private void enviarSolicitudeAmizade(Perfil perfil, Perfil perfilPosibleAmigo) {
        if (!perfil.solicitudesAmizade.contains(perfilPosibleAmigo)) {
            switch (perfilPosibleAmigo.engadirSolicitudeDeAmizade(perfil)) {
                case 0:
                    System.out.println("Solicitude enviada satisfactoriamente");
                    break;
                case 1:
                    System.err.println("Xa enviaches unha solicitude");
                    break;
                case 2:
                    System.err.println("Xa sodes amigos");
                    break;
            }
        } else {
            System.err.println(perfilPosibleAmigo.nome + " xa lle enviou unha solicitude de amizade que ten pendente de aceptar");
        }
    }

    private void cambiarEstado(Perfil perfil) {
        System.out.println("Introduzca un novo estado");
        perfil.estado = leerStringTeclado();
        System.out.println("Estado cambiado satifactoriamente");
    }

    private void visualizarEstado(Perfil perfil) {
        System.out.println("O seu estado actual é: " + perfil.estado);
    }

    private void escribirComentario(Perfil perfil, Publicacion publicacion) {
        String textoComentario;
        System.out.println("Escriba o seu comentario");
        textoComentario = leerStringTeclado();
        Comentario novoComentario = new Comentario(perfil, textoComentario);
        publicacion.engadirComentario(novoComentario);
        System.out.println("Comentario engadido con éxito");
    }

    private void escribirPublicacion(Perfil perfilPublicador, Perfil perfilBiografia) {
        String textoPublicacion;
        System.out.println("Escriba a súa publicación");
        textoPublicacion = leerStringTeclado();
        Publicacion novaPublicacion = new Publicacion(perfilPublicador, textoPublicacion);
        perfilBiografia.engadirPublicacion(novaPublicacion);
        System.out.println("Publicación engadida con éxito");
    }

    private void facerMeGusta(Publicacion publicacion, Perfil persoaQueDaLike) {
        switch (publicacion.engadirMeGusta(persoaQueDaLike)) {
            case 0:
                System.out.println("Fixo like satisfactoriamente");
                break;
            case 1:
                System.err.println("Xa fixo un like a esta publicacion anteriormente");
                break;
            case 2:
                System.err.println("Non se pode dar like a si mesmo");
                break;
        }
    }

    private void escribirMensaxes(Perfil remitente, Perfil destinatario) {
        String textoMensaxe;
        System.out.println("Escriba a súa mensaxe para " + destinatario.nome);
        textoMensaxe = leerStringTeclado();
        Mensaxe novaMensaxe = new Mensaxe(remitente, textoMensaxe);
        destinatario.engadirMensaxePrivada(novaMensaxe);
        System.out.println("A sua mensaxe foi enviada");
    }

    private void marcarMensaxeComoLida(Mensaxe mensaxe) {
        mensaxe.lido = true;
    }

    private byte leerByteTeclado() {
        byte valorTeclado = 0;
        boolean bandeira;
        do {
            Scanner entradaTeclado;
            try {
                entradaTeclado = new Scanner(System.in);
                valorTeclado = entradaTeclado.nextByte();
                bandeira = false;
            } catch (Exception e) {
                System.err.println("O valor introducido non se corresponde con un número válido, volva a intentalo");
                bandeira = true;
            }
        } while (bandeira);
        return valorTeclado;
    }

    private int leerIntTeclado() {
        int valorTeclado = 0;
        boolean bandeira;
        do {
            Scanner entradaTeclado;
            try {
                entradaTeclado = new Scanner(System.in);
                valorTeclado = entradaTeclado.nextInt();
                bandeira = false;
            } catch (Exception e) {
                System.err.println("O valor introducido non se corresponde con un número válido, volva a intentalo");
                bandeira = true;
            }
        } while (bandeira);
        return valorTeclado;
    }

    private String leerStringTeclado() {
        String valorTeclado = "";
        boolean bandeira;
        do {
            Scanner entradaTeclado;
            try {
                entradaTeclado = new Scanner(System.in);
                valorTeclado = entradaTeclado.nextLine();
                bandeira = false;
            } catch (Exception e) {
                System.err.println("O valor introducido non se corresponde cunha cadea de texto, volva a intentalo");
                bandeira = true;
            }
        } while (bandeira);
        return valorTeclado;
    }

    private void mostrarMensaxe(Mensaxe mensaxe) {
        System.out.println("Identificador da mensaxe: " + mensaxe.idMensaxe);
        System.out.println("Autor da mensaxe: " + mensaxe.remitente.nome);
        System.out.println("Mensaxe lida -> " + mensaxe.lido);
        System.out.println("Data da mensaxe: " + formatearFecha(mensaxe.data));
        System.out.println("Texto da mensaxe: \n" +
                "" + mensaxe.texto);
        System.out.println("----------------------------------------");
    }

    private File crearFicheiro() {
        String ruta = "datos.txt";
        return new File(ruta);
    }

    private ObjectOutputStream escrituraNoFicheiro() {
        File ficheiro = this.crearFicheiro();
        try {
            return new ObjectOutputStream(new FileOutputStream(ficheiro));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private ObjectInputStream lecturaNoFicheiro() {
        File ficheiro = this.crearFicheiro();
        try {
            return new ObjectInputStream(new FileInputStream(ficheiro));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void recuperarBaseDeDatosGardada() {
        try {
            new CaraLibroBD().setPerfis((ArrayList<Perfil>) this.lecturaNoFicheiro().readObject());
        } catch (NullPointerException e) {
            //System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            //System.err.println(e.getMessage());
        } catch (IOException e) {
            //System.err.println(e.getMessage());
        }
    }

    private ArrayList<Perfil> atoparAmigosDeAmigos(Perfil perfil) {
        ArrayList<Perfil> amigosDeAmigos = new ArrayList<Perfil>();
        for (Perfil amigo : perfil.amigos) {
            for (Perfil amigoDeAmigo : amigo.amigos) {
                if(!amigosDeAmigos.contains(amigoDeAmigo) && !amigoDeAmigo.equals(perfil)){
                    amigosDeAmigos.add(amigoDeAmigo);
                }
            }
        }
        return amigosDeAmigos;
    }

    private void mostrarAmigosDeAmigos(ArrayList<Perfil> amigos) {
        System.out.println("AMIGOS SUXERIDOS");
        for (Perfil amigo : amigos) {
            mostrarAmigo(amigo);
        }
    }

    private Evento crearEvento(Perfil perfil) {
        System.out.println("Inserte o nome do evento");
        String nomeDoEvento = leerStringTeclado();
        System.out.println("Evento creado satifactoriamente");
        return new Evento(perfil, nomeDoEvento);
    }

}