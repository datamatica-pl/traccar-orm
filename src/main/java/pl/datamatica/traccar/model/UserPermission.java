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

import java.util.EnumSet;
import java.util.Set;

/**
 *
 * @author ŁŁ
 */
public enum UserPermission {
    DEVICE_READ, DEVICE_EDIT, DEVICE_SHARE,
    GEOFENCE_READ, GEOFENCE_EDIT, GEOFENCE_SHARE,
    TRACK_READ, TRACK_EDIT, TRACK_SHARE,
    HISTORY_READ, COMMAND_TCP, COMMAND_SMS,
    DEVICE_STATS, REPORTS, ALERTS_READ, NOTIFICATIONS,
    DEVICE_GROUP_MANAGEMENT, 
    ALL_DEVICES, ALL_GEOFENCES, ALL_TRACKS, 
    USER_MANAGEMENT, GROUP_MANAGEMENT, RESOURCE_MANAGEMENT,
    LOGS_ACCESS, AUDIT_ACCESS, SERVER_MANAGEMENT,
    ALLOW_MOBILE, ALLOW_WEB;
    
    public static Set<UserPermission> getAdminsPermissions() {
        return EnumSet.of(NOTIFICATIONS, ALL_DEVICES, ALL_GEOFENCES,
                ALL_TRACKS, USER_MANAGEMENT, GROUP_MANAGEMENT,
                LOGS_ACCESS, AUDIT_ACCESS, SERVER_MANAGEMENT);
    }

    public static Set<UserPermission> getUsersPermissions() {
        return EnumSet.of(DEVICE_READ, DEVICE_EDIT, GEOFENCE_READ,
                GEOFENCE_EDIT, GEOFENCE_SHARE, HISTORY_READ, 
                COMMAND_TCP, COMMAND_SMS, DEVICE_STATS, ALERTS_READ,
                NOTIFICATIONS);
    }
}
