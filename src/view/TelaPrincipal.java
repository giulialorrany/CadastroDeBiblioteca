// view/TelaPrincipal.java
package view;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {
    public TelaPrincipal() {
        setTitle("Sistema de Cadastro de Clientes");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel de botões à esquerda
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(5, 1, 10, 10));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnCadastrar = new JButton("Cadastrar Cliente");
        JButton btnListar = new JButton("Listar Clientes");
        JButton btnSair = new JButton("Sair");

        // Estilizar botões
        Font fonte = new Font("Arial", Font.BOLD, 14);
        btnCadastrar.setFont(fonte);
        btnListar.setFont(fonte);
        btnSair.setFont(fonte);

        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnListar);
        painelBotoes.add(new JLabel()); // espaço
        painelBotoes.add(new JLabel());
        painelBotoes.add(btnSair);

        add(painelBotoes, BorderLayout.WEST);

        // Área central com título
        JLabel titulo = new JLabel("Bem-vindo ao Sistema!", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo, BorderLayout.CENTER);

        // Ações dos botões
        btnCadastrar.addActionListener(e -> {
            new TelaCadastro().setVisible(true);
        });

        btnListar.addActionListener(e -> {
            new TelaListagem().setVisible(true);
        });

        btnSair.addActionListener(e -> System.exit(0));
    }
}