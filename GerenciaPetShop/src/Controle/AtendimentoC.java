package Controle;

import Modelo.AtendimentoM;
import Modelo.DAO.AtendimentoDAO;

import java.util.List;

/**
 *  
 * @author Joao
 */
public class AtendimentoC {

    private AtendimentoDAO atendimentoDAO;

    public AtendimentoC() {
        this.atendimentoDAO = new AtendimentoDAO();
    }

    // Inserir novo Atendimento
    public boolean inserirAtendimento(AtendimentoM atendimento) {
        if (atendimento.getDataAtendimento() == null) {
            throw new IllegalArgumentException("Data do atendimento não pode ser nula.");
        }
        if (atendimento.getIdPet() <= 0) {
            throw new IllegalArgumentException("ID do pet inválido para o atendimento.");
        }
        if (atendimento.getIdFuncionario() <= 0) {
            throw new IllegalArgumentException("ID do funcionário inválido para o atendimento.");
        }
        if (atendimento.getIdServico() <= 0) {
            throw new IllegalArgumentException("ID do serviço inválido para o atendimento.");
        }
        return atendimentoDAO.inserir(atendimento);
    }

    // Atualizar Atendimento existente
    public boolean atualizarAtendimento(AtendimentoM atendimento) {
        if (atendimento.getIdAtendimento() <= 0) {
            throw new IllegalArgumentException("ID do atendimento inválido para atualização.");
        }
        return atendimentoDAO.atualizar(atendimento);
    }

    // Excluir Atendimento pelo ID
    public boolean excluirAtendimento(int idAtendimento) {
        if (idAtendimento <= 0) {
            throw new IllegalArgumentException("ID do atendimento inválido para exclusão.");
        }
        return atendimentoDAO.excluir(idAtendimento);
    }

    // Buscar Atendimento pelo ID
    public AtendimentoM buscarAtendimentoPorId(int idAtendimento) {
        if (idAtendimento <= 0) {
            throw new IllegalArgumentException("ID do atendimento inválido para busca.");
        }
        return atendimentoDAO.buscarPorId(idAtendimento);
    }

    // Listar todos os Atendimentos
    public List<AtendimentoM> listarAtendimentos() {
        return atendimentoDAO.listarTodos();
    }
}

