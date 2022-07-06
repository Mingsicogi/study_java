package minssogi.study.design_pattern.template_method.bad.impl;

import minssogi.study.design_pattern.template_method.bad.NaverLoginService;
import minssogi.study.design_pattern.template_method.bad.User;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static minssogi.study.design_pattern.template_method.bad.DummyData.NAVER_USER_DB_DUMMY_DATA;

@Service
public class NaverLoginServiceImpl implements NaverLoginService {


    @Override
    public User login(String requestId, String requestPassword) {
        User requestUserInfo = getUser(requestId);
        return authenticate(requestUserInfo, requestPassword);
    }

    /**
     *  get user info from  DB
     *
     * @param id
     * @return
     */
    private User getUser(String id) {
        User userInfo = NAVER_USER_DB_DUMMY_DATA.get(id);
        if (userInfo == null) {
            throw new NoSuchElementException("존재하지 않는 ID 입니다.");
        } else {
            return userInfo;
        }
    }

    /**
     * authenticate request id/pw
     *
     * @return
     */
    private User authenticate(User requestUserInfo, String requestPassword) {
        if (requestPassword.equals(requestUserInfo.getPassword())) {
            return requestUserInfo;
        } else {
            throw new IllegalArgumentException("password가 일치하지 않습니다.");
        }
    }
}
