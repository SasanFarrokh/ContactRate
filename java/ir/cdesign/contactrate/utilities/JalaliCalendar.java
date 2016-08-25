package ir.cdesign.contactrate.utilities;

/**
 * Created by Sasan on 2016-08-25.
 */
public class JalaliCalendar {
    public static int[] gregorian_to_jalali(int gy, int gm, int gd) {
        int[] g_d_m = new int[]{0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};
        int jy = (gy <= 1600) ? 0 : 979;
        gy -= (gy <= 1600) ? 621 : 1600;
        int gy2 = (gm > 2) ? (gy + 1) : gy;
        int days = (365 * gy) + ((int) ((gy2 + 3) / 4)) - ((int) ((gy2 + 99) / 100))
                + ((int) ((gy2 + 399) / 400)) - 80 + gd + g_d_m[gm - 1];
        jy += 33 * ((int) (days / 12053));
        days %= 12053;
        jy += 4 * ((int) (days / 1461));
        days %= 1461;
        jy += (int) ((days - 1) / 365);
        if (days > 365) days = (days - 1) % 365;
        int jm = (days < 186) ? 1 + (int) (days / 31) : 7 + (int) ((days - 186) / 30);
        int jd = 1 + ((days < 186) ? (days % 31) : ((days - 186) % 30));
        return new int[]{jy, jm, jd};
    }

    public static int[] jalali_to_gregorian(int jy, int jm, int jd) {

        int gy = (jy <= 979) ? 621 : 1600;
        jy -= (jy <= 979) ? 0 : 979;
        int days = (365 * jy) + (((int) (jy / 33)) * 8) + ((int) (((jy % 33) + 3) / 4))
                + 78 + jd + ((jm < 7) ? (jm - 1) * 31 : ((jm - 7) * 30) + 186);
        gy += 400 * ((int) (days / 146097));
        days %= 146097;
        if (days > 36524) {
            gy += 100 * ((int) (--days / 36524));
            days %= 36524;
            if (days >= 365) days++;
        }
        gy += 4 * ((int) ((days) / 1461));
        days %= 1461;
        gy += (int) ((days - 1) / 365);
        if (days > 365) days = (days - 1) % 365;
        int gd = days + 1;

        int[] array = new int[]{0, 31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        array[2] = ((gy % 4 == 0 && gy % 100 != 0) || (gy % 400 == 0)) ? 29 : 28;

        int gm = 0;
        for (int v : array) {
            gm++;
            if (gd <= v) break;
            gd -= v;
        }
        return new int[]{gy, gm, gd};
    }
}
