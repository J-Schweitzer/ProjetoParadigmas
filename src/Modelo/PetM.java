/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDate;

/**
 *
 * @author Joao
 */
public class PetM {
    private int idPet;
    private String nome;
    private String especie;
    private String raca;
    private LocalDate dataNascimento; // DATE no BD
    private String porte;
    private int idCliente;
    private ClienteM cliente;

    public PetM(int idPet, String nome, String especie, String raca, LocalDate dataNascimento, String porte, int idCliente, ClienteM cliente) {
        this.idPet = idPet;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.dataNascimento = dataNascimento;
        this.porte = porte;
        this.idCliente = idCliente;
        this.cliente = cliente;
    }

    public int getIdPet() {
        return idPet;
    }

    public void setIdPet(int idPet) {
        this.idPet = idPet;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public ClienteM getCliente() {
        return cliente;
    }

    public void setCliente(ClienteM cliente) {
        this.cliente = cliente;
    }
    
    @Override
    public String toString() {
        return "Pet{" +
               "idPet=" + idPet +
               ", nome='" + nome + '\'' +
               ", especie='" + especie + '\'' +
               ", raca='" + raca + '\'' +
               ", dataNascimento=" + dataNascimento +
               ", porte='" + porte + '\'' +
               ", idCliente=" + idCliente +
               '}';
    }
    
}
