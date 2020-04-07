package ni.org.jug.hr.attendance.model;

import java.time.LocalDate;

public final class Dates {

    private Dates() {
    }

    public static int toInt(LocalDate date) {
        int resp = date.getYear()*100;
        resp = (resp + date.getMonthValue())*100;
        resp += date.getDayOfMonth();
        return resp;
    }
}
