package Model;

import android.net.Uri;

import androidx.annotation.NonNull;

public class Persona {
    private String nombre;
    private String apellido;
    private String sexo;
    private String ciudad;
    private int edad;
    private String DNI;
    private double peso;
    private double altura;
    private byte[] foto; //cambio Uri

    public Persona(String nombre, String apellido,String sexo,String ciudad, int edad, String DNI, double peso, double altura,byte[] foto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo=sexo;
        this.ciudad=ciudad;
        this.edad = edad;
        this.DNI = DNI;
        this.peso = peso;
        this.altura = altura;
        this.foto=foto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDNI() {
        return DNI;
    }

    public byte[] getFoto() {
        return foto;
    }
//2unidad

        public String getNombreCompleto()
        {
            return apellido+" "+nombre;
        }
        public  String getTipoPeso()
        {
            String[] tipoPeso={"Bajo peso","Peso Ideal","Sobrepeso"};
            return tipoPeso[calcularIMC()];
        }
        public String getTipoPersona()
        {
            return esMayorDeEdad() ? "Mayor de edad" : "Menor de edad";
        }
        public String getSexo()
        {
            return sexo;
        }
        public String getCiudad() //procedencia
        {
            return ciudad;
        }

    public double getPeso() {
        return peso;
    }

    public int getEdad() {
        return edad;
    }

    public double getAltura() {
        return altura;
    }
    //-----------------------------------------------------------

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

        return apellido + " " + nombre +" estado de su peso: "
                + estadoPeso + " | " + " y es: "+(esMayorDeEdad() ? "Mayor de edad" : "Menor de edad");

    }

/*
    @NonNull
    @Override
    public String toString() {
        String[] tipoPeso={"Bajo peso","Peso Ideal","Sobrepeso"};
        return  apellido+"; "+nombre+" tiene peso "+tipoPeso[calcularIMC()+1]+" y es "+(esMayorDeEdad() ? "Mayor de edad" : "Menor de edad");
    }

 */



}
