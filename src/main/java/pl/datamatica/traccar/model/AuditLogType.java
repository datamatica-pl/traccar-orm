/*
 *  Copyright (C) 2017  Datamatica (dev@datamatica.pl)
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

import java.io.Serializable;

/**
 *
 * @author piotrkrzeszewski
 */
public enum AuditLogType implements Serializable{
    CHANGED_USER_USERGROUP,
    CHANGED_USERGROUP_PROPERTY,
    CHANGED_USERGROUP_PERMISSION,
    REMOVED_USERGROUP,
    CREATED_USERGROUP,
    CHANGED_SERVER_SETTING,
    CREATED_USER,
    REMOVED_USER,
    CHANGED_USER;
      
}
