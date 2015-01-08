/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkmastermind;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Ignacio
 */
public class VentanaMasterMind extends javax.swing.JFrame {

    //Declaramos la Ventana Modal
    VentanaOpciones opcModal = new VentanaOpciones(this, true);
    
    int[] numerosGenerados;
    int[] numerosSeleccionados;
    int   seleccion  = 0;
    int   filaActual = 0;
    
    //PARA GUARDAR EL TAMAÑO INICIAL Y POSICION DE LOS PANELES
    private int altoPanelSelector;
    private int anchoPanelSelector;
    private int altoPanelJugada;
    private int anchoPanelJugada;
    private int altoPanelResult;
    private int anchoPanelResult;
    private int anchoPanelSolucion;
    private int PosX_Result;
    private int PosY_Result;
    
    ///
    JButton matrizSelect [];
    JButton matrizJugada [][];
    JLabel  matrizResult [][];
    
    final int NUM_FILAS   = 10;
    final int ANCHO_BTN   = 35;
    final int ALTO_BTN    = 35;
    final int MARGEN_BTN  = 5;
    final int ANCHO_RES   = 17;
    final int ALTO_RES    = 17;
    final int MARGEN_RES  = 1;
    final int MIN_OBJETOS = 6;
    
    public VentanaMasterMind() {
        
        //Inicializa Componentes
        initComponents();
        
        //Centramos la ventana
        setLocationRelativeTo(null);
        
        //Generamos una nueva secuencia de Numeros Aleatorios
        generaNumerosAleatorios();
        
        //Guardamos el Tamaño Inicial de los Paneles (Solo se ejecuta una vez)
        saveSizePosIniPanels();
        
        //Generamos el Panel Selector con Iconos de Gatos
        generaPanelSelector();
        
        //Generamos el Panel del Jugador
        generaPanelJugada();
        
        //Generamos el Panel del Resultado
        generaPanelResultado();
    }
    


    //Generamos una nueva secuencia de Numeros Aleatorios
    private void generaNumerosAleatorios()
    {
        //Recuperamos algunas Opciones
        int     longitud   = opcModal.getLongitud();
        int     numObjetos = opcModal.getNumObjetos();
        boolean duplicados = opcModal.isDuplicados();

        //Generamos la Temperatura Aleatoria Inicial
        Random generadorNum = new Random();        
        int contador = 0;
        
        //Inicializamos Matriz para los Numeros Aleatorios que vamos a Generar
        numerosGenerados = new int[longitud];
        
        //Inicializamos Matriz para los Numeros Que vamos a Seleccionar (*)
        reiniciaArraySeleccionados(longitud);
        
        //Generamos los Numeros Aleatorios Definidos en 'numFilas'
        while (contador < longitud){
            //Generamos el Numero Aleatorio
            int numAleatorio = generadorNum.nextInt(numObjetos) + 1;
            
            //Si Estan Permitidos los Duplicados
            if (duplicados){     
                numerosGenerados[contador]=numAleatorio;
                contador++;                
            }
            //Si no Estan Permitidos los Duplicados
            else if (!estaRepetido(numAleatorio)){  
                numerosGenerados[contador]=numAleatorio;
                contador++;
            }
        }
    }
    
    //Reinicia la fila que hemos seleccionado
    private void reiniciaArraySeleccionados(int longitud){
        //Inicializamos Matriz para los Numeros Que vamos a Seleccionar
        numerosSeleccionados = new int[longitud];
        
        for (int x=0; x<longitud; x++){
            numerosSeleccionados[x]=-1;
        }
    }    
    
    //Comprueba si el Numero ya está Repetido
    private boolean estaRepetido(int numAleatorio){
        int longSolucion = numerosGenerados.length;
        
        for (int x=0; x < longSolucion; x++){
            if (numerosGenerados[x] == numAleatorio)
                return true;
        }
        return false;
    }
    
