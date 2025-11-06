// view/TelaCadastro.java
package view;

import dao.PacienteDAO;
import model.Paciente;
import javax.swing.*;
import java.awt.*;

public class TelaCadastro extends JFrame {
    private JTextField txtNome, txtCpf, txtIdade, txtEmail, txtTelefone, txtHorario;
    private JButton btnSalvar, btnLimpar, btnVoltar;
    private PacienteDAO pacienteDAO = PacienteDAO.getInstancia();
    private Paciente pacienteEditar = null;

    private final Color LARANJA = new Color(255, 152, 0);
    private final Color AZUL = new Color(33, 150, 243);
    private final Color BRANCO = Color.WHITE;
    private final Color FUNDO = new Color(245, 245, 245);

    public TelaCadastro() {
        setTitle("Marcar Consulta");
        setSize(520, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(FUNDO);

        // === INICIALIZAÇÃO CORRETA DOS CAMPOS ===
        txtNome = new JTextField(20);
        txtCpf = new JTextField(20);
        txtIdade = new JTextField(20);
        txtEmail = new JTextField(20);
        txtTelefone = new JTextField(20);
        txtHorario = new JTextField(20);

        // === PAINEL DE CAMPOS ===
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(FUNDO);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] rotulos = {"Nome:", "CPF:", "Idade:", "E-mail:", "Telefone:", "Horário (ex: 14:30):"};
        JTextField[] campos = {txtNome, txtCpf, txtIdade, txtEmail, txtTelefone, txtHorario};

        for (int i = 0; i < rotulos.length; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            JLabel label = new JLabel(rotulos[i]);
            label.setForeground(AZUL);
            label.setFont(new Font("Arial", Font.BOLD, 12));
            painel.add(label, gbc);

            gbc.gridx = 1;
            campos[i].setFont(new Font("Arial", Font.PLAIN, 12));
            painel.add(campos[i], gbc);
        }

        // === PAINEL DE BOTÕES ===
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        painelBotoes.setBackground(FUNDO);

        btnSalvar = criarBotao("Salvar", LARANJA);
        btnLimpar = criarBotao("Limpar", AZUL);
        btnVoltar = criarBotao("Voltar", new Color(100, 100, 100));

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnVoltar);

        // === MONTAGEM DA JANELA ===
        add(painel, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        // === AÇÕES ===
        btnSalvar.addActionListener(e -> salvar());
        btnLimpar.addActionListener(e -> limparCampos());
        btnVoltar.addActionListener(e -> dispose());
    }

    private JButton criarBotao(String texto, Color bg) {
        JButton btn = new JButton(texto);
        btn.setBackground(bg);
        btn.setForeground(BRANCO);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        return btn;
    }

    private void salvar() {
        if (!validarCampos()) return;

        if (pacienteEditar == null) {
            pacienteEditar = new Paciente();
        }

        pacienteEditar.setNome(txtNome.getText().trim());
        pacienteEditar.setCpf(txtCpf.getText().trim());
        pacienteEditar.setIdade(Integer.parseInt(txtIdade.getText().trim()));
        pacienteEditar.setEmail(txtEmail.getText().trim());
        pacienteEditar.setTelefone(txtTelefone.getText().trim());
        pacienteEditar.setHorario(txtHorario.getText().trim());

        pacienteDAO.salvar(pacienteEditar);
        JOptionPane.showMessageDialog(this, "Consulta marcada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        limparCampos();
        dispose();
    }

    private boolean validarCampos() {
        if (txtNome.getText().trim().isEmpty()) {
            mostrarErro("Nome é obrigatório!");
            return false;
        }
        if (txtCpf.getText().trim().isEmpty()) {
            mostrarErro("CPF é obrigatório!");
            return false;
        }
        if (txtIdade.getText().trim().isEmpty()) {
            mostrarErro("Idade é obrigatória!");
            return false;
        }
        if (txtHorario.getText().trim().isEmpty()) {
            mostrarErro("Horário é obrigatório!");
            return false;
        }

        try {
            Integer.parseInt(txtIdade.getText().trim());
        } catch (NumberFormatException e) {
            mostrarErro("Idade deve ser um número!");
            return false;
        }

        if (!txtHorario.getText().trim().matches("\\d{2}:\\d{2}")) {
            mostrarErro("Horário inválido! Use o formato HH:MM (ex: 14:30)");
            return false;
        }

        return true;
    }

    private void mostrarErro(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erro de Validação", JOptionPane.ERROR_MESSAGE);
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtIdade.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtHorario.setText("");
        pacienteEditar = null;
    }

    public void setPacienteParaEditar(Paciente paciente) {
        this.pacienteEditar = paciente;
        txtNome.setText(paciente.getNome());
        txtCpf.setText(paciente.getCpf());
        txtIdade.setText(String.valueOf(paciente.getIdade()));
        txtEmail.setText(paciente.getEmail());
        txtTelefone.setText(paciente.getTelefone());
        txtHorario.setText(paciente.getHorario());
    }
}