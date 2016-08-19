/*
 * Copyright 2014 Vitaly Litvak (vitavaque@gmail.com)
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

import com.google.gwt.user.client.rpc.IsSerializable;

public enum DeviceIconType implements IsSerializable {
    DEFAULT(1, PositionIconType.humanLatest, PositionIconType.humanOffline),
    BICYCLE(2, PositionIconType.bicycleLatest, PositionIconType.bicycleOffline),
    SEDAN(3, PositionIconType.humanLatest, PositionIconType.humanOffline),
    UNIVERSAL(4, PositionIconType.containerLatest, PositionIconType.containerOffline),

    MINIVAN(5, PositionIconType.motoLatest, PositionIconType.motoOffline),
    TRUCK(6, PositionIconType.tracktorLatest, PositionIconType.tractorOffline),
    BUS(7, PositionIconType.petLatest, PositionIconType.petOffline),
    LONG_TRUCK(8, PositionIconType.longTruckLatest, PositionIconType.longTruckOffline),
    CAR_TRUCK(9, PositionIconType.carTruckLatest, PositionIconType.carTruckOffline),

    PLANE(10, PositionIconType.pickupLatest, PositionIconType.pickupOffline),
    SHIP(11, PositionIconType.shipLatest, PositionIconType.shipOffline),
    TRAIN(12, PositionIconType.quadLatest, PositionIconType.quadOffline);

    private final PositionIconType iconLatest;
    private final PositionIconType iconOffline;
    private final int id;

    DeviceIconType(int id, PositionIconType iconLatest, PositionIconType iconOffline) {
        this.iconLatest = iconLatest;
        this.iconOffline = iconOffline;
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public PositionIconType getPositionIconType(Position.Status status) {
        switch (status) {
            case LATEST:
                return iconLatest;
            case OFFLINE:
                return iconOffline;
        }
        return null;
    }
}
