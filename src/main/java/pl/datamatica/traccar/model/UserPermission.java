/*
 *  Copyright (C) 2016  Datamatica (dev@datamatica.pl)
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published
 *  by the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 * 
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package pl.datamatica.traccar.model;

import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author ŁŁ
 */
public enum UserPermission {
    DEVICE_EDIT, DEVICE_SHARE,
    GEOFENCE_READ, GEOFENCE_EDIT, GEOFENCE_SHARE,
    TRACK_READ, TRACK_EDIT, TRACK_SHARE,
    HISTORY_READ, COMMAND_TCP, COMMAND_SMS, COMMAND_CUSTOM,
    DEVICE_STATS, REPORTS, ALERTS_READ, NOTIFICATIONS,
    DEVICE_GROUP_MANAGEMENT, 
    ALL_DEVICES, ALL_GEOFENCES, ALL_TRACKS, ALL_USERS,
    USER_MANAGEMENT, USER_GROUP_MANAGEMENT, RESOURCE_MANAGEMENT,
    LOGS_ACCESS, AUDIT_ACCESS, SERVER_MANAGEMENT,
    ALLOW_MOBILE;
    
    public static Set<UserPermission> getAdminsPermissions() {
        return EnumSet.of(DEVICE_EDIT, DEVICE_SHARE, HISTORY_READ, 
                COMMAND_TCP, COMMAND_SMS, DEVICE_STATS, 
                REPORTS, ALERTS_READ, DEVICE_GROUP_MANAGEMENT, 
                ALL_DEVICES, GEOFENCE_READ, GEOFENCE_EDIT, 
                GEOFENCE_SHARE, ALL_GEOFENCES, TRACK_READ, 
                TRACK_EDIT, TRACK_SHARE, ALL_TRACKS, USER_MANAGEMENT, 
                ALL_USERS, NOTIFICATIONS, USER_GROUP_MANAGEMENT, RESOURCE_MANAGEMENT, 
                LOGS_ACCESS, AUDIT_ACCESS, SERVER_MANAGEMENT, ALLOW_MOBILE, COMMAND_CUSTOM);
    }

    public static Set<UserPermission> getUsersPermissions() {
        return EnumSet.of(DEVICE_EDIT, DEVICE_SHARE, DEVICE_GROUP_MANAGEMENT, 
                GEOFENCE_READ, GEOFENCE_EDIT, GEOFENCE_SHARE, HISTORY_READ, 
                COMMAND_TCP, COMMAND_SMS, DEVICE_STATS, REPORTS, ALERTS_READ, 
                NOTIFICATIONS, USER_MANAGEMENT, ALLOW_MOBILE);
    }
    
    private static Map<UserPermission, Set<UserPermission>> required;
    private static Map<UserPermission, Set<UserPermission>> requiring;
    
