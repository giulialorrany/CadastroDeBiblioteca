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

    private final Color LARANJA = new Color(255, 152, 0);
    private final Color AZUL = new Color(33, 150, 243);
    private final Color BRANCO = Color.WHITE;
    private final Color FUNDO = new Color(245, 245, 245);

    public TelaListagem() {
        setTitle("Listagem de Leitores");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(FUNDO);

        // Modelo da tabela
        String[] colunas = {"ID", "Nome", "CPF", "Idade", "E-mail", "Telefone"};
        modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(modelo);
        tabela.getTableHeader().setBackground(AZUL);
        tabela.getTableHeader().setForeground(BRANCO);
        tabela.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setBackground(FUNDO);

        JButton btnAtualizar = new JButton("Atualizar Lista");
        JButton btnAlterar = new JButton("Alterar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnVoltar = new JButton("Voltar");

        btnAtualizar.setBackground(AZUL);
        btnAlterar.setBackground(LARANJA);
        btnExcluir.setBackground(new Color(200, 0, 0));
        btnVoltar.setBackground(new Color(100, 100, 100));

        for (JButton btn : new JButton[]{btnAtualizar, btnAlterar, btnExcluir, btnVoltar}) {
            btn.setForeground(BRANCO);
            btn.setFocusPainted(false);
        }

        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(Box.createHorizontalStrut(20));
        painelBotoes.add(btnVoltar);

        add(painelBotoes, BorderLayout.SOUTH);

        // Ações
        btnAtualizar.addActionListener(e -> carregarDados());
        btnAlterar.addActionListener(e -> alterarCliente());
        btnExcluir.addActionListener(e -> excluirCliente());
        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaPrincipal().setVisible(true);
        });

        carregarDados();
    }

    private void carregarDados() {
        modelo.setRowCount(0);
        List<Cliente> clientes = clienteDAO.listarTodos();
        for (Cliente c : clientes) {
            modelo.addRow(new Object[]{
                    c.getId(), c.getNome(), c.getCpf(), c.getIdade(), c.getEmail(), c.getTelefone()
            });
        }
    }

    private void alterarCliente() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um leitor!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) modelo.getValueAt(linha, 0);
        Cliente cliente = clienteDAO.buscarPorId(id);
        TelaCadastro telaCadastro = new TelaCadastro();
        telaCadastro.setClienteParaEditar(cliente);
        telaCadastro.setVisible(true);
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
            JOptionPane.showMessageDialog(this, "Selecione um leitor!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) modelo.getValueAt(linha, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Excluir leitor?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            clienteDAO.excluir(id);
            carregarDados();
            JOptionPane.showMessageDialog(this, "Leitor excluído!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}