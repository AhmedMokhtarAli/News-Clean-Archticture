package com.example.domain

import com.example.data.model.BaseEndPointResponse
import com.example.data.model.NewsResponse
import com.example.utilis.api.handleError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.transform
import retrofit2.Response

inline fun <T, R> Flow<Response<BaseEndPointResponse<T>>>.transformResponseData(
    crossinline onSuccess: suspend FlowCollector<R>.(T) -> Unit
): Flow<R> {
    return transform { response ->
        val body = response.body()
        val errorBody = response.errorBody()

        when {
            response.isSuccessful && response.body() != null -> {
                onSuccess(response.body()!!.data!!)
            /* if (body?.data is NewsResponse ) {
                    val newsResponse = body.data as NewsResponse
//                    user.token = body.meta?.token
                    onSuccess(newsResponse as T)
                } else
                    onSuccess(response.body()!!.data!!)*/
            }

            response.code() == 401 ->{
                throw Throwable(ErrorAPI.UNAUTHRIZED)}

            response.code() in 402..499 && response.errorBody() == null ->
                throw Throwable(errorBody?.handleError())

            response.code() in 500..599 -> throw Throwable(ErrorAPI.SERVER_ERROR)
            else -> throw Exception()
        }
    }
}
object ErrorAPI {
    const val SERVER_ERROR = "serverError"

    const val BAD_REQUEST = "bad_request"

    const val UNAUTHRIZED = "unauthrized"

}