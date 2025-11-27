package org.realityfn.fortnite.core.managers.profiles;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import net.sf.oval.internal.util.StringUtils;
import org.bson.conversions.Bson;
import org.jvnet.hk2.annotations.Service;
import org.realityfn.common.utils.database.setup.MongoDBConnection;
import org.realityfn.fortnite.core.annotations.ProfileOperation;
import org.realityfn.fortnite.core.config.BuildProperties;
import org.realityfn.fortnite.core.enums.ProfileRoute;
import org.realityfn.fortnite.core.exceptions.fortnite.OperationNotFoundException;
import org.realityfn.fortnite.core.exceptions.modules.profiles.*;
import org.realityfn.common.exceptions.PayloadValidator;
import org.realityfn.common.exceptions.common.ServerErrorException;
import org.realityfn.fortnite.core.game.commands.OperationContext;
import org.realityfn.fortnite.core.game.commands.ProfileCommand;
import org.realityfn.fortnite.core.models.profiles.MultiProfileUpdateResponse;
import org.realityfn.fortnite.core.models.profiles.Profile;
import org.realityfn.fortnite.core.models.profiles.ProfileChangeRequest;
import org.realityfn.fortnite.core.models.profiles.ProfileResponse;
import org.realityfn.fortnite.core.models.profiles.items.Template;
import org.realityfn.fortnite.core.models.profiles.notifications.Notification;
import org.realityfn.fortnite.core.services.ItemShopService;
import org.realityfn.fortnite.core.templates.TemplateConfiguration;
import org.reflections.Reflections;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.mongodb.client.model.Filters.*;

@Service
@Singleton
@SuppressWarnings("unchecked")
public class ProfileManager {
    public static final ObjectMapper MAPPER = new ObjectMapper()
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            .setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
            .setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    // Setup logger
    static MongoCollection<Profile> PROFILES;
    static Logger ProfileLogger = LoggerFactory.getLogger(ProfileManager.class);
    private static final MongoDatabase mongoDatabase;
    private static ProfileManager INSTANCE;

    private ItemShopService itemShopService;

    @Inject
    public ProfileManager(ItemShopService itemShopService) {
        this.itemShopService = itemShopService;
        INSTANCE = this;  // Set the singleton instance when DI creates it
    }

    public ItemShopService getItemShopService() {
        return itemShopService;
    }

    static HashMap<String, Class<? extends ProfileCommand>> PROFILE_OPERATIONS = new HashMap<>();
    public ProfileManager() {}