    private static void prepareRequirementsForPermissions() {
            
        required = new EnumMap<>(UserPermission.class);
        requiring = new EnumMap<>(UserPermission.class);

        required.put(UserPermission.DEVICE_EDIT, Collections.EMPTY_SET);
        required.put(UserPermission.DEVICE_SHARE, EnumSet.of(UserPermission.USER_MANAGEMENT));
        required.put(UserPermission.GEOFENCE_READ, Collections.EMPTY_SET);
        required.put(UserPermission.GEOFENCE_EDIT, EnumSet.of(UserPermission.GEOFENCE_READ));
        required.put(UserPermission.GEOFENCE_SHARE, EnumSet.of(UserPermission.GEOFENCE_READ, UserPermission.USER_MANAGEMENT));
        required.put(UserPermission.TRACK_READ, EnumSet.of(UserPermission.GEOFENCE_READ));
        required.put(UserPermission.TRACK_EDIT, EnumSet.of(UserPermission.GEOFENCE_READ, UserPermission.GEOFENCE_EDIT, UserPermission.TRACK_READ));
        required.put(UserPermission.TRACK_SHARE, EnumSet.of(UserPermission.GEOFENCE_READ, UserPermission.TRACK_READ, UserPermission.GEOFENCE_SHARE));
        required.put(UserPermission.HISTORY_READ, Collections.EMPTY_SET);
        required.put(UserPermission.COMMAND_TCP, Collections.EMPTY_SET);
        required.put(UserPermission.COMMAND_SMS, Collections.EMPTY_SET);
        required.put(UserPermission.COMMAND_CUSTOM, EnumSet.of(UserPermission.COMMAND_TCP));
        required.put(UserPermission.DEVICE_STATS, EnumSet.of(UserPermission.HISTORY_READ));
        required.put(UserPermission.REPORTS, EnumSet.of(UserPermission.HISTORY_READ));
        required.put(UserPermission.ALERTS_READ, EnumSet.of(UserPermission.HISTORY_READ, UserPermission.GEOFENCE_READ));
        required.put(UserPermission.NOTIFICATIONS, EnumSet.of(UserPermission.HISTORY_READ, UserPermission.GEOFENCE_READ, UserPermission.ALERTS_READ));
        required.put(UserPermission.DEVICE_GROUP_MANAGEMENT, EnumSet.of(UserPermission.DEVICE_EDIT, UserPermission.DEVICE_SHARE, UserPermission.USER_MANAGEMENT));
        required.put(UserPermission.ALL_DEVICES, EnumSet.of(UserPermission.DEVICE_EDIT, UserPermission.DEVICE_SHARE, UserPermission.GEOFENCE_READ, UserPermission.HISTORY_READ, UserPermission.COMMAND_TCP, UserPermission.COMMAND_SMS, UserPermission.DEVICE_STATS, UserPermission.REPORTS, UserPermission.ALERTS_READ, UserPermission.DEVICE_GROUP_MANAGEMENT, UserPermission.USER_MANAGEMENT));
        required.put(UserPermission.ALL_GEOFENCES, EnumSet.of(UserPermission.GEOFENCE_READ, UserPermission.GEOFENCE_EDIT, UserPermission.GEOFENCE_SHARE, UserPermission.USER_MANAGEMENT));
        required.put(UserPermission.ALL_TRACKS, EnumSet.of(UserPermission.GEOFENCE_READ, UserPermission.GEOFENCE_EDIT, UserPermission.TRACK_READ, UserPermission.TRACK_EDIT, UserPermission.TRACK_SHARE, UserPermission.USER_MANAGEMENT));
        required.put(UserPermission.USER_MANAGEMENT, Collections.EMPTY_SET);
        required.put(UserPermission.ALL_USERS, EnumSet.of(UserPermission.USER_MANAGEMENT));
        required.put(UserPermission.USER_GROUP_MANAGEMENT, EnumSet.of(UserPermission.USER_MANAGEMENT, UserPermission.ALL_USERS, UserPermission.SERVER_MANAGEMENT));
        required.put(UserPermission.RESOURCE_MANAGEMENT, Collections.EMPTY_SET);
        required.put(UserPermission.LOGS_ACCESS, EnumSet.of(UserPermission.GEOFENCE_READ, UserPermission.TRACK_READ, UserPermission.HISTORY_READ));
        required.put(UserPermission.AUDIT_ACCESS, EnumSet.of(UserPermission.GEOFENCE_READ, UserPermission.TRACK_READ, UserPermission.HISTORY_READ, UserPermission.LOGS_ACCESS));
        required.put(UserPermission.SERVER_MANAGEMENT, Collections.EMPTY_SET);
        required.put(UserPermission.ALLOW_MOBILE, Collections.EMPTY_SET);

        for(UserPermission up : UserPermission.values()) {
            EnumSet<UserPermission> requiringSet = EnumSet.noneOf(UserPermission.class);
            for(UserPermission perm : required.keySet())
                if(required.get(perm).contains(up))
                    requiringSet.add(perm);
            requiring.put(up, requiringSet);
        }
    }
    
    // Returns map with permission that are required by map-key permissions
    public static Map<UserPermission, Set<UserPermission>> getRequiredPermissions() {
        if (required == null)
            prepareRequirementsForPermissions();
        return required;
    }
    
    // Returns map with permissions that require map-key permission.
    public static Map<UserPermission, Set<UserPermission>> getRequiringPermissions() {
        if (requiring == null)
            prepareRequirementsForPermissions();
        return requiring;
    }
}
