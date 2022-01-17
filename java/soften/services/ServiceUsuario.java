package soften.services;

import soften.entidades.Usuario;
import java.io.Serializable;

public interface ServiceUsuario extends Serializable, ServiceGenerico<Usuario> {

    public Usuario onLogar(Usuario usuario);
}
