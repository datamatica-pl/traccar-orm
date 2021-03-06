package pl.datamatica.traccar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gwt.user.client.rpc.GwtTransient;
import com.google.gwt.user.client.rpc.IsSerializable;

import javax.persistence.*;

@Entity
@Table(name="application_settings")
public class ApplicationSettings implements IsSerializable {

    private static final long serialVersionUID = 1;
    public static final short DEFAULT_UPDATE_INTERVAL = 15000;
    public static final short DEFAULT_NOTIFICATION_EXPIRATION_PERIOD = 12 * 60;
    public static final String DEFAULT_MATCH_SERVICE_URL = "https://router.project-osrm.org/match";
    public static final int DEFAULT_ICON_ID = 12;
    public static final int DEFAULT_FREE_HISTORY = 14;

    public static final short UPDATE_INTERVAL_MIN = 1000;
    public static final short UPDATE_INTERVAL_MAX = 30000;

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    @JsonIgnore
    private long id;

    public ApplicationSettings() {
        registrationEnabled = true;
        updateInterval = DEFAULT_UPDATE_INTERVAL;
        defaultPasswordHash = PasswordHashMethod.MD5;
        eventRecordingEnabled = true;
        language = "default";
        notificationExpirationPeriod = DEFAULT_NOTIFICATION_EXPIRATION_PERIOD;
        matchServiceURL = DEFAULT_MATCH_SERVICE_URL;
        defaultIconId = DEFAULT_ICON_ID;
        freeHistory = DEFAULT_FREE_HISTORY;
    }

    private boolean registrationEnabled;

    private Short updateInterval;

    @Enumerated(EnumType.STRING)
    private PasswordHashMethod defaultPasswordHash;

    @Deprecated
    @Column(nullable = true)
    private boolean disallowDeviceManagementByUsers;

    @Column(nullable = true)
    @JsonIgnore
    private boolean eventRecordingEnabled;

    @Column(nullable = true)
    private int notificationExpirationPeriod;

    public long getId() {
        return id;
    }
    
    public void setRegistrationEnabled(boolean registrationEnabled) {
        this.registrationEnabled = registrationEnabled;
    }

    public boolean getRegistrationEnabled() {
        return registrationEnabled;
    }

    public Short getUpdateInterval() {
        return updateInterval;
    }

    public void setUpdateInterval(Short updateInterval) {
        this.updateInterval = updateInterval;
    }

    public boolean isDisallowDeviceManagementByUsers() {
        return disallowDeviceManagementByUsers;
    }

    public void setDisallowDeviceManagementByUsers(boolean disallowDeviceManagementByUsers) {
        this.disallowDeviceManagementByUsers = disallowDeviceManagementByUsers;
    }

    public PasswordHashMethod getDefaultHashImplementation() {
        return defaultPasswordHash;
    }

    public void setDefaultHashImplementation(PasswordHashMethod hash) {
        this.defaultPasswordHash = hash;
    }

    public boolean isEventRecordingEnabled() {
        return eventRecordingEnabled;
    }

    public void setEventRecordingEnabled(boolean eventRecordingEnabled) {
        this.eventRecordingEnabled = eventRecordingEnabled;
    }

    @JsonIgnore
    private String language;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @JsonIgnore
    private String salt;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @JsonIgnore
    private String bingMapsKey;

    public String getBingMapsKey() {
        return bingMapsKey;
    }

    public void setBingMapsKey(String bingMapsKey) {
        this.bingMapsKey = bingMapsKey;
    }

    public int getNotificationExpirationPeriod() {
        return notificationExpirationPeriod;
    }

    public void setNotificationExpirationPeriod(int notificationExpirationPeriod) {
        this.notificationExpirationPeriod = notificationExpirationPeriod;
    }

    private String matchServiceURL;

    public String getMatchServiceURL() {
        return matchServiceURL;
    }

    public void setMatchServiceURL(String matchServiceURL) {
        this.matchServiceURL = matchServiceURL;
    }

    @Deprecated
    @Column(nullable = true)
    private boolean allowCommandsOnlyForAdmins;

    public boolean isAllowCommandsOnlyForAdmins() {
        return allowCommandsOnlyForAdmins;
    }

    public void setAllowCommandsOnlyForAdmins(boolean allowCommandsOnlyForAdmins) {
        this.allowCommandsOnlyForAdmins = allowCommandsOnlyForAdmins;
    }
    
    @GwtTransient
    @ManyToOne
    private UserGroup defaultGroup;
    
    public UserGroup getDefaultGroup() {
        return defaultGroup;
    }
    
    public void setDefaultGroup(UserGroup group) {
        defaultGroup = group;
        if(group != null)
            defaultGroupId = group.getId();
    }
    
    @Transient
    private long defaultGroupId;
    
    public long getDefaultGroupId() {
        return defaultGroupId;
    }
    
    public void setDefaultGroupId(long id) {
        this.defaultGroupId = id;
    }
    
    private int defaultIconId;
    
    public int getDefaultIconId() {
        return defaultIconId;
    }
    
    public void setDefaultIconId(int id) {
        this.defaultIconId = id;
    }
    
    @Column(nullable = false)
    private int freeHistory;
    
    public int getFreeHistory() {
        return freeHistory;
    }
    
    public void setFreeHistory(int value) {
        this.freeHistory = value;
    }
    
    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ApplicationSettings)) {
            return false;
        }

        ApplicationSettings other = (ApplicationSettings) object;
        
        return this.id == other.id;
    }
}
