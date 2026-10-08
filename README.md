# 📦 Gerenciamento de Estoque de Produtos

Aplicação desktop em **Java** para cadastrar e controlar produtos em estoque, com interface gráfica em **JavaFX** e persistência de dados em **SQLite** via **JDBC**.

O projeto implementa um **CRUD** completo (criar, ler, atualizar e excluir) seguindo o padrão **DAO**, separando a interface, o acesso ao banco e o modelo de dados.

---

## ✨ Funcionalidades

- Adicionar novos produtos (nome, quantidade, preço e status)
- Listar todos os produtos em uma tabela
- Atualizar um produto selecionado na tabela
- Excluir um produto selecionado
- Limpar os campos do formulário
- Aceita vírgula ou ponto como separador decimal no preço
- Status do estoque: **Estoque Normal** ou **Estoque Baixo**
- Dados salvos localmente em um arquivo SQLite (`meu_banco_de_dados.db`)

---

## 🛠️ Tecnologias

| Tecnologia | Uso |
|---|---|
| Java 21 | Linguagem principal |
| Maven | Gerenciamento de dependências e build |
| JavaFX | Interface gráfica |
| SQLite | Banco de dados local |
| JDBC (`sqlite-jdbc`) | Conexão entre Java e SQLite |

---

## 🗂️ Estrutura do projeto

```
.
├── pom.xml                          # Configuração do Maven (dependências e plugin do JavaFX)
├── meu_banco_de_dados.db            # Banco de dados SQLite
└── src
    └── main
        ├── java
        │   ├── ConexaoDB.java       # Cria a conexão com o banco SQLite
        │   ├── CriadorTabela.java   # Script que cria a tabela "produtos"
        │   ├── Produto.java         # Classe modelo (entidade)
        │   ├── ProdutoDAO.java      # Acesso ao banco: inserir, consultar, atualizar e excluir
        │   └── ProdutoGUI.java      # Interface gráfica JavaFX (classe principal)
        └── resources
            └── styles-produtos.css  # Estilos da interface
```

### Modelo da tabela `produtos`

| Coluna | Tipo | Descrição |
|---|---|---|
| `id_produto` | INTEGER (PK) | Identificador do produto |
| `nome_produto` | TEXT (NOT NULL) | Nome do produto |
| `quantidade` | INTEGER | Quantidade em estoque |
| `preco` | REAL | Preço unitário |
| `status` | TEXT | Situação do estoque |

---

## ✅ Pré-requisitos

- [JDK 21](https://adoptium.net/) instalado
- Uma IDE com suporte a Maven (como o IntelliJ IDEA, que já vem com o Maven embutido) **ou** o [Maven](https://maven.apache.org/download.cgi) instalado para usar pelo terminal

> 💡 Não é preciso baixar o JavaFX nem o driver do SQLite manualmente. O Maven baixa tudo sozinho a partir do `pom.xml`.

---

## ▶️ Como executar

1. **Clone o repositório**

```bash
   git clone https://github.com/FerreiraRAf/gerenciamento-de-compras.git
   cd gerenciamento-de-compras
```

2. **Abra o projeto na IDE**

   No IntelliJ: `File > Open`, selecione o arquivo `pom.xml` e escolha **Open as Project**. Aguarde o Maven baixar as dependências (a barra de progresso fica no canto inferior direito).

3. **Crie a tabela** (apenas na primeira execução, caso o arquivo `.db` ainda não tenha a tabela):

   Execute a classe `CriadorTabela`.

4. **Inicie a aplicação pelo Maven**

   No terminal, dentro da pasta do projeto:

```bash
   mvn javafx:run
```

Ou, no IntelliJ, abra a aba **Maven** (lado direito) e vá em `Plugins > javafx > javafx:run`.

> ⚠️ **Não execute a `ProdutoGUI` pelo botão de play da IDE.** Isso causa o erro `JavaFX runtime components are missing`, pois o JavaFX precisa ser configurado pelo plugin do Maven. Use sempre `mvn javafx:run`.

> ⚠️ O arquivo `styles-produtos.css` precisa estar em `src/main/resources` para que os estilos sejam carregados.

---

## 🧭 Como usar

1. Preencha **Produto**, **Quantidade**, **Preço** e **Status** e clique em **Adicionar**.
2. Clique em uma linha da tabela para carregar os dados nos campos.
3. Edite os campos e clique em **Atualizar** para salvar as alterações.
4. Com uma linha selecionada, clique em **Excluir** para remover o produto.
5. Use **Limpar** para esvaziar o formulário.

---

## 🧱 Arquitetura

```
ProdutoGUI  ──►  ProdutoDAO  ──►  ConexaoDB  ──►  SQLite
 (interface)     (acesso a dados)   (conexão)       (.db)
        └────────────► Produto (modelo) ◄────────────┘
```

- **Produto**: representa um registro da tabela.
- **ProdutoDAO**: concentra todas as operações SQL usando `PreparedStatement`, o que protege contra SQL Injection.
- **ConexaoDB**: centraliza a string de conexão com o banco.
- **ProdutoGUI**: monta a tela, trata os eventos dos botões e atualiza a `TableView`.

---

## 👤 Autor

**Rafael da Rosa Ferreira**

- GitHub: [@FerreiraRAf](https://github.com/FerreiraRAf)
- LinkedIn: [Rafael Ferreira](https://www.linkedin.com/in/rafael-ferreira-21131539b/)
- Portifólio [SZM](https://ferreiraraf.github.io/my-portifolio/)