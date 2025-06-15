package visao;

import Controle.ClienteC;
import Controle.FuncionarioC;
import Modelo.ClienteM;
import Modelo.FuncionarioM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Tela de Login utilizando apenas CPF.
 * Ao identificar CPF válido, abre TelaAdmV para funcionários
 * ou TelaClienteV para clientes.
 * Ainda sem uso de senha.
 * 
 * @author Joao
 */
public class LoginV extends JFrame {

    private JTextField txtCpf;
    private JButton btnEntrar;
    private JButton btnCancelar;
    private JButton btnContratar; // Novo botão "Contratar"

    public LoginV() {
        setTitle("Login - Gerenciador PetShop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 260);
        setLocationRelativeTo(null); // Centraliza na tela
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel painel = new JPanel();
        painel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        painel.setLayout(new GridBagLayout());
        painel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(16, 0, 16, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JLabel lblTitulo = new JLabel("Entre com seu CPF");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(33, 150, 243));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(lblTitulo, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        JLabel lblCpf = new JLabel("CPF: ");
        lblCpf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        painel.add(lblCpf, gbc);

        gbc.gridx = 1;
        txtCpf = new JTextField();
        txtCpf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        painel.add(txtCpf, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(24, 0, 0, 0);

        JPanel panelBotoes = new JPanel();
        panelBotoes.setBackground(Color.WHITE);
        panelBotoes.setLayout(new GridLayout(1, 3, 15, 0)); // Agora 3 botões com espaçamento de 15px

        btnEntrar = new JButton("Entrar");
        btnEntrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEntrar.setBackground(new Color(33, 150, 243));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFocusPainted(false);
        btnEntrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnCancelar = new JButton("Limpar");
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancelar.setBackground(new Color(244, 67, 54));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panelBotoes.add(btnEntrar);
        panelBotoes.add(btnCancelar);

        painel.add(panelBotoes, gbc);

        add(painel);

        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onEntrar();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancelar();
            }
        });
    }

    private void onEntrar() {
        String cpf = txtCpf.getText().trim();

        if (cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira o CPF.", "Erro", JOptionPane.ERROR_MESSAGE);
            txtCpf.requestFocus();
            return;
        }

        FuncionarioC funcionarioC = new FuncionarioC();
        FuncionarioM funcionario = funcionarioC.listarFuncionarios().stream()
                .filter(f -> cpf.equals(f.getCpf()))
                .findFirst()
                .orElse(null);

        if (funcionario != null) {
            // Abrir TelaAdmV
            JOptionPane.showMessageDialog(this, "Login como Funcionário: " + funcionario.getNome(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new TelaFuncionarioV().setVisible(true);
            return;
        }

        ClienteC clienteC = new ClienteC();
        ClienteM cliente = clienteC.listarClientes().stream()
                .filter(c -> cpf.equals(c.getCpf()))
                .findFirst()
                .orElse(null);

        if (cliente != null) {
            // Abrir TelaClienteV
            JOptionPane.showMessageDialog(this, "Login como Cliente: " + cliente.getNome(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new TelaClienteV(cliente).setVisible(true);
            return;
        }

        JOptionPane.showMessageDialog(this, "CPF não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
        txtCpf.requestFocus();
    }

    private void onCancelar() {
        txtCpf.setText("");
        txtCpf.requestFocus();
    }

    // Para execução e teste independentes desta tela
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            LoginV login = new LoginV();
            login.setVisible(true);
        });
    }
}

