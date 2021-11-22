package com.android.example.demomvvm.ui.repo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.example.demomvvm.data.remote.model.Repo
import com.android.example.demomvvm.databinding.ItRepoListBinding
import com.android.example.demomvvm.ui.repo.RepoFragmentDirections


class ReposAdapter :
    PagingDataAdapter<Repo, ReposAdapter.ViewHolder>(REPO_COMPARATOR) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo) = oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItRepoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { repo ->
            with(holder) {
                itemView.tag = repo
                if (repo != null) {
                    binding.item = repo
                    bind(createOnClickListener(binding, repo), repo)
                }
            }
        }
    }

    private fun createOnClickListener(
        binding: ItRepoListBinding,
        repo: Repo,
    ): View.OnClickListener {
        return View.OnClickListener {
            val directions = RepoFragmentDirections.actionReposToDetails(repo)
            val extras = FragmentNavigatorExtras(
                binding.avatar to "avatar_${repo.id}")
            it.findNavController().navigate(directions, extras)
        }
    }

    class ViewHolder(val binding: ItRepoListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, repo: Repo) {
            binding.apply {
                ViewCompat.setTransitionName(this.avatar, "avatar_${repo.id}")
                root.setOnClickListener(listener)
            }

        }
    }

}
