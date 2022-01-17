package soften.componentes;

import java.io.IOException;
import javax.faces.component.FacesComponent;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import org.primefaces.component.api.UITree;
import org.primefaces.component.tree.UITreeNode;
import org.primefaces.model.TreeNode;


@FacesComponent(value = "soften.component.UIDisablePanel", namespace = "http://soften.com.br/s", tagName = "disablePanel", createTag = true)
public class UIDisablePanel extends UIComponentBase {

    private enum PropertyKeys {

        disabled,
        exclude;
    }

    public UIDisablePanel() {
        setRendererType(null);
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        boolean toDisable = isDisabled();
        processDisablePanel(this, toDisable);
        //super.encodeBegin(context);
    }

    public void processDisablePanel(UIComponent root, boolean toDisable) {
        
        for (UIComponent c : root.getChildren()) {
            if (c instanceof UIInput || c instanceof UICommand || c instanceof UITree) {
                if (toDisable) { // <x:disablePanel disabled="true">
                    
                    if(c instanceof UITree) {  
                         TreeNode node = (TreeNode)c.getAttributes().get("value");
                         percorreTreeDisable(node);
                    }
                    
                    Boolean curState = (Boolean) c.getAttributes().get("disabled");
                    Object id = c.getAttributes().get("id");

                    if (pularCampo(id)) {
                        continue;
                    }
                   
                    if (curState == null || curState == false) {
                        c.getAttributes().put("UIPanelDisableFlag", true);
                        c.getAttributes().put("disabled", true);
                    }
                } else { // <x:disablePanel disabled="false">

                    if (c.getAttributes().get("UIPanelDisableFlag") != null) {
                        c.getAttributes().remove("UIPanelDisableFlag");
                        c.getAttributes().put("disabled", false);
                    }
                }
            }

            if (c.getChildCount() > 0) {
                processDisablePanel(c, toDisable);
            }
        }

    }

    private boolean pularCampo(Object idCampo) {
        if (idCampo == null) {
            return false;
        }

        String[] ids = getExclude().split(",");
        for (String id : ids) {
            if (idCampo.toString().trim().equals(id.trim())) {
                return true;
            }
        }
        return false;
    }
    
    private void percorreTreeDisable(TreeNode noPai) {
        
        for(TreeNode child: noPai.getChildren()) {
            child.setSelectable(false);
            if(child.getChildCount() > 0 ) {
                percorreTreeDisable(child);
            }
        }
        
    }

    @Override
    public String getFamily() {
        // Got to override it but it doesn't get called.
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getExclude() {
        return (String) getStateHelper().eval(PropertyKeys.exclude, "");
    }

    public void setExclude(String exclude) {
        getStateHelper().put(PropertyKeys.exclude, exclude);
    }

    public boolean isDisabled() {
        return (boolean) getStateHelper().eval(PropertyKeys.disabled, false);
    }

    public void setDisabled(boolean disabled) {
        getStateHelper().put(PropertyKeys.disabled, disabled);
    }
}
