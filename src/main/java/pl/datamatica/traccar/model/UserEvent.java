/*
 *  Copyright (C) 2018  Datamatica (dev@datamatica.pl)
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

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class UserEvent {
    public static enum Type {
        INACTIVE_30D, INACTIVE_90D, INACTIVE_173D
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private long id;
    
    @Column(nullable=false)
    private boolean notificationSent = false;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date time = new Date();
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastRequestTime;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey= @ForeignKey(name= "events_fkey_user_id"))
    private User user;
    
    private Type kind;
    
    public UserEvent() {}
    
    public UserEvent(User u, Type kind, Date lastRequestTime) {
        this.user = u;
        this.kind = kind;
        this.lastRequestTime = lastRequestTime;
    }
    
    public Date getTime() {
        return time;
    }
    
    public boolean isNotificationSent() {
        return notificationSent;
    }
    
    public void setNotificationSent(boolean notificationSent) {
        this.notificationSent = notificationSent;
    }
    
    public Date getLastRequestTime() {
        return lastRequestTime;
    }
    
    public User getUser() {
        return user;
    }
    
    public Type getKind() {
        return kind;
    }
}
