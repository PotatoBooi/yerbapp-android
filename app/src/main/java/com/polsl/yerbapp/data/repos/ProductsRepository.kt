package com.polsl.yerbapp.data.repos

import android.net.Uri
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.polsl.yerbapp.data.network.ApolloClientFactory
import com.polsl.yerbapp.data.network.RetrofitService
import com.polsl.yerbapp.domain.exceptions.UnauthorizedException
import com.polsl.yerbapp.domain.models.reponse.graphql.ManufacturerModel
import com.polsl.yerbapp.domain.models.reponse.graphql.ProductModel
import com.polsl.yerbapp.domain.models.reponse.graphql.TypeModel
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import yerba.AddProductMutation
import yerba.GetProductQuery
import yerba.GetProductsQuery
import yerba.type.AddProductInput
import java.io.File

class ProductsRepository(
    private val apolloClientFactory: ApolloClientFactory,
    private val retrofitService: RetrofitService,
    private val usersRepository: UsersRepository
) {


    suspend fun getProducts(
        filter: String?,
        perPage: Int,
        offset: Int,
        orderBy: String
    ): List<ProductModel> {
        val productsQuery = GetProductsQuery
            .builder()
            .searchByName(filter)
            .perPage(perPage)
            .offset(offset)
            .orderBy(orderBy)
        if (usersRepository.checkUserAuthorized()) {
            productsQuery.personalizeForUer(usersRepository.getCurrentUserInfo().userId.toString())
        }
        try {
            val apolloClient = apolloClientFactory.create()
            val response =
                apolloClient
                    .query(productsQuery.build())
                    .toDeferred()
                    .await()

            return response.data()?.products()?.items()?.let { items ->
                items.map {
                    ProductModel(
                        it.id(),
                        it.name(),
                        it.overallAverage().toFloat(),
                        it.photoUrl()
                    )
                }
            } ?: run {
                throw IllegalStateException()
            }
        } catch (ex: ApolloException) {
            throw ex
        } catch (ex: Exception) {
            throw  ex
        }
    }

    suspend fun getProduct(productId: String): ProductModel {
        val productQuery = GetProductQuery
            .builder()
            .productId(productId)
            .build()
        try {
            val apolloClient = apolloClientFactory.create()
            //delay(2000)  // for testing loaders
            val response =
                apolloClient
                    .query(productQuery)
                    .toDeferred()
                    .await()

            return response.data()?.product()?.let {
                val manufacturer = ManufacturerModel(
                    country = it.manufacturer().country(),
                    name = it.manufacturer().name()
                )
                val type = TypeModel(name = it.type()?.name() ?: "-")
                ProductModel(
                    id = it.id(),
                    name = it.name(),
                    details = it.details(),
                    photoUrl = it.photoUrl(),
                    overallAverage = it.overallAverage().toFloat(),
                    aromaAverage = it.aromaAverage().toFloat(),
                    bitternessAverage = it.bitternessAverage().toFloat(),
                    energyAverage = it.energyAverage().toFloat(),
                    priceAverage = it.priceAverage().toFloat(),
                    tasteAverage = it.tasteAverage().toFloat(),
                    manufacturerModel = manufacturer,
                    typeModel = type
                )
            } ?: run {
                throw IllegalStateException()
            }
        } catch (ex: ApolloException) {
            throw ex
        } catch (ex: Exception) {
            throw  ex
        }
    }

    suspend fun addProduct(
        name: String,
        details: String,
        typeId: String,
        manufacturerId: String,
        image: File?
    ): String {

        val url = image?.let {
            uploadFile(image)
        } ?: run { null }
        val productInput = AddProductInput
            .builder()
            .name(name)
            .details(details)
            .typeId(typeId)
            .manufacturerId(manufacturerId)
            .photoUrl(url)
            .build()

        val productMutation = AddProductMutation
            .builder()
            .product(productInput)
            .build()
        try {
            val apolloClient = apolloClientFactory.create()
            //delay(2000)  // for testing loaders
            val response =
                apolloClient
                    .mutate(productMutation)
                    .toDeferred()
                    .await()

            if (response.hasErrors()) {
                val errorMessage = response.errors().first().message()
                if (errorMessage == "{statusCode=401, error=Unauthorized}") {
                    throw UnauthorizedException()
                }
            }

            return response.data()?.addProduct()?.let {
                return it.id()
            } ?: run {
                throw IllegalStateException()
            }
        } catch (ex: UnauthorizedException) {
            throw UnauthorizedException()
        } catch (ex: ApolloException) {
            throw ex
        } catch (ex: Exception) {
            throw  ex
        }
    }

    suspend fun uploadFile(file: File): String? {
        val uri = Uri.fromFile(file)
        val filePart = MultipartBody.Part.createFormData(
            "file",
            file.name,
            RequestBody.create(MediaType.parse("image/jpeg"), file)
        )
        try {
            val result = retrofitService.upload(
                filePart
            )
            return result.url
        } catch (ex: HttpException) {
            when (ex.code()) {
                401 -> throw UnauthorizedException()
            }
        } catch (ex: Exception) {
            throw  ex
        }
        return null
    }
}

