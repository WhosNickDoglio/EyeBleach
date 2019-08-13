/*
 * MIT License
 *
 *   Copyright (c) 2019. Nicholas Doglio
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

package com.nicholasdoglio.eyebleach.ui.photolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation.findNavController
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.api.load
import com.nicholasdoglio.eyebleach.R
import com.nicholasdoglio.eyebleach.db.RedditPost
import com.nicholasdoglio.eyebleach.ui.base.AwwGalleryHolder
import kotlinx.android.synthetic.main.item_photo_list.*

class PhotoListViewHolder(override val containerView: View) : AwwGalleryHolder<RedditPost>(containerView) {

    override fun bind(model: RedditPost) {
        galleryImage.setOnClickListener {
            findNavController(containerView).navigate(PhotoListFragmentDirections.openDetails(model.url))
        }

        val placeholder = CircularProgressDrawable(galleryImage.context).apply {
            setStyle(CircularProgressDrawable.DEFAULT)
            setColorSchemeColors(
                ContextCompat.getColor(
                    galleryImage.context,
                    R.color.colorAccent
                )
            )
        }

        placeholder.start()

        galleryImage.load(model.url) {
            placeholder(placeholder)
        }
    }

    companion object {
        fun create(parent: ViewGroup): PhotoListViewHolder =
            PhotoListViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_photo_list,
                    parent,
                    false
                )
            )
    }
}