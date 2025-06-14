package Modelo.DAO;

import Modelo.AtendimentoM;
import Modelo.BDConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Data Access Object para a entidade AtendimentoM.
 * Fornece operações CRUD para atendimentos.
 *  
 * @author Joao
 */
public class AtendimentoDAO {

    // Inserir novo Atendimento no banco de dados
    public boolean inserir(AtendimentoM atendimento) {
        String sql = "INSERT INTO atendimento (data_atendimento, observacoes, valor_final, id_pet, id_funcionario, id_servico) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = BDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(atendimento.getDataAtendimento()));
            stmt.setString(2, atendimento.getObservacoes());
            stmt.setBigDecimal(3, atendimento.getValorFinal());
            stmt.setInt(4, atendimento.getIdPet());
            stmt.setInt(5, atendimento.getIdFuncionario());
            stmt.setInt(6, atendimento.getIdServico());
            int linhasInseridas = stmt.executeUpdate();
            return linhasInseridas > 0;
        } catch (SQLException ex) {
            System.err.println("Erro ao inserir atendimento: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    // Atualizar Atendimento existente
    public boolean atualizar(AtendimentoM atendimento) {
        String sql = "UPDATE atendimento SET data_atendimento = ?, observacoes = ?, valor_final = ?, id_pet = ?, id_funcionario = ?, id_servico = ? WHERE id_atendimento = ?";
        try (Connection conn = BDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(atendimento.getDataAtendimento()));
            stmt.setString(2, atendimento.getObservacoes());
            stmt.setBigDecimal(3, atendimento.getValorFinal());
            stmt.setInt(4, atendimento.getIdPet());
            stmt.setInt(5, atendimento.getIdFuncionario());
            stmt.setInt(6, atendimento.getIdServico());
            stmt.setInt(7, atendimento.getIdAtendimento());
            int linhasAtualizadas = stmt.executeUpdate();
            return linhasAtualizadas > 0;
        } catch (SQLException ex) {
            System.err.println("Erro ao atualizar atendimento: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    // Excluir Atendimento pelo id
    public boolean excluir(int idAtendimento) {
        String sql = "DELETE FROM atendimento WHERE id_atendimento = ?";
        try (Connection conn = BDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAtendimento);
            int linhasDeletadas = stmt.executeUpdate();
            return linhasDeletadas > 0;
        } catch (SQLException ex) {
            System.err.println("Erro ao excluir atendimento: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    // Buscar Atendimento pelo id
    public AtendimentoM buscarPorId(int idAtendimento) {
        String sql = "SELECT id_atendimento, data_atendimento, observacoes, valor_final, id_pet, id_funcionario, id_servico FROM atendimento WHERE id_atendimento = ?";
        try (Connection conn = BDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAtendimento);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int atendimentoId = rs.getInt("id_atendimento");
                    LocalDateTime dataAtendimento = rs.getTimestamp("data_atendimento").toLocalDateTime();
                    String observacoes = rs.getString("observacoes");
                    BigDecimal valorFinal = rs.getBigDecimal("valor_final");
                    int idPet = rs.getInt("id_pet");
                    int idFuncionario = rs.getInt("id_funcionario");
                    int idServico = rs.getInt("id_servico");

                    // Para simplificação, os objetos PetM, FuncionarioM e ServicoM serão nulos
                    AtendimentoM atendimento = new AtendimentoM(atendimentoId, dataAtendimento, observacoes, valorFinal, idPet, idFuncionario, idServico, null, null, null);
                    return atendimento;
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao buscar atendimento pelo ID: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    // Listar todos os Atendimentos
    public List<AtendimentoM> listarTodos() {
        List<AtendimentoM> atendimentos = new ArrayList<>();
        String sql = "SELECT id_atendimento, data_atendimento, observacoes, valor_final, id_pet, id_funcionario, id_servico FROM atendimento ORDER BY data_atendimento DESC";
        try (Connection conn = BDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int atendimentoId = rs.getInt("id_atendimento");
                LocalDateTime dataAtendimento = rs.getTimestamp("data_atendimento").toLocalDateTime();
                String observacoes = rs.getString("observacoes");
                BigDecimal valorFinal = rs.getBigDecimal("valor_final");
                int idPet = rs.getInt("id_pet");
                int idFuncionario = rs.getInt("id_funcionario");
                int idServico = rs.getInt("id_servico");

                AtendimentoM atendimento = new AtendimentoM(atendimentoId, dataAtendimento, observacoes, valorFinal, idPet, idFuncionario, idServico, null, null, null);
                atendimentos.add(atendimento);
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao listar atendimentos: " + ex.getMessage());
            ex.printStackTrace();
        }
        return atendimentos;
    }
}
