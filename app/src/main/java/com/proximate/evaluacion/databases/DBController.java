package com.proximate.evaluacion.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.proximate.evaluacion.network.DataItem;
import com.proximate.evaluacion.network.SeccionesItem;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.vansuita.sqliteparser.SqlParser;

/**
 * Created by G1L21088 on 22/12/2017.
 */

public class DBController {

    public static String[] createTables() {
        return new String[]{
                SqlParser.create("data")
                        .pk("id")
                        .str("nombres")
                        .str("apellidos")
                        .str("correo")
                        .str("numero_documento")
                        .str("ultima_sesion")
                        .num("eliminado")
                        .num("documentos_id")
                        .str("documentos_abrev")
                        .str("documentos_label")
                        .str("estados_usuarios_label")
                        .build(),
                SqlParser.create("secciones")
                        .pk("id")
                        .str("seccion")
                        .str("abrev")
                        .num("id_data")
                        .build()
        };

    }

    public static String[] dropTables() {
        return new String[]{
                SqlParser.delete("data").build(),
                SqlParser.delete("secciones").build()
        };
    }

    public static String insertData(DataItem dataItem) {
        return SqlParser.insert("data")
                .col("id", dataItem.getId())
                .col("nombres", dataItem.getNombres())
                .col("apellidos", dataItem.getApellidos())
                .col("correo", dataItem.getCorreo())
                .col("numero_documento", dataItem.getNumeroDocumento())
                .col("ultima_sesion", dataItem.getUltimaSesion())
                .col("eliminado", dataItem.getEliminado())
                .col("documentos_id", dataItem.getDocumentosId())
                .col("documentos_abrev", dataItem.getDocumentosAbrev())
                .col("documentos_label", dataItem.getDocumentosLabel())
                .col("estados_usuarios_label", dataItem.getEstadosUsuariosLabel())
                .build();
    }

    public static String insertSeccion(SeccionesItem seccionesItem, int id) {
        return SqlParser.insert("secciones")
                .col("id", seccionesItem.getId())
                .col("seccion", seccionesItem.getSeccion())
                .col("abrev", seccionesItem.getAbrev())
                .col("id_data", id)
                .build();
    }

    public static String selectData() {
        return SqlParser.query()
                .table("data")
                .table("secciones")
                .equal("data", "id", "secciones", "id_data")
                .build();
    }

}
