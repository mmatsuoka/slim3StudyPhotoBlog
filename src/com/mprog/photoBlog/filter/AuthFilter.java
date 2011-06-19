package com.mprog.photoBlog.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class AuthFilter implements Filter {

    private String[] excludes;

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        UserService userService = UserServiceFactory.getUserService();
        String thisURL = ((HttpServletRequest) request).getRequestURI();
        if (((HttpServletRequest) request).getUserPrincipal() == null) {
            if (!isExcludePath(thisURL)) {

                // × 除外したURLのアクセスの場合、ログイン画面にリダイレクトする[このコメントは間違い]
                // ○ 除外したURL以外へのアクセスの場合、ログイン画面にリダイレクトする
                ((HttpServletResponse) response).sendRedirect(userService
                  .createLoginURL(thisURL));
                return;
            }
        }
        chain.doFilter(request, response);
    }

    /**
     * リクエストされたURLが除外対象か判断する。
     * 除外対象の場合trueを返す
     * @param thisURL
     * @return
     */
    private boolean isExcludePath(String thisURL) {
        String[] excludes = this.excludes;
        for (String path : excludes) {
            // 除外対象パスの最後が「*」の場合、indexOfで含まれるか確認
            if (path.indexOf("*") == path.length() - 1) {
                if (thisURL.indexOf(path.substring(0, path.length() - 2)) >= 0) {
                    return true;
                }
            } else {
                // 上記以外は、完全一致
                if (thisURL.equals(path)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void init(FilterConfig config) throws ServletException {
        String exclude = config.getInitParameter("exclude");
        if (exclude == null) return;
        this.excludes = exclude.split(",");
    }

}
