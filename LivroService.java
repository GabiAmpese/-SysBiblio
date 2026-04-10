import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LivroService {

    private List<Livro> acervo = new ArrayList<>();

    public void cadastrar(Livro novoLivro) throws Exception {

        if (novoLivro == null)
            throw new Exception("Objeto Nulo");

        // Criado o método para validar e formatar os dados do livro, evitando repetição de código no cadastrar() e editarLivroCadastrado()
        validarEFormatar(novoLivro); 

        // Criado o método para verificar duplicidade, passando -1 pois no cadastro não precisamos ignorar nenhum índice
        verificarDuplicidade(novoLivro, -1);

        // Nesta parte estaria chamando a camada Repository
        acervo.add(novoLivro);
    }

    public List<Livro> listar() {
        return acervo;
    }

    // O método pesquisar() foi dividido em três métodos de pesquisa, por título, por autor e por ano de publicação.
    public List<Livro> pesquisarPorTitulo(String titulo) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        String tituloFormatado = titulo.trim().toUpperCase(); 

        for (Livro livro : acervo) {
            if (livro.getTitulo().contains(tituloFormatado))
                livrosEncontrados.add(livro);
        }
        return livrosEncontrados;
    }

    public List<Livro> pesquisarPorAutor(String autor) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        String autorFormatado = autor.trim().toUpperCase(); // Independente do que o usuário digite, o sistema vai formatar para maiúsculo e retirar os espaços em branco no início e no final da string, facilitando a busca.

        for (Livro livro : acervo) {
            if (livro.getAutor().contains(autorFormatado)) // contains() permite buscar por parte do nome do autor, por exemplo "Machado" para encontrar "Machado de Assis"
                livrosEncontrados.add(livro);
        }
        return livrosEncontrados;
    }

    public List<Livro> pesquisarPorAno(int ano) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        for (Livro livro : acervo) {
            if (livro.getAnoPublicacao() == ano)
                livrosEncontrados.add(livro);
        }
        return livrosEncontrados;
    }

    // Excluir por índice de exibição (int)
    public void excluirPorIndice(int indiceExibicao) throws Exception {
        if (acervo.isEmpty()) {
            throw new Exception("Nenhum livro cadastrado.");
        }
        if (indiceExibicao <= 0 || indiceExibicao > acervo.size()) {
            throw new Exception("Livro não encontrado!");
        }

        acervo.remove(indiceExibicao - 1);
    }

    // Excluir por título (String)
    public void excluirPorTitulo(String titulo) throws Exception {
        if (acervo.isEmpty()) {
            throw new Exception("Nenhum livro cadastrado.");
        }
        if (titulo == null || titulo.isBlank()) {
            throw new Exception("Nome do livro inválido!");
        }

        // No cadastrar(), você formata o título do livro que entra no sistema (novoLivro);
        // No excluirPorTitulo(), você precisa formatar o texto digitado pelo usuário para busca/exclusão (titulo).
        // "Dom Casmurro" vira "DOM CASMURRO" e vice-versa
        String tituloFormatadoBusca = titulo.trim().toUpperCase();

        // Esse bloco faz uma busca linear na lista e remove o primeiro livro que tiver título igual ao que o usuário digitou.
        for (int i = 0; i < acervo.size(); i++) { // Percorre a lista de livros
            if (acervo.get(i).getTitulo().equalsIgnoreCase(tituloFormatadoBusca)) { // Compara o título do livro com o título formatado para busca
                acervo.remove(i);  // Remove o livro da lista
                return;
            }
        }
        
        throw new Exception("Livro não encontrado!");
    }

    public void editarLivroCadastrado(int indiceExibicao, Livro livroEditado) throws Exception {
        if (acervo.isEmpty()) {
            throw new Exception("Nenhum livro cadastrado.");
        }
        if (indiceExibicao <= 0 || indiceExibicao > acervo.size()) {
            throw new Exception("Livro não encontrado!");
        }
        if (livroEditado == null) {
            throw new Exception("Objeto Nulo");
        }
        
        validarEFormatar(livroEditado);

        // Verifica duplicidade ignorando o índice que está sendo editado agora
        verificarDuplicidade(livroEditado, indiceExibicao - 1);

        // Atualizar o livro na lista
        acervo.set(indiceExibicao - 1, livroEditado);
    }

    // Método para validar e formatar os dados do livro antes de cadastrar ou editar
    private void validarEFormatar(Livro livro) throws Exception {
        if (livro.getTitulo() == null || livro.getTitulo().isBlank()) {
            throw new Exception("Título inválido!!!");
        }
        if (livro.getAutor() == null || livro.getAutor().isBlank()) {
            throw new Exception("Autor inválido!!!");
        }
        if (livro.getAnoPublicacao() < 1900 || livro.getAnoPublicacao() > LocalDate.now().getYear()) {
            throw new Exception("Ano de publicação inválido");
        }
        if (livro.getNumeroPaginas() <= 0) {
            throw new Exception("O livro deve ter pelo menos 1 página!");
        }

        livro.setTitulo(livro.getTitulo().trim().toUpperCase());
        livro.setAutor(livro.getAutor().trim().toUpperCase());
    }

    // Método para verificar duplicidade de livros, ignorando um índice específico (usado na edição)
    private void verificarDuplicidade(Livro livroAlvo, int indiceParaIgnorar) throws Exception {
        for (int i = 0; i < acervo.size(); i++) {
            // Se for o índice que queremos ignorar (no caso da edição), pula para o próximo
            if (i == indiceParaIgnorar) continue;

            Livro livroExistente = acervo.get(i);
            if (livroExistente.getTitulo().equalsIgnoreCase(livroAlvo.getTitulo())
                    && livroExistente.getAutor().equalsIgnoreCase(livroAlvo.getAutor())
                    && livroExistente.getAnoPublicacao() == livroAlvo.getAnoPublicacao()) {
                throw new Exception("Já existe um livro cadastrado com este Título, Autor e Ano de publicação");
            }
        }
    }
}
