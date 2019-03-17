package zzk.project.dms.domain.services;

public interface SummarizeService {
    int countCommunities();

    int countBuildings();

    int countRooms();

    int countBerths();

    int countResident();

    int countPerson();

    int countCurrentMouthBills();

    int countCurrentMouthBills(boolean mark);

    double amountUnmarkCheckIn();
}
