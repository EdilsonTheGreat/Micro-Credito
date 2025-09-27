package estrutura;

public class No {
    private No proximo;
    private No anterior;
    private Object elemento;


    public No( No anterior, Object elemento,No primeiro) {
        this.proximo = primeiro;
        this.elemento = elemento;
        this.anterior=anterior;
    }
    public No( No anterior, Object elemento) {
        this.anterior = anterior;
        this.elemento = elemento;
        this.proximo=null;
    }
    public No( Object elemento, No proximo) {
        this.proximo = proximo;
        this.elemento = elemento;
        this.anterior=null;
    }
    public No( Object elemento) {
        this.elemento = elemento;
        this.anterior=null;
        this.proximo=null;
    }





    public No getProximo() {
        return proximo;
    }

    public void setProximo(No proximo) {
        this.proximo = proximo;
    }

    public No getAnterior() {
        return anterior;
    }

    public void setAnterior(No anterior) {
        this.anterior = anterior;
    }

    public Object getElemento() {
        return elemento;
    }

    public void setElemento(Object elemento) {
        this.elemento = elemento;
    }
}
