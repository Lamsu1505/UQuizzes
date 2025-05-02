package org.example.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.util.function.ToDoubleBiFunction;

public class PruebaPrincipalController {

    @FXML
    private ImageView ImgPortadaCancionReprodu;

    @FXML
    private SVGPath btnPausar;

    @FXML
    private SVGPath btnReanudar;

    @FXML
    private SVGPath btnReiniciarCancion;

    @FXML
    private SVGPath btnSiguienteCancion;

    @FXML
    private SVGPath btnSiguienteCancion1;

    @FXML
    private SVGPath btncancionAnterior;

    @FXML
    private StackPane contenedorCambiante;

    @FXML
    private SVGPath imgAlbumes;

    @FXML
    private SVGPath imgBuscar;

    @FXML
    private SVGPath imgCasa;

    @FXML
    private SVGPath imgCorazon;

    @FXML
    private SVGPath imgCorazonCancionReproduciendo;

    @FXML
    private SVGPath imgGeneros;

    @FXML
    private SVGPath imgMicrofono;

    @FXML
    private SVGPath imgStorify;

    @FXML
    private SVGPath imgTresPuntos;

    @FXML
    private Label lblAlbumes;

    @FXML
    private Label lblArtistas;

    @FXML
    private Label lblCanciones;

    @FXML
    private Label lblGeneros;

    @FXML
    private Label lblInicio;

    @FXML
    private Label lblNombreAlbumReproduciendo;

    @FXML
    private Label lblNombreArtistaReproduciendi;

    @FXML
    private Label lblNombreCancionReproduciendo;

    @FXML
    private Label lblStorify;

    @FXML
    private Label lblDuracionCancion;

    @FXML
    private BorderPane paneIrAdelante;

    @FXML
    private BorderPane paneIrAtras;

    @FXML
    private BorderPane panelCentral;

    @FXML
    private TextField txtFIeldBuscar;

    @FXML
    private SVGPath btnCancionAnterior;

    @FXML
    private SVGPath btnCancionSiguiente;

    private boolean btnPausaPulsado = false;
    private boolean btnPlayPulsado = false;



