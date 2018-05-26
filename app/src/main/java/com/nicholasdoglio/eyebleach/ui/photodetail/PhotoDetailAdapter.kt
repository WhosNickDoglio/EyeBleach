/*
    Aww Gallery
    Copyright (C) 2017  Nicholas Doglio

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.nicholasdoglio.eyebleach.ui.photodetail

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.nicholasdoglio.eyebleach.data.model.reddit.ChildData

/**
 * @author Nicholas Doglio
 */
class PhotoDetailAdapter :
    ListAdapter<ChildData, RecyclerView.ViewHolder>(photoDetailDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inner class PhotoDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    companion object {
        private val photoDetailDiff = object : DiffUtil.ItemCallback<ChildData>() {
            override fun areItemsTheSame(oldItem: ChildData, newItem: ChildData): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ChildData, newItem: ChildData): Boolean =
                oldItem == newItem
        }
    }

}