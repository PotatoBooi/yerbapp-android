package com.polsl.yerbapp.data.repos

import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.polsl.yerbapp.data.network.ApolloClientFactory
import com.polsl.yerbapp.domain.models.reponse.graphql.ManufacturerModel
import yerba.GetManufacturersQuery

class ManufacturersRepository(private val apolloClientFactory: ApolloClientFactory) {

    suspend fun getManufacturers(): List<ManufacturerModel> {
        val manufacturerQuery = GetManufacturersQuery
            .builder()
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

}