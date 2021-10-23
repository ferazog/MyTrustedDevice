package com.guerrero.mytrusteddevice.network

import retrofit2.http.*

interface MyService {

    @POST("api/login")
    @FormUrlEncoded
    suspend fun login(
        @Field("name") name: String,
        @Field("password") password: String
    ): LoginResponse

    @POST("api/devices/token")
    @FormUrlEncoded
    suspend fun getAccessToken(
        @Field("id") id: String
    ): AccessTokenResponse

    @POST("api/devices/register")
    @FormUrlEncoded
    suspend fun register(
        @Field("id") identity: String,
        @Field("sid") factorSid: String
    ): RegisterResponse
}
