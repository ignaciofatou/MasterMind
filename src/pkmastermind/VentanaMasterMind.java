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

    //Declaramos la Ventana Modal
    VentanaOpciones opcModal = new VentanaOpciones(this, true);
    
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
        
        //Generamos una nueva secuencia de Numeros Aleatorios
        generaNumerosAleatorios();       

        //Generamos el Panel Selector con Iconos de Gatos
        generaPanelSelector();
        
        //Generamos el Panel del Jugador
        //generaPanelJugada();
        
        //Generamos el Panel del Resultado
        //generaPanelResultado();
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
        numerosGenerados     = new int[longitud];
        
        //Inicializamos Matriz para los Numeros Que vamos a Seleccionar (*)
        numerosSeleccionados = new int[longitud];
        
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
    public void generaPanelSelector(){

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
        
        //Recuperamos la Longitud de Columnas
        int longitud = opcModal.getLongitud();

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
        
        //Recuperamos el Numero de Objetos
        int numObjetos = opcModal.getNumObjetos();

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

        jBNuevaPartida = new javax.swing.JButton();
        jBOpciones = new javax.swing.JButton();
        jPanelSelector = new javax.swing.JPanel();
        jPanelJugada = new javax.swing.JPanel();
        jPanelResult = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MasterMind");

        jBNuevaPartida.setText("Nueva Partida");
        jBNuevaPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNuevaPartidaActionPerformed(evt);
            }
        });

        jBOpciones.setText("Opciones");
        jBOpciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBOpcionesActionPerformed(evt);
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
            .addGap(0, 441, Short.MAX_VALUE)
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBNuevaPartida)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBOpciones)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanelSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelJugada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanelResult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBNuevaPartida)
                    .addComponent(jBOpciones))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelResult, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelJugada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelSelector, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE))
                .addGap(75, 75, 75))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBOpcionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBOpcionesActionPerformed
        //Abrimos la Ventana Opciones en Modo Modal
        opcModal.setVisible(true);
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
    // End of variables declaration//GEN-END:variables
}
