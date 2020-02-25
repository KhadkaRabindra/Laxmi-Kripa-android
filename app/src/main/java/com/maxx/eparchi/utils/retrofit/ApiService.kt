package com.maxx.eparchi.utils.retrofit

import android.content.Context
import android.text.TextUtils
import com.maxx.eparchi.BuildConfig
import com.maxx.eparchi.R
import com.maxx.eparchi.model.LoginPostData
import com.maxx.eparchi.model.LoginResponse
import com.maxx.eparchi.model.UserInfo
import com.maxx.eparchi.utils.Constants
import com.maxx.eparchi.utils.kotlin.utils.extensions.doLogOut
import com.maxx.eparchi.utils.kotlin.utils.extensions.isLocationPermissionGranted
import com.maxx.eparchi.utils.view.Alerts
import io.reactivex.Observable
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

interface ApiService {


    @POST("login/")
    fun login(@Body loginPostData: LoginPostData): Observable<LoginResponse>


    companion object Factory {
        private val TIMEOUT: Long = 1 * 60
        private val builder = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())

        private val httpClient = getUnsafeOkHttpClient()
        private var retrofit: Retrofit? = null
        fun create(applicationContext: Context): ApiService {

            httpClient.connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            httpClient.writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            httpClient.readTimeout(TIMEOUT, TimeUnit.SECONDS)

            //httpClient.connectionSpecs(Collections.singletonList(configureConnectionSpec()))
            //add custom header
            httpClient.addInterceptor(HeaderInterceptor(applicationContext))
            //enable http request log
            if (BuildConfig.DEBUG)
                httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

            if (retrofit == null)
                retrofit = builder.client(httpClient.build()).build()

            return retrofit!!.create(ApiService::class.java)
        }
    }
}

private fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
    try {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(
                chain: Array<java.security.cert.X509Certificate>,
                authType: String
            ) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(
                chain: Array<java.security.cert.X509Certificate>,
                authType: String
            ) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate?> {
                return arrayOfNulls(0)
            }
        })

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.socketFactory

        return OkHttpClient.Builder()
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier { hostname, session -> true }

    } catch (e: Exception) {
        throw RuntimeException(e)
    }

}

private fun configureConnectionSpec(): ConnectionSpec {
    return ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
        .tlsVersions(TlsVersion.TLS_1_2)
        .cipherSuites(
            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
            CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
            CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384
        )
        .build()
}


