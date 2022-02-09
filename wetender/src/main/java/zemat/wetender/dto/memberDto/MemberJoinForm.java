package zemat.wetender.dto.memberDto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class MemberJoinForm {
    private String id;
    private String pwd1;
    private String pwd2;
    private String name;
    private String email;
    private String address;
    private String phone;

    public MemberJoinForm(String id, String pwd1, String pwd2, String name, String email, String address, String phone) {
        this.id = id;
        this.pwd1 = pwd1;
        this.pwd2 = pwd2;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }
}
