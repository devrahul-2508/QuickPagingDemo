package com.example.quickpagingdemo.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.quickpagingdemo.db.QuoteDatabase
import com.example.quickpagingdemo.models.QuoteRemoteKeys
import com.example.quickpagingdemo.retrofit.QuoteApi
import com.example.quickpagingdemo.models.Result

@OptIn(ExperimentalPagingApi::class)
class QuoteRemoteMediator(
    private val quoteApi: QuoteApi,
    private val quoteDatabase: QuoteDatabase
): RemoteMediator<Int,Result>() {

    private val quoteDao = quoteDatabase.quoteDao()
    private val quoteRemoteKeysDao = quoteDatabase.remoteKeysDao()
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Result>): MediatorResult {

        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            //FETCH QUOTES FROM API
            val response = quoteApi.getQuotes(currentPage)
            val endOfPagination = response.totalPages == currentPage

            val prevPage = if (currentPage == 1) null else currentPage-1
            val nextPage = if (endOfPagination) null else currentPage+1

            //Save these Quotes + RemoteKeys Data into DB
            quoteDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    quoteDao.deleteQuotes()
                    quoteRemoteKeysDao.deleteAllRemoteKeys()
                }
                quoteDao.addQuotes(response.results)
                val keys = response.results.map {
                    QuoteRemoteKeys(
                        id = it._id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                quoteRemoteKeysDao.addAllRemoteKeys(keys)
            }
            MediatorResult.Success(endOfPagination)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Result>
    ): QuoteRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?._id?.let { id ->
                quoteRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Result>
    ): QuoteRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { quote ->
                quoteRemoteKeysDao.getRemoteKeys(id = quote._id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Result>
    ): QuoteRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { quote ->
                quoteRemoteKeysDao.getRemoteKeys(id = quote._id)
            }
    }


}