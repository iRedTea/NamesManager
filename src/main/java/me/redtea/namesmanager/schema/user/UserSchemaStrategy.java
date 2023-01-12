package me.redtea.namesmanager.schema.user;

import me.redtea.namesmanager.domain.UserDto;
import me.redtea.namesmanager.schema.SchemaStrategy;

import java.util.UUID;

public interface UserSchemaStrategy extends SchemaStrategy<UUID, UserDto> {
}
