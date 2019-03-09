package zzk.project.dms.domain.utilies;

import zzk.project.dms.domain.entities.DormitorySpace;

import java.util.LinkedList;

public class Dormitories {
    private Dormitories() {
    }

    public static String getFullName(DormitorySpace dormitorySpace) {
        LinkedList<String> dormitoryNameStuck = new LinkedList<>();
        while (dormitorySpace != null) {
            dormitoryNameStuck.push(dormitorySpace.getName());
            dormitorySpace = dormitorySpace.getUpperSpace();
        }
        return String.join("-", dormitoryNameStuck);
    }
}
