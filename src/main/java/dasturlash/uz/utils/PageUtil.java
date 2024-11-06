package dasturlash.uz.utils;

public class PageUtil {

    public static int getCurrentPage(int page) {
        return page > 0 ? page - 1 : 1;
    }
}
