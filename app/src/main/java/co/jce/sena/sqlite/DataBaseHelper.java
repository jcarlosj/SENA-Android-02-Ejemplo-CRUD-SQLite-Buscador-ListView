package co.jce.sena.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jce on 27/08/15.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    // Atributos (Constantes)
    private static final String DB_NAME = "contactos.sqlite";   //: Nombre del archivo de la base de datos, puede usar otra extensión soportada por SQLite o no llevarla.
    private static final int DB_SCHEME_VERSION = 1;             //: Número de la versión de la estructura de la base de datos, cada que la estructura cambia, cambia el número de la versión.

    // Constructor
    public DataBaseHelper( Context context ) {
        super( context, DB_NAME, null, DB_SCHEME_VERSION );
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {
        //-> Este método se encarga de crear la estructura de la base de datos.
        db .execSQL( DataBaseManager .CREATE_TABLE );

    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        //-> Este método se encarga de modificar la estructura de una tabla de la base de datos.
        //   Cuando una versión cambia se usa la CONSTANTE entera "DB_SCHEME_VERSION" y en este método a través de condicionales se indica la versión
        //   con la que se ha de trabajar. Es corriente que en los ejemplos de la implementación de este método se elimine y nuevamente se cree la BD,
        //   pero en la vida real no se debe hacer pues se perderían todos los datos reales de los usuarios de la aplicación.
    }
}
