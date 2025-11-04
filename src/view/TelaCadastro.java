// view/TelaCadastro.java
package view;

import dao.ClienteDAO;
import model.Cliente;

import javax.swing.*;
import java.awt.*;

public class TelaCadastro extends JFrame {
    private JTextField txtNome, txtCpf, txtIdade, txtEmail, txtTelefone;
    private JButton btnSalvar, btnLimpar;
    private ClienteDAO clienteDAO = ClienteDAO.getInstancia();
    private Cliente clienteEditar = null;

    public TelaCadastro() {
        setTitle("Cadastro de Cliente");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel de campos
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos
        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        painel.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        txtCpf = new JTextField(20);
        painel.add(txtCpf, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        painel.add(new JLabel("Idade:"), gbc);
        gbc.gridx = 1;
        txtIdade = new JTextField(20);
        painel.add(txtIdade, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        painel.add(new JLabel("E-mail:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        painel.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        painel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        txtTelefone = new JTextField(20);
        painel.add(txtTelefone, gbc);

        // Botões
        JPanel painelBotoes = new JPanel();
        btnSalvar = new JButton("Salvar");
        btnLimpar = new JButton("Limpar");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);

        add(painel, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        // Ações
        btnSalvar.addActionListener(e -> salvar());
        btnLimpar.addActionListener(e -> limparCampos());
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
        JOptionPane.showMessageDialog(this, "Cliente salvo com sucesso!");
        limparCampos();
        dispose();
    }

    private boolean validarCampos() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome é obrigatório!");
            return false;
        }
        if (txtCpf.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "CPF é obrigatório!");
            return false;
        }
        try {
            Integer.parseInt(txtIdade.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Idade deve ser um número!");
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

    // Método para edição (chamado da listagem)
    public void setClienteParaEditar(Cliente cliente) {
        this.clienteEditar = cliente;
        txtNome.setText(cliente.getNome());
        txtCpf.setText(cliente.getCpf());
        txtIdade.setText(String.valueOf(cliente.getIdade()));
        txtEmail.setText(cliente.getEmail());
        txtTelefone.setText(cliente.getTelefone());
    }
}