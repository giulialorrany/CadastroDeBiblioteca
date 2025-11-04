// view/TelaListagem.java
package view;

import dao.ClienteDAO;
import model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListagem extends JFrame {
    private JTable tabela;
    private DefaultTableModel modelo;
    private ClienteDAO clienteDAO = ClienteDAO.getInstancia();

    public TelaListagem() {
        setTitle("Listagem de Clientes");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Modelo da tabela
        String[] colunas = {"ID", "Nome", "CPF", "Idade", "E-mail", "Telefone"};
        modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // não editável diretamente
            }
        };
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        // Botões
        JPanel painelBotoes = new JPanel();
        JButton btnAtualizar = new JButton("Atualizar Lista");
        JButton btnAlterar = new JButton("Alterar");
        JButton btnExcluir = new JButton("Excluir");

        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);
        add(painelBotoes, BorderLayout.SOUTH);

        // Ações
        btnAtualizar.addActionListener(e -> carregarDados());
        btnAlterar.addActionListener(e -> alterarCliente());
        btnExcluir.addActionListener(e -> excluirCliente());

        carregarDados(); // carrega ao abrir
    }

    private void carregarDados() {
        modelo.setRowCount(0); // limpa
        List<Cliente> clientes = clienteDAO.listarTodos();
        for (Cliente c : clientes) {
            modelo.addRow(new Object[]{
                    c.getId(),
                    c.getNome(),
                    c.getCpf(),
                    c.getIdade(),
                    c.getEmail(),
                    c.getTelefone()
            });
        }
    }

    private void alterarCliente() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente!");
            return;
        }

        int id = (int) modelo.getValueAt(linha, 0);
        Cliente cliente = clienteDAO.buscarPorId(id);

        TelaCadastro telaCadastro = new TelaCadastro();
        telaCadastro.setClienteParaEditar(cliente);
        telaCadastro.setVisible(true);

        // Atualiza após fechar
        telaCadastro.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                carregarDados();
            }
        });
    }

    private void excluirCliente() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente!");
            return;
        }

        int id = (int) modelo.getValueAt(linha, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Excluir cliente?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            clienteDAO.excluir(id);
            carregarDados();
            JOptionPane.showMessageDialog(this, "Cliente excluído!");
        }
    }
}