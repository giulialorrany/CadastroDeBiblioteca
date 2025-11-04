// view/TelaCadastro.java
package view;

import dao.ClienteDAO;
import model.Cliente;
import javax.swing.*;
import java.awt.*;

public class TelaCadastro extends JFrame {
    private JTextField txtNome, txtCpf, txtIdade, txtEmail, txtTelefone;
    private JButton btnSalvar, btnLimpar, btnVoltar;
    private ClienteDAO clienteDAO = ClienteDAO.getInstancia();
    private Cliente clienteEditar = null;

    // Cores do tema
    private final Color LARANJA = new Color(255, 152, 0);
    private final Color AZUL = new Color(33, 150, 243);
    private final Color BRANCO = Color.WHITE;
    private final Color FUNDO = new Color(245, 245, 245);

    public TelaCadastro() {
        setTitle("Cadastro de Leitor");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(FUNDO);

        // Painel de campos
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(FUNDO);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setForeground(AZUL);
        painel.add(lblNome, gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        painel.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblCpf = new JLabel("CPF:");
        lblCpf.setForeground(AZUL);
        painel.add(lblCpf, gbc);
        gbc.gridx = 1;
        txtCpf = new JTextField(20);
        painel.add(txtCpf, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblIdade = new JLabel("Idade:");
        lblIdade.setForeground(AZUL);
        painel.add(lblIdade, gbc);
        gbc.gridx = 1;
        txtIdade = new JTextField(20);
        painel.add(txtIdade, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblEmail = new JLabel("E-mail:");
        lblEmail.setForeground(AZUL);
        painel.add(lblEmail, gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        painel.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        JLabel lblTelefone = new JLabel("Telefone:");
        lblTelefone.setForeground(AZUL);
        painel.add(lblTelefone, gbc);
        gbc.gridx = 1;
        txtTelefone = new JTextField(20);
        painel.add(txtTelefone, gbc);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.setBackground(FUNDO);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBackground(LARANJA);
        btnSalvar.setForeground(BRANCO);
        btnSalvar.setFocusPainted(false);

        btnLimpar = new JButton("Limpar");
        btnLimpar.setBackground(AZUL);
        btnLimpar.setForeground(BRANCO);
        btnLimpar.setFocusPainted(false);

        btnVoltar = new JButton("Voltar");
        btnVoltar.setBackground(new Color(100, 100, 100));
        btnVoltar.setForeground(BRANCO);
        btnVoltar.setFocusPainted(false);

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnVoltar);

        add(painel, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        // Ações
        btnSalvar.addActionListener(e -> salvar());
        btnLimpar.addActionListener(e -> limparCampos());
        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaPrincipal().setVisible(true);
        });
    }

    private void salvar() {
        if (!validarCampos()) return;
        if (clienteEditar == null) {
            clienteEditar = new Cliente();
        }
        clienteEditar.setNome(txtNome.getText());
        clienteEditar.setCpf(txtCpf.getText());
        clienteEditar.setIdade(Integer.parseInt(txtIdade.getText()));
        clienteEditar.setEmail(txtEmail.getText());
        clienteEditar.setTelefone(txtTelefone.getText());
        clienteDAO.salvar(clienteEditar);
        JOptionPane.showMessageDialog(this, "Leitor salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        limparCampos();
        dispose(); // FECHA apenas a tela de cadastro
    }

    private boolean validarCampos() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtCpf.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "CPF é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Integer.parseInt(txtIdade.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Idade deve ser um número!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtIdade.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        clienteEditar = null;
    }

    public void setClienteParaEditar(Cliente cliente) {
        this.clienteEditar = cliente;
        txtNome.setText(cliente.getNome());
        txtCpf.setText(cliente.getCpf());
        txtIdade.setText(String.valueOf(cliente.getIdade()));
        txtEmail.setText(cliente.getEmail());
        txtTelefone.setText(cliente.getTelefone());
    }
}