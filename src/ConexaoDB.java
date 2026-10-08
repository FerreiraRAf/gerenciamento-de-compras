import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//"jdbc:sqlite:meu_banco_de_dados.db"

public class ConexaoDB {
   private static final String SQL_JDBC = "jdbc:sqlite:meu_banco_de_dados.db";

   public static Connection conectar() {
       try {
           return DriverManager.getConnection(SQL_JDBC);
       } catch (SQLException e) {
           System.err.println("Erro ao conectar no banco: " + e.getMessage());
           return null;
       }
   }

    // Método para conectar com url, usuário e senha
//    public static Connection conectarGenerico(String url, String usuario, String senha) {
//        try {
//            return DriverManager.getConnection(url, usuario, senha);
//        } catch (SQLException e) {
//            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
//            return null;
//        }
//    }

    public static void main(String[] args) {

    }
}
