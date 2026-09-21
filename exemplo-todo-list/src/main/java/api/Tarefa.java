package api;

public class Tarefa {
    private int id;
    private String title;
    private String description;
    private TarefaStatusEnum status;

    public Tarefa(
            int id,
            String title,
            String description,
            TarefaStatusEnum status
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public TarefaStatusEnum getStatus() {
        return this.status;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(TarefaStatusEnum status) {
        this.status = status;
    }

};