/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controle;
//imports necessários para manipulação de dados (sql)
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author rf5974
 */
//DAO: Data Access OBject

public class Database {
    // Objeto de Conexão e Desconexão com BD
    public Connection conn;
    // Objeto de comando DML (Dicionario de Manipulação de Dados)
    //DML: select, insert, delete e update
    public Statement stmt;
    // Objeto com dados (ArrayList com n tuplas) SQL
    private ResultSet res;
    
    /*
        PROCEDIMENTO (void) que realiza a conexão física com PostgreSQL
        COMO?
        1. Usar driver JDBC
        2. Informar login e senha do usuárioo do SGBD
        3. Define o Banco de dados e o Processo (Porta de conexão: 5432
    )
    */
   
    public void conexao(){                        
       try
       {
        Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection( "jdbc:postgresql://localhost:5433/Paradigmas","postgres", "ucdb");
        System.out.println("Conectado ao PostGreSQL.");        
        }catch(Exception e){
            System.out.println("Falha ao tentar a conexão");
            e.printStackTrace();
        }
       
       try{
           stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);      
       }catch(Exception e){
           System.out.println("Falha no Cursor de Execucao");
           e.printStackTrace();
       }
    }
    
     public Connection getConnection(){
        return conn;
    }
    
    public Statement getStatement(){
        return stmt;
    }
    
    public void desconecta(){
        if(conn != null){
            try{
                conn.close();
            }catch(SQLException erro){
                erro.printStackTrace();
            }
            
        }
    }
}

