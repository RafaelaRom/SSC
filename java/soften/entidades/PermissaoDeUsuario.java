package soften.entidades;

import soften.seguranca.AreaDoSistema;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;

@Entity(name = "permissao_de_usuario")
public class PermissaoDeUsuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "permissoes_usuario",
            joinColumns = @JoinColumn(name = "id_permissao_de_usuario", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_acaodousuario", referencedColumnName = "id"))
    @MapKeyColumn(name = "area_do_sistema")
    private Map<AreaDoSistema, AcaoDoUsuario> permissoes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<AreaDoSistema, AcaoDoUsuario> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(Map<AreaDoSistema, AcaoDoUsuario> permissoes) {
        this.permissoes = permissoes;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PermissaoDeUsuario other = (PermissaoDeUsuario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
