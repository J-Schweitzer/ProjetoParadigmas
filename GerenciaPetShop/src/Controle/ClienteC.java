package Controle;

import Modelo.ClienteM;
import Modelo.DAO.ClienteDAO;

import java.util.List;

/**
 * 
 * @author Joao
 */
public class ClienteC {

    private ClienteDAO clienteDAO;

    public ClienteC() {
        this.clienteDAO = new ClienteDAO();
    }

    // Inserir novo Cliente
    public boolean inserirCliente(ClienteM cliente) {
        // Aqui podem ser feitas validações antes da inserção
        if (cliente.getNome() == null || cliente.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente não pode ser vazio.");
        }
        return clienteDAO.inserir(cliente);
    }

    // Atualizar Cliente existente
    public boolean atualizarCliente(ClienteM cliente) {
        if (cliente.getId() <= 0) {
            throw new IllegalArgumentException("ID do cliente inválido para atualização.");
        }
        return clienteDAO.atualizar(cliente);
    }

    // Excluir Cliente pelo ID
    public boolean excluirCliente(int idCliente) {
        if (idCliente <= 0) {
            throw new IllegalArgumentException("ID do cliente inválido para exclusão.");
        }
        return clienteDAO.excluir(idCliente);
    }

    // Buscar Cliente pelo ID
    public ClienteM buscarClientePorId(int idCliente) {
        if (idCliente <= 0) {
            throw new IllegalArgumentException("ID do cliente inválido para busca.");
        }
        return clienteDAO.buscarPorId(idCliente);
    }

    // Listar todos os Clientes
    public List<ClienteM> listarClientes() {
        return clienteDAO.listarTodos();
    }
}

