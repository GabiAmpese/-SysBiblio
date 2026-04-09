import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LivroService {

    private List<Livro> acervo = new ArrayList<>();

    public void cadastrar(Livro novoLivro) throws Exception {

        if (novoLivro == null)
            throw new Exception("Objeto Nulo");

        //Estou validando e formatando o Título
        if (novoLivro.getTitulo() == null || novoLivro.getTitulo().isEmpty())
            throw new Exception("Título inválido!!!");
        novoLivro.setTitulo(novoLivro.getTitulo().trim().toUpperCase());

        // Formatação e validação do autor que faltava, pedida em aula
        if (novoLivro.getAutor() == null || novoLivro.getAutor().isEmpty())
            throw new Exception("Autor inválido!!!");
        novoLivro.setAutor(novoLivro.getAutor().trim().toUpperCase());

        // Validação do ano de publicação
        if (novoLivro.getAnoPublicacao() < 1900
                || novoLivro.getAnoPublicacao() > LocalDate.now().getYear())
            throw new Exception("Ano de publicação inválido");

        // Verificar se já existe um livro com o mesmo título, autor e ano de publicação
        for (Livro livro : acervo) { 
            if (livro.getTitulo().equalsIgnoreCase(novoLivro.getTitulo())
                    && livro.getAutor().equalsIgnoreCase(novoLivro.getAutor())
                    && livro.getAnoPublicacao() == novoLivro.getAnoPublicacao())
                throw new Exception("Já existe livro cadastrado com este Título, Autor e Ano de publicação");
        }

        // Nesta parte estaria chamando a camada Repository
        acervo.add(novoLivro);
    }

    public List<Livro> listar() {
        return acervo;
    }

    public List<Livro> pesquisar(String titulo) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        titulo = titulo.toUpperCase();

        for (Livro livro : acervo) {
            if (livro.getTitulo().contains(titulo))
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
}



