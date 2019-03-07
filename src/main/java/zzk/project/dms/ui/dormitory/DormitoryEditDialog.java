package zzk.project.dms.ui.dormitory;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import zzk.project.dms.domain.services.DormitoryManagementService;

public class DormitoryEditDialog extends Dialog {
    private VerticalLayout root = new VerticalLayout();
    private DormitoryEditForm dormitoryEditForm;

    private Button commitButton;
    private Button giveUpButton;

    public DormitoryEditDialog(
            DormitoryEditForm dormitoryEditForm,
            Button commitButton,
            Button giveUpButton
    ) {
        this.dormitoryEditForm = dormitoryEditForm;
        this.commitButton = commitButton;
        this.giveUpButton = giveUpButton;
        init();
    }

    private void init() {
        this.setCloseOnOutsideClick(true);
        this.setCloseOnEsc(false);

        H4 headerH4 = new H4("新建&编辑宿舍空间");
        root.add(headerH4);
        root.add(dormitoryEditForm);
        root.add(new HorizontalLayout(commitButton, giveUpButton));

        addComponentAsFirst(root);
        onEvent();
    }

    private void onEvent() {
        commitButton.addClickListener(close -> {
            dormitoryEditForm.doPersistence();
            dormitoryEditForm.reset();
            close();
        });

        giveUpButton.addClickListener(close -> {
            dormitoryEditForm.reset();
            close();
        });
    }


}
