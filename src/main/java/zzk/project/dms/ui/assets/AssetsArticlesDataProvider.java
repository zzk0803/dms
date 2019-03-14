package zzk.project.dms.ui.assets;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.BackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import org.springframework.stereotype.Component;
import zzk.project.dms.domain.dao.AssetsArticleRepository;
import zzk.project.dms.domain.entities.AssetsArticle;

import java.util.stream.Stream;

@Component
public class AssetsArticlesDataProvider extends AbstractBackEndDataProvider<AssetsArticle,Void> {

    private AssetsArticleRepository assetsArticleRepository;

    @Override
    protected Stream<AssetsArticle> fetchFromBackEnd(Query<AssetsArticle, Void> query) {
        return assetsArticleRepository.findAll().stream().skip(query.getOffset()).limit(query.getLimit());
    }

    @Override
    protected int sizeInBackEnd(Query<AssetsArticle, Void> query) {
        return (int) assetsArticleRepository.count();
    }
}
