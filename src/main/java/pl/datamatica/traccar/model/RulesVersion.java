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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ŁŁ
 */
@Table(name="rules_versions")
@Entity
public class RulesVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private long id;
    private String url;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Enumerated(EnumType.STRING)
    private Type type;
    
    public RulesVersion(){
    }
    
    public RulesVersion(String url, Date startDate, Type type) {
        this.url = url;
        this.startDate = startDate;
        this.type = type;
    }
    
    public long getId() {
        return id;
    }
    
    public String getUrl() {
        return url;
    }
    
    public Date getStartDate() {
        return startDate;
    }
    
    public Date getEndDate() {
        return endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public Type getType() {
        return type;
    }
    
    public boolean isObligatory() {
        return type.isObligatory();
    }
    
    public enum Type {
        TERMS_OF_USE(true, "Regulamin / Terms of use"), 
        PRIVACY_POLICY(true, "Polityka prywatności / Privacy policy"),
        MARKETING(false, "Przetwarzanie w celach marketingowych / Marketing");
        
        private boolean obligatory;
        private String name;
        private Type(boolean obligatory, String name) {
            this.obligatory = obligatory;
            this.name = name;
        }
        
        public boolean isObligatory() {
            return obligatory;
        }
        
        public String getName() {
            return name;
        }
    }
}
