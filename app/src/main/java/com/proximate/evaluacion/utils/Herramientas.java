package com.proximate.evaluacion.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.Nullable;

import com.proximate.evaluacion.R;

/**
 * Created by G1L21088 on 21/12/2017.
 */

public class Herramientas {

    public static ProgressDialog progressStat(Context context, @Nullable String mensaje) {
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getString(R.string.msg_cargando));
        progressDialog.setCancelable(false);
        if (mensaje != null)
            progressDialog.setMessage(mensaje);
        return progressDialog;
    }
}
