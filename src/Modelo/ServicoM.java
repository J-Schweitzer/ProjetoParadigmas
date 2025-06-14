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
public class ServicoM {
    private int idServico;
    private String tipoServico;
    private String descricao;
    private BigDecimal valorBase;

    public ServicoM(int idServico, String tipoServico, String descricao, BigDecimal valorBase) {
        this.idServico = idServico;
        this.tipoServico = tipoServico;
        this.descricao = descricao;
        this.valorBase = valorBase;
    }

    public int getIdServico() {
        return idServico;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValorBase() {
        return valorBase;
    }

    public void setValorBase(BigDecimal valorBase) {
        this.valorBase = valorBase;
    }
    
    @Override
    public String toString() {
        return "Servico{" +
               "idServico=" + idServico +
               ", tipoServico='" + tipoServico + '\'' +
               ", descricao='" + descricao + '\'' +
               ", valorBase=" + valorBase +
               '}';
    }
}
