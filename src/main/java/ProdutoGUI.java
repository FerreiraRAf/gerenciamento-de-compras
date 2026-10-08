import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProdutoGUI extends Application {
    private  ProdutoDAO produtoDAO;
    private ObservableList<Produto> produtos;
    private TableView<Produto> tableView;
    private TextField nomeInput, quantidadeInput, precoInput;
    private ComboBox<String> statusComboBox;
    private Connection conexaoDB;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage palco) {
        conexaoDB = ConexaoDB.conectar();
        produtoDAO = new ProdutoDAO(conexaoDB); // Inicializa o DAO
        produtos = FXCollections.observableArrayList(produtoDAO.consultarTodos()); // Carrega todos os produtos do banco de dados

        palco.setTitle("Gerenciamento de Stoque de Produtos");
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(10);

        HBox nomeProdutoBox = new HBox();
        nomeProdutoBox.setSpacing(10);
        Label nomeLabel = new Label("Produto:");
        nomeInput = new TextField();
        nomeProdutoBox.getChildren().addAll(nomeLabel, nomeInput);

        HBox quantidadeBox = new HBox();
        quantidadeBox.setSpacing(10);
        Label nomeQuantidade = new Label("Quantidade:");
        quantidadeInput = new TextField();
        quantidadeBox.getChildren().addAll(nomeQuantidade, quantidadeInput);

        HBox precoBox = new HBox();
        Label precoLabel = new Label("Preço:");
        precoInput = new TextField();
        precoBox.getChildren().addAll(precoLabel, precoInput);

        HBox  statusBox = new HBox();
        Label statusLabel = new Label("Sataus:");
        statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("Estoque Normal", "Estoque Baixo");
        statusBox.getChildren().addAll(statusLabel, statusComboBox);

        // Add produto
        Button adicionar = new Button("Adicionar");
        adicionar.setOnAction(e -> {
            String preco = precoInput.getText().replace(',', '.'); // Subtituir virgula pelo ponto
            Produto produto = new Produto(
                    nomeInput.getText(),
                    Integer.parseInt(quantidadeInput.getText()),
                    Double.parseDouble(preco),
                    statusComboBox.getValue()
            );
            produtoDAO.inserir(produto);
            produtos.setAll(produtoDAO.consultarTodos()); // Atualizar a lista  de produtos na lista
            limparCampos(); // Limpar os campos de entrada para uma nova digitação
        });


        // Ataulizar produto
        Button updateButton = new Button("Atualizar");
        updateButton.setOnAction(e -> {
            String preco = precoInput.getText().replace(',', '.');
            Produto selectedProduto = tableView.getSelectionModel().getSelectedItem(); // Obtém o produto selecionado
            if (selectedProduto != null) {
                selectedProduto.setNome_produto(nomeInput.getText());
                selectedProduto.setQuantidade(Integer.parseInt(quantidadeInput.getText()));
                selectedProduto.setPreco(Double.parseDouble(preco));
                selectedProduto.setStatus(statusComboBox.getValue());
                produtoDAO.atualizar(selectedProduto); // Atualizar o produto no banoc de dados
                produtos.setAll(produtoDAO.consultarTodos()); //Atualizar a lista de produtos
                limparCampos(); // Limpar os campos de entrada para uma nova digitação
            }
        });

        // Deletar o produto
        Button deleteButton = new Button("Excluir");
        deleteButton.setOnAction(e -> {
            Produto selectedProduto = tableView.getSelectionModel().getSelectedItem();// Obtém o produto selecionado
            if (selectedProduto != null) {
                produtoDAO.deletarProuto(selectedProduto.getId_produto()); // Excluir produto no banci de dados
                produtos.setAll(produtoDAO.consultarTodos()); //Atualizar a lista de produtos
                limparCampos(); // Limpar os campos de entrada para uma nova digitação
            }
        });

        // Limpar inputs
        Button clearButton = new Button("Limpar");
        clearButton.setOnAction(e -> {limparCampos();});

        tableView = new TableView<>();
        tableView.setItems(produtos);// Define a lista de produtos na tabela
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS); // Ajusta o tamanho das colunas
        List<TableColumn<Produto, ?>> columns = List.of(
                criarColuna("ID", "id_produto"),
                criarColuna("Produto", "nome_produto"),
                criarColuna("Quantidade", "quantidade"),
                criarColuna("Prço", "preco"),
                criarColuna("Status", "status")
        );
        tableView.getColumns().addAll(columns);

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nomeInput.setText(newSelection.getNome_produto());
                quantidadeInput.setText(String.valueOf(newSelection.getQuantidade()));
                precoInput.setText(String.valueOf(newSelection.getPreco()));
                statusComboBox.setValue(newSelection.getStatus());
            }
        });

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);
        buttonBox.getChildren().addAll(adicionar, updateButton, deleteButton, clearButton);// Add os botões

        vBox.getChildren().addAll(nomeProdutoBox, quantidadeBox, precoBox, statusBox, buttonBox, tableView);

        Scene scene = new Scene(vBox, 800, 600);
        palco.setScene(scene);
        palco.show();
    }

    /**
     * O método  stop é chamado automáticamente quando a aplicação JavaFX é encerrada
     */
    @Override
    public void stop() {
        try {
            conexaoDB.close(); // Fecha a conexão com o banco de dados
        } catch (SQLException e) {
            System.err.println("Erro ao fechar a conexão: " + e.getMessage());
        }
    }

    /**
     * Limpa os campos de entrada do formulário
     * Este metodo é chamado após adicionar, atualizar ou excluir um produto
     * Para garantir que os campos de entrada estejam prontos para uma nova entrada
     */
    private void limparCampos() {
        nomeInput.setText("");
        quantidadeInput.setText("");
        precoInput.setText("");
        statusComboBox.setValue(null);
    }

    /**
     * Cria uma coluna para TableView.
     * @param title O titulo da coluna que será exibido no cabeçalho.
     * @param property A propriedade do objeto Produto que esta coluna deve exibir.
     * @return A coluna configurada para a TableView
     */

    private TableColumn<Produto,String> criarColuna(String title, String property) {
        TableColumn<Produto, String> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property)); // Define a propriedade da coluna
        return column;
    }
}
