package zzk.project.dms.ui.summarize;

import com.github.appreciated.card.Card;
import com.github.appreciated.card.content.Item;
import com.github.appreciated.card.label.TitleLabel;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import zzk.project.dms.ui.MainView;

@Route(value = SummarizeView.VIEW_NAME, layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
public class SummarizeView extends VerticalLayout {
    public static final String VIEW_NAME = "overview";
    public static final String VIEW_TITLE = "总览";

    private HorizontalLayout cardsGroup;
    private Card dormitorySummarizedCard;
    private Card tenementSummarizedCard;
    private Card financeSummarizedCard;

    public SummarizeView() {
        add(new H1(VIEW_TITLE));
        cardsGroup = new HorizontalLayout();
        cardsGroup.setWidth("100%");
        cardsGroup.setJustifyContentMode(JustifyContentMode.EVENLY);
        cardsGroup.setDefaultVerticalComponentAlignment(Alignment.CENTER);

        dormitorySummarizedCard = new Card(
                new TitleLabel("住宿概况"),
                new Item("总可容纳", "10000"),
                new Item("当前已入住", "9725")
        );
        dormitorySummarizedCard.getStyle().set("color", "#FFFFFF");
        dormitorySummarizedCard.setBackground("var(--lumo-primary-color)");
        dormitorySummarizedCard.setSizeUndefined();

        tenementSummarizedCard = new Card(
                new TitleLabel("住户概况"),
                new Item("已注册", "9800"),
                new Item("已分配", "9725")
        );
        tenementSummarizedCard.getStyle().set("color", "#FFFFFF");
        tenementSummarizedCard.setBackground("var(--lumo-primary-color)");
        tenementSummarizedCard.setSizeUndefined();

        financeSummarizedCard = new Card(
                new TitleLabel("财务"),
                new Item("未缴清", "3"),
                new Item("当月累计缴费", "3800000")
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
