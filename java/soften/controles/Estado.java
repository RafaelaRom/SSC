package soften.controles;

import soften.entidades.Estado;
import soften.services.ServiceGenerico;
import soften.services.impl.ServiceGenericoImpl;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped 
public class EstadoMB {

    private List<Estados> estados;
    private ServiceGenerico<Estados> estadoService;

    @PostConstruct
    private void init() {
        estadoService = new ServiceGenericoImpl<>(Estados.class);
        onAtualizar();
    }

    public void onAtualizar() { 
        estados = estadoService.findAll();
    }

    public List<Estados> getEstados() {
        return estados;
    }

    public void setEstados(List<Estados> estados) {
        this.estados = estados;
    }

}
