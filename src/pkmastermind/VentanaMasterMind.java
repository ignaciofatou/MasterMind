/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkmastermind;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.util.Timer;
import javax.swing.JOptionPane;

/**
 *
 * @author Ignacio
 */
public class VentanaMasterMind extends javax.swing.JFrame {

    //Declaramos la Ventana Modal
    VentanaOpciones  opcModal  = new VentanaOpciones(this, true);
    VentanaHistorial histModal = new VentanaHistorial(this, true);
    Timer cronometro;
    
    int[] numerosGenerados;
    int[] numerosSeleccionados;
    int   seleccion  = 0;
    int   filaActual = 0;
    private boolean btnIniciar = true;
    
    ///
    JButton matrizSelect [];
    JButton matrizJugada [][];
    JLabel  matrizResult [][];
    
    final int ANCHO_VENTANA  = 95;
    final int ALTO_VENTANA   = 532;
    final int ANCHO_SELECTOR = 48;
    final int ALTO_JUGADA    = 408;    
    final int ANCHO_BTN      = 35;
    final int ALTO_BTN       = 35;
    final int MARGEN_BTN     = 5;
    final int ANCHO_RES      = 17;
    final int ALTO_RES       = 17;
    final int MARGEN_RES     = 1;
    final int NUM_FILAS      = 10;
    final int MIN_OBJETOS    = 6;
    final int MAX_PUNTUACION = 10000;
    
    public final static int ONE_SECOND = 1000;
    public int segundos = 0;
    public int minutos  = 0;
    public int horas    = 0;
    
    //Creamos el objeto Timer
    javax.swing.Timer t = new javax.swing.Timer(ONE_SECOND, new ActionListener(){
        public void actionPerformed(ActionEvent ae) {
            segundos++;
            
            if (segundos == 60){
                minutos++;
                segundos = 0;
            }
            if (minutos == 60){
                horas++;
                minutos = 0;
            }
            //Mostramos las Horas, Minutos y Segundos
            DecimalFormat formato = new DecimalFormat("00");
            jLTiempo.setText(formato.format(horas) + ":"
                    + formato.format(minutos) + ":"
                    + formato.format(segundos));
        }
    });

    
    public VentanaMasterMind() {
        
        //Inicializa Componentes
        initComponents();        
        
        //Centramos la ventana
        setLocationRelativeTo(null);
        
        //Ponemos el Layout (Diseño) a Nulo
        this.setLayout(null);
        
        //Establecemos el Tamaño de la Ventana
        changeSizeVentana();
        
        //Generamos una nueva secuencia de Numeros Aleatorios
        generaNumerosAleatorios();

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
        System.out.print("Solucion: ");
        
        //Generamos los Numeros Aleatorios Definidos en 'numFilas'
        while (contador < longitud){
            //Generamos el Numero Aleatorio
            int numAleatorio = generadorNum.nextInt(numObjetos) + 1;
            
            //Si Estan Permitidos los Duplicados
            if (duplicados){     
                numerosGenerados[contador]=numAleatorio;
                System.out.print(numAleatorio + ",");
                contador++;                
            }
            //Si no Estan Permitidos los Duplicados
            else if (!estaRepetido(numAleatorio)){  
                numerosGenerados[contador]=numAleatorio;
                System.out.print(numAleatorio + ",");
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
            matrizSelect[posicion].setEnabled(false);

            //Añadimos el evento al boton
            matrizSelect[posicion].setActionCommand(String.valueOf(posicion + 1));
            addEventoBtnSelector(posicion);
            
            //Añadimos los Botones a sus Paneles
            jPanelSelector.add(matrizSelect[posicion]);

            //Incrementamos X
            posBotonesY = posBotonesY + ANCHO_BTN + MARGEN_BTN;
        }        
        //Cambiamos el Alto del Panel Selector
        jPanelSelector.setSize(ANCHO_SELECTOR, posBotonesY + 2);
        
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
                activaDesactivaCheck();
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
        matrizJugada[filaActual][posicion].setIcon(new javax.swing.ImageIcon(getClass().getResource("../Imagenes/" + tipoObjeto + "_" + (btnPulsado) + ".png")));
        matrizJugada[filaActual][posicion].setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("../Imagenes/" + tipoObjeto + "_" + (btnPulsado) + ".png")));
        
        //Activamos el Boton
        matrizJugada[filaActual][posicion].setEnabled(true);
        
