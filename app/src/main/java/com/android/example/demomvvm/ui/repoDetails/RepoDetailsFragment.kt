package com.android.example.demomvvm.ui.repoDetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.android.example.demomvvm.R
import com.android.example.demomvvm.core.util.DateUtils
import com.android.example.demomvvm.core.util.setDisplayHomeAsUpEnabled
import com.android.example.demomvvm.databinding.FragmnetRepoDetailBinding
import com.bumptech.glide.Glide

class RepoDetailsFragment : Fragment() {


    private lateinit var binding: FragmnetRepoDetailBinding

    private val args: RepoDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragmnet_repo_detail,
            container, false
        )
        sharedElementEnterTransition =
            TransitionInflater.from(requireActivity()).inflateTransition(android.R.transition.move)
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
        setDisplayHomeAsUpEnabled(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.apply {
            args.repoData?.let {
                name.text = it.name
                username.text = it.owner.login
                language.text = it.language
                description.text = it.description

                avatar.apply {
                    transitionName = it.owner.avatar_url
                    Glide.with(view)
                        .load(it.owner.avatar_url)
                        .error(android.R.drawable.stat_notify_error)
                        .into(this)
                }

                stars.text = it.stars.toString()
                forks.text = it.forks.toString()
                watchers.text = it.watchers.toString()
                issuesOpened.text = it.openIssues.toString()
                createDate.text = DateUtils.formatDate(it.createDate)
                updateDate.text = DateUtils.formatDate(it.updateDate)
            }

            btnBrowse.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(args.repoData!!.url))
                startActivity(browserIntent)
            }

        }

        ViewCompat.setTransitionName(binding.avatar, "avatar_${args.repoData!!.id}")

        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                view?.let { Navigation.findNavController(it).navigateUp() }
            }
        }
        return super.onOptionsItemSelected(item)
    }


}