package sistemas.unc.edu.pe.appregistropersona;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import AccesoDatos.DAOLlamadas;

public class ActividadLlamadas extends AppCompatActivity {

    private ListView lvListaLlamadas;
    private Spinner spFiltro;
    private DAOLlamadas oLlamadas;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ly_llamadas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //

        lvListaLlamadas = findViewById(R.id.lvListaLlamadas);
        spFiltro = findViewById(R.id.spFiltro);
        oLlamadas = new DAOLlamadas();


        // Definir opciones del Spinner
        String[] opcionesFiltro = {"Todos", "Entrante", "Saliente", "Perdida", "No contestó", "Bloqueada"};
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opcionesFiltro);
        spFiltro.setAdapter(adapterSpinner);

        // Mostrar todas las llamadas al inicio
        actualizarListaLlamadas("Todos");

        // Evento para manejar la selección del Spinner
        spFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String filtroSeleccionado = opcionesFiltro[position];
                actualizarListaLlamadas(filtroSeleccionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                actualizarListaLlamadas("Todos");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void actualizarListaLlamadas(String filtro) {
        List<String> llamadasFiltradas = oLlamadas.ListarLlamadas(this, filtro);
        lvListaLlamadas.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, llamadasFiltradas));
    }

    //




        /*
        ListView lvListaLlamadas = findViewById(R.id.lvListaLlamadas);
        DAOLlamadas oLlamadas = new DAOLlamadas();
        lvListaLlamadas.setAdapter(new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1,
                oLlamadas.ListarLlamadas(this)));

         */

}