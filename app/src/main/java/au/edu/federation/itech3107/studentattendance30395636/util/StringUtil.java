package au.edu.federation.itech3107.studentattendance30395636.util;


public class StringUtil {
    /**
     * Determines whether the string is empty
     *
     * @param str
     * @return false:非null、非""、非"null"
     */
    public static boolean isEmpty(String str) {
        if (str != null && !"".equals(str) && !"null".equals(str)) {
            return false;
        }
        return true;
    }


}
