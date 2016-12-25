package com.warriorminds.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Random;

/**
 * Actividad que envía analytics usando las constantes de Firebase, y valores personalizados.
 * También envía propiedades de usuario.
 *
 * @author warriorminds
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Objeto de analytics de Firebase.
     */
    private FirebaseAnalytics analyticsFirebase;

    private String[] grupos = new String[] { "The Beatles", "Pink Floyd", "Led Zeppelin" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Obtenemos la instancia del objeto FirebaseAnalytics.
         */
        analyticsFirebase = FirebaseAnalytics.getInstance(this);
        inicializarVistas();
    }

    /**
     * Método para inicializar las vistas y asignar OnClickListeners a los botones.
     */
    private void inicializarVistas() {
        Button botonCompra = (Button) findViewById(R.id.boton_compra);
        botonCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                analyticsCompra();
            }
        });

        Button botonPersonalizado = (Button) findViewById(R.id.boton_personalizado);
        botonPersonalizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                analyticsPersonalizado();
            }
        });

        Button botonPropiedadUsuario = (Button) findViewById(R.id.boton_propiedad_usuario);
        botonPropiedadUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                propiedadUsuario();
            }
        });
    }

    /**
     * Método que envía una propiedad de usuario.
     */
    private void propiedadUsuario() {
        Random random = new Random(System.currentTimeMillis());
        String grupoFavorito = grupos[random.nextInt(3)];
        /**
         * setUserProperty(nombre, valor), envía la propiedad de usuario.
         * La propiedad debe de crearse antes en la consola de Firebase.
         */
        analyticsFirebase.setUserProperty("grupo_favorito", grupoFavorito);
    }

    /**
     * Método que envía analytics con valores personalizados.
     */
    private void analyticsPersonalizado() {
        Bundle bundle = new Bundle();
        Random random = new Random(System.currentTimeMillis());
        /**
         * Se agregan los parámetros en un bundle.
         */
        bundle.putDouble(FirebaseAnalytics.Param.VALUE, random.nextInt(100));
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "prueba");
        /**
         * Se envía el evento.
         */
        analyticsFirebase.logEvent("analytics_prueba", bundle);
    }

    /**
     * Método que envía analytics utilizando las constantes de Firebase.
     */
    private void analyticsCompra() {
        Bundle bundle = new Bundle();
        /**
         * Se agregan los parámetros en un bundle.
         */
        bundle.putString(FirebaseAnalytics.Param.CURRENCY, "MXN");
        bundle.putDouble(FirebaseAnalytics.Param.VALUE, 50.0);
        bundle.putString(FirebaseAnalytics.Param.TRANSACTION_ID, "compra2x1");
        /**
         * Se envía el evento.
         */
        analyticsFirebase.logEvent(FirebaseAnalytics.Event.ECOMMERCE_PURCHASE, bundle);
    }
}
