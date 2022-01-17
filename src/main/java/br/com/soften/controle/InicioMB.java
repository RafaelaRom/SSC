/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.soften.controle;

import br.com.soften.exception.DaoException;
import br.com.soften.entidades.Cidades;
import br.com.soften.entidades.Estados;
import br.com.soften.services.ServiceGenerico;
import br.com.soften.services.impl.ServiceGenericoImpl;
import br.com.soften.util.FacesMensagensUtil;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.Reader;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * 
 *
 * @author Renata Rafa
 */
@Named
@ViewScoped
public class InicioMB implements Serializable {

    @Inject
    private EstadoMB estadoMB;

    private ServiceGenerico<Estados> serviceEstados;
    private ServiceGenerico<Cidades> serviceCidades;

    private Integer progress = null;

    private int atual;

    @PostConstruct
    private void init() {
        serviceEstados = new ServiceGenericoImpl<>(Estados.class);
        serviceCidades = new ServiceGenericoImpl<>(Cidades.class);

    }

    private void updateProgress(Integer total, Integer atual) {

        atual = (atual * 100) / total;
        if (atual > 100) {
            atual = 100;
        }

        this.progress = atual;
    }

    public void onImportarEstados() {
        if (serviceEstados.countLinhas(null, null) <= 0) {
            importarEstados();
            estadoMB.onAtualizar(); 
        } else {
            FacesMensagensUtil.adcionarMensagem("Os estados já foram importados");
        }

        this.progress = 100;

    }

    public void onImportarCidades() {

        if (serviceEstados.countLinhas(null, null) <= 0) {
            FacesMensagensUtil.adcionarMensagemErro("Os estados ainda não foram importados");
            return;
        }

        if (serviceCidades.countLinhas(null, null) <= 0) {
            importarCidades();
        } else {
            FacesMensagensUtil.adcionarMensagem("As cidades já foram importadas");
        }

        this.progress = 100;

    }

    private void importarEstados() {

        try {
            String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("WEB-INF/banco/");

            Reader readerEstados = Files.newBufferedReader(Paths.get(realPath + "/states.csv"));
            CSVReader csvReaderEstados = new CSVReaderBuilder(readerEstados).build();
            List<String[]> estados = csvReaderEstados.readAll();
            atual = 0;
            estados.forEach(estado -> {

                try {
                    serviceEstados.onSalvar(new Estados(Long.parseLong(estado[0]), estado[1]), false);
                    atual++;
                    updateProgress(estados.size(), atual);

                } catch (DaoException ex) {
                    Logger.getLogger(InicioMB.class.getName()).log(Level.SEVERE, null, ex);
                }

            });

            FacesMensagensUtil.adcionarMensagem("Estados cadastrados com sucesso!");

        } catch (Exception ex) {
            FacesMensagensUtil.adcionarMensagemErro("Ocorreu um erro ao importar os estados!");
        }

    }

    private void importarCidades() {

        try {
            String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("WEB-INF/banco/");

            Reader readerCidades = Files.newBufferedReader(Paths.get(realPath + "/cities.csv"));
            CSVReader csvReaderCidades = new CSVReaderBuilder(readerCidades).build();
            List<String[]> cidades = csvReaderCidades.readAll();
            atual = 0;
            List<Cidades> cidadesObject = new ArrayList<>();
            for (String[] cidade : cidades) {
                atual++;
                updateProgress(cidades.size(), atual);
                cidadesObject.add(new Cidades(Long.parseLong(cidade[0]), cidade[1], new Estados(Long.parseLong(cidade[2]))));
                if (cidadesObject.size() == 100) {
                    serviceCidades.onSalvar(cidadesObject);
                    cidadesObject.clear();
                }
            }

            if (!cidadesObject.isEmpty()) {
                serviceCidades.onSalvar(cidadesObject); 
            }

            FacesMensagensUtil.adcionarMensagem("Cidades cadastradas com sucesso!");
        } catch (Exception ex) {
            FacesMensagensUtil.adcionarMensagemErro("Ocorreu um erro ao importar as cidades!");
        }

    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

}
