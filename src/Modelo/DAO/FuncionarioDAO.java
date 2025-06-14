package Modelo.DAO;

import Modelo.FuncionarioM;
import Modelo.BDConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para a entidade FuncionarioM.
 * Fornece operações CRUD para funcionários.
 *  
 * @author Joao
 */
public class FuncionarioDAO {

    // Inserir novo Funcionario no banco de dados
    public boolean inserir(FuncionarioM funcionario) {
        String sql = "INSERT INTO funcionario (nome, cpf, telefone, email, cargo, salario) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = BDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setString(3, funcionario.getTelefone());
            stmt.setString(4, funcionario.getEmail());
            stmt.setString(5, funcionario.getCargo());
            stmt.setBigDecimal(6, funcionario.getSalario());
            int linhasInseridas = stmt.executeUpdate();
            return linhasInseridas > 0;
        } catch (SQLException ex) {
            System.err.println("Erro ao inserir funcionário: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    // Atualizar Funcionario existente
    public boolean atualizar(FuncionarioM funcionario) {
        String sql = "UPDATE funcionario SET nome = ?, cpf = ?, telefone = ?, email = ?, cargo = ?, salario = ? WHERE id = ?";
        try (Connection conn = BDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setString(3, funcionario.getTelefone());
            stmt.setString(4, funcionario.getEmail());
            stmt.setString(5, funcionario.getCargo());
            stmt.setBigDecimal(6, funcionario.getSalario());
            stmt.setInt(7, funcionario.getId());
            int linhasAtualizadas = stmt.executeUpdate();
            return linhasAtualizadas > 0;
        } catch (SQLException ex) {
            System.err.println("Erro ao atualizar funcionário: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    // Excluir Funcionario pelo id
    public boolean excluir(int id) {
        String sql = "DELETE FROM funcionario WHERE id = ?";
        try (Connection conn = BDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int linhasDeletadas = stmt.executeUpdate();
            return linhasDeletadas > 0;
        } catch (SQLException ex) {
            System.err.println("Erro ao excluir funcionário: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    // Buscar Funcionario pelo id
    public FuncionarioM buscarPorId(int id) {
        String sql = "SELECT id, nome, cpf, telefone, email, cargo, salario FROM funcionario WHERE id = ?";
        try (Connection conn = BDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int funcionarioId = rs.getInt("id");
                    String nome = rs.getString("nome");
                    String cpf = rs.getString("cpf");
                    String telefone = rs.getString("telefone");
                    String email = rs.getString("email");
                    String cargo = rs.getString("cargo");
                    java.math.BigDecimal salario = rs.getBigDecimal("salario");
                    FuncionarioM funcionario = new FuncionarioM(cargo, salario, funcionarioId, nome, cpf, telefone, email);
                    return funcionario;
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao buscar funcionário pelo ID: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    // Listar todos os Funcionários
    public List<FuncionarioM> listarTodos() {
        List<FuncionarioM> funcionarios = new ArrayList<>();
        String sql = "SELECT id, nome, cpf, telefone, email, cargo, salario FROM funcionario ORDER BY nome";
        try (Connection conn = BDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int funcionarioId = rs.getInt("id");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");
                String cargo = rs.getString("cargo");
                java.math.BigDecimal salario = rs.getBigDecimal("salario");
                FuncionarioM funcionario = new FuncionarioM(cargo, salario, funcionarioId, nome, cpf, telefone, email);
                funcionarios.add(funcionario);
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao listar funcionários: " + ex.getMessage());
            ex.printStackTrace();
        }
        return funcionarios;
    }
}

