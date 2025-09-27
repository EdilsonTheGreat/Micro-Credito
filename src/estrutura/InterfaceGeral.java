package estrutura;

public interface InterfaceGeral {

        public void adcionaInicio(Object elemento);

        public void adcionaPosicao(int posicao, Object elemento);

        public void adcionaFim(Object elemento);

        public Object pega(int posicao);

        public void removeInicio();

        public void removePosicao(int posicao);

        public void removeFim();

        public boolean contem(Object elemento);

        public int tamanho();



}
