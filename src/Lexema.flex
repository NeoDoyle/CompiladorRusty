import compilerTools.Token;
 import java.util.HashSet;
 import java.util.Set;
 import java.util.Arrays;

%%
%class Lexema
%type Token
%line
%column
%{




    private Token token(String lexeme, String lexicalComp, int line, int column){
        return new Token(lexeme, lexicalComp, line+1, column+1);
    }

    // Definir el conjunto de funciones a excluir
   private static final Set<String> PALABRASRESERV = new HashSet<>(Arrays.asList(
    "FREC", "ENT", "COLOR", "DEC", "BOOL", "CAD", "SI", "SINO", "MIENTRAS", 
    "REPETIR", "CLASE", "DEF", "CONSOL", "ESCRIB", "APAGAR", "LCD", 
    "PRENDER", "IMPR", "ATRAS", "IZQUIERDA", "DERECHA", "ALARMA", "REVISAR", 
    "ADELANTE", "CAJA", "SOLTAR", "PARAR", "LIMPIAR", "TOMAR", "VECT",
    "VERADELANTE","VERATRAS","VERIZQUIERDA","VERDERECHA"
    ));
%}

/* Variables básicas de comentarios y espacios */
TerminadorDeLinea = \r|\n|\r\n
EntradaDeCaracter = [^\r\n]
EspacioEnBlanco = {TerminadorDeLinea} | [ \t\f]
ComentarioTradicional = "/*" [^*] ~"*/" | "/*" "*"+ "/"
FinDeLineaComentario = "//" {EntradaDeCaracter}* {TerminadorDeLinea}?
ContenidoComentario = ( [^*] | \*+ [^/*] )*
ComentarioDeDocumentacion = "/**" {ContenidoComentario} "*"+ "/"

/* Comentario */
Comentario = {ComentarioTradicional} | {FinDeLineaComentario} | {ComentarioDeDocumentacion}

/* Identificador */
LetraMin = [a-zñ]
LetraMay = [A-ZÑ]
Letra = [A-Za-zÑñ]
Digito = [0-9]
Identificador = {LetraMin}({Letra}|{Digito}){0,29}


/*PARA ERRORES
TipoDato = FREC | ENT | COLOR | DEC | BOOL | CAD 
Est_PalReservadas = SI | SINO | MIENTRAS | REPETIR | RETORNA | CLASE | DEF |CONSOL | ESCRIB | APAGAR |  LCD  | PRENDER 
Funciones =  ATRAS | IZQUIERDA | DERECHA | ALARMA | REVISAR | ADELANTE | CAJA | BAJAR | SUBIR | SOLTAR 
            | PARAR | LIMPIAR | TOMAR | VER */

/* MINI*/
Mini = (0|1?[0-9]{1}|2[0-4][0-9]|25[0-5])

/* Número */
Numero = [0-9]{1,38}

NumeroLetra = {Numero}{LetraMay}

/* Número decimal*/
NumeroDecimal = (0|[1-9][0-9]{0,18})\.[0-9]{1,18}
NDIncompleto = (0|[1-9][0-9]{0,18})\.

/* Color RGB*/
ColorHX = #[A-Fa-f0-9]{6}

/* Cadena*/
Cadena = '[^']*'




/*Errores*/
noASCII =[^\u0000-\u007F] | [´$@_~`]
cadNoCerrada = '([^']* )TerminadorDeLinea


%%

/* Comentarios o espacios en blanco */
{Comentario}|{EspacioEnBlanco} { /*Ignorar*/ }

/*Retorno Identificador*/
{Identificador} { return token(yytext(), "Identificador", yyline, yycolumn); }

/*Tipos de dato*/
FREC { return token(yytext(), "FREC", yyline, yycolumn); }
ENT { return token(yytext(), "ENT", yyline, yycolumn); }
COLOR { return token(yytext(), "COLOR", yyline, yycolumn); }
DEC { return token(yytext(), "DEC", yyline, yycolumn); }
BOOL { return token(yytext(), "BOOL", yyline, yycolumn); }
CAD { return token(yytext(), "CAD", yyline, yycolumn); }
MINI { return token(yytext(), "MINI", yyline, yycolumn); }

/* Número */
{Mini} { return token(yytext(), "Numero_Mini", yyline, yycolumn); }

/* Número */
{Numero} { return token(yytext(), "Numero_Entero", yyline, yycolumn); }

/* Número decimal*/
{NumeroDecimal} { return token(yytext(), "Numero_Decimal", yyline, yycolumn); }

/*COLOR */
{ColorHX} { return token(yytext(), "Hexadecimal", yyline, yycolumn); }

/*VERDADERO*/
VERDADERO { return token(yytext(), "Verdadero", yyline, yycolumn); }

/*FALSO*/
FALSO { return token(yytext(), "Falso", yyline, yycolumn); }

/* CADENA */
{Cadena} { return token(yytext(), "Cadena", yyline, yycolumn); }

/* Velocidad */
"1" | "2" | "3" { return token(yytext(), "Velocidad", yyline, yycolumn); }

/*  Estructuras de control Palabras reservadas para robot */
SI {return token(yytext(),"Est_SI",yyline,yycolumn);}
SINO {return token(yytext(),"Est_SINO",yyline,yycolumn);} 
MIENTRAS {return token(yytext(),"Est_MIENTRAS",yyline,yycolumn);}
CONSOL {return token(yytext(),"CONSOL",yyline,yycolumn);}
LEER {return token(yytext(),"Est_LEER",yyline,yycolumn);}
APAGAR {return token(yytext(),"APAGAR",yyline,yycolumn);}
LCD  {return token(yytext(),"LCD",yyline,yycolumn);}
PRENDER {return token(yytext(),"PRENDER",yyline,yycolumn);}
REPETIR {return token(yytext(),"Est_REPETIR",yyline,yycolumn);} 
RETORNA {return token(yytext(),"PbC_RETORNA",yyline,yycolumn);} 
CLASE  {return token(yytext(),"CLASE",yyline,yycolumn);}
DEF  { return token(yytext(), "DEF", yyline, yycolumn); }
PRINCIPAL  { return token(yytext(), "PRINCIPAL", yyline, yycolumn); }
TOMAR  { return token(yytext(), "F_TOMAR", yyline, yycolumn); }
CONF  { return token(yytext(), "CONF", yyline, yycolumn); }
VECT { return token(yytext(), "VECTOR", yyline, yycolumn); }
VERADELANTE { return token(yytext(), "VerAdelante", yyline, yycolumn); }
VERATRAS { return token(yytext(), "VerAtras", yyline, yycolumn); }
VERIZQUIERDA { return token(yytext(), "VerIzquierda", yyline, yycolumn); }
VERDERECHA { return token(yytext(), "VerDerecha", yyline, yycolumn); }
IMPRVECTOR { return token(yytext(), "ImprVector", yyline, yycolumn); }
/*MATRIZ { return token(yytext(), "MATRIZ", yyline, yycolumn); }*/


/*  Funciones del Lenguaje */
IMPR { return token(yytext(), "F_IMPR", yyline, yycolumn); } 
ADELANTE { return token(yytext(), "F_ADELANTE", yyline, yycolumn); } 
ATRAS { return token(yytext(), "F_ATRAS", yyline, yycolumn); }
IZQUIERDA { return token(yytext(), "F_IZQUIERDA", yyline, yycolumn); }
DERECHA { return token(yytext(), "F_DERECHA", yyline, yycolumn); }
REVISAR { return token(yytext(), "F_REVISAR", yyline, yycolumn); }
ALARMA { return token(yytext(), "F_ALARMA", yyline, yycolumn); }
CAJA { return token(yytext(), "F_CAJA", yyline, yycolumn); }
BAJAR { return token(yytext(), "F_BAJAR", yyline, yycolumn); }
SUBIR { return token(yytext(), "F_SUBIR", yyline, yycolumn); }
SOLTAR { return token(yytext(), "F_SOLTAR", yyline, yycolumn); }
PARAR { return token(yytext(), "F_PARAR", yyline, yycolumn); }
LIMPIAR { return token(yytext(), "F_LIMPIAR", yyline, yycolumn); }
VER {return token(yytext(), "F_VER", yyline, yycolumn);}
/*AGREGAR { return token(yytext(), "F_AGREGAR", yyline, yycolumn); }
CANTIDAD { return token(yytext(), "F_CANTIDAD", yyline, yycolumn); }
SACAR { return token(yytext(), "F_Sacar", yyline, yycolumn); }*/

/* Operadores aritméticos*/
"+" { return token(yytext(), "Op_Sum", yyline, yycolumn); }
"-" { return token(yytext(), "Op_Res", yyline, yycolumn); }
"/" { return token(yytext(), "Op_Div", yyline, yycolumn); }
"*" { return token(yytext(), "Op_Mul", yyline, yycolumn); }
"%" { return token(yytext(), "Op_Mod", yyline, yycolumn); }
"+=" { return token(yytext(), "Op_MasIgual", yyline, yycolumn); }
"-=" { return token(yytext(), "Op_MenosIgual", yyline, yycolumn); }
"^" { return token(yytext(), "Op_Potencia", yyline, yycolumn); }

/* Simbolo de agrupación */
"(" { return token(yytext(), "Par_abr", yyline, yycolumn); }
")" { return token(yytext(), "Par_cer", yyline, yycolumn); } 
"[" { return token(yytext(), "Corch_abr", yyline, yycolumn); }
"]" { return token(yytext(), "Corch_cer", yyline, yycolumn); }
"{" { return token(yytext(), "Llav_abr", yyline, yycolumn); }
"}" { return token(yytext(), "Llav_cer", yyline, yycolumn); }

/* Simbolo de puntuación */
"." { return token(yytext(), "Punto", yyline, yycolumn); }
"," { return token(yytext(), "Coma", yyline, yycolumn); } 
":" { return token(yytext(), "DosPuntos", yyline, yycolumn); }

/* Operador de linea */
";" { return token(yytext(), "Punto_coma", yyline, yycolumn); }

/* Operadores lógicos*/
"&&" { return token(yytext(), "OpLog_Y", yyline, yycolumn); }
"||" { return token(yytext(), "OpLog_O", yyline, yycolumn); }
"!" { return token(yytext(), "OpLog_NO", yyline, yycolumn); }

/* Operadores relacionales */
"==" { return token(yytext(), "OpRel_Igual", yyline, yycolumn); }
"!=" { return token(yytext(), "OpRel_NoIgual", yyline, yycolumn); }
"<"  { return token(yytext(), "OpRel_Menor", yyline, yycolumn); }
">"  { return token(yytext(), "OpRel_Mayor", yyline, yycolumn); }
"<=" { return token(yytext(), "OpRel_MenorIg", yyline, yycolumn); }
">=" { return token(yytext(), "OpRel_MayorIg", yyline, yycolumn); }

/* Operador asignación*/
"=" { return token(yytext(), "Op_asignacion", yyline, yycolumn); }



/*ERRORES*/

{noASCII} { return token(yytext(), "ERROR_1", yyline, yycolumn); }

0{Numero} { return token(yytext(), "ERROR_2", yyline, yycolumn); }

({LetraMay}|{Numero})+{Identificador} | {Identificador}+ { return token(yytext(), "ERROR_3", yyline, yycolumn); }

{cadNoCerrada} { return token(yytext(), "ERROR_4", yyline, yycolumn); }

{NumeroDecimal}+\. | {NDIncompleto} { return token(yytext(), "ERROR_5", yyline, yycolumn); }

"&" | "|" { return token(yytext(), "ERROR_6", yyline, yycolumn); }

{LetraMay}+ | ({NumeroLetra})+ {
    if (!PALABRASRESERV.contains(yytext())) {
        return token(yytext(), "ERROR_7", yyline, yycolumn);
    }
}

. { return token(yytext(), "ERROR", yyline, yycolumn); }
