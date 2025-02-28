#start=robot.exe# 
#start=stepper_motor.exe#
.model small
.stack
.data
      MATRIZ DB 11111111B, 11111111B , 11111111B, 11111111B,11111111B ;2000H - 2004H   DISPLAY 1
             DB 00000000B, 00000000B,  00000000B, 00000000B,00000000B ;2005H - 2009H   DISPLAY 2
             DB 00000000B, 00111100B,  00010010B, 00111100B,00000000B ;200AH - 200EH   DISPLAY 3
             DB 00000000B, 00111110B,  00100000B, 00100000B,00000000B ;200FH - 2013H   DISPLAY 4
             DB 00000000B, 00000010B,  00111110B, 00000010B,00000000B ;2014H - 2018H   DISPLAY 5
             DB 00000000B, 00111110B,  00100010B, 00111110B,00000000B ;2019H - 201DH   DISPLAY 6 
             DB 00000000B, 00000000B,  00000000B, 00000000B,00000000B ;201EH - 2022H   DISPLAY 7
             DB 11111111B, 11111111B , 11111111B, 11111111B,11111111B ;2023H - 2027H 
             
      BEP    Db 007      
             
      VUELTA   DB 0000_0110b
               DB 0000_0100b    
               DB 0000_0011b
               DB 0000_0010b
               
      VUELTAINV  DB 0000_0011b
                 DB 0000_0001b    
                 DB 0000_0110b
                 DB 0000_0010b 
                 
      CADIMPR    DB '$'
      ACUMCAR    DW 0
 
               
      FINVUELTA = 8h
      
      r_port equ 9  
      
      ESPACIOS DW 0 
 ;*************************************** MACROS **************************
 IMPRIMIR_CONSOLA macro  cadd
    MOV AH,9
    LEA DX,CADd
    INT 21H 
          
IMPRIMIR_CONSOLA  endm 
 ;*************************************************************************
   colorVacio dw '#70FF00$'
   fin db 'Finalización de la revision$'
   cajaVacia db 'Caja vacia$'
   cajaLlena db 'Producto tomado$'
   alarm dw 20
   x dw 1
   buscados dw  'Zucaritas' , 'Pan Bimbo' , 'Canelitas' 
   estado dw  0 , 0 , 0  

   cadz0 db 'lleno$'

   cadz2 db 'vacio$'
.code
   mov ax,@data
   mov ds,ax
   mov es,ax
   CALL tomarHay  
FINZ:
   mov ax,4c00h
   int 21h

 ;****************************** PROCEDIMIENTOS *************************** 

 ;----- ALARMA -----
ALARMA proc
       mov ah,9
       mov al,007h    
       mov cx,3 
       int 10h
              
       MOV DX, 2000H 
       MOV BX, 0        
DISPLAY:
        
       MOV CX, 5
       MOV SI, 0         

COLUMNA:
       
       MOV AL, MATRIZ[BX][SI]
       OUT DX, AL 
       INC SI
       INC DX 
         
       CMP SI, 5
       LOOPNE COLUMNA 
       
       ADD BX,5 
       CMP BX,40 
        
       
       JL DISPLAY
                     
RET  
ALARMA endp



  ; ----- TOMAR -----
TOMAR proc 
        
IniciarRueda:
mov bx, offset VUELTA 
mov si, 0
mov cx, 0 

darVuelta:

espera: in al, 7     
        test al, 10000000b
        jz espera

mov al, [bx][si]
out 7, al

inc si

cmp si, 4
jb darVuelta
mov si, 0
 

inc cx
cmp cx, FINVUELTA
jb  darVuelta 

;apagar luz
mov al, 6
out r_port, al
ret

RET

TOMAR endp  



  ; ----- SOLTAR -----
SOLTAR proc 
        
IniciarRuedaS:
mov bx, offset VUELTAINV 
mov si, 0
mov cx, 0 

darVueltaS:

esperaS: in al, 7     
        test al, 10000000b
        jz esperaS

mov al, [bx][si]
out 7, al

inc si

cmp si, 4
jb darVueltaS
mov si, 0
 

