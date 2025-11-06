// dao/PacienteDAO.java
package dao;

import model.Paciente;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {
    private static PacienteDAO instancia;
    private List<Paciente> pacientes;
    private int proximoId = 1;

    private PacienteDAO() {
        this.pacientes = new ArrayList<>();
    }

    public static PacienteDAO getInstancia() {
        if (instancia == null) {
            instancia = new PacienteDAO();
        }
        return instancia;
    }

    public void salvar(Paciente paciente) {
        if (paciente.getId() == 0) {
            paciente.setId(proximoId++);
            pacientes.add(paciente);
        } else {
            for (int i = 0; i < pacientes.size(); i++) {
                if (pacientes.get(i).getId() == paciente.getId()) {
                    pacientes.set(i, paciente);
                    break;
                }
            }
        }
    }

    public List<Paciente> listarTodos() {
        return new ArrayList<>(pacientes);
    }

    public void excluir(int id) {
        pacientes.removeIf(p -> p.getId() == id);
    }

    public Paciente buscarPorId(int id) {
        for (Paciente p : pacientes) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}