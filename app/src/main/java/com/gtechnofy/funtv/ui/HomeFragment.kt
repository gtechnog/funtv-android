package com.gtechnofy.funtv.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.gtechnofy.funtv.databinding.HomeFragmentBinding
import com.gtechnofy.funtv.usecase.DiscoverMovieUseCase

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var recyclerView: RecyclerView
    private val adapter = DiscoveryListAdapter()

    private val viewModel: HomeFragmentViewModel by lazy {
        ViewModelProvider(this, HomeFragmentViewModel.HomeFragmentViewModelFactory(
            useCase = DiscoverMovieUseCase()
        )).get(HomeFragmentViewModel::class.java)
    }

    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        recyclerView = binding.list
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()
        subscribeForData()
        viewModel.loadPage()
    }

    private fun setupRecyclerView() {
        recyclerView.adapter = adapter
    }

    private fun subscribeForData() {
        viewModel.loadMovies.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty().not()) {
                adapter.setMoviesList(it)
            }
        }
    }
}