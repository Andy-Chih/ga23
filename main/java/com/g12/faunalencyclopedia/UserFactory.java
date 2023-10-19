package com.g12.faunalencyclopedia;
/**
 * @author UID: u7532171 Name: Vaibhav Nohria
 */

public interface UserFactory {
    User createUser(String username, String email, String password);
}
