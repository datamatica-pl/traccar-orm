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

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.*;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class TimestampedEntity {
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date lastUpdate;

    public Date getLastUpdate() {
        if(lastUpdate == null)
            return new Date(1000);
        return lastUpdate;
    }
    
    public void setLastUpdate(Date updateTime) {
        this.lastUpdate = updateTime;
    }
    
    @PreUpdate
    @PrePersist
    public void updateModificationTime() {
        setLastUpdate(new Date());
    }
    
}
