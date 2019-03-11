package zzk.project.dms.ui.assets;

import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfigurationForAssetsView {
    /**
     * AssetsView
     */

    public Tabs subviewTabs() {
        Tabs subviewTabs = new Tabs();
        subviewTabs.add(new Tab("物品管理"));
        subviewTabs.add(new Tab("资产配置"));
        subviewTabs.add(new Tab("资产配置模板"));
        subviewTabs.setOrientation(Tabs.Orientation.VERTICAL);
        return subviewTabs;
    }

    public FlexLayout subviewLayout() {
        return new FlexLayout();
    }
}