        //Si No estan Permitidos los Duplicados --> Desactivamos el Boton Pulsado
        if (!opcModal.isDuplicados())
            matrizSelect[btnPulsado - 1].setEnabled(false);
    }
    private void activaDesactivaCheck(){
        
        int longitud = opcModal.getLongitud();
        
        for (int x=0; x<longitud; x++){
            if (numerosSeleccionados[x]==-1){
                jBCheck.setEnabled(false);
                return;
            }
        }
        //Si Estan todos seleccionados Entonces Activamos el Check
        jBCheck.setEnabled(true);
    }
    
    
    //Genera el Panel de la Jugada
    //Añade Dinamicamente los Label + Reposiciona el Panel + 
    //Cambia el Ancho del Panel
    private void generaPanelJugada(){
        
        //Recuperamos la Longitud de Columnas
        int longitud = opcModal.getLongitud();

        //Inicializamos las Posiciones por Defecto
        int posBotonesX;
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
        jPanelJugada.setSize(calculaAnchoPanelJugada(longitud), ALTO_JUGADA);

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
                activaDesactivaCheck();
            }
        });
    }
    //Guarda y Ordena en una Matriz el Boton que hemos Pulsado
    private void excluyeSeleccion(int posicion){
        
        //Volvemos a Activar el Boton de la Seleccion
        matrizSelect[numerosSeleccionados[posicion] - 1].setEnabled(true);
        
        //Reiniciamos la Posicion Marcada
        numerosSeleccionados[posicion]=-1;
        
        //Reestablece el Boton Pulsado
        unsetIconoSeleccionado(posicion);
    }
    private void unsetIconoSeleccionado(int posicion){
        //Mostramos en el Panel de la Jugada el Icono Seleccionado
        matrizJugada[filaActual][posicion].setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("../Imagenes/Question.png")));
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
                matrizResult [filas][colum].setIcon(new javax.swing.ImageIcon(getClass().getResource("../Imagenes/Regular.png")));
                matrizResult [filas][colum].setEnabled(false);

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
        jPanelResult.setSize(calculaAnchoPanelResultado(longitud), ALTO_JUGADA);

        //Refrescamos el Panel
        jPanelResult.repaint();
    }
    
    private void changeSizeVentana(){
        int altoVentana  = ALTO_VENTANA;
        int anchoVentana = calculaAnchoVentana();
        this.setSize(new Dimension(anchoVentana, altoVentana));        
        this.repaint();
    }
    private int calculaAnchoVentana(){
        int longitud = opcModal.getLongitud();
        return ANCHO_VENTANA + calculaAnchoPanelJugada(longitud) + calculaAnchoPanelResultado(longitud);
    }
    private int calculaAnchoPanelJugada(int longitud){
        return MARGEN_BTN + (MARGEN_BTN + ANCHO_BTN) * longitud + MARGEN_BTN - 2;
    }
    private int calculaAnchoPanelResultado(int longitud){
        int columRes = (longitud + 1) / 2;
        return ((MARGEN_BTN + 1) + (columRes * (ANCHO_RES + MARGEN_RES)) + (MARGEN_BTN + 1) + 1);
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
        jLabel1 = new javax.swing.JLabel();
        jLTiempo = new javax.swing.JLabel();
        jBOpciones = new javax.swing.JButton();
        jBHistory = new javax.swing.JButton();
        jBCheck = new javax.swing.JButton();
        jBNuevaPartida = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLIntentos = new javax.swing.JLabel();
        jLNivel = new javax.swing.JLabel();

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
            .addGap(0, 124, Short.MAX_VALUE)
        );
        jPanelJugadaLayout.setVerticalGroup(
            jPanelJugadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 404, Short.MAX_VALUE)
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

        jLabel1.setText("Tiempo:");

        jLTiempo.setText("00:00:00");

        jBOpciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Option.png"))); // NOI18N
        jBOpciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBOpcionesActionPerformed(evt);
            }
        });

        jBHistory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/History.png"))); // NOI18N
        jBHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBHistoryActionPerformed(evt);
            }
        });

        jBCheck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/check2.png"))); // NOI18N
        jBCheck.setEnabled(false);
        jBCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCheckActionPerformed(evt);
            }
        });

        jBNuevaPartida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Nueva_Partida.png"))); // NOI18N
        jBNuevaPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNuevaPartidaActionPerformed(evt);
            }
        });

        jLabel3.setText("Intentos:");

        jLIntentos.setText("0");

        jLNivel.setText("Nivel: 1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanelSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jPanelJugada, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jPanelResult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jBNuevaPartida, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBOpciones, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLTiempo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLIntentos, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLNivel)))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLTiempo)
                    .addComponent(jLabel3)
                    .addComponent(jLIntentos)
                    .addComponent(jLNivel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBNuevaPartida, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBOpciones, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelJugada, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelResult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
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
            
            //Calculamos el Nuevo Tamaño de la Ventana
            changeSizeVentana();
            
            //Restablecemos las Opciones
            jBCheck.setEnabled(false);
            
            //Cambiamos el Icono a Reiniciar Partida
            jBNuevaPartida.setIcon(new javax.swing.ImageIcon(getClass().getResource("../Imagenes/Nueva_Partida.png")));
            
            //Indicamos el Nivel Actual
            if (opcModal.getNivel() != 0) {
                jLNivel.setText("Nivel: " + opcModal.getNivel());
            } else {
                jLNivel.setText("");
            }
        
            //Ponemos a True
            btnIniciar = true;
        }
    }//GEN-LAST:event_jBOpcionesActionPerformed

    private void jBNuevaPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNuevaPartidaActionPerformed

        //Indicamos el Nivel Actual
        if (opcModal.getNivel() != 0)
            jLNivel.setText("Nivel: " + opcModal.getNivel());
        else
            jLNivel.setText("");
            
        //Reiniciamos el Cronometro
        reiniciaCronometro();
        
        //Si Pinchamos en Iniciar
        if (btnIniciar == true){
            //Ponemos a False
            btnIniciar = false;
            
            //Iniciamos el Numero de Fila
            filaActual = 0;
            
            //Desactivamos el Check
            jBCheck.setEnabled(false);

            //Activa los Botones del Panel Selector
            setEnabledPanelSelector(true);

            //Marcamos la Fila en la que Jugamos
            seleccionaFilaDeJuego();
            
            //Indicamos que se ha Iniciado la Partida en el Historial
            histModal.iniciarPartida(opcModal.getNivel());
            
            //Cambiamos el Icono a Reiniciar Partida
            jBNuevaPartida.setIcon(new javax.swing.ImageIcon(getClass().getResource("../Imagenes/Restart.png")));
            
            //Iniciamos el Cronometro
            t.start();
        }
        //Si Pinchamos en Reiniciar
        else{
            //Desactivamos el Check
            jBCheck.setEnabled(false);
            
            //Cambiamos el Icono a Reiniciar Partida
            jBNuevaPartida.setIcon(new javax.swing.ImageIcon(getClass().getResource("../Imagenes/Nueva_Partida.png")));
            
            //Generamos una nueva secuencia de Numeros Aleatorios
            generaNumerosAleatorios();

            //Establecemos el Tamaño de la Ventana
            changeSizeVentana();
        
            //Generamos el Panel Selector con el Tipo de Objeto Seleccionado
            generaPanelSelector();
            
            //Activa los Botones del Panel Selector
            setEnabledPanelSelector(false);

            //Generamos el Panel del Jugador
            generaPanelJugada();

            //Generamos el Panel del Resultado
            generaPanelResultado();
            
            //Indicamos que se ha Iniciado la Partida en el Historial
            histModal.limpiarHistorial();
            
            //Iniciamos el Numero de Fila
            filaActual = 0;
            
            jLIntentos.setText("" + filaActual);
            
            //Ponemos a True
            btnIniciar = true;
        }
    }//GEN-LAST:event_jBNuevaPartidaActionPerformed
    private void reiniciaCronometro(){
        t.stop();               
        segundos = 0;
        minutos  = 0;
        horas    = 0;
        jLTiempo.setText("00:00:00");
    }
    
    
    private void setEnabledPanelSelector(boolean activo){
        
        int numObjetos = opcModal.getNumObjetos();
        
        for (int posicion = 0; posicion < numObjetos; posicion++) {
            matrizSelect[posicion].setEnabled(activo);
        }
    }
    //Pone en Azul los botones de Interrogacion
    //para saber que es la fila en la que va a jugar
    private void seleccionaFilaDeJuego(){
        
        int longitud = opcModal.getLongitud();

        //Ponemos visibles los Botones de la Barra Actual de Juego
        for (int posicion=0; posicion<longitud; posicion++){
            matrizJugada[filaActual][posicion].setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("../Imagenes/Question.png")));
        }
    }
    
    private void jBCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCheckActionPerformed
        
        int longitud = opcModal.getLongitud();
        int verdes  = 0;
        int blancos = 0;

        //filaActual        
        for (int posSelecc=0; posSelecc < longitud; posSelecc++){
            for (int posSoluc=0; posSoluc < longitud; posSoluc++){
                if (numerosSeleccionados[posSelecc]==numerosGenerados[posSoluc]){
                    if (posSelecc == posSoluc)
                        verdes++;
                    else
                        blancos++;
                }
            }
        }
        //Incluimos la Jugada en el Historial
        histModal.incluirNuevaJugada(numerosSeleccionados, longitud, verdes, blancos);

        //Mostramos el Resultado
        reflejaResultado(verdes, blancos);
        
        //Desactivamos el Check
        jBCheck.setEnabled(false);
    }//GEN-LAST:event_jBCheckActionPerformed

    private void jBHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBHistoryActionPerformed

        //Abrimos la Ventana Historica en Modo Modal
        histModal.setVisible(true);
    }//GEN-LAST:event_jBHistoryActionPerformed
    private void reflejaResultado(int verdes, int blancos){
        
        int longitud = opcModal.getLongitud();
        
        //Refleja los que hemos acertado en el Panel de Solucion
        for (int pos=0; pos < longitud; pos++){
            if (pos < verdes){
                matrizResult[filaActual][pos].setIcon(new javax.swing.ImageIcon(getClass().getResource("../Imagenes/check_verde.png")));
                matrizResult[filaActual][pos].setEnabled(true);
            }
            else if (pos < (verdes + blancos)){
                matrizResult[filaActual][pos].setIcon(new javax.swing.ImageIcon(getClass().getResource("../Imagenes/Regular.png")));
                matrizResult[filaActual][pos].setEnabled(true);
            }
            else
                matrizResult[filaActual][pos].setIcon(null);
        }
        
        //Si las ha acertado todas --> Partida ganada
        if (verdes == longitud){
            //Paramos el Cronometro
            t.stop();
            
            //Desactivamos la Fila Anterior
            desactivaFila(filaActual, longitud);
          
            //Mostramos las Horas, Minutos y Segundos
            DecimalFormat formato = new DecimalFormat("00");

            //Indicamos en el Historial que se gano la partida
            histModal.ganoPartida(formato.format(horas) + ":"
                    + formato.format(minutos) + ":"
                    + formato.format(segundos), getPuntuacion());

            //Mostramos un Mensaje de Felicitacion
            JOptionPane.showMessageDialog(
                    this,
                    "!!Bien, ha ganado!! - Ha obtenido " + getPuntuacion() + " puntos",
                    "Ha ganado",
                    JOptionPane.INFORMATION_MESSAGE);
            
            //Incrementamos el Nivel
            incrementaNivel();
        }
        //Si no las ha acertado todas
        else{
            //Si No esta en la Ultima Fila
            if (filaActual < (NUM_FILAS - 1)){
                //Pasamos a la Siguiente Fila
                siguienteFila();                
            }
            //Si Esta en la Ultima Fila --> Las ha fallado todas
            else{
                //Paramos el Cronometro
                t.stop();

                //Mostramos las Horas, Minutos y Segundos
                DecimalFormat formato = new DecimalFormat("00");

                //Indicamos en el Historial que se perdio la partida
                histModal.perdioPartida(formato.format(horas) + ":"
                        + formato.format(minutos) + ":"
                        + formato.format(segundos));
                
                //Mostramos un Mensaje de Partida Perdida
                JOptionPane.showMessageDialog(
                        this,
                        "!!Ohh, ha perdido!! - Fin de la partida",
                        "Ha perdido",
                        JOptionPane.ERROR_MESSAGE);
            }
        }        
    }
    private void incrementaNivel(){
        //Incrementamos el Nivel
        opcModal.incrementaNivel();
    }    
    
    //Calcula la Puntuacion Obtenida
    private int getPuntuacion(){
        int segundosTotales = (horas * 3600) + (minutos * 60) + segundos;
        
        //Calculamos la Puntuacion
        int puntIntentos = MAX_PUNTUACION / (filaActual + 1);
        int puntTiempo   = (MAX_PUNTUACION / (segundosTotales)) * 5;
        return puntIntentos + puntTiempo;
    }
    private void siguienteFila(){

        //Obtenemos la Longitud
        int longitud = opcModal.getLongitud();
        int numObjet = opcModal.getNumObjetos();

        //Reiniciar el Array Seleccionado
        reiniciaArraySeleccionados(longitud);
        
        //Activamos todos los Botones del Panel Selector
        for (int posicion = 0; posicion < numObjet; posicion++) {
            matrizSelect[posicion].setEnabled(true);
        }
        
        //Desactivamos la Fila Anterior
        desactivaFila(filaActual, longitud);
                
        //Incrementamos el Numero de Fila
        filaActual++;
        
        //Incrementamos en Numero de Intentos
        jLIntentos.setText("" + filaActual);
        
        //Seleccionamos la Nueva Fila de Juego
        seleccionaFilaDeJuego();
    }
    private void desactivaFila(int numFila, int longitud){
        for (int x=0; x<longitud; x++){
            matrizJugada[numFila][x].setEnabled(false);
        }
    }
    
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
    private javax.swing.JButton jBCheck;
    private javax.swing.JButton jBHistory;
    private javax.swing.JButton jBNuevaPartida;
    private javax.swing.JButton jBOpciones;
    private javax.swing.JLabel jLIntentos;
    private javax.swing.JLabel jLNivel;
    private javax.swing.JLabel jLTiempo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanelJugada;
    private javax.swing.JPanel jPanelResult;
    private javax.swing.JPanel jPanelSelector;
    // End of variables declaration//GEN-END:variables
}
