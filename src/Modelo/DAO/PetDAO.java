package Modelo.DAO;

import Modelo.PetM;
import Modelo.BDConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para a entidade PetM.
 * Fornece operações CRUD para pets.
 * 
 * @author Joao
 */
public class PetDAO {

    // Inserir novo Pet no banco de dados
    public boolean inserir(PetM pet) {
        String sql = "INSERT INTO pet (nome, especie, raca, data_nascimento, porte, id_cliente) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = BDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pet.getNome());
            stmt.setString(2, pet.getEspecie());
            stmt.setString(3, pet.getRaca());
            stmt.setDate(4, Date.valueOf(pet.getDataNascimento()));
            stmt.setString(5, pet.getPorte());
            stmt.setInt(6, pet.getIdCliente());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            System.err.println("Erro ao inserir pet: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    // Atualizar Pet existente
    public boolean atualizar(PetM pet) {
        String sql = "UPDATE pet SET nome = ?, especie = ?, raca = ?, data_nascimento = ?, porte = ?, id_cliente = ? WHERE id_pet = ?";
        try (Connection conn = BDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pet.getNome());
            stmt.setString(2, pet.getEspecie());
            stmt.setString(3, pet.getRaca());
            stmt.setDate(4, Date.valueOf(pet.getDataNascimento()));
            stmt.setString(5, pet.getPorte());
            stmt.setInt(6, pet.getIdCliente());
            stmt.setInt(7, pet.getIdPet());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            System.err.println("Erro ao atualizar pet: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    // Excluir Pet pelo id
    public boolean excluir(int idPet) {
        String sql = "DELETE FROM pet WHERE id_pet = ?";
        try (Connection conn = BDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPet);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException ex) {
            System.err.println("Erro ao excluir pet: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    // Buscar Pet pelo id
    public PetM buscarPorId(int idPet) {
        String sql = "SELECT id_pet, nome, especie, raca, data_nascimento, porte, id_cliente FROM pet WHERE id_pet = ?";
        try (Connection conn = BDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPet);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int petId = rs.getInt("id_pet");
                    String nome = rs.getString("nome");
                    String especie = rs.getString("especie");
                    String raca = rs.getString("raca");
                    LocalDate dataNascimento = rs.getDate("data_nascimento").toLocalDate();
                    String porte = rs.getString("porte");
                    int idCliente = rs.getInt("id_cliente");
                    PetM pet = new PetM(petId, nome, especie, raca, dataNascimento, porte, idCliente, null);
                    return pet;
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao buscar pet pelo ID: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    // Listar todos os Pets
    public List<PetM> listarTodos() {
        List<PetM> pets = new ArrayList<>();
        String sql = "SELECT id_pet, nome, especie, raca, data_nascimento, porte, id_cliente FROM pet ORDER BY nome";
        try (Connection conn = BDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int petId = rs.getInt("id_pet");
                String nome = rs.getString("nome");
                String especie = rs.getString("especie");
                String raca = rs.getString("raca");
                LocalDate dataNascimento = rs.getDate("data_nascimento").toLocalDate();
                String porte = rs.getString("porte");
                int idCliente = rs.getInt("id_cliente");
                PetM pet = new PetM(petId, nome, especie, raca, dataNascimento, porte, idCliente, null);
                pets.add(pet);
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao listar pets: " + ex.getMessage());
            ex.printStackTrace();
        }
        return pets;
    }

    // Opcional: Listar Pets pelo ID do Cliente (dono)
    public List<PetM> listarPorClienteId(int clienteId) {
        List<PetM> pets = new ArrayList<>();
        String sql = "SELECT id_pet, nome, especie, raca, data_nascimento, porte, id_cliente FROM pet WHERE id_cliente = ? ORDER BY nome";
        try (Connection conn = BDConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int petId = rs.getInt("id_pet");
                    String nome = rs.getString("nome");
                    String especie = rs.getString("especie");
                    String raca = rs.getString("raca");
                    LocalDate dataNascimento = rs.getDate("data_nascimento").toLocalDate();
                    String porte = rs.getString("porte");
                    int idCliente = rs.getInt("id_cliente");
                    PetM pet = new PetM(petId, nome, especie, raca, dataNascimento, porte, idCliente, null);
                    pets.add(pet);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao listar pets pelo ID do cliente: " + ex.getMessage());
            ex.printStackTrace();
        }
        return pets;
    }
}

