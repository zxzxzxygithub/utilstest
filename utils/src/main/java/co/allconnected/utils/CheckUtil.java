
package co.allconnected.utils;

import android.app.Dialog;
import android.text.TextUtils;
import android.widget.PopupWindow;

import java.util.List;

/**
 * 【各种异常检查】
 *
 * @author zhengyx 15-9-23
 */
public class CheckUtil {

    private CheckUtil() {
    }

    /**
     * 检查数组下标是否有效
     *
     * @return
     */
    public static boolean checArrayLength(Object[] array, int position) {
        if (array != null && position >= 0 && position < array.length) return true;
        return false;
    }

    /**
     * 检查list是否有效
     *
     * @param list
     * @return
     */
    public static boolean checkList(List list) {
        if (list != null && list.size() > 0) return true;
        return false;
    }

    /**
     * 检查分母有效性
     *
     * @param d
     * @return
     */
    public static boolean checkDenominator(Double d) {
        if (d != 0) return true;
        return false;
    }

    /**
     * dismiss前检查对话框
     *
     * @param dialog
     * @return
     */
    public static boolean checkDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) return true;
        return false;
    }

    /**
     * dismiss前检查popupwindow
     *
     * @param dialog
     * @return
     */
    public static boolean checkPopupwindow(PopupWindow dialog) {
        if (dialog != null && dialog.isShowing()) return true;
        return false;
    }

    /**
     * 检查字符长度有效性
     *
     * @param text
     * @param index
     * @return
     */

    public static boolean checkStringlength(String text, int index) {
        if (!TextUtils.isEmpty(text) && index > 0 && index <= text.length()) return true;
        return false;
    }

}