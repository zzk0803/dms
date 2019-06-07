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
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import zzk.project.dms.domain.DormitoryManageException;
import zzk.project.dms.domain.entities.Tenement;
import zzk.project.dms.domain.entities.TenementGender;
import zzk.project.dms.domain.services.DormitorySpaceService;
import zzk.project.dms.domain.services.ExcelService;
import zzk.project.dms.domain.services.TenementService;
import zzk.project.dms.domain.utilies.Dormitories;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

@Configuration
public class BeanConfigurationForTenementView {

    @Autowired
    private TenementService tenementService;

    @Autowired
    private DormitorySpaceService dormitorySpaceService;

    @Autowired
    private TenementBackendDataProvider tenementBackendDataProvider;

    @Autowired
    private ExcelService excelService;

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
    public Upload upload() {
        MemoryBuffer memoryBuffer = new MemoryBuffer();
        Upload upload = new Upload(memoryBuffer);
        final Button button = new Button("批量导入");
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        upload.setDropAllowed(false);
        upload.setAutoUpload(true);
        upload.setUploadButton(button);
        upload.setAcceptedFileTypes("application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        upload.setMaxFileSize(1024 * 1024 * 5);
        upload.addFinishedListener(event -> {
            MemoryBuffer receiver = (MemoryBuffer) event.getUpload().getReceiver();
            try (InputStream inputStream = receiver.getInputStream()) {
                final List<Tenement> tenements = excelService.parseExcelFileToTenementList(inputStream, event.getMIMEType());
                tenementService.saveAll(tenements);
            } catch (IOException e) {
                throw new DormitoryManageException(e);
            }
            event.getSource().getUI().ifPresent(ui -> {
                ui.getPage().reload();
            });
        });
        return upload;
    }

    @Bean
    @UIScope
    public Grid<Tenement> tenementGrid() {
        Grid<Tenement> tenementGrid = new Grid<>();
        tenementGrid.addColumn(Tenement::getName)
                .setHeader("姓名")
                .setWidth("4em")
                .setFlexGrow(1)
                .setResizable(true);
        tenementGrid.addColumn(tenement -> tenement.getGender().getCn())
                .setHeader("性别")
                .setFlexGrow(1)
                .setResizable(true);
        tenementGrid.addColumn(Tenement::getPersonIdentityID)
                .setHeader("身份证号码")
                .setWidth("5em")
                .setFlexGrow(1)
                .setResizable(true);
        tenementGrid.addColumn(tenement -> tenement.getTenementContactMethod().getMobilePhone())
                .setHeader("手机号")
                .setWidth("5em")
                .setFlexGrow(1)
                .setResizable(true);
        tenementGrid.addColumn(tenement -> Dormitories.getFullName(tenement.getDormitorySpace()))
                .setHeader("寝室信息")
                .setWidth("20em")
                .setFlexGrow(1)
                .setResizable(true);
        tenementGrid.addColumn(Tenement::getStartDate)
                .setHeader("入住日期")
                .setFlexGrow(1)
                .setResizable(true);
        tenementGrid.addColumn(Tenement::getExpiredDate)
                .setHeader("到期日期")
                .setFlexGrow(1)
                .setResizable(true);
        tenementGrid.addComponentColumn(tenement -> {
            HorizontalLayout group = new HorizontalLayout();
            group.setAlignItems(FlexComponent.Alignment.BASELINE);

            //编辑
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

            //删除
            Button delete = new Button(VaadinIcon.CLOSE_CIRCLE.create(), click -> {
                Tenement delTenement = tenementService.delete(tenement);
                tenementGrid.getParent().ifPresent(component -> {
                    if (component instanceof TenementView) {
                        tenementGrid.getDataProvider().refreshItem(delTenement);
                        tenementGrid.getDataProvider().refreshAll();
                        tenementGrid.getDataCommunicator().reset();
                    }
                });
            });
            delete.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
            group.add(delete);
            return group;
        }).setHeader("可用操作").setFlexGrow(1).setResizable(true);
        tenementGrid.addContextMenu().addItem("刷新", click -> click.getGrid().getDataCommunicator().reset());
        tenementGrid.setDataProvider(tenementBackendDataProvider);
        return tenementGrid;
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
    public Checkbox tenementDistributeCurrently() {
        return new Checkbox("显示房间分配");
    }

    @Bean
    @UIScope
    public TextField tenementNameField() {
        return new TextField("姓名");
    }

    @Bean
    @UIScope
    public Select<TenementGender> tenementGenderSelect() {
        Select<TenementGender> genderSelect = new Select<>();
        genderSelect.setLabel("性别");
        genderSelect.setItems(TenementGender.values());
        genderSelect.setRenderer(new ComponentRenderer<>(gender -> {
            Button button = new Button(gender.getCn());
            button.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            return button;
        }));
        genderSelect.setItemLabelGenerator(TenementGender::getCn);
        return genderSelect;
    }

    @Bean
    @UIScope
    public TextField tenementPersonIdentityIDField() {
        return new TextField("身份证号码");
    }

    @Bean
    @UIScope
    public TextField tenementHouseTelephoneField() {
        return new TextField("座机号码");
    }

    @Bean
    @UIScope
    public TextField tenementMobilePhone() {
        return new TextField("手机号码");
    }

    @Bean
    @UIScope
    public EmailField tenementPrimaryEmailField() {
        return new EmailField("电子邮箱");
    }

    @Bean
    @UIScope
    public Label tenementBerthLabel() {
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
    public DatePicker tenementDatePicker() {
        DatePicker datePicker = new DatePicker();
        datePicker.setLocale(Locale.CHINA);
//        datePicker.setI18n(datePickerI18n());
        return datePicker;
    }

    @Bean
    @UIScope
    public Checkbox tenementValidCheckbox() {
        return new Checkbox("有效");
    }


    //--------------------------------------------------------------------------------
    //------------                       住户表单对话框                          -----------------
    //--------------------------------------------------------------------------------

    @Bean
    @UIScope
    public H4 tenementDialogHeader() {
        return new H4("编辑&新建住户");
    }

    @Bean
    @UIScope
    public Button tenementOkButton() {
        Button button = new Button("提交");
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return button;
    }

    @Bean
    @UIScope
    public Button tenementCancelButton() {
        Button cancel = new Button("放弃并返回");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        cancel.getStyle().set("color", "red");
        return cancel;
    }
}
