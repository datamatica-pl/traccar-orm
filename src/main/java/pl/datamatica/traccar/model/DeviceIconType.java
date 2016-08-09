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
    DEFAULT(PositionIconType.humanLatest, PositionIconType.humanOffline),
    BICYCLE(PositionIconType.bicycleLatest, PositionIconType.bicycleOffline),
    SEDAN(PositionIconType.humanLatest, PositionIconType.humanOffline),
    UNIVERSAL(PositionIconType.containerLatest, PositionIconType.containerOffline),

    MINIVAN(PositionIconType.motoLatest, PositionIconType.motoOffline),
    TRUCK(PositionIconType.tracktorLatest, PositionIconType.tractorOffline),
    BUS(PositionIconType.petLatest, PositionIconType.petOffline),
    LONG_TRUCK(PositionIconType.longTruckLatest, PositionIconType.longTruckOffline),
    CAR_TRUCK(PositionIconType.carTruckLatest, PositionIconType.carTruckOffline),

    PLANE(PositionIconType.pickupLatest, PositionIconType.pickupOffline),
    SHIP(PositionIconType.shipLatest, PositionIconType.shipOffline),
    TRAIN(PositionIconType.quadLatest, PositionIconType.quadOffline);

    private final PositionIconType iconLatest;
    private final PositionIconType iconOffline;

    DeviceIconType(PositionIconType iconLatest, PositionIconType iconOffline) {
        this.iconLatest = iconLatest;
        this.iconOffline = iconOffline;
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
