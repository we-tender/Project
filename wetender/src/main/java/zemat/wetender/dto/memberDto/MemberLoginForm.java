package zemat.wetender.dto.memberDto;

import lombok.Data;

@Data
public class MemberLoginForm {
    private String idname;
    private String pwd;

    public MemberLoginForm(String idname, String pwd) {
        this.idname = idname;
        this.pwd = pwd;
    }
}
