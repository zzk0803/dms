package zzk.project.dms.middle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import zzk.project.dms.domain.entities.DormitorySpace;
import zzk.project.dms.domain.entities.Tenement;

@Getter
@AllArgsConstructor
public class TenementOccupySpaceEvent {
    private DormitorySpace berth;
    private Tenement tenement;
}
