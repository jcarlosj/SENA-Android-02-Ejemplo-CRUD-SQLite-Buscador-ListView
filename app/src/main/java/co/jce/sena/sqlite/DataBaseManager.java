package co.jce.sena.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.net.ContentHandler;

/**
 * Created by jce on 27/08/15.
 */
public class DataBaseManager {

    //-> ATRIBUTOS (Constantes)
    private static final String TABLE_NAME = "contactos";   //: Nombre de la tabla.

    // Nombre de cada uno de los campos de la tabla "contactos"
    public static final String CN_ID = "_id";          //: Identificador de la tabla.
    public static final String CN_NAME = "nombre";     //: Nombre del contácto.
    public static final String CN_PHONE = "telefono";  //: Tel[efono del contácto.

    //
    private String columnas[];

    // Sentencia SQL para la creación de la tabla
    public static final String CREATE_TABLE = "create table " + TABLE_NAME + " ("
            + CN_ID + " integer primary key autoincrement, "
            + CN_NAME + " text not null,"
            + CN_PHONE + " text "
            + " ); ";

    //-> ATRIBUTOS
    private DataBaseHelper helper;
    private SQLiteDatabase db;
    private ContentValues valores;

    //-> Constructor
    public DataBaseManager( Context context ) {

        //-> Inicializamos el "Array" columnas.
        columnas = new String [] { CN_ID, CN_NAME, CN_PHONE };

        //-> Instanciamos la clase "DataBaseHelper"
        helper = new DataBaseHelper( context );
        db = helper .getWritableDatabase();     //: Esta línea permite la creación de la BD. el método ".getWritableDatabase()" es un método de
                                                //: la clase "SQLiteOpenHelper" que se encarga de crear la BD si no existe y la establece en modo
                                                //: de escritura, si ya existe solo la devuelve.

    }

    private ContentValues contenedor_valores( String nombre, String telefono ) {
        valores = new ContentValues();
        valores .put( CN_NAME, nombre );
        valores .put(CN_PHONE, telefono);

        return valores;
    }

    //-> Esta forma de insertar datos se realiza usando un método proporcionado por Android para
    //   la inserción de registros en la tabla correspondiente de una BD.
    public void insertar_Android( String nombre, String telefono ) {

        //-> db .insert( TABLE, NullColumnHack, ContentValues ); donde "NullColumnHack" puede ser "null" o un campo de la tabla que desde su creación
        //   permita valores núlos, por ejemplo "CN_PHONE"
        //      insert into table; / SQL-> insert into table (telefono) values(null)
        db .insert(TABLE_NAME, CN_PHONE, contenedor_valores(nombre, telefono));     //: Si devuelve - es por que ha ocurrido un error y no se ha realizado.

    }

    //-> Esta forma de insertar datos es la manera tradicional usando directamente una sentencia SQL.
    public void insertar_Tradicional( String nombre, String telefono ) {
        db .execSQL("insert into " + TABLE_NAME + " values ( null, '" + nombre + "', '" + telefono + "' )");
    }

    public void eliminar( String nombre ) {
        //-> db .delete( TABLE, WHERE, Array ARGUMENTS );
        //      TABLE: Nombre de la tabla
        //      WHERE: Nombre del campo a filtrar y ?, que significa donde se van a sustituir los argumentos
        //      ARGUMENTS:  Los valores que vamos a reemplazar en el filtro WHERE. Debe ser un "Array" de tipo "String"
        db .delete(TABLE_NAME, CN_NAME + "=?", new String[]{nombre});
    }

    public void eliminarMultiple( String nombre1, String nombre2 ) {
        db .delete(TABLE_NAME, CN_NAME + " IN ( ?, ? )", new String[]{nombre1, nombre2});
    }

    public void editarTelefono( String nombre, String nuevoTelefono ) {
        //-> db .update( TABLE, ContentValues, Clausula WHERE,  Array ARGUMENTS Where );
        db .update(TABLE_NAME, contenedor_valores(nombre, nuevoTelefono), CN_NAME + "=?", new String[]{nombre});
    }

    //-> Cargamos la lista de contactos en un cursor
    public Cursor listaContactos() {

        //-> db. query( String table, String [] columns, String selection, String [] selectionArgs, String groupBy, String having, String orderBy );
        return db .query( TABLE_NAME, columnas, null, null, null, null, null );
    }

    //-> Buscar contacto por nombre.
    public Cursor buscarContacto( String nombre ) {

        //-> Simulamos relentización de la BD.
        try {
            Thread .sleep( 7000 );      //: Detenemos (o dormimos) la ejecución de la aplicación por 7 segundos.
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }

        nombre = nombre .trim();                    //: Eliminamos los espacios al inicio y al final de la cadena.

        String cadenaABuscar = "%",                 //: Inicializamos la cadena de busqueda con "%".
               busqueda[] = nombre .split( " " );   //: Fragmentamos la busqueda por cada espacio en blanco.

        //-> Itera cada campo del "Array" para ser concatenado en la cadena final de búsqueda.
        for( String item : busqueda ) {
            cadenaABuscar += item + "%";            //: Concatenamos cada item del "Array" agregandole un "%"
        }

        return db .query( TABLE_NAME, columnas, CN_NAME + " LIKE ?", new String[] { cadenaABuscar } , null, null, null );
    }

}
