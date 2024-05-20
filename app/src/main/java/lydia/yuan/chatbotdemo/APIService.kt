package lydia.yuan.chatbotdemo

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("get")
    suspend fun getMessage(
        @Query("bid") bid: String,
        @Query("key") key: String,
        @Query("uid") uid: String,
        @Query("msg") msg: String
    ): ApiResponse
}
