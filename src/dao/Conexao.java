package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public Connection conectaBD() {
        Connection conn = null;

        // Config do banco
        String url = "jdbc:mysql://localhost:3307/agendamento"; // port / nome do banco
        String username = "root"; // user
        String password = ""; // senha
        try {
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("✅ Conexão estabelecida com sucesso!");
        } catch (SQLException e) {
            System.out.println("❌ Erro ao conectar: " + e.getMessage());
        }
        return conn;
    }
}