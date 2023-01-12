package me.redtea.namesmanager.repo.impl.user;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.redtea.namesmanager.domain.Gender;
import me.redtea.namesmanager.domain.UserDto;
import me.redtea.namesmanager.factory.UserFactory;
import me.redtea.namesmanager.repo.cache.CacheMapRepo;
import me.redtea.namesmanager.schema.user.UserSchemaStrategy;

import java.util.UUID;

@Singleton
public class UserRepoImpl extends CacheMapRepo<UUID, UserDto> implements UserFactory, UserRepo {

    @Inject
    public UserRepoImpl(UserSchemaStrategy schemaStrategy) {
        super(schemaStrategy);
        init();

    }

    @Override
    public UserDto create(UUID uuid, String name, Gender gender, int kills) {
        var userDto = new UserDto(uuid, name, kills, gender);
        data.put(uuid, userDto);
        schemaStrategy.insert(userDto);
        toRemove.remove(uuid);
        return userDto;
    }

    @Override
    public UserDto update(UserDto userDto) {
        return update(userDto.getUuid(), userDto);
    }


    @Override
    public void init() {
        schemaStrategy.init();
    }


}
