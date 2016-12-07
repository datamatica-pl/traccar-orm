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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_sessions")
public class UserSession {
    
    public UserSession() {
    }
    
    public UserSession(String sessionId, long userId, String fcmToken) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.fcmToken = fcmToken;
    }
    
    @Id
    private String sessionId;
    public String getSessionId() {
        return sessionId;
    }
    
    private long userId;
    public long getUserId() {
        return userId;
    }
    
    @Column(length = 255)
    private String fcmToken;
    public String getFcmToken() {
        return fcmToken;
    }
}
