package sistemafinal;

import Controle.UsuarioC;
import controle.ProdutoC;
import modelo.ProdutoM;
import java.sql.ResultSet;

public class SistemaFinal {

    public static void main(String[] args) {
        ResultSet dadosBD;
        try {
            UsuarioC bancoUsuario = new UsuarioC();
            dadosBD = bancoUsuario.ConsultaGeralPorLogin("maria");
            while (dadosBD.next()) {
                System.out.print("LOGIN: " + dadosBD.getString("usuario"));
                System.out.println(" SENHA : " + dadosBD.getString(2));
            }

            bancoUsuario.AtualizaLogin("maria", "chega");
            dadosBD = bancoUsuario.ConsultaGeralPorLogin("maria");
            while (dadosBD.next()) {
                System.out.print("LOGIN: " + dadosBD.getString("usuario"));
                System.out.println(" SENHA : " + dadosBD.getString(2));
            }

            ProdutoC bancoProduto = new ProdutoC();

            // Criando e cadastrando um novo produto
            ProdutoM produto = new ProdutoM(1, "Caneta", 25, true);
            bancoProduto.Cadastrar(produto);

            // Consultando produtos
            dadosBD = bancoProduto.ConsultaGeralProduto();
            while (dadosBD.next()) {
                System.out.print("CÓDIGO: " + dadosBD.getInt("codigoProduto"));
                System.out.print(" | NOME: " + dadosBD.getString("nomeProduto"));
                System.out.print(" | VALOR: " + dadosBD.getDouble("valorUnitario"));
                System.out.println(" | DISPONÍVEL: " + dadosBD.getBoolean("disponivel"));
            }

            // Atualizando produto
            bancoProduto.AtualizaProduto(1, "Caneta Azul", 3.00, false);

            // Consultando produto específico
            dadosBD = bancoProduto.ConsultaProdutoPorCodigo(1);
            while (dadosBD.next()) {
                System.out.print("CÓDIGO: " + dadosBD.getInt("codigoProduto"));
                System.out.print(" | NOME: " + dadosBD.getString("nomeProduto"));
                System.out.print(" | VALOR: " + dadosBD.getDouble("valorUnitario"));
                System.out.println(" | DISPONÍVEL: " + dadosBD.getBoolean("disponivel"));
            }

            // Deletando produto
            bancoProduto.DeletaProduto(1);

        } catch (Exception erro) {
            erro.printStackTrace();
        }
    }
}