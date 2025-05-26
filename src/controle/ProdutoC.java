/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import Controle.Database;

import java.sql.ResultSet;
import modelo.ProdutoM;

/**
 *
 * @author seu_nome
 */
public class ProdutoC {
    
    private Database dao = new Database();
    public ResultSet dadosConsulta;
    String comando = "";

    public void Cadastrar(ProdutoM produtom) {
        try {
            dao.conexao();
            String SQL = "INSERT INTO Produto(codigoProduto, nomeProduto, valorUnitario, disponivel) VALUES ('"
                    + produtom.getCodigoProduto() + "', '" + produtom.getNomeProduto() + "', '"
                    + produtom.getValorUnitario() + "', '" + produtom.isDisponivel() + "')";
            dao.getStatement().execute(SQL);
            dao.desconecta();
        } catch (Exception tipoExcecao) {
            System.out.println("Erro ao cadastrar produto!");
            tipoExcecao.printStackTrace();
        }
    }

    public ResultSet ConsultaGeralProduto() {
        try {
            dao.conexao();
            String SQL = "SELECT * FROM Produto";
            dadosConsulta = dao.getStatement().executeQuery(SQL);
            dao.desconecta();
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return dadosConsulta;
    }

    public ResultSet ConsultaProdutoPorCodigo(int codigoProduto) {
        try {
            dao.conexao();
            String SQL = "SELECT * FROM Produto WHERE codigoProduto = '" + codigoProduto + "'";
            dadosConsulta = dao.getStatement().executeQuery(SQL);
            dao.desconecta();
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        return dadosConsulta;
    }

    public void DeletaProduto(int codigoProduto) {
        try {
            dao.conexao();
            String SQL = "DELETE FROM Produto WHERE codigoProduto = '" + codigoProduto + "'";
            dao.getStatement().execute(SQL);
            System.out.println("Produto deletado.");
            dao.desconecta();
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }

    public void AtualizaProduto(int codigoProduto, String nomeProduto, double valorUnitario, boolean disponivel) {
        try {
            dao.conexao();
            String SQL = "UPDATE Produto SET nomeProduto = '" + nomeProduto + "', valorUnitario = '" + valorUnitario
                    + "', disponivel = '" + disponivel + "' WHERE codigoProduto = '" + codigoProduto + "'";
            dao.getStatement().execute(SQL);
            System.out.println("Produto atualizado.");
            dao.desconecta();
        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }
}
