package models;

import java.time.LocalDate;

public class Emprestimo {
    private String idEmprestimo;
    private String biCliente;       // FK para Cliente
    private double valorEmprestado;
    private double valorAPagar;   // valorTotal + 30% de juro
    private String tipo;         // "prestacao" ou "directo"
    private int numeroPrestacoes; // válido apenas se tipo = "prestacao"
    private  final double TAXA_JURO = 0.30;
    private String estado;       // "ativo", "liquidado", "atrasado"
    private LocalDate dataConcessao;
    private LocalDate dataVencimento;

    public Emprestimo(String idEmprestimo, String biCliente, double valorEmprestado, String tipo, int numeroPrestacoes, String estado, LocalDate dataConcessao, LocalDate dataVencimento) {
        this.idEmprestimo = idEmprestimo;
        this.biCliente = biCliente;
        this.valorEmprestado = valorEmprestado;
        this.tipo = tipo;
        this.numeroPrestacoes = numeroPrestacoes;
        this.estado = estado;
        this.dataConcessao = dataConcessao;
        this.dataVencimento=  dataVencimento;
        this.valorAPagar = valorEmprestado + (valorEmprestado * TAXA_JURO);
    }



    public String getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(String idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public String getBiCliente() {
        return biCliente;
    }

    public void setBiCliente(String biCliente) {
        this.biCliente = biCliente;
    }

    public double getValorEmprestado() {
        return valorEmprestado;
    }

    public void setValorEmprestado(double valorEmprestado) {
        this.valorEmprestado = valorEmprestado;
    }
    public double getValorAPagar() {
        return valorAPagar;
    }
    public void setValorAPagar(double valorAPagar) {
        this.valorAPagar=valorAPagar;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getNumeroPrestacoes() {
        return numeroPrestacoes;
    }

    public void setNumeroPrestacoes(int numeroPrestacoes) {
        this.numeroPrestacoes = numeroPrestacoes;
    }

    public double getTAXA_JURO() {
        return TAXA_JURO;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getDataConcessao() {
        return dataConcessao;
    }

    public void setDataConcessao(LocalDate dataConcessao) {
        this.dataConcessao = dataConcessao;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Emprestimo{");
        sb.append("idEmprestimo='").append(idEmprestimo).append('\'');
        sb.append(", biCliente='").append(biCliente).append('\'');
        sb.append(", valorTotal=").append(valorEmprestado);
        sb.append(", valor A Pagar=").append(valorAPagar);
        sb.append(", tipo='").append(tipo).append('\'');
        sb.append(", numeroPrestacoes=").append(numeroPrestacoes);
        sb.append(", TAXA_JURO=").append(TAXA_JURO).append('%');
        sb.append(", estado='").append(estado).append('\'');
        sb.append(", dataConcessao=").append(dataConcessao);
        sb.append(", dataVencimento=").append(dataVencimento);
        sb.append('}');
        return sb.toString();
    }


}
