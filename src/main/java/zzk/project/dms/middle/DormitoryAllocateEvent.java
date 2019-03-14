package zzk.project.dms.middle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import zzk.project.dms.domain.entities.DormitorySpace;

@Getter
@AllArgsConstructor
public class DormitoryAllocateEvent {
    private DormitorySpace parent;
    private int allocation;
}
