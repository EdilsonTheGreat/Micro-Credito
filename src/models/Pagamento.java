package models;

import java.time.LocalDate;

public class Pagamento {
    private String idPagamento;
    private String idEmprestimo;
    private double valorPago;
    private LocalDate dataPagamento;
    private int numeroParcela;
    private double juroAplicado;

    public Pagamento(String idPagamento, String idEmprestimo, double valorPago, LocalDate dataPagamento,
                    int numeroParcela) {
        this.idPagamento = idPagamento;
        this.idEmprestimo = idEmprestimo;
        this.valorPago = valorPago;
        this.dataPagamento = dataPagamento;
        this.numeroParcela = numeroParcela;
        this.juroAplicado = 0.0;
      
    }

    public String getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(String idPagamento) {
        this.idPagamento = idPagamento;
    }

    public String getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(String idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }


    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }


    public int getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(int numeroParcela) {
        this.numeroParcela = numeroParcela;
    }


    public double getJuroAplicado() {
        return juroAplicado;
    }

    public void setJuroAplicado(double juroAplicado) {
        this.juroAplicado = juroAplicado;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Pagamento{");
        sb.append("idPagamento='").append(idPagamento).append('\'');
        sb.append(", idEmprestimo='").append(idEmprestimo).append('\'');
        sb.append(", valorPago=").append(valorPago);
        sb.append(", dataPagamento=").append(dataPagamento);
        sb.append(", numeroParcela=").append(numeroParcela);
        sb.append(", juroAplicado=").append(juroAplicado);
        sb.append('}');
        return sb.toString();
    }
}
