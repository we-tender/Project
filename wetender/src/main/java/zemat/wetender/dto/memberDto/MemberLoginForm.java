package zemat.wetender.dto.memberDto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberLoginForm {

    @NotBlank
    private String idname;

    @NotBlank
    private String pwd;

    public MemberLoginForm(String idname, String pwd) {
        this.idname = idname;
        this.pwd = pwd;
    }
}
