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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ble_devices")
public class BleDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private long id;
    private String modelName;
    private Date creationTimestamp;
    private String phoneModel;
    private String phoneSystem;
    
    @ManyToOne
    private User owner;
    
    public BleDevice() {}
    
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    
    public void setCreationTimestamp(Date creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }
    
    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }
    
    public void setPhoneSystem(String phoneSystem) {
        this.phoneSystem = phoneSystem;
    }
    
    public void setOwner(User owner) {
        this.owner = owner;
    }
}
