package Controle;

import Modelo.FuncionarioM;
import Modelo.DAO.FuncionarioDAO;

import java.util.List;

/**
 * 
 * @author Joao
 */
public class FuncionarioC {

    private FuncionarioDAO funcionarioDAO;

    public FuncionarioC() {
        this.funcionarioDAO = new FuncionarioDAO();
    }

    // Inserir novo Funcionário
    public boolean inserirFuncionario(FuncionarioM funcionario) {
        if (funcionario.getNome() == null || funcionario.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome do funcionário não pode ser vazio.");
        }
        if (funcionario.getCargo() == null || funcionario.getCargo().isEmpty()) {
            throw new IllegalArgumentException("O cargo do funcionário não pode ser vazio.");
        }
        return funcionarioDAO.inserir(funcionario);
    }

    // Atualizar Funcionário existente
    public boolean atualizarFuncionario(FuncionarioM funcionario) {
        if (funcionario.getId() <= 0) {
            throw new IllegalArgumentException("ID do funcionário inválido para atualização.");
        }
        return funcionarioDAO.atualizar(funcionario);
    }

    // Excluir Funcionário pelo ID
    public boolean excluirFuncionario(int idFuncionario) {
        if (idFuncionario <= 0) {
            throw new IllegalArgumentException("ID do funcionário inválido para exclusão.");
        }
        return funcionarioDAO.excluir(idFuncionario);
    }

    // Buscar Funcionário pelo ID
    public FuncionarioM buscarFuncionarioPorId(int idFuncionario) {
        if (idFuncionario <= 0) {
            throw new IllegalArgumentException("ID do funcionário inválido para busca.");
        }
        return funcionarioDAO.buscarPorId(idFuncionario);
    }

    // Listar todos os Funcionários
    public List<FuncionarioM> listarFuncionarios() {
        return funcionarioDAO.listarTodos();
    }
}

