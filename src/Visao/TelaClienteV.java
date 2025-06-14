package visao;

import Controle.PetC;
import Controle.AtendimentoC;
import Modelo.PetM;
import Modelo.ServicoM;
import Modelo.ClienteM;
import Modelo.AtendimentoM;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Tela para o Cliente logado.
 * Exibe Pets do cliente e Serviços contratados.
 * 
 * @author Joao
 */
public class TelaClienteV extends JFrame {

    private ClienteM cliente;
    private JTable tabelaPets;
    private JTable tabelaServicosContratados;

    private PetC petC;
    private AtendimentoC atendimentoC;

    public TelaClienteV(ClienteM cliente) {
        this.cliente = cliente;
        petC = new PetC();
        atendimentoC = new AtendimentoC();

        setTitle("Área do Cliente - " + cliente.getNome());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        initComponents();
        carregarDados();
    }

    private void initComponents() {
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        painelPrincipal.setLayout(new BorderLayout(0, 24));
        painelPrincipal.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Bem-vindo, " + cliente.getNome());
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(33, 150, 243));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // Painel central com Pets e Serviços contratados lado a lado
        JPanel painelConteudo = new JPanel(new GridLayout(2, 1, 0, 24));
        painelConteudo.setBackground(Color.WHITE);

        // Tabela de Pets
        tabelaPets = new JTable();
        JScrollPane scrollPets = new JScrollPane(tabelaPets);
        scrollPets.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(33, 150, 243)),
                "Meus Pets", TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 16)));

        painelConteudo.add(scrollPets);

        // Tabela de Serviços contratados
        tabelaServicosContratados = new JTable();
        JScrollPane scrollServicos = new JScrollPane(tabelaServicosContratados);
        scrollServicos.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(33, 150, 243)),
                "Serviços Contratados", TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 16)));

        painelConteudo.add(scrollServicos);

        painelPrincipal.add(painelConteudo, BorderLayout.CENTER);

        // Botão para sair / voltar para login
        JButton btnLogout = new JButton("Sair");
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogout.setBackground(new Color(244, 67, 54));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogout.addActionListener(e -> onLogout());

        JPanel painelSul = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelSul.setBackground(Color.WHITE);
        painelSul.add(btnLogout);
        painelPrincipal.add(painelSul, BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    private void carregarDados() {
        // Carregar pets do cliente
        List<PetM> pets = petC.listarPetsPorClienteId(cliente.getId());

        String[] colunasPets = {"ID", "Nome", "Espécie", "Raça", "Data Nascimento", "Porte"};
        DefaultTableModel modeloPets = new DefaultTableModel(colunasPets, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabela somente leitura
            }
        };
        for (PetM pet : pets) {
            modeloPets.addRow(new Object[]{
                    pet.getIdPet(),
                    pet.getNome(),
                    pet.getEspecie(),
                    pet.getRaca(),
                    pet.getDataNascimento(),
                    pet.getPorte()
            });
        }
        tabelaPets.setModel(modeloPets);

        // Carregar serviços contratados pelo cliente via atendimentos vinculados a seus pets
        List<AtendimentoM> atendimentos = atendimentoC.listarAtendimentos();
        // Filtrar atendimentos relacionados aos pets do cliente
        List<Integer> petIds = pets.stream().map(PetM::getIdPet).collect(Collectors.toList());
        List<ServicoM> servicosContratados = atendimentos.stream()
                .filter(a -> petIds.contains(a.getIdPet()))
                .map(AtendimentoM::getServico)
                .filter(s -> s != null)
                .distinct()
                .collect(Collectors.toList());

        String[] colunasServicos = {"ID Serviço", "Tipo de Serviço", "Descrição", "Valor Base"};
        DefaultTableModel modeloServicos = new DefaultTableModel(colunasServicos, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabela somente leitura
            }
        };

        for (ServicoM servico : servicosContratados) {
            modeloServicos.addRow(new Object[]{
                    servico.getIdServico(),
                    servico.getTipoServico(),
                    servico.getDescricao(),
                    servico.getValorBase()
            });
        }
        tabelaServicosContratados.setModel(modeloServicos);
    }

    private void onLogout() {
        dispose();
        new LoginV().setVisible(true);
    }

    // Para teste independente da tela (usando cliente fictício)
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        // Cliente fictício para teste
        ClienteM clienteTeste = new ClienteM("", null, 1, "Cliente Teste", "99999999999", "99999-9999", "teste@email.com");

        SwingUtilities.invokeLater(() -> {
            TelaClienteV tela = new TelaClienteV(clienteTeste);
            tela.setVisible(true);
        });
    }
}
