/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author Joao
 */
public class AtendimentoM {
    private int idAtendimento;
    private LocalDateTime dataAtendimento; // TIMESTAMP no BD
    private String observacoes;
    private BigDecimal valorFinal;
    private int idPet;          // Chave estrangeira para Pet
    private int idFuncionario;  // Chave estrangeira para Funcionario
    private int idServico;      // Chave estrangeira para Servico
    private PetM pet;
    private FuncionarioM funcionario;
    private ServicoM servico;

    public AtendimentoM(int idAtendimento, LocalDateTime dataAtendimento, String observacoes, BigDecimal valorFinal, int idPet, int idFuncionario, int idServico, PetM pet, FuncionarioM funcionario, ServicoM servico) {
        this.idAtendimento = idAtendimento;
        this.dataAtendimento = dataAtendimento;
        this.observacoes = observacoes;
        this.valorFinal = valorFinal;
        this.idPet = idPet;
        this.idFuncionario = idFuncionario;
        this.idServico = idServico;
        this.pet = pet;
        this.funcionario = funcionario;
        this.servico = servico;
    }

    public int getIdAtendimento() {
        return idAtendimento;
    }

    public void setIdAtendimento(int idAtendimento) {
        this.idAtendimento = idAtendimento;
    }

    public LocalDateTime getDataAtendimento() {
        return dataAtendimento;
    }

    public void setDataAtendimento(LocalDateTime dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public BigDecimal getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(BigDecimal valorFinal) {
        this.valorFinal = valorFinal;
    }

    public int getIdPet() {
        return idPet;
    }

    public void setIdPet(int idPet) {
        this.idPet = idPet;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public int getIdServico() {
        return idServico;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }

    public PetM getPet() {
        return pet;
    }

    public void setPet(PetM pet) {
        this.pet = pet;
    }

    public FuncionarioM getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(FuncionarioM funcionario) {
        this.funcionario = funcionario;
    }

    public ServicoM getServico() {
        return servico;
    }

    public void setServico(ServicoM servico) {
        this.servico = servico;
    }
    
    
    @Override
    public String toString() {
        return "Atendimento{" +
               "idAtendimento=" + idAtendimento +
               ", dataAtendimento=" + dataAtendimento +
               ", observacoes='" + observacoes + '\'' +
               ", valorFinal=" + valorFinal +
               ", idPet=" + idPet +
               ", idFuncionario=" + idFuncionario +
               ", idServico=" + idServico +
               '}';
    }
}
