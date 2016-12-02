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

import com.google.gwt.user.client.rpc.IsSerializable;

public enum CommandType implements IsSerializable {
    positionSingle,
    positionPeriodic,
    positionStop,
    engineStop,
    engineResume,
    alarmArm,
    alarmDisarm,
    autoAlarmArm,
    autoAlarmDisarm,
    setTimezone,
    requestPhoto,
    rebootDevice,
    movementAlarm,
    sendSms,
    setDefenseTime,
    getParams,
    getStatus,
    setSOSNumbers,
    deleteSOSNumber,
    setCenterNumber,
    factorySettings,
    extendedCustom,
    listenMode,
    voiceCallMode,
    sleepMode,
    exitSleepMode,
    custom;

    public static final String KEY_FREQUENCY = "frequency";
    public static final String KEY_FREQUENCY_STOP = "frequencyStop";
    public static final String KEY_TIMEZONE = "timezone";
    public static final String KEY_RADIUS = "radius";
    public static final String KEY_PHONE_NUMBER = "phoneNumber";
    public static final String KEY_DEFENSE_TIME = "defenseTime";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_SOS_NUMBER_1 = "SOSNumber1";
    public static final String KEY_SOS_NUMBER_2 = "SOSNumber2";
    public static final String KEY_SOS_NUMBER_3 = "SOSNumber3";
    public static final String KEY_SOS_NUMBER = "SOSNumber";
    public static final String KEY_CENTER_NUMBER = "centerNumber";
    
    public static CommandType fromString(String commandType) {
        return CommandType.valueOf(commandType);
    }
}
