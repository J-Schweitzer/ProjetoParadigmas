package visao;

import Controle.FuncionarioC;
import Modelo.FuncionarioM;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Tela para cadastro, atualização e exclusão de Funcionários.
 * Interface moderna, responsiva e consistente com o projeto.
 * 
 * @author Joao
 */
public class TelaAdmV extends JFrame {

    private FuncionarioC funcionarioC;

    private JTextField txtNome, txtCpf, txtTelefone, txtEmail, txtCargo, txtSalario;
    private JButton btnAdd, btnUpdate, btnDelete;
    private JTable tabelaFuncionarios;
    private DefaultTableModel modeloFuncionarios;

    public TelaAdmV() {
        funcionarioC = new FuncionarioC();

        setTitle("Cadastro de Funcionários - Gerenciador PetShop");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 550);
        setLocationRelativeTo(null);
        initComponents();
        carregarFuncionarios();
    }

    private void initComponents() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(16,16));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        painelPrincipal.setBackground(Color.WHITE);

        // Formulário campos
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(25);
        form.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        txtCpf = new JTextField(25);
        txtCpf.setToolTipText("Digite o CPF do funcionário");
        form.add(txtCpf, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        txtTelefone = new JTextField(25);
        form.add(txtTelefone, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(25);
        form.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Cargo:"), gbc);
        gbc.gridx = 1;
        txtCargo = new JTextField(25);
        txtCargo.setToolTipText("Cargo do funcionário");
        form.add(txtCargo, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Salário:"), gbc);
        gbc.gridx = 1;
        txtSalario = new JTextField(25);
        txtSalario.setToolTipText("Digite o salário (ex: 1200.00)");
        form.add(txtSalario, gbc);

        // Botões
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        buttons.setBackground(Color.WHITE);
        btnAdd = criarBotao("Adicionar");
        btnUpdate = criarBotao("Atualizar");
        btnDelete = criarBotao("Excluir");

        buttons.add(btnAdd);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        form.add(buttons, gbc);

        painelPrincipal.add(form, BorderLayout.NORTH);

        // Tabela Funcionários
        String[] colunas = {"ID", "Nome", "CPF", "Telefone", "Email", "Cargo", "Salário"};
        modeloFuncionarios = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        tabelaFuncionarios = new JTable(modeloFuncionarios);
        tabelaFuncionarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaFuncionarios.getSelectionModel().addListSelectionListener(e -> selecionarFuncionarioTabela());

        JScrollPane scroll = new JScrollPane(tabelaFuncionarios);
        scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(33,150,243)),
                "Funcionários Cadastrados", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 16)));

        painelPrincipal.add(scroll, BorderLayout.CENTER);

        add(painelPrincipal);

        // Eventos botões
        btnAdd.addActionListener(e -> adicionarFuncionario());
        btnUpdate.addActionListener(e -> atualizarFuncionario());
        btnDelete.addActionListener(e -> excluirFuncionario());
    }

    // Cria botão estilizado
    private JButton criarBotao(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(new Color(33,150,243));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            Color bg = btn.getBackground();
            Color hover = bg.brighter();
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(hover); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(bg); }
        });
        return btn;
    }

    // Carrega funcionários na tabela
    private void carregarFuncionarios() {
        modeloFuncionarios.setRowCount(0);
        List<FuncionarioM> funcionarios = funcionarioC.listarFuncionarios();
        for (FuncionarioM f : funcionarios) {
            modeloFuncionarios.addRow(new Object[]{
                    f.getId(), f.getNome(), f.getCpf(), f.getTelefone(), f.getEmail(),
                    f.getCargo(), f.getSalario()
            });
        }
        limparFormulario();
    }

    // Popula formulário quando seleciona na tabela
    private void selecionarFuncionarioTabela() {
        int linha = tabelaFuncionarios.getSelectedRow();
        if (linha >= 0) {
            txtNome.setText(modeloFuncionarios.getValueAt(linha, 1).toString());
            txtCpf.setText(modeloFuncionarios.getValueAt(linha, 2).toString());
            txtTelefone.setText(modeloFuncionarios.getValueAt(linha, 3).toString());
            txtEmail.setText(modeloFuncionarios.getValueAt(linha, 4).toString());
            txtCargo.setText(modeloFuncionarios.getValueAt(linha, 5).toString());
            txtSalario.setText(modeloFuncionarios.getValueAt(linha, 6).toString());
        }
    }

    // Limpa o formulário
    private void limparFormulario() {
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtCargo.setText("");
        txtSalario.setText("");
        tabelaFuncionarios.clearSelection();
    }

    // Validação básica dos campos
    private boolean validarCampos(boolean isUpdate) {
        if (txtNome.getText().trim().isEmpty()) {
            mostrarErro("Nome é obrigatório.");
            return false;
        }
        if (txtCpf.getText().trim().isEmpty()) {
            mostrarErro("CPF é obrigatório.");
            return false;
        }
        if (txtCargo.getText().trim().isEmpty()) {
            mostrarErro("Cargo é obrigatório.");
            return false;
        }
        String salarioStr = txtSalario.getText().trim();
        if (!salarioStr.isEmpty()) {
            try {
                new BigDecimal(salarioStr);
            } catch (NumberFormatException e) {
                mostrarErro("Salário inválido. Use formato numérico válido (ex: 1200.00).");
                return false;
            }
        } else if (!isUpdate) { 
            // For new add, can allow blank salary but warn or default to 0 maybe
        }
        return true;
    }

    // Adicionar funcionário
    private void adicionarFuncionario() {
        if (!validarCampos(false)) return;

        try {
            String nome = txtNome.getText().trim();
            String cpf = txtCpf.getText().trim();
            String telefone = txtTelefone.getText().trim();
            String email = txtEmail.getText().trim();
            String cargo = txtCargo.getText().trim();
            String salarioStr = txtSalario.getText().trim();
            BigDecimal salario = salarioStr.isEmpty() ? BigDecimal.ZERO : new BigDecimal(salarioStr);

            FuncionarioM funcionario = new FuncionarioM(cargo, salario, 0, nome, cpf, telefone, email);
            boolean ok = funcionarioC.inserirFuncionario(funcionario);
            if (ok) {
                mostrarInfo("Funcionário adicionado com sucesso.");
                carregarFuncionarios();
                limparFormulario();
            } else {
                mostrarErro("Erro ao adicionar funcionário.");
            }
        } catch (Exception ex) {
            mostrarErro("Erro: " + ex.getMessage());
        }
    }

    // Atualizar funcionário
    private void atualizarFuncionario() {
        int linha = tabelaFuncionarios.getSelectedRow();
        if (linha < 0) {
            mostrarErro("Selecione um funcionário para atualizar.");
            return;
        }
        if (!validarCampos(true)) return;

        try {
            int id = (int)modeloFuncionarios.getValueAt(linha, 0);
            String nome = txtNome.getText().trim();
            String cpf = txtCpf.getText().trim();
            String telefone = txtTelefone.getText().trim();
            String email = txtEmail.getText().trim();
            String cargo = txtCargo.getText().trim();
            String salarioStr = txtSalario.getText().trim();
            BigDecimal salario = salarioStr.isEmpty() ? BigDecimal.ZERO : new BigDecimal(salarioStr);

            FuncionarioM funcionario = new FuncionarioM(cargo, salario, id, nome, cpf, telefone, email);
            boolean ok = funcionarioC.atualizarFuncionario(funcionario);
            if (ok) {
                mostrarInfo("Funcionário atualizado com sucesso.");
                carregarFuncionarios();
                limparFormulario();
            } else {
                mostrarErro("Erro ao atualizar funcionário.");
            }
        } catch (Exception ex) {
            mostrarErro("Erro: " + ex.getMessage());
        }
    }

    // Excluir funcionário
    private void excluirFuncionario() {
        int linha = tabelaFuncionarios.getSelectedRow();
        if (linha < 0) {
            mostrarErro("Selecione um funcionário para excluir.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Confirma exclusão do funcionário?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            int id = (int)modeloFuncionarios.getValueAt(linha, 0);
            boolean ok = funcionarioC.excluirFuncionario(id);
            if (ok) {
                mostrarInfo("Funcionário excluído com sucesso.");
                carregarFuncionarios();
                limparFormulario();
            } else {
                mostrarErro("Erro ao excluir funcionário.");
            }
        } catch (Exception ex) {
            mostrarErro("Erro: " + ex.getMessage());
        }
    }

    // Mensagens de erro e info consistentes com projeto
    private void mostrarErro(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarInfo(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            TelaAdmV telaFuncionarioV = new TelaAdmV();
            telaFuncionarioV.setVisible(true);
        });
    }
}
