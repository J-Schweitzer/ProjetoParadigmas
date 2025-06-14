/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.math.BigDecimal;

/**
 *
 * @author Joao
 */
public class FuncionarioM extends PessoaM{
    private String cargo;
    private BigDecimal salario;

    public FuncionarioM(String cargo, BigDecimal salario, int id, String nome, String cpf, String telefone, String email) {
        super(id, nome, cpf, telefone, email);
        this.cargo = cargo;
        this.salario = salario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return "Funcionario{" +
               "id=" + getId() +
               ", nome='" + getNome() + '\'' +
               ", cpf='" + getCpf() + '\'' +
               ", telefone='" + getTelefone() + '\'' +
               ", email='" + getEmail() + '\'' +
               ", cargo='" + cargo + '\'' +
               ", salario=" + salario +
               '}';
    }

}
