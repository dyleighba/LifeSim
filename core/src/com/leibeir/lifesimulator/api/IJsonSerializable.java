package com.leibeir.lifesimulator.api;

public interface IJsonSerializable {
    void fromJSON(String json);
    String toJSON();
}
