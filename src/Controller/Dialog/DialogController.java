package Controller.Dialog;

import Controller.Menu.MenuController;
import com.jfoenix.controls.JFXDialog;

public abstract class DialogController {

    protected JFXDialog dialog;
    protected MenuController menuController;

    public void setDialog(JFXDialog dialog) {
        this.dialog = dialog;
    }

    public void setMenuController(MenuController menuController) { this.menuController = menuController; }
}
