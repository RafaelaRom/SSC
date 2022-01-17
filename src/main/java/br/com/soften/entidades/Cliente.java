/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.soften.entidades;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

/**
 *
 * @author Renata Rafa
 */
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REGISTRO_TABELA", nullable = false, unique = true)
    private Long id;

    @Column(name = "FLG_JURIDICA", nullable = false)
    private boolean pessoaJuridica = false;

    @NotEmpty(message = "Campo nome / razão social é obrigatório")
    @Column(name = "DES_NOME_RAZAO", nullable = false)
    private String nome;

    @NotEmpty(message = "Campo inscrição estadual é obrigatório")
    @Column(name = "DES_INSCRICAO_ESTADUAL", nullable = false, length = 14)
    private String ie;

    @NotEmpty(message = "Campo CPF /CNPJ é obrigatório")
    @Column(name = "DES_CPF_CNPJ", nullable = false, length = 14)
    private String cpfCnpj;

    @Embedded 
    private Endereco endereco;

    public Cliente() {
    }

    public Cliente(String ie, String cpfCnpj, Endereco endereco) {
        this.ie = ie;
        this.cpfCnpj = cpfCnpj;
        this.endereco = endereco;
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

    public String getIe() {
        return ie;
    }

    public void setIe(String ie) {
        this.ie = ie;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        if (cpfCnpj != null) {
            cpfCnpj = cpfCnpj.replaceAll("[\\D]", "");
        }
        this.cpfCnpj = cpfCnpj;
    }

    public Endereco getEndereco() {
        if (endereco == null) {
            endereco = new Endereco();
        }
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public boolean isPessoaJuridica() {
        return pessoaJuridica;
    }

    public void setPessoaJuridica(boolean pessoaJuridica) {
        this.pessoaJuridica = pessoaJuridica;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Cliente other = (Cliente) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
