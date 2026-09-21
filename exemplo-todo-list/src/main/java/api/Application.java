// Este arquivo pertence ao pacote "api" e mora em src/main/java/api/.
// Nome completo desta classe: api.Application
package api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
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

    // DTOs

    public record TarefaCriarRequest(
            String title,
            String description,
            TarefaStatusEnum status
    ) {};

    public record TarefaAtualizarParcialmenteRequest(
            String title,
            String description,
            TarefaStatusEnum status
    ) {};

    public record TarefaResponse(
            int id,
            String title,
            String description,
            TarefaStatusEnum status
    ) {};

    // ----- FIM DTOs

    private List<Tarefa> tarefas = new ArrayList<>();


    @GetMapping("/tarefas")
    public List<TarefaResponse> getTarefas() {
        return tarefas
                .stream()
                .map((tarefa) -> {
                    return new TarefaResponse(
                            tarefa.getId(),
                            tarefa.getTitle(),
                            tarefa.getDescription(),
                            tarefa.getStatus()
                    );
                })
                .toList();
    }

    @PostMapping("/tarefas")
    public Tarefa postTarefas(@RequestBody TarefaCriarRequest request) {
        var tarefa = new Tarefa(
                tarefas.size(),
                request.title(),
                request.description(),
                request.status()
        );

        tarefas.add(tarefa);

        return tarefa;
    }

    @PatchMapping("/tarefas/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String atualizarParcialmenteTarefaPorId(
            @PathVariable int id,
            @RequestBody TarefaAtualizarParcialmenteRequest request
    ) {
        Tarefa tarefa = this.tarefas
                .stream()
                .filter(v -> v.getId() == id )
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada."));

        if (request.title() != null) {
            tarefa.setTitle(request.title());
        }

        if (request.description() != null) {
            tarefa.setDescription(request.description());
        }

        if (request.status() != null) {
            tarefa.setStatus(request.status());
        }

        return "Tarefa atualizada com sucesso.";
    }


    @GetMapping("/tarefas/{id}")
    public TarefaResponse getTarefa(@PathVariable  int id) {
        var tarefa = this.tarefas.stream()
                .filter(v -> v.getId() == id )
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada."));

        return new TarefaResponse(
                tarefa.getId(),
                tarefa.getTitle(),
                tarefa.getDescription(),
                tarefa.getStatus()
        );
    }


    @DeleteMapping("/tarefas/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirTarefa(@PathVariable int id) {
        this.tarefas
            .removeIf((tarefa) -> {
                return tarefa.getId() == id;
            });
    }

}
