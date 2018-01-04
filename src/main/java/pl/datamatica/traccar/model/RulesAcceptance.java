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

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ŁŁ
 */
@Table(name="rules_acceptances")
@Entity
public class RulesAcceptance {
    @EmbeddedId
    private RulesAcceptanceId id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable=false)
    private Date timestamp;
    
    public RulesAcceptance() {}
    
    public RulesAcceptance(User user, RulesVersion version) {
        this.id = new RulesAcceptanceId(user, version);
        this.timestamp = new Date();
    }
    
    public User getUser() {
        return id.getUser();
    }
    
    public RulesVersion getVersion() {
        return id.getVersion();
    }
    
    public Date getTimestamp() {
        return timestamp;
    }
    
    @Embeddable
    public static class RulesAcceptanceId implements java.io.Serializable {
        @ManyToOne
        private User user;
        @ManyToOne
        private RulesVersion version;
        
        public RulesAcceptanceId() {}
        
        public RulesAcceptanceId(User user, RulesVersion version) {
            this.user = user;
            this.version = version;
        }
        
        public User getUser() {
            return user;
        }
        
        public RulesVersion getVersion() {
            return version;
        }
        
        @Override
        public boolean equals(Object o) {
            if(this == o) 
                return true;
            if(o == null || this.getClass() != o.getClass())
                return false;
            RulesAcceptanceId that = (RulesAcceptanceId)o;
            
            if(user != null ? !user.equals(that.user) : that.user != null)
                return false;
            if(version != null ? !version.equals(that.version) : that.version != null)
                return false;
            return true;
        }
        
        @Override
        public int hashCode() {
            int result = (user != null ? user.hashCode() : 0);
            result = result*31 + (version != null ? version.hashCode() : 0);
            return result;
        }
    }
}
