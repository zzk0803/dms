package zzk.project.dms.ui.tenement;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import zzk.project.dms.domain.entities.PersonGender;
import zzk.project.dms.domain.entities.Tenement;
import zzk.project.dms.domain.services.TenementService;
import zzk.project.dms.domain.utilies.Dormitories;

import java.util.Locale;

@Configuration
public class BeanConfigurationForTenementView {

    @Autowired
    private TenementService tenementService;

    @Autowired
    private TenementBackendDataProvider tenementBackendDataProvider;

    //--------------------------------------------------------------------------------
    //------------                       住户管理页面                             -----------------
    //--------------------------------------------------------------------------------

    @Bean
    @UIScope
    public H1 h1header() {
        return new H1(TenementView.VIEW_TITLE);
    }

    @Bean
    @UIScope
    public Button createButton() {
        Button button = new Button("创建住户");
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return button;
    }

    @Bean
    @UIScope
    public Grid<Tenement> tenementGrid() {
        Grid<Tenement> tenementGrid = new Grid<>();
        tenementGrid.setSelectionMode(Grid.SelectionMode.NONE);
        tenementGrid.addColumn(Tenement::getName).setHeader("姓名").setWidth("4em");
        tenementGrid.addColumn(tenement -> tenement.getGender().getCn()).setHeader("性别").setFlexGrow(0);
        tenementGrid.addColumn(Tenement::getPersonIdentityID).setHeader("身份证号码").setWidth("5em");
        tenementGrid.addColumn(tenement -> tenement.getPersonContactMethod().getMobilePhone()).setHeader("手机号").setWidth("5em");
        tenementGrid.addColumn(tenement -> Dormitories.getFullName(tenement.getSpot())).setHeader("寝室信息").setWidth("20em");
        tenementGrid.addColumn(Tenement::getStartDate).setHeader("入住日期").setFlexGrow(1);
        tenementGrid.addColumn(Tenement::getExpiredDate).setHeader("到期日期").setFlexGrow(1);
        tenementGrid.addComponentColumn(tenement -> {
            HorizontalLayout group = new HorizontalLayout();
            group.setAlignItems(FlexComponent.Alignment.BASELINE);
            Button edit = new Button(VaadinIcon.EDIT.create(), click -> {
                tenementGrid.getParent().ifPresent(component -> {
                    if (component instanceof TenementView) {
                        TenementView tenementView = (TenementView) component;
                        tenementView.getTenementEditDialog().warp(tenement);
                        tenementView.getTenementEditDialog().open();
                    }
                });
            });
            edit.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_SMALL);
            group.add(edit);
            Button delete = new Button(VaadinIcon.CLOSE_CIRCLE.create(), click -> {

            });
            delete.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
            group.add(delete);
            return group;
        }).setHeader("可用操作");
        tenementGrid.addContextMenu().addItem("刷新", click -> click.getGrid().getDataCommunicator().reset());
        tenementGrid.setDataProvider(tenementBackendDataProvider);
        return tenementGrid;
    }

    @Bean
    @UIScope
    public TenementEditDialog tenementEditDialog() {
        return new TenementEditDialog(
                dialogHeader(),
                tenementEditForm(),
                okButton(),
                cancelButton()
        );
    }

    //--------------------------------------------------------------------------------
    //------------                       住户表单页面                             -----------------
    //--------------------------------------------------------------------------------

    @Bean
    @UIScope
    public Binder<Tenement> tenementBinder() {
        return new Binder<>();
    }

    @Bean
    @UIScope
    public Checkbox distributeCurrently() {
        return new Checkbox("显示房间分配");
    }

    @Bean
    @UIScope
    public TextField nameField() {
        return new TextField("姓名");
    }

    @Bean
    @UIScope
    public Select<PersonGender> genderSelect() {
        Select<PersonGender> genderSelect = new Select<>();
        genderSelect.setLabel("性别");
        genderSelect.setItems(PersonGender.values());
        genderSelect.setRenderer(new ComponentRenderer<>(gender -> {
            Button button = new Button(gender.getCn());
            button.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            return button;
        }));
        genderSelect.setItemLabelGenerator(PersonGender::getCn);
        return genderSelect;
    }

    @Bean
    @UIScope
    public TextField personIdentityIDField() {
        return new TextField("身份证号码");
    }

    @Bean
    @UIScope
    public TextField houseTelephoneField() {
        return new TextField("座机号码");
    }

    @Bean
    @UIScope
    public TextField mobilePhone() {
        return new TextField("手机号码");
    }

    @Bean
    @UIScope
    public EmailField primaryEmailField() {
        return new EmailField("电子邮箱");
    }

    @Bean
    @UIScope
    public Label berthLabel() {
        return new Label();
    }

//    @Bean
//    @UIScope
//    public DatePicker.DatePickerI18n datePickerI18n() {
//        return new DatePicker.DatePickerI18n()
//                .setCalendar("月历")
//                .setCancel("取消")
//                .setClear("清除")
//                .setFirstDayOfWeek(7)
//                .setMonthNames(
//                        Arrays.asList(
//                                "一月",
//                                "二月",
//                                "三月",
//                                "四月",
//                                "五月",
//                                "六月",
//                                "七月",
//                                "八月",
//                                "九月",
//                                "十月",
//                                "十一月",
//                                "十二月"
//                        )
//                )
//                .setToday("今日")
//                .setWeek("周")
//                .setWeekdays(
//                        Arrays.asList(
//                                "星期一",
//                                "星期二",
//                                "星期三",
//                                "星期四",
//                                "星期五",
//                                "星期六",
//                                "星期天"
//                        )
//                )
//                .setWeekdaysShort(
//                        Arrays.asList(
//                                "周一",
//                                "周二",
//                                "周三",
//                                "周四",
//                                "周五",
//                                "周六",
//                                "周日"
//                        )
//                );
//    }

    @Bean
    @Scope("prototype")
    public DatePicker datePicker() {
        DatePicker datePicker = new DatePicker();
        datePicker.setLocale(Locale.CHINA);
//        datePicker.setI18n(datePickerI18n());
        return datePicker;
    }

    @Bean
    @UIScope
    public Checkbox validCheckbox() {
        return new Checkbox("有效");
    }


    //--------------------------------------------------------------------------------
    //------------                       住户表单对话框                          -----------------
    //--------------------------------------------------------------------------------

    @Bean
    @UIScope
    public H4 dialogHeader() {
        return new H4("编辑&新建住户");
    }

    @Bean
    @UIScope
    public TenementEditForm tenementEditForm() {
        return new TenementEditForm(
                tenementBinder(),
                tenementService,
                distributeCurrently(),
                nameField(),
                genderSelect(),
                personIdentityIDField(),
                houseTelephoneField(),
                mobilePhone(),
                primaryEmailField(),
                berthLabel(),
                datePicker(),
                datePicker(),
                validCheckbox()
        );
    }

    @Bean
    @UIScope
    public Button okButton() {
        Button button = new Button("提交");
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return button;
    }

    @Bean
    @UIScope
    public Button cancelButton() {
        Button cancel = new Button("放弃并返回");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        cancel.getStyle().set("color", "red");
        return cancel;
    }
}