    public static ProfileManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProfileManager();
        }
        return INSTANCE;
    }

    static {
        mongoDatabase = MongoDBConnection.getDatabase();
        PROFILES = mongoDatabase.getCollection(BuildProperties.APP + "_profiles", Profile.class);
        Reflections reflections = new Reflections("org.realityfn");
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(ProfileOperation.class);
        for (Class<?> operationClass : annotatedClasses) {
            ProfileOperation operation = operationClass.getAnnotation(ProfileOperation.class);
            PROFILE_OPERATIONS.put(operation.operation(), (Class<? extends ProfileCommand>) operationClass);
        }
        ProfileLogger.info("Loaded {} operations from annotated class", PROFILE_OPERATIONS.size());
    }

    public void migrateAllProfiles() {
        ProfileLogger.info("Starting profile migration...");

        // Get all templates
        ArrayList<Template> profileTemplates = TemplateManager.findAllProfileTemplates();

        for (Template profileTemplate : profileTemplates) {
            String profileId = profileTemplate.getStat("static_profile_type");
            String targetVersion = profileTemplate.getStat("static_migration");

            if (StringUtils.isBlank(profileId)) continue;

            String initializationClassName = profileTemplate.getStat("static_initialization_class");
            TemplateConfiguration configuration = null;
            if (!StringUtils.isBlank(initializationClassName)) {
                try {
                    Class<?> initializationClass = Class.forName(initializationClassName);
                    Constructor<?> configurationConstructor = initializationClass.getConstructor();
                    configuration = (TemplateConfiguration) configurationConstructor.newInstance();
                } catch (Exception e) {
                    ProfileLogger.error("Skipping migration for '{}': Could not load configuration class '{}'", profileId, initializationClassName);
                    continue;
                }
            }

            // Only get profiles that exist BUT have the wrong version
            Bson filter = and(
                    eq("profileId", profileId),
                    ne("version", targetVersion)
            );

            long matchCount = PROFILES.countDocuments(filter);
            if (matchCount == 0) continue;

            ProfileLogger.info("Found {} profiles of type '{}' requiring migration to version '{}'", matchCount, profileId, targetVersion);

            try (var cursor = PROFILES.find(filter).iterator()) {
                int processed = 0;

                while (cursor.hasNext()) {
                    Profile profile = cursor.next();

                    assert configuration != null;
                    configuration.updateProfile(profile);

                    this.updateProfile(profile);
                    processed++;
                }
                ProfileLogger.info("Successfully migrated {} profiles for '{}'", processed, profileId);

            } catch (Exception exception) {
                ProfileLogger.error("Failed to execute profile template initialization.", exception);
                throw new RuntimeException();
            }
        }
    }

    @SuppressWarnings("all")
    public Profile getProfile(String accountId, String profileId) {
        return getProfile(accountId, TemplateManager.findProfileTemplateByType(profileId));
    }

    public Profile getProfile(String accountId, Template profileTemplate)
    {
        String profileId = profileTemplate.getStat("static_profile_type");
        if (StringUtils.isBlank(profileId))
        {
            return null;
        }

        Bson filter = and(eq("accountId", accountId), eq("profileId", profileId));
        Profile profile = PROFILES.find(filter).first();
        if (profile == null) {
            return null; // No profile exists so we should create one
        }

        String profileVersion = profileTemplate.getStat("static_migration");
        if (!profileVersion.equals(profile.getVersion())) {
            throw new ServerErrorException();
        };

        return profile;
    }

    public Profile getOrCreateProfile(String accountId, String profileId)
    {
        Template profileTemplate = Objects.requireNonNull(TemplateManager.findProfileTemplateByType(profileId));

        Profile profile = getProfile(accountId, profileTemplate);

        if (profile == null)
        {
            profile = createProfile(accountId, profileTemplate);
        }

        return profile;
    }

    public boolean deleteProfile(String accountId, String profileId)
    {
        DeleteResult deleteResult = PROFILES.deleteOne(and(eq("accountId", accountId), eq("profileId", profileId)));

        return deleteResult.wasAcknowledged() && deleteResult.getDeletedCount() != 0;
    }

    public boolean deleteAllProfiles(String accountId)
    {
        DeleteResult deleteResult = PROFILES.deleteOne(and(eq("accountId", accountId)));

        return deleteResult.wasAcknowledged() && deleteResult.getDeletedCount() != 0;
    }

    public void updateProfile(Profile profile)
    {
        profile.setUpdated(Instant.now().truncatedTo(ChronoUnit.MILLIS).toString());

        UpdateResult updateResult = PROFILES.replaceOne(and(
                eq("accountId", profile.getAccountId()),
                eq("profileId", profile.getProfileId())), profile);

        if (updateResult.wasAcknowledged()) {
            updateResult.getModifiedCount();
        }
    }

    @SuppressWarnings("unchecked")
    public Profile createProfile(String accountId, Template profileTemplate)
    {
        String profileType = profileTemplate.getStat("static_profile_type");
        String migration = profileTemplate.getStat("static_migration");

        Profile profile = new Profile(profileType, accountId);

        profile.setVersion(migration);

        String initializationClassName = profileTemplate.getStat("static_initialization_class");
        if (!StringUtils.isBlank((initializationClassName)))
        {
            try
            {
                Class<? extends TemplateConfiguration> initializationClass
                        = (Class<? extends TemplateConfiguration>) Class.forName(initializationClassName);

                Constructor<?> configurationConstructor = initializationClass.getConstructor();

                TemplateConfiguration configuration = (TemplateConfiguration) configurationConstructor.newInstance();

                configuration.initializeProfile(profile, profileTemplate);
            }
            catch (Exception exception)
            {
                ProfileLogger.error("Failed to execute profile template initialization.", exception);
                throw new RuntimeException();
                //throw new InvalidTemplateConfigurationException("profile", profileTemplate.getTemplateId());
            }
        }

        PROFILES.insertOne(profile);

        return profile;
    }

    public ProfileResponse runProfileCommand(
            ProfileRoute route,
            String accountId,
            String profileId,
            int rvn,
            String command,
            byte[] body) {
        Class<? extends ProfileCommand> commandClass = PROFILE_OPERATIONS.get(command);
        if (commandClass == null) {
            throw new OperationNotFoundException(command);
        }

        ProfileOperation operationAnnotation = commandClass.getAnnotation(ProfileOperation.class);
        if (operationAnnotation == null) {
            throw new OperationForbiddenException("Operation {} is not properly set up.", command);
        }

        List<ProfileRoute> allowedRoutes = Arrays.asList(operationAnnotation.routes());
        if (!allowedRoutes.contains(route) && !route.equals(ProfileRoute.ALL)) {
            throw new OperationForbiddenException("Operation {} not allowed via this route", command);
        }

        Template profileTemplate = TemplateManager.findProfileTemplateByType(profileId);
        if (profileTemplate == null) {
            throw new OperationForbiddenException("Unable to find template configuration for profile {}", profileId);
        }

        List<String> allowedProfiles = Arrays.asList(operationAnnotation.profiles());
        if (!allowedProfiles.isEmpty() && !allowedProfiles.contains(profileId)) {
            throw new InvalidCommandException(command, profileTemplate.getTemplateId(), profileId);
        }

        ProfileCommand commandInstance = createCommandInstance(commandClass, body);

        boolean isFullProfileUpdate = false;

        Profile primaryProfile = getProfile(accountId, profileTemplate);
        if (primaryProfile == null) {
            if (!operationAnnotation.nonAllowCreate() || operationAnnotation.readOnly()) {
                throw new ProfileNotFoundException(accountId, profileId);
            }

            primaryProfile = createProfile(accountId, profileTemplate);
            isFullProfileUpdate = true;
        }

        ProfileLogger.info("Invoking operation {}:{}[rvn={}] for profile {} on user {}", route.toString().toLowerCase(), command, rvn, profileId, accountId);

        OperationContext context = new OperationContext(accountId, primaryProfile);
        List<MultiProfileUpdateResponse> multiUpdateList = new ArrayList<>();
        List<Notification> notificationList = new ArrayList<>();
        ProfileResponse profileResponse = new ProfileResponse(primaryProfile, new ArrayList<>(), notificationList, multiUpdateList);

        commandInstance.invokeProfileCommand(context);

        for (Profile modifiedProfile : context.getModifiedProfiles()) {
            // Skip primary profile handled after the loop
            if (modifiedProfile.getProfileId().equals(profileId)) {
                continue;
            }

            List<ProfileChangeRequest> changes = modifiedProfile.getChanges();
            if (changes.isEmpty()) {
                continue;
            }

            modifiedProfile.updateRevision();
            if (!operationAnnotation.bypassesProfileLock()) {
                modifiedProfile.updateCommandRevision();
            }
            updateProfile(modifiedProfile);

            MultiProfileUpdateResponse secondaryUpdate = MultiProfileUpdateResponse.builder()
                    .profileId(modifiedProfile.getProfileId())
                    .profileRevision(modifiedProfile.getRevision())
                    .profileCommandRevision(modifiedProfile.getCommandRevision())
                    .profileChangesBaseRevision(modifiedProfile.getRevision() - 1)
                    .profileChanges(changes)
                    .build();
            multiUpdateList.add(secondaryUpdate);
        }

        List<ProfileChangeRequest> primaryChanges = primaryProfile.getChanges();

        // Write-lock check for the primary profile
        if (primaryProfile.getProfileLockExpiration() != null) {
            Instant lockExpiration = Instant.parse(primaryProfile.getProfileLockExpiration());
            if (lockExpiration.isBefore(Instant.now())) {
                primaryProfile.setProfileLockCode(null);
                primaryProfile.setProfileLockExpiration(null);
            } else if (!primaryChanges.isEmpty() && !operationAnnotation.bypassesProfileLock()) {
                throw new ProfileWriteLockedException(accountId, profileId);
            }
        }

        // Process the primary profile changees
        if (!primaryChanges.isEmpty()) {
            primaryProfile.updateRevision();
            if (!operationAnnotation.bypassesProfileLock()) {
                primaryProfile.setCommandRevision(primaryProfile.getRevision());
            }
            updateProfile(primaryProfile);
        }

        int profileChangesBaseRevision = primaryChanges.isEmpty()
                ? primaryProfile.getRevision() : primaryProfile.getRevision() - 1;

        if (rvn == -1 || profileChangesBaseRevision != rvn || operationAnnotation.forceFullProfileUpdate()) {
            isFullProfileUpdate = true;
        }

        profileResponse.setNotifications(context.getNotifications());

        if (isFullProfileUpdate) {
            profileResponse.resetChangeRequests();
            profileResponse.addChangeRequest(new ProfileChangeRequest.FullUpdateRequest(primaryProfile));
        } else {
            profileResponse.getProfileChanges().addAll(primaryChanges);
        }

        profileResponse.setProfileRevision(primaryProfile.getRevision());
        profileResponse.setProfileCommandRevision(primaryProfile.getRevision());
        profileResponse.setProfileChangesBaseRevision(profileChangesBaseRevision);

        if (profileResponse.getMultiUpdate() == null || profileResponse.getMultiUpdate().isEmpty()) profileResponse.setMultiUpdate(null);

        if (profileResponse.getNotifications() == null || profileResponse.getNotifications().isEmpty()) profileResponse.setNotifications(null);

        return profileResponse;
    }

    private ProfileCommand createCommandInstance(Class<? extends ProfileCommand> command, byte[] body)
    {
        ProfileCommand commandInstance;

        try
        {
            commandInstance = MAPPER.readValue(body, command);
        }
        catch (Exception exception)
        {
            throw new InvalidPayloadException(command.getName(), exception.getMessage());
        }

        if (commandInstance != null)
        {
            PayloadValidator.validate(commandInstance, true);
        }
        else
        {
            throw new InvalidPayloadException(command.toString(), "Failed to create command instance from request body.");
        }

        return commandInstance;
    }
}
