package AccesoDatos;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import Model.Persona;

public class DAOPersona {
    private String nombreDB;
    private int versionDB;
    List<Persona> listaPersonas;
    public DAOPersona()
    {
        nombreDB="BDSalud";
        versionDB=1;
        listaPersonas=new ArrayList<>();
    }
    public boolean Agregar(Activity contexto, Persona oP)
    {
        boolean rpta=false;
        BDOpenHelper oOH= new BDOpenHelper(contexto,nombreDB,null,versionDB);
        SQLiteDatabase oBD = oOH.getWritableDatabase();

        if(oBD!=null)
        {
            rpta = true;
            ContentValues oColumnas = new ContentValues();
            oColumnas.put("nonmbre",oP.getNombre());
            oColumnas.put("apellido",oP.getApellido());
            oColumnas.put("sexo",oP.getSexo());
            oColumnas.put("ciudad",oP.getCiudad());
            oColumnas.put("edad",oP.getEdad());
            oColumnas.put("DNI",oP.getDNI());
            oColumnas.put("peso",oP.getPeso());
            oColumnas.put("altura",oP.getAltura());
            oColumnas.put("foto",oP.getFoto());
            int fila= (int) oBD.insert("Persona",null,oColumnas);
            if(fila>0)
            {
                rpta=true;
            }
            oBD.close();
            oOH.close();

        }

        return rpta;
    }
    public boolean cargarLista(Activity contexto)
    {
        boolean rpta = false;
        BDOpenHelper oOH= new BDOpenHelper(contexto,nombreDB,null,versionDB);
        SQLiteDatabase oBD = oOH.getReadableDatabase();
        String sql = "Select * from Persona";
        Cursor oRegistros = oBD.rawQuery(sql,null);
        if(oRegistros.moveToFirst())
            rpta=true;
        do{
            // extraer indices(de la base de datos que existe en este caso del 0 al 9 de BDOpenHelper)
            String nombre = oRegistros.getString(1);
            String apellido = oRegistros.getString(2);
            String sexo = oRegistros.getString(3);
            String ciudad = oRegistros.getString(4);
            int edad = oRegistros.getInt(5);
            String DNI = oRegistros.getString(6);
            Double peso = oRegistros.getDouble(7);
            Double altura = oRegistros.getDouble(8);
            byte[] foto =  oRegistros.getBlob(9);
            Persona oP = new Persona(nombre,apellido,sexo,ciudad,edad,DNI,peso,altura,foto);
            listaPersonas.add(oP);
        }while(oRegistros.moveToNext());

        return rpta;
    }
    public  List<Persona> getListaPersonas()
    {
        return  listaPersonas;
    }
    public int getSize()
    {
        return listaPersonas.size();
    }

    public Persona getObjetoPersona(int indice)
    {
        return  listaPersonas.get(indice);
    }
}
