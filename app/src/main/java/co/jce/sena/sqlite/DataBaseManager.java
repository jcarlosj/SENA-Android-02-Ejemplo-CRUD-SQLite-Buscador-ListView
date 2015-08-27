package co.jce.sena.sqlite;

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

}