    /**
     * Metodo que se usa para ir a la ventana de canciones
     * @param mouseEvent evento del mouse que se activa al hacer click
     */
    public void irVentanaCanciones(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            Parent panel = FXMLLoader.load(getClass().getResource("/Ventanas/Paneles/PanelCancionesFavoritas.fxml"));
            this.contenedorCambiante.getChildren().clear();
            this.contenedorCambiante.getChildren().addAll(panel);
            ponerLabelGrises(mouseEvent);
            lblCanciones.setTextFill(Color.WHITE);

        }
        catch (Exception e) {
        }
    }

    /**
     * Metodo que se usa para cerrar sesion
     * @param mouseEvent variable que almacena el evento del mouse
     */
    public void cerrarSesion(MouseEvent mouseEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Ventanas/Ventanas/IniciarSesionStorify.fxml"));
            Parent root = loader.load();

            // Crear una nueva escena
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("VentanaPrincipal");

            // Mostrar la nueva ventana
            stage.show();

            // Cerrar la ventana actual
            Stage ventanaActual = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            ventanaActual.close();
        }
        catch (Exception e) {
        }
    }

    /**
     * Metodo que se usa para ir al panel de artistas
     * @param mouseEvent variable que almacena un evento del mouse
     */
    public void irPanelArtistas(MouseEvent mouseEvent) {
        try {

            Parent panel = FXMLLoader.load(getClass().getResource("/Ventanas/Paneles/PanelArtistas.fxml"));
            this.contenedorCambiante.getChildren().clear();
            this.contenedorCambiante.getChildren().addAll(panel);
            ponerLabelGrises(mouseEvent);
            lblArtistas.setTextFill(Color.WHITE);
        }
        catch (Exception e) {
        }
    }

    /**
     * Metodo que se usa para ir al panel de generos
     * @param mouseEvent variable que almacena el evento del mouse
     */
    public void irVentanaGeneros(MouseEvent mouseEvent) {
        try {

            Parent panel = FXMLLoader.load(getClass().getResource("/Ventanas/Paneles/PanelGeneros.fxml"));
            this.contenedorCambiante.getChildren().clear();
            this.contenedorCambiante.getChildren().addAll(panel);
            ponerLabelGrises(mouseEvent);
            lblGeneros.setTextFill(Color.WHITE);
        }
        catch (Exception e) {

        }
    }

    /**
     * Metodo que se usa para ir a la ventana de albumes
     * @param mouseEvent variable que contiene el evento del mouse
     */
    public void irVentanaAlbumes(MouseEvent mouseEvent) {
        try {
            Parent panel = FXMLLoader.load(getClass().getResource("/Ventanas/Paneles/PanelAlbumes.fxml"));
            this.contenedorCambiante.getChildren().clear();
            this.contenedorCambiante.getChildren().addAll(panel);
            ponerLabelGrises(mouseEvent);
            lblAlbumes.setTextFill(Color.WHITE);
        }
        catch (Exception e) {
        }
    }

    /**
     * Metodo que se usa para ir al panel de inicio
     * @param mouseEvent variable que contiene el evento del mouse
     */
    public void irPanelInicio(MouseEvent mouseEvent) {
        try {
            Parent panel = FXMLLoader.load(getClass().getResource("/Ventanas/Paneles/PanelInicio.fxml"));
            this.contenedorCambiante.getChildren().clear();
            this.contenedorCambiante.getChildren().addAll(panel);
            ponerLabelGrises(mouseEvent);
            lblInicio.setTextFill(Color.WHITE);
        }
        catch (Exception e) {
        }
    }

    /**
     * Metodo que se usa para ir al panel de storify
     * @param mouseEvent vaiable que contiene el evento del mouse
     */
    public void irPanelStorify(MouseEvent mouseEvent) {
        try {
            Parent panel = FXMLLoader.load(getClass().getResource("/Ventanas/Paneles/PanelStorify.fxml"));
            this.contenedorCambiante.getChildren().clear();
            this.contenedorCambiante.getChildren().addAll(panel);

            ponerLabelGrises(mouseEvent);
            lblStorify.setTextFill(Color.WHITE);
        }
        catch (Exception e) {
        }
    }

    /**
     * Metodo que se usa para reiniciar una cancion
     * @param event variable que captura el evento del mouse
     */
    @FXML
    void reiniciarCancion(MouseEvent event) {
        //modelFactory.reiciarCancion();
    }

    /**
     * Metodo que se usa para reproducir la cancion anterior
     * @param event variable que contiene el evento del mouse
     */
    @FXML
    void reproducirCancionAnterior(MouseEvent event) {

    }

    /**
     * Metodo que se usa para reproducir la siguiente cancion
     * @param event variable que contiene el evento del mouse
     */
    @FXML
    void reproducirCancionSiguiente(MouseEvent event) {

    }
    /*
        /**
         * Metodo que se usa para reproducir una cancion
         * @param mouseEvent variable que almacena el evento del mouse
         * @throws UnsupportedAudioFileException excepcion que se arroja en caso de existir un tipo de archivo de audio que no se soporta
         * @throws LineUnavailableException excepcion que se arroja en caso de haber una linea no disponible
         * @throws IOException excepcion que se arroja en caso de haber un error de tipo imput - output
         */
    /*@FXML
    public void reproducirCancion(MouseEvent mouseEvent) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        try {

            //TODO no dejar la cancion quemada
            //modelFactory.cargarCancion("src/main/java/org/example/proyectofinalestructura/model/ReproductorAudio/Musica/Tini,MariaBecerra;Mienteme;Sencillo.wav");
            //modelFactory.reproducirCancion();

            //float duracion = modelFactory.obtenerDuracionCancion("src/main/java/org/example/proyectofinalestructura/model/ReproductorAudio/Musica/Tini,MariaBecerra;Mienteme;Sencillo.wav");
            //actualizarLblDuracionCancion(duracion);

        }catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo que se usa para pausar la cancion
     * @param event variable que almacena el evento del mouse
     */
    /*@FXML
    void pausarCancion(MouseEvent event) {
        try {
            modelFactory.pausarCancion();
            // Cambiar el color del botón al dar clic
            btnPausar.setFill(Color.valueOf("#1ed55f"));
            // Actualizar el estado del botón
            btnPausaPulsado = true;
            // Actualizar el estado del otro botón
            btnPlayPulsado = false;
            // Cambiar el color del otro botón a blanco
            btnReanudar.setFill(Color.WHITE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

    /*
    /**
     * Metodo que se usa para reanudar una cancion
     * @param event variable que almacena el evento del mouse
     */
    /*@FXML
    void reanudarCancion(MouseEvent event) {
        if(!btnPlayPulsado)
        {
            try {
                modelFactory.reanudarCancion();
                // Cambiar el color del botón al dar clic
                btnReanudar.setFill(Color.valueOf("#1ed55f"));
                // Actualizar el estado del botón
                btnPlayPulsado = true;
                // Actualizar el estado del otro botón
                btnPausaPulsado = false;
                // Cambiar el color del otro botón a blanco
                btnPausar.setFill(Color.WHITE);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * Metodo que pinta el boton de pausar
     * @param mouseEvent variable que almacena el evento del mouse
     */
    @FXML
    public void pintarbtnPausar(MouseEvent mouseEvent) {
        // Cambiar el color del botón cuando el ratón pasa sobre él
        if (!btnPausaPulsado) {
            btnPausar.setFill(Color.valueOf("#1ed55f"));
        }
    }

    /**
     * Metodo que pinta el boton de reanudar la cancion
     * @param mouseEvent variable que almacena el evento del mouse
     */
    @FXML
    public void pintarBtnReanudar(MouseEvent mouseEvent) {
        // Cambiar el color del botón cuando el ratón pasa sobre él
        if (!btnPlayPulsado) {
            btnReanudar.setFill(Color.valueOf("#1ed55f"));
        }
    }

    /**
     * Metodo que se usa para despintar el boton de pausa
     * @param mouseEvent variable que almacena el evento del mouse
     */
    @FXML
    public void despintarBtnPausar(MouseEvent mouseEvent) {
        // Cambiar el color del botón cuando el ratón sale del botón
        if (!btnPausaPulsado && !mouseEvent.isPrimaryButtonDown()) {
            btnPausar.setFill(Color.WHITE);
        }
    }

    /**
     * Metodo que se usa para despintar el boton de reanudar
     * @param mouseEvent variable que almacena el evento del mouse
     */
    @FXML
    public void despintarBtnReanudar(MouseEvent mouseEvent) {
        // Cambiar el color del botón cuando el ratón sale del botón
        if (!btnPlayPulsado && !mouseEvent.isPrimaryButtonDown()) {
            btnReanudar.setFill(Color.WHITE);
        }
    }

    /**
     * Metodo que se usa para pintar el boton de reiniciar la cancion
     * @param mouseEvent variable que almacena el evento del mouse
     */
    @FXML
    public void pintarBtnReiniciarCancion(MouseEvent mouseEvent) {
        btnReiniciarCancion.setFill(Color.WHITE);
    }

    /**
     * Metodo que se usa para despintar el boton de reinicio
     * @param mouseEvent variable que almacena el evento del mouse
     */
    @FXML
    public void despintarBtnReiniciarCancion(MouseEvent mouseEvent) {
        btnReiniciarCancion.setFill(Color.valueOf("#8c8c8c"));
    }

    /**
     * Metodo que se usa para pintar el boton de cancion anterior
     * @param mouseEvent variable que almacena el evento del mouse
     */
    @FXML
    public void pintarBtnCancionAnterior(MouseEvent mouseEvent) {
        btnCancionAnterior.setFill(Color.WHITE);
    }

    /**
     * Metodo que se usa para despintar el boton de cancion anterior
     * @param mouseEvent variable que almacena el evento del mouse
     */
    @FXML
    public void despintarBtnCancionAnterior(MouseEvent mouseEvent) {
        btnCancionAnterior.setFill(Color.valueOf("#8c8c8c"));
    }

    /**
     * Metodo que se usa para pintar el boton de siguiente cancion
     * @param mouseEvent variable que almacena el evento del mouse
     */
    @FXML
    public void pintarBtnCancionSiguiente(MouseEvent mouseEvent) {
        btnCancionSiguiente.setFill(Color.WHITE);
    }

    /**
     * Metodo que se usa para despintar el boton de siguiente cancion
     * @param mouseEvent variable que almacena el evento del mouse
     */
    @FXML
    public void despintarBtnCancionSiguiente(MouseEvent mouseEvent) {
        btnCancionSiguiente.setFill(Color.valueOf("#8c8c8c"));
    }

    /**
     * Metodo que se usa para actualizar el label de la duracion de la cancion
     * permitiendo ver asi la duracion de la cancion
     * @param duracion variable que contiene la duracion de la cancion
     */
    public void actualizarLblDuracionCancion(float duracion)
    {
        int minutos = (int) duracion / 60;
        int segundos = (int)duracion % 60;

        lblDuracionCancion.setText(""+minutos+":"+segundos);
    }

    /**
     * Metodo que se usa para cambiar el color del corazon
     * @param mouseEvent variable que contiene el evento del mouse
     */
    @FXML
    public void cambiarColorCorazon(MouseEvent mouseEvent) {
        if(Color.valueOf("#1ed55f").equals(imgCorazonCancionReproduciendo.getFill()))
        {
            imgCorazonCancionReproduciendo.setFill(Color.WHITE);
        }
        else{
            imgCorazonCancionReproduciendo.setFill(Color.valueOf("#1ed55f"));
        }
    }

    /**
     * Metodo que se usa para poner los labels grises
     * @param mouseEvent variable que almacena el evento del mouse
     */
    @FXML
    public void ponerLabelGrises(MouseEvent mouseEvent) {
        lblCanciones.setTextFill(Color.valueOf("#9c9c9c"));
        lblArtistas.setTextFill(Color.valueOf("#9c9c9c"));
        lblGeneros.setTextFill(Color.valueOf("#9c9c9c"));
        lblAlbumes.setTextFill(Color.valueOf("#9c9c9c"));
        lblInicio.setTextFill(Color.valueOf("#9c9c9c"));
        lblStorify.setTextFill(Color.valueOf("#9c9c9c"));
    }
}
