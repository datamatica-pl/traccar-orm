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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import pl.datamatica.traccar.model.GeoFence.LonLat;

@MappedSuperclass
public class Route implements IsSerializable, Cloneable {
    public static enum Status {
        NEW, IN_PROGRESS_OK, IN_PROGRESS_LATE,
        FINISHED_OK, FINISHED_LATE, CANCELLED
    }
    
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
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToMany(cascade = {CascadeType.ALL})
    @OrderColumn(name="point_index")
    private List<RoutePoint> routePoints = new ArrayList<>();
    @OneToOne(cascade= {CascadeType.ALL})
    private GeoFence corridor;
    @ManyToOne
    @JoinColumn(nullable=false)
    @GwtTransient
    private User owner;
    @Temporal(TemporalType.TIMESTAMP)
    private Date cancelTimestamp;
    private boolean archive;
    private int tolerance;
    private int archiveAfter;
    private boolean forceFirst;
    private boolean forceLast;
    @Transient
    private LonLat[] linePoints;
    
    public Route() {
        status = Status.NEW;
        tolerance = 30;
        archiveAfter = 7;
    }
    
    public Route(Route copy) {
        this.id = copy.id;
        this.device = copy.device;
        this.name = copy.name;
        this.created = copy.created;
        this.deadline = copy.deadline;
        this.status = copy.status;
        this.routePoints = new ArrayList<>(copy.routePoints);
        this.corridor = copy.corridor;
        this.owner = copy.owner;
        if(corridor != null)
            corridor.setDevices(corridor.getTransferDevices());
        this.archive = copy.archive;
        this.status = copy.status;
        this.tolerance = copy.tolerance;
        this.archiveAfter = copy.archiveAfter;
        this.forceFirst = copy.forceFirst;
        this.forceLast = copy.forceLast;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<RoutePoint> getRoutePoints() {
        return routePoints;
    }
    
    public GeoFence getCorridor() {
        return corridor;
    }
    
    public void setCorridor(GeoFence corridor) {
        this.corridor = corridor;
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
    
    public boolean isArchived() {
        return archive;
    }
    
    public void setArchived(boolean archived) {
        this.archive = archived;
    }
    
    public int getDonePointsCount() {
        int donePointsCount = 0;
        for(RoutePoint rp : routePoints) {
            if(rp.getExitTime() != null)
                ++donePointsCount;
        }
        return donePointsCount;
    }
    
    public int getTolerance() {
        return tolerance;
    }
    
    public void setTolerance(int tolerance) {
        this.tolerance = tolerance;
    }
    
    public int getArchiveAfter() {
        return archiveAfter;
    }
    
    public void setArchiveAfter(int aa) {
        this.archiveAfter = aa;
    }
    
    public boolean isForceFirst() {
        return forceFirst;
    }
    
    public void setForceFirst(boolean forceFirst) {
        this.forceFirst = forceFirst;
    }
    
    public boolean isForceLast() {
        return forceLast;
    }
    
    public void setForceLast(boolean forceLast) {
        this.forceLast = forceLast;
    }
    
    public Date getCancelTimestamp() {
        return cancelTimestamp;
    }
    
    public void setCancelTimestamp(Date cancelTimestamp) {
        this.cancelTimestamp = cancelTimestamp;
    }

    public void update(Route updated) {
        this.name = updated.name;
        this.device = updated.device;
        this.status = updated.status;
        this.deadline = updated.deadline;
        this.routePoints = updated.routePoints;
        if(updated.corridor == null)
            this.corridor = null;
        else if(this.corridor == null) {
            this.corridor = updated.corridor;
            corridor.setDevices(corridor.getTransferDevices());
        } else {
            this.corridor.copyFrom(updated.corridor);
            this.corridor.getDevices().clear();
            if(corridor.getTransferDevices() != null)
                this.corridor.getDevices().addAll(corridor.getTransferDevices());
        }
        this.archive = updated.archive;
        this.cancelTimestamp = updated.cancelTimestamp;
        this.tolerance = updated.tolerance;
        this.archiveAfter = updated.archiveAfter;
        this.forceFirst = updated.forceFirst;
        this.forceLast = updated.forceLast;
    }
}
