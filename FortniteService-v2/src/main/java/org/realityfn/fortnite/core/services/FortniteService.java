package org.realityfn.fortnite.core.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import org.jvnet.hk2.annotations.Service;
import org.realityfn.fortnite.core.exceptions.modules.profiles.InvalidClientRevisionsHeaderException;
import org.realityfn.fortnite.core.models.Keychain;
import org.realityfn.fortnite.core.models.profiles.ProfileRevision;
import org.realityfn.fortnite.core.utils.repositories.fortnite.KeychainRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FortniteService {

    private final KeychainRepository keychainRepository;

    @Inject
    public FortniteService(KeychainRepository keychainRepository) {
        this.keychainRepository = keychainRepository;
    }

    public List<String> getKeychain() {
        List<Keychain> keychains = keychainRepository.findAll();

        return keychains.stream()
                .map(Keychain::getKey)
                .collect(Collectors.toList());
    }

    public static List<ProfileRevision> parseProfileRevisions(String json) {
        if (json == null || json.isEmpty()) {
            return Collections.emptyList();
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new InvalidClientRevisionsHeaderException();
        }
    }
}