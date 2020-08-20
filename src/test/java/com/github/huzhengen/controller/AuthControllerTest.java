package com.github.huzhengen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.huzhengen.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AuthControllerTest {
    private MockMvc mvc;
    @Mock
    private UserService userService;
    @Mock
    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new AuthController(userService, authenticationManager)).build();
    }

    @Test
    void returnNotLoginByDefault() throws Exception {
        mvc.perform(get("/auth")).andExpect(status().isOk()).andExpect(mvcResult -> {
            System.out.println(mvcResult.getResponse().getContentAsString());
            Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains("用户没有登录"));
        });
    }

    @Test
    void testLogin() throws Exception {
        mvc.perform(get("/auth")).andExpect(status().isOk()).andExpect(mvcResult -> {
            Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains("用户没有登录"));
        });

        Map<String, String> usernamePassword = new HashMap<>();
        usernamePassword.put("username", "user");
        usernamePassword.put("password", "pass");

        Mockito.when(userService.loadUserByUsername("user"))
                .thenReturn(new User("user", bCryptPasswordEncoder.encode("pass"), Collections.emptyList()));
        Mockito.when(userService.getUserByUsername("user"))
                .thenReturn(new com.github.huzhengen.entity.User(123, "user", bCryptPasswordEncoder.encode("pass")));


        MvcResult response = mvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(usernamePassword)))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains("登陆成功")))
                .andReturn();

        HttpSession session = response.getRequest().getSession();

        mvc.perform(get("/auth").session((MockHttpSession) session)).andExpect(status().isOk()).andExpect(mvcResult -> {
            //            System.out.println(mvcResult.getResponse().getContentAsString());
            Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains("user"));
        });
    }
}