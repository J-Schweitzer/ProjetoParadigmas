/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controle;
import java.sql.ResultSet;
import modelo.UsuarioM;


public class UsuarioC {
    
    private Database dao=new Database();
    //public Statement stmt;    
    
    
    
    public ResultSet dadosConsulta;
    String comando="";
    
    public void Cadastrar(UsuarioM usuariom){
        try{
            dao.conexao();
            String SQL = "insert into login(usuario,senha) values ('"+usuariom.getUsuario()+"', '"+usuariom.getSenha()+"')";
            
            dao.getStatement().execute(SQL);
            dao.desconecta();
        }catch(Exception tipoExcecao){
            System.out.println("ERRO!");
            tipoExcecao.printStackTrace();
        }
    }
    
    public ResultSet ConsultaGeralLogin(){
        //1. Try e Catch
        //2. Conexão com Database
        //3. Comando SQL: String e Statement
        //4. Fechar a conexão
        try{
            dao.conexao();
            String SQL="select * from login";
            dadosConsulta=dao.getStatement().executeQuery(SQL);
            dao.desconecta();
        }catch(Exception erro){
            erro.printStackTrace();
        }
        return dadosConsulta;
    }
    
    public ResultSet  ConsultaGeralPorLogin(String login){
        //1. Try e Catch
        //2. Conexão com Database
        //3. Comando SQL: String e Statement
        //4. Fechar a conexão
        
        try{
            dao.conexao();
            String SQL="Select * from login where usuario='"+login+"'";
            dadosConsulta=dao.getStatement().executeQuery(SQL);
            dao.desconecta();
        }catch(Exception erro){
            erro.printStackTrace();
        }
        return dadosConsulta;
    }
    
    public void DeletaLogin(String login){
        try{
            dao.conexao();
            String SQL="delete from login where usuario='"+login+"'";
            dao.getStatement().execute(SQL);
            System.out.println("Deletou");
            dao.desconecta();
            
        }catch(Exception erro){
            erro.printStackTrace();
        }
    }
    
    public void AtualizaLogin(String login,String senha){
        try{
            dao.conexao();
            String SQL="update login set senha='"+senha+"',usuario='"+login+"'"
                    + "where usuario='"+login+"'";
            dao.getStatement().execute(SQL);
            System.out.println("Atualizou");
            dao.desconecta();
            
        }catch(Exception erro){
            erro.printStackTrace();
        }
    }
}
