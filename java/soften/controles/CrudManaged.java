package soften.controles;

import java.io.Serializable;


public interface ICrudManagedBean extends Serializable {

    public void onNovo();

    public void onSalvar();

    public void onExcluir();

    public void onEditar();

}
