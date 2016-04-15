package com.qtd.muabannhadat.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Envy 15T on 6/5/2015.
 */
public class DialogUtil {

    public static <T extends Dialog> T showSimply(T dialog) {
        T showingDialog = null;
        if (dialog == null) {
            return null;
        }

        if (dialog.isShowing()) {
            showingDialog = dialog;
        } else {
            try {
                dialog.show();
                showingDialog = dialog;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return showingDialog;
    }

    public static void dismissQuietly(Dialog dialog) {
        if (dialog == null) {
            return;
        }
        try {
            dialog.dismiss();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static AlertDialog createDialogFull(Context context, String title, String message, String okText, String cancelText, DialogInterface.OnClickListener okOnClickListener, DialogInterface.OnClickListener cancelOnClickListener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        if (title != null && !title.isEmpty()) {
            alertDialogBuilder.setTitle(title);
        }

        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton(okText, okOnClickListener);

        alertDialogBuilder.setNegativeButton(cancelText, cancelOnClickListener);

        alertDialogBuilder.setCancelable(false);
        return alertDialogBuilder.create();
    }



    /**
     * Show common OK dialog
     *
     * @param context
     * @param title
     * @param message
     * @return
     */
    public static AlertDialog showDialog(final Context context, String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        if (title != null && !title.isEmpty()) {
            alertDialogBuilder.setTitle(title);
        }

        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialogBuilder.setCancelable(false);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return alertDialog;
    }

    /**
     * Show common OK dialog with button click callback
     *
     * @param context
     * @param title
     * @param message
     * @param onClickListener
     * @return
     */
    public static AlertDialog showDialog(final Context context, String title, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        if (title != null && !title.isEmpty()) {
            alertDialogBuilder.setTitle(title);
        }

        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton("OK",
                onClickListener);

        alertDialogBuilder.setCancelable(false);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return alertDialog;
    }

    public static AlertDialog showDialogJP(final Context context, String title, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        if (title != null && !title.isEmpty()) {
            alertDialogBuilder.setTitle(title);
        }

        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton("OK",
                onClickListener);

        alertDialogBuilder.setCancelable(false);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return alertDialog;
    }

    public static AlertDialog showDialogPasscode(final Context context, String title, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        if (title != null && !title.isEmpty()) {
            alertDialogBuilder.setTitle(title);
        }

        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton("ディスミス",
                onClickListener);

        alertDialogBuilder.setCancelable(false);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return alertDialog;
    }

    /**
     * Show common
     *
     * @param context
     * @param title
     * @param message
     * @param onClickListener
     * @return
     */
    public static AlertDialog showDialogCancelable(final Context context, String title, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        if (title != null && !title.isEmpty()) {
            alertDialogBuilder.setTitle(title);
        }

        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton("OK",
                onClickListener);

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialogBuilder.setCancelable(false);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return alertDialog;
    }

    public static AlertDialog showDialogFull(Context context, String title, String message, String okText, String cancelText, DialogInterface.OnClickListener okOnClickListener, DialogInterface.OnClickListener cancelOnClickListener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        if (title != null && !title.isEmpty()) {
            alertDialogBuilder.setTitle(title);
        }

        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton(okText, okOnClickListener);

        alertDialogBuilder.setNegativeButton(cancelText, cancelOnClickListener);

        alertDialogBuilder.setCancelable(false);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return alertDialog;
    }

    public static AlertDialog showDialogComfirm(Context context, String title, String message, String okText, DialogInterface.OnClickListener okOnClickListener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        if (title != null && !title.isEmpty()) {
            alertDialogBuilder.setTitle(title);
        }

        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton(okText, okOnClickListener);
        alertDialogBuilder.setCancelable(false);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return alertDialog;
    }

    public static AlertDialog showDialogComfirmInfo(Context context, String title, String message, String okText) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        if (title != null && !title.isEmpty()) {
            alertDialogBuilder.setTitle(title);
        }

        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton(okText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialogBuilder.setCancelable(false);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return alertDialog;
    }

    public static AlertDialog showApiErrorDialog(final Context context) {
        return showDialog(context, null, "API error");
    }

    public static AlertDialog showApiErrorDialog(final Context context, DialogInterface.OnClickListener onClickListener) {
        return showDialog(context, null, "API error", onClickListener);
    }

    public static AlertDialog showInputErrorDialog(final Context context) {
        return showDialog(context, null, "Please input data");
    }

    public static void showConfirm_JPDialog(Context context, String title, String message,
                                            DialogInterface.OnClickListener onOK, DialogInterface.OnClickListener onCancel) {
        showDialogFull(context, title, message, "OK", "キャンセル", onOK, onCancel);
    }

    public static void showConfirmLocation_JPDialog(Context context, String title, String message,
                                                    DialogInterface.OnClickListener onOK, DialogInterface.OnClickListener onCancel) {
        showDialogFull(context, title, message, "設定", "キャンセル", onOK, onCancel);
    }

    public static AlertDialog showJPDialogRetry(Context context, String title, String message,
                                                DialogInterface.OnClickListener onOK) {
        return showDialogComfirm(context, title, message, "リトライ", onOK);
    }

    public static AlertDialog showJPDialogErrorMedia(Context context, String title, String message, DialogInterface.OnClickListener onOK, DialogInterface.OnClickListener onCan) {
        return showDialogFull(context, title, message,"リトライ","キャンセル",   onOK, onCan);
    }
}
