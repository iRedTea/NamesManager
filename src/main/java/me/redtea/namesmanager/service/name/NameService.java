package me.redtea.namesmanager.service.name;

import me.redtea.namesmanager.domain.Gender;
import me.redtea.namesmanager.reload.Reloadable;

public interface NameService extends Reloadable {

    String randName(Gender gender);
}