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
import com.google.gwt.user.client.rpc.IsSerializable;
import javax.persistence.*;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class MaintenanceBase implements IsSerializable {

    public MaintenanceBase() {}
    
    protected MaintenanceBase(MaintenanceBase other) {
        copyFrom(other);
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    @JsonIgnore
    protected long id;
    
    public long getId(){
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
   // sequence number of this interval
    private int indexNo;

    public int getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(int indexNo) {
        this.indexNo = indexNo;
    }
    
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    protected Device device;

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Maintenance)) return false;

        Maintenance that = (Maintenance) o;

        if (getId() != that.getId()) return false;
        if (getIndexNo() != that.getIndexNo()) return false;
        if (getDevice() != null ? !getDevice().equals(that.getDevice()) : that.getDevice() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getDevice() != null ? getDevice().hashCode() : 0);
        result = 31 * result + getIndexNo();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }
    
    public void copyFrom(MaintenanceBase other) {
        this.device = other.device;
        this.id = other.id;
        this.indexNo = other.indexNo;
        this.name = other.name;
    }
}
