/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkmastermind;

import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Ignacio
 */
public class VentanaMasterMind extends javax.swing.JFrame {

    private static final String OBJT_GATO  = "gato";
    private static final String OBJT_COLOR = "color";
    private static final String OBJT_LETRA = "letra";
    private static final String OBJT_NUMER = "numero";
    
    boolean automatico = true;
    boolean duplicados = false;
    int     numObjetos = 6;
    int     longitud   = 3;
    
    int[] numerosGenerados;
    int[] numerosSeleccionados;
    int seleccion;
    
    ///
    JLabel  matrizUsuario[][];
    JLabel  matrizResult [][];
    JButton matrizSelect [];
    final int NUM_FILAS  = 10;
    final int ANCHO_BTN  = 30;
    final int ALTO_BTN   = 30;
    final int MARGEN_BTN = 5;
    final int ANCHO_RES  = 19;
    final int ALTO_RES   = 19;
    final int MARGEN_RES = 2;    
    
    public VentanaMasterMind() {
        
        //Inicializa Componentes
        initComponents();
        
        //Centramos la ventana
        setLocationRelativeTo(null);
        
        //Recuperamos las Opciones que se han marcado
        recuperaOpciones();

        //Generamos una nueva secuencia de Numeros Aleatorios
        generaNumerosAleatorios();       

        //Generamos el Panel Selector con Iconos de Gatos
        generaPanelSelector(OBJT_GATO);
        
        //Generamos el Panel del Jugador
        generaPanelJugada();
        
        //Generamos el Panel del Resultado
        generaPanelResultado();
    }

    //Inicializamos las Opciones
    //Activamos y Desactivamos lo Necesario para que no de Error
    private void recuperaOpciones(){
        
        //Obtenemos si el Nivel esta en Modo Automatico
        automatico = jChkNivAuto.isSelected();
        
        //Obtenemos si estan Permitidos los Duplicados
        duplicados = jCBDuplicados.isSelected();
        
       //Obtenemos el Numero de Columnas Totales
        longitud   = Integer.valueOf(jCBLongitud.getSelectedItem().toString());
    
        //Obtenemos el Numero de Objetos que se pueden seleccionar
        numObjetos = Integer.valueOf(jCBObjetos.getSelectedItem().toString());
    }
    //Valida si hay alguna incongluencia en las Opciones
    private void validaOpciones(){
        //Si hay mas Filas que Objetos -> Permitimos Duplicados
        if (Integer.valueOf(jCBLongitud.getSelectedItem().toString()) > Integer.valueOf(jCBObjetos.getSelectedItem().toString())) {
            jCBDuplicados.setSelected(true);
            jCBDuplicados.setEnabled(false);
        } else {
            jCBDuplicados.setEnabled(true);
        }
    }
    
