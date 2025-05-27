;Rusty - Código ASM Generado

; Inicio del programa
MOV colorVacio, #70FF00
MOV fin, 'Finalización de la revision'
MOV cajaVacia, 'Caja vacia'
MOV cajaLlena, 'Caja llena'
MOV alarm, 20
MOV x, 1
MOV buscados, [ 'Zucaritas' , 'Pan Bimbo' , 'Canelitas' ]
; PROC tomarHay:
CMP REVISAR ( #70FF00 , 12 ), FALSO
JE LBL2
JMP LBL3
LBL2:
CALL TOMAR
CALL IMPR 'C' , LCD
LBL3:
JMP LBL5
CALL IMPR 't' , LCD
CALL ALARMA alarm
; Fin del bloque
; PROC recorridoZucaritas:
; REPETIR ,  3
CMP VERADELANTE ( ), VERDADERO
JE LBL10
JMP LBL11
LBL10:
MOV cajaLlena2, 'Producto tomado'
MOV T1, x
; buscados[T1] =  'bueno'
CALL ADELANTE 4 , 2
LBL11:
JMP LBL13
CALL PARAR
CALL IMPR 'obstruccion' , LCD
; LOOP LBL8
; Fin del bloque
; PROC recorridoPan:
MOV cajaLlena3, 'Producto tomado'
; REPETIR ,  2
CMP VERDERECHA ( ), VERDADERO
JE LBL18
JMP LBL19
LBL18:
CALL IZQUIERDA 2 , 2
LBL19:
JMP LBL21
CALL PARAR
CALL IMPR 'obstruccion' , LCD
; LOOP LBL16
; Fin del bloque
; PROC recorridoCanelitas:
; REPETIR ,  4
CMP VERDERECHA ( ), VERDADERO
JE LBL26
JMP LBL27
LBL26:
CALL DERECHA 2 , 2
LBL27:
JMP LBL29
CALL PARAR
CALL IMPR 'obstruccion' , LCD
; LOOP LBL24
; Fin del bloque
; PRINCIPAL:
CALL recorridoZucaritas
CALL tomarHay
CALL recorridoPan
CALL tomarHay
CALL recorridoCanelitas
CALL tomarHay
; Fin del bloque
