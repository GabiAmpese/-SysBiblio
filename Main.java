
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
            5 - Editar Livro
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
                case 5 -> editar();
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

    try {
        switch (opc) {
            case 1 -> {
                listar(); // ajuda o usuário a ver os índices
                int indice = Input.scanInt("Digite o índice de exibição do livro a ser removido: ");
                service.excluirPorIndice(indice);
                IO.println("Livro removido com sucesso!");
            }
            case 2 -> {
                String titulo = Input.scanString("Digite o título do livro a ser removido: ");
                service.excluirPorTitulo(titulo);
                IO.println("Livro removido com sucesso!");
            }
            default -> IO.println("Opção inválida!");
        }
    } catch (Exception e) {
        IO.println("ERRO: " + e.getMessage());
    }
}

void editar() {
    if (service.listar().isEmpty()) {
        IO.println("Nenhum livro cadastrado.");
        return;
    }

    listar(); // Sugestão de funcionamento

    int indice = Input.scanInt("Digite o índice de exibição do livro a ser editado: ");
    
    String titulo = Input.scanString("Digite o novo título do livro: ");
    String autor = Input.scanString("Digite o novo autor do livro: ");
    int anoPublicacao = Input.scanInt("Digite o novo ano de publicação do livro: ");
    int numeroPaginas = Input.scanInt("Digite o novo número de páginas do livro: ");

    Livro livroEditado = new Livro(titulo, autor, anoPublicacao, numeroPaginas);

    try {
        service.editarLivroCadastrado(indice, livroEditado);
        IO.println("Livro editado com sucesso!");
    } catch (Exception e) {
        IO.println("ERRO: " + e.getMessage());
    }
}