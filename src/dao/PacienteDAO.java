// dao/PacienteDAO.java
package dao;

import model.Paciente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {
    private static PacienteDAO instancia;
    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;

    public static PacienteDAO getInstancia() {
        if (instancia == null) {
            instancia = new PacienteDAO();
        }
        return instancia;
    }


    // CREATE || UPDATE
    public void salvar(Paciente paciente) {
        if (paciente.getId() > 0 && buscarPorId(paciente.getId()) != null) {
            editarPaciente(paciente);
        } else {
            cadastrarPaciente(paciente);
        }
    }


    // CREATE
    public void cadastrarPaciente(Paciente paciente) {
        String sql = "INSERT INTO pacientes (nome, cpf, idade, email, telefone, horario) VALUES (?, ?, ?, ?, ?, ?)";
        conn = new Conexao().conectaBD();
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, paciente.getNome());
            pstm.setString(2, paciente.getCpf());
            pstm.setInt(3, paciente.getIdade());
            pstm.setString(4, paciente.getEmail());
            pstm.setString(5, paciente.getTelefone());
            pstm.setString(6, paciente.getHorario());
            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
        }
    }



    // READ *
    public List<Paciente> listarTodos() {
        String sql = "SELECT * FROM pacientes";
        conn = new Conexao().conectaBD();
        List<Paciente> lista = new ArrayList<>();
        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("id"));
                paciente.setNome(rs.getString("nome"));
                paciente.setCpf(rs.getString("cpf"));
                paciente.setIdade(rs.getInt("idade"));
                paciente.setEmail(rs.getString("email"));
                paciente.setTelefone(rs.getString("telefone"));
                paciente.setHorario(rs.getString("horario"));
                lista.add(paciente);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar: " + e.getMessage());
        }
        return lista;
    }

    // READ by id
    public Paciente buscarPorId(int id) {
        String sql = "SELECT * FROM pacientes WHERE id=?";
        conn = new Conexao().conectaBD();
        Paciente paciente = null;
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            if (rs.next()) {
                paciente = new Paciente();
                paciente.setId(rs.getInt("id"));
                paciente.setNome(rs.getString("nome"));
                paciente.setCpf(rs.getString("cpf"));
                paciente.setIdade(rs.getInt("idade"));
                paciente.setEmail(rs.getString("email"));
                paciente.setTelefone(rs.getString("telefone"));
                paciente.setHorario(rs.getString("horario"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar: " + e.getMessage());
        }
        return paciente;
    }



    // UPDATE
    public void editarPaciente(Paciente paciente) {
        String sql = "UPDATE pacientes SET nome=?, cpf=?, idade=?, email=?, telefone=?, horario=? WHERE id=?";
        conn = new Conexao().conectaBD();
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, paciente.getNome());
            pstm.setString(2, paciente.getCpf());
            pstm.setInt(3, paciente.getIdade());
            pstm.setString(4, paciente.getEmail());
            pstm.setString(5, paciente.getTelefone());
            pstm.setString(6, paciente.getHorario());
            pstm.setInt(7, paciente.getId());
            int linhas = pstm.executeUpdate();
            if (linhas > 0) {
                System.out.println("Registro alterado com sucesso!");
            } else {
                System.out.println("Nenhum registro foi atualizado.");
            }
            pstm.close();
        } catch (SQLException erro) {
            System.out.println("Erro ao alterar: " + erro.getMessage());
        }
    }



    // DELETE
    public void excluir(int id) {
        String sql = "DELETE FROM pacientes WHERE id=?";
        conn = new Conexao().conectaBD();
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
        }
    }
}