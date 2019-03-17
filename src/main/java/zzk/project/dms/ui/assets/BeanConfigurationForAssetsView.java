//package zzk.project.dms.ui.assets;
//
//import com.vaadin.flow.component.orderedlayout.FlexLayout;
//import com.vaadin.flow.component.tabs.Tab;
//import com.vaadin.flow.component.tabs.Tabs;
//import com.vaadin.flow.component.textfield.NumberField;
//import com.vaadin.flow.component.textfield.TextArea;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.data.binder.Binder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import zzk.project.dms.domain.entities.AssetsArticle;
//import zzk.project.dms.domain.services.AssetsArticleService;
//import zzk.project.dms.domain.services.AssetsService;
//
//@Configuration
//public class BeanConfigurationForAssetsView {
//
//    @Autowired
//    private AssetsArticleService assetsArticleService;
//
//    //--------------------------------------------------------------------------------
//    //------------                       资产管理页面                              -----------------
//    //--------------------------------------------------------------------------------
//
//    public TextField articleNameField() {
//        return new TextField("条目名称");
//    }
//
//    public TextArea articleDescriptionField() {
//        return new TextArea("条目简介");
//    }
//
//    public NumberField articleValuesField() {
//        return new NumberField("物价");
//    }
//
//    public AssetsArticleEditForm assetsArticleEditForm() {
//        return new AssetsArticleEditForm(
//                assetsArticleBinder(),
//                articleNameField(),
//                articleDescriptionField(),
//                articleValuesField(),
//                assetsArticleService
//        );
//    }
//
//    //--------------------------------------------------------------------------------
//    //------------                       资产表单页面                              -----------------
//    //--------------------------------------------------------------------------------
//    public Binder<AssetsArticle> assetsArticleBinder() {
//        return new Binder<>();
//    }
//
//    //--------------------------------------------------------------------------------
//    //------------                       资产表单对话框                             -----------------
//    //--------------------------------------------------------------------------------
//}