class HeaderInterceptor(applicationContext: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain?.request()

        val token = UserInfo.deviceKey
        val requestBuilder = original?.newBuilder()
            ?.header("Content", "application/json")
            ?.header("AUTHORIZATION", Constants.HEADER_AUTHORIZATION)
        /*?.header("DeviceType", Constants.DEVICE_TYPE)
        ?.header("BuildVersion", BuildConfig.VERSION_NAME)
        ?.header("ApiVersion", Constants.API_VERSION)
        ?.header("LocationPermission", mContext.isLocationPermissionGranted(chain).toString())
        ?.header("LanguageVersion", UserInfo.languageVersion)*/

        if (!TextUtils.isEmpty(token))
            requestBuilder?.addHeader("DeviceKey", token)

        var response: Response? = null
        val request = requestBuilder?.build()
        if (request != null) {
            response = chain?.proceed(request) as Response
            if (response.code() == 401) {
                val refreshTokenStr = UserInfo.refreshToken
                val deviceIdentifier = UserInfo.deviceIdentifier
                val requestBody = FormBody.Builder()
                    .add("RefreshToken", refreshTokenStr)
                    .add("DeviceIdentifier", deviceIdentifier)
                    .build()

                val newRequest = request?.newBuilder()
                    ?.header("Content-Type", "application/json")
                    ?.header("DeviceKey", token)
                    ?.header("DeviceType", Constants.DEVICE_TYPE)
                    ?.header("BuildVersion", BuildConfig.VERSION_NAME)
                    ?.header("ApiVersion", Constants.API_VERSION)
                    ?.header("LanguageVersion", UserInfo.languageVersion)
                    ?.url(Constants.BASE_URL + Constants.REFRESH_REQUEST_URL)
                    ?.post(requestBody)
                    ?.build()

                val unauthorisedResponse = chain.proceed(newRequest)
                val bodyString = unauthorisedResponse.body()?.string()
                var unauthorisedObject: JSONObject? = null
                try {
                    unauthorisedObject = JSONObject(bodyString)
                    if (unauthorisedResponse.code() == 200) {
                        val newDeviceKey = unauthorisedObject.getString("DeviceKey")
                        val newRefreshToken = unauthorisedObject.getString("RefreshToken")
                        if (unauthorisedObject.isNull("DeviceKey") || unauthorisedObject.isNull("RefreshToken")) {
                            mContext.doLogOut()
                        } else {
                            requestBuilder?.removeHeader("DeviceKey")
                            requestBuilder?.addHeader("DeviceKey", newDeviceKey)

                            //save device key, refresh tokena and other user info
                            UserInfo.apply {
                                deviceKey = newDeviceKey
                                refreshToken = newRefreshToken
                            }
                            val newOriginalRequest = requestBuilder?.build() as Request
                            return chain.proceed(newOriginalRequest)
                        }
                    } else {
                        mContext.doLogOut()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    mContext.doLogOut()
                }
            } else if (response.code() == Constants.APP_FORCE_UPDATE_ERROR_CODE)
                Alerts.displayError(mContext, mContext.getString(R.string.update_current_version))
            else if (response.code() == Constants.APP_LANGUATE_UPDATE_CODE) {
                val bodyString = response.body()?.string()

                requestBuilder?.removeHeader("LanguageVersion")
                requestBuilder?.addHeader("LanguageVersion", UserInfo.languageVersion)
                val newOriginalRequest = requestBuilder?.build() as Request
                val languageResponse = chain.proceed(newOriginalRequest) as Response

                if (languageResponse.code() == 401) {
                    val refreshTokenStr = UserInfo.refreshToken
                    val deviceIdentifier = UserInfo.deviceIdentifier
                    val requestBody = FormBody.Builder()
                        .add("RefreshToken", refreshTokenStr)
                        .add("DeviceIdentifier", deviceIdentifier)
                        .build()

                    val newRequest = request?.newBuilder()
                        ?.header("Content-Type", "application/json")
                        ?.header("DeviceKey", token)
                        ?.header("DeviceType", Constants.DEVICE_TYPE)
                        ?.header("BuildVersion", BuildConfig.VERSION_NAME)
                        ?.header("ApiVersion", Constants.API_VERSION)
                        ?.header("LocationPermission", mContext.isLocationPermissionGranted(chain).toString())
                        ?.header("LanguageVersion", UserInfo.languageVersion)
                        ?.url(Constants.BASE_URL + Constants.REFRESH_REQUEST_URL)
                        ?.post(requestBody)
                        ?.build()

                    val unauthorisedResponse = chain.proceed(newRequest)
                    val bodyString = unauthorisedResponse.body()?.string()
                    var unauthorisedObject: JSONObject? = null
                    try {
                        unauthorisedObject = JSONObject(bodyString)

                        if (unauthorisedResponse.code() == 200) {
                            val newDeviceKey = unauthorisedObject.getString("DeviceKey")
                            val newRefreshToken = unauthorisedObject.getString("RefreshToken")
                            if (unauthorisedObject.isNull("DeviceKey") || unauthorisedObject.isNull("RefreshToken")) {
                                mContext.doLogOut()
                            } else {
                                requestBuilder?.removeHeader("DeviceKey")
                                requestBuilder?.addHeader("DeviceKey", newDeviceKey)

                                //save device key, refresh tokena and other user info
                                UserInfo.apply {
                                    deviceKey = newDeviceKey
                                    refreshToken = newRefreshToken
                                }

                                val newOriginalRequest = requestBuilder?.build() as Request
                                return chain.proceed(newOriginalRequest)
                            }
                        } else {
                            mContext.doLogOut()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        mContext.doLogOut()
                    }
                }
                return languageResponse
            }
        }
        return response!!
    }

    val mContext: Context = applicationContext
}