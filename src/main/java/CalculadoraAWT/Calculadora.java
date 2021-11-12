/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CalculadoraAWT; import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
public class Calculadora extends Frame implements WindowListener, ActionListener  {
	public String [] botonesNum = {"1","2","3","4","5","6","7","8","9","√","0","."}; // Panel con los numeros
        public String [] botonesOP = {"  +  ", "  -  ","  *  ","  /  ","  AC  ","  =  "}; // Panel con las operaciones
        public String [] botonesAv = {"MC","SAVE M","LOAD M","M+","M-", "x2", "x3", "x^x","x-1", "x!"}; // Panel con operaciones avanzadas
       	public Double boton1; // Primer botón que se marca
        public Double boton2; // Segundo botón marcado, necesario en las operaciones de multibotones.
        public String operador; // Guardamos el tipo de operador que vamos a tratar en el igual
	public TextField display;  // Donde se muestran los numeros.
        public Double memoriaNumInt; // variable que utilizamos para guardar la memoria en operaciones avanzadas
        public String memoriaNum; // El string que copia a memoria.

	public Calculadora () {
            
            //*****OPERACIONES GENERALES 
            setTitle("Calculadora Diego de Arriba Franco    2ºDAM"); // Para poner el titulo
            setSize(800,800); // Ponemos altura y anchura
            setLocationRelativeTo(null); // Para que aparezca en el centro
            addWindowListener(this); // Añadimos el windowsListener para poder cerrar

            //******CREACIÓN DE PANELES GENERALES 
            Panel panel = new Panel(new BorderLayout()); // Creamos el Panel, y a su vez en el constructor ponemos ya predefinido el Layout
            this.add(panel); // Añadimos el panel
            Panel numerosCentro = new Panel(new BorderLayout());
            panel.add("Center",numerosCentro);
            
            //******** CREACIÓN DE TEXTFIELD 
            display = new TextField(30); // Creamos el textField que es donde mostraremos las operaciones
            display.setBackground(Color.white);                
	    display.setFont(new Font("Script fonts", Font.BOLD, 50));
	    display.setEditable(false);
            panel.add("North",display);  // Añadimos el Textfield a la zona norte del panel padre.
         
            
                // Aquí creamos el panel de los botones, el cual pondremos en el centro
                Panel panelAv = new Panel(new GridLayout(0,5)); // El panel en el que se van a encontrar estos botones avanzados
		for (int i=0;i<botonesAv.length;i++) { // Creamos un bucle for para asignar los parametros a todos los botones de una vez
			Button b = new Button(botonesAv[i]); // Creamos el boton
                        b.addActionListener(this);        //Añadimos el actionListener de clase
                        b.setBackground(Color.ORANGE);    // Color de fondo
                        b.setForeground(Color.black);     // Color de letra
                        b.setFont(new Font ("Times New Roman",Font.BOLD,18));
			panelAv.add(b);// Los añadimos al panel uno por uno
		}
                    numerosCentro.add("North",panelAv); // Añadimos el panel creado, al panel ya existente de numerosCentro, y lo asignamos en el norte
                    
                    //Aquí creamos el panel de Botones, que iran en el centro
                Panel panelBotones = new Panel(new GridLayout(4,4)); // Creamos el panel de los botones, en el cual vamos a establecer un grid para que se posicionen en celdas de 4x4
                for (int i=0;i<botonesNum.length;i++) { // Mismo proceso que arriba, pero matizando cambios
			Button b = new Button(botonesNum[i]);
                        b.addActionListener(this);        
                        b.setBackground(Color.BLACK);
                        b.setForeground(Color.white);
                        b.setFont(new Font ("Times New Roman",Font.BOLD,24));
			panelBotones.add(b);
		}  
		numerosCentro.add("Center",panelBotones); // Añadimos el panel al panel numerosCentro, ocupando el centro, justo debajo del panelAv
               
                    // Aquí creamos el panel de operaciones, que ira al Este del panel padre
                Panel panelOP = new Panel ();  
                panelOP.setLayout(new GridLayout(3,2)); //establecemos un grid para que se posicionen en celdas de 3x2
                 for (int i=0;i<botonesOP.length;i++) {
			Button a = new Button(botonesOP[i]);
                        a.addActionListener(this);        
                        a.setBackground(Color.red);
                        a.setForeground(Color.black);
                        a.setFont(new Font ("Times New Roman",Font.BOLD,18));
			panelOP.add(a);
		}  
                panel.add("East", panelOP); // lo añadimos a la zona este del panel Principal.
		
	}


