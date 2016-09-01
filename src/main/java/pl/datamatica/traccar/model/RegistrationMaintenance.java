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

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="registrationReviews", 
        indexes = @Index(name="regReview_pkey", columnList="id"))
public class RegistrationMaintenance extends MaintenanceBase implements IsSerializable{
    
    public RegistrationMaintenance() {}
    
    @Temporal(TemporalType.DATE)
    private Date serviceDate;

    RegistrationMaintenance(RegistrationMaintenance other) {
        copyFrom(other);
    }
    
    public Date getServiceDate() {
        return serviceDate;
    }
    
    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }
    
    public void copyFrom(RegistrationMaintenance other) {
        serviceDate = other.serviceDate;
        super.copyFrom(other);
    }
}
