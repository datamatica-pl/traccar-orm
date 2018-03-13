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

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="route_points")
public class RoutePoint implements IsSerializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private long id;
    @ManyToOne
    private GeoFence geofence;
    @Temporal(TemporalType.TIMESTAMP)
    private Date enterTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date exitTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date deadline;

    public RoutePoint() {}
    
    public RoutePoint(long id, GeoFence geofence) {
        this.id = id;
        this.geofence = geofence;
    }
    
    public long getId() {
        return id;
    }

    public GeoFence getGeofence() {
        return geofence;
    }

    public void setGeofence(GeoFence geofence) {
        this.geofence = geofence;
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enter) {
        this.enterTime = enter;
    }

    public Date getExitTime() {
        return exitTime;
    }

    public void setExitTime(Date exit) {
        this.exitTime = exit;
    }
    
    public Date getDeadline() {
        return deadline;
    }
    
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
