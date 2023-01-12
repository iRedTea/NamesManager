package me.redtea.namesmanager.factory;

import me.redtea.namesmanager.domain.Gender;
import me.redtea.namesmanager.domain.UserDto;

import java.util.UUID;

public interface UserFactory {
    UserDto create(UUID uuid, String name, Gender gender, int kills);
}