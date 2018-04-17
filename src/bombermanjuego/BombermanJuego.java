/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bombermanjuego;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author 12
 */
public class BombermanJuego extends Canvas implements Runnable {
//Configuración del JFrame
    //Ancho del JFrame

    private static final int ANCHO = 800;
    //Alto del JFrame
    private static final int ALTO = 600;
    //Nombre de la ventana
    private static final String NOMBRE = "Bomberman";
    private static int aps = 0;
    private static int fps = 0;
    //Nos permite saber si el juego se esta en ejecución    
    /*
    Volatile.- mientras una variable este siendo leida o escrita, no se le permitira
    a otro threal acceder a la variable
     */
    private static volatile boolean ejecucion = false;

    private static JFrame ventana;
    private static Thread thread;

    private BombermanJuego() {
        setPreferredSize(new Dimension(ANCHO, ALTO));
        ventana = new JFrame(NOMBRE);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setLayout(new BorderLayout());
        ventana.add(this, BorderLayout.CENTER);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    public static void main(String[] args) {
        BombermanJuego juego = new BombermanJuego();
        ejecucion = true;
        juego.Iniciar();
    }

    /*
    synchronized.- se asegura que los metodo nos accedan al mismo tiempo ala 
    variable
     */
    private synchronized void Iniciar() {
        thread = new Thread(this, "gps");
        thread.run();
    }

    private synchronized void Detener() {
        ejecucion = false;
        // join.- espera a que el thread termine sus operaciones
        //para que deje de ejecurtarce
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(BombermanJuego.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Nos  permite manejar las variable del juego
    private void actualizar() {
        aps++;
    }

    //Nos permite manejar todo lo que quiere mostrar lo que queremos en el juego
    private void mostrar() {
        fps++;;
    }

    @Override
    public void run() {
        // Equivalencia de nanosegundos a segundos
        final int NS_POR_SEGUNDO = 1000000000;
        //Actualizaciones por segundo que se desean realizar
        final byte APS_OBJETIVO = 60;
        //Nümero de nanosegundos que transcurriran por actualizacion
        final double NS_POR_ACTUALIZACION = NS_POR_SEGUNDO / APS_OBJETIVO;
        //Se asigna una cantidad en nanosegundos en el momento exacto
        long referenciaActualizacion = System.nanoTime();
        long referenciaContador = System.nanoTime();
        double tiempoTranscurrido;
        //Cantidad de tiempo transcurrida que se realiza hasta una actualizacion
        double delta = 0;

        while (ejecucion) {
            final long INICIO_BUCLE = System.nanoTime();
            //Tiempo pasado correccion de errores de tiempo
            tiempoTranscurrido = INICIO_BUCLE - referenciaActualizacion;
            referenciaActualizacion = INICIO_BUCLE;
            delta += tiempoTranscurrido / NS_POR_ACTUALIZACION;
            while (delta >= 1) {
                actualizar();
                delta--;

            }
            mostrar();
            if (System.nanoTime() - referenciaContador > NS_POR_SEGUNDO) {
                ventana.setTitle(NOMBRE + "|| APS: " + aps + " ||FPS: " + fps);
                aps = 0;
                fps = 0;
                referenciaContador = System.nanoTime();
            }
        }
    }

}
