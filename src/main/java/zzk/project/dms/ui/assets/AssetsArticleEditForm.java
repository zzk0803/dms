//package zzk.project.dms.ui.assets;
//
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.NumberField;
//import com.vaadin.flow.component.textfield.TextArea;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.data.binder.Binder;
//import com.vaadin.flow.data.binder.Setter;
//import com.vaadin.flow.data.binder.ValidationResult;
//import com.vaadin.flow.function.ValueProvider;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.data.jpa.repository.JpaRepository;
//import zzk.project.dms.domain.entities.AssetsArticle;
//import zzk.project.dms.domain.services.AssetsArticleService;
//import zzk.project.dms.domain.services.AssetsService;
//import zzk.project.dms.domain.services.common.FormSupportService;
//import zzk.project.dms.ui.common.AbstractEntityEditForm;
//
//import java.math.BigDecimal;
//import java.util.function.Supplier;
//
//public class AssetsArticleEditForm extends AbstractEntityEditForm<AssetsArticle> {
//    private TextField nameField;
//    private TextArea descriptionField;
//    private NumberField valuesField;
//
//    private AssetsArticleService assetsArticleService;
//
//    public AssetsArticleEditForm(
//            @Qualifier("assetsArticleBinder") Binder<AssetsArticle> assetsArticleBinder,
//            @Qualifier("articleNameField") TextField nameField,
//            @Qualifier("descriptionField") TextArea descriptionField,
//            @Qualifier("valuesField") NumberField valuesField,
//            @Qualifier("assetsService") AssetsArticleService assetsService
//    ) {
//        super(assetsArticleBinder);
//        this.nameField = nameField;
//        this.descriptionField = descriptionField;
//        this.valuesField = valuesField;
//        this.assetsArticleService = assetsService;
//        configure();
//    }
//
//    @Override
//    protected void configure() {
//        super.configure();
//    }
//
//    @Override
//    protected void configureUI(VerticalLayout rootLayout) {
//        nameField.setWidth("100%");
//        nameField.setRequiredIndicatorVisible(true);
//        rootLayout.add(nameField);
//
//        descriptionField.setWidth("100%");
//        descriptionField.setHeight("10em");
//        rootLayout.add(descriptionField);
//
//        valuesField.setWidth("100%");
//        rootLayout.add(valuesField);
//    }
//
//    @Override
//    protected void configureUIEvent() {
//        addAttachListener(attach -> {
//            this.getEntityBinder().readBean(getEditEntity());
//        });
//    }
//
//    @Override
//    protected void configureBinging(Binder<AssetsArticle> entityBinder) {
//        entityBinder.forField(nameField)
//                .asRequired("条目名必须非空")
//                .bind(AssetsArticle::getName, AssetsArticle::setName);
//        entityBinder.forField(descriptionField)
//                .bind(AssetsArticle::getDescription, AssetsArticle::setDescription);
//        entityBinder.forField(valuesField)
//                .asRequired("价格必填")
//                .withValidator((aDouble, valueContext) -> {
//                    if (aDouble > 0) {
//                        return ValidationResult.ok();
//                    } else {
//                        return ValidationResult.error("价格必须大于0");
//                    }
//                })
//                .bind((ValueProvider<AssetsArticle, Double>) assetsArticle -> assetsArticle.getValues().doubleValue(),
//                        (Setter<AssetsArticle, Double>) (assetsArticle, aDouble) -> assetsArticle.setValues(BigDecimal.valueOf(aDouble)));
//
//    }
//
//    @Override
//    public FormSupportService<AssetsArticle, ?> getFormSupportService() {
//        return assetsArticleService;
//    }
//
//    @Override
//    public Supplier<AssetsArticle> getBlackEntitySupplier() {
//        return null;
//    }
//}
