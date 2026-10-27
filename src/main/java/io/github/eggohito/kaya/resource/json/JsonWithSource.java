package io.github.eggohito.kaya.resource.json;

import com.google.gson.JsonElement;

public record JsonWithSource(JsonElement json, String source) {

}
