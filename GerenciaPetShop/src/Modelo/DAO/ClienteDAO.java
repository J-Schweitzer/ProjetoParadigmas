package Modelo.DAO;

import Modelo.ClienteM;
import Modelo.PetM;
import Modelo.BDConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para a entidade ClienteM.
 * Fornece operações CRUD para clientes.
 *  
 * @author Joao
 */
public class ClienteDAO {

    // Inserir novo Cliente no banco de dados
    public boolean inserir(ClienteM cliente) {
        String sql = "INSERT INTO cliente (nome, cpf, telefone, email, endereco) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = BDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getEndereco());
            int linhasInseridas = stmt.executeUpdate();
            return linhasInseridas > 0;
        } catch (SQLException ex) {
            System.err.println("Erro ao inserir cliente: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    // Atualizar Cliente existente
    public boolean atualizar(ClienteM cliente) {
        String sql = "UPDATE cliente SET nome = ?, cpf = ?, telefone = ?, email = ?, endereco = ? WHERE id = ?";
        try (Connection conn = BDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getEndereco());
            stmt.setInt(6, cliente.getId());
            int linhasAtualizadas = stmt.executeUpdate();
            return linhasAtualizadas > 0;
        } catch (SQLException ex) {
            System.err.println("Erro ao atualizar cliente: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    // Excluir Cliente pelo id
    public boolean excluir(int id) {
        String sql = "DELETE FROM cliente WHERE id = ?";
        try (Connection conn = BDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int linhasDeletadas = stmt.executeUpdate();
            return linhasDeletadas > 0;
        } catch (SQLException ex) {
            System.err.println("Erro ao excluir cliente: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    // Buscar Cliente pelo id
    public ClienteM buscarPorId(int id) {
        String sql = "SELECT id, nome, cpf, telefone, email, endereco FROM cliente WHERE id = ?";
        try (Connection conn = BDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int clienteId = rs.getInt("id");
                    String nome = rs.getString("nome");
                    String cpf = rs.getString("cpf");
                    String telefone = rs.getString("telefone");
                    String email = rs.getString("email");
                    String endereco = rs.getString("endereco");
                    // Pets list não carregada no DAO
                    ClienteM cliente = new ClienteM(endereco, new ArrayList<PetM>(), clienteId, nome, cpf, telefone, email);
                    return cliente;
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao buscar cliente pelo ID: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    // Listar todos os Clientes
    public List<ClienteM> listarTodos() {
        List<ClienteM> clientes = new ArrayList<>();
        String sql = "SELECT id, nome, cpf, telefone, email, endereco FROM cliente ORDER BY nome";
        try (Connection conn = BDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int clienteId = rs.getInt("id");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");
                String endereco = rs.getString("endereco");
                // Pets list não carregada no DAO
                ClienteM cliente = new ClienteM(endereco, new ArrayList<PetM>(), clienteId, nome, cpf, telefone, email);
                clientes.add(cliente);
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao listar clientes: " + ex.getMessage());
            ex.printStackTrace();
        }
        return clientes;
    }
}

