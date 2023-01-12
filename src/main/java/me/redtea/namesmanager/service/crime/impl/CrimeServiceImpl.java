package me.redtea.namesmanager.service.crime.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import me.redtea.namesmanager.domain.CrimeDto;
import me.redtea.namesmanager.domain.UserDto;
import me.redtea.namesmanager.repo.impl.crime.CrimeRepo;
import me.redtea.namesmanager.repo.impl.user.UserRepo;
import me.redtea.namesmanager.service.crime.CrimeService;

import java.util.UUID;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class CrimeServiceImpl implements CrimeService {
    private final CrimeRepo repo;

    private final UserRepo userRepo;

    @Override
    public CrimeDto crimeByKills(int kills) {
        var found = repo.findByKills(kills);
        if (found.isPresent()) return found.get();

        int d = -1;
        CrimeDto result = null;
        for (CrimeDto crimeDto : repo.all()) {
            if (d < kills - crimeDto.getKills() || d == -1) {
                result = crimeDto;
                d = kills - crimeDto.getKills();
            }
        }
        return result == null ? repo.defaultCrime() : result;
    }

    @Override
    public UserDto increase(UUID uuid) {
        UserDto userDto = userRepo.get(uuid).orElseThrow();
        userDto.setKills(userDto.getKills() + 1);
        return userDto;
    }
}
