package com.example.aipulse.di

import android.content.Context
import androidx.room.Room
import com.example.aipulse.BuildConfig
import com.example.aipulse.data.article.remote.ArticleApi
import com.example.aipulse.data.article.room.ArticleRepository
import com.example.aipulse.data.article.room.ArticleRepositoryImpl
import com.example.aipulse.data.ai.AiApi
import com.example.aipulse.data.ai.AiRepository
import com.example.aipulse.data.ai.AiSummaryRepository
import com.example.aipulse.data.article.remote.interceptor.ApiKeyInterceptor
import com.example.aipulse.data.article.room.ArticleDAO
import com.example.aipulse.data.article.room.ArticleDatabase
import com.example.aipulse.data.article.room.RemoteKey.RemoteKeyDAO
import com.example.aipulse.data.source.Remote.SourceApi
import com.example.aipulse.data.source.Room.SourceDAO
import com.example.aipulse.data.source.Room.SourceRepository
import com.example.aipulse.data.source.Room.SourceRepositoryImpl
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

private const val BASE_URL = "https://newsapi.org/v2/"
private const val OPEN_AI_BASE_URL = "https://api.openai.com/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply{
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }

        val apiKeyInterceptor = ApiKeyInterceptor(BuildConfig.NEWS_API_KEY)
        return OkHttpClient
            .Builder()
            .addInterceptor(logging)
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        moshi: Moshi,
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    @Named("openAiRetrofit")
    fun provideOpenAiRetrofit(
        moshi: Moshi,
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(OPEN_AI_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideArticleApi(retrofit: Retrofit): ArticleApi {
        return retrofit.create(ArticleApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSourceApi(retrofit: Retrofit): SourceApi {
        return retrofit.create(SourceApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAiApi(
        @Named("openAiRetrofit") retrofit: Retrofit,
    ): AiApi {
        return retrofit.create(AiApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ArticleDatabase {
        return Room.databaseBuilder(
            context,
            ArticleDatabase::class.java,
            "article_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providesSourceDAO(
        database : ArticleDatabase
    ) : SourceDAO{
        return database.sourceDao()
    }

    @Provides
    fun provideArticleDao(
        articleDatabase: ArticleDatabase
    ): ArticleDAO {
        return articleDatabase.articleDao()
    }

    @Provides
    fun providesRemoteKeyDao(
        articleDatabase: ArticleDatabase
    ): RemoteKeyDAO{
        return articleDatabase.remoteKeysDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ArticleRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindArticleRepository(
        articleRepositoryImpl: ArticleRepositoryImpl,
    ): ArticleRepository

    @Binds
    @Singleton
    abstract fun bindSourceRepository(
        sourceRepositoryImpl: SourceRepositoryImpl,
    ): SourceRepository

    @Binds
    @Singleton
    abstract fun bindAiSummaryRepository(
        aiRepository: AiRepository,
    ): AiSummaryRepository
}
