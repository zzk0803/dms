package zzk.project.dms.domain.services;

import zzk.project.dms.domain.entities.AssetsAllocation;
import zzk.project.dms.domain.entities.AssetsAllocationTemplate;
import zzk.project.dms.domain.entities.AssetsArticle;
import zzk.project.dms.domain.entities.DormitorySpace;

import java.util.List;

public interface AssetsService {
    AssetsAllocationTemplate putAssetsAllocationTemplate(AssetsAllocationTemplate assetsAllocationTemplate);

    AssetsAllocationTemplate findAssetsAllocationTemplateByName(String templateName);

    AssetsAllocationTemplate deleteAssetsAllocationTemplate(AssetsAllocationTemplate assetsAllocationTemplate);

    List<AssetsAllocationTemplate> findAssetsAllocationTemplate();

    AssetsAllocation putAssetsAllocation(AssetsAllocation assetsAllocation);

    AssetsAllocation deleteAssetsAllocation(AssetsAllocation assetsAllocation);

    List<AssetsAllocation> findAssetsAllocationByDormitorySpace(DormitorySpace dormitorySpace);

    AssetsArticle putAssetsArticle(AssetsArticle assetsArticle);

    AssetsArticle deleteAssetsArticle(AssetsArticle assetsArticle);

    List<AssetsArticle> findAssetsArticle(AssetsArticle assetsArticle);
}
