/*
 * Copyright (C) 2015 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.twitter.sdk.android.unity;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.persistence.SerializationStrategy;

class ApiError {
    final public int code;
    final public String message;

    ApiError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    static class Serializer implements SerializationStrategy<ApiError> {

        private final Gson gson;

        public Serializer() {
            this.gson = new Gson();
        }

        @Override
        public String serialize(ApiError error) {
            try {
                return gson.toJson(error);
            } catch (Exception e) {
                Fabric.getLogger().d(TwitterCore.TAG, e.getMessage());
            }
            return "";
        }

        @Override
        public ApiError deserialize(String serializedSession) {
            if (!TextUtils.isEmpty(serializedSession)) {
                try {
                    return gson.fromJson(serializedSession, ApiError.class);
                } catch (Exception e) {
                    Fabric.getLogger().d(TwitterCore.TAG, e.getMessage());
                }
            }
            return null;
        }
    }
}
