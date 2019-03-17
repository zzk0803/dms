package zzk.project.dms.ui.summarize;

import com.github.appreciated.card.Card;
import com.github.appreciated.card.content.Item;
import com.github.appreciated.card.label.TitleLabel;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import zzk.project.dms.domain.services.SummarizeService;
import zzk.project.dms.ui.MainView;

@Route(value = SummarizeView.VIEW_NAME, layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
public class SummarizeView extends VerticalLayout {
    public static final String VIEW_NAME = "overview";
    public static final String VIEW_TITLE = "总览";

    private SummarizeService summarizeService;

    private HorizontalLayout cardsGroup;
    private Card dormitorySummarizedCard;
    private Card tenementSummarizedCard;
    private Card financeSummarizedCard;

    public SummarizeView(
            SummarizeService summarizeService
    ) {
        this.summarizeService = summarizeService;
        buildUI();

    }

    private void buildUI() {
        add(new H1(VIEW_TITLE));
        cardsGroup = new HorizontalLayout();
        cardsGroup.setWidth("100%");
        cardsGroup.setJustifyContentMode(JustifyContentMode.EVENLY);
        cardsGroup.setDefaultVerticalComponentAlignment(Alignment.STRETCH);

        dormitorySummarizedCard = new Card(
                new TitleLabel("住宿概况"),
                new Item("社区总数", String.valueOf(summarizeService.countCommunities())),
                new Item("建筑总数", String.valueOf(summarizeService.countBuildings())),
                new Item("房间总数", String.valueOf(summarizeService.countRooms())),
                new Item("床位总数", String.valueOf(summarizeService.countBerths()))
        );
        dormitorySummarizedCard.getStyle().set("color", "#FFFFFF");
        dormitorySummarizedCard.setBackground("var(--lumo-primary-color)");
        dormitorySummarizedCard.setSizeUndefined();

        tenementSummarizedCard = new Card(
                new TitleLabel("住户概况"),
                new Item("已登记注册", String.valueOf(summarizeService.countPerson())),
                new Item("已实际分配", String.valueOf(summarizeService.countResident()))
        );
        tenementSummarizedCard.getStyle().set("color", "#FFFFFF");
        tenementSummarizedCard.setBackground("var(--lumo-primary-color)");
        tenementSummarizedCard.setSizeUndefined();

//        financeSummarizedCard = new Card(
//                new TitleLabel("财务概况"),
//                new Item("当月生成账单", "100"),
//                new Item("当月已交账单", "36"),
//                new Item("当月未交总额", "3800000")
//        );
//        financeSummarizedCard.getStyle().set("color", "#FFFFFF");
//        financeSummarizedCard.setBackground("var(--lumo-primary-color)");
//        financeSummarizedCard.setSizeUndefined();

        cardsGroup.add(dormitorySummarizedCard, tenementSummarizedCard);
//        cardsGroup.add(dormitorySummarizedCard, tenementSummarizedCard, financeSummarizedCard);
        cardsGroup.setFlexGrow(1, dormitorySummarizedCard);
        cardsGroup.setFlexGrow(1, tenementSummarizedCard);
//        cardsGroup.setFlexGrow(1, financeSummarizedCard);
        add(cardsGroup);
    }

}
