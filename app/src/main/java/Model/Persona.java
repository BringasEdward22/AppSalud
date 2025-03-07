package Model;

import androidx.annotation.NonNull;

public class Persona {
    private String nombre;
    private String apellido;
    private int edad;
    private String DNI;
    private double peso;
    private double altura;

    public Persona(String nombre, String apellido, int edad, String DNI, double peso, double altura) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.DNI = DNI;
        this.peso = peso;
        this.altura = altura;
    }

    public int calcularIMC() {
        double par = peso / (altura * altura);
        if (par < 20) {
            return -1;
        } else if (par >= 20 && par <= 25) {
            return 0;
        } else {
            return 1;
        }
    }
    public boolean esMayorDeEdad() {
        /*if(edad>=18)
            return true;
          else
            return false;
         */

        return edad >= 18;
    }

    public boolean verificarDNI() {
        return DNI.matches("\\d{8}");
    }
    /*public boolean verificarDNI(){
        if(dni.lenght()==8)
            return true;
        else
            return false
    }

     */

    @Override
    public String toString() {
        String estadoPeso;
        switch (calcularIMC()) {
            case -1: estadoPeso = "Bajo peso"; break;
            case 0: estadoPeso = "Peso ideal"; break;
            case 1: estadoPeso = "Sobrepeso"; break;
            default: estadoPeso = "Desconocido"; break;
        }

        return apellido + " " + nombre + " | " +" estado de su peso: "
                + estadoPeso + " | " + " y es: "+(esMayorDeEdad() ? "Mayor de edad" : "Menor de edad");

    }

    /*
    @NonNull
    @Override
    public String toString() {
        String[] tipoPeso={"debajo de ideal","ideal","sobre ideal"};
        return  apellidos+"; "+nombres+" tiene peso "+tipoPeso[calcuarIMC()+1]+" y es "+(esMayorDeEdad() ? "Mayor de edad" : "Menor de edad");
    }
     */


}
