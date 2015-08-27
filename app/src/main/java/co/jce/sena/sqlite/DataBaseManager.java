package co.jce.sena.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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

        //-> Instanciamos la clase "DataBaseHelper"
        helper = new DataBaseHelper( context );
        db = helper .getWritableDatabase();     //: Esta línea permite la creación de la BD. el método ".getWritableDatabase()" es un método de
                                                //: la clase "SQLiteOpenHelper" que se encarga de crear la BD si no existe y la establece en modo
                                                //: de escritura, si ya existe solo la devuelve.

    }

    private ContentValues contenedor_valores( String nombre, String telefono ) {
        valores = new ContentValues();
        valores .put( CN_NAME, nombre );
        valores .put( CN_PHONE, telefono );

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
        db .execSQL("insert into " + TABLE_NAME + " values ( null, '+ nombre +', '+ telefono +' )");
    }

}
