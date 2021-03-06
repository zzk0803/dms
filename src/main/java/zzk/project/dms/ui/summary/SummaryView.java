package zzk.project.dms.ui.summary;

import com.github.appreciated.card.Card;
import com.github.appreciated.card.content.Item;
import com.github.appreciated.card.label.TitleLabel;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import zzk.project.dms.domain.services.SummarizeService;
import zzk.project.dms.ui.MainView;

@Route(value = SummaryView.VIEW_NAME, layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
public class SummaryView extends VerticalLayout {
    public static final String VIEW_NAME = "overview";
    public static final String VIEW_TITLE = "总览";

    private SummarizeService summarizeService;

    private HorizontalLayout cardsGroup;
    private Card dormitorySummarizedCard;
    private Card tenementSummarizedCard;
    private Card financeSummarizedCard;

    public SummaryView(
            SummarizeService summarizeService
    ) {
        this.summarizeService = summarizeService;
        add(new H1(VIEW_TITLE));
        cardsGroup = new HorizontalLayout();
        cardsGroup.setWidth("100%");
        cardsGroup.setJustifyContentMode(JustifyContentMode.EVENLY);
        cardsGroup.setDefaultVerticalComponentAlignment(Alignment.STRETCH);

        dormitorySummarizedCard = new Card(
                new TitleLabel("住宿概况"),
                new Item("社区总数", String.valueOf(this.summarizeService.countCommunities())),
                new Item("建筑总数", String.valueOf(this.summarizeService.countBuildings())),
                new Item("房间总数", String.valueOf(this.summarizeService.countRooms())),
                new Item("床位总数", String.valueOf(this.summarizeService.countBerths()))
        );
        dormitorySummarizedCard.getStyle().set("color", "#FFFFFF");
        dormitorySummarizedCard.setBackground("var(--lumo-primary-color)");
        dormitorySummarizedCard.setSizeUndefined();

        tenementSummarizedCard = new Card(
                new TitleLabel("住户概况"),
                new Item("已登记注册", String.valueOf(this.summarizeService.countPerson())),
                new Item("已实际分配", String.valueOf(this.summarizeService.countResident()))
        );
        tenementSummarizedCard.getStyle().set("color", "#FFFFFF");
        tenementSummarizedCard.setBackground("var(--lumo-primary-color)");
        tenementSummarizedCard.setSizeUndefined();

        financeSummarizedCard = new Card(
                new TitleLabel("财务概况"),
                new Item("当月生成账单", String.valueOf(this.summarizeService.countCurrentMouthBills())),
                new Item("当月已交账单", String.valueOf(this.summarizeService.countCurrentMouthBills(true))),
                new Item("未交总额", String.valueOf(this.summarizeService.amountUnmarkCheckIn()))
        );
        financeSummarizedCard.getStyle().set("color", "#FFFFFF");
        financeSummarizedCard.setBackground("var(--lumo-primary-color)");
        financeSummarizedCard.setSizeUndefined();

        cardsGroup.add(dormitorySummarizedCard, tenementSummarizedCard, financeSummarizedCard);
        cardsGroup.setFlexGrow(1, dormitorySummarizedCard);
        cardsGroup.setFlexGrow(1, tenementSummarizedCard);
        cardsGroup.setFlexGrow(1, financeSummarizedCard);
        add(cardsGroup);
    }

}
