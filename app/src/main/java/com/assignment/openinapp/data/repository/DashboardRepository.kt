package com.assignment.openinapp.data.repository

import com.assignment.openinapp.data.model.DashboardDetails
import retrofit2.Response

class DashboardRepository : RepositoryBase() {

    suspend fun fetchDashboardDetails() : Response<DashboardDetails> {
        return makeApiCall { it.fetchDashboardDetails() }
    }
}