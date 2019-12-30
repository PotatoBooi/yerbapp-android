package com.polsl.yerbapp.data.repos

import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.polsl.yerbapp.data.network.ApolloClientFactory
import com.polsl.yerbapp.domain.exceptions.UnauthorizedException
import com.polsl.yerbapp.domain.models.reponse.graphql.ManufacturerModel
import com.polsl.yerbapp.domain.models.reponse.graphql.ProductModel
import com.polsl.yerbapp.domain.models.reponse.graphql.TypeModel
import yerba.AddProductMutation
import yerba.GetProductQuery
import yerba.GetProductsQuery
import yerba.type.AddProductInput
import java.lang.IllegalStateException

class ProductsRepository(private val apolloClientFactory: ApolloClientFactory){


    suspend fun getProducts(perPage: Int, offset: Int, orderBy: String): List<ProductModel>{
        val productsQuery = GetProductsQuery
            .builder()
            .perPage(perPage)
            .offset(offset)
            .orderBy(orderBy)
            .build()

        try{
            val apolloClient = apolloClientFactory.create()
        //delay(2000)  // for testing loaders
            val response =
                apolloClient
                    .query(productsQuery)
                    .toDeferred()
                    .await()

            return response.data()?.products()?.items()?.let { items ->
                items.map{ProductModel(it.id(), it.name(), it.photoUrl())}
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

    suspend fun getProduct(productId: String): ProductModel{
        val productQuery = GetProductQuery
            .builder()
            .productId(productId)
            .build()
        try{
            val apolloClient = apolloClientFactory.create()
        //delay(2000)  // for testing loaders
            val response =
                apolloClient
                    .query(productQuery)
                    .toDeferred()
                    .await()

            return response.data()?.product()?.let {
                val manufacturer = ManufacturerModel(country = it.manufacturer().country(), name = it.manufacturer().name())
                val type = TypeModel(name = it.type().name())
                ProductModel(id = it.id(), name = it.name(), details = it.details(), photoUrl = it.photoUrl(), manufacturerModel = manufacturer, typeModel = type )
            }?: run {
                throw IllegalStateException()}
        }
        catch(ex: ApolloException){
            throw ex
        }
        catch (ex: Exception) {
            throw  ex
        }
    }

   suspend fun addProduct(name: String, details: String, typeId: String, manufacturerId: String, imagePath: String) : String {
       val tempUrl = null
       if(imagePath.isNotEmpty()){
           // TODO post multipart form data
           // tempUrl = response
       }
        val productInput = AddProductInput
            .builder()
            .name(name)
            .details(details)
            .typeId(typeId)
            .manufacturerId(manufacturerId)
            .photoUrl(tempUrl)
            .build()

        val productMutation = AddProductMutation
            .builder()
            .product(productInput)
            .build()
        try{
            val apolloClient = apolloClientFactory.create()
            //delay(2000)  // for testing loaders
            val response =
                apolloClient
                    .mutate(productMutation)
                    .toDeferred()
                    .await()

            if(response.hasErrors()){
                val errorMessage = response.errors().first().message()
                if(errorMessage == "{statusCode=401, error=Unauthorized}"){
                    throw UnauthorizedException()
                }
            }

            return response.data()?.addProduct()?.let {
                return it.id()
            }?: run {
                throw IllegalStateException()}
        }
        catch(ex: ApolloException){
            throw ex
        }
        catch (ex: Exception) {
            throw  ex
        }
    }
}