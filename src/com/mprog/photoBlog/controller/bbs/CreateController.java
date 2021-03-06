package com.mprog.photoBlog.controller.bbs;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.google.appengine.api.users.User;


public class CreateController extends Controller {

    @Override
    public Navigation run() throws Exception {

        User user = sessionScope(SessionKeys.AUTH_USER);
        requestScope(RequestKeys.USERNAME,user.getNickname() );
        return forward("create.jsp");
    }
}
