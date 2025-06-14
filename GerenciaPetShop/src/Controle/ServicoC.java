package Controle;

import Modelo.ServicoM;
import Modelo.DAO.ServicoDAO;

import java.util.List;

/**
 * Classe de controle para gerenciar operações relacionadas a Serviços.
 * Atua como intermediário entre a camada de apresentação e a camada DAO.
 *  
 * @author Joao
 */
public class ServicoC {

    private ServicoDAO servicoDAO;

    public ServicoC() {
        this.servicoDAO = new ServicoDAO();
    }

    // Inserir novo Serviço
    public boolean inserirServico(ServicoM servico) {
        if (servico.getTipoServico() == null || servico.getTipoServico().isEmpty()) {
            throw new IllegalArgumentException("O tipo do serviço não pode ser vazio.");
        }
        return servicoDAO.inserir(servico);
    }

    // Atualizar Serviço existente
    public boolean atualizarServico(ServicoM servico) {
        if (servico.getIdServico() <= 0) {
            throw new IllegalArgumentException("ID do serviço inválido para atualização.");
        }
        return servicoDAO.atualizar(servico);
    }

    // Excluir Serviço pelo ID
    public boolean excluirServico(int idServico) {
        if (idServico <= 0) {
            throw new IllegalArgumentException("ID do serviço inválido para exclusão.");
        }
        return servicoDAO.excluir(idServico);
    }

    // Buscar Serviço pelo ID
    public ServicoM buscarServicoPorId(int idServico) {
        if (idServico <= 0) {
            throw new IllegalArgumentException("ID do serviço inválido para busca.");
        }
        return servicoDAO.buscarPorId(idServico);
    }

    // Listar todos os Serviços
    public List<ServicoM> listarServicos() {
        return servicoDAO.listarTodos();
    }
}

