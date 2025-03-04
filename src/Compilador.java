import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import compilerTools.CodeBlock;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import compilerTools.Directory;
import compilerTools.ErrorLSSL;
import compilerTools.Functions;
import compilerTools.Grammar;
import compilerTools.Production;
import compilerTools.TextColor;
import compilerTools.Token;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.Timer;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author rebek
 */
public class Compilador extends javax.swing.JFrame {
    private String titulo;
    private Directory directorio;
    private ArrayList<Token> tokens;
    
    private ArrayList<ErrorLSSL> errores;
    private ArrayList<TextColor> textocolor;//Colores de palabras reservadas
    private Timer timerKeyReleased; //pintar

    private boolean estadoCompilacion = false;
    private tablaSimbolosIdent tabla;
    private tablaListas tablaV;
    private tablaTokens tablaT;

    private ArrayList<String[]> simbolos;
    private ArrayList<String[]> metodosDeclarados;
    private ArrayList<String[]> idVector;
    private ArrayList<String[]> lisTokens;

    //PARA EL SEMANTICO
    private ArrayList<Production> identValor;//DECLARACION con valorr
    private ArrayList<Production> asign;
    private ArrayList<Production> FuncionesMovimiento;
    private ArrayList<Production> idSValor;
    private ArrayList<Production> decVecVal;
    private ArrayList<Production> FuncionAlarma;
    private ArrayList<Production> FuncionCaja;
    private ArrayList<Production> FuncionRevisar;
    private ArrayList<Production> FuncionImprimir;
    private ArrayList<Production> FuncionImprimirVec;
    private ArrayList<Production> DefinirMetodos;
    private ArrayList<Production> funcRepetir;
    private TreeMap<Integer, String> mapaMetodos = new TreeMap<>();
    
    private ArrayList<Production> asignVec;
    private ArrayList<Production> expRel;
    
    //Codigo intermedio
    String codigoIntermedio;
    String cuidameloTantito;
    //PARA el ASM
    String data,code,proc;
    //rest

    /**
     * Creates new form Compilador
     */
    public Compilador() {
        initComponents();
        init();
        for (int i = 0; i < 24000; i++) // Default Height of cmd is 300 and Default width is 80
        {
            System.out.println("\b"); // Prints a backspace
        }
    }

    private void init() {
        titulo = "Rusty";
        setLocationRelativeTo(null);
        tabla = new tablaSimbolosIdent(this, true);
        tablaV = new tablaListas(this, true);
        tablaT = new tablaTokens(this, true);
        //rest

        setTitle(titulo);
        directorio = new Directory(this, areaCodigo, titulo, ".ware");
        addWindowListener(new WindowAdapter() {// Método para preguntar al cerrar, si no se ha guardado
            @Override
            public void windowClosing(WindowEvent e) {
                directorio.Exit();
                System.exit(0);
            }
        });
        Functions.setLineNumberOnJTextComponent(areaCodigo); //Funcion para numero de linea
        timerKeyReleased = new Timer((int) (100 * 0.3), (ActionEvent e) -> {
            timerKeyReleased.stop();

            int posicion = areaCodigo.getCaretPosition();
            areaCodigo.setText(areaCodigo.getText().replaceAll("[\r]+", ""));
            areaCodigo.setCaretPosition(posicion);

            cambioColor();
        });
        Functions.insertAsteriskInName(this, areaCodigo, () -> {
            timerKeyReleased.restart();
        }); //Al momento de hacer una edición se pone un asteriso en el titulo
        // y se llama al metodo timerKeyReleased

        //INICIALIZACIÓN
        tokens = new ArrayList<>();
        lisTokens = new ArrayList<>();
        simbolos = new ArrayList<>();
        metodosDeclarados = new ArrayList<>();
        idVector = new ArrayList<>();

        //modificacion para hacer el semantico
        identValor = new ArrayList<>();//Asignacion con valor
        asign = new ArrayList<>();
        FuncionesMovimiento = new ArrayList<>();
        idSValor = new ArrayList<>();
        FuncionAlarma = new ArrayList<>();
        FuncionCaja = new ArrayList<>();
        FuncionRevisar = new ArrayList<>();
        FuncionImprimir = new ArrayList<>();
        FuncionImprimirVec = new ArrayList<>();
        DefinirMetodos = new ArrayList<>();
        expRel = new ArrayList<>();
        decVecVal = new ArrayList<>();
        asignVec = new ArrayList<>();
        funcRepetir = new ArrayList<>();
        errores = new ArrayList();
        textocolor = new ArrayList<>();
        
        //Generar codigo intermedio
        codigoIntermedio = "";
        cuidameloTantito = "";
        //Generar el asm
        data="";
        code="";
        proc="";
        
        estadoCompilacion = false;
        //tablaSimbolos.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        areaCodigo = new javax.swing.JTextPane();
        panelButtonCompilerExecute = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        consola = new javax.swing.JTextPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnAbrir = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnGuardarC = new javax.swing.JButton();
        btnCompilar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 255, 255));

        areaCodigo.setBackground(new java.awt.Color(204, 204, 204));
        areaCodigo.setFont(new java.awt.Font("Courier New", 0, 16)); // NOI18N
        areaCodigo.setCaretColor(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(areaCodigo);

        panelButtonCompilerExecute.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Estructuras de la Compilación", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(241, 170, 17))); // NOI18N

        jButton1.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/9928199 (4).png"))); // NOI18N
        jButton1.setText("Tokens");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/9928199 (4).png"))); // NOI18N
        jButton3.setText("Simbolos");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelButtonCompilerExecuteLayout = new javax.swing.GroupLayout(panelButtonCompilerExecute);
        panelButtonCompilerExecute.setLayout(panelButtonCompilerExecuteLayout);
        panelButtonCompilerExecuteLayout.setHorizontalGroup(
            panelButtonCompilerExecuteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelButtonCompilerExecuteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(145, 145, 145))
        );
        panelButtonCompilerExecuteLayout.setVerticalGroup(
            panelButtonCompilerExecuteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelButtonCompilerExecuteLayout.createSequentialGroup()
                .addGroup(panelButtonCompilerExecuteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jScrollPane2.setBackground(new java.awt.Color(204, 255, 255));

        consola.setBackground(new java.awt.Color(204, 204, 204));
        consola.setForeground(new java.awt.Color(51, 51, 51));
        jScrollPane2.setViewportView(consola);

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/escudo_itt_grande (1).png"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/LOGO-VERTICAL-TECNM (3).png"))); // NOI18N
        jLabel4.setToolTipText("");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Equipo:");

        jLabel7.setFont(new java.awt.Font("Franklin Gothic Medium Cond", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Lenguajes y Autómatas II ");

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("- Natividad Aguilera Andrick Joksan");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("- Hernández Arvizu Dulce Adilene");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("- Orozco Estarrón Amir");

        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("- Vargas Murillo Otmar Fidel");

        jLabel12.setFont(new java.awt.Font("Franklin Gothic Medium Cond", 1, 48)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Rusty");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/logo_campana (1).png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(48, 48, 48)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(145, 145, 145))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(88, 88, 88)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addGap(80, 80, 80))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel11)
                                .addComponent(jLabel9))
                            .addContainerGap()))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel1)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10))
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(29, 29, 29))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Herramientas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(232, 179, 25))); // NOI18N

        btnNuevo.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Create_New-80_icon-icons.com_57345.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnAbrir.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/62917openfilefolder_109270.png"))); // NOI18N
        btnAbrir.setText("Abrir");
        btnAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirActionPerformed(evt);
            }
        });

        btnGuardar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Save_37110.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnGuardarC.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnGuardarC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/diskette_save_saveas_1514.png"))); // NOI18N
        btnGuardarC.setText("Guardar como");
        btnGuardarC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCActionPerformed(evt);
            }
        });

        btnCompilar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnCompilar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/1486485588-add-create-new-math-sign-cross-plus_81186.png"))); // NOI18N
        btnCompilar.setText("Compilar");
        btnCompilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompilarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAbrir, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardarC, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCompilar)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnAbrir)
                    .addComponent(btnGuardar)
                    .addComponent(btnGuardarC)
                    .addComponent(btnCompilar))
                .addGap(0, 1, Short.MAX_VALUE))
        );

        jMenuBar1.setBackground(java.awt.SystemColor.textHighlight);
        jMenuBar1.setForeground(new java.awt.Color(255, 153, 0));
        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                        .addComponent(panelButtonCompilerExecute, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))
                    .addComponent(jScrollPane2))
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelButtonCompilerExecute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        panelButtonCompilerExecute.getAccessibleContext().setAccessibleName("");
        jPanel3.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirActionPerformed
        if (directorio.Open()) {
            cambioColor();
            limpiarAreaCodigo();
        }
    }//GEN-LAST:event_btnAbrirActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        directorio.New();
        limpiarAreaCodigo();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (directorio.Save()) {
            limpiarAreaCodigo();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnGuardarCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCActionPerformed
        if (directorio.SaveAs()) {
            limpiarAreaCodigo();
        }
    }//GEN-LAST:event_btnGuardarCActionPerformed

    private void btnCompilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompilarActionPerformed
        if (getTitle().contains("*") || getTitle().equals(titulo)) { //Si no se ha guardadp se llama a SAVE()
            if (directorio.Save()) {
                compile();
            }
        } else {
            compile();
        }
    }//GEN-LAST:event_btnCompilarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        tablaT.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       tabla.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void compile() {
        limpiarAreaCodigo();
        analisisLexico();
        analisisSintactico();
        analisisSemantico();
        TablaTokens();
        tablasSimbolos();
        imprimirConsola();
        
        estadoCompilacion = true;

        //Se genera el codigo intermedio aqui en caliente.
        if(errores.isEmpty()){
            ArrayList<String> codigoDividido = Functions.splitCodeInCodeBlocks(tokens, "{", "}", ";").getBlocksOfCodeInOrderOfExec();
            //rest
        }else{
            JOptionPane.showMessageDialog(null, "No se puede generar el codigo intermedio porque el programa contiene errores...",
                    "Error en la generacion de codigo", JOptionPane.ERROR_MESSAGE);
            return;
        }
        codigoIntermedio+=" \n"+cuidameloTantito;
        data+=code+proc+"END";
        //rest
        //rest
        
        averQPedo();
        
        jButton1.setEnabled(estadoCompilacion);
    }

    public void tablasSimbolos() {
        tablaT.setSimbolos(lisTokens);
        tablaT.llenarTabla();
        tabla.setSimbolos(simbolos);
        tabla.llenarTabla();
        tablaV.setSimbolos(idVector);
        tablaV.llenarTabla();
    }

    private void analisisLexico() {
        // Extraer tokens
        Lexema lexema;
        try {
            File codigo = new File("code.encrypter");
            FileOutputStream output = new FileOutputStream(codigo);
            byte[] bytesText = areaCodigo.getText().getBytes();
            output.write(bytesText);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(codigo), "UTF8"));
            lexema = new Lexema(entrada);
            while (true) {
                Token token = lexema.yylex();
                if (token == null) {
                    break;
                }
                tokens.add(token);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo no pudo ser encontrado... " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error al escribir en el archivo... " + ex.getMessage());
        }
    }

    private void analisisSintactico() {
        Grammar gramatica = new Grammar(tokens, errores);

        //Eliminacin de errores Lexicos
        gramatica.delete("ERROR", 1, "Error léxico ({}): En la línea #, Cadena inválida");

        gramatica.delete("ERROR_1", 2, "Error Léxico ({}): En la línea #, Cadena no reconocida");

        gramatica.delete("ERROR_2", 3, "Error Léxico ({}): En la línea #, El número no es válido, quitar el 0 al inicio");

        gramatica.delete("ERROR_3", 4, "Error Léxico ({}): En la línea #, Identificador no cumple con la estructura, inicia con mayuscula o numero");

        gramatica.delete("ERROR_4", 5, "Error Léxico ({}): En la línea #, Faltó cerrar cadena con apóstrofe");

        gramatica.delete("ERROR_5", 6, "Error Léxico ({}): En la línea #, Numero decimal no válido, puntos demás o faltó decimal");

        gramatica.delete("ERROR_6", 7, "Error Léxico ({}): En la línea #, Operador incompleto");

        gramatica.delete("ERROR_7", 9, "Error Léxico ({}): En la línea #, Cadena no reconocida");

        //production - ArrayList de Producciones donde deseamos guardar las agrupaciones realizadas
        
     //************************* AGRUPACIONES ******************
        gramatica.group("Numero", "(Numero_Entero | Numero_Decimal | Numero_Mini)");
        gramatica.group("Valor", "(Hexadecimal | Verdadero | Falso  | Cadena)");
        gramatica.group("TipoDato", "(FREC | ENT | COLOR | DEC | BOOL | CAD | MINI)");
        gramatica.group("Op_Adisi", "(Op_Sum | Op_Res)");
        gramatica.group("Op_Multi", "(Op_Mul | Op_Div)");
        gramatica.group("Op_Asign", "(Op_MasIgual | Op_MenosIgual)");
        gramatica.group("OpRel", "(OpRel_Igual | OpRel_NoIgual | OpRel_Menor | OpRel_Mayor | OpRel_MenorIg | OpRel_MayorIg)");
        gramatica.group("OpLog", "(OpLog_Y | OpLog_O | OpLgo_NO)");
        gramatica.group("OpImpr", "(CONSOL| LCD)");
        gramatica.group("MetodoSP", "F_SOLTAR | APAGAR | PRENDER | F_LIMPIAR | F_TOMAR | F_PARAR ");
        gramatica.group("Metodo", "F_SOLTAR | APAGAR | PRENDER | F_LIMPIAR | F_TOMAR | F_PARAR ");
        
        //***************************************** VECTORES *******************************
        
        gramatica.group("DeclaracionVect", "VECTOR TipoDato Identificador Corch_abr (Numero|Valor) Corch_cer ",decVecVal);
       
        gramatica.group("DeclaracionVect", "VECTOR TipoDato Identificador Op_asignacion Corch_abr"
                + " ((Valor | Numero) | (Valor | Numero) Coma)+ Corch_cer ", decVecVal);

        //Errores
        gramatica.group("DeclaracionVect", " VECTOR TipoDato Identificador Op_asignacion  Corch_abr (Corch_abr)+"
                + " ((Valor | Numero) | (Valor | Numero) Coma)* (Corch_cer)+", 51,
                "Error sintáctico (51): En la línea #, Existen corchetes de más.");
        
         gramatica.group("DeclaracionVect", " VECTOR TipoDato Identificador Op_asignacion (Corch_abr)+ "
                + " ((Valor | Numero) | (Valor | Numero) Coma)* Corch_cer Corch_cer (Corch_cer)+", 51,
                "Error sintáctico (51): En la línea #, Existen corchetes de más.");  
         
         gramatica.group("DeclaracionVect", " VECTOR TipoDato Identificador (Op_Adisi|Op_Multi|OpRel|OpLog) (Corch_abr)+"
                + " ((Valor | Numero) | (Valor | Numero) Coma)* (Corch_cer)+", 52,
                "Error sintáctico (52): En la línea #, Operador invalido para la asignación.");
        
        
        gramatica.group("DeclaracionVect", "VECTOR TipoDato (Corch_abr)+ Corch_cer ", 12,
                "Error sintáctico (15): En la línea #, Falta un identificador en la declaración.");
        
        gramatica.group("DeclaracionVect", " VECTOR TipoDato  Op_asignacion (Corch_abr)+"
                + " ((Valor | Numero) | (Valor | Numero) Coma)+ (Corch_cer)+ ",15,
                "Error sintáctico (16): En la línea #, Falta palabra clave para poder declarar.");
        
        gramatica.group("DeclaracionVect", "TipoDato (Corch_abr)+ (Corch_cer)+ ", 15,
                "Error sintáctico (16): En la línea #, Falta palabra clave para poder declarar.");

        gramatica.group("DeclaracionVect", "TipoDato Identificador Op_asignacion Corch_abr"
                + " ((Valor | Numero) | (Valor | Numero) Coma)+ (Corch_cer)+ ", 15,
                "Error sintáctico (16): En la línea #, Falta palabra clave para poder declarar.");

        gramatica.group("DeclaracionVect", "VECTOR TipoDato Identificador Op_asignacion (Corch_abr)+"
                + " (Coma)+ Corch_cer ", 16,
                "Error sintáctico (26): En la línea #, Faltan por asignar valores en la lista");

        gramatica.group("DeclaracionVect", "VECTOR TipoDato Identificador Op_asignacion (Corch_abr)+"
                + " ((Valor | Numero) Coma)+ (Corch_cer)+", 16,
                "Error sintáctico (26): En la línea #, Faltan por asignar valores en la lista");
        gramatica.finalLineColumn();

        

        //*********************************** DECLARACION DE VARIABLES ASIGNADA **********************************
        gramatica.group("DeclaracionAsignada", "CONF TipoDato Identificador Op_asignacion (Valor|Numero)", identValor);
        
        gramatica.group("DeclaracionAsignada", "TipoDato Identificador (Op_Adisi|Op_Multi|OpRel|OpLog) (Valor|Numero)", 52,
                "Error sintáctico (52): En la línea #, Operador invalido para la asignación.");

        gramatica.group("DeclaracionAsignada", "TipoDato Identificador Op_asignacion (Valor|Numero)", 15,
                "Error sintáctico (16): En la línea #, Falta palabra clave para poder declarar.");

        gramatica.group("DeclaracionAsignada", "CONF Identificador Op_asignacion (Valor|Numero)", 14,
                "Error sintáctico (14): En la línea #, No está definido el tipo de dato en la declaración.");

        gramatica.group("DeclaracionAsignada", "CONF Op_asignacion (Valor|Numero)", 12,
                "Error sintáctico (15): En la línea #, Falta un identificador en la declaración.");
        
        gramatica.group("DeclaracionAsignada", "CONF TipoDato Op_asignacion (Valor|Numero)", 12,
                "Error sintáctico (15): En la línea #, Falta un identificador en la declaración.");

        gramatica.group("DeclaracionAsignada", "CONF TipoDato Identificador Op_asignacion ", 13,
                "Error sintáctico (23): En la línea #, Falta valor a asignar a la variable");

        gramatica.group("DeclaracionAsignada", "CONF TipoDato Identificador  (Valor|Numero)", 17,
                "Error sintáctico (25): En la línea #, Falta símbolo de asignación");
        gramatica.finalLineColumn();

       
        
        // ******************************* DECLARACION SIMPLE *******************************
        gramatica.group("DeclaracionVariable", "CONF TipoDato Identificador", idSValor );

        // Errores
        gramatica.group("DeclaracionVariable", "TipoDato Identificador", 15,
                "Error sintáctico (16): En la línea #, Falta palabra clave CONF para poder declarar.");

        gramatica.group("DeclaracionVariable", "CONF Identificador", 14,
                "Error sintáctico (14): En la línea #, No está definido el tipo de dato en la declaración.");

        gramatica.group("DeclaracionVariable", "CONF TipoDato ", 12,
                "Error sintáctico (15): En la línea #, Falta un identificador en la declaración.");

        gramatica.group("DeclaracionVariable", "Identificador Punto_coma", 14,
                "Error sintáctico (14): En la línea #, No está definido el tipo de dato en la declaración.");

        gramatica.finalLineColumn();
   
        
        //************************************** DECLARACIONES ************************************
        gramatica.loopForFunExecUntilChangeNotDetected(() -> {
            gramatica.group("Declaracion", "(DeclaracionVariable Punto_coma  | DeclaracionAsignada  Punto_coma | DeclaracionVect Punto_coma | DeclaracionMatriz Punto_coma )");
            gramatica.group("Declaracion", "(DeclaracionVariable | DeclaracionAsignada | DeclaracionVect | DeclaracionMatriz)", 11,
                    "Error sintáctico (9): En la línea #, Falta punto y coma al final de la linea");
            gramatica.group("Declaraciones", "(Declaracion | Declaracion Declaraciones)*");
            gramatica.finalLineColumn();
        });
              
        
        
        //*************************** MÉTODOS ESTÁTICOS (MetodoSP) ***************************************
    
        gramatica.group("MetodoEstatique", "MetodoSP Par_abr Par_cer"); 
        
        
        gramatica.group("MetodoEstatique", "MetodoSP",57,
                "Error sintáctico (49): En la línea #, Faltan parentésis despúes de la palabra reservada.");
          
        gramatica.group("MetodoEstatique", "MetodoSP Par_abr (Valor | Numero| Identificador| ERROR_7)Par_cer ",50,
                "Error sintáctico (50): En la línea #, Sintaxis de método incorrecta.");
                     
        gramatica.group("MetodoEstatique", "MetodoSP Par_abr ",19,
                "Error sintáctico (19): En la línea #, Estructura inválida del método.");
        
        gramatica.group("MetodoEstatique", "MetodoSP  Par_cer ",19,
                "Error sintáctico (19): En la línea #, Estructura inválida del método.");
        
        gramatica.group("MetodoEstatique", "MetodoSP (Par_abr)+ Par_cer (Par_cer)+ ",49,
                "Error sintáctico (49): En la línea #, Existen paréntesis de más.");
        
        gramatica.group("MetodoEstatique", "MetodoSP (Par_abr)+ Par_abr (Par_cer)+ ",49,
                "Error sintáctico (49): En la línea #, Existen paréntesis de más.");
               
  
        
        // **********************  DECLARACION DE METODOS **********************************
        gramatica.group("DeclaracionMetodo", "DEF (Identificador|Expresion) Par_abr Par_cer",DefinirMetodos);
        
        gramatica.group("DeclaracionMetodoError", "(Identificador|Expresion) Par_abr Par_cer Llav_abr");
        
        //*************  LLAMADA A METODOS SIN PARAMETROS ******************* 
        gramatica.group("MetodoEstatique", "(Identificador|Expresion) Par_abr Par_cer");
        
//        gramatica.group("MetodoEstatique", "(Identificador|Expresion) Par_cer",52,
//                "Error sintáctico (52): En la línea #, Falta abrir o cerrar paréntesis.");
        
        gramatica.group("MetodoEstatique", "(Identificador|Expresion) Par_abr ",52,
                "Error sintáctico (52): En la línea #, Falta abrir o cerrar paréntesis.");
                 
        gramatica.group("MetodoEstatique", "(Identificador|Expresion) (Par_abr)+ Par_cer (Par_cer)+ Punto_coma",49,
                "Error sintáctico (49): En la línea #, Existen paréntesis de más."); 
        
        gramatica.group("MetodoEstatique", "(Identificador|Expresion) (Par_abr)+ Par_abr (Par_cer)+ Punto_coma",49,
                "Error sintáctico (49): En la línea #, Existen paréntesis de más."); 
                      
        gramatica.group("DeclaracionMetodo", "(Identificador|Expresion) Parametros ",44,
                "Error sintáctico (44): En la línea #, Falta palabra clave para definir método.");
      
        gramatica.group("DeclaracionMetodo", "DEF Par_abr Par_cer",45,
                "Error sintáctico (45): En la línea #, Falta nombre del método.");
        

                
        //********************************** EST ALARMA ***************************
        gramatica.group("EstAlarma", "F_ALARMA Par_abr (Identificador) Par_cer",FuncionAlarma);
        gramatica.group("EstAlarma", "F_ALARMA Par_abr (Numero|Valor) Par_cer",FuncionAlarma);
       
        // ERRORES ALARMA
        gramatica.group("EstAlarma", "F_ALARMA Par_abr Par_cer", 20,
                "Error sintáctico (23): En la línea #, Falta un valor en método.");
        
        gramatica.group("EstAlarma", "F_ALARMA Par_abr (Identificador | Numero)", 18,
                "Error sintáctico (18): En la línea #, Falta abrir o cerrar paréntesis ().");
        
        gramatica.group("EstAlarma", "F_ALARMA Par_abr (Identificador | Numero)  ", 18,
                "Error sintáctico (18): En la línea #, Falta abrir o cerrar paréntesis ().");
        
        gramatica.group("EstAlarma", "F_ALARMA  (Par_abr)+ (Identificador | Numero) Par_cer (Par_cer)+ ",49,
                "Error sintáctico (49): En la línea #, Existen paréntesis de más."); 
        
        gramatica.group("EstAlarma", "F_ALARMA (Par_abr)+ Par_abr (Identificador | Numero) (Par_cer)+ ",49,
                "Error sintáctico (49): En la línea #, Existen paréntesis de más."); 

        gramatica.group("EstAlarma", "F_ALARMA (Identificador | Numero) Par_cer", 18,
                "Error sintáctico (18): En la línea #, Falta abrir o cerrar paréntesis ().");
        
        gramatica.group("EstAlarma", "F_ALARMA", 19,
                "Error sintáctico (19): En la línea #, Estructura inválida del método.");
     
        
        gramatica.finalLineColumn();
        
            //************************************  F_MOVIMIENTO    ****************************
        gramatica.group("F_MOV", "(F_ADELANTE | F_ATRAS | F_DERECHA | F_IZQUIERDA) "
                + "Par_abr (Identificador | Valor  | Numero ) Coma (Identificador | Valor | Numero ) Par_cer", FuncionesMovimiento);
        
                
        gramatica.group("F_MOV", "(F_ADELANTE | F_ATRAS | F_DERECHA | F_IZQUIERDA) "
                + "Par_abr  Coma (Numero | Identificador) Par_cer  ",20,
                "Error sintáctico (23): En la línea #, Falta un valor en método."); 
        
        
        gramatica.group("F_MOV", "(F_ADELANTE | F_ATRAS | F_DERECHA | F_IZQUIERDA) "
                + "Par_abr (Numero| Identificador) (Coma)+ (Numero | Identificador) Par_cer ",55,
                    "Error sintáctico (55): En la línea #, Sintaxis incorrecta comas (,) de más");
        
        gramatica.group("F_MOV", "(F_ADELANTE | F_ATRAS | F_DERECHA | F_IZQUIERDA) "
                + "Par_abr (Numero| Identificador) Coma Par_cer  ",20,
                "Error sintáctico (23): En la línea #, Falta un valor en método.");
        
        gramatica.group("F_MOV", "(F_ADELANTE | F_ATRAS | F_DERECHA | F_IZQUIERDA) (Par_abr)+ (Numero| Identificador)"
                + " Coma (Numero | Identificador) Par_cer Par_cer (Par_cer)+ ",49,
                "Error sintáctico (49): En la línea #, Existen paréntesis de más."); 
        
        gramatica.group("F_MOV", "(F_ADELANTE | F_ATRAS | F_DERECHA | F_IZQUIERDA) Par_abr Par_abr (Par_abr)+ "
                + " (Numero| Identificador) Coma (Numero | Identificador) (Par_cer)+ ",49,
                "Error sintáctico (49): En la línea #, Existen paréntesis de más.");
  
        gramatica.group("F_MOV", "(F_ADELANTE | F_ATRAS | F_DERECHA | F_IZQUIERDA) "
                + "Par_abr  Coma  Par_cer  ",20,
                "Error sintáctico (23): En la línea #, Falta un valor en método.");
         
         gramatica.group("F_MOV", "(F_ADELANTE | F_ATRAS | F_DERECHA | F_IZQUIERDA) "
                + "Par_abr  Par_cer ",43,
                "Error sintáctico (43): En la línea #, Faltan valores en el método");
        
        gramatica.group("F_MOV", "(F_ADELANTE | F_ATRAS | F_DERECHA | F_IZQUIERDA) "
                + "Par_abr (Numero| Identificador) Coma (Numero | Identificador) ",18,
                "Error sintáctico (18): En la línea #, Falta abrir o cerrar paréntesis ().");
        
        gramatica.group("F_MOV", "(F_ADELANTE | F_ATRAS | F_DERECHA | F_IZQUIERDA) "
                + " (Numero| Identificador) Coma (Numero | Identificador) Par_cer  ",18,
                "Error sintáctico (18): En la línea #, Falta abrir o cerrar paréntesis ().");
        

       gramatica.group("F_MOV", "(F_ADELANTE | F_ATRAS | F_DERECHA | F_IZQUIERDA)",19,
                "Error sintáctico (19): En la línea #, Estructura inválida del método.");

       
        gramatica.finalLineColumn();
        
        
        //********************************* EST CAJA *******************************

        gramatica.group("EstCaja", "F_CAJA Par_abr (Identificador |Valor| Numero) Par_cer", FuncionCaja);
        // ERRORES CAJa 
        gramatica.group("EstCaja", "F_CAJA ", 11,
                "Error sintáctico (11): En la línea #, Falta abrir o cerrar paréntesis ().");
                
        gramatica.group("EstCaja", "F_CAJA Par_abr  Par_cer", 16,
                "Error sintáctico (16): En la línea #, Estructura inválida del método falta un valor.");
        
        gramatica.group("EstCaja", "F_CAJA (Identificador | Numero) Par_cer", 11,
                "Error sintáctico (11): En la línea #, Falta abrir o cerrar paréntesis ().");
        
        gramatica.group("EstCaja", "F_CAJA Par_abr (Identificador | Numero) ", 11,
                "Error sintáctico (11): En la línea #, Falta abrir o cerrar paréntesis ().");
        
        gramatica.group("EstCaja", "F_CAJA  (Par_abr)+ (Identificador | Numero) Par_cer (Par_cer)+ ",49,
                "Error sintáctico (49): En la línea #, Existen paréntesis de más."); 
        
        gramatica.group("EstCaja", "F_CAJA  (Par_abr)+ Par_abr (Identificador | Numero) (Par_cer)+ ",49,
                "Error sintáctico (49): En la línea #, Existen paréntesis de más."); 
        
            gramatica.finalLineColumn();
                
       
    //**********************************METODOS IMPRM  ***********************
        gramatica.group("EstImpr", "F_IMPR Par_abr (Valor | Numero | Identificador |Expresion) Coma (Valor| Numero | Identificador|Expresion|OpImpr | ERROR_7| ERROR|ERROR_1) Par_cer", FuncionImprimir);
        
        gramatica.group("EstImpr", "F_IMPR ",19,
                "Error sintáctico (19): En la línea #, Estructura inválida del método.");
           
        gramatica.group("EstImpr", "F_IMPR Par_abr Numero Coma OpImpr Par_cer",32,
                "Error sintáctico (32): En la línea #, No se permite un numero como mensaje a imprimir.");
        
        gramatica.group("EstImpr", "F_IMPR Par_abr Par_cer",20,
                "Error sintáctico (27): En la línea #, Falta valores en los parámetros.");
        gramatica.group("EstImpr", "F_IMPR  (Par_abr)+ (Valor|metListaSacar) Coma OpImpr Par_cer (Par_cer)+ ",49,
                "Error sintáctico (49): En la línea #, Existen paréntesis de más."); 
        
        gramatica.group("EstImpr", "F_IMPR (Par_abr)+ Par_abr (Valor|metListaSacar) Coma OpImpr (Par_cer)+ ",49,
                "Error sintáctico (49): En la línea #, Existen paréntesis de más."); 
        
        gramatica.group("EstImpr", "F_IMPR Par_abr Coma Par_cer",20,
                "Error sintáctico (27): En la línea #, Falta valores en los parámetros.");
        
        gramatica.group("EstImpr", "F_IMPR Par_abr Valor Coma Par_cer",30,
                "Error sintáctico (30): En la línea #, Falta donde se imprimirá el mensaje.");
        
        gramatica.group("EstImpr", "F_IMPR Par_abr Coma OpImpr Par_cer",31,
                "Error sintáctico (31): En la línea #, Falta mensaje a imprimir.");
        
        gramatica.group("EstImpr", "F_IMPR Par_abr Valor Coma OpImpr ",18,
                "Error sintáctico (18): En la línea #, Falta abrir o cerrar paréntesis ().");
        
         gramatica.group("EstImpr", "F_IMPR Valor Coma OpImpr Par_cer ",18,
                "Error sintáctico (18): En la línea #, Falta abrir o cerrar paréntesis ().");
        
        
         gramatica.finalLineColumn(); 
         
         
        
           
       //**********************************METODOS IMPRM VECTOR ***********************
        gramatica.group("EstImprVec", "ImprVector Par_abr (Valor | Numero | Identificador |Expresion) Coma (Valor| Numero | Identificador|Expresion) Par_cer", FuncionImprimirVec);
        
        gramatica.group("EstImprVec", "ImprVector",19,
                "Error sintáctico (19): En la línea #, Estructura inválida del método.");
           
        gramatica.group("EstImprVec", "ImprVector Par_abr Numero Coma OpImpr Par_cer",32,
                "Error sintáctico (32): En la línea #, No se permite un numero como mensaje a imprimir.");
        
        gramatica.group("EstImprVec", "ImprVector Par_abr Par_cer",20,
                "Error sintáctico (27): En la línea #, Falta valores en los parámetros.");
        gramatica.group("EstImprVec", "F_IMPR  (Par_abr)+ (Valor|metListaSacar) Coma OpImpr Par_cer (Par_cer)+ ",49,
                "Error sintáctico (49): En la línea #, Existen paréntesis de más."); 
        
        gramatica.group("EstImprVec", "ImprVector (Par_abr)+ Par_abr (Valor|metListaSacar) Coma OpImpr (Par_cer)+ ",49,
                "Error sintáctico (49): En la línea #, Existen paréntesis de más."); 
        
        gramatica.group("EstImprVec", "ImprVector Par_abr Coma Par_cer",20,
                "Error sintáctico (27): En la línea #, Falta valores en los parámetros.");
        
        gramatica.group("EstImprVec", "ImprVector Par_abr Valor Coma Par_cer",30,
                "Error sintáctico (30): En la línea #, Falta donde se imprimirá el mensaje.");
        
        gramatica.group("EstImprVec", "ImprVector Par_abr Coma OpImpr Par_cer",31,
                "Error sintáctico (31): En la línea #, Falta mensaje a imprimir.");
        
        gramatica.group("EstImprVec", "ImprVector Par_abr Valor Coma OpImpr ",18,
                "Error sintáctico (18): En la línea #, Falta abrir o cerrar paréntesis ().");
        
         gramatica.group("EstImprVec", "ImprVector Valor Coma OpImpr Par_cer ",18,
                "Error sintáctico (18): En la línea #, Falta abrir o cerrar paréntesis ().");
        
         gramatica.finalLineColumn();   
         
         
         
         
       

        //********************************** EST REVISAR **************************
        gramatica.group("EstRevisar", "F_REVISAR Par_abr (Valor|Identificador|Numero) Coma (Valor |Identificador | Numero) Par_cer", FuncionRevisar);
        
                 
        gramatica.group("EstRevisar", "F_REVISAR Par_abr  Par_cer",20,
                "Error sintáctico (27): En la línea #, Falta valores en los parámetros.");
        
        gramatica.group("EstRevisar", "F_REVISAR ",19,
                "Error sintáctico (19): En la línea #, Estructura inválida del método.");
        
        gramatica.group("EstRevisar", "F_REVISAR Par_abr (Valor|Identificador) (Coma)+ (Identificador | Numero) Par_cer",55,
                    "Error sintáctico (55): En la línea #, Sintaxis incorrecta comas (,) de más");
        
        gramatica.group("EstRevisar", "F_REVISAR Par_abr (Valor|Identificador) Coma (Identificador | Numero)",18,
                "Error sintáctico (18): En la línea #, Falta abrir o cerrar paréntesis ().");
        
        gramatica.group("EstRevisar", "F_REVISAR  (Valor|Identificador) Coma (Identificador | Numero) Par_cer",18,
                "Error sintáctico (18): En la línea #, Falta abrir o cerrar paréntesis ().");
        
        gramatica.group("EstRevisar", "F_REVISAR Par_abr  Coma (Identificador | Numero) Par_cer",20,
                "Error sintáctico (25): En la línea #, Falta valor en el parámetro de color.");
        
        gramatica.group("EstRevisar", "F_REVISAR Par_abr (Valor|Identificador)  Coma  Par_cer",21,
                "Error sintáctico (26): En la línea #, Falta valor en el parámetro de puerto.");
        
         gramatica.group("EstRevisar", "F_REVISAR Par_abr   (Coma)+  Par_cer",20,
                "Error sintáctico (27): En la línea #, Falta valores en los parámetros.");
         
         gramatica.group("EstRevisar", "F_REVISAR   (Par_abr)+ (Valor|Identificador)? Coma (Identificador | Numero)? Par_cer (Par_cer)+ ",49,
                "Error sintáctico (49): En la línea #, Existen paréntesis de más."); 
        
        gramatica.group("EstRevisar", "F_REVISAR (Par_abr)+ Par_abr (Valor|Identificador)? Coma (Identificador | Numero)? (Par_cer)+ ",49,
                "Error sintáctico (49): En la línea #, Existen paréntesis de más.");
         
         gramatica.group("EstRevisar", "Par_abr (Valor|Identificador) Coma (Identificador | Numero) Par_cer", 19,
                "Error sintáctico (19): En la línea #, Estructura inválida del método.");
        gramatica.finalLineColumn();
        
 
        
    
        
        

     
        
        
        //******************************  ESTRUCTUTA REPETIR   *********************************
        gramatica.group("EstRepetir", "Est_REPETIR Par_abr (Numero | Identificador | metListaRet) Par_cer ",funcRepetir);
        // ERRORES REPETIR
        gramatica.group("EstRepetir", "Est_REPETIR Par_abr  Par_cer ",19,
                "Error sintáctico (23): En la línea #, Falta un valor."); 
        
        gramatica.group("EstRepetir", "Est_REPETIR F_CANTIDAD Par_abr (Par_abr)+ (Numero | Identificador | metListaRet) Par_cer (Par_cer)+",49,
                "Error sintáctico (49): En la línea #, Existen paréntesis de más."); 
        
        gramatica.group("EstRepetir", "Est_REPETIR ",18,
                "Error sintáctico (18): En la línea #, Falta abrir o cerrar parentesis ().");
        
        gramatica.group("EstRepetir", "Est_REPETIR (Numero | Identificador) Par_cer ",18,
                "Error sintáctico (18): En la línea #, Falta abrir o cerrar parentesis ().");
        
        gramatica.group("EstRepetir", "Est_REPETIR Par_abr (Numero | Identificador) ",18,
                "Error sintáctico (18): En la línea #, Falta abrir o cerrar parentesis ().");
        
        gramatica.group("EstRepetir", "Par_abr (Numero | Identificador) Par_cer ",15,
                "Error sintáctico (16): En la línea #, Falta palabra clave.");
        gramatica.finalLineColumn();
        
        
        
        //*************************** VER **************************************************
        gramatica.group("EstVER","(VerAdelante | VerAtras | VerDerecha | VerIzquierda) Par_abr Par_cer");
        //ERRORES
        gramatica.group("EstVER","(VerAdelante | VerAtras | VerDerecha | VerIzquierda) Par_abr (Par_abr)+ Par_cer", 49,
                "Error sintáctico (49): En la línea #, Existen paréntesis de más.");
        
        gramatica.group("EstVER","(VerAdelante | VerAtras | VerDerecha | VerIzquierda) Par_abr  (Par_cer)+ Par_cer ", 49,
                "Error sintáctico (49): En la línea #, Existen paréntesis de más.");
        
         gramatica.group("EstVER", "(VerAdelante | VerAtras | VerDerecha | VerIzquierda) Par_abr ",11,
                "Error sintáctico (11): En la línea #, Falta abrir o cerrar paréntesis.");
         
        gramatica.group("EstVER", "(VerAdelante | VerAtras | VerDerecha | VerIzquierda) Par_cer ",11,
                "Error sintáctico (11): En la línea #, Falta abrir o cerrar paréntesis.");
        
        gramatica.group("EstVER","(VerAdelante | VerAtras | VerDerecha | VerIzquierda)", 57,
                "Error sintáctico (49): En la línea #, Faltan parentésis depúes de la palabra reservada.");
        
        gramatica.group("EstVER","(VerAdelante | VerAtras | VerDerecha | VerIzquierda)"
                + " Par_abr (Valor | Numero| Identificador) Par_cer", 50,
                "Error sintáctico (50): En la línea #, Sintaxis de método incorrecta.");
        
        gramatica.group("LlamadaCond", "(EstRevisar | EstVER)");
        
        
        
         
       //***************************************** ASIGNACIONES *************************
       gramatica.group("Asignacion", "(Identificador) Op_asignacion (Expresion|Identificador|Numero|Valor)", asign);
       
       gramatica.group("Asignacion", "(Identificador)    Op_Adisi (Expresion|Identificador|Numero|Valor)",56,
                    "Error sintáctico (56): En la línea #, Operador invalido para esta operación");
       
       gramatica.group("Asignacion", "(Expresion|Identificador) Op_Asign (Expresion|Identificador|Numero) ", asign);
       
       gramatica.group("Asignacion", "(Expresion|Identificador) Op_Adisi (Expresion|Identificador|Numero) ",56,
                    "Error sintáctico (56): En la línea #, Operador invalido para esta operación");
       
       gramatica.group("Asignacion", "(Expresion|Identificador) Corch_abr (Identificador|Numero|Valor)Corch_cer Op_asignacion (Expresion|Identificador|Numero|Valor)",asignVec);
       
        gramatica.group("Asignacion", "(Identificador) Corch_abr (Identificador|Numero)Corch_cer Op_Adisi"
                + "(Expresion|Identificador|Numero|Valor)",56,
                    "Error sintáctico (56): En la línea #, Operador invalido para esta operación");
        
        
    
       
        //***************************** EXPRESIONES REL Y LOGICAS *********************************
        gramatica.loopForFunExecUntilChangeNotDetected(() -> {
            gramatica.group("ExpresionDos","LlamadaCond");
            gramatica.group("Expresion", "Valor | Numero | Identificador");
            gramatica.group("ExpRel", "(Expresion |ExpresionDos) OpRel (Expresion |ExpresionDos)",expRel);
            gramatica.group("ExpRel", "(Expresion |ExpresionDos) (Op_Adisi|Op_Asign|Op_asignacion)"
                    + "(Expresion |ExpresionDos)",56,
                    "Error sintáctico (56): En la línea #, Operador invalido para esta operación");
            gramatica.group("ExpLog", "ExpRel | ExpLog OpLog ExpLog |  ExpLog ");
         });


       
       // ******************************* Errores de asignacion ******************************
       
       gramatica.group("Asignacion", "(Expresion|Identificador) Corch_abr Corch_cer Op_asignacion (Expresion|Identificador|Numero|Valor) ",52,
               "Error sintáctico (52): En la línea #, Falta el indice del arreglo");
       
       gramatica.group("Asignacion", "(ERROR_1 |ERROR_7)* Op_Asign (Expresion|Identificador|Numero) ",53,
                "Error sintáctico (53): En la línea #, Sentencia incompleta");
       
       gramatica.group("Asignacion", "(ERROR_1 |ERROR_7)* Op_asignacion (Expresion|Identificador|Numero|Valor)",53,
                "Error sintáctico (53): En la línea #, Sentencia incompleta");
       
       gramatica.group("Asignacion", "(ERROR_1 |ERROR_7)* Corch_abr (Identificador|Numero)Corch_cer Op_asignacion"
               + " (Expresion|Identificador|Numero|Valor)",53, "Error sintáctico (53): En la línea #, Sentencia incompleta");
       
        gramatica.group("Asignacion", "(Expresion|Identificador) Op_asignacion",41,
                    "Error sintáctico (41): En la línea #, Asignación incompleta falta un valor");
        
       gramatica.group("Asignacion", "(Expresion|Identificador) Op_Asign",41,
                    "Error sintáctico (41): En la línea #, Asignación incompleta falta un valor");
       
       gramatica.group("Asignacion", "(Identificador)Corch_abr (Identificador|Numero)Corch_cer Op_asignacion (Expresion|Identificador|Numero|Valor) ",11,
               "Error sintáctico (9): En la línea #, Falta punto y coma al final de la linea");
       
       gramatica.group("Asignacion", "(Identificador) Corch_abr (Identificador|Numero)Corch_cer Op_asignacion ",41,
               "Error sintáctico (41): En la línea #, Asignación incompleta falta un valor");
       
       gramatica.group("Asignacion", "(Expresion|Identificador) (Corch_abr) Asignacion",51,
               "Error sintáctico (51): En la línea #, Falta abrir o cerrar corchetes");
       gramatica.group("Asignacion", "(Expresion|Identificador)  (Expresion|Identificador|Numero)(Corch_cer) Op_asignacion (Expresion|Identificador|Numero|Valor)",51,
               "Error sintáctico (51): En la línea #, Falta abrir o cerrar corchetes");
       
       
       
       
       gramatica.finalLineColumn();
            

        //*************************** ESTRUCTURA SII Y SINO *********************************
        gramatica.group("EstSi", "Est_SI Par_abr ExpLog Par_cer");
        
        
        gramatica.group("EstSi", "Est_SI  (Par_abr)+  ExpLog  Par_cer (Par_cer)+(Par_cer Par_cer Par_cer Par_cer)?",48,
                "Error sintáctico (48): En la línea #, Existen paréntesis de más.");
        
        gramatica.group("EstSi", "Est_SI  Par_abr (Par_abr)+ ExpLog (Par_cer)+",48,
                "Error sintáctico (48): En la línea #,Existen paréntesis de más.");   
        
        
        gramatica.group("EstSi", "Est_SI Par_abr ExpLog ",18,
                "Error sintáctico (18): En la línea #, Falta abrir o cerrar parentesis ().");
        
        gramatica.group("EstSi", "Est_SI ExpLog Par_cer ",18,
                "Error sintáctico (18): En la línea #, Falta abrir o cerrar parentesis ().");
                              
        gramatica.group("EstSi", "Est_SI Par_abr  Par_cer",39,
                "Advertencia (39): En la línea #, No existe condición a evaluar.");
        
        gramatica.finalLineColumn();
    
        gramatica.group("EstSiNO", "Est_SINO");
        


        //***************************** ESTRUCTUTA MIENTRAS *****************************
        gramatica.group("EstMientras", "Est_MIENTRAS Par_abr ExpLog Par_cer");
        
        gramatica.group("EstMientras", "Est_MIENTRAS (Par_abr)+ Par_abr ExpLog Par_cer (Par_cer)+",48,
                "Error sintáctico (18): En la línea #, Existen parentesis de más().");
        
        gramatica.group("EstMientras", "Est_MIENTRAS Par_abr ExpLog ",18,
                "Error sintáctico (18): En la línea #, Falta abrir o cerrar parentesis ().");
        
        gramatica.group("EstMientras", "Est_MIENTRAS ExpLog Par_cer ",18,
                "Error sintáctico (18): En la línea #, Falta abrir o cerrar parentesis ().");
                              
        gramatica.group("EstMientras", "Est_MIENTRAS Par_abr  Par_cer",39,
                "Advertencia (39): En la línea #, No existe condición a evaluar.");
        
        //ERROR PARA ESTRUCTURAS CONDICIONALES
        gramatica.group("EstSi", "(ERROR_1 |ERROR_7)* Par_abr ExpLog Par_cer",50,
                "Error sintáctico (50): En la línea #, Falta palabra reservada");

        
        
        //ERRORES SINGULARES 
//        
//        gramatica.group("ERRORES", "Par_abr Par_cer Punto_coma",49,
//                "Error sintáctico (49): En la línea #, Falta palabra reservada."); 
//            
//         gramatica.group("ERRORES", "(ERROR_1 | ERROR_7) Par_abr Par_cer Punto_coma",49,
//                "Error sintáctico (49): En la línea #, Falta palabra reservada"); 


        // *********************** AGRUPACION DE SENTENCIAS **********************
        gramatica.loopForFunExecUntilChangeNotDetected(() -> {          
            gramatica.group("Sentencia", " ( EstControl | SentenciasPunto | ERRORES)+");
            gramatica.group("SentenciasPunto", "(MetodoEstatique | Asignacion | ExpresionDos  | EstCaja | EstAlarma | F_MOV"
                    + "| EstImpr |EstImprVec) Punto_coma");
            gramatica.group("SentenciasPunto", "(MetodoEstatique | Asignacion | ExpresionDos | EstCaja | EstAlarma | F_MOV"
                    + "| EstImpr | EstImprVec)",11,
                    "Error sintáctico (9): En la línea #, Falta punto y coma al final de la linea");
            gramatica.group("DeclaracionesF", "(Declaraciones)+");
            gramatica.group("Sentencias", "(Sentencia | Sentencia Sentencias)+");
            gramatica.group("EstControl", "(EstMientras |  EstRepetir | EstSi | EstSiNO) Llav_abr (Sentencias|DeclaracionesF)* Llav_cer");
        });
        
          
            //errores llaves control
            gramatica.finalLineColumn(); 
            gramatica.group("EstControl", "(EstMientras |  EstRepetir | EstSi | EstSiNO) Llav_abr (Sentencias|DeclaracionesF)*",
                34, "Error sintáctico (34): En la línea #, Falta abrir o cerrar }{ llaves.");
            gramatica.initialLineColumn();
            gramatica.group("EstControl", "(EstMientras |  EstRepetir | EstSi | EstSiNO) (Sentencias|DeclaracionesF)* Llav_cer",
                34, "Error sintáctico (34): En la línea #, Falta abrir o cerrar }{ llaves.");
        
        
      
            
           //**************************** METODOS CON SENTENCIAS ******************

        //****************************** CUERPO DE CLASE *********************************************
        gramatica.loopForFunExecUntilChangeNotDetected(() -> {
        gramatica.group("DeclaracionMetodoSentencias", "DeclaracionMetodo Llav_abr (Sentencias | DeclaracionesF)* Llav_cer");

        gramatica.group("DeclaracionMetodoSentencias", "DeclaracionMetodoError (Sentencias | DeclaracionesF)* Llav_cer ",44,
                     "Error sintáctico (44): En la línea #, Falta palabra clave para definir método.");

             // Errores
        gramatica.finalLineColumn(); 
        gramatica.group("DeclaracionMetodoSentencias", "DeclaracionMetodo Llav_abr (Sentencias|DeclaracionesF)* ",
                     34, "Error sintáctico (34): En la línea #, Falta abrir o cerrar }{ llaves.");
        gramatica.initialLineColumn();
        gramatica.group("DeclaracionMetodoSentencias", "DeclaracionMetodo (Sentencias|DeclaracionesF)* Llav_cer ",
                     34, "Error sintáctico (34): En la línea #, Falta abrir o cerrar }{ llaves.");    
        
        gramatica.group("CuerpoClases", "(DeclaracionMetodoSentencias | CuerpoClases)");
         });

        
        
        ////********************** CLASEE ***********************
        gramatica.group("EstClase", "CLASE (Expresion|Identificador) Llav_abr (CuerpoClases|DeclaracionesF)* Llav_cer");
        
        gramatica.initialLineColumn();
        
        gramatica.group("EstClase", "CLASE Llav_abr (CuerpoClases|DeclaracionesF)* Llav_cer",
                35, "Error sintáctico (35): En la línea #, Falta nombrar a la clase.");
        
        gramatica.group("EstClase", "CLASE (Expresion|Identificador) (CuerpoClases|DeclaracionesF)* Llav_cer",
                34, "Error sintáctico (34): En la línea #, Falta abrir o cerrar }{ llaves.");
        
        gramatica.group("EstClase", "(ERROR_1 |ERROR_7)* (Expresion|Identificador)(CuerpoClases|DeclaracionesF)* Llav_cer",
                34, "Error sintáctico (34): En la línea #, Falta abrir o cerrar }{ llaves.");  
        
        gramatica.group("EstClase", "(Expresion|Identificador) Llav_abr (CuerpoClases|DeclaracionesF)* Llav_cer",
                36, "Error sintáctico (36): En la línea #, Falta palabra clave CLASE.");
              

        gramatica.finalLineColumn(); 
        
        gramatica.group("EstClase", "CLASE (Expresion|Identificador) Llav_abr (CuerpoClases|DeclaracionesF)*",
                34, "Error sintáctico (34): En la línea #, Falta abrir o cerrar }{ llaves.");
        
        gramatica.group("EstClase", " (ERROR_1 |ERROR_7)* (Expresion|Identificador) Llav_abr (CuerpoClases|DeclaracionesF)*",
                34, "Error sintáctico (34): En la línea #, Falta abrir o cerrar }{ llaves.");

        
        gramatica.show();
    }

    
    //****************************************** ANALISIS SEMANTICO ******************************************
    private void analisisSemantico() {
        //Asociar tipo de datos
        HashMap<String, String> idTipoDato = new HashMap<>(); 
        idTipoDato.put("ENT","Numero_Entero | Numero_Mini");
        idTipoDato.put("MINI", "Numero_Mini");
        idTipoDato.put("CAD", "Cadena");
        idTipoDato.put("DEC", "Numero_Decimal");
        idTipoDato.put("FREC", "Numero_Entero");
        idTipoDato.put("COLOR", "Hexadecimal");
        idTipoDato.put("BOOL", "Verdadero Falso");
        
        int pri =0;
         // -------------------------- ERROR SEMANTICO PARA NOMBRES DE METODOS REPETIDOS
        for(Production Metodos: DefinirMetodos){ 

                String NombreMetodo = Metodos.lexemeRank(1); 
                int lineaMetodo = Metodos.getLine();
                
                if(metodosDeclarados.size()<1){
                    metodosDeclarados.add(new String[]{NombreMetodo});
                    mapaMetodos.put(lineaMetodo, NombreMetodo);
                    continue;
                }
                
                boolean c = true;
                
                for (var Met:metodosDeclarados) {
                    if (Met[0].equals(NombreMetodo) ) {
                        errores.add(new ErrorLSSL(61, "Error Semantico {} en la linea #, el nombre de este método ya existe.", Metodos, true));
                        c = false;
                        break;
                    }
                }
                if(c){
                    metodosDeclarados.add(new String[]{NombreMetodo});
                     mapaMetodos.put(lineaMetodo, NombreMetodo);
                }
                
            } //error nombres metodos repetidos
        
        for(var m: metodosDeclarados){
            if (m[0].equalsIgnoreCase("principal")) {
                pri++;
            }
        }
        if (pri == 0) {
            errores.add(new ErrorLSSL(61, "Error Semantico {} en la linea #, no existe el método 'principal'.",DefinirMetodos.get(DefinirMetodos.size()-1),true));
        }
           
    // -------------------------- ERROR SEMANTICO: Validar el tipo de dato y el dato asignado ------------------------
        for(var id: identValor){ 
            if(!idTipoDato.get(id.lexemeRank(1)).equals(id.lexicalCompRank(-1)) && !(id.lexemeRank(1).equals("ENT") || id.lexemeRank(1).equals("BOOL"))){
                errores.add(new ErrorLSSL(54,"Error Semantico {} en la linea #, no corresponde el valor asignado con el tipo de dato",id,true  ));
                
            }else if(id.lexemeRank(1).equals("ENT") && !(id.lexicalCompRank(-1).equals("Numero_Entero") || id.lexicalCompRank(-1).equals("Numero_Mini")) ){
                errores.add(new ErrorLSSL(54,"Error Semantico {} en la linea #, no corresponde el valor asignado con el tipo de dato",id,true  ));
                
            }else if(id.lexemeRank(1).equals("BOOL") && !(id.lexicalCompRank(-1).equals("Verdadero") || id.lexicalCompRank(-1).equals("Falso")) ){
                errores.add(new ErrorLSSL(1,"Error Semantico {} en la linea #, no corresponde el valor asignado con el tipo de dato",id,true  ));
                
            }else{
                //Insertar las variables en la tabla de simbolos, y ver si estan repetidos
                String tipo_dato = id.lexemeRank(1);
                String identificador = id.lexemeRank(2);
                String valor = id.lexemeRank(-1);
                int lineaSimbolo = id.getLine();
                // Encuentra la última entrada de método cuya línea es <= a la del símbolo
                Map.Entry<Integer, String> entry = mapaMetodos.floorEntry(lineaSimbolo);
                String metodoAsociado = (entry != null) ? entry.getValue() : "Global"; // O algún valor por defecto
                if(simbolos.size()<1){
                    simbolos.add(new String[]{identificador,tipo_dato,valor,id.getLine()+"", metodoAsociado});
                    continue;
                }
                //ERROR SEMANTICO 5: declaracion de variable repetida.
                boolean c = true;
                for (var s:simbolos) {
                    if (s[0].equals(identificador) ) {
                        errores.add(new ErrorLSSL(5, "Error Semantico {} en la linea #, declaración de una variable previamente declarada", id, true));
                        c = false;
                        break;
                    }
                    
                }
                if(c){
                    simbolos.add(new String[]{identificador, tipo_dato, valor, id.getLine() + "", metodoAsociado});
                }
            }
        }
        
        //---------------Agregar las variables declaradas sin valor
        for (Production id : idSValor) {
            String tipoDato = id.lexemeRank(1);
            String nombre = id.lexemeRank(2); //id,td,valor,declaracion,ref
            if(simbolos.size()<1){
                simbolos.add(new String[]{nombre,tipoDato,null,id.getLine()+""});
                continue;
            }
            //ERROR SEMANTICO 5: declaracion de variable repetida.
            int c = 0;
            for (var s : simbolos) {
                if (s[0].equals(nombre)) {
                    errores.add(new ErrorLSSL(5, "Error Semantico {} en la linea #, declaración de una variable previamente declarada", id, true));
                    break;
                }
                c++;
            }
            if (c == simbolos.size()) {
                simbolos.add(new String[]{nombre, tipoDato, null, id.getLine() + ""});
            }
        }
        
        
        //------------------Tipo de Dato no valido en la declaracion de un VECTOR-----------
        //VECT TD id OpAsig CorchA valores CorchC
        for(Production v:decVecVal){
            String id = v.lexemeRank(2);
            String td = v.lexemeRank(1);    // VECT0 TD1 ID2 =3 [4 val5];VECT0 TD1 id2 [3 N4 ]5
            String vals = "";
            //------------------VERIFICAR QUE LOS VALORES INGRGESADOS CORRESPONDEN CON EL TIPO
            int x = 5;
            int tam = 0;
            while (!v.lexemeRank(x).equals("]")) {
                if(!v.lexemeRank(x).equals(",")){
                    if (!idTipoDato.get(td).equals(v.lexicalCompRank(x)) && !(v.lexemeRank(1).equals("ENT") || v.lexemeRank(1).equals("BOOL"))) {
                        errores.add(new ErrorLSSL(3, "Error Semantico {} en la linea #, no corresponde 1 o mas de los valores asignados al arreglo", v, true));
                        break;
                    } else if (v.lexemeRank(1).equals("ENT") && !(v.lexicalCompRank(x).equals("Numero_Entero") || v.lexicalCompRank(x).equals("Numero_Mini"))) {
                        errores.add(new ErrorLSSL(3, "Error Semantico {} en la linea #, no corresponde 1 o mas de los valores asignados al arreglo", v, true));
                        break;
                    } else if (v.lexemeRank(1).equals("BOOL") && !(v.lexicalCompRank(x).equals("Verdadero") || v.lexicalCompRank(x).equals("Falso"))) {
                        errores.add(new ErrorLSSL(3, "Error Semantico {} en la linea #, no corresponde 1 o mas de los valores asignados al arreglo", v, true));
                        break;
                    }else{
                       vals += v.lexemeRank(x)+",";
                       tam++;
                    }
                }
                x++;
            }
            //-------VERIFICAR QUE NO SE HA USADO ESE NOMBRE ANTES
            for(var s: simbolos){
                if(s[0].equals(id)){
                    errores.add(new ErrorLSSL(5, "Error Semantico {} en la linea #, la variable fue declarada previamente.", v, true));
                    break;
                }
            }
            //Ahora si a meterlos a la "tabla"
            if(idVector.size()<1){
                if(v.lexemeRank(4).equals("[")){
                    idVector.add(new String[]{id,td,tam+"",vals,v.getLine()+""});
                }else{
                    if(!v.lexicalCompRank(4).equals("Numero_Entero") && !v.lexicalCompRank(4).equals("Numero_Mini")){
                        errores.add(new ErrorLSSL(58, "Error Semantico {} en la linea #, se esperaba un valor entero como tamaño del vector.", v, true));
                        
                    }else{
                        idVector.add(new String[]{id,td,v.lexemeRank(4),vals,v.getLine()+""});
                    }
                }
                continue;
            }
            //ver que no se repita dentro de los vectores
            boolean repetido=true;
            for(var idv:idVector){
                if(idv[0].equals(id)){
                    errores.add(new ErrorLSSL(5, "Error Semantico {} en la linea #, la variable fue declarada previamente.", v, true));
                    repetido=false;
                    break; //Este solo rompe el ciclo que busca en los vectores
                }
            }
            if(repetido){
                if(v.lexemeRank(4).equals("[")){
                    idVector.add(new String[]{id,td,tam+"",vals,v.getLine()+""});
                }else{
                    if(!v.lexicalCompRank(4).equals("Numero_Entero") || !v.lexicalCompRank(4).equals("Numero_Mini")){
                        errores.add(new ErrorLSSL(58, "Error Semantico {} en la linea #, se esperaba un valor entero como tamaño del vector.", v, true));
                    }else{
                        idVector.add(new String[]{id,td,v.lexemeRank(4),vals,v.getLine()+""});
                    }
                }
            }
            
        }
        
        
        //Validar que la variable este declarada en la asignacion y corresponda el tipo de dato
        for(var p: asign){ 
            String id = p.lexemeRank(0);
            String valAs = p.lexicalCompRank(2);
            String td = null;
            for(var s:simbolos){
                if(s[0].equals(id)){
                    td = s[1].trim();
                    if (!idTipoDato.get(td).equals(valAs) && !(td.equals("BOOL") || td.equals("ENT"))) {
                        errores.add(new ErrorLSSL(54, "Error Semantico {} en la linea #, no corresponde el valor asignado con el tipo de dato", p, true));
                    } else if (td.equals("ENT") && !(valAs.equals("Numero_Entero") || valAs.equals("Numero_Mini"))) {
                        errores.add(new ErrorLSSL(54, "Error Semantico {} en la linea #, no corresponde el valor asignado con el tipo de dato", p, true));
                    } else if (td.equals("BOOL") && !(valAs.equals("Verdadero") || valAs.equals("Falso"))) {
                        errores.add(new ErrorLSSL(54, "Error Semantico {} en la linea #, no corresponde el valor asignado con el tipo de dato", p, true));
                    }
                    break;
                }
            }
            if(td ==null){
                errores.add(new ErrorLSSL(55, "Error Semantico {} en la linea #, la variable no ha sido declarada", p, true));
            }
        }
        
    }//FIN SEMANTICO

    //generar el asm
    private void averQPedo(){
        try {
            //System.out.println("ventana: "+this.getTitle());
            String tit = this.getTitle();
            tit = tit.substring(0, tit.indexOf("."));
            String ruta = "C:\\Users\\yvano\\OneDrive\\Documentos\\Tec y asi\\LyA2Verano\\WareLangCompiler\\"+tit +".asm";
            File file = new File(ruta);
            
            if (!file.exists()) {
                file.createNewFile();
            }
            
            FileWriter fw = new FileWriter(file);
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(data);
            }
            
            System.out.println("Archivo creado: " + file.getName());
        } catch (Exception e) {
            System.out.println("Ocurrió un error.");
            //e.printStackTrace();
        }
    }//FIN AQP
    
    private void cambioColor() {
        /* Limpiar el arreglo de colores */
        textocolor.clear();
        /* Extraer rangos de colores */
        LexemaColor lexemaColor;
        try {
            File codigo = new File("color.encrypter");
            FileOutputStream output = new FileOutputStream(codigo);
            byte[] bytesText = areaCodigo.getText().getBytes();
            output.write(bytesText);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(codigo), "UTF8"));
            lexemaColor = new LexemaColor(entrada);
            while (true) {
                TextColor textColor = lexemaColor.yylex();
                if (textColor == null) {
                    break;
                }
                textocolor.add(textColor);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo no pudo ser encontrado... " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error al escribir en el archivo... " + ex.getMessage());
        }
        Functions.colorTextPane(textocolor, areaCodigo, new Color(40, 40, 40));
    }

    
    // ******************************************* TABLA DE TOKENS ***********************************
    private void TablaTokens() {

        tokens.forEach(token -> {
            String lexeme = token.getLexeme();
            String lexicalComp = token.getLexicalComp();
            if (lexeme.equals("FREC") || lexeme.equals("ENT") || lexeme.equals("COLOR")
                    || lexeme.equals("DEC") || lexeme.equals("BOOL") || lexeme.equals("CAD")) {
                lexicalComp = "TipoDato";
            }
            if (lexeme.equals("SI") || lexeme.equals("SINO") || lexeme.equals("MIENTRAS")
                    || lexeme.equals("REPETIR") || lexeme.equals("RETORNA") || lexeme.equals("CLASE")
                    || lexeme.equals("DEF") || lexeme.equals("CONSOL") || lexeme.equals("ESCRIB")
                    || lexeme.equals("APAGAR") || lexeme.equals("LCD") || lexeme.equals("PRENDER")
                    || lexeme.equals("IMPR") || lexeme.equals("ATRAS") || lexeme.equals("IZQUIERDA")
                    || lexeme.equals("DERECHA") || lexeme.equals("ALARMA") || lexeme.equals("REVISAR")
                    || lexeme.equals("ALARMA") || lexeme.equals("ADELANTE") || lexeme.equals("CAJA")
                    || lexeme.equals("BAJAR") || lexeme.equals("SUBIR") || lexeme.equals("SOLTAR")
                    || lexeme.equals("PARAR") || lexeme.equals("LIMPIAR") || lexeme.equals("AGREGAR")
                    || lexeme.equals("CANTIDAD") || lexeme.equals("TOMAR") || lexeme.equals("SACAR")) {
                lexicalComp = "PalabraReservada";
            }

            if (lexeme.equals("+") || lexeme.equals("-") || lexeme.equals("/")
                    || lexeme.equals("*") || lexeme.equals("%") || lexeme.equals("+=")
                    || lexeme.equals("-=") || lexeme.equals("^")) {
                lexicalComp = "Op_Aritmetico";
            }
            if (lexeme.equals("(") || lexeme.equals(")") || lexeme.equals("[")
                    || lexeme.equals("]") || lexeme.equals("{") || lexeme.equals("}") || lexeme.equals(";")) {
                lexicalComp = "Op_Agrup";
            }

            if (lexeme.equals("=")) {
                lexicalComp = "Op_Asignacion";
            }

            if (lexeme.equals("==") || lexeme.equals("!=") || lexeme.equals("<")
                    || lexeme.equals(">") || lexeme.equals("<=") || lexeme.equals(">=")) {
                lexicalComp = "Op_Relacional";
            }

            if (lexeme.equals("&&") || lexeme.equals("||") || lexeme.equals("!")) {
                lexicalComp = "Op_Logico";
            }

            String lineColumn = "[" + token.getLine() + ", " + token.getColumn() + "]";
            lisTokens.add(new String[]{lexeme, lexicalComp, lineColumn});
        });
    }

    private void imprimirConsola() {
        int sizeErrors = errores.size();
        if (sizeErrors > 0) {
            Functions.sortErrorsByLineAndColumn(errores);
            String strErrores = "\n";
            for (ErrorLSSL error : errores) {
                String strError = String.valueOf(error);
                strErrores += strError + "\n";
            }
            //consola.setForeground(Color.red);
            appendToPane(consola, "Compilación terminada.\n", Color.black);
            appendToPane(consola, strErrores, Color.red);
            appendToPane(consola, "\nCompilación con errores...", Color.black);
        } else {
            consola.setText("Compilación terminada, no existen errores.");
        }
        consola.setCaretPosition(0);
    }

    private void limpiarAreaCodigo() {
        // Functions.clearDataInTable(tablaSimbolos);
        consola.setText("");
        tokens.clear();
        errores.clear();
        idVector.clear();
        simbolos.clear();
        metodosDeclarados.clear();
        lisTokens.clear();
        estadoCompilacion = false;
        
        FuncionesMovimiento.clear();
        FuncionRevisar.clear();
        FuncionImprimir.clear();
        FuncionImprimirVec.clear();
        DefinirMetodos.clear();
        identValor.clear();
        FuncionAlarma.clear();
        FuncionCaja.clear();
        asign.clear();
        decVecVal.clear();
        idSValor.clear();
        asignVec.clear();
        expRel.clear(); 
        funcRepetir.clear();
        
        //rest
        codigoIntermedio="";
        cuidameloTantito="";
        //asm
        data="";
        code="";
        proc="";
    }

    private void appendToPane(JTextPane tp, String msg, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
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
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatIntelliJLaf());
                FlatDarkLaf.setup();
            } catch (UnsupportedLookAndFeelException ex) {
                System.out.println("LookAndFeel no soportado: " + ex);
            }
            new Compilador().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane areaCodigo;
    private javax.swing.JButton btnAbrir;
    private javax.swing.JButton btnCompilar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnGuardarC;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JTextPane consola;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panelButtonCompilerExecute;
    // End of variables declaration//GEN-END:variables
}
