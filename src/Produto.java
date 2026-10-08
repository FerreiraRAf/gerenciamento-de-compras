public class Produto {
    private int id_produto;
    private String nome_produto;
    private int quantidade;
    private double preco;
    private String status;

    public Produto (String nome, int quantidade, double preco, String status) {
        nome_produto = nome;
        this.quantidade = quantidade;
        this.preco = preco;
        this.status = status;
    }

    public Produto() {
        // Construtor vazio
    }

    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() { return preco; }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
