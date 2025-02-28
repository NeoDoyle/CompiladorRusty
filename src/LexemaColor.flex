import compilerTools.TextColor;
import java.awt.Color;

%%
%class LexemaColor
%type TextColor
%char
%{
    private TextColor textColor(long start, int size, Color color){
        return new TextColor((int) start, size, color);
    }
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
Letra = [A-Za-zÑñ]
Digito = [0-9]
Identificador = {LetraMin}({Letra}|{Digito}){1,29}

TipoDato = FREC | ENT | COLOR | DEC | BOOL | CAD | CONF| MINI | VECT 
Est_PalReservadas = SI | SINO | MIENTRAS | REPETIR | RETORNA | CLASE | DEF |CONSOL | LEER | ESCRIB | APAGAR | IMPRVECTOR 
                    | IMPR | LCD  | PRENDER  | PRINCIPAL
Funciones = ATRAS | IZQUIERDA | DERECHA | ALARMA | REVISAR | ADELANTE | CAJA
            | SOLTAR | PARAR | LIMPIAR | TOMAR | VER | VERADELANTE | VERIZQUIERDA | VERDERECHA | VERATRAS

/* Número */
Numero = ([-+]?[1-9]([0-9])*) | 0

/* Número decimal*/
NumeroDecimal = (0|[1-9][0-9]{0,18})\.[0-9]{1,18}

/* Color RGB*/
ColorHX = #([A-Fa-f0-9]{6})

/* Cadena*/
Cadena = '[^']*'

%%

/*Retorno Identificador*/
{Identificador} { }

/*Tipos de dato*/
{TipoDato} { return textColor(yychar, yylength(), new Color(231, 43, 43)); }

/* Número */
{Numero} {}

/* Número decimal*/
{NumeroDecimal} {  }

/*COLOR */
{ColorHX} { return textColor(yychar, yylength(), new Color(148, 212, 12)); }

/*VERDADERO*/
VERDADERO { return textColor(yychar, yylength(), new Color(124, 3, 3 )); }

/*FALSO*/
FALSO { return textColor(yychar, yylength(), new Color(124, 3, 3 )); }

/*  Estructuras de control  Y Palabras reservadas*/
{Est_PalReservadas} { return textColor(yychar, yylength(), new Color(20, 125, 20 )); }


/* Funciones  */
{Funciones} { return textColor(yychar, yylength(), new Color(19, 171, 238)); }

/* CADENA */
{Cadena} { return textColor(yychar, yylength(), new Color(185, 84, 0 )); }


/* Operadores aritméticos*/
"+" | "-" | "/" | "*" | "%" | "+=" | "-=" | 
"^" { }

/* Simbolo de agrupación */
"(" | ")" | "[" | "]" | "{" |
"}" {  }

/* Simbolo de puntuación */
";" | "." | "," |
":" {  }

/* Operadores lógicos*/
"&&" | "||" |
"!" {  }

/* Operadores relacionales */
"==" | "!=" | "<" | ">" | "<=" | 
">=" {  }

/* Operador asignación*/
"=" { }

/* Comentarios o espacios en blanco */
{Comentario} { return textColor(yychar, yylength(),  new Color(146, 146, 146)); }
{EspacioEnBlanco} { /*Ignorar*/ }

{Est_PalReservadas}{Letra}+ { }
{Funciones}{Letra}+ { }
{TipoDato}{Letra}+ { }

{Est_PalReservadas}{Numero}+ { }
{Funciones}{Numero}+ { }
{TipoDato}{Numero}+ { }

{Letra}+{Est_PalReservadas} { }
{Letra}+{Funciones} { }
{Letra}+{TipoDato} { }

{Numero}+{Est_PalReservadas} { }
{Numero}+{Funciones} { }
{Numero}+{TipoDato} { }

. { /* Ignorar */ }
