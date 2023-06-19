package arquitectura.trescapas.primerparcial.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.HashMap;

public class DBconexion extends SQLiteOpenHelper {

    private static final   String DB_NAME = "dbarqui";
    private  static final  int DB_VERSION = 1;

    public DBconexion(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(DBmigrations.TABLA_CLIENTE_CREATE);

        sqLiteDatabase.execSQL(DBmigrations.TABLA_REPARTIDOR_CREATE);

        sqLiteDatabase.execSQL(DBmigrations.TABLA_CATEGORIA_CREATE);

        sqLiteDatabase.execSQL(DBmigrations.TABLA_PRODUCTO_CREATE);

        sqLiteDatabase.execSQL(DBmigrations.TABLA_PEDIDO_CREATE);

        sqLiteDatabase.execSQL(DBmigrations.TABLA_COTIZACION_CREATE);

        sqLiteDatabase.execSQL(DBmigrations.TABLA_DETALLE_COTIZACION_CREATE);

    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL( "DROP TABLE IF EXISTS "+ DBmigrations.TABLA_CLIENTE );
        sqLiteDatabase.execSQL( "DROP TABLE IF EXISTS "+ DBmigrations.TABLA_REPARTIDOR );
        sqLiteDatabase.execSQL( "DROP TABLE IF EXISTS "+ DBmigrations.TABLA_CATEGORIA );
        sqLiteDatabase.execSQL( "DROP TABLE IF EXISTS "+ DBmigrations.TABLA_PRODUCTO );
        sqLiteDatabase.execSQL( "DROP TABLE IF EXISTS "+ DBmigrations.TABLA_PEDIDO_CREATE );
        sqLiteDatabase.execSQL( "DROP TABLE IF EXISTS "+ DBmigrations.TABLA_COTIZACION_CREATE );
        sqLiteDatabase.execSQL( "DROP TABLE IF EXISTS "+ DBmigrations.TABLA_DETALLE_COTIZACION_CREATE );

    }
}
