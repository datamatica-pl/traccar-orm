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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Jan Usarek
 */
public class PositionTest {

    @Test
    public void testNullValidStatus() throws Exception {
        Position position = new Position();
        assertTrue(position.hasProperValidStatus());
    }

    @Test
    public void testProperValidStatus() throws Exception {
        Position position = new Position();
        position.setValidStatus(Position.VALID_STATUS_CORRECT_POSITION);
        assertTrue(position.hasProperValidStatus());
    }

    @Test
    public void testAlarmValidStatus() throws Exception {
        Position position = new Position();
        position.setValidStatus(Position.VALID_STATUS_ALARM);
        assertFalse(position.hasProperValidStatus());
    }
    
    @Test
    public void testSpeedUnitConversion() throws Exception {
        Position position = new Position();
        position.setSpeed(1000.00);
        assertEquals(1852, position.getSpeedInKmh().intValue());
    }

}
