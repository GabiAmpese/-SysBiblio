import java.util.List;

//Dependências
LivroService service = new LivroService();

void main() {
    String menu = """
            ===== SysBiblio =====
            1 - Cadastrar Livro
            2 - Listar Livros
            3 - Pesquisar Livro
            4 - Remover Livro
                1 - Por Índice
                2 - Por Nome
            0 - Sair
            """;

    int opcao;
    do {
        IO.println(menu);
        opcao = Input.scanInt("Digite uma opção: ");
        try {
            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listar();
                case 3 -> pesquisar();
                case 4 -> excluir();
                case 0 -> IO.println("Até breve!!!");
                default -> IO.println("Opção Inválida");
            }
        } catch (Exception e) {
            IO.println("ERRO: " + e.getMessage());
        }
        IO.readln("Pressione Enter para continuar...");
    } while (opcao != 0);
}

void cadastrar() throws Exception {
    String titulo = Input.scanString("Digite o título do livro: ");
    String autor = Input.scanString("Digite o autor do livro: ");
    int anoPublicacao = Input.scanInt("Digite o ano de publicação do livro: ");
    int numeroPaginas = Input.scanInt("Digite o número de páginas do livro: ");

    Livro novoLivro = new Livro(titulo, autor, anoPublicacao, numeroPaginas);

    service.cadastrar(novoLivro);
    
    IO.println("Livro cadastrado com sucesso!!!");
}

void listar() {

    List<Livro> livros = service.listar();

    imprimirLista(livros);

}

void pesquisar() {

    String pesquisa = Input.scanString("Digite parte do título: ");
    
    List<Livro> livros = service.pesquisar(pesquisa);

    imprimirLista(livros);
}

void imprimirLista(List<Livro> livros) {
    if (livros.isEmpty()) {
        IO.println("Nenhum livro encontrado!");
        return;
    }

    int i = 1;
    for (Livro livro : livros) {
        IO.println(i++  + " - " + livro);
        //IO.println(i++  + " - " + livro.toString());
    }
}

void excluir() {
    if (service.listar().isEmpty()) {
        IO.println("Nenhum livro cadastrado.");
        return;
    }

    IO.println("Escolha a forma de remoção:");
    IO.println("1 - Por Índice");
    IO.println("2 - Por Nome");
    int opc = Input.scanInt("Digite a opção 1 ou 2: ");

    if (opc == 1) { // 1
        listar();
        int indice = Input.scanInt("Digite o número do livro que deseja remover: ");
        if (indice > service.listar().size() || indice <= 0) {
            IO.println("Livro não encontrado!");
        } else {
            service.listar().remove(indice - 1);
            IO.println("Livro removido com sucesso!");
        }
    } else if (opc == 2) { // 2
        String nome = IO.readln("Digite o nome do livro a ser removido: ").trim();
        if (nome.isEmpty()) {
            IO.println("Nome do livro inválido!");
            return;
        }

        for (int i = 0; i < service.listar().size(); i++) {
            if (service.listar().get(i).equalsIgnoreCase(nome)) {
                service.listar().remove(i);
                IO.println("Livro removido com sucesso!");
                return;
            }
        }

        IO.println("Livro não encontrado!");
    } else {
        IO.println("Opção inválida de remoção.");
    }
}
