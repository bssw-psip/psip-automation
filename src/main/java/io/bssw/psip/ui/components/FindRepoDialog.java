package io.bssw.psip.ui.components;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import io.bssw.psip.backend.service.RepositoryProviderManager;

public class FindRepoDialog extends Dialog {
    public FindRepoDialog(RepositoryProviderManager repositoryManager) {
        super();
        
        setHeaderTitle("Find Repository");

        // VerticalLayout dialogLayout = createDialogLayout();
        // add(dialogLayout);
        TextField searchString = new TextField("");
        add(searchString);
        // Button saveButton = new Button("Find", e -> repositoryManager.getRepositoryProvider().findRepositories(searchString.getValue()));
        Button cancelButton = new Button("Cancel", e -> close());
        getFooter().add(cancelButton);
        // getFooter().add(saveButton);
    }
}
