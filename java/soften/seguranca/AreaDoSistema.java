package soften.seguranca;

public enum AreaDoSistema {

    CADASTROS_USUARIOS("Cadastro Usuarios");

    private String label;

    private AreaDoSistema(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }

}