inc cx
cmp cx, FINVUELTA
jb  darVueltaS 

;prender luz
mov al, 5
out r_port, al

RET

SOLTAR endp
 
       

 ;----- VER ----
VER proc 

call enfriar 
    
mov al, 4
out r_port, al ;EXAMINA
        
ocupado: in al, r_port+2
       test al, 00000001b
       jz ocupado

RET

VER endp 



 ; ---- IZQUIERDA ----
IZQUIERDA proc  
    CALL ENFRIAR
    
    MOV AL, 2
    OUT 9, AL 
    
    MOV CX,ESPACIOS  
CICLOIZ:
    CALL ENFRIAR
     
    mov al, 0
    out r_port, al 
    
    CALL ENFRIAR 
    
    mov al, 1
    out r_port, al 
    
    CALL ENFRIAR  
    
    LOOP CICLOIZ

RET

IZQUIERDA endp

  
  
  
  
 ; ---- DERECHA ----
DERECHA proc
    CALL ENFRIAR
    
    MOV AL, 3
    OUT 9, AL 
    
    MOV CX,ESPACIOS  
CICLODER: 
    CALL ENFRIAR
     
    mov al, 0
    out r_port, al
    
    CALL ENFRIAR  
    
    mov al, 1
    out r_port, al
    
    CALL ENFRIAR

    LOOP CICLODER

RET

DERECHA endp   



 ; ---- ADELANTE ----
ADELANTE proc 
    CALL ENFRIAR  
    
    MOV CX,ESPACIOS  
CICLOADEL:
    CALL ENFRIAR
    
    mov al, 0
    out r_port, al
    
    CALL ENFRIAR  
    
    mov al, 1
    out r_port, al
    
    CALL ENFRIAR

    LOOP CICLOADEL

RET

ADELANTE endp 


; ---- ATRAS ----
 ATRAS proc   
    CALL ENFRIAR 
    
    MOV AL, 3
    OUT 9, AL 
    
    CALL ENFRIAR
    
    mov al, 0
    out r_port, al
    
    CALL ENFRIAR 
    
    MOV AL, 3
    OUT 9, AL    
    
    MOV CX,ESPACIOS  
CICLOAT: 
    CALL ENFRIAR 
    
    mov al, 0
    out r_port, al  
    
    CALL ENFRIAR
    
    mov al, 1
    out r_port, al
    
    CALL ENFRIAR
     
    LOOP CICLOAT

RET

ATRAS endp


  ; ---- PARAR ----
PARAR proc
  mov al, 0
  out r_port, al 
    
ret    
PARAR  endp 

   ;----- ENFRIAR -----
 
ENFRIAR proc
busy: in al, r_port+2
      test al, 00000010b
      jnz busy ; busy, so wait.
ret     
ENFRIAR endp   


  ; ---- IMPRIMIRLCD ----
IMPRIMIR_LCD proc 
     MOV ACUMCAR, 0
     MOV AX,0
 
     
     MOV DX, 2050h
	 MOV SI, 0
     MOV CX, 15 
     
LOOPLCD:
	MOV AL, CADIMPR[SI]
	CMP AL, '$'
	JE SALIR
	OUT DX,AL 
	INC SI
	INC DX

	LOOP LOOPLCD 
	
SALIR:
RET 
          
IMPRIMIR_LCD  endp


  ; ---- IMPRIMIRCONSOLA ----
IMPRIMIR_CONSOLA proc 
    MOV AH,9
    LEA DX,CADIMPR
    INT 21H
RET 
          
IMPRIMIR_CONSOLA  endp

tomarHay PROC 
LBL1:
   call VER 
   in al, r_port + 1  
   cmp al, 7
   JNZ LBL3
LBL2:
   CALL TOMAR  
   MOV al, cadz0
   MOV CADIMPR, al 
IMPRIMIR_CONSOLA cadz1
   JMP LBL4
LBL3:
   MOV al, cadz2
   MOV CADIMPR, al 
IMPRIMIR_CONSOLA cadz3
   CALL ALARMA 
LBL4:
   RET
tomarHay ENDP 

END