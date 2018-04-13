package Controller.Dialog;

import com.jfoenix.controls.JFXDialog;

public abstract class DialogController {

    protected JFXDialog dialog;

    public void setDialog(JFXDialog dialog) {
        this.dialog = dialog;
    }
}