    //Generamos una nueva secuencia de Numeros Aleatorios
    private void generaNumerosAleatorios()
    {
        //Generamos la Temperatura Aleatoria Inicial
        Random generadorNum = new Random();        
        int contador = 0;
        
        //Inicializamos Matriz para los Numeros Aleatorios que vamos a Generar
        numerosGenerados     = new int[longitud];
        
        //Inicializamos Matriz para los Numeros Que vamos a Seleccionar (*)
        numerosSeleccionados = new int[longitud];
        
        //Generamos los Numeros Aleatorios Definidos en 'numFilas'
        while (contador < longitud){
            //Generamos el Numero Aleatorio
            int numAleatorio = generadorNum.nextInt(numObjetos) + 1;
            
            //Si Estan Permitidos los Duplicados
            if (jCBDuplicados.isSelected()){     
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
    private void generaPanelSelector(String tipoObjeto){

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
            matrizSelect[posicion].setSize(ANCHO_BTN, ALTO_BTN);
            matrizSelect[posicion].setLocation(posBotonesX, posBotonesY);
            matrizSelect[posicion].setIcon(new javax.swing.ImageIcon(getClass().getResource("../Imagenes/" + tipoObjeto + "_" + (posicion + 1) + ".png")));

            //Añadimos los Botones a sus Paneles
            jPanelSelector.add(matrizSelect[posicion]);

            //Incrementamos X
            posBotonesY = posBotonesY + ANCHO_BTN + MARGEN_BTN;
        }
        //Refrescamos el Panel
        jPanelSelector.repaint();
    }
    
    //Genera el Panel de la Jugada
    //Añade Dinamicamente los Label + Reposiciona el Panel + 
    //Cambia el Tamaño del Panel (Ancho)
    private void generaPanelJugada(){
        
        //Inicializamos las Posiciones por Defecto
        int posBotonesX = MARGEN_BTN + 1;
        int posBotonesY = MARGEN_BTN + 1;

        //Inicializamos las Matrices
        matrizUsuario = new JLabel[NUM_FILAS][longitud];
        
        //Inicializamos el Panel
        jPanelJugada.removeAll(); 
        
        //Generamos todos los Botones del Panel de Botones
        for (int filas = 0; filas < NUM_FILAS; filas++) {
            for (int colum = 0; colum < longitud; colum++) {
                //Nuevo boton
                matrizUsuario[filas][colum] = new JLabel();
                matrizUsuario[filas][colum].setSize(ANCHO_BTN, ALTO_BTN);
                matrizUsuario[filas][colum].setLocation(posBotonesX, posBotonesY);
                matrizUsuario[filas][colum].setIcon(new javax.swing.ImageIcon(getClass().getResource("../Imagenes/Question.png")));
                
                //Añadimos los Botones a sus Paneles
                jPanelJugada.add(matrizUsuario[filas][colum]);

                //Incrementamos X
                posBotonesX = posBotonesX + ANCHO_BTN + MARGEN_BTN;
            }
            //Inicializamos Variables
            posBotonesX = MARGEN_BTN + 1;

            //Incrementamos Y
            posBotonesY = posBotonesY + ALTO_BTN + MARGEN_BTN;
        }
        //Refrescamos el Panel
        jPanelJugada.repaint();
    }
    
    //Genera el Panel del Resultado
    //Añade Dinamicamente los Label + Reposiciona el Panel + 
    //Cambia el Tamaño del Panel (Ancho)
    private void generaPanelResultado(){
        
        //Calculamos el Numero de Columnas
        int columRes = (numObjetos + 1) / 2;
        
        //Inicializamos las Posiciones por Defecto
        int posResultX = MARGEN_BTN + 1;
        int posResultY = MARGEN_BTN + 1;

        //Inicializamos las Matrices
        matrizResult  = new JLabel[NUM_FILAS][numObjetos];
        
        //Inicializamos el Panel
        jPanelResult.removeAll(); 
        
        //Generamos todos los Label del Resultado
        for (int filas = 0; filas < NUM_FILAS; filas++) {
            for (int colum = 0; colum < numObjetos; colum++) {
                //Nuevo Label
                matrizResult [filas][colum] = new JLabel();
                matrizResult [filas][colum].setSize(ANCHO_RES, ALTO_RES);
                matrizResult [filas][colum].setLocation(posResultX , posResultY);
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
        //Refrescamos el Panel
        jPanelResult.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jBNuevaPartida = new javax.swing.JButton();
        jPanelSelector = new javax.swing.JPanel();
        jPanelJugada = new javax.swing.JPanel();
        jPanelResult = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPOpciones = new javax.swing.JPanel();
        jChkNivAuto = new javax.swing.JCheckBox();
        jCBLongitud = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jCBDuplicados = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jCBObjetos = new javax.swing.JComboBox();
        jBColores = new javax.swing.JButton();
        jBIconos = new javax.swing.JButton();
        jBLetras = new javax.swing.JButton();
        jBNumeros = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jBNuevaPartida.setText("Nueva Partida");
        jBNuevaPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNuevaPartidaActionPerformed(evt);
            }
        });

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
            .addGap(0, 453, Short.MAX_VALUE)
        );

        jPanelJugada.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanelJugadaLayout = new javax.swing.GroupLayout(jPanelJugada);
        jPanelJugada.setLayout(jPanelJugadaLayout);
        jPanelJugadaLayout.setHorizontalGroup(
            jPanelJugadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 273, Short.MAX_VALUE)
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
            .addGap(0, 57, Short.MAX_VALUE)
        );
        jPanelResultLayout.setVerticalGroup(
            jPanelResultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBNuevaPartida)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanelSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelJugada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelResult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(106, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBNuevaPartida)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanelSelector, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
                            .addComponent(jPanelJugada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(8, 8, 8))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanelResult, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jTabbedPane2.addTab("Juego", jPanel1);

