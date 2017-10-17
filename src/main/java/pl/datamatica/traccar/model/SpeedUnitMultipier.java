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

/**
 *
 * @author Jan Usarek
 */
public class SpeedUnitMultipier {
    private SpeedUnitMultipier() {}
    
    public static final double KNOTS_TO_KM_MULTIPIER = 1.852;
    public static final double MILES_TO_KM_MULTIPIER = 1.609344;
    
    public static final double KM_TO_KNOTS_MULTIPIER = 0.5399568;
    public static final double KM_TO_MILES_MULTIPIER = 0.6217119;
    
}
