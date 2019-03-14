package zzk.project.dms.ui.common;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import zzk.project.dms.domain.services.FormSupportService;

import java.util.function.Supplier;

public abstract class AbstractEntityEditForm<T> extends VerticalLayout {
    private Supplier<T> blackEntitySupplier;
    private T editEntity;
    private T completedEntity;
    private Binder<T> entityBinder;
    private boolean commitSuccess;

    private AbstractEntityEditForm() {
        
    }

    public AbstractEntityEditForm(Binder<T> entityBinder) {
        this.entityBinder = entityBinder;
    }

    public VerticalLayout getRootLayout() {
        return this;
    }

    public Binder<T> getEntityBinder() {
        return entityBinder;
    }

    public T getEditEntity() {
        return editEntity;
    }

    public void setEditEntity(T editEntity) {
        this.editEntity = editEntity;
    }

    public T getCompletedEntity() {
        return completedEntity;
    }

    public void setCompletedEntity(T completedEntity) {
        this.completedEntity = completedEntity;
    }

    public boolean isCommitSuccess() {
        return commitSuccess;
    }

    public void setCommitSuccess(boolean commitSuccess) {
        this.commitSuccess = commitSuccess;
    }

    /**
     * 配置UI
     * 使用给定的layout添加自己创建的UI组件
     *
     * @param rootLayout
     */
    protected abstract void configureUI(VerticalLayout rootLayout);

    protected abstract void configureUIEvent();

    protected abstract void configureBinging(Binder<T> entityBinder);

    public abstract FormSupportService<T, ?> getFormSupportService();

    public abstract Supplier<T> getBlackEntitySupplier();

    public boolean doCommit() {
        try {
            getEntityBinder().writeBean(getEditEntity());
            setCompletedEntity(getEditEntity());
            getFormSupportService().save(getCompletedEntity());
            setCommitSuccess(true);
        } catch (ValidationException e) {
            setCommitSuccess(false);
        }
        return isCommitSuccess();
    }

    public void doAbort() {
        setEditEntity(getBlackEntitySupplier().get());
    }

    protected void configure() {
        configureUI(this);
        addAttachListener(attachEvent -> {
            getEntityBinder().readBean(getEditEntity());
        });
        configureUIEvent();
        configureBinging(this.entityBinder);
    }
}
