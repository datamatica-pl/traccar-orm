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
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import pl.datamatica.traccar.model.GeoFence.LonLat;

@Entity
@Table(name="routes")
public class DbRoute extends Route {
    @GwtTransient
    @Type(type="org.hibernate.spatial.GeometryType")
    private LineString geom;
    
    @GwtTransient
    private static final GeometryFactory geomFactory = new GeometryFactory();
    
    public DbRoute() {}
    
    public DbRoute(Route r) {
        super(r);
        update(r);
    }
    
    public LineString getLineString() {
        return geom;
    }
    
    public void update(Route updated, Coordinate[] coords) {
        super.update(updated);
        CoordinateSequence cs = new CoordinateArraySequence(coords);
        geom = new LineString(cs, geomFactory);
    }
}
