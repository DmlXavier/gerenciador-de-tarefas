import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Tarefa {
    // Lista estática que armazena todas as tarefas criadas
    private static ArrayList<Tarefa> lista_tarefas = new ArrayList<>();

    // Atributos de cada tarefa
    private String titulo;
    private String descricao;
    private LocalDate data_criacao;
    private LocalDate data_limite;
    private boolean status;

    // Construtor da tarefa
    public Tarefa(String titulo, String descricao, LocalDate data_limite) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.data_criacao = LocalDate.now();
        this.data_limite = data_limite;
        this.status = false;
    }

    // Retorna a lista de tarefas
    public static ArrayList<Tarefa> getListaTarefas() {
        return lista_tarefas;
    }

    // Busca uma tarefa pelo título
    public static Tarefa encontrarTarefa(String titulo) {
        for (Tarefa tarefa : lista_tarefas) {
            if (tarefa.titulo.equalsIgnoreCase(titulo)) {
                return tarefa;
            }
        }

        return null;
    }

    // Filtra a lista de tarefas com base no critério escolhido
    public static ArrayList<Tarefa> filtrarListaTarefas(int atributo, Scanner input) {
        ArrayList<Tarefa> lista_filtrada = new ArrayList<>();

        // Filtro por título
        if (atributo == 1) {
            while (true) {
                System.out.println("Digite o título da tarefa:");
                String titulo = input.nextLine();

                if (!titulo.isBlank()) {
                    Tarefa tarefa = encontrarTarefa(titulo);

                    if (tarefa != null) {
                        lista_filtrada.add(tarefa);
                    };

                    break;
                }
                else {
                    System.out.println("Erro! Título não pode ser vazio");
                }
            }
        }

        // Filtro por descrição
        else if (atributo == 2) {
            while (true) {
                System.out.println("Digite a descrição da tarefa:");
                String descricao = input.nextLine();

                if (!descricao.isBlank()) {
                    for (Tarefa tarefa : lista_tarefas)  {
                        if (tarefa.descricao.equalsIgnoreCase(descricao)) {
                            lista_filtrada.add(tarefa);
                        }
                    }

                    break;
                }
                else {
                    System.out.println("Erro! Descrição não pode ser vazia");
                }
            }
        }

        // Filtro por data de criação ou data limite
        else if (atributo == 3 || atributo == 4) {
            while (true) {
                System.out.println("Digite a data (dd/MM/yyyy)");
                String dataStr = input.nextLine();

                if (Utilidades.isDate(dataStr)) {
                    LocalDate data = Utilidades.toDate(dataStr);

                    if (atributo == 3) {
                        for (Tarefa tarefa : lista_tarefas) {
                            if (tarefa.data_criacao.equals(data)) {
                                lista_filtrada.add(tarefa);
                            }
                        }
                    }

                    else {
                        for (Tarefa tarefa : lista_tarefas) {
                            if (tarefa.data_limite.equals(data)) {
                                lista_filtrada.add(tarefa);
                            }
                        }
                    }
                    break;
                }
                else {
                    System.out.println("Erro! Data inválida.");
                }
            }
        }

        // Filtro por status
        else if (atributo == 5) {
            while (true) {
                System.out.println("Selecione o status:");
                String escolha = Utilidades.imprimirMenuSelecao_status((input));

                if (Utilidades.isInt(escolha)) {
                    int escolhaInt = Integer.parseInt(escolha);

                    if (escolhaInt == 1 || escolhaInt == 2) {
                        if (escolhaInt == 1) {
                            for (Tarefa tarefa : lista_tarefas) {
                                if (tarefa.status) {
                                    lista_filtrada.add(tarefa);
                                }
                            }
                        }
                        else {
                            for (Tarefa tarefa : lista_tarefas) {
                                if (!tarefa.status) {
                                    lista_filtrada.add(tarefa);
                                }
                            }
                        }
                    }
                    else if (escolhaInt == 3) {
                        return null;
                    }
                    else {
                        System.out.println("Erro! Opção inválida");
                    }

                    break;

                }
                else {
                    System.out.println("Erro! Opção inválida");
                }
            }
        }

        if (lista_filtrada.isEmpty()) {
            System.out.println("Não há tarefas que correspondam ao filtro.\n");
        }

        return lista_filtrada;
    }

    // Métodos getters e setters para os atributos principais
    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String novo_titulo) {
        this.titulo = novo_titulo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String nova_descricao) {
        this.descricao = nova_descricao;
    }

    public LocalDate getData_criacao() {
        return this.data_criacao;
    }

    public LocalDate getData_limite() {
        return this.data_limite;
    }

    public void setDataLimite(LocalDate data_limite) {
        this.data_limite = data_limite;
    }

    // Verifica se a data limite da instância é antes da data de criação
    public boolean verificarDataLimite(LocalDate data) {
        return data.isBefore(this.getData_criacao());
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean novo_status) {
        this.status = novo_status;
    }

    // Adiciona a tarefa à lista, caso ela ainda não exista
    public void cadastrarTarefa() {
        if (encontrarTarefa(this.titulo) == null) {
            lista_tarefas.add(this);
        }
        else {
            System.out.println();
            System.out.println("Tarefa já cadastrada.");
        }
    }

    // Remove a tarefa da lista
    public void excluirTarefa(Scanner input) {
        while (true) {
            System.out.println("Tem certeza que deseja alterar o título desta tarefa? (s/n)");
            String confirmacao = input.next().toLowerCase();

            if (confirmacao.equals("s")) {
                lista_tarefas.remove(this);
                break;
            }
            else if (confirmacao.equals("n")) {
                break;
            }
            else {
                System.out.println("Comando inválido");
                break;
            }
        }
    }

    // Permite editar tarefa
    public void editarTarefa(int atributo, Scanner input) {
        // Editar título
        if (atributo == 1) {
            System.out.println("Digite o novo título:");
            String novo_titulo = input.nextLine();

            if (!novo_titulo.isBlank()) {
                while (true) {
                    System.out.println("Tem certeza que deseja alterar o título desta tarefa? (s/n)");
                    String confirmacao = input.next().toLowerCase();

                    if (confirmacao.equals("s")) {
                        setTitulo(novo_titulo);
                        break;
                    }
                    else if (confirmacao.equals("n")) {
                        break;
                    }
                    else {
                        System.out.println("Comando inválido");
                        break;
                    }
                }
            }
            else {
                System.out.println("Erro! Título não pode ser vazio");
            }
        }

        // Editar descrição
        else if (atributo == 2) {
            System.out.println("Digite a nova descrição:");
            String nova_descricao = input.nextLine();

            if (!nova_descricao.isBlank()) {
                while (true) {
                    System.out.println("Tem certeza que deseja alterar a descrição desta tarefa? (s/n)");
                    String confirmacao = input.next().toLowerCase();

                    if (confirmacao.equals("s")) {
                        setDescricao(nova_descricao);
                        break;
                    }
                    else if (confirmacao.equals("n")) {
                        break;
                    }
                    else {
                        System.out.println("Comando inválido");
                        break;
                    }
                }
            }
            else {
                System.out.println("Erro! Descrição não pode ser vazia.");
            }
        }

        // Editar data limite
        else if (atributo == 3) {
            boolean loop = true;

            while (loop) {
                System.out.println("Digite a nova data limite (dd/MM/yyyy):");
                String nova_dataStr = input.nextLine();

                if (Utilidades.isDate(nova_dataStr)) {
                    LocalDate nova_data = Utilidades.toDate(nova_dataStr);

                    if (this.verificarDataLimite(nova_data)) {
                        System.out.println("Erro! Data limite não pode ser antes da data de criação.\n");
                    }
                    else {
                        while (loop) {
                            System.out.println("Tem certeza que deseja alterar a data limite desta tarefa? (s/n)");
                            String confirmacao = input.next().toLowerCase();

                            if (confirmacao.equals("s")) {
                                setDataLimite(nova_data);
                                loop = false;
                            }
                            else if (confirmacao.equals("n")) {
                                loop = false;
                            }
                            else {
                                System.out.println("Comando inválido");
                                loop = false;
                            }
                        }
                    }
                }
                else {
                    System.out.println("Erro! Data inválida");
                }
            }
        }

        // Alterar status da tarefa
        else if (atributo == 4) {
            while (true) {
                System.out.println("Tem certeza que deseja alterar o status desta tarefa? (s/n)");
                String confirmacao = input.next().toLowerCase();

                if (confirmacao.equals("s")) {
                    if (!this.status) {
                        setStatus(true);
                        break;
                    }
                    else {
                        setStatus(false);
                        break;
                    }
                }
                else if (confirmacao.equals("n")) {
                    break;
                }
                else {
                    System.out.println("Comando inválido");
                    break;
                }
            }
        }
    }
}