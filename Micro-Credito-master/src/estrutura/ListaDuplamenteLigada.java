package estrutura;

public class ListaDuplamenteLigada implements InterfaceGeral {
    private No primeiro;
    private No ultimo;
    private int totalElem;
    @Override
    public void adcionaInicio(Object elemento) {
        if (elemento==null){
            throw new IllegalArgumentException("Elemento não pode ser Null");
        }
        No novo = new No(elemento,primeiro);
        if(totalElem==0){
            primeiro=novo;
            ultimo= novo;

        }else{
            primeiro.setAnterior(novo);
            primeiro=novo;
        }
        totalElem++;
    }

    @Override
    public void adcionaPosicao(int posicao, Object elemento) {
        if (!posicaoValida(posicao)){
            throw new IndexOutOfBoundsException("Posição "+ posicao + "Invalida");
        }
        if(elemento==null){
            throw new IllegalArgumentException("Elemento não pode ser Null");
        }
        if (posicao==0){
            adcionaInicio(elemento);
        }else if(posicao==totalElem){
            adcionaFim(elemento);
        } else {
            No anterior = pegaNo(posicao-1);
            No actual = anterior.getProximo();
            No novo = new No(anterior,elemento,actual);
            anterior.setProximo(novo);
            actual.setAnterior(novo);
            totalElem++;
        }
    }



    @Override
    public void adcionaFim(Object elemento) {
        if(elemento==null){
            throw new IllegalArgumentException("Elemento não pode ser Null");
        }
        if (totalElem==0) {
            adcionaInicio(elemento);
        }else{
            No novo = new No(ultimo, elemento);
            ultimo.setProximo(novo);
            ultimo = novo;
            totalElem++;
        }

    }

    @Override
    public Object pega(int posicao) {
        return pegaNo(posicao).getElemento();
    }

    @Override
    public void removeInicio() {
        if (eVazia()){throw new NullPointerException("Estrutura vazia");}
        if (totalElem==1) {
            primeiro=null;
            ultimo = null;
        }else{
            No segundo = primeiro.getProximo();
            segundo.setAnterior(null);
            primeiro=segundo;
        }
        totalElem--;
    }

    @Override
    public void removePosicao(int posicao) {
        if (eVazia()){throw new NullPointerException("Estrutura vazia");}
        if (!posicaoOcupada(posicao)){throw new IndexOutOfBoundsException("Posicao " + posicao + "Invalida");}
        if (posicao==0) {
            removeInicio();
        }else if (posicao==totalElem-1) {
            removeFim();
        }else{
            No anterior = pegaNo(posicao-1);
            No actual = anterior.getProximo();
            No proximo = actual.getProximo();
            anterior.setProximo(proximo);
            proximo.setAnterior(anterior);
            actual=null;
            totalElem--;
        }

    }

    @Override
    public void removeFim() {
        if (eVazia()){throw new NullPointerException("Estrutura vazia");}
        if (totalElem==1){
            primeiro=null;
            ultimo=null;

        }else{
            No penultimo = ultimo.getAnterior();
            penultimo.setProximo(null);
            ultimo=penultimo;

        }
        totalElem--;
    }

    @Override
    public boolean contem(Object elemento) {
        No actual = primeiro;
        while(actual!=null){
            if (actual.getElemento().equals(elemento)) {return true;}
            actual= actual.getProximo();
        }
        return false;
    }

    @Override
    public int tamanho() {
        return totalElem;
    }

    //metodo complementar
    private No pegaNo(int posicao){
        if (!posicaoOcupada(posicao)) {
            throw new IndexOutOfBoundsException("Posição "+ posicao + " Invalida");
        }
        int meio = totalElem/2;
        No actual;
        if (posicao<=meio){
            actual=primeiro;
            for (int i = 0; i < posicao; i++) {
                actual =actual.getProximo();
            }
        }else{
            actual=ultimo;
            for (int i=totalElem-1;i>posicao;i--){
                actual=actual.getAnterior();
            }
        }
        return actual;
    }
    public void imprimir() {

        No actual = primeiro;
        for(int i = 0; i < this.totalElem; i++) {
            System.out.println(actual.getElemento());
            actual = actual.getProximo();
        }

    }

    private boolean posicaoValida(int posicao) {
        return posicao<=totalElem && posicao>=0;
    }
    private boolean posicaoOcupada(int posicao) {
        return posicao<totalElem && posicao>=0;
    }
    private boolean eVazia() {
        return totalElem==0;
    }

}
