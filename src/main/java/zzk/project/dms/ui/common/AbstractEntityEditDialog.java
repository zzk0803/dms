package zzk.project.dms.ui.common;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class AbstractEntityEditDialog<T> extends Dialog {
    private VerticalLayout rootLayout = new VerticalLayout();

    private H4 dialogHeader;
    private AbstractEntityEditForm<T> entityEditForm;
    private Button okButton;
    private Button cancelButton;

    private AbstractEntityEditDialog() {
    }

    public AbstractEntityEditDialog(
            H4 dialogHeader,
            AbstractEntityEditForm<T> entityEditForm,
            Button okButton,
            Button cancelButton) {
        this.dialogHeader = dialogHeader;
        this.entityEditForm = entityEditForm;
        this.okButton = okButton;
        this.cancelButton = cancelButton;
        init();
    }

    private void init() {
        rootLayout.add(dialogHeader);
        rootLayout.add(entityEditForm);
        HorizontalLayout buttonGroup = new HorizontalLayout(okButton,cancelButton);
        buttonGroup.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        rootLayout.add(buttonGroup);
        rootLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        add(rootLayout);
        onEvent();
    }

    private void onEvent() {
        okButton.addClickListener(click -> {
            UI.getCurrent().access(() -> {
                onCommit(entityEditForm);
                if (entityEditForm.isCommitSuccess()) {
                    close();
                }
            });
        });

        cancelButton.addClickListener(click -> {
            UI.getCurrent().access(() -> {
                onAbort(entityEditForm);
                close();
            });
        });
    }

    public abstract <T> void onCommit(AbstractEntityEditForm<T> form);

    public abstract <T> void onAbort(AbstractEntityEditForm<T> form);
}
