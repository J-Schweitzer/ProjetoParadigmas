package Modelo.DAO;

import Modelo.ServicoM;
import Modelo.BDConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para a entidade ServicoM.
 * Fornece operações CRUD para serviços.
 *  
 * @author Joao
 */
public class ServicoDAO {

    // Inserir novo Serviço no banco de dados
    public boolean inserir(ServicoM servico) {
        String sql = "INSERT INTO servico (tipo_servico, descricao, valor_base) VALUES (?, ?, ?)";
        try (Connection conn = BDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, servico.getTipoServico());
            stmt.setString(2, servico.getDescricao());
            stmt.setBigDecimal(3, servico.getValorBase());
            int linhasInseridas = stmt.executeUpdate();
            return linhasInseridas > 0;
        } catch (SQLException ex) {
            System.err.println("Erro ao inserir serviço: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    // Atualizar Serviço existente
    public boolean atualizar(ServicoM servico) {
        String sql = "UPDATE servico SET tipo_servico = ?, descricao = ?, valor_base = ? WHERE id_servico = ?";
        try (Connection conn = BDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, servico.getTipoServico());
            stmt.setString(2, servico.getDescricao());
            stmt.setBigDecimal(3, servico.getValorBase());
            stmt.setInt(4, servico.getIdServico());
            int linhasAtualizadas = stmt.executeUpdate();
            return linhasAtualizadas > 0;
        } catch (SQLException ex) {
            System.err.println("Erro ao atualizar serviço: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    // Excluir Serviço pelo id
    public boolean excluir(int idServico) {
        String sql = "DELETE FROM servico WHERE id_servico = ?";
        try (Connection conn = BDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idServico);
            int linhasDeletadas = stmt.executeUpdate();
            return linhasDeletadas > 0;
        } catch (SQLException ex) {
            System.err.println("Erro ao excluir serviço: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    // Buscar Serviço pelo id
    public ServicoM buscarPorId(int idServico) {
        String sql = "SELECT id_servico, tipo_servico, descricao, valor_base FROM servico WHERE id_servico = ?";
        try (Connection conn = BDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idServico);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int servicoId = rs.getInt("id_servico");
                    String tipoServico = rs.getString("tipo_servico");
                    String descricao = rs.getString("descricao");
                    BigDecimal valorBase = rs.getBigDecimal("valor_base");
                    ServicoM servico = new ServicoM(servicoId, tipoServico, descricao, valorBase);
                    return servico;
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao buscar serviço pelo ID: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    // Listar todos os Serviços
    public List<ServicoM> listarTodos() {
        List<ServicoM> servicos = new ArrayList<>();
        String sql = "SELECT id_servico, tipo_servico, descricao, valor_base FROM servico ORDER BY tipo_servico";
        try (Connection conn = BDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int servicoId = rs.getInt("id_servico");
                String tipoServico = rs.getString("tipo_servico");
                String descricao = rs.getString("descricao");
                BigDecimal valorBase = rs.getBigDecimal("valor_base");
                ServicoM servico = new ServicoM(servicoId, tipoServico, descricao, valorBase);
                servicos.add(servico);
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao listar serviços: " + ex.getMessage());
            ex.printStackTrace();
        }
        return servicos;
    }
}

