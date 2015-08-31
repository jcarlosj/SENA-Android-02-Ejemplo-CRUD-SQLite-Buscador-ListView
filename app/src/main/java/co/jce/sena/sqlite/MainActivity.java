package co.jce.sena.sqlite;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    //-> Atributos (Componentes)
    private ListView lvContactos;

    //-> Atributos (Especiales)
    private DataBaseManager manager;
    private Cursor cContactos;
    private SimpleCursorAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //-> Accedemos a los componentes del "Activity"
        lvContactos = ( ListView ) findViewById( R .id .lvContactos );

        //-> Accedemos a la clase que construye la BD.
        //   (Instanciamos)
        manager = new DataBaseManager( this );

        //-> Cargamos la lista de contactos en el cursor
        cContactos = manager .listaContactos();

        //-> Insertando valores en la BD.
        manager .insertar_Android( "Laura Zapata A", "2795411" );
        manager .insertar_Tradicional("Juliana Muñoz Betancour", "2698541");
        manager .insertar_Android("Lina Ossa", "3569521");
        manager .insertar_Android( "Lina Marcela García Gomez", "7982014" );

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
}
