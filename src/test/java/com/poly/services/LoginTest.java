package com.poly.services;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.FileOutputStream;
import java.io.OutputStream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {
//    thiên về test integration
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoginSuccessWithRoleAdmin() throws Exception {
        mockMvc.perform(formLogin("/logon").user("admin").password("123"))
                .andExpect(authenticated()) // Kiểm tra đã đăng nhập thành công
                .andExpect(redirectedUrl("/admin/")); // Chuyển hướng đến trang home
    }
    @Test
    public void testLoginSuccessWithRoleUser() throws Exception {
        mockMvc.perform(formLogin("/logon").user("user").password("123"))
                .andExpect(authenticated()) // Kiểm tra đã đăng nhập thành công
                .andExpect(redirectedUrl("/public/shop")); // Chuyển hướng đến trang home
    }
    @Test
    public void testLoginFailWithRoleUserWrongUsername() throws Exception{
        mockMvc.perform(formLogin("/logon").user("user1").password("123"))
                .andExpect(unauthenticated());
    }
    @Test
    public void testLoginFailWithRoleUserWrongUsernameAndPassword() throws Exception{
        mockMvc.perform(formLogin("/logon").user("user1").password("1234"))
                .andExpect(unauthenticated());
    }
    @Test
    public void testLoginFailWithRoleUserWrongPassword() throws Exception{
        mockMvc.perform(formLogin("/logon").user("user").password("1234"))
                .andExpect(unauthenticated());
    }


}
