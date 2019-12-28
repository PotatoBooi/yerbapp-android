package com.polsl.yerbapp.data.repos

import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.polsl.yerbapp.data.network.ApolloClientFactory
import com.polsl.yerbapp.domain.models.reponse.graphql.TypeModel
import yerba.GetTypesQuery
import java.lang.IllegalStateException

class TypesRepository(private val apolloClientFactory: ApolloClientFactory) {

    suspend fun getTypes(): List<TypeModel>{
        val typesQuery = GetTypesQuery
            .builder()
            .build()

        try{
            val apolloClient = apolloClientFactory.create()
            val response =
                apolloClient
                    .query(typesQuery)
                    .toDeferred()
                    .await()

            return response.data()?.types()?.items()?.let { items ->
                items.map{ TypeModel(it.id(), it.name())}
            } ?: run {
                throw IllegalStateException()
            }
        }
        catch(ex: ApolloException){
            throw ex
        }
        catch (ex: Exception) {
            throw  ex
        }
    }
}
