package com.gustavonascimento.stock.factories;

import com.gustavonascimento.stock.entities.User;

public class UserFactory {

    public static User createUser() {
        User user = new User();
        user.setName("Capitão América");
        user.setEmail("capitão@vingadores.com");
        user.setPassword("123456");

        return user;
    }


}