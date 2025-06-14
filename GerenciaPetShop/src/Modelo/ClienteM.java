/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.List;
/**
 *
 * @author Joao
 */
public class ClienteM extends PessoaM{
    private String endereco;
    private List<PetM> pets;

    public ClienteM(String endereco, List<PetM> pets, int id, String nome, String cpf, String telefone, String email) {
        super(id, nome, cpf, telefone, email);
        this.endereco = endereco;
        this.pets = pets;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<PetM> getPets() {
        return pets;
    }

    public void setPets(List<PetM> pets) {
        this.pets = pets;
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
        return "Cliente{" +
               "id=" + getId() +
               ", nome='" + getNome() + '\'' +
               ", cpf='" + getCpf() + '\'' +
               ", telefone='" + getTelefone() + '\'' +
               ", email='" + getEmail() + '\'' +
               ", endereco='" + endereco + '\'' +
               '}';
    }
}
