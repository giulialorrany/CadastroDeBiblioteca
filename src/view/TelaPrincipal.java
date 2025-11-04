// view/TelaPrincipal.java
package view;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {
    private final Color LARANJA = new Color(255, 152, 0);
    private final Color AZUL = new Color(33, 150, 243);
    private final Color AZUL_ESCURO = new Color(13, 71, 161); // #0D47A1
    private final Color BRANCO = Color.WHITE;

    public TelaPrincipal() {
        setTitle("Sistema de Biblioteca - Gestão de Leitores");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BRANCO);

        // Painel de botões à esquerda
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(5, 1, 10, 15));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        painelBotoes.setBackground(AZUL);

        JButton btnCadastrar = criarBotao("Cadastrar Leitor", LARANJA, 16);
        JButton btnListar = criarBotao("Listar Leitores", AZUL_ESCURO, 16); // AZUL ESCURO
        JButton btnSair = criarBotao("Sair", new Color(200, 0, 0), 14); // menor

        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnListar);
        painelBotoes.add(new JLabel());
        painelBotoes.add(new JLabel());
        painelBotoes.add(btnSair);

        add(painelBotoes, BorderLayout.WEST);

        // Título central
        JLabel titulo = new JLabel("Bem-vindo à Biblioteca!", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(AZUL);
        add(titulo, BorderLayout.CENTER);

        // Ações
        btnCadastrar.addActionListener(e -> new TelaCadastro().setVisible(true));
        btnListar.addActionListener(e -> new TelaListagem().setVisible(true));
        btnSair.addActionListener(e -> System.exit(0));
    }

    private JButton criarBotao(String texto, Color bg, int fonteTamanho) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, fonteTamanho));
        btn.setForeground(BRANCO);
        btn.setBackground(bg);
        btn.setFocusPainted(false);

        // Ajuste de padding: Sair tem menos espaço interno
        int topBottom = texto.equals("Sair") ? 4 : 6;
        int leftRight = texto.equals("Sair") ? 7 : 10;
        btn.setBorder(BorderFactory.createEmptyBorder(topBottom, leftRight, topBottom, leftRight));

        return btn;
    }
}