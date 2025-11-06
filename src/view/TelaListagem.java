// view/TelaListagem.java
package view;

import dao.PacienteDAO;
import model.Paciente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListagem extends JFrame {
    private JTable tabela;
    private DefaultTableModel modelo;
    private PacienteDAO pacienteDAO = PacienteDAO.getInstancia();

    private final Color LARANJA = new Color(255, 152, 0);
    private final Color AZUL = new Color(33, 150, 243);
    private final Color BRANCO = Color.WHITE;
    private final Color FUNDO = new Color(245, 245, 245);

    public TelaListagem() {
        setTitle("Consultas Marcadas");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(FUNDO);

        String[] colunas = {"ID", "Nome", "CPF", "Idade", "E-mail", "Telefone", "Horário"};
        modelo = new DefaultTableModel(colunas, 0) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        tabela = new JTable(modelo);
        tabela.getTableHeader().setBackground(AZUL);
        tabela.getTableHeader().setForeground(BRANCO);
        tabela.setRowHeight(25);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        painelBotoes.setBackground(FUNDO);

        JButton[] botoes = {
                criarBotao("Atualizar", AZUL),
                criarBotao("Alterar", LARANJA),
                criarBotao("Excluir", new Color(200, 0, 0)),
                criarBotao("Voltar", new Color(100, 100, 100))
        };
        for (JButton b : botoes) painelBotoes.add(b);
        add(painelBotoes, BorderLayout.SOUTH);

        botoes[0].addActionListener(e -> carregarDados());
        botoes[1].addActionListener(e -> alterarPaciente());
        botoes[2].addActionListener(e -> excluirPaciente());
        botoes[3].addActionListener(e -> dispose());

        carregarDados();
    }

    private JButton criarBotao(String texto, Color bg) {
        JButton btn = new JButton(texto);
        btn.setBackground(bg);
        btn.setForeground(BRANCO);
        btn.setFocusPainted(false);
        return btn;
    }

    private void carregarDados() {
        modelo.setRowCount(0);
        List<Paciente> pacientes = pacienteDAO.listarTodos();
        for (Paciente p : pacientes) {
            modelo.addRow(new Object[]{
                    p.getId(), p.getNome(), p.getCpf(), p.getIdade(),
                    p.getEmail(), p.getTelefone(), p.getHorario()
            });
        }
    }

    private void alterarPaciente() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma consulta!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) modelo.getValueAt(linha, 0);
        Paciente paciente = pacienteDAO.buscarPorId(id);
        TelaCadastro tela = new TelaCadastro();
        tela.setPacienteParaEditar(paciente);
        tela.setVisible(true);
        tela.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override public void windowClosed(java.awt.event.WindowEvent e) { carregarDados(); }
        });
    }

    private void excluirPaciente() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma consulta!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) modelo.getValueAt(linha, 0);
        if (JOptionPane.showConfirmDialog(this, "Excluir consulta?", "Confirmação", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            pacienteDAO.excluir(id);
            carregarDados();
            JOptionPane.showMessageDialog(this, "Consulta excluída!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}