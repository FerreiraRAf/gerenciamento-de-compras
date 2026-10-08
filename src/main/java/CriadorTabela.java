import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

//"CREATE TABLE produtos (" +
//        "id_produto INTEGER PRIMARY KEY," +
//        "nome_produto TEXT NOT NULL," +
//        "quantidade INTEGER," +
//        "preco REAL," +
//        "status TEXT" +
//        ");";

public class CriadorTabela {

    public static void main(String[] args) {
       try (Connection conexao = ConexaoDB.conectar(); Statement stmt = conexao.createStatement()) {

           String comandoSQL = "CREATE TABLE produtos (" +
                   "id_produto INTEGER PRIMARY KEY," +
                   "nome_produto TEXT NOT NULL," +
                   "quantidade INTEGER," +
                   "preco REAL," +
                   "status TEXT" +
                   ");";

           System.out.println("Comando executado com sucesso!");
           stmt.executeUpdate(comandoSQL);
       } catch (SQLException e) {
           System.err.println("Erro ao criar tabela no banco: " + e.getMessage());
       }
    }
}
