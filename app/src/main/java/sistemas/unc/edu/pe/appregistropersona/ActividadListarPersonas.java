package sistemas.unc.edu.pe.appregistropersona;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import AccesoDatos.DAOPersona;
import Model.Persona;

public class ActividadListarPersonas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_listar_personas);

        Toolbar oBarra2=findViewById(R.id.tbListadoPersonas);
        setSupportActionBar(oBarra2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView lvListaPersonas = findViewById(R.id.lv_ListaPersonas);
        // ya no lo utilizo

        // lvListaPersonas.setAdapter(new ArrayAdapter<Persona>(this,android.R.layout.simple_list_item_1, ActividadRegistrarPersona.listaPersonas));

        DAOPersona oDAOPersona = new DAOPersona();
        oDAOPersona.cargarLista(this);

        lvListaPersonas.setAdapter(new AdaptadorPersonas(oDAOPersona,this));
    }
}
/*
Toolbar oBarra=findViewById(R.id.tbRegistrarPersonas);
        setSupportActionBar(oBarra);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
 */