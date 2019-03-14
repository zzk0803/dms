package zzk.project.dms.ui.assets;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import zzk.project.dms.domain.entities.AssetsArticle;
import zzk.project.dms.ui.common.AbstractEntityEditDialog;
import zzk.project.dms.ui.common.AbstractEntityEditForm;

public class AssetsArticleEditDialog extends AbstractEntityEditDialog<AssetsArticle> {

    public AssetsArticleEditDialog(
            H4 dialogHeader,
            AbstractEntityEditForm<AssetsArticle> entityEditForm,
            Button okButton,
            Button cancelButton) {
        super(dialogHeader, entityEditForm, okButton, cancelButton);
    }

    @Override
    public void onCommit(AbstractEntityEditForm form) {

    }

    @Override
    public void onAbort(AbstractEntityEditForm form) {

    }
}
