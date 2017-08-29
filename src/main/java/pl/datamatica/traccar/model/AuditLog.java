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

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author piotrkrzeszewski
 */
@Entity
@Table(name="auditlog")
public class AuditLog implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    @Enumerated(EnumType.STRING)
    private AuditLogType type;

    public AuditLogType getType() {
        return type;
    }

    public void setType(AuditLogType type) {
        this.type = type;
    }
    
    private Date time;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    
    private String agentLogin;

    public String getAgentLogin() {
        return agentLogin;
    }

    public void setAgentLogin(String agentLogin) {
        this.agentLogin = agentLogin;
    }
    
    private String targetUserLogin;

    public String getTargetUserLogin() {
        return targetUserLogin;
    }

    public void setTargetUserLogin(String targetUserLogin) {
        this.targetUserLogin = targetUserLogin;
    }
    
    private String targetUserGroupName;

    public String getTargetUserGroupName() {
        return targetUserGroupName;
    }

    public void setTargetUserGroupName(String targetUserGroupName) {
        this.targetUserGroupName = targetUserGroupName;
    }
    
    private String fieldName;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String name) {
        this.fieldName = name;
    }
   
    private String fieldNewValue;

    public String getFieldNewValue() {
        return fieldNewValue;
    }

    public void setFieldNewValue(String fieldNewValue) {
        this.fieldNewValue = fieldNewValue;
    }
    
    private String permissionName;

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
    
    static class Builder {
        private Date time;
        private String agentLogin;
        private String targetUserLogin;
        private String targetUserGroupName;
        private String fieldName;
        private String fieldNewValue;
        private String permissionName;
        private AuditLogType type;

        public Builder time(Date time) {
            this.time = time;
            return this;
        }

        public Builder agentLogin(String agentLogin) {
            this.agentLogin = agentLogin;
            return this;
        }

        public Builder targetUserLogin(String targetUserLogin) {
            this.targetUserLogin = targetUserLogin;
            return this;
        }

        public Builder targetUserGroupName(String targetUserGroupName) {
            this.targetUserGroupName = targetUserGroupName;
            return this;
        }

        public Builder fieldName(String fieldName) {
            this.fieldName = fieldName;
            return this;
        }

        public Builder fieldNewValue(String fieldNewValue) {
            this.fieldNewValue = fieldNewValue;
            return this;
        }

        public Builder permissionName(String permissionName) {
            this.permissionName = permissionName;
            return this;
        }

        public Builder type(AuditLogType type) {
            this.type = type;
            return this;
        }
        
        public AuditLog build() {
            AuditLog al = new AuditLog();
            al.time = time;
            al.type = type;
            al.agentLogin = agentLogin;
            al.targetUserLogin = targetUserLogin;
            al.targetUserGroupName = targetUserGroupName;
            al.fieldName = fieldName;
            al.fieldNewValue = fieldNewValue;
            al.permissionName = permissionName;
            return al;
        }
    }
}
