package Controle;

import Modelo.PetM;
import Modelo.DAO.PetDAO;

import java.util.List;

/**
 * Classe de controle para gerenciar operações relacionadas a Pets.
 * Atua como intermediário entre a camada de apresentação e a camada DAO.
 * 
 * @author Joao
 */
public class PetC {

    private PetDAO petDAO;

    public PetC() {
        this.petDAO = new PetDAO();
    }

    // Inserir novo Pet
    public boolean inserirPet(PetM pet) {
        if (pet.getNome() == null || pet.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome do pet não pode ser vazio.");
        }
        if (pet.getIdCliente() <= 0) {
            throw new IllegalArgumentException("ID do cliente inválido para o pet.");
        }
        return petDAO.inserir(pet);
    }

    // Atualizar Pet existente
    public boolean atualizarPet(PetM pet) {
        if (pet.getIdPet() <= 0) {
            throw new IllegalArgumentException("ID do pet inválido para atualização.");
        }
        return petDAO.atualizar(pet);
    }

    // Excluir Pet pelo ID
    public boolean excluirPet(int idPet) {
        if (idPet <= 0) {
            throw new IllegalArgumentException("ID do pet inválido para exclusão.");
        }
        return petDAO.excluir(idPet);
    }

    // Buscar Pet pelo ID
    public PetM buscarPetPorId(int idPet) {
        if (idPet <= 0) {
            throw new IllegalArgumentException("ID do pet inválido para busca.");
        }
        return petDAO.buscarPorId(idPet);
    }

    // Listar todos os Pets
    public List<PetM> listarPets() {
        return petDAO.listarTodos();
    }

    // Listar Pets pelo ID do Cliente
    public List<PetM> listarPetsPorClienteId(int clienteId) {
        if (clienteId <= 0) {
            throw new IllegalArgumentException("ID do cliente inválido para listar pets.");
        }
        return petDAO.listarPorClienteId(clienteId);
    }
}