    //Guardamos el Tamaño Inicial de los Paneles (Solo se ejecuta una vez)
    private void saveSizePosIniPanels(){
        //Guardamos el Tamaño Inicial del Panel
        altoPanelSelector  = jPanelSelector.getHeight();
        anchoPanelSelector = jPanelSelector.getWidth();
        altoPanelJugada    = jPanelJugada.getHeight();
        anchoPanelJugada   = jPanelJugada.getWidth();
        altoPanelResult    = jPanelResult.getHeight();
        anchoPanelResult   = jPanelResult.getWidth();
        anchoPanelSolucion = jPanelSolucion.getWidth();
        PosX_Result        = jPanelResult.getX();
        PosY_Result        = jPanelResult.getY();
    }   

    //Genera el Panel Selector
    //Añade Dinamicamente los Botones + Cambia el Tamaño del Panel (Alto)
    private void generaPanelSelector(){

        //Recuperamos el Numero de Objetos y el Tipo
        int    numObjetos = opcModal.getNumObjetos();
        String tipoObjeto = opcModal.getTipObjeto();

        //Inicializamos las Posiciones por Defecto
        int posBotonesX = MARGEN_BTN + 1;
        int posBotonesY = MARGEN_BTN + 1;
        
        //Inicializamos las Matrices
        matrizSelect = new JButton[numObjetos];
        
        //Inicializamos el Panel
        jPanelSelector.removeAll();        
        
        //Generamos todos los Botones del Panel de Botones
        for (int posicion = 0; posicion < numObjetos; posicion++) {
            //Nuevo boton
            matrizSelect[posicion] = new JButton();            
            matrizSelect[posicion].setBounds(posBotonesX, posBotonesY, ANCHO_BTN, ALTO_BTN);
            matrizSelect[posicion].setIcon(new javax.swing.ImageIcon(getClass().getResource("../Imagenes/" + tipoObjeto + "_" + (posicion + 1) + ".png")));

            //Añadimos el evento al boton
            matrizSelect[posicion].setActionCommand(String.valueOf(posicion));
            addEventoBtnSelector(posicion);
            
            //Añadimos los Botones a sus Paneles
            jPanelSelector.add(matrizSelect[posicion]);

            //Incrementamos X
            posBotonesY = posBotonesY + ANCHO_BTN + MARGEN_BTN;
        }        
        //Cambiamos el Alto del Panel Selector
        jPanelSelector.setSize(anchoPanelSelector, posBotonesY + 2);
        
        //Refrescamos el Panel
        jPanelSelector.repaint();
    }
    
