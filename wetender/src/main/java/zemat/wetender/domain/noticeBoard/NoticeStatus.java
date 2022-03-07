package zemat.wetender.domain.noticeBoard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;

public class NoticeStatus {
    public static final String ALL = "전체공지";
    public static final String PART = "부분공지";


    public static String[] values() {
        return new String[] {ALL, PART};
    }
}
