package soften.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesMensagensUtil {

    public static final String MENSAGEM_SALVO_SUCESSO = "#nome# salvo com sucesso!";
    public static final String MENSAGEM_ATUALIZADO_SUCESSO = "#nome# atualizado com sucesso!";
    public static final String MENSAGEM_EXLUIDO_SUCESSO = "#nome# excluído com sucesso!";

    public static final String MENSAGEM_ERRO_SALVAR = "Falha ao salvar #nome#! Consulte o suporte.";
    public static final String MENSAGEM_ERRO_EXCLUIR = "Falha ao excluir #nome#! Consulte o suporte.";
    public static final String MENSAGEM_ERRO_ATUALIZAR = "Falha ao atualizar #nome#! Consulte o suporte.";

    public static final String MENSAGEM_JA_CADASTRADO = "#nome# já cadastrado no sistema.";

    public static final String MENSAGEM_CNPJ_INVALIDO = "O CNPJ é inválido.";

    public static final String MENSAGEM_SEM_PERMISSAO_SALVAR = "Você não tem permissão para salvar. Consulte suas permissões de usuário!";
    public static final String MENSAGEM_SEM_PERMISSAO_VISUALIZAR = "Você não tem permissão para visualizar. Consulte suas permissões de usuário!";
    public static final String MENSAGEM_SEM_PERMISSAO_EDITAR = "Você não tem permissão para editar. Consulte suas permissões de usuário!";
    public static final String MENSAGEM_SEM_PERMISSAO_EXCLUIR = "Você não tem permissão para excluir. Consulte suas permissões de usuário!";

    public static void adicionarMensagemSalvoSucesso(String objeto, boolean update) {
        String mensagem = update ? MENSAGEM_ATUALIZADO_SUCESSO : MENSAGEM_SALVO_SUCESSO;

        objeto = primeiraLetraMaiscula(objeto);
        mensagem = mensagem.replace("#nome#", objeto);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, "");
        FacesContext contexto = FacesContext.getCurrentInstance();
        contexto.addMessage(null, msg);
    }

    public static void adicionarMensagemSalvarErro(String objeto, boolean update) {
        String mensagem = update ? MENSAGEM_ERRO_ATUALIZAR : MENSAGEM_ERRO_SALVAR;

        objeto = objeto.toLowerCase();
        mensagem = mensagem.replace("#nome#", objeto);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, "");
        FacesContext contexto = FacesContext.getCurrentInstance();
        contexto.addMessage(null, msg);
    }

    public static void adicionarMensagemExcluir(String objeto, boolean sucesso) {
        String mensagem = sucesso ? MENSAGEM_EXLUIDO_SUCESSO : MENSAGEM_ERRO_EXCLUIR;
        FacesMessage.Severity tipo = sucesso ? FacesMessage.SEVERITY_INFO : FacesMessage.SEVERITY_ERROR;
        if (sucesso) {
            objeto = primeiraLetraMaiscula(objeto);
        }

        mensagem = mensagem.replace("#nome#", objeto);
        FacesMessage msg = new FacesMessage(tipo, mensagem, "");
        FacesContext contexto = FacesContext.getCurrentInstance();
        contexto.addMessage(null, msg);
    }

    public static void adicionarMensagemSucesso(String mensagem, String objeto) {
        objeto = primeiraLetraMaiscula(objeto);
        mensagem = mensagem.replace("#nome#", objeto);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, "");
        FacesContext contexto = FacesContext.getCurrentInstance();
        contexto.addMessage(null, msg);
    }

    public static void adicionarMensagemErro(String mensagem, String objeto) {
        objeto = objeto.toLowerCase();
        mensagem = mensagem.replace("#nome#", objeto);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, "");
        FacesContext contexto = FacesContext.getCurrentInstance();
        contexto.addMessage(null, msg);

    }

    public static void adcionarMensagemSemPermissao(String mensagem) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, "");
        FacesContext contexto = FacesContext.getCurrentInstance();
        contexto.addMessage(null, msg);
    }

    public static void adcionarMensagemJaCadastrado(String objeto) {
        String mensagem = MENSAGEM_JA_CADASTRADO.replace("#nome#", primeiraLetraMaiscula(objeto));
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, "");
        FacesContext contexto = FacesContext.getCurrentInstance();
        contexto.addMessage(null, msg);
    }

    public static void adcionarMensagemCNpjInvalido() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MENSAGEM_CNPJ_INVALIDO, MENSAGEM_CNPJ_INVALIDO);
        FacesContext contexto = FacesContext.getCurrentInstance();
        contexto.addMessage(null, msg);
    }

    public static void adcionarMensagemErro(String mensagem) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, "");
        FacesContext contexto = FacesContext.getCurrentInstance();
        contexto.addMessage(null, msg);
    }

    public static void adcionarMensagem(String mensagem) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, "");
        FacesContext contexto = FacesContext.getCurrentInstance();
        contexto.addMessage(null, msg);
    }

    private static String primeiraLetraMaiscula(String texto) {
        char primeiraLetra;
        String restante;
        primeiraLetra = texto.charAt(0);
        restante = texto.substring(1, texto.length());
        StringBuilder textoFinal = new StringBuilder();

        textoFinal.append(String.valueOf(primeiraLetra).toUpperCase());
        textoFinal.append(restante.toLowerCase());

        return textoFinal.toString();
    }

}