        jPOpciones.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jChkNivAuto.setSelected(true);
        jChkNivAuto.setText("Nivel Automático");
        jChkNivAuto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChkNivAutoActionPerformed(evt);
            }
        });

        jCBLongitud.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "3", "4", "5", "6", "7", "8" }));
        jCBLongitud.setEnabled(false);
        jCBLongitud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBLongitudActionPerformed(evt);
            }
        });

        jLabel2.setText("Longitud:");

        jCBDuplicados.setText("Permitir Duplicados");
        jCBDuplicados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBDuplicadosActionPerformed(evt);
            }
        });

        jLabel3.setText("Num. Objetos:");

        jCBObjetos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "6", "7", "8", "9", "10" }));
        jCBObjetos.setEnabled(false);
        jCBObjetos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBObjetosActionPerformed(evt);
            }
        });

        jBColores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/color_1.png"))); // NOI18N
        jBColores.setBorder(null);
        jBColores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBColoresActionPerformed(evt);
            }
        });

        jBIconos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gato_1.png"))); // NOI18N
        jBIconos.setBorder(null);
        jBIconos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBIconosActionPerformed(evt);
            }
        });

        jBLetras.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jBLetras.setText("A");
        jBLetras.setBorder(null);
        jBLetras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLetrasActionPerformed(evt);
            }
        });

        jBNumeros.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jBNumeros.setText("0");
        jBNumeros.setBorder(null);
        jBNumeros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNumerosActionPerformed(evt);
            }
        });

        jLabel1.setText("Seleccione Tipo de Iconos:");

        jButton1.setText("Puntuaciones");

        jButton2.setText("Ayuda");

        javax.swing.GroupLayout jPOpcionesLayout = new javax.swing.GroupLayout(jPOpciones);
        jPOpciones.setLayout(jPOpcionesLayout);
        jPOpcionesLayout.setHorizontalGroup(
            jPOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPOpcionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCBDuplicados)
                    .addGroup(jPOpcionesLayout.createSequentialGroup()
                        .addComponent(jBIconos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBColores, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBLetras, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBNumeros, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jChkNivAuto)
                    .addComponent(jLabel1)
                    .addGroup(jPOpcionesLayout.createSequentialGroup()
                        .addGroup(jPOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jCBLongitud, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCBObjetos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPOpcionesLayout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)))
                .addContainerGap(322, Short.MAX_VALUE))
        );
        jPOpcionesLayout.setVerticalGroup(
            jPOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPOpcionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jChkNivAuto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCBDuplicados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jCBLongitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jCBObjetos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBColores, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBIconos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBLetras, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jBNumeros, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(287, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPOpciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPOpciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Opciones", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jChkNivAutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChkNivAutoActionPerformed
        //Si ponemos el Nivel Automático Desactivamos el ComboBox
        if (jChkNivAuto.isSelected()) {
            jCBLongitud.setEnabled(false);
            jCBObjetos.setEnabled(false);
        } else {
            jCBLongitud.setEnabled(true);
            jCBObjetos.setEnabled(true);
        }
        //Queda pendiente establecer los Niveles
    }//GEN-LAST:event_jChkNivAutoActionPerformed

    private void jCBObjetosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBObjetosActionPerformed
        //Valida si hay alguna incongluencia en las Opciones
        validaOpciones();
    }//GEN-LAST:event_jCBObjetosActionPerformed

    private void jBNuevaPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNuevaPartidaActionPerformed

        //Recuperamos las Opciones que se han marcado
        recuperaOpciones();

        //Generamos una nueva secuencia de Numeros Aleatorios
        generaNumerosAleatorios();       

        //Generamos el Panel Selector con Iconos de Gatos
        generaPanelSelector(OBJT_GATO);

        //Generamos el Panel del Jugador
        generaPanelJugada();

        //Generamos el Panel del Resultado
        generaPanelResultado();
    }//GEN-LAST:event_jBNuevaPartidaActionPerformed

    private void jCBLongitudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBLongitudActionPerformed
        //Valida si hay alguna incongluencia en las Opciones
        validaOpciones();
    }//GEN-LAST:event_jCBLongitudActionPerformed
    private void jBColoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBColoresActionPerformed
        //Establecemos los Colores como Tipo de Objeto
        generaPanelSelector(OBJT_COLOR);
    }//GEN-LAST:event_jBColoresActionPerformed
    private void jBIconosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBIconosActionPerformed
        //Establecemos los Iconos como Tipo de Objeto
        generaPanelSelector(OBJT_GATO);
    }//GEN-LAST:event_jBIconosActionPerformed
    private void jBLetrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLetrasActionPerformed
        //Establecemos las Letras como Tipo de Objeto
        generaPanelSelector(OBJT_LETRA);
    }//GEN-LAST:event_jBLetrasActionPerformed
    private void jBNumerosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNumerosActionPerformed
        //Establecemos los Numeros como Tipo de Objeto
        generaPanelSelector(OBJT_NUMER);
    }//GEN-LAST:event_jBNumerosActionPerformed

    private void jCBDuplicadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBDuplicadosActionPerformed
        //Valida si hay alguna incongluencia en las Opciones
        validaOpciones();
    }//GEN-LAST:event_jCBDuplicadosActionPerformed

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
    private javax.swing.JButton jBColores;
    private javax.swing.JButton jBIconos;
    private javax.swing.JButton jBLetras;
    private javax.swing.JButton jBNuevaPartida;
    private javax.swing.JButton jBNumeros;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCBDuplicados;
    private javax.swing.JComboBox jCBLongitud;
    private javax.swing.JComboBox jCBObjetos;
    private javax.swing.JCheckBox jChkNivAuto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPOpciones;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelJugada;
    private javax.swing.JPanel jPanelResult;
    private javax.swing.JPanel jPanelSelector;
    private javax.swing.JTabbedPane jTabbedPane2;
    // End of variables declaration//GEN-END:variables
}
