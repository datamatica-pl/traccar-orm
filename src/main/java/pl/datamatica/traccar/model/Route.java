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

import com.google.gwt.user.client.rpc.GwtTransient;
import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import pl.datamatica.traccar.model.GeoFence.LonLat;

@MappedSuperclass
public class Route implements IsSerializable, Cloneable {    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private long id;
    @ManyToOne
    private Device device;
    private String name;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Temporal(TemporalType.TIMESTAMP)
    private Date deadline;
    private String status;
    @OneToMany(cascade = {CascadeType.ALL})
    @OrderColumn(name="point_index")
    private List<RoutePoint> routePoints = new ArrayList<>();
    @ManyToOne
    @JoinColumn(nullable=false)
    @GwtTransient
    private User owner;
    @Transient
    private LonLat[] linePoints;
    
    public Route() {}
    
    public Route(Route copy) {
        this.id = copy.id;
        this.device = copy.device;
        this.name = copy.name;
        this.created = copy.created;
        this.deadline = copy.deadline;
        this.status = copy.status;
        this.routePoints = new ArrayList<>(copy.routePoints);
        this.owner = copy.owner;
    }
    
    public long getId() {
        return id;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<RoutePoint> getRoutePoints() {
        return routePoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public User getOwner() {
        return owner;
    }
    
    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    public LonLat[] getLinePoints() {
        return linePoints;
    }
    
    public void setLinePoints(LonLat[] ll) {
        linePoints = ll; 
    }

    public void update(Route updated) {
        this.name = updated.name;
        this.device = updated.device;
        this.status = updated.status;
        this.deadline = updated.deadline;
        this.routePoints = updated.routePoints;
    }
}
