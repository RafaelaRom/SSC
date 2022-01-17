package soften.seguranca;

public enum Acao {

    SALVAR("Salvar"),
    EDITAR("Editar"),
    EXCLUIR("Excluir"),
    VISUALIZAR("Visualizar");

    private String label;

    private Acao(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
