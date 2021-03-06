/*
 * MIT License
 *
 *   Copyright (c) 2020. Nicholas Doglio
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 */

package com.nicholasdoglio.eyebleach.data

import androidx.paging.PagingSource
import com.nicholasdoglio.eyebleach.data.util.toRedditPosts
import com.nicholasdoglio.eyebleach.util.DispatcherProvider
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class RedditPagingSource @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val postService: PostService
) : PagingSource<String, RedditPost>() {

    @Suppress("ReturnCount")
    override suspend fun load(params: LoadParams<String>): LoadResult<String, RedditPost> {
        Timber.i("Load request key: ${params.key}")

        try {
            val after = params.key

            val response = postService.posts(after = after ?: "")

            val data = withContext(dispatcherProvider.background) { response.toRedditPosts() }

            Timber.i("Fetch size: ${data.size}")

            if (data.isEmpty()) return LoadResult.Error(NoDataException())

            return LoadResult.Page(
                data = data,
                prevKey = after,
                nextKey = data.last().name
            )
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}

class NoDataException : Exception("Response doesn't contain any data!")
