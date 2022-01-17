package soften.entidades;

import soften.tipos.TipoUsuario;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "usuario_sistema")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REGISTRO_TABELA")
    private Long id;

    @Column(name = "DES_NOME", nullable = false, length = 40) 
    @NotEmpty(message = "Campo nome é obrigatório") 
    private String nome;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_LOGIN")
    private Login login;

    @Enumerated(EnumType.ORDINAL)
    private TipoUsuario tipoUsuario;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ID_PERMISSAO_USER")
    private PermissaoDeUsuario permissaoDeUsuario;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataCadastro;

    public Usuario() {
        this.login = new Login(this);
        dataCadastro = new Date();
        tipoUsuario = TipoUsuario.USUARIO_COLABORADOR;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Login getLogin() {
        if (login == null) {
            this.login = new Login(this);
        }

        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public boolean isAdministrador() { 
        return tipoUsuario == TipoUsuario.USUARIO_ADMINISTRADOR;
    }

    public PermissaoDeUsuario getPermissaoDeUsuario() {
        return permissaoDeUsuario;
    }

    public void setPermissaoDeUsuario(PermissaoDeUsuario permissaoDeUsuario) {
        this.permissaoDeUsuario = permissaoDeUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
