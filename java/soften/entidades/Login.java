package soften.entidades;

import soften.tipos.TipoUsuario;
import soften.util.CriptografaSenha;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "login")
public class Login implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LOGIN", nullable = false, unique = true)
    private Long idLogin;

    @NotEmpty(message = "Campo login é obrigatório") 
    @Column(name = "DES_LOGIN", nullable = false, length = 20)
    private String login;

    @NotEmpty(message = "Campo login é obrigatório")
    @Column(name = "DES_SENHA", nullable = false, length = 100) //alterado pra 100 pois estou usando jasyp para codificar a senha
    private String senha;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    @Column(name = "FLG_ATIVO")
    private boolean ativo = true;

    @Transient
    private String senhaAtual;

    public Login() {

    }

    public Login(Usuario usuario) {
        this.usuario = usuario;
    }

    public Login(String usuario, String senha, TipoUsuario tipo, Usuario usuario1) {
        this.usuario = usuario1;
        setSenha(senha);
    }

    public Long getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(Long idLogin) {
        this.idLogin = idLogin;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public String getSenhaDescrip() {

        return CriptografaSenha.desCriptografa(senha);
    }

    public void setSenha(String senha) {
        this.senha = CriptografaSenha.criptografa(senha);

    }

    public void setSenhaCriptografada(String senha) {
        this.senha = senha;

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public String getSenhaAtualDescrip() {
        return CriptografaSenha.desCriptografa(senhaAtual);
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;

    }

    public boolean isAtivo() {
        return ativo;
    }

    public String getAtivoFormatado() {
        if (ativo) {
            return "Sim";
        } else {
            return "Não";
        }
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.idLogin);
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
        final Login other = (Login) obj;
        if (!Objects.equals(this.idLogin, other.idLogin)) {
            return false;
        }
        return true;
    }

}
