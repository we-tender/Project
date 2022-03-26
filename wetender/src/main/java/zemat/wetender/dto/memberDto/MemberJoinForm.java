package zemat.wetender.dto.memberDto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberJoinForm {

    @NotBlank
    private String idname;

    @NotBlank
    private String pwd1;

    @NotBlank
    private String pwd2;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String address;

    @NotBlank
    private String phone;

    public MemberJoinForm(String idname, String pwd1, String pwd2, String name, String email, String address, String phone) {
        this.idname = idname;
        this.pwd1 = pwd1;
        this.pwd2 = pwd2;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }
}
