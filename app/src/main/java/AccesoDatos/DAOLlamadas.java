package AccesoDatos;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CallLog;
import android.telecom.Call;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class DAOLlamadas {
    private Uri oTabla;
    private String [] oColumna;

    public DAOLlamadas()
    {
        oTabla = CallLog.Calls.CONTENT_URI;
        oColumna = new String[]{CallLog.Calls.TYPE, CallLog.Calls.NUMBER};

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<String> ListarLlamadas(Activity contexto,String filtro)
    {
        List<String> lista = new ArrayList<>();
        try
        {
            ContentResolver oCR = contexto.getContentResolver();
            Cursor oRegistro = oCR.query(oTabla,oColumna,null,null);
            if (oRegistro.moveToFirst()) {
                do {
                    int iTipo = oRegistro.getInt(0);
                    String numero = oRegistro.getString(1);
                    String tipoLlamada[]={"Entrante","Saliente","Perdida","No contesto", "Bloqueada"};

                    //
                    if (iTipo >= 1 && iTipo <= tipoLlamada.length) {
                        String tipo = tipoLlamada[iTipo - 1];


                        if (filtro.equals("Todos") || tipo.equals(filtro)) {
                            lista.add("Llamada " + tipo + " - Número: " + numero);
                        }
                    }
                    //


                    //lista.add("Llamada " +tipoLlamada[iTipo-1] + " numero telf: " + numero);

                } while (oRegistro.moveToNext());
                oRegistro.close();
            }
        }catch (Exception ex)
        {
            Toast.makeText(contexto, "Error" +ex.getMessage(), Toast.LENGTH_SHORT).show();

        }


        return lista;
    }
}
