package co.jce.sena.sqlite;

import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //-> Atributos (Componentes)
    private ListView lvContactos;
    private EditText etBuscar;
    private ImageButton ibBuscar;

    //-> Atributos (Especiales)
    private DataBaseManager manager;
    private Cursor cContactos,
                   cBusqueda;
    private SimpleCursorAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //-> Accedemos a los componentes del "Activity"
        lvContactos = ( ListView ) findViewById( R .id .lvContactos );
        etBuscar = ( EditText ) findViewById( R .id .etBuscar );
        ibBuscar = ( ImageButton ) findViewById( R .id .ibBuscar );

        //-> Creamos el escuchador para el botón "Buscar"
        ibBuscar .setOnClickListener( this );

        //-> Accedemos a la clase que construye la BD.
        //   (Instanciamos)
        manager = new DataBaseManager( this );

        //-> Cargamos la lista de contactos en el cursor
        cContactos = manager .listaContactos();

        //-> Insertando valores en la BD.
        manager .insertar_Android( "Laura Zapata A", "2795411" );
        manager .insertar_Tradicional("Juliana Muñoz Betancour", "2698541");
        manager .insertar_Android("Lina Ossa", "3569521");
        manager .insertar_Android("Lina Marcela García Gomez", "7982014");
        manager .insertar_Android("Juliana Puerta Villada", "5369211");
        manager .insertar_Android("Shirley Patiño", "5996410");
        manager .insertar_Android("Ana María Fernández", "6332598");
        manager .insertar_Android( "Veronica Puerta Puerta", "5689531" );
        manager .insertar_Android( "Laura Cuatin", "25698410" );
        manager .insertar_Android( "Valentina Hoyos Farfan", "7592610" );

        //-> Creamos un "Array" con los valores de los campos traidos de la BD
        String from[] = new String[] {
                manager .CN_NAME,       //: Columna Nombre en la BD.
                manager .CN_PHONE       //: Columna Teléfono en la BD.
        };

        //-> Creamos un "Array" con los campos por defecto del "Layout" elegido (two_line_list_item)
        //   para desplegar los datos en el "ListView"
        int to[] = new int[] {
            android .R .id .text1,      //: Nombre del "TextView" del Layout "two_line_list_item".
            android .R .id .text2       //: Nombre del "TextView" del Layout "two_line_list_item".
        };

        //-> Instanciamos el Adaptador y le asociamos> el Contexto, el Layout, el Cursor (con la lista de contactos)
        //   el "Array" con los valores de la BD que nos interesan y el "Array" de los campos por defecto donde del
        //   "Layout" donde se van a mostrar.
        adaptador = new SimpleCursorAdapter( this, android .R .layout . two_line_list_item, cContactos, from, to, 0 );

        //-> Se asocia el adaptador al componente "ListView" donde se van a desplegar los datos en la Vista.
        lvContactos .setAdapter( adaptador );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick( View v ) {

        if( v .getId() == R .id .ibBuscar ) {

            //-> Hacemos un llamado a la clase privada y ella se encargará de ejecutar.
            //   cada tarea en primer o segundo plano seǵún lo requiera.
            new Tareas() .execute();

        }

    }

    //-> Creamos una clase privada de la clase principal con el objetivo de trabajar algunas tareas en segundo plano.
    //   esto nos permite evitar la relentización de algunas tareas (por ejemplo la carga de datos de una BD muy grande),
    //   que se ejecutan en primer plano.
    private class Tareas extends AsyncTask<Void, Void, Void> {

        private String buscar;

        //-> Segundo se ejecuta este método.
        //   Aquí se ejecutan tareas en segundo plano, que requieren estarlo por que pueden afectar
        //   el rendimiendo de la misma si se ejecutaran en primer plano.
        @Override
        protected Void doInBackground(Void... params) {

            //-> Colocamos la busqueda en un cursor nuevo.
            cBusqueda = manager .buscarContacto( buscar );     //: Una búsqueda puede relentizar la aplicación por lo cual la ejecutamos en segundo plano

            return null;
        }

        //-> Primero se ejecuta este método.
        //   Aquí se ejecuta dentro del hilo principal (o Activity principal) de la aplicación.
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            buscar = etBuscar .getText() .toString();           //: Se captura el valor del campo "EditText"
            Toast .makeText( getApplicationContext(), "Buscando...", Toast .LENGTH_SHORT ) .show();
        }

        //-> Tercero se ejecuta este método
        //   Una ves se termina la ejecución de las tareas en segundo plano este método se ejecuta
        //   y todas las tareas aqui contenidas lo harán en primer plano.
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            adaptador .changeCursor(cBusqueda);       //: Una vez finalizadas las tareas en segundo plano. Cambiamos el cursor que desplegará el "ListView"
            Toast .makeText( getApplicationContext(), "Finalizada.", Toast .LENGTH_SHORT ) .show();

        }
    }


}
