package me.redtea.namesmanager.repo.impl.crime;

import me.redtea.namesmanager.domain.CrimeDto;
import me.redtea.namesmanager.repo.Repo;

import java.util.Optional;

public interface CrimeRepo extends Repo<String, CrimeDto> {
    Optional<CrimeDto> findByKills(int kill);

    CrimeDto defaultCrime();
}
