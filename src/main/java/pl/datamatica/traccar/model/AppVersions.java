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

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author piotrkrzeszewski
 */
@Entity
@Table(name="appversions")
public class AppVersions implements Serializable 
{    
    private static final String defaultAppVersion = "1.0.0";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    @JsonIgnore
    private long id;
    
    public AppVersions()
    {
        // default settings
        iosVersion = defaultAppVersion;
        iosRequiredVersion = defaultAppVersion;
        androidVersion = defaultAppVersion;
        androidRequiredVersion = defaultAppVersion;
        messageKey = null;
        localizedMessage = null;
        messageUrl = null;
    }
    
    private String iosVersion;
    
    public String getIosVersion() {
        return iosVersion;
    }

    public void setIosVersion(String iosVersion) {
        this.iosVersion = iosVersion;
    }
    
    private String iosRequiredVersion;
    
    public String getIosRequiredVersion() {
        return iosRequiredVersion;
    }

    public void setIosRequiredVersion(String iosRequiredVersion) {
        this.iosRequiredVersion = iosRequiredVersion;
    }
    
    private String androidVersion;

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    private String androidRequiredVersion;
   
    public String getAndroidRequiredVersion() {
        return androidRequiredVersion;
    }

    public void setAndroidRequiredVersion(String androidRequiredVersion) {
        this.androidRequiredVersion = androidRequiredVersion;
    }
    
    @Column(nullable = true)
    private String messageKey;
    
    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }
    
    @Column(nullable = true)
    private String localizedMessage;
    
    public String getLocalizedMessage() {
        return localizedMessage;
    }

    public void setLocalizedMessage(String localizedMessage) {
        this.localizedMessage = localizedMessage;
    }
    
    @Column(nullable = true)
    private String messageUrl;
    
    public String getMessageUrl() {
        return messageUrl;
    }

    public void setMessageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
    }
}
