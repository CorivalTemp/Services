package org.realityfn.common.utils;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;
import org.realityfn.common.enums.Actions;
import org.realityfn.common.exceptions.common.oauth.MissingPermissionException;
import org.realityfn.common.models.OAuthToken;
import org.realityfn.common.models.Permission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PermissionCheckerImpl
{
    UriInfo uriInfo;

    OAuthToken oAuthToken;

    static String PERMISSION_DELIMITER = ":";

    static String PERMISSION_APP_PREFIX;

    public PermissionCheckerImpl(OAuthToken credentials, UriInfo uriInfo)
    {
        this.oAuthToken = credentials;
        this.uriInfo = uriInfo;
    }

    static
    {
        if (AdminAccess.PUBLIC_APP_NAME.startsWith("org.realityfn."))
        {
            PERMISSION_APP_PREFIX = AdminAccess.PUBLIC_APP_NAME.substring("org.realityfn.".length()).split("\\.")[0];
        }
        else if (AdminAccess.PUBLIC_APP_NAME.contains("-"))
        {
            PERMISSION_APP_PREFIX = AdminAccess.PUBLIC_APP_NAME.split("-")[0];
        }
        else
        {
            PERMISSION_APP_PREFIX = AdminAccess.PUBLIC_APP_NAME;
        }
    }

    private String applyAppPrefix(String resource)
    {
        return String.format("%s:%s", PERMISSION_APP_PREFIX, resource);
    }

    private boolean isAllowedInternal(String resourceToCheck, Integer actionToCheck)
    {
        int actionValue = actionToCheck;

        List<Permission> loadedPerms = new ArrayList<>();

        List<Permission> permissions = oAuthToken.perms;

        for (Permission permission : permissions)
        {
            String resource = permission.getResource();
            int action = permission.getAction();

            if (resource.equals(resourceToCheck))
            {
                if ((action & actionValue) != 0) return true;

                if (action == Actions.NONE.getValue()) return false;
            }

            loadedPerms.add(permission);
        }

        for (Permission permission : loadedPerms)
        {
            String resource = permission.getResource();
            int action = permission.getAction();

            boolean found = true;

            String[] permissionSplit = resourceToCheck.split(":");
            String[] resourceSplit = resource.split(":");

            if (permissionSplit.length != resourceSplit.length) continue;

            for (int index = 0; index < permissionSplit.length; index++)
            {
                String part = permissionSplit[index];
                String resourcePart = resourceSplit[index];

                if (!resourcePart.equals("*") && !part.equals(resourcePart))
                {
                    found = false;
                    break;
                }
            }

            if (found)
            {
                if (actionValue == Actions.ALL.getValue())
                {
                    return action == Actions.ALL.getValue();
                }

                if ((action & actionValue) != 0)
                {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isAllowed(String resourceToCheck, Actions actionToCheck)
    {
        return isAllowedInternal(applyAppPrefix(resourceToCheck), actionToCheck.getValue());
    }

    public void validatePermissions(Permission permission)
    {
        List<String> perms = getPermissions(permission);
        List<Integer> actions = getActions(permission);

        perms.replaceAll(this::applyAppPrefix);

        validatePermissions(perms, actions);
    }

    private ArrayList<String> getPermissions(Permission perm)
    {
        return perm != null ? new ArrayList<>(Collections.singletonList(perm.getResource())) : new ArrayList<>();
    }

    private ArrayList<Integer> getActions(Permission perm)
    {
        return perm != null ? new ArrayList<>(Collections.singletonList(perm.getAction())) : new ArrayList<>();
    }

    public void validatePermissions(
            List<String> perms,
            List<Integer> actions
    ) throws MissingPermissionException
    {
        if (perms.size() != actions.size())
        {
            throw new RuntimeException("Invalid perms or actions size.");
        }

        var pathParameters = uriInfo.getPathParameters();
        var queryParameters = uriInfo.getQueryParameters();

        // set wildcards accordingly (i.e.: replaces {accountId} with the actual ID in resource)
        for (int i = 0; i < perms.size(); i++)
        {
            String permission = perms.get(i);

            String[] permSplit = permission.split(PERMISSION_DELIMITER);

            for (String part : permSplit)
            {
                if (!part.startsWith("{") || !part.endsWith("}")) continue;

                String key = part.substring(1, part.length() - 1);

                MultivaluedMap<String, String> parameters;

                if (pathParameters.containsKey(key))
                {
                    parameters = pathParameters;
                }
                else if (queryParameters.containsKey(key))
                {
                    parameters = queryParameters;
                }
                else
                {
                    permission = permission.replace(part, "MissingParameterValue");
                    continue;
                }

                List<String> keyList = parameters.get(key);

                for (String param : keyList)
                {
                    permission = permission.replace(part, param.replace(PERMISSION_DELIMITER, "%3A"));
                    break;
                }
            }

            Integer action = actions.get(i);
            if (!(Objects.equals(permission, "null") && action == Actions.NONE.getValue())) validate(permission, action, true);
        }
    }

    public boolean validate(String permission, Integer action) throws MissingPermissionException
    {
        permission = applyAppPrefix(permission);

        if (isAllowedInternal(permission, action))
        {
            return true;
        }
        else
        {
            throw new MissingPermissionException(permission, action);
        }
    }

    public boolean validate(String permission, Integer action, boolean shouldApplyPrefix) throws MissingPermissionException
    {
        if (shouldApplyPrefix)
        {
            permission = applyAppPrefix(permission);

        }

        if (isAllowedInternal(permission, action))
        {
            return true;
        }
        else
        {
            throw new MissingPermissionException(permission, action);
        }
    }
}