package sistemas.unc.edu.pe.appregistropersona;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Model.Persona;

public class ActividadPrincipal extends AppCompatActivity {

    // public static List<Persona> listaPersonas = new ArrayList<>();


    TextView lbContar,lbUltimaSesion;
    int contador;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.ly_principal);
            Toolbar cBarra = findViewById(R.id.toolbarPrincipal);
            setSupportActionBar(cBarra);
            lbContar = findViewById(R.id.lbContador);
            lbUltimaSesion = findViewById(R.id.lbUltimaSesion);

            //Archivo de preferencia
            /*SharedPreferences oFlujo = getSharedPreferences("control", Context.MODE_PRIVATE);
            contador = oFlujo.getInt("contar",1);
            lbContar.setText("N° Ingresos: "+contador);

             */
            SharedPreferences oFlujo = getSharedPreferences("control", Context.MODE_PRIVATE);
            contador = oFlujo.getInt("contar", 1); // Recuperar contador
            lbContar.setText("N° Ingresos: " + contador);

            String ultimaSesion = oFlujo.getString("ultima_sesion", "No disponible");
            lbUltimaSesion.setText("Última sesión: " + ultimaSesion);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences oFlujo = getSharedPreferences("control", Context.MODE_PRIVATE);
        SharedPreferences.Editor oEditar = oFlujo.edit();

        contador++;
        oEditar.putInt("contar",contador);

        String fechaHoraActual = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
        oEditar.putString("ultima_sesion", fechaHoraActual);

        oEditar.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent oIntento = null;

        if(item.getItemId()==R.id.itemSalir)
        {
            finish();
        }
        if(item.getItemId()==R.id.itemRegistrarPersonas)
        {
            oIntento=new Intent(this, ActividadRegistrarPersona.class);
            startActivity(oIntento);
        }
        if(item.getItemId()==R.id.itemListarPersonas)
        {
            oIntento=new Intent(this, ActividadListarPersonas.class);
            startActivity(oIntento);
        }

        if(item.getItemId()==R.id.itemLlamadas)
        {
            oIntento=new Intent(this, ActividadLlamadas.class);
            startActivity(oIntento);
        }
        return super.onOptionsItemSelected(item);
    }
}

