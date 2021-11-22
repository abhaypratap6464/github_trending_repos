package com.android.example.demomvvm.ui.repo

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import com.android.example.demomvvm.R
import com.android.example.demomvvm.core.base.BaseFragment
import com.android.example.demomvvm.core.util.onQueryTextChange
import com.android.example.demomvvm.core.util.setDisplayHomeAsUpEnabled
import com.android.example.demomvvm.core.util.setTitle
import com.android.example.demomvvm.databinding.FragmentRepoBinding
import com.android.example.demomvvm.ui.repo.adapter.ReposAdapter
import com.android.example.demomvvm.ui.repo.adapter.ReposLoadStateAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

/**
 * used for displaying all the issue on the screen
 */
class RepoFragment : BaseFragment() {

    override val _viewModel: RepoViewModel by viewModel()

    private lateinit var binding: FragmentRepoBinding

    private lateinit var adapter: ReposAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_repo,
            container, false
        )
        binding.viewModel = _viewModel
        setHasOptionsMenu(true)
        setDisplayHomeAsUpEnabled(false)
        setTitle(getString(R.string.app_name))
        return binding.root
    }

    private fun setupRecyclerView() {

        adapter = ReposAdapter()
        binding.apply {

            rvRepoList.apply {
                setHasFixedSize(true)
                itemAnimator = null
                this.adapter = this@RepoFragment.adapter.withLoadStateHeaderAndFooter(
                    header = ReposLoadStateAdapter { this@RepoFragment.adapter.retry() },
                    footer = ReposLoadStateAdapter { this@RepoFragment.adapter.retry() }
                )
                postponeEnterTransition()
                viewTreeObserver.addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
            }

            btnRetry.setOnClickListener {
                adapter.retry()
            }
        }

        _viewModel.reposList.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }


        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                rvRepoList.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                error.isVisible = loadState.source.refresh is LoadState.Error

                // no results found
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    rvRepoList.isVisible = false
                    emptyTv.isVisible = true
                } else {
                    emptyTv.isVisible = false
                }
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        setupRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_repos, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.onQueryTextChange {
            _viewModel.searchRepos(it)
            binding.rvRepoList.scrollToPosition(0)
        }

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                _viewModel.searchRepos("since=monthly")
                searchView.clearFocus()
                return true
            }
        })
    }
}