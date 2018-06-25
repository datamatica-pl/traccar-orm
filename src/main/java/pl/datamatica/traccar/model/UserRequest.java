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

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class UserRequest {
    @EmbeddedId
    private Id id;
    private int count;
    
    public UserRequest() {}
    
    public UserRequest(User user, Date date) {
        id = new Id(user, date);
        count = 1;
    }
    
    public User getUser() {
        return id.user;
    }
    
    public Date getDate() {
        return id.date;
    }
    
    public int getCount() {
        return count;
    }
    
    public void increaseCount() {
        ++count;
    }
    
    @Embeddable
    public static class Id implements Serializable {
        @ManyToOne
        private User user;
        @Temporal(TemporalType.DATE)
        private Date date;
        
        public Id() {}
        
        public Id(User user, Date date) {
            this.user = user;
            this.date = date;
        }
    }
}
