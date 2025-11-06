// view/TelaPrincipal.java
package view;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {
    private final Color LARANJA = new Color(255, 152, 0);
    private final Color AZUL = new Color(33, 150, 243);
    private final Color AZUL_ESCURO = new Color(13, 71, 161);
    private final Color BRANCO = Color.WHITE;

    public TelaPrincipal() {
        setTitle("Sistema de Marcação de Consultas");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BRANCO);

        // Painel esquerdo com botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(6, 1, 10, 12));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        painelBotoes.setBackground(AZUL);

        JButton btnMarcar = criarBotao("Marcar Consulta", LARANJA, 16);
        JButton btnListar = criarBotao("Listar Consultas", AZUL_ESCURO, 16);

        painelBotoes.add(btnMarcar);
        painelBotoes.add(btnListar);
        painelBotoes.add(new JLabel());
        painelBotoes.add(new JLabel());
        painelBotoes.add(new JLabel());

        // BOTÃO SAIR: TEXTO CENTRALIZADO + ÍCONE + TAMANHO MAIOR
        JButton btnSair = new JButton("Sair  ");
        try {
            ImageIcon icon = new ImageIcon("src/resources/power-icon.png");
            Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            btnSair.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            // fallback se não encontrar ícone
        }

        btnSair.setBackground(new Color(220, 20, 60)); // vermelho
        btnSair.setForeground(BRANCO);
        btnSair.setFont(new Font("Arial", Font.BOLD, 15)); // TEXTO MAIOR
        btnSair.setFocusPainted(false);
        btnSair.setPreferredSize(new Dimension(80, 40)); // BOTÃO MAIOR
        btnSair.setHorizontalAlignment(SwingConstants.CENTER); // TEXTO CENTRALIZADO
        btnSair.setIconTextGap(8);
        btnSair.setToolTipText("Sair do sistema");
        btnSair.addActionListener(e -> System.exit(0));

        JPanel painelSair = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelSair.setBackground(AZUL);
        painelSair.add(btnSair);
        painelBotoes.add(painelSair);

        add(painelBotoes, BorderLayout.WEST);

        // Título central
        JLabel titulo = new JLabel("Clínica Saúde & Vida", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(AZUL);
        add(titulo, BorderLayout.CENTER);

        // Ações
        btnMarcar.addActionListener(e -> new TelaCadastro().setVisible(true));
        btnListar.addActionListener(e -> new TelaListagem().setVisible(true));
    }

    private JButton criarBotao(String texto, Color bg, int tamanho) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, tamanho));
        btn.setForeground(BRANCO);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        return btn;
    }
}