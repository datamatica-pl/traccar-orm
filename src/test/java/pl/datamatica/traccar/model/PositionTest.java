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
import org.junit.Test;

/**
 *
 * @author Jan Usarek
 */
public class PositionTest {

    @Test
    public void testAlarmTrue() throws Exception {
        Position position = new Position();
        position.setOther("{\"alarm\":true}");
        assertTrue(position.isAlarm());
    }

    @Test
    public void testAlarmTrueString() throws Exception {
        Position position = new Position();
        position.setOther("{\"alarm\":\"true\"}");
        assertTrue(position.isAlarm());
    }

    @Test
    public void testAlarmFalse() throws Exception {
        Position position = new Position();
        position.setOther("{\"alarm\":false}");
        assertFalse(position.isAlarm());
    }

    @Test
    public void testAlarmFalseString() throws Exception {
        Position position = new Position();
        position.setOther("{\"alarm\":\"false\"}");
        assertFalse(position.isAlarm());
    }

    @Test
    public void testAlarmNotExists() throws Exception {
        Position position = new Position();
        position.setOther("{\"ignition\":\"true\"}");
        assertFalse(position.isAlarm());
    }

    @Test
    public void testAlarmJsonEmpty() throws Exception {
        Position position = new Position();
        position.setOther("{}");
        assertFalse(position.isAlarm());
    }

    @Test
    public void testAlarmJsonEmptyString() throws Exception {
        Position position = new Position();
        position.setOther("");
        assertFalse(position.isAlarm());
    }

    @Test
    public void testAlarmOtherNotExists() throws Exception {
        Position position = new Position();
        assertFalse(position.isAlarm());
    }

}
