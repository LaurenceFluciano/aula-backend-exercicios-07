// Este arquivo pertence ao pacote "api" e mora em src/main/java/api/.
// Nome completo desta classe: api.Application
package api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

// Exemplo mínimo, tudo numa classe só, para focar em três ideias:
//
// 1. @RestController marca a classe como responsável por responder requisições HTTP
//    (por baixo dos panos: @Controller + @ResponseBody, então o retorno de cada
//    método vira o CORPO da resposta, não o nome de uma página HTML).
// 2. @GetMapping/@PostMapping mapeiam um método para responder GET/POST numa rota.
// 3. O valor retornado pelo método vira automaticamente o corpo da resposta --
//    Strings viram texto puro, objetos (como o record Nome, abaixo) o Spring
//    serializa em JSON sozinho, usando Jackson por baixo.
@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public enum TarefaStatusEnum {
        PENDENTE,
        FAZENDO,
        CONCLUIDA
    }

    public class Tarefa {
        private int id;
        private String title;
        private String description;

        public Tarefa(int id, String title, String description, TarefaStatusEnum status) {

        }
    };

    public record TarefaRequest(String title, String description, TarefaStatusEnum status) {};

    public record TarefaAtualizarParcialmenteRequest(Optional<String> title, Optional<String> description, Optional<TarefaStatusEnum> status) {};

    private List<Tarefa> tarefas = new ArrayList<>();


    @GetMapping("/tarefas")
    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    @PostMapping("/tarefas")
    public Tarefa postTarefas(@RequestBody TarefaRequest request) {
        var tarefa = new Tarefa(tarefas.size()+1, request.title(), request.description(), request.status());

        tarefas.add(tarefa);

        return tarefa;
    }

    @PatchMapping("/tarefas/{id}")
    public void atualizarParcialmenteTarefaPorId(int id, @RequestBody TarefaAtualizarParcialmenteRequest request) {
        var tarefa = this.tarefas.stream().filter(v -> v.id() == id ).findFirst();

        if (tarefa.isEmpty()) {
            throw new RuntimeException("Tarefa não encontrada.");
        }


    }


    @GetMapping("/tarefas/{id}")
    public Tarefa getTarefa(int id) {
        var tarefa = this.tarefas.stream().filter(v -> v.id() == id ).findFirst();

        if (tarefa.isEmpty()) {
            throw new RuntimeException("Tarefa não encontrada.");
        }

        return tarefa.get();
    }

}
