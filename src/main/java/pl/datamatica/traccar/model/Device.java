/*
 * Copyright 2013 Anton Tananaev (anton.tananaev@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.datamatica.traccar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gwt.core.shared.GwtIncompatible;
import com.google.gwt.user.client.rpc.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.SQLDelete;

import com.google.gwt.user.datepicker.client.CalendarUtil;

@Entity
@Table(name = "devices",
       indexes = { @Index(name = "devices_pkey", columnList = "id") },
       uniqueConstraints = { @UniqueConstraint(name = "devices_ukey_uniqueid", columnNames = "uniqueid") })
@SQLDelete(sql="UPDATE devices d SET d.deleted = 1 WHERE d.id = ?")
@FilterDef(name="softDelete", defaultCondition="deleted = 0")
@Filter(name="softDelete")
public class Device extends TimestampedEntity implements IsSerializable, GroupedDevice {

    private static final long serialVersionUID = 1;
    public static final short DEFAULT_TIMEOUT = 5 * 60;
    public static final short DEFAULT_MIN_IDLE_TIME = 1 * 60;

    public static final String DEFAULT_MOVING_ARROW_COLOR = "00017A";
    public static final String DEFAULT_PAUSED_ARROW_COLOR = "B12222";
    public static final String DEFAULT_STOPPED_ARROW_COLOR = "016400";
    public static final String DEFAULT_OFFLINE_ARROW_COLOR = "778899";
    public static final String DEFAULT_COLOR = "0000FF";
    
    public static final double DEFAULT_ARROW_RADIUS = 5;
    public static final int DEFAULT_HISTORY_LENGTH_DAYS = 2;
    public static final int NEAR_EXPIRATION_THRESHOLD_DAYS = 7; 

    public Device() {
        iconType = DeviceIconType.DEFAULT;
        iconMode = DeviceIconMode.ICON;
        iconArrowMovingColor = DEFAULT_MOVING_ARROW_COLOR;
        iconArrowPausedColor = DEFAULT_PAUSED_ARROW_COLOR;
        iconArrowStoppedColor = DEFAULT_STOPPED_ARROW_COLOR;
        iconArrowOfflineColor = DEFAULT_OFFLINE_ARROW_COLOR;
        iconArrowRadius = DEFAULT_ARROW_RADIUS;
        color = DEFAULT_COLOR;
        deviceModelId = -1;
        showName = true;
        showProtocol = true;
        showOdometer = true;
        supportedCommands = new ArrayList<>();
        historyLength = DEFAULT_HISTORY_LENGTH_DAYS;
    }

    public Device(Device device) {
        id = device.id;
        uniqueId = device.uniqueId;
        name = device.name;
        description = device.description;
        phoneNumber = device.phoneNumber;
        plateNumber = device.plateNumber;
        vehicleInfo = device.vehicleInfo;
        timeout = device.timeout;
        idleSpeedThreshold = device.idleSpeedThreshold;
        minIdleTime = device.minIdleTime;
        speedLimit = device.speedLimit;
        iconType = device.iconType;
        icon = device.getIcon();
        photo = device.getPhoto();
        odometer = device.odometer;
        autoUpdateOdometer = device.autoUpdateOdometer;
        if (device.maintenances != null) {
            maintenances = new ArrayList<>(device.maintenances.size());
            for (Maintenance maintenance : device.maintenances) {
                maintenances.add(new Maintenance(maintenance));
            }
        }
        if (device.registrations != null) {
            registrations = new ArrayList<>(device.registrations.size());
            for(RegistrationMaintenance registration : device.registrations)
                registrations.add(new RegistrationMaintenance(registration));
        }
        if (device.sensors != null) {
            sensors = new ArrayList<>(device.sensors.size());
            for (Sensor sensor : device.sensors) {
                sensors.add(new Sensor(sensor));
            }
        }
        group = device.group == null ? null : new Group(device.group.getId()).copyFrom(device.group);

        iconMode = device.iconMode;
        iconRotation = device.iconRotation;
        iconArrowMovingColor = device.iconArrowMovingColor;
        iconArrowPausedColor = device.iconArrowPausedColor;
        iconArrowStoppedColor = device.iconArrowStoppedColor;
        iconArrowOfflineColor = device.iconArrowOfflineColor;
        iconArrowRadius = device.iconArrowRadius;
        showName = device.showName;
        showProtocol = device.showProtocol;
        showOdometer = device.showOdometer;
        timezoneOffset = device.timezoneOffset;
        commandPassword = device.commandPassword;
        supportedCommands = new ArrayList<>(device.supportedCommands);
        protocol = device.protocol;
        historyLength = device.historyLength;
        validTo = device.validTo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private long id;

    public long getId() {
        return id;
    }
    
    @GwtTransient
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "devices_fkey_position_id"))
    @JsonIgnore
    private Position latestPosition;

    public void setLatestPosition(Position latestPosition) {
        this.latestPosition = latestPosition;
    }

    public Position getLatestPosition() {
        return latestPosition;
    }

    private String uniqueId;

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Consider device offline after 'timeout' seconds spent from last position
     */
    @Column(nullable = true)
    private int timeout = DEFAULT_TIMEOUT;

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Column(nullable = true)
    private double idleSpeedThreshold;

    public double getIdleSpeedThreshold() {
        return idleSpeedThreshold;
    }

    public void setIdleSpeedThreshold(double idleSpeedThreshold) {
        this.idleSpeedThreshold = idleSpeedThreshold;
    }

    @Column(nullable = true)
    private int minIdleTime = DEFAULT_MIN_IDLE_TIME;

    public int getMinIdleTime() {
        return minIdleTime;
    }

    public void setMinIdleTime(int minIdleTime) {
        this.minIdleTime = minIdleTime;
    }

    @Column(nullable = true)
    private Double speedLimit;

    public Double getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(Double speedLimit) {
        this.speedLimit = speedLimit;
    }

    // Hibernate bug HHH-8783: (http://hibernate.atlassian.net/browse/HHH-8783)
    //     ForeignKey(name) has no effect in JoinTable (and others).  It is
    //     reported as closed but the comments indicate it is still not fixed
    //     for @JoinTable() and targeted to be fixed in 5.x :-(.
    //                          
    @GwtTransient
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_devices",
               foreignKey = @ForeignKey(name = "users_devices_fkey_devices_id"),
               joinColumns = { @JoinColumn(name = "devices_id", table = "devices", referencedColumnName = "id") },
               inverseJoinColumns = { @JoinColumn(name = "users_id", table = "users", referencedColumnName = "id") })
    @JsonIgnore
    private Set<User> users;

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @GwtTransient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "devices_fkey_owner_id"))
    @JsonIgnore
    private User owner;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Enumerated(EnumType.STRING)
    private DeviceIconType iconType;

    public DeviceIconType getIconType() {
        return iconType;
    }

    public void setIconType(DeviceIconType iconType) {
        this.iconType = iconType;
    }

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "devices_fkey_icon_id"))
    private DeviceIcon icon;

    public DeviceIcon getIcon() {
        return icon;
    }

    public void setIcon(DeviceIcon icon) {
        this.icon = icon;
    }

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "devices_fkey_photo_id"))
    @JsonIgnore
    private Picture photo;

    public Picture getPhoto() {
        return photo;
    }

    public void setPhoto(Picture photo) {
        this.photo = photo;
    }

    @JsonIgnore
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonIgnore
    private String plateNumber;

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    @JsonIgnore
    private String vehicleInfo;

    public String getVehicleInfo() {
        return vehicleInfo;
    }

    public void setVehicleInfo(String vehicleInfo) {
        this.vehicleInfo = vehicleInfo;
    }

    // contains current odometer value in kilometers
    @Column(nullable = true)
    @JsonIgnore
    private double odometer;

    public double getOdometer() {
        return odometer;
    }

    public void setOdometer(double odometer) {
        this.odometer = odometer;
    }

    // indicates that odometer must be updated automatically by positions history
    @Column(nullable = true)
    @JsonIgnore
    private boolean autoUpdateOdometer;

    public boolean isAutoUpdateOdometer() {
        return autoUpdateOdometer;
    }

    public void setAutoUpdateOdometer(boolean autoUpdateOdometer) {
        this.autoUpdateOdometer = autoUpdateOdometer;
    }

    @Transient
    private List<Maintenance> maintenances;

    public List<Maintenance> getMaintenances() {
        return maintenances;
    }

    public void setMaintenances(List<Maintenance> maintenances) {
        this.maintenances = maintenances;
    }
    
    @Transient
    private List<RegistrationMaintenance> registrations;
    
    public List<RegistrationMaintenance> getRegistrations() {
        return registrations;
    }
    
    public void setRegistrations(List<RegistrationMaintenance> registrations) {
        this.registrations = registrations;
    }

    @Transient
    private List<Sensor> sensors;

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "devices_fkey_group_id"))
    private Group group;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Column(nullable = true, length = 128)
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Enumerated(EnumType.STRING)
    private DeviceIconMode iconMode;

    public DeviceIconMode getIconMode() {
        return iconMode;
    }

    public void setIconMode(DeviceIconMode iconMode) {
        this.iconMode = iconMode;
    }

    private String iconArrowMovingColor;
    private String iconArrowPausedColor;
    private String iconArrowStoppedColor;
    private String iconArrowOfflineColor;

    public String getIconArrowMovingColor() {
        return iconArrowMovingColor;
    }

    public void setIconArrowMovingColor(String iconArrowMovingColor) {
        this.iconArrowMovingColor = iconArrowMovingColor;
    }

    public String getIconArrowPausedColor() {
        return iconArrowPausedColor;
    }

    public void setIconArrowPausedColor(String iconArrowPausedColor) {
        this.iconArrowPausedColor = iconArrowPausedColor;
    }

    public String getIconArrowStoppedColor() {
        return iconArrowStoppedColor;
    }

    public void setIconArrowStoppedColor(String iconArrowStoppedColor) {
        this.iconArrowStoppedColor = iconArrowStoppedColor;
    }

    public String getIconArrowOfflineColor() {
        return iconArrowOfflineColor;
    }

    public void setIconArrowOfflineColor(String iconArrowOfflineColor) {
        this.iconArrowOfflineColor = iconArrowOfflineColor;
    }

    @Column(nullable = true)
    private boolean iconRotation;

    public boolean isIconRotation() {
        return iconRotation;
    }

    public void setIconRotation(boolean iconRotation) {
        this.iconRotation = iconRotation;
    }

    @Column(nullable = true)
    private double iconArrowRadius;

    public double getIconArrowRadius() {
        return iconArrowRadius;
    }

    public void setIconArrowRadius(double iconArrowRadius) {
        this.iconArrowRadius = iconArrowRadius;
    }

    @Column(nullable = true)
    private boolean showName;

    public boolean isShowName() {
        return showName;
    }

    public void setShowName(boolean showName) {
        this.showName = showName;
    }

    @Column(nullable = true)
    private boolean showProtocol;
    @Column(nullable = true)
    private boolean showOdometer;

    public boolean isShowProtocol() {
        return showProtocol;
    }

    public void setShowProtocol(boolean showProtocol) {
        this.showProtocol = showProtocol;
    }

    public boolean isShowOdometer() {
        return showOdometer;
    }

    public void setShowOdometer(boolean showOdometer) {
        this.showOdometer = showOdometer;
    }
    
    @Column(nullable = true)
    private Integer timezoneOffset;
    
    public int getTimezoneOffset() {
        return timezoneOffset == null ? 0 : timezoneOffset;
    }
    
    public void setTimezoneOffset(Integer timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }
    
    @Transient
    private ArrayList<CommandType> supportedCommands;
    
    public List<CommandType> getSupportedCommands() {
        return new ArrayList(supportedCommands);
    }
    
    public void addSupportedCommand(CommandType type) {
        supportedCommands.add(type);
    }
    
    public void clearSupportedCommands() {
        supportedCommands.clear();
    }
    
    @Transient
    private String protocol;
    
    public String getProtocol() {
        return protocol;
    }
    
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    
    @Column(nullable = true)
    private String commandPassword;
    
    public String getCommandPassword() {
        return commandPassword;
    }
    
    public void setCommandPassword(String commandPassword) {
        this.commandPassword = commandPassword;
    }
    
    @Transient
    private boolean isAlarmEnabled;
    
    public boolean isAlarmEnabled() {
        return isAlarmEnabled;
    }
    
    public void setAlarmEnabled(boolean isEnabled) {
        isAlarmEnabled = isEnabled;
    }
    
    @Transient
    private boolean isIgnitionEnabled;
    
    public boolean isIgnitionEnabled() {
        return isIgnitionEnabled;
    }
    
    public void setIgnitionEnabled(boolean isIgnitionEnabled) {
        this.isIgnitionEnabled = isIgnitionEnabled;
    }
    
    @Transient
    private boolean unreadAlarms;

    public boolean hasUnreadAlarms() {
        return unreadAlarms;
    }

    public void setUnreadAlarms(boolean isIgnitionEnabled) {
        this.unreadAlarms = isIgnitionEnabled;
    }
    
    @Transient
    @JsonIgnore
    private Date lastAlarmsCheck;
    
    public Date getLastAlarmsCheck() {
        return lastAlarmsCheck;
    }
    
    public void setLastAlarmsCheck(Date date) {
        lastAlarmsCheck = date;
    }
    
    @Column(nullable=false, columnDefinition = "boolean default false")
    private boolean deleted;
    
    public boolean isDeleted() {
        return deleted;
    }
    
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    @Column(nullable=false, columnDefinition = "CHAR(6) default '0000FF'")
    private String color;
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    @Column(nullable=false, columnDefinition = "BIGINT default -1")
    private long deviceModelId;
    
    public long getDeviceModelId() {
        return deviceModelId;
    }
    
    public void setDeviceModelId(long id) {
        this.deviceModelId = id;
    }
    
    @GwtTransient
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy="device")
    private List<Position> positions = new ArrayList<>();
    
    public List<Position> getPositions() {
        return positions;
    }
    
    private Long iconId;
    
    public Long getIconId() {
        return iconId;
    }
    
    public void setIconId(Long iconId) {
        this.iconId = iconId;
    }
    
    private Long customIconId;
    
    public Long getCustomIconId() {
        return customIconId;
    }
    
    public void setCustomIconId(Long value) {
        this.customIconId = value;
    }
    
    @Temporal(TemporalType.DATE)
    private Date validTo;
    
    public Date getValidTo() {
        return validTo;
    }
    
    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }
    
    @GwtIncompatible
    public boolean isValid(Date today) {
        if(getValidTo() == null)
            return true;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            today = sdf.parse(sdf.format(today));
            return today.compareTo(getValidTo()) <= 0;
        } catch (ParseException ex) {
            Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    @GwtIncompatible
    public Date getLastAvailablePositionDate(Date today) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            today = sdf.parse(sdf.format(today));
        } catch (ParseException e) {
            Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, e);
        }
        
        int availableHistoryLength = DEFAULT_HISTORY_LENGTH_DAYS;
        if (isValid(today)) {
            availableHistoryLength = getHistoryLength();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DATE, -availableHistoryLength);
        
        return cal.getTime();
    }
    
    public int getSubscriptionDaysLeft(Date from) {
        if (validTo == null) {
            return 0;
        }
        
        int daysDiff = CalendarUtil.getDaysBetween(from, validTo);
        int daysLeft = daysDiff + 1;
        if (daysLeft < 0) {
            daysLeft = 0;
        }
        return daysLeft;
    }
    
    public boolean isCloseToExpire(Date from) {
        int daysLeft = getSubscriptionDaysLeft(from);
        return (daysLeft <= NEAR_EXPIRATION_THRESHOLD_DAYS && daysLeft > 0);
    }
    
    @Column(nullable = false, columnDefinition = "integer default " + DEFAULT_HISTORY_LENGTH_DAYS)
    private int historyLength;
    
    public int getHistoryLength() {
        return historyLength;
    }
    
    public void setHistoryLength(int historyLength) {
        this.historyLength = historyLength;
    }
    
    @GwtIncompatible
    public int getAlertsHistoryLength() {
        if(isValid(new Date()))
            return Math.min(getHistoryLength(), 7);
        return 2;
    }
    
    @Column(nullable = false, columnDefinition="bit default false")
    private boolean isBlocked;
    
    public boolean isBlocked() {
        return isBlocked;
    }
    
    public void setBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }
    
    private Integer battery;
    
    public Integer getBatteryLevel() {
        return battery;
    }
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date battTime;
    
    public Date getBatteryTime() {
        return battTime;
    }
    
    public int getBatteryTimeout() {
        return 3600;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Device)) return false;

        Device device = (Device) o;

        if (getUniqueId() != null ? !getUniqueId().equals(device.getUniqueId()) : device.getUniqueId() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return getUniqueId() != null ? getUniqueId().hashCode() : 0;
    }
}
