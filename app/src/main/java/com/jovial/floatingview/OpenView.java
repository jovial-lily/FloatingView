package com.jovial.floatingview;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by:[ Jovial ]
 * Created date:[ 2018/1/25 0025]
 * About Class:[ 点击FloatView打开时弹出的dialog ]
 */

public class OpenView extends AlertDialog implements IOpenView{
    protected OpenView(Context context) {
        this(context,true , null);
    }

    protected OpenView(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_open_view);
    }

    @Override
    public void showView() {
        this.show();
    }

    @Override
    public void dismissView() {
        this.dismiss();
    }

    @Override
    public void setCancelListener(OnCancelListener listener) {
        this.setOnCancelListener(listener);
    }

}
