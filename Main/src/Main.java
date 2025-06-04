import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Tarefa> lista_tarefas = Tarefa.getListaTarefas();
        boolean filtro = false, loop_menu = true;

        do {
            System.out.println("Bem-vindo! Qual é o seu nome?");
            String nome_usuario = input.nextLine();

            if (!nome_usuario.isBlank()) {
                do {
                    String opcaoStr = Utilidades.imprimirMenuPrincipal(nome_usuario, lista_tarefas, filtro, input);
                    input.nextLine();

                    if (Utilidades.isInt(opcaoStr)) {
                        int opcao = Integer.parseInt(opcaoStr);

                        if (opcao == 1) {
                            String titulo, descricao, dataStr;
                            boolean loop = true;

                            while (loop) {
                                System.out.println("Informe o título da tarefa:");
                                titulo = input.nextLine();

                                if (!titulo.isBlank()) {
                                    while (loop) {
                                        System.out.println("Faça uma breve descrição da tarefa:");
                                        descricao = input.nextLine();

                                        if (!descricao.isBlank()) {
                                            while (loop) {
                                                System.out.println("Informe a data limite (dd/MM/yyyy):");
                                                dataStr = input.nextLine();

                                                if (Utilidades.isDate(dataStr)) {
                                                    LocalDate data = Utilidades.toDate(dataStr);
                                                    Tarefa nova_tarefa = new Tarefa(titulo, descricao, data);

                                                    if (nova_tarefa.verificarDataLimite(nova_tarefa.getData_limite())) {
                                                        System.out.println("Erro! Data limite não pode ser antes da data de criação.\n");
                                                    }
                                                    else {
                                                        nova_tarefa.cadastrarTarefa();
                                                        loop = false;
                                                    }
                                                }
                                                else {
                                                    System.out.println("Erro! Data inválida");
                                                }
                                            }
                                        }
                                        else {
                                            System.out.println("Erro! Descrição não pode ser vazia.");
                                        }
                                    }
                                }
                                else {
                                    System.out.println("Erro! Título não pode ser vazio");
                                }
                            }
                        }

                        else if (opcao == 2) {
                            if (!Tarefa.getListaTarefas().isEmpty()) {
                                boolean loop = true;

                                while (loop) {
                                    System.out.println("Digite o título da tarefa que deseja editar:");
                                    String titulo = input.nextLine();

                                    if (!titulo.isBlank()) {
                                        Tarefa tarefa = Tarefa.encontrarTarefa_titulo(titulo);

                                        if (tarefa == null) {
                                            System.out.println("Não há registro.");
                                        }
                                        else {
                                            while (loop) {
                                                String opcao_edicaoStr;

                                                System.out.println();
                                                System.out.println("O que deseja editar?");
                                                opcao_edicaoStr = Utilidades.imprimirMenuSelecao(input, "modificar");
                                                input.nextLine();

                                                if (Utilidades.isInt(opcao_edicaoStr)) {
                                                    int opcao_edicao = Integer.parseInt(opcao_edicaoStr);

                                                    if (opcao_edicao > 0 && opcao_edicao < 5)  {
                                                        tarefa.editarTarefa(opcao_edicao, input);
                                                        loop = false;
                                                    }
                                                    else if (opcao_edicao == 5) {
                                                        loop = false;
                                                    }
                                                    else {
                                                        System.out.println("Comando inválido");
                                                    }
                                                }
                                                else {
                                                    System.out.println("Comando inválido");
                                                }
                                            }
                                        }
                                    }
                                    else {
                                        System.out.println("Erro! Título não pode ser vazio");
                                    }
                                }
                            }
                            else {
                                System.out.println("Não há registros de tarefas.");
                            }
                        }

                        else if (opcao == 3) {
                            if (!Tarefa.getListaTarefas().isEmpty()) {
                                while (true) {
                                    System.out.println("Digite o título da tarefa que deseja remover:");
                                    String titulo = input.nextLine();

                                    if (!titulo.isBlank()) {
                                        Tarefa tarefa = Tarefa.encontrarTarefa_titulo(titulo);

                                        if (tarefa == null) {
                                            System.out.println("Não há registro da tarefa");
                                            break;
                                        }
                                        else {
                                            tarefa.excluirTarefa();
                                            break;
                                        }
                                    }
                                    else {
                                        System.out.println("Erro! Título não pode ser vazio");
                                    }
                                }
                            }
                            else {
                                System.out.println("Não há registros de tarefas.");
                            }
                        }

                        else if (opcao == 4) {
                            if (!filtro) {
                                while (true) {
                                    String opcao_filtroStr;

                                    System.out.println();
                                    System.out.println("Selecione o critério do filtro");
                                    opcao_filtroStr = Utilidades.imprimirMenuSelecao(input, "filtrar");
                                    input.nextLine();

                                    if (Utilidades.isInt(opcao_filtroStr)) {
                                        int opcao_filtro = Integer.parseInt(opcao_filtroStr);

                                        if (opcao_filtro > 0 && opcao_filtro < 6)  {
                                            lista_tarefas = Tarefa.filtrarListaTarefas(opcao_filtro, input);

                                            if (lista_tarefas == null) {
                                                filtro = false;
                                                break;
                                            }
                                            else {
                                                filtro = true;
                                                break;
                                            }
                                        }
                                        else if (opcao_filtro == 6) {
                                            break;
                                        }
                                        else {
                                            System.out.println("Comando inválido");
                                        }
                                    }
                                    else {
                                        System.out.println("Comando inválido");
                                    }
                                }
                            }
                            else {
                                filtro = false;
                            }
                        }

                        else if (opcao == 5) {
                            System.out.println();
                            System.out.println("Até mais!");
                            input.close();
                            loop_menu = false;
                        }

                        else {
                            System.out.println();
                            System.out.println("Comando inválido. Tente novamente");
                        }
                    }
                    else {
                        System.out.println();
                        System.out.println("Comando inválido. Tente novamente");
                    }
                } while (loop_menu);
            }
            else {
                System.out.println("Erro! Nome de usuário não pode ser vazio");
            }
        } while (loop_menu);
    }
}
