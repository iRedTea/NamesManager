package me.redtea.namesmanager.repo.impl.skin;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.redtea.namesmanager.domain.Gender;
import me.redtea.namesmanager.domain.SkinDto;
import me.redtea.namesmanager.repo.YamlRepo;
import org.bukkit.plugin.Plugin;

import java.io.File;

@Singleton
public class SkinRepoImpl extends YamlRepo<String, SkinDto> implements SkinRepo {

    @Inject
    public SkinRepoImpl(Plugin plugin) {
        super(new File(plugin.getDataFolder(), "skins.yml").toPath(), plugin);
        init();
    }

    public void init() {

        var conf = initFile();

        for (String key : conf.getKeys(false)) {
            var url = conf.getString(key + ".url");
            var gender = Gender.valueOf(conf.getString(key + ".gender"));
            SkinDto skinDto = new SkinDto(key, url, gender);
            data.put(key, skinDto);
        }
    }

}
