package org.realityfn.fortnite.core.models.cloudstorage;

public class ConfigTransport {
    public String Name;
    public String Type;
    public String AppName = "Fortnite";
    public boolean IsEnabled;
    public boolean IsRequired;
    public boolean IsPrimary;
    public int TimeoutSeconds = 30;
    public int Priority;

    public ConfigTransport(String name, String type, boolean isEnabled, int priority) {
        Name = name;
        Type = type;
        IsEnabled = isEnabled;
        IsRequired = isEnabled;
        IsPrimary = isEnabled;
        Priority = priority;
    }
}
