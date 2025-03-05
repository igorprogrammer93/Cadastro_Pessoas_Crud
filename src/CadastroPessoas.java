import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class CadastroPessoas extends JFrame {
    private JTextField nomeField, idadeField, emailField, cpfField, enderecoField, buscaField;
    private JButton adicionarBtn, atualizarBtn, excluirBtn, buscarBtn;
    private JTable tabela;
    private DefaultTableModel modelo;
    private ArrayList<Pessoa> pessoas;
    private int linhaSelecionada = -1;

    public CadastroPessoas() {
        pessoas = new ArrayList<>();
        setTitle("Cadastro de Pessoas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(7, 2));
        formPanel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        formPanel.add(nomeField);

        formPanel.add(new JLabel("Idade:"));
        idadeField = new JTextField();
        formPanel.add(idadeField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("CPF:"));
        cpfField = new JTextField();
        formPanel.add(cpfField);

        formPanel.add(new JLabel("Endereço:"));
        enderecoField = new JTextField();
        formPanel.add(enderecoField);

        formPanel.add(new JLabel("Buscar por Nome:"));
        buscaField = new JTextField();
        formPanel.add(buscaField);

        JPanel btnPanel = new JPanel();
        adicionarBtn = new JButton("Adicionar");
        atualizarBtn = new JButton("Atualizar");
        excluirBtn = new JButton("Excluir");
        buscarBtn = new JButton("Buscar");
        btnPanel.add(adicionarBtn);
        btnPanel.add(atualizarBtn);
        btnPanel.add(excluirBtn);
        btnPanel.add(buscarBtn);
        formPanel.add(btnPanel);

        add(formPanel, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new String[]{"Nome", "Idade", "Email", "CPF", "Endereço"}, 0);
        tabela = new JTable(modelo);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        adicionarBtn.addActionListener(e -> adicionarPessoa());
        atualizarBtn.addActionListener(e -> atualizarPessoa());
        excluirBtn.addActionListener(e -> excluirPessoa());
        buscarBtn.addActionListener(e -> buscarPessoa());
        tabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                linhaSelecionada = tabela.getSelectedRow();
                if (linhaSelecionada != -1) {
                    nomeField.setText((String) modelo.getValueAt(linhaSelecionada, 0));
                    idadeField.setText((String) modelo.getValueAt(linhaSelecionada, 1).toString());
                    emailField.setText((String) modelo.getValueAt(linhaSelecionada, 2));
                    cpfField.setText((String) modelo.getValueAt(linhaSelecionada, 3));
                    enderecoField.setText((String) modelo.getValueAt(linhaSelecionada, 4));
                }
            }
        });

        setVisible(true);
    }

    private void adicionarPessoa() {
        String nome = nomeField.getText();
        String idade = idadeField.getText();
        String email = emailField.getText();
        String cpf = cpfField.getText();
        String endereco = enderecoField.getText();
        pessoas.add(new Pessoa(nome, Integer.parseInt(idade), email, cpf, endereco));
        modelo.addRow(new Object[]{nome, idade, email, cpf, endereco});
        limparCampos();
    }

    private void buscarPessoa() {
        String termo = buscaField.getText().toLowerCase();
        modelo.setRowCount(0);
        pessoas.stream().filter(p -> p.getNome().toLowerCase().contains(termo)).forEach(p ->
                modelo.addRow(new Object[]{p.getNome(), p.getIdade(), p.getEmail(), p.getCpf(), p.getEndereco()})
        );
    }

    private void atualizarPessoa() {
        if (linhaSelecionada != -1) {
            String nome = nomeField.getText();
            String idade = idadeField.getText();
            String email = emailField.getText();
            String cpf = cpfField.getText();
            String endereco = enderecoField.getText();
            modelo.setValueAt(nome, linhaSelecionada, 0);
            modelo.setValueAt(idade, linhaSelecionada, 1);
            modelo.setValueAt(email, linhaSelecionada, 2);
            modelo.setValueAt(cpf, linhaSelecionada, 3);
            modelo.setValueAt(endereco, linhaSelecionada, 4);
            limparCampos();
        }
    }

    private void excluirPessoa() {
        if (linhaSelecionada != -1) {
            modelo.removeRow(linhaSelecionada);
            linhaSelecionada = -1;
            limparCampos();
        }
    }

    private void limparCampos() {
        nomeField.setText("");
        idadeField.setText("");
        emailField.setText("");
        cpfField.setText("");
        enderecoField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CadastroPessoas::new);
    }
}

class Pessoa {
    private String nome, email, cpf, endereco;
    private int idade;

    public Pessoa(String nome, int idade, String email, String cpf, String endereco) {
        this.nome = nome;
        this.idade = idade;
        this.email = email;
        this.cpf = cpf;
        this.endereco = endereco;
    }

    public String getNome() { return nome; }
    public int getIdade() { return idade; }
    public String getEmail() { return email; }
    public String getCpf() { return cpf; }
    public String getEndereco() { return endereco; }
}
