package com.offcn.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.offcn.pojo.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * 验证用户名密码正确后，生成一个token，并将token返回给客户端
 * 该类继承自UsernamePasswordAuthenticationFilter，重写了其中的2个方法
 * attemptAuthentication ：接收并解析用户凭证。
 * successfulAuthentication ：用户成功登录后，这个方法会被调用，我们在这个方法里生成token。
 *
 */
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTLoginFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    //接收并解析用户凭证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response){
        try {
            User user = new ObjectMapper()
                        .readValue(request.getInputStream(),User.class);
            return authenticationManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(
                                                user.getUsername(),
                                                user.getPassword(),
                                                new ArrayList<>()
                                        )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 用户成功登录后，这个方法会被调用，我们在这个方法里生成token
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication){
        String token = Jwts.builder().setSubject(
                ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername()
        ).setExpiration(
                new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000)
        ).signWith(SignatureAlgorithm.HS512, "MyJwtSecret").compact();
        response.addHeader("Authorization","Bearer"+token);
    }
}
