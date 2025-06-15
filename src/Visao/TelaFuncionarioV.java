package visao;

import Controle.AtendimentoC;
import Controle.ClienteC;
import Controle.PetC;
import Controle.ServicoC;
import Controle.FuncionarioC;

import Modelo.AtendimentoM;
import Modelo.ClienteM;
import Modelo.PetM;
import Modelo.ServicoM;
import Modelo.FuncionarioM;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TelaFuncionarioV extends JFrame {

    private ClienteC clienteC;
    private PetC petC;
    private ServicoC servicoC;
    private AtendimentoC atendimentoC;
    private FuncionarioC funcionarioC;

    // Componentes Relatórios
    private JTable tabelaClientesRel;
    private JTable tabelaPetsRel;

    // Componentes Cadastro Clientes e Pets
    private JTextField txtClienteNome, txtClienteCpf, txtClienteTel, txtClienteEmail, txtClienteEnd;
    private JButton btnAddCliente, btnUpdateCliente, btnDeleteCliente;
    private JTable tabelaClientesCad;

    private JTextField txtPetNome, txtPetEspecie, txtPetRaca, txtPetPorte;
    private JFormattedTextField txtPetNascimento;
    private JButton btnAddPet, btnUpdatePet, btnDeletePet;
    private JTable tabelaPetsCad;
    private JComboBox<ClienteM> comboClientesPet;

    // Componentes Cadastro Serviços
    private JTextField txtServicoTipo, txtServicoDescricao;
    private JFormattedTextField txtServicoValor;
    private JButton btnAddServico, btnUpdateServico, btnDeleteServico;
    private JTable tabelaServicos;

    // Componentes Cadastro Atendimento
    private JComboBox<PetM> comboPetAtend;
    private JComboBox<FuncionarioM> comboFuncAtend;
    private JComboBox<ServicoM> comboServAtend;
    private JTextArea txtObservacoesAtend;
    private JFormattedTextField txtDataHoraAtend;
    private JTextField txtValorFinalAtend;
    private JButton btnAddAtend, btnUpdateAtend, btnDeleteAtend;
    private JTable tabelaAtendimentos;

    private DefaultTableModel modeloClientesRel;
    private DefaultTableModel modeloPetsRel;
    private DefaultTableModel modeloClientesCad;
    private DefaultTableModel modeloPetsCad;
    private DefaultTableModel modeloServicos;
    private DefaultTableModel modeloAtendimentos;

    // Formatter for date/time
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public TelaFuncionarioV() {
        clienteC = new ClienteC();
        petC = new PetC();
        servicoC = new ServicoC();
        atendimentoC = new AtendimentoC();
        funcionarioC = new FuncionarioC();

        setTitle("Tela Administrativa - Gerenciador PetShop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 720);
        setLocationRelativeTo(null);
        initComponents();
        carregarDadosRelatorios();
        carregarDadosCadastroClientesPets();
        carregarDadosServicos();
        carregarDadosAtendimentos();
    }

    private void initComponents() {
        JTabbedPane tabbedPane = new JTabbedPane();

        // Aba 1: Relatórios
        JPanel painelRelatorios = criarPainelRelatorios();
        tabbedPane.addTab("Relatórios Clientes e Pets", painelRelatorios);

        // Aba 2: Cadastro Clientes e Pets
        JPanel painelCadastroClientesPets = criarPainelCadastroClientesPets();
        tabbedPane.addTab("Cadastro de Clientes e Pets", painelCadastroClientesPets);

        // Aba 3: Cadastro Serviços
        JPanel painelCadastroServicos = criarPainelCadastroServicos();
        tabbedPane.addTab("Cadastro de Serviços", painelCadastroServicos);

        // Aba 4: Cadastro Atendimentos
        JPanel painelCadastroAtendimentos = criarPainelCadastroAtendimentos();
        tabbedPane.addTab("Cadastro de Atendimentos", painelCadastroAtendimentos);

        add(tabbedPane);
    }

    // --- Aba Relatórios ---
    private JPanel criarPainelRelatorios() {
        JPanel painel = new JPanel(new BorderLayout(16, 16));
        painel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        painel.setBackground(Color.WHITE);

        // Tabela Clientes
        String[] colClientes = {"ID", "Nome", "CPF", "Telefone", "Email", "Endereço"};
        modeloClientesRel = new DefaultTableModel(colClientes, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaClientesRel = new JTable(modeloClientesRel);
        JScrollPane spClientes = new JScrollPane(tabelaClientesRel);
        spClientes.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(33,150,243)),
                "Clientes", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 16)));

        // Tabela Pets
        String[] colPets = {"ID", "Nome", "Espécie", "Raça", "Data Nascimento", "Porte", "ID Cliente"};
        modeloPetsRel = new DefaultTableModel(colPets,0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaPetsRel = new JTable(modeloPetsRel);
        JScrollPane spPets = new JScrollPane(tabelaPetsRel);
        spPets.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(33,150,243)),
                "Pets", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 16)));

        // Layout lado a lado
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, spClientes, spPets);
        split.setResizeWeight(0.5);
        painel.add(split, BorderLayout.CENTER);

        return painel;
    }

    private void carregarDadosRelatorios() {
        // Clientes
        modeloClientesRel.setRowCount(0);
        List<ClienteM> clientes = clienteC.listarClientes();
        for (ClienteM c : clientes) {
            modeloClientesRel.addRow(new Object[]{
                    c.getId(), c.getNome(), c.getCpf(), c.getTelefone(), c.getEmail(), c.getEndereco()
            });
        }
        // Pets
        modeloPetsRel.setRowCount(0);
        List<PetM> pets = petC.listarPets();
        for (PetM p : pets) {
            modeloPetsRel.addRow(new Object[]{
                    p.getIdPet(), p.getNome(), p.getEspecie(), p.getRaca(), p.getDataNascimento(), p.getPorte(), p.getIdCliente()
            });
        }
    }

    // --- Aba Cadastro Clientes e Pets ---
    private JPanel criarPainelCadastroClientesPets() {
        JPanel painel = new JPanel(new BorderLayout(16,16));
        painel.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        painel.setBackground(Color.WHITE);

        JTabbedPane subTabs = new JTabbedPane();

        // Sub-aba Clientes
        subTabs.addTab("Clientes", criarPainelCadastroClientes());

        // Sub-aba Pets
        subTabs.addTab("Pets", criarPainelCadastroPets());

        painel.add(subTabs, BorderLayout.CENTER);

        return painel;
    }

    // Cadastro clientes
    private JPanel criarPainelCadastroClientes() {
        JPanel painel = new JPanel(new BorderLayout(12,12));
        painel.setBackground(Color.WHITE);

        // Formulário
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;

        form.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtClienteNome = new JTextField(20);
        form.add(txtClienteNome, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        txtClienteCpf = new JTextField(20);
        form.add(txtClienteCpf, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        txtClienteTel = new JTextField(20);
        form.add(txtClienteTel, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtClienteEmail = new JTextField(20);
        form.add(txtClienteEmail, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Endereço:"), gbc);
        gbc.gridx = 1;
        txtClienteEnd = new JTextField(20);
        form.add(txtClienteEnd, gbc);

        // Botões
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        buttons.setBackground(Color.WHITE);

        btnAddCliente = criarBotao("Adicionar");
        btnUpdateCliente = criarBotao("Atualizar");
        btnDeleteCliente = criarBotao("Excluir");

        buttons.add(btnAddCliente);
        buttons.add(btnUpdateCliente);
        buttons.add(btnDeleteCliente);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        form.add(buttons, gbc);

        // Tabela clientes
        String[] colClientes = {"ID", "Nome", "CPF", "Telefone", "Email", "Endereço"};
        modeloClientesCad = new DefaultTableModel(colClientes, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaClientesCad = new JTable(modeloClientesCad);
        tabelaClientesCad.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabelaClientesCad);

        // Seleção tabela para preencher formulário
        tabelaClientesCad.getSelectionModel().addListSelectionListener(e -> selecionarClienteTabela());

        painel.add(form, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);

        // Eventos dos botões
        btnAddCliente.addActionListener(e -> adicionarCliente());
        btnUpdateCliente.addActionListener(e -> atualizarCliente());
        btnDeleteCliente.addActionListener(e -> excluirCliente());

        return painel;
    }

    // Cadastro pets
    private JPanel criarPainelCadastroPets() {
        JPanel painel = new JPanel(new BorderLayout(12,12));
        painel.setBackground(Color.WHITE);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;

        form.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtPetNome = new JTextField(20);
        form.add(txtPetNome, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Espécie:"), gbc);
        gbc.gridx = 1;
        txtPetEspecie = new JTextField(20);
        form.add(txtPetEspecie, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Raça:"), gbc);
        gbc.gridx = 1;
        txtPetRaca = new JTextField(20);
        form.add(txtPetRaca, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Data Nascimento (yyyy-MM-dd):"), gbc);
        gbc.gridx = 1;
        txtPetNascimento = new JFormattedTextField();
        txtPetNascimento.setColumns(20);
        txtPetNascimento.setToolTipText("Format: yyyy-MM-dd");
        form.add(txtPetNascimento, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Porte:"), gbc);
        gbc.gridx = 1;
        txtPetPorte = new JTextField(20);
        form.add(txtPetPorte, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1;
        comboClientesPet = new JComboBox<>();
        form.add(comboClientesPet, gbc);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        buttons.setBackground(Color.WHITE);
        btnAddPet = criarBotao("Adicionar");
        btnUpdatePet = criarBotao("Atualizar");
        btnDeletePet = criarBotao("Excluir");

        buttons.add(btnAddPet);
        buttons.add(btnUpdatePet);
        buttons.add(btnDeletePet);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        form.add(buttons, gbc);

        String[] colPets = {"ID", "Nome", "Espécie", "Raça", "Nascimento", "Porte", "Cliente"};
        modeloPetsCad = new DefaultTableModel(colPets, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaPetsCad = new JTable(modeloPetsCad);
        tabelaPetsCad.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabelaPetsCad);

        tabelaPetsCad.getSelectionModel().addListSelectionListener(e -> selecionarPetTabela());

        painel.add(form, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);

        btnAddPet.addActionListener(e -> adicionarPet());
        btnUpdatePet.addActionListener(e -> atualizarPet());
        btnDeletePet.addActionListener(e -> excluirPet());

        return painel;
    }

    // --- Aba Cadastro Serviços ---
    private JPanel criarPainelCadastroServicos() {
        JPanel painel = new JPanel(new BorderLayout(12,12));
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;

        form.add(new JLabel("Tipo Serviço:"), gbc);
        gbc.gridx = 1;
        txtServicoTipo = new JTextField(25);
        form.add(txtServicoTipo, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1;
        txtServicoDescricao = new JTextField(25);
        form.add(txtServicoDescricao, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Valor Base:"), gbc);
        gbc.gridx = 1;
        txtServicoValor = new JFormattedTextField();
        txtServicoValor.setColumns(25);
        txtServicoValor.setToolTipText("Valor monetário decimal");
        form.add(txtServicoValor, gbc);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        buttons.setBackground(Color.WHITE);
        btnAddServico = criarBotao("Adicionar");
        btnUpdateServico = criarBotao("Atualizar");
        btnDeleteServico = criarBotao("Excluir");

        buttons.add(btnAddServico);
        buttons.add(btnUpdateServico);
        buttons.add(btnDeleteServico);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        form.add(buttons, gbc);

        String[] colServicos = {"ID Serviço","Tipo Serviço","Descrição","Valor Base"};
        modeloServicos = new DefaultTableModel(colServicos, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaServicos = new JTable(modeloServicos);
        tabelaServicos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabelaServicos);
        scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(33,150,243)),
                "Serviços Cadastrados", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 16)));

        tabelaServicos.getSelectionModel().addListSelectionListener(e -> selecionarServicoTabela());

        painel.add(form, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);

        btnAddServico.addActionListener(e -> adicionarServico());
        btnUpdateServico.addActionListener(e -> atualizarServico());
        btnDeleteServico.addActionListener(e -> excluirServico());

        return painel;
    }

    // --- Aba Cadastro Atendimentos ---
    private JPanel criarPainelCadastroAtendimentos() {
        JPanel painel = new JPanel(new BorderLayout(12,12));
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;

        form.add(new JLabel("Pet:"), gbc);
        gbc.gridx = 1;
        comboPetAtend = new JComboBox<>();
        form.add(comboPetAtend, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Funcionário:"), gbc);
        gbc.gridx = 1;
        comboFuncAtend = new JComboBox<>();
        form.add(comboFuncAtend, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Serviço:"), gbc);
        gbc.gridx = 1;
        comboServAtend = new JComboBox<>();
        form.add(comboServAtend, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Data e Hora (yyyy-MM-dd HH:mm):"), gbc);
        gbc.gridx = 1;
        txtDataHoraAtend = new JFormattedTextField();
        txtDataHoraAtend.setColumns(25);
        txtDataHoraAtend.setToolTipText("Formato: yyyy-MM-dd HH:mm");
        // Set initial text to current PC date/time automatically
        txtDataHoraAtend.setText(LocalDateTime.now().format(dateTimeFormatter));
        form.add(txtDataHoraAtend, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Observações:"), gbc);
        gbc.gridx = 1;
        txtObservacoesAtend = new JTextArea(3, 25);
        txtObservacoesAtend.setLineWrap(true);
        txtObservacoesAtend.setWrapStyleWord(true);
        JScrollPane spObs = new JScrollPane(txtObservacoesAtend);
        form.add(spObs, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Valor Final:"), gbc);
        gbc.gridx = 1;
        txtValorFinalAtend = new JTextField(25);
        form.add(txtValorFinalAtend, gbc);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        buttons.setBackground(Color.WHITE);
        btnAddAtend = criarBotao("Adicionar");
        btnUpdateAtend = criarBotao("Atualizar");
        btnDeleteAtend = criarBotao("Excluir");

        buttons.add(btnAddAtend);
        buttons.add(btnUpdateAtend);
        buttons.add(btnDeleteAtend);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        form.add(buttons, gbc);

        String[] colAtendimentos = {"ID", "Data/Hora", "Pet", "Funcionário", "Serviço", "Valor Final", "Observações"};
        modeloAtendimentos = new DefaultTableModel(colAtendimentos, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaAtendimentos = new JTable(modeloAtendimentos);
        tabelaAtendimentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabelaAtendimentos);
        scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(33,150,243)),
                "Atendimentos Cadastrados", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 16)));

        tabelaAtendimentos.getSelectionModel().addListSelectionListener(e -> selecionarAtendimentoTabela());

        painel.add(form, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);

        btnAddAtend.addActionListener(e -> adicionarAtendimento());
        btnUpdateAtend.addActionListener(e -> atualizarAtendimento());
        btnDeleteAtend.addActionListener(e -> excluirAtendimento());

        return painel;
    }

    // --- Métodos utilitários de botão ---
    private JButton criarBotao(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(new Color(33,150,243));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            Color original = btn.getBackground();
            Color hover = original.brighter();
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(hover); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(original); }
        });
        return btn;
    }

    // --- Seleção e carregamento das tabelas para edição ---
    private void selecionarClienteTabela() {
        int linha = tabelaClientesCad.getSelectedRow();
        if (linha >= 0) {
            txtClienteNome.setText(modeloClientesCad.getValueAt(linha,1).toString());
            txtClienteCpf.setText(modeloClientesCad.getValueAt(linha,2).toString());
            txtClienteTel.setText(modeloClientesCad.getValueAt(linha,3).toString());
            txtClienteEmail.setText(modeloClientesCad.getValueAt(linha,4).toString());
            txtClienteEnd.setText(modeloClientesCad.getValueAt(linha,5).toString());

            carregarClientesComboPet(); // Atualizar combo pets
        }
    }

    private void selecionarPetTabela() {
        int linha = tabelaPetsCad.getSelectedRow();
        if (linha >= 0) {
            txtPetNome.setText(modeloPetsCad.getValueAt(linha,1).toString());
            txtPetEspecie.setText(modeloPetsCad.getValueAt(linha,2).toString());
            txtPetRaca.setText(modeloPetsCad.getValueAt(linha,3).toString());
            txtPetNascimento.setText(modeloPetsCad.getValueAt(linha,4).toString());
            txtPetPorte.setText(modeloPetsCad.getValueAt(linha,5).toString());

            // Selecionar cliente no combo box, buscar pelo id
            int idCliente = Integer.parseInt(modeloPetsCad.getValueAt(linha,6).toString());
            for (int i=0; i<comboClientesPet.getItemCount(); i++) {
                if (comboClientesPet.getItemAt(i).getId() == idCliente) {
                    comboClientesPet.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void selecionarServicoTabela() {
        int linha = tabelaServicos.getSelectedRow();
        if (linha >= 0) {
            txtServicoTipo.setText(modeloServicos.getValueAt(linha,1).toString());
            txtServicoDescricao.setText(modeloServicos.getValueAt(linha,2).toString());
            txtServicoValor.setText(modeloServicos.getValueAt(linha,3).toString());
        }
    }

    private void selecionarAtendimentoTabela() {
        int linha = tabelaAtendimentos.getSelectedRow();
        if (linha >= 0) {
            String dataHoraStr = modeloAtendimentos.getValueAt(linha,1).toString();
            txtDataHoraAtend.setText(dataHoraStr);

            String nomePet = modeloAtendimentos.getValueAt(linha,2).toString();
            selecionarComboPorNome(comboPetAtend, nomePet);

            String nomeFunc = modeloAtendimentos.getValueAt(linha,3).toString();
            selecionarComboPorNome(comboFuncAtend, nomeFunc);

            String nomeServico = modeloAtendimentos.getValueAt(linha,4).toString();
            selecionarComboPorNome(comboServAtend, nomeServico);

            txtValorFinalAtend.setText(modeloAtendimentos.getValueAt(linha,5).toString());
            txtObservacoesAtend.setText(modeloAtendimentos.getValueAt(linha,6).toString());
        }
    }

    // Seleciona comboBox item pelo nome (toString)
    private <T> void selecionarComboPorNome(JComboBox<T> combo, String nome) {
        for (int i=0; i<combo.getItemCount(); i++) {
            if (combo.getItemAt(i).toString().equals(nome)) {
                combo.setSelectedIndex(i);
                return;
            }
        }
        combo.setSelectedIndex(-1);
    }

    // --- Métodos CRUD Clientes ---
    private void adicionarCliente() {
        try {
            String nome = txtClienteNome.getText().trim();
            String cpf = txtClienteCpf.getText().trim();
            String tel = txtClienteTel.getText().trim();
            String email = txtClienteEmail.getText().trim();
            String end = txtClienteEnd.getText().trim();

            if (nome.isEmpty() || cpf.isEmpty()) {
                mostrarErro("Nome e CPF são obrigatórios.");
                return;
            }

            ClienteM cliente = new ClienteM(end, null, 0, nome, cpf, tel, email);
            boolean ok = clienteC.inserirCliente(cliente);
            if (ok) {
                mostrarInfo("Cliente adicionado com sucesso.");
                carregarDadosCadastroClientesPets();
                limparFormularioCliente();
            } else {
                mostrarErro("Erro ao adicionar cliente.");
            }
        } catch (Exception ex) {
            mostrarErro("Erro: "+ex.getMessage());
        }
    }

    private void atualizarCliente() {
        int linha = tabelaClientesCad.getSelectedRow();
        if (linha < 0) {
            mostrarErro("Selecione um cliente para atualizar.");
            return;
        }
        try {
            int id = (int)modeloClientesCad.getValueAt(linha,0);
            String nome = txtClienteNome.getText().trim();
            String cpf = txtClienteCpf.getText().trim();
            String tel = txtClienteTel.getText().trim();
            String email = txtClienteEmail.getText().trim();
            String end = txtClienteEnd.getText().trim();

            if (nome.isEmpty() || cpf.isEmpty()) {
                mostrarErro("Nome e CPF são obrigatórios.");
                return;
            }

            ClienteM cliente = new ClienteM(end, null, id, nome, cpf, tel, email);
            boolean ok = clienteC.atualizarCliente(cliente);
            if (ok) {
                mostrarInfo("Cliente atualizado com sucesso.");
                carregarDadosCadastroClientesPets();
                limparFormularioCliente();
            } else {
                mostrarErro("Erro ao atualizar cliente.");
            }
        } catch (Exception ex) {
            mostrarErro("Erro: "+ex.getMessage());
        }
    }

    private void excluirCliente() {
        int linha = tabelaClientesCad.getSelectedRow();
        if (linha < 0) {
            mostrarErro("Selecione um cliente para excluir.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Confirma exclusão do cliente?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            int id = (int)modeloClientesCad.getValueAt(linha,0);
            boolean ok = clienteC.excluirCliente(id);
            if (ok) {
                mostrarInfo("Cliente excluído com sucesso.");
                carregarDadosCadastroClientesPets();
                limparFormularioCliente();
            } else {
                mostrarErro("Erro ao excluir cliente. Verifique se há pets vinculados.");
            }
        } catch (Exception ex) {
            mostrarErro("Erro: "+ex.getMessage());
        }
    }

    private void limparFormularioCliente() {
        txtClienteNome.setText("");
        txtClienteCpf.setText("");
        txtClienteTel.setText("");
        txtClienteEmail.setText("");
        txtClienteEnd.setText("");
        tabelaClientesCad.clearSelection();
    }

    // --- Métodos CRUD Pets ---
    private void adicionarPet() {
        try {
            String nome = txtPetNome.getText().trim();
            String especie = txtPetEspecie.getText().trim();
            String raca = txtPetRaca.getText().trim();
            String nascimentoStr = txtPetNascimento.getText().trim();
            String porte = txtPetPorte.getText().trim();
            ClienteM clienteSelecionado = (ClienteM) comboClientesPet.getSelectedItem();

            if (nome.isEmpty() || clienteSelecionado == null) {
                mostrarErro("Nome e Cliente são obrigatórios.");
                return;
            }

            java.time.LocalDate nascimento = null;
            if (!nascimentoStr.isEmpty()) {
                nascimento = java.time.LocalDate.parse(nascimentoStr);
            }

            PetM pet = new PetM(0, nome, especie, raca, nascimento, porte, clienteSelecionado.getId(), clienteSelecionado);
            boolean ok = petC.inserirPet(pet);
            if (ok) {
                mostrarInfo("Pet adicionado com sucesso.");
                carregarDadosCadastroClientesPets();
                limparFormularioPet();
            } else {
                mostrarErro("Erro ao adicionar pet.");
            }
        } catch (Exception ex) {
            mostrarErro("Erro: "+ex.getMessage());
        }
    }

    private void atualizarPet() {
        int linha = tabelaPetsCad.getSelectedRow();
        if (linha < 0) {
            mostrarErro("Selecione um pet para atualizar.");
            return;
        }
        try {
            int id = (int)modeloPetsCad.getValueAt(linha,0);
            String nome = txtPetNome.getText().trim();
            String especie = txtPetEspecie.getText().trim();
            String raca = txtPetRaca.getText().trim();
            String nascimentoStr = txtPetNascimento.getText().trim();
            String porte = txtPetPorte.getText().trim();
            ClienteM clienteSelecionado = (ClienteM) comboClientesPet.getSelectedItem();

            if (nome.isEmpty() || clienteSelecionado == null) {
                mostrarErro("Nome e Cliente são obrigatórios.");
                return;
            }

            java.time.LocalDate nascimento = null;
            if (!nascimentoStr.isEmpty()) {
                nascimento = java.time.LocalDate.parse(nascimentoStr);
            }

            PetM pet = new PetM(id, nome, especie, raca, nascimento, porte, clienteSelecionado.getId(), clienteSelecionado);
            boolean ok = petC.atualizarPet(pet);
            if (ok) {
                mostrarInfo("Pet atualizado com sucesso.");
                carregarDadosCadastroClientesPets();
                limparFormularioPet();
            } else {
                mostrarErro("Erro ao atualizar pet.");
            }
        } catch (Exception ex) {
            mostrarErro("Erro: "+ex.getMessage());
        }
    }

    private void excluirPet() {
        int linha = tabelaPetsCad.getSelectedRow();
        if (linha < 0) {
            mostrarErro("Selecione um pet para excluir.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Confirma exclusão do pet?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            int id = (int)modeloPetsCad.getValueAt(linha,0);
            boolean ok = petC.excluirPet(id);
            if (ok) {
                mostrarInfo("Pet excluído com sucesso.");
                carregarDadosCadastroClientesPets();
                limparFormularioPet();
            } else {
                mostrarErro("Erro ao excluir pet.");
            }
        } catch (Exception ex) {
            mostrarErro("Erro: "+ex.getMessage());
        }
    }

    private void limparFormularioPet() {
        txtPetNome.setText("");
        txtPetEspecie.setText("");
        txtPetRaca.setText("");
        txtPetNascimento.setText("");
        txtPetPorte.setText("");
        comboClientesPet.setSelectedIndex(-1);
        tabelaPetsCad.clearSelection();
    }

    private void carregarClientesComboPet() {
        comboClientesPet.removeAllItems();
        List<ClienteM> clientes = clienteC.listarClientes();
        for (ClienteM c : clientes) {
            comboClientesPet.addItem(c);
        }
        comboClientesPet.setSelectedIndex(-1);
    }

    // --- Métodos CRUD Serviços ---
    private void adicionarServico() {
        try {
            String tipo = txtServicoTipo.getText().trim();
            String descricao = txtServicoDescricao.getText().trim();
            String valorStr = txtServicoValor.getText().trim();

            if (tipo.isEmpty() || descricao.isEmpty() || valorStr.isEmpty()) {
                mostrarErro("Preencha todos os campos do serviço.");
                return;
            }

            BigDecimal valor = new BigDecimal(valorStr);
            Modelo.ServicoM servico = new ServicoM(0, tipo, descricao, valor);
            boolean ok = servicoC.inserirServico(servico);
            if (ok) {
                mostrarInfo("Serviço adicionado com sucesso.");
                carregarDadosServicos();
                limparFormularioServico();
            } else {
                mostrarErro("Erro ao adicionar serviço.");
            }
        } catch (Exception ex) {
            mostrarErro("Erro: " + ex.getMessage());
        }
    }

    private void atualizarServico() {
        int linha = tabelaServicos.getSelectedRow();
        if (linha < 0) {
            mostrarErro("Selecione um serviço para atualizar.");
            return;
        }
        try {
            int id = (int)modeloServicos.getValueAt(linha, 0);
            String tipo = txtServicoTipo.getText().trim();
            String descricao = txtServicoDescricao.getText().trim();
            String valorStr = txtServicoValor.getText().trim();

            if (tipo.isEmpty() || descricao.isEmpty() || valorStr.isEmpty()) {
                mostrarErro("Preencha todos os campos do serviço.");
                return;
            }

            BigDecimal valor = new BigDecimal(valorStr);
            Modelo.ServicoM servico = new ServicoM(id, tipo, descricao, valor);
            boolean ok = servicoC.atualizarServico(servico);
            if (ok) {
                mostrarInfo("Serviço atualizado com sucesso.");
                carregarDadosServicos();
                limparFormularioServico();
            } else {
                mostrarErro("Erro ao atualizar serviço.");
            }
        } catch (Exception ex) {
            mostrarErro("Erro: " + ex.getMessage());
        }
    }

    private void excluirServico() {
        int linha = tabelaServicos.getSelectedRow();
        if (linha < 0) {
            mostrarErro("Selecione um serviço para excluir.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Confirma exclusão do serviço?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            int id = (int)modeloServicos.getValueAt(linha, 0);
            boolean ok = servicoC.excluirServico(id);
            if (ok) {
                mostrarInfo("Serviço excluído com sucesso.");
                carregarDadosServicos();
                limparFormularioServico();
            } else {
                mostrarErro("Erro ao excluir serviço.");
            }
        } catch (Exception ex) {
            mostrarErro("Erro: " + ex.getMessage());
        }
    }

    private void limparFormularioServico() {
        txtServicoTipo.setText("");
        txtServicoDescricao.setText("");
        txtServicoValor.setText("");
        tabelaServicos.clearSelection();
    }

    // --- Métodos CRUD Atendimentos ---
    private void adicionarAtendimento() {
        try {
            PetM pet = (PetM)comboPetAtend.getSelectedItem();
            FuncionarioM func = (FuncionarioM)comboFuncAtend.getSelectedItem();
            ServicoM serv = (ServicoM)comboServAtend.getSelectedItem();
            String dataHoraStr = txtDataHoraAtend.getText().trim();
            String obs = txtObservacoesAtend.getText().trim();
            String valorFinalStr = txtValorFinalAtend.getText().trim();

            if (pet == null || func == null || serv == null || dataHoraStr.isEmpty() || valorFinalStr.isEmpty()) {
                mostrarErro("Preencha todos os campos obrigatórios.");
                return;
            }

            LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, dateTimeFormatter);
            BigDecimal valorFinal = new BigDecimal(valorFinalStr);

            AtendimentoM atendimento = new AtendimentoM(0, dataHora, obs, valorFinal,
                    pet.getIdPet(), func.getId(), serv.getIdServico(),
                    pet, func, serv);

            boolean ok = atendimentoC.inserirAtendimento(atendimento);
            if (ok) {
                mostrarInfo("Atendimento cadastrado com sucesso.");
                carregarDadosAtendimentos();
                limparFormularioAtendimento();
            } else {
                mostrarErro("Erro ao cadastrar atendimento.");
            }
        } catch (Exception ex) {
            mostrarErro("Erro: " + ex.getMessage());
        }
    }

    private void atualizarAtendimento() {
        int linha = tabelaAtendimentos.getSelectedRow();
        if (linha < 0) {
            mostrarErro("Selecione um atendimento para atualizar.");
            return;
        }
        try {
            int id = (int)modeloAtendimentos.getValueAt(linha, 0);
            PetM pet = (PetM)comboPetAtend.getSelectedItem();
            FuncionarioM func = (FuncionarioM)comboFuncAtend.getSelectedItem();
            ServicoM serv = (ServicoM)comboServAtend.getSelectedItem();
            String dataHoraStr = txtDataHoraAtend.getText().trim();
            String obs = txtObservacoesAtend.getText().trim();
            String valorFinalStr = txtValorFinalAtend.getText().trim();

            if (pet == null || func == null || serv == null || dataHoraStr.isEmpty() || valorFinalStr.isEmpty()) {
                mostrarErro("Preencha todos os campos obrigatórios.");
                return;
            }

            LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, dateTimeFormatter);
            BigDecimal valorFinal = new BigDecimal(valorFinalStr);

            AtendimentoM atendimento = new AtendimentoM(id, dataHora, obs, valorFinal,
                    pet.getIdPet(), func.getId(), serv.getIdServico(),
                    pet, func, serv);

            boolean ok = atendimentoC.atualizarAtendimento(atendimento);
            if (ok) {
                mostrarInfo("Atendimento atualizado com sucesso.");
                carregarDadosAtendimentos();
                limparFormularioAtendimento();
            } else {
                mostrarErro("Erro ao atualizar atendimento.");
            }
        } catch (Exception ex) {
            mostrarErro("Erro: " + ex.getMessage());
        }
    }

    private void excluirAtendimento() {
        int linha = tabelaAtendimentos.getSelectedRow();
        if (linha < 0) {
            mostrarErro("Selecione um atendimento para excluir.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Confirma exclusão do atendimento?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            int id = (int)modeloAtendimentos.getValueAt(linha, 0);
            boolean ok = atendimentoC.excluirAtendimento(id);
            if (ok) {
                mostrarInfo("Atendimento excluído com sucesso.");
                carregarDadosAtendimentos();
                limparFormularioAtendimento();
            } else {
                mostrarErro("Erro ao excluir atendimento.");
            }
        } catch (Exception ex) {
            mostrarErro("Erro: " + ex.getMessage());
        }
    }

    private void limparFormularioAtendimento() {
        comboPetAtend.setSelectedIndex(-1);
        comboFuncAtend.setSelectedIndex(-1);
        comboServAtend.setSelectedIndex(-1);
        // Reset date/time to current PC datetime automatically
        txtDataHoraAtend.setText(LocalDateTime.now().format(dateTimeFormatter));
        txtObservacoesAtend.setText("");
        txtValorFinalAtend.setText("");
        tabelaAtendimentos.clearSelection();
    }

    // --- Carregamento dos dados ---
    private void carregarDadosCadastroClientesPets() {
        // Carregar tabela clientes cadastro
        modeloClientesCad.setRowCount(0);
        List<ClienteM> clientes = clienteC.listarClientes();
        for (ClienteM c : clientes) {
            modeloClientesCad.addRow(new Object[]{
                    c.getId(), c.getNome(), c.getCpf(), c.getTelefone(), c.getEmail(), c.getEndereco()
            });
        }

        // Carregar clientes no combo pet
        carregarClientesComboPet();

        // Carregar tabela pets cadastro
        modeloPetsCad.setRowCount(0);
        List<PetM> pets = petC.listarPets();
        for (PetM p : pets) {
            modeloPetsCad.addRow(new Object[]{
                    p.getIdPet(), p.getNome(), p.getEspecie(), p.getRaca(),
                    p.getDataNascimento(), p.getPorte(), p.getIdCliente()
            });
        }
    }

    private void carregarDadosServicos() {
        modeloServicos.setRowCount(0);
        List<ServicoM> servicos = servicoC.listarServicos();
        for (ServicoM s : servicos) {
            modeloServicos.addRow(new Object[]{
                    s.getIdServico(), s.getTipoServico(), s.getDescricao(), s.getValorBase()
            });
        }
    }

    private void carregarDadosAtendimentos() {
        modeloAtendimentos.setRowCount(0);
        List<AtendimentoM> atendimentos = atendimentoC.listarAtendimentos();

        // Carregar combos para atendimento
        carregarComboPets();
        carregarComboFuncionarios();
        carregarComboServicos();

        for (AtendimentoM a : atendimentos) {
            modeloAtendimentos.addRow(new Object[]{
                    a.getIdAtendimento(),
                    a.getDataAtendimento().format(dateTimeFormatter),
                    a.getPet() != null ? a.getPet().getNome(): "",
                    a.getFuncionario() != null ? a.getFuncionario().getNome() : "",
                    a.getServico() != null ? a.getServico().getTipoServico() : "",
                    a.getValorFinal(),
                    a.getObservacoes()
            });
        }
    }

    private void carregarComboPets() {
        comboPetAtend.removeAllItems();
        List<PetM> pets = petC.listarPets();
        for (PetM p : pets) {
            comboPetAtend.addItem(p);
        }
        comboPetAtend.setSelectedIndex(-1);
    }

    private void carregarComboFuncionarios() {
        comboFuncAtend.removeAllItems();
        List<FuncionarioM> funcionarios = funcionarioC.listarFuncionarios();
        for (FuncionarioM f : funcionarios) {
            comboFuncAtend.addItem(f);
        }
        comboFuncAtend.setSelectedIndex(-1);
    }

    private void carregarComboServicos() {
        comboServAtend.removeAllItems();
        List<ServicoM> servicos = servicoC.listarServicos();
        for (ServicoM s : servicos) {
            comboServAtend.addItem(s);
        }
        comboServAtend.setSelectedIndex(-1);
    }

    // --- Mensagens de erro / info ---
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
            TelaFuncionarioV telaAdmV = new TelaFuncionarioV();
            telaAdmV.setVisible(true);
        });
    }
}

