package com.faultaddr.coffeebeggerserver.form;

import com.faultaddr.coffeebeggerserver.entity.MUserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinGameForm {
    private MUserEntity userBean;
    private String gameId;

    public boolean checkForm() {
        if (gameId == null || gameId.isEmpty()) {
            return false;
        } else {
            return userBean != null && !userBean.getAvatar().isEmpty();
        }
    }
}
