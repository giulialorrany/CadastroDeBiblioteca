// dao/ClienteDAO.java
package dao;

import model.Cliente;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    // ÚNICA instância do DAO
    private static ClienteDAO instancia;
    private List<Cliente> clientes;
    private int proximoId = 1;

    // Construtor privado (impede criar com new)
    private ClienteDAO() {
        this.clientes = new ArrayList<>();
    }

    // Método para pegar a única instância
    public static ClienteDAO getInstancia() {
        if (instancia == null) {
            instancia = new ClienteDAO();
        }
        return instancia;
    }

    public void salvar(Cliente cliente) {
        if (cliente.getId() == 0) {
            cliente.setId(proximoId++);
            clientes.add(cliente);
        } else {
            for (int i = 0; i < clientes.size(); i++) {
                if (clientes.get(i).getId() == cliente.getId()) {
                    clientes.set(i, cliente);
                    break;
                }
            }
        }
    }

    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }

    public void excluir(int id) {
        clientes.removeIf(c -> c.getId() == id);
    }

    public Cliente buscarPorId(int id) {
        for (Cliente c : clientes) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }
}