package com.polsl.yerbapp.data.repos

import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.polsl.yerbapp.data.network.ApolloClientFactory
import com.polsl.yerbapp.domain.models.reponse.graphql.ManufacturerModel
import yerba.GetManufacturersQuery
import yerba.GetNumberOfManufacturersQuery

class ManufacturersRepository(private val apolloClientFactory: ApolloClientFactory) {

    suspend fun getManufacturers(): List<ManufacturerModel> {
        val manufacturerQuery = GetManufacturersQuery
            .builder()
            .perPage(getNumberOfManufacturers())
            .orderBy("name")
            .order("ASC")
            .build()

        try {
            val apolloClient = apolloClientFactory.create()
            val response =
                apolloClient
                    .query(manufacturerQuery)
                    .toDeferred()
                    .await()

            return response.data()?.manufacturers()?.items()?.let { items ->
                items.map { ManufacturerModel(it.id(), it.name(), it.country()) }
            } ?: run {
                throw IllegalStateException()
            }
        } catch (ex: ApolloException) {
            throw ex
        } catch (ex: Exception) {
            throw  ex
        }
    }

    private suspend fun getNumberOfManufacturers(): Int {
        val numberOfManufacturers = GetNumberOfManufacturersQuery
            .builder()
            .build()

        try {
            val apolloClient = apolloClientFactory.create()
            val response =
                apolloClient
                    .query(numberOfManufacturers)
                    .toDeferred()
                    .await()

            return response.data()?.manufacturers()?.total() ?: 0
        } catch (ex: ApolloException) {
            throw ex
        } catch (ex: Exception) {
            throw  ex
        }
    }

}