

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rebek
 */
public class ExecutarJFlex {

    /**
     * @param args the command line arguments
     */

    public static void main(String omega[]) {
        String lexerFile = System.getProperty("user.dir") + "/src/Lexema.flex",
        lexerFileColor = System.getProperty("user.dir") + "/src/LexemaColor.flex";
        try {
            jflex.Main.generate(new String[]{lexerFile, lexerFileColor});
        } catch (Exception ex) {
            System.out.println("Error al compilar/generar el archivo flex: " + ex);
        }
    }
}
