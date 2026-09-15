package org.example;

public class Produto {
    private String nome;
    private double preco;
    private double peso;
    private boolean disponivel;

    public Produto() {
    }

    public Produto(String nome, double preco, double peso) {
        this.nome = nome;
        this.preco = preco;
        this.peso = peso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }

    public boolean isDisponivel() { return this.disponivel; }

    @Override
    public String toString() {
        return "Produto{nome='" + nome + "', preco=" + preco + ", peso=" + peso + "}";
    }
}
