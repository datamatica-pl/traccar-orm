/*
 * Copyright 2015 Vitaly Litvak (vitavaque@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.datamatica.traccar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gwt.user.client.rpc.GwtTransient;
import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.HashSet;

import javax.persistence.*;
import java.util.Set;
import org.hibernate.annotations.BatchSize;

@Entity
@Table(name = "groups",
       indexes = { @Index(name = "groups_pkey", columnList = "id") })
@BatchSize(size = 25)
public class Group implements IsSerializable, GroupedDevice {
    public Group() {
        shared = true;
    }

    public Group(long id, String name) {
        this(id);
        this.name = name;
    }

    public Group(long id) {
        this();
        this.id = id;
    }

    public Group(String name) {
        this();
        this.name = name;
    }
    
    public Group(Group other) {
        this.id = other.id;
        this.name = other.name;
        this.description = other.description;
        this.owned = other.owned;
        this.shared = other.shared;
        this.users = new HashSet<>(other.users);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private long id;

    public long getId() {
        return id;
    }

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @GwtTransient
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "groups_users",
            foreignKey = @ForeignKey(name = "groups_users_fkey_report_id"),
            joinColumns = { @JoinColumn(name = "group_id", table = "groups", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id", table = "users", referencedColumnName = "id") })
    @JsonIgnore
    private Set<User> users;

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "groups_fkey_parent_id"))
    @JsonIgnore
    @GwtTransient
    private Group parent;

    public Group getParent() {
        return parent;
    }

    public void setParent(Group parent) {
        this.parent = parent;
    }
    
    @Transient
    private boolean shared;
    
    public boolean isShared() {
        return shared;
    }
    
    public void setShared(boolean shared) {
        this.shared = shared;
    }
    
    @Transient
    private boolean owned;
    
    public boolean isOwned() {
        return owned;
    }
    
    public void setOwned(boolean owned) {
        this.owned = owned;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Group)) return false;

        Group that = (Group) o;

        if (getId() != that.getId()) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getParent() != null ? !getParent().equals(that.getParent()) : that.getParent() != null) return false;

        return true;
    }

    public Group copyFrom(Group group) {
        this.name = group.name;
        this.description = group.description;
        return this;
    }
}
