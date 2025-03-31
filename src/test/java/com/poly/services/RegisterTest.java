package com.poly.services;

import com.poly.models.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class RegisterTest {
    @Autowired
    private UserService userService;
    @Test
    public void testRegisterSuccess() {
User user = new User("user1","123","Tran Huu Loc",
        LocalDate.of(2005,10,9),
        "locthps39267@gmail.com","0906921242",LocalDate.now().atStartOfDay(),null,"Long An","USER",true);
        assertTrue(userService.register(user));
    }
    @Test
    public void testRegisterFail() {
        User user = new User("user","123","Tran Huu Loc",
                LocalDate.of(2005,10,9),
                "locthps39267@gmail.com","0906921242",LocalDate.now().atStartOfDay(),null,"Long An","USER",true);
        assertFalse(userService.register(user));
    }
    @Test
    public void testRegisterFailWithNullInfor() {
        User user = new User("","","",
                LocalDate.of(2005,10,9),
                "locthps39267@gmail.com","0906921242",LocalDate.now().atStartOfDay(),null,"Long An","USER",true);
        assertFalse(userService.register(user));
    }
    @Test
    public void testRegisterSuccessWith20CharacterUsername() {
        User user = new User("zxcvbnmasdfghjklqwer","Tran Huu Loc","123",
                LocalDate.of(2005,10,9),
                "locthps39267@gmail.com","0906921242",LocalDate.now().atStartOfDay(),null,"Long An","USER",true);
        assertTrue(userService.register(user));
    }
    @Test
    public void testRegisterFailWith2CharacterUsername() {
        User user = new User("tl","Tran Huu Loc","123",
                LocalDate.of(2005,10,9),
                "locthps39267@gmail.com","0906921242",LocalDate.now().atStartOfDay(),null,"Long An","USER",true);
        assertFalse(userService.register(user));
    }
    @Test
    public void testRegisterSuccessWith3CharacterUsername() {
        User user = new User("loc","Tran Huu Loc","123",
                LocalDate.of(2005,10,9),
                "locthps39267@gmail.com","0906921242",LocalDate.now().atStartOfDay(),null,"Long An","USER",true);
        assertTrue(userService.register(user));
    }
    @Test
    public void testRegisterFailWith4CharacterUsername() {
        User user = new User("tloc","Tran Huu Loc","123",
                LocalDate.of(2005,10,9),
                "locthps39267@gmail.com","0906921242",LocalDate.now().atStartOfDay(),null,"Long An","USER",true);
        assertFalse(userService.register(user));
    }
    @Test
    public void testRegisterFailWith21CharacterUsername() {
        User user = new User("zxcvbnmasdfghjklqweyu","Tran Huu Loc","123",
                LocalDate.of(2005,10,9),
                "locthps39267@gmail.com","0906921242",LocalDate.now().atStartOfDay(),null,"Long An","USER",true);
        assertFalse(userService.register(user));
    }
    @Test
    public void testRegisterSuccessWith19CharacterUsername() {
        User user = new User("zxcvbnmasdfghjklqwe","Tran Huu Loc","123",
                LocalDate.of(2005,10,9),
                "locthps39267@gmail.com","0906921242",LocalDate.now().atStartOfDay(),null,"Long An","USER",true);
        assertTrue(userService.register(user));
    }

}
