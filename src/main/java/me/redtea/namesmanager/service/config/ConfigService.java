package me.redtea.namesmanager.service.config;

import me.redtea.namesmanager.domain.Gender;
import me.redtea.namesmanager.reload.Reloadable;

public interface ConfigService extends Reloadable {
    String maleDisplay();

    String femaleDisplay();

    String rtpCommand();

    String banCommand();

    String genderName(Gender gender);
}