	public void actionPerformed (ActionEvent e) { 
		String receptor = ((Button)e.getSource()).getLabel(); // Obtenemos toda la información de los botones, y la enviamos a ejecutaOrden
		ejecutaOrden(receptor); // Hacemos la llamada al método poniendo como parámetro el resultado anterior
	}
        
        public void ejecutaOrden (String receptor) {
               // EN CASO DE QUE SEA UN NÚMERO --
            if (ordenNumerica(receptor)) {
                tratamientoNumeros(receptor);   // Llamamos a tratamiento de numeros
		} else { // Si no es un numero
                    tratamientoOperadores(receptor); // Llamamos a tratamiento de operadores
                }    
	}
        
        
        public boolean ordenNumerica (String orden) { // para asegurarnos que pasamos un número
            try {
                Integer.parseInt(orden); 
                    return true;
            } catch (NumberFormatException nfe) {
                    return false;
            }
	}
        
        
        public void tratamientoNumeros(String receptor) {
            if (((String)display.getText()).equals("0")) { // Proceso explicado en ReadMe
                display.setText(receptor);            
            }else {                           
                display.setText(display.getText()+receptor);  
           }
        }
        
        public void tratamientoOperadores (String receptor) {
            if (receptor.equals("  +  ")) {
                    sumar();  
		} else if (receptor.equals("  -  ")) {
                    restar();
		} else if (receptor.equals("  *  ")) {
                    multiplicar();   
		} else if (receptor.equals("  /  ")) {
                    dividir();
                } else if (receptor.equals("  =  ")) {
                    igual();
                } else if (receptor.equals("  AC  ")) {
                    AC();
		} else if (receptor.equals("SAVE M")) { // Almacena el numero sin mostrarlo
                    MS();
                } else if (receptor.equals("LOAD M")) { // Muestra el número que lleva almacenado
                    MR();   
                } else if (receptor.equals("M+")) { // Suma el numero que hay en el teclado a la variable
                    Mmas();
                } else if (receptor.equals("M-")) { // Resta el numero que hay en el teclado a la variable
                    Mmenos();
                } else if (receptor.equals("MC")) { // Borra cualquier numero guardado
                    MC();
                } else if (receptor.equals("√")) {
                    raiz();
                } else if (receptor.equals(".")) {
                    añadirDecimales();
                } else if (receptor.equals("x2")) {
                    elevarCuadrado();
                }  else if (receptor.equals("x3")) {
                    elevarCubo();
                } else if (receptor.equals("x^x")) {
                    elevarCubo();
                } else if (receptor.equals("x-1")) {
                    negativo();
                } else if (receptor.equals("x!")) {
                    factorial();
                }                         
        }


        public void  añadirDecimales() { // Sirve para añadir decimales a nuestra calculadora
            if (!(display.getText().contains("."))){ // Comprobamos que no haya ningun decimal ya puesto para poder ponerle (Solo permitiremos un . a la vez)
                display.setText(display.getText()+".");
            }
        }
            
        
        public void sumar () { // Para sumar los numeros
            boton1 = Double.parseDouble((String)display.getText()); // Obtenemos el valor que ha sido marcado en nuestro Textfield, tenemos que hacer un Double.ParseDouble ya que este parametro es String
            operador = "+"; // Marcamos el operador que ha sido seleccionado
            display.setText(""); // Vaciamos la caja texto

        }
        
        public void restar () { // Misma operacion que en Suma
            boton1 = Double.parseDouble((String)display.getText());
            operador = "-";
            display.setText("");

        }
         
        public void multiplicar () { // Misma operacion que en Suma
            boton1 = Double.parseDouble((String)display.getText());
            operador = "*";
            display.setText("");

        }
          
        public void dividir () { // Misma operacion que en Suma
            boton1 = Double.parseDouble((String)display.getText());
            operador = "/";
            display.setText("");

        }
        public void elevarNumero () {// Misma operacion que en Suma
            boton1 = Double.parseDouble((String)display.getText());
            operador = "elevadoNum";
            display.setText(""); 
        }
           
        public void igual () { 
            // Creamos el botón dos, que sabemos que es un nuevo parámetro ya que el resto de operadores han dejado siemrpe vacio el Textfield, por lo que solo tenemos que trabajar con esos dos parámetros
            boton2 = Double.parseDouble((String)display.getText());
            
            switch(this.operador){ // Establecemos un switch con sus case, cada cual esta definido por la condición del operador que definimos antes en el proceso de suma, resta, division, multiplicacion y elevadoNum
                case "+": display.setText(Double.toString(boton1+boton2)); break;
                case "-": display.setText(Double.toString(boton1-boton2)); break;
                case "*": display.setText(Double.toString(boton1*boton2)); break;
                case "/": display.setText(Double.toString(boton1/boton2)); break;
                case "elevadoNum": display.setText(Double.toString(Math.pow(boton1, boton1)));
            }  
        }
        
        public void negativo () {  // Convertimos cualquier numero en negativo
            boton1 = Double.parseDouble((String)display.getText());
            display.setText(Double.toString(boton1 *-1));        
        }
        public void raiz () { // Obtenemos la raiz
            boton1 = Double.parseDouble((String)display.getText());
            display.setText(Double.toString(Math.sqrt(boton1)));        
        }
        
        public void elevarCuadrado() { // Elevar al cuadrado
            boton1 = Double.parseDouble((String)display.getText());
            display.setText(Double.toString(Math.pow(boton1,2))); 
        }
        
        public void elevarCubo() { // Elevar al cubo
            boton1 = Double.parseDouble((String)display.getText());
            display.setText(Double.toString(Math.pow(boton1,3))); 
        }
        
        public void factorial() { // Obtenemos el factorial del numero deseado
            Double factorial = 1.0;
            boton1 = Double.parseDouble((String)display.getText());
            for (Double i = boton1; i > 0; i--) {
                factorial = factorial * i;
            }
            display.setText(Double.toString(factorial)); 
        }
            
        public void AC () { // Vaciamos almacenado
            boton2 = 0.0;
            boton1 = 0.0;
            display.setText(""); 
        }
        
        public void MS () { // Guardamos el parametro en la "nube" para poder ser usado cuando se quiera
            memoriaNumInt=Double.parseDouble((String)display.getText()); // Asignamos a un atributo el contenido actual del textfield
            display.setText(""); // Vaciamos
        }
        
        public void MR () { // Recuperamos el dato guardado
            display.setText(memoriaNumInt.toString());  // Recuperamos el String 
            boton1 = Double.parseDouble((String)display.getText());

        }
        
        public void MC () { // Vaciamos todo lo almacenado de los parametros
            memoriaNumInt  = 0.0;
            display.setText("");
        }
        
        
        public void Mmas () { // Sumamos lo almacenado con el dato actual
            boton1 = Double.parseDouble((String)display.getText());
            memoriaNumInt = memoriaNumInt + boton1; 
            display.setText("");   
        }
        
        public void Mmenos () { //Restamos lo almacenado con el dato actual
            boton1 = Double.parseDouble((String)display.getText());
            memoriaNumInt = memoriaNumInt - boton1; 
            display.setText("");         
        }


    public static void main (String [] args) {
        Calculadora a = new Calculadora (); // Creamos nuestra calculadora
        a.setVisible(true); // La hacemos visible
        
        
    }

    @Override
    public void windowOpened(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override 
    public void windowClosing(WindowEvent e) { // Habilitamos el cierre de ventana
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void windowIconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void windowActivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
 
}
