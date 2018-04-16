package Controller.Dialog;

import Controller.Menu.MenuController;
import com.jfoenix.controls.JFXDialog;

/**
 * Dialog controller class. Contains all the methods used by the dialog controllers which extend this class.
 */
public abstract class DialogController {

    protected JFXDialog dialog;
    protected MenuController menuController;
    protected Object objectToModify;

    /**
     * Set dialog.
     * @param dialog The dialog.
     */
    public void setDialog(JFXDialog dialog) {
        this.dialog = dialog;
    }

    /**
     * Set menu controller.
     * @param menuController The menu controller.
     */
    public void setMenuController(MenuController menuController) { this.menuController = menuController; }

    /**
     * Set the object to modify.
     * @param objectToModify The object to modify.
     */
    public void setObjectToModify(Object objectToModify){
        this.objectToModify = objectToModify;
        initObjectToModify();
    }

    protected abstract void initObjectToModify();
}
