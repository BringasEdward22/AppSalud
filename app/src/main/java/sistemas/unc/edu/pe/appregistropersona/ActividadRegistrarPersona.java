package sistemas.unc.edu.pe.appregistropersona;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import AccesoDatos.DAOPersona;
import Model.Persona;

public class ActividadRegistrarPersona extends AppCompatActivity {

    EditText txtNombres, txtApellidos, txtEdad, txtDNI, txtPeso, txtAltura;
    Button btnRegistrar, btnListar;
    RadioGroup rgSexo;
    Spinner sp_ciudad;
    ImageView imgFoto;
    // public static List<Persona> listaPersonas;  borrar
    String[] ciudades = {"Seleccionar ciudad", "Cajamarca", "Trujillo", "Chiclayo"};
    byte[] imgSeleccionado = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_registrar_personas);

        Toolbar oBarra=findViewById(R.id.tbRegistrarPersonas);
        setSupportActionBar(oBarra);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtNombres = findViewById(R.id.txtNombres);
        txtApellidos = findViewById(R.id.txtApellidos);
        rgSexo = findViewById(R.id.rgSexo);
        sp_ciudad = findViewById(R.id.sp_ciudad);
        sp_ciudad.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ciudades));

        txtEdad = findViewById(R.id.txtEdad);
        txtDNI = findViewById(R.id.txtDNI);
        txtPeso = findViewById(R.id.txtPeso);
        txtAltura = findViewById(R.id.txtAltura);
        imgFoto = findViewById(R.id.imgFoto);
        imgFoto.setOnClickListener(v -> seleccionarFoto());

        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnListar = findViewById(R.id.btnListar);
        btnRegistrar.setOnClickListener(v -> registrarPersona());

        //listaPersonas = new ArrayList<>();
    }

    private void seleccionarFoto() {
        Intent oIntento = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        oIntento.setType("image/*");
        startActivityIfNeeded(Intent.createChooser(oIntento, "Seleccionar imagen"), 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent oIntento) {
        super.onActivityResult(requestCode, resultCode, oIntento);
        if (requestCode == 100 && resultCode == RESULT_OK) {

            Uri foto=oIntento.getData();
            imgFoto.setImageURI(foto);
            imgFoto.buildDrawingCache();
            Bitmap oBitmap= imgFoto.getDrawingCache();
            ByteArrayOutputStream oFlujo = new ByteArrayOutputStream();
            oBitmap.compress(Bitmap.CompressFormat.PNG,0,oFlujo);
            imgSeleccionado = oFlujo.toByteArray();

            //imgSeleccionado = oIntento.getData();
            //imgFoto.setImageURI(oIntento.getData());
        }
    }

    private void registrarPersona() {

       //if(validarControles()) // parte de la validacion hecha en clase
            //return;

        String nombres = txtNombres.getText().toString().trim();
        String apellidos = txtApellidos.getText().toString().trim();
        String sexo = obtenerSexoSeleccionado();
        String ciudad = sp_ciudad.getSelectedItem().toString();
        String edadTexto = txtEdad.getText().toString().trim();
        String dni = txtDNI.getText().toString().trim();
        String pesoTexto = txtPeso.getText().toString().trim();
        String alturaTexto = txtAltura.getText().toString().trim();

        if (nombres.isEmpty() || apellidos.isEmpty() || sexo.isEmpty() || ciudad.equals("Seleccionar ciudad") ||
                edadTexto.isEmpty() || dni.isEmpty() || pesoTexto.isEmpty() || alturaTexto.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        // validacion propia
        int edad;
        double peso, altura;

        try {
            edad = Integer.parseInt(edadTexto);
            if (edad < 1 || edad > 120) {
                Toast.makeText(this, "Edad inválida (1 - 120 años)", Toast.LENGTH_SHORT).show();
                txtEdad.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Ingrese una edad válida", Toast.LENGTH_SHORT).show();
            txtEdad.requestFocus();
            return;
        }

        try {
            peso = Double.parseDouble(pesoTexto);
            if (peso < 1 || peso > 300) {
                Toast.makeText(this, "Peso inválido (1 - 300 kg)", Toast.LENGTH_SHORT).show();
                txtPeso.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Ingrese un peso válido", Toast.LENGTH_SHORT).show();
            txtPeso.requestFocus();
            return;
        }

        try {
            altura = Double.parseDouble(alturaTexto);
            if (altura < 0.5 || altura > 2.5) {
                Toast.makeText(this, "Altura inválida (0.5 - 2.5 m)", Toast.LENGTH_SHORT).show();
                txtAltura.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Ingrese una altura válida", Toast.LENGTH_SHORT).show();
            txtAltura.requestFocus();
            return;
        }

        if (!dni.matches("\\d{8}")) {
            Toast.makeText(this, "DNI inválido (8 dígitos)", Toast.LENGTH_SHORT).show();
            txtDNI.requestFocus();
            return;
        }

        Persona oPersona = new Persona(nombres, apellidos, sexo, ciudad, edad, dni, peso, altura, imgSeleccionado);

        if (oPersona.verificarDNI()) {
            Toast.makeText(this, "Registro Correcto: " + oPersona.toString(), Toast.LENGTH_SHORT).show();
            //listaPersonas.add(oPersona); despues de borrar

            //ActividadPrincipal.listaPersonas.add(oPersona);

            //limpiar();
            DAOPersona oDAOPersona = new DAOPersona();
            if(oDAOPersona.Agregar(this,oPersona))
                //nuevo 2da unidad
                cuadroDialogo();



        } else {
            Toast.makeText(this, "No se registró. DNI inválido.", Toast.LENGTH_SHORT).show();
            txtDNI.requestFocus();
        }
    }

    //2da unidad
    private void cuadroDialogo() {
        AlertDialog.Builder oDialogo = new AlertDialog.Builder(this);
        oDialogo.setTitle("Aviso");
        oDialogo.setMessage("Desea seguir registrando");
        oDialogo.setIcon(R.drawable.baseline_add_alert_24);
        oDialogo.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
                ActividadRegistrarPersona.this.finish();
            }
        });
        oDialogo.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                limpiar();
            }
        });
        oDialogo.create();
        oDialogo.show();
    }



    // validacion hecha en clase

    /* private boolean validarControles() {
        if(comprobarCampoObligatorio(txtNombres,"Nombres")) return true;
        if(comprobarCampoObligatorio(txtApellidos,"Apellidos")) return true;
        if(obtenerSexoSeleccionado().isEmpty()){
            mostrarMensaje("Seleccionar un tipo de sexo de la persona");
            rgSexo.requestFocus();
            return true;
        }
        if(sp_ciudad.getSelectedItemPosition()==0){
            mostrarMensaje("Seleccionar ciudad de procedencia");
            sp_ciudad.requestFocus();
            return true;
        }
        if(comprobarCampoObligatorio(txtEdad,"Edad")) return true;
        if(comprobarCampoObligatorio(txtDNI,"DNI")) return true;
        if(comprobarCampoObligatorio(txtPeso,"Altura")) return true;
        if(imgSeleccionado==null){
            mostrarMensaje("Seleccionar una foto de galeria");
            imgFoto.requestFocus();
            return true;
        }
        return false;
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this,mensaje, Toast.LENGTH_SHORT).show();
    }



    private boolean comprobarCampoObligatorio(EditText campo,String mensaje){
        if(campo.getText().toString().trim().isEmpty()){
            campo.setError("Campo"+mensaje+"obligatorios");
            campo.requestFocus();
            return true;
        }
        return false;
    }

     */



    private String obtenerSexoSeleccionado() {
        int identificador = rgSexo.getCheckedRadioButtonId();
        if (identificador == R.id.rbFemenino) return "Femenino";
        if (identificador == R.id.rbMasculino) return "Masculino";
        return "No especificado";
    }

    private void limpiar() {
        txtNombres.setText("");
        txtApellidos.setText("");
        txtEdad.setText("");
        txtDNI.setText("");
        txtPeso.setText("");
        txtAltura.setText("");
        rgSexo.check(R.id.rbFemenino);
        sp_ciudad.setSelection(0);
        imgFoto.setImageResource(R.drawable.agregar_imagen);
        imgSeleccionado = null;
        txtNombres.requestFocus();
    }


     public void onClickListar(View boton){
        Intent oIntento = new Intent(this,ActividadListarPersonas.class);
        startActivity(oIntento);
     }


    /*public List<Persona> listaPersonas() {
        return listaPersonas;
    }

     */
}
