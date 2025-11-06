// model/Paciente.java
package model;

public class Paciente {
    private int id;
    private String nome;
    private String cpf;
    private int idade;
    private String email;
    private String telefone;
    private String horario; // NOVO CAMPO

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }
}