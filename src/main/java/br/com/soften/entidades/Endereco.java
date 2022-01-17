package br.com.soften.entidades;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


@Embeddable
public class Endereco implements Serializable {

    @NotEmpty(message = "Campo CEP é obrigatório")
    @Pattern(regexp = "^[0-9]+$", message = "Campo nome deve conter apenas letras")
    @Column(name = "DES_CEP", nullable = false, length = 8)
    private String cep;

    @NotEmpty(message = "Campo logradouro é obrigatório")
    @Column(name = "DES_LOGRADOURO", nullable = false, length = 60)
    private String logradouro;

    @NotEmpty(message = "Campo número é obrigatório")
    @Column(name = "DES_NUMERO", nullable = false, length = 6)
    private String numero;

    @NotEmpty(message = "Campo bairro é obrigatório")
    @Column(name = "DES_BAIRRO", nullable = false, length = 60)
    private String bairro;

    @Column(name = "DES_COMPLEMENTO", length = 60)
    private String complemento;

    @NotEmpty(message = "Campo cidade é obrigatório")
    @Column(name = "DES_CIDADE", nullable = false, length = 60)
    private String cidade;

    @NotEmpty(message = "Campo estado é obrigatório")
    @Column(name = "DES_ESTADO", nullable = false, length = 20)
    private String estado;

    @Transient
    private boolean encontrado = true;

    
    @Transient
    private String cepAntigo;

    @Transient
    private String logradouroAntigo;

    @Transient
    private String complementoAntigo;

    @Transient
    private String bairroAntigo;

    @Transient
    private String numeroAntigo;

    @Transient
    private String cidadeAntiga;

    @Transient
    private String ufAntiga;

    private static final long serialVersionUID = 1L;

    public Endereco() {

        super();

    }

    public Endereco(String logradouro, String numero, String complemento, String bairro, String cep, String cidade, String estado) {
        super();
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;

    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCepAntigo() {
        return cepAntigo;
    }

    public void setCepAntigo(String cepAntigo) {
        this.cepAntigo = cepAntigo;
    }

    public String getLogradouroAntigo() {
        return logradouroAntigo;
    }

    public void setLogradouroAntigo(String logradouroAntigo) {
        this.logradouroAntigo = logradouroAntigo;
    }

    public String getComplementoAntigo() {
        return complementoAntigo;
    }

    public void setComplementoAntigo(String complementoAntigo) {
        this.complementoAntigo = complementoAntigo;
    }

    public String getBairroAntigo() {
        return bairroAntigo;
    }

    public void setBairroAntigo(String bairroAntigo) {
        this.bairroAntigo = bairroAntigo;
    }

    public String getNumeroAntigo() {
        return numeroAntigo;
    }

    public void setNumeroAntigo(String numeroAntigo) {
        this.numeroAntigo = numeroAntigo;
    }

    public String getCidadeAntiga() {
        return cidadeAntiga;
    }

    public void setCidadeAntiga(String cidadeAntiga) {
        this.cidadeAntiga = cidadeAntiga;
    }

    public String getUfAntiga() {
        return ufAntiga;
    }

    public void setUfAntiga(String ufAntiga) {
        this.ufAntiga = ufAntiga;
    }

    public String getCidade() {

        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        String enderecoQualificado = logradouro + ", " + numero + " - " + bairro + " - CEP: " + cep + " - " + cidade + "-" + estado;
        return enderecoQualificado;
    }

    public boolean isEncontrado() {
        return encontrado;
    }

    public void setEncontrado(boolean encontrado) {
        this.encontrado = encontrado;
    }

}
