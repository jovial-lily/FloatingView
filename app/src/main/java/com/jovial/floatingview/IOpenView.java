package com.jovial.floatingview;

import android.content.DialogInterface;

/**
 * Created by:[ Jovial ]
 * Created date:[ 2018/1/25 0025]
 * About Class:[ OpenView 的接口，方便后续拓展自己的openView ]
 *
 */

public interface IOpenView  {
    void showView();
    void dismissView();
    void setCancelListener(DialogInterface.OnCancelListener listener);
}
