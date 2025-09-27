package models;

import java.time.LocalDate;

public class Cliente {
    private int Id;
    private String bi;
    private String nome;
    private String apelido;
    private int telefone;
    private String endereco;
    private LocalDate dataCadastro;

    public Cliente(int id, String bi, String nome, String apelido,
                   int telefone, String endereco, LocalDate dataCadastro) {
        Id = id;
        this.bi = bi;
        this.nome = nome;
        this.apelido = apelido;
        this.telefone = telefone;
        this.endereco = endereco;
        this.dataCadastro = LocalDate.now();
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getBi() {
        return bi;
    }

    public void setBi(String bi) {
        this.bi = bi;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Cliente{");
        sb.append("Id=").append(Id);
        sb.append(", bi='").append(bi).append('\'');
        sb.append(", nome='").append(nome).append('\'');
        sb.append(", apelido='").append(apelido).append('\'');
        sb.append(", telefone=").append(telefone);
        sb.append(", endereco='").append(endereco).append('\'');
        sb.append(", dataCadastro=").append(dataCadastro);
        sb.append('}');
        return sb.toString();
    }
}
