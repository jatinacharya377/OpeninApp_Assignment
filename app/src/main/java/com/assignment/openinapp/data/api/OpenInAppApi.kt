package com.assignment.openinapp.data.api

import com.assignment.openinapp.data.model.DashboardDetails
import retrofit2.Response
import retrofit2.http.GET

interface OpenInAppApi {
    @GET("api/v1/dashboardNew")
    suspend fun fetchDashboardDetails() : Response<DashboardDetails>
}