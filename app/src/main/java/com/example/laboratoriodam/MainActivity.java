package com.example.laboratoriodam;

import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;

import android.widget.Toast;


import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    int porcentajeDesc; //?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EditText editTitulo = findViewById(R.id.editTitulo);
        EditText editDescripcion = findViewById(R.id.editDescripcion);
        EditText editCorreo = findViewById(R.id.editCorreo);
        EditText editPrecio = findViewById(R.id.editPrecio);
        EditText editDireccion = findViewById(R.id.editDireccion);


        TextView textTitulo = findViewById(R.id.titulo);
        TextView textDescripcion = findViewById(R.id.descripcion);
        TextView textCorreo = findViewById(R.id.correoContacto);
        TextView textPrecio = findViewById(R.id.precio);
        TextView textCategoria = findViewById(R.id.categoria);
        TextView textDireccion = findViewById(R.id.direccionRetiro);

        Button buttonPublicar = findViewById(R.id.botonPublicar);

        Spinner spinnerCateg = findViewById(R.id.spinnerCategorias);

        Switch switchDesc = findViewById(R.id.switchDescuento);

        SeekBar seekDesc = findViewById(R.id.seekBarDescuento);

        CheckBox checkRetiro = findViewById(R.id.checkBoxRetiro);
        CheckBox checkTerminos = findViewById(R.id.checkBoxAceptarTerminos);



        seekDesc.setVisibility(View.GONE);
        textDireccion.setEnabled(false);
        editDireccion.setEnabled(false);
        //?
        textDireccion.setVisibility(View.GONE);
        editDireccion.setVisibility(View.GONE);
        buttonPublicar.setEnabled(false);



        //MODIFICAR. Cerrar teclado en campos no editText

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                                                                            R.array.categorias,
                                                                            android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerCateg.setAdapter(adapter);

        switchDesc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchDesc.isChecked()) {
                    seekDesc.setVisibility(View.VISIBLE);
                } else {
                    seekDesc.setVisibility(View.GONE);
                    porcentajeDesc = 0;
                    seekDesc.setProgress(0);
                }
            }
        });

        seekDesc.setMax(100);
        seekDesc.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
             porcentajeDesc = progress;
             //switchDesc.setText(R.string.descuento + progress + R.string.porc); //Modificar
              switchDesc.setText("Ofrecer descuento de envío:      " + progress + "%"); //?
             //Thumb?
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        checkRetiro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
               if(checkRetiro.isChecked()){
                   textDireccion.setEnabled(true);
                   editDireccion.setEnabled(true);
                   //No mostrar o deshabilitado?
                   textDireccion.setVisibility(View.VISIBLE);
                   editDireccion.setVisibility(View.VISIBLE);
               }
               else {
                    textDireccion.setEnabled(false);
                    editDireccion.setEnabled(false);
                    textDireccion.setVisibility(View.GONE);
                    editDireccion.setVisibility(View.GONE);

               }
            }
        });


        checkTerminos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (checkTerminos.isChecked()) {
                    buttonPublicar.setEnabled(true);
                } else {
                    buttonPublicar.setEnabled(false);
                }
            }
        });

        buttonPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        // VER. error al utilizar R.string etc para los mensajes

        buttonPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensaje = "";

                //Solo validar obligatorios?
                if(editTitulo.getText().toString().length() == 0) {
                  //  mensaje += R.string.mensajeTitulo;
                    mensaje += "El campo de Titulo esta vacio. ";
                }
                if(editPrecio.getText().toString().length() == 0) {
                   // mensaje += R.string.mensajePrecioVacio;
                    mensaje += "El campo de Precio esta vacio. ";
                }
                if(spinnerCateg.getSelectedItemId() == 0){
                   // mensaje += R.string.mensajeCategoria;
                    mensaje += "No se ha seleccionado ninguna categoria. ";
                }
                if(checkRetiro.isChecked())
                    if(editDireccion.getText().toString().length() == 0) {
                       // mensaje += R.string.mensajeDireccion;
                        mensaje += "El campo de Direccion de retiro esta vacio. ";
                    }
                if(switchDesc.isChecked() && porcentajeDesc == 0) {
                   // mensaje += R.string.mensajeDescuento;
                    mensaje += "Por favor seleccione un porcentaje mayor a 0 o quite la opcion de ofrecer descuento de envio. ";
                    // Solo este mensaje?
                }
                if(Double.parseDouble((editPrecio.getText().toString())) <= 0){
                    //mensaje += R.string.mensajePrecioMenor;
                    mensaje += "El precio ingresado debe ser mayor a 0. ";
                }
                if(editCorreo.getText().toString().length() != 0){
                    if(!validarCorreo(editCorreo.getText().toString())) {
                        //mensaje += R.string.mensajeCorreo;
                        mensaje += "El correo electronico debe tener al menos una @ y 3 letras luego. ";
                    }
                }
                if(validarTextoCampo(editTitulo.getText().toString()) && validarTextoCampo(editDescripcion.getText().toString()) && validarTextoCampo(editDireccion.getText().toString())){
                   // mensaje += R.string.mensajeTextoCampo;
                    mensaje += "Los campos Titulo, Descripción y Dirección solo admiten letras, números, comas, puntos o saltos de linea.";
                }
                //
                if(mensaje.length() == 0){
                    //Toast.makeText(getApplicationContext(), R.string.mensajeExito, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "¡Publicación realizada con éxito!", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show(); //?
                //Limpiar campos

            }
        });

    }

    public boolean validarTextoCampo(String texto){
        Pattern p = Pattern.compile("[a-zA-Z0-9\\s.,]");
        //Texto multiple? [a-zA-Z0-9|,|.|,\s\n]*
        Matcher m = p.matcher(texto);
        boolean b = m.matches();
        return b;

    }

    //MODIFICAR. utilizar Pattern
    //Revisar (crashing)
    public boolean validarCorreo(String correo){
        correo = correo.toLowerCase();
        if(correo.contains("@")){
            int indiceArroba = correo.indexOf('@');
            //indexOf? -1?
            if(correo.length()- indiceArroba >= 3){
                if(correo.charAt(indiceArroba+1) >= 'a' && correo.charAt(indiceArroba+1) <= 'z'
                && correo.charAt(indiceArroba+2) >= 'a' && correo.charAt(indiceArroba+2) <= 'z'
                && correo.charAt(indiceArroba+3) >= 'a' && correo.charAt(indiceArroba+3) <= 'z')
                    return true;
            }
            //Mejorar validacion letras
        }
        return false;
    }

}