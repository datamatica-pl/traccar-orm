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
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="users_devices_statuses")
public class UserDeviceStatus implements Serializable {

    public UserDeviceStatus() {
    }
    
    public UserDeviceStatus(IdClass id) {
        this.id = id;
    }
    
    @EmbeddedId
    private IdClass id;
    
    public User getUser() {
        return id.user;
    }
    
    public void setUser(User user) {
        id.user = user;
    }
    
    public Device getDevice() {
        return id.device;
    }
    
    public void setDevice(Device device) {
        id.device = device;
    }
    
    
    @Column(name="unread_alarms")
    private boolean unreadAlarms;
    
    public boolean hasUnreadAlarms() {
        return unreadAlarms;
    }
    
    public void setUnreadAlarms(boolean value) {
        unreadAlarms = value;
    }
    
    
    @Column(name="last_check")
    private Date lastCheck;
    
    public Date getLastCheck() {
        return lastCheck;
    }
    
    public void setLastCheck(Date value) {
        lastCheck = value;
    }
    
    
    @Embeddable
    public static class IdClass implements Serializable{
        @ManyToOne
        @JoinColumn(name="users_id")
        private User user;
        
        @ManyToOne
        @JoinColumn(name="devices_id")
        private Device device;
        
        public IdClass() {
        }
        
        public IdClass(User user, Device device) {
            this.user = user;
            this.device = device;
        }
    }
}
