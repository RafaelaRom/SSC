package soften.entidades;

import soften.seguranca.Acao;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity(name = "acaodo_usuario")
public class AcaoDoUsuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String valorVazio;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "acaodousuario_acoes", joinColumns = {
        @JoinColumn(name = "id_acaodousuario")})
    @Column(name = "tipo_acao")
    private List<Acao> acoes;

    public boolean isPermitido(Acao acao) {
        if (acoes != null) {
            return acoes.contains(acao);
        }
        return false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValorVazio() {
        return valorVazio;
    }

    public void setValorVazio(String valorVazio) {
        this.valorVazio = valorVazio;
    }

    public List<Acao> getAcoes() {
        return acoes;
    }

    public void setAcoes(List<Acao> acoes) {
        this.acoes = acoes;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final AcaoDoUsuario other = (AcaoDoUsuario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