    //Añade el Evento a la Matriz de Botones del Panel Selector
    private void addEventoBtnSelector(int posicion){
        matrizSelect[posicion].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //Obtenemos el Numero del Boton que se ha pulsado
                JButton evento = (JButton)evt.getSource();                
                int btnPulsado = Integer.valueOf(evento.getActionCommand());
                incluyeSeleccion(btnPulsado);
            }
        });
    }    
    //Guarda y Ordena en una Matriz el Boton que hemos Pulsado
    private void incluyeSeleccion(int btnPulsado){
        int longitud = opcModal.getLongitud();
        
        for (int x=0; x<longitud; x++){
            if (numerosSeleccionados[x]==-1){
                numerosSeleccionados[x]=btnPulsado;
                setIconoSeleccionado(x, btnPulsado);
                break;
            }
        }
    }
    //Muestra en el Panel de la Jugada el Icono seleccionado
    private void setIconoSeleccionado(int posicion, int btnPulsado){
        //Obtenemos el Tipo de Icono con el que estamos Jugando
        String tipoObjeto = opcModal.getTipObjeto();
        
        //Mostramos en el Panel de la Jugada el Icono Seleccionado
        matrizJugada[filaActual][posicion].setIcon(new javax.swing.ImageIcon(getClass().getResource("../Imagenes/" + tipoObjeto + "_" + (btnPulsado + 1) + ".png")));
        
        //Activamos el Boton
        matrizJugada[filaActual][posicion].setEnabled(true);
    }
    
    
    //Genera el Panel de la Jugada
    //Añade Dinamicamente los Label + Reposiciona el Panel + 
    //Cambia el Ancho del Panel
    private void generaPanelJugada(){
        
        //Recuperamos la Longitud de Columnas
        int longitud = opcModal.getLongitud();

        //Inicializamos las Posiciones por Defecto
        int posBotonesX = MARGEN_BTN + 1;
        int posBotonesY = MARGEN_BTN + 1;

        //Inicializamos las Matrices
        matrizJugada = new JButton[NUM_FILAS][longitud];
        
        //Inicializamos el Panel
        jPanelJugada.removeAll(); 
        
        //Generamos todos los Botones del Panel de Botones
        for (int filas = 0; filas < NUM_FILAS; filas++) {

            //Inicializamos Variables
            posBotonesX = MARGEN_BTN + 1;

            for (int colum = 0; colum < longitud; colum++) {
                //Nuevo boton
                matrizJugada[filas][colum] = new JButton();
                matrizJugada[filas][colum].setBounds(posBotonesX, posBotonesY, ANCHO_BTN, ALTO_BTN);
                matrizJugada[filas][colum].setIcon(new javax.swing.ImageIcon(getClass().getResource("../Imagenes/Question.png")));
                matrizJugada[filas][colum].setEnabled(false);
                
                //Añadimos el evento al boton
                matrizJugada[filas][colum].setActionCommand(String.valueOf(colum));
                addEventoBtnJugada(filas, colum);
                
                //Añadimos los Botones a sus Paneles
                jPanelJugada.add(matrizJugada[filas][colum]);

                //Incrementamos X
                posBotonesX = posBotonesX + ANCHO_BTN + MARGEN_BTN;
            }
            //Incrementamos Y
            posBotonesY = posBotonesY + ALTO_BTN + MARGEN_BTN;
        }
        //Cambiamos el Ancho del Panel de Juego        
        jPanelJugada.setSize(posBotonesX - 1, altoPanelJugada);
        jPanelJugada.setMinimumSize(new Dimension(posBotonesX - 1, altoPanelJugada));
        jPanelJugada.setMaximumSize(new Dimension(posBotonesX - 1, altoPanelJugada));
        jPanelJugada.setPreferredSize(new Dimension(posBotonesX - 1, altoPanelJugada));
        //System.out.println("Ancho Panel: " + jPanelJugada.getWidth());
        //System.out.println("Alto  Panel: " + jPanelJugada.getHeight());        

        //Refrescamos el Panel
        jPanelJugada.repaint();
    }
    //Añade el Evento a la Matriz de Botones del Panel de la Jugada
    private void addEventoBtnJugada(int fila, int colum){
        matrizJugada[fila][colum].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //Obtenemos el Numero del Boton que se ha pulsado
                JButton evento = (JButton)evt.getSource();                
                int btnPulsado = Integer.valueOf(evento.getActionCommand());
                excluyeSeleccion(btnPulsado);
            }
        });
    }
    //Guarda y Ordena en una Matriz el Boton que hemos Pulsado
    private void excluyeSeleccion(int posicion){
        
        //Reiniciamos la Posicion Marcada
        numerosSeleccionados[posicion]=-1;
        
        //Reestablece el Boton Pulsado
        unsetIconoSeleccionado(posicion);
    }
    private void unsetIconoSeleccionado(int posicion){
        //Mostramos en el Panel de la Jugada el Icono Seleccionado
        matrizJugada[filaActual][posicion].setIcon(new javax.swing.ImageIcon(getClass().getResource("../Imagenes/Question.png")));
        matrizJugada[filaActual][posicion].setEnabled(false);
    }
    
    
    //Genera el Panel del Resultado
    //Añade Dinamicamente los Label + Reposiciona el Panel + 
    //Cambia el Tamaño del Panel (Ancho)
    private void generaPanelResultado(){
        
        //Recuperamos la Longitud de Columnas
        int longitud = opcModal.getLongitud();

        //Calculamos el Numero de Columnas
        int columRes = (longitud + 1) / 2;
        
        //Inicializamos las Posiciones por Defecto
        int posResultX = MARGEN_BTN + 1;
        int posResultY = MARGEN_BTN + 1;

        //Inicializamos las Matrices
        matrizResult  = new JLabel[NUM_FILAS][longitud];
        
        //Inicializamos el Panel
        jPanelResult.removeAll(); 
        
        //Generamos todos los Label del Resultado
        for (int filas = 0; filas < NUM_FILAS; filas++) {
            for (int colum = 0; colum < longitud; colum++) {
                //Nuevo Label
                matrizResult [filas][colum] = new JLabel();
                matrizResult [filas][colum].setBounds(posResultX, posResultY, ANCHO_RES, ALTO_RES);
                matrizResult [filas][colum].setIcon(new javax.swing.ImageIcon(getClass().getResource("../Imagenes/check_verde.png")));

                //Añadimos los Botones a sus Paneles
                jPanelResult.add(matrizResult[filas][colum]);

                //Si ha llegado a la Mitad
                if ((colum + 1) == columRes) {
                    //Reposicionamos la Posicion X al Inicio
                    posResultX = MARGEN_BTN + 1;
                    
                    //Bajamos un Nivel la Posicion Y
                    posResultY = posResultY + ALTO_RES + MARGEN_RES;
                } 
                else {
                    //Aumentamos distancia en la Posicion X
                    posResultX = posResultX + ANCHO_RES + MARGEN_RES;
                }
            }
            //Reposicionamos la Posicion X al Inicio
            posResultX  = MARGEN_BTN + 1;

            //Aumentamos distancia en la Posicion Y
            posResultY  = posResultY  + ALTO_RES + MARGEN_BTN;
        }
        //Posicionamos el Panel de Resultado
        int posicionX = jPanelJugada.getX() + jPanelJugada.getWidth() + MARGEN_BTN + 1;
        jPanelResult.setLocation(posicionX, jPanelResult.getY());
        
        //Establecemos el Nuevo Tamaño
        int ancho = (MARGEN_BTN + 1) + (columRes * (ANCHO_RES + MARGEN_RES)) + (MARGEN_BTN + 1) + 1;
        jPanelResult.setSize(ancho, altoPanelResult);
        
        jPanelSolucion.setSize(jPanelResult.getX() + jPanelResult.getWidth() - 5, jPanelSolucion.getHeight());

        //int anchoVentana = posicionX + ancho + MARGEN_BTN + 16;
        //this.setSize(anchoVentana, this.getHeight());
        //this.repaint();
        //System.out.println("Posicion: " + posicionX);
        //System.out.println("Ancho: " + ancho);
        //System.out.println("Ancho Completo: " + (posicionX + ancho));
        
        
        
        
        //Refrescamos el Panel
        jPanelResult.repaint();
        

    }
    
    private void changeSizeVentana(){
        
        setLayout(null);//Agregado 08/01/2015 - soluciona el problema grafico
        int altoVentana  = this.getHeight();
        int anchoVentana = jPanelResult.getWidth() + jPanelResult.getX() + MARGEN_BTN;
        this.setSize(new Dimension(anchoVentana,altoVentana));        
        this.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelSelector = new javax.swing.JPanel();
        jPanelJugada = new javax.swing.JPanel();
        jPanelResult = new javax.swing.JPanel();
        jPanelSolucion = new javax.swing.JPanel();
        jBNuevaPartida = new javax.swing.JButton();
        jBOpciones = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MasterMind");

        jPanelSelector.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelSelector.setPreferredSize(new java.awt.Dimension(48, 562));

        javax.swing.GroupLayout jPanelSelectorLayout = new javax.swing.GroupLayout(jPanelSelector);
        jPanelSelector.setLayout(jPanelSelectorLayout);
        jPanelSelectorLayout.setHorizontalGroup(
            jPanelSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 44, Short.MAX_VALUE)
        );
        jPanelSelectorLayout.setVerticalGroup(
            jPanelSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 244, Short.MAX_VALUE)
        );

        jPanelJugada.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelJugada.setPreferredSize(new java.awt.Dimension(131, 411));

        javax.swing.GroupLayout jPanelJugadaLayout = new javax.swing.GroupLayout(jPanelJugada);
        jPanelJugada.setLayout(jPanelJugadaLayout);
        jPanelJugadaLayout.setHorizontalGroup(
            jPanelJugadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 121, Short.MAX_VALUE)
        );
        jPanelJugadaLayout.setVerticalGroup(
            jPanelJugadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanelResult.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanelResultLayout = new javax.swing.GroupLayout(jPanelResult);
        jPanelResult.setLayout(jPanelResultLayout);
        jPanelResultLayout.setHorizontalGroup(
            jPanelResultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );
        jPanelResultLayout.setVerticalGroup(
            jPanelResultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 404, Short.MAX_VALUE)
        );

        jPanelSolucion.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jBNuevaPartida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Nueva_Partida.png"))); // NOI18N
        jBNuevaPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNuevaPartidaActionPerformed(evt);
            }
        });

        jBOpciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Option.png"))); // NOI18N
        jBOpciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBOpcionesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelSolucionLayout = new javax.swing.GroupLayout(jPanelSolucion);
        jPanelSolucion.setLayout(jPanelSolucionLayout);
        jPanelSolucionLayout.setHorizontalGroup(
            jPanelSolucionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSolucionLayout.createSequentialGroup()
                .addComponent(jBNuevaPartida, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBOpciones, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelSolucionLayout.setVerticalGroup(
            jPanelSolucionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jBNuevaPartida, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jBOpciones, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanelSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelJugada, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelResult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanelSolucion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelJugada, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                    .addComponent(jPanelResult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelSolucion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void jBOpcionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBOpcionesActionPerformed
        
        //Abrimos la Ventana Opciones en Modo Modal
        opcModal.setVisible(true);
        
        //Si se ha Modificado Algo
        if (opcModal.isIndCambios()){
            
            //Generamos una nueva secuencia de Numeros Aleatorios
            generaNumerosAleatorios();       

            //Generamos el Panel Selector con Iconos de Gatos
            generaPanelSelector();

            //Generamos el Panel del Jugador
            generaPanelJugada();

            //Generamos el Panel del Resultado
            generaPanelResultado();
            
            changeSizeVentana();
        }
    }//GEN-LAST:event_jBOpcionesActionPerformed

    private void jBNuevaPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNuevaPartidaActionPerformed

        //Generamos una nueva secuencia de Numeros Aleatorios
        generaNumerosAleatorios();

        //Generamos el Panel Selector con el Tipo de Objeto Seleccionado
        generaPanelSelector();

        //Generamos el Panel del Jugador
        generaPanelJugada();

        //Generamos el Panel del Resultado
        generaPanelResultado();
    }//GEN-LAST:event_jBNuevaPartidaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaMasterMind.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaMasterMind.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaMasterMind.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaMasterMind.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaMasterMind().setVisible(true);
            }
        });
    }
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBNuevaPartida;
    private javax.swing.JButton jBOpciones;
    private javax.swing.JPanel jPanelJugada;
    private javax.swing.JPanel jPanelResult;
    private javax.swing.JPanel jPanelSelector;
    private javax.swing.JPanel jPanelSolucion;
    // End of variables declaration//GEN-END:variables
}
