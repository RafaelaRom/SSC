package soften.tipos;

public enum TipoUsuario {

    USUARIO_ADMINISTRADOR("Administrador"),
    USUARIO_COLABORADOR("Colaborador");

    private String label;

    private TipoUsuario(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
