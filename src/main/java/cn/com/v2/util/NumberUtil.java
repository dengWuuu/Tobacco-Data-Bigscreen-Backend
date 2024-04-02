package cn.com.v2.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @author Wu
 * @date 2024年04月02日 16:10
 */
public class NumberUtil {
    public static double halfUp(double x) {
        DecimalFormat dFormat = new DecimalFormat();
        dFormat.setMaximumFractionDigits(4);
        dFormat.setGroupingSize(0);
        dFormat.setRoundingMode(RoundingMode.FLOOR);
        String str = dFormat.format(x);

        double v = Double.parseDouble(str);
        v *= 100;
        dFormat.setMaximumFractionDigits(2);
        str = dFormat.format(v);
        v = Double.parseDouble(str);
        return v;
    }
}
