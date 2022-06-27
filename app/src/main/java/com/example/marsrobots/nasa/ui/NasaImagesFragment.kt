package com.example.marsrobots.nasa.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.marsrobots.R
import com.example.marsrobots.databinding.FragmentNasaImagesBinding
import com.example.marsrobots.nasa.mvi.NasaIntent
import com.example.marsrobots.nasa.mvi.NasaSideEffect
import com.example.marsrobots.nasa.ui.adapters.GridSpacingItemDecoration
import com.example.marsrobots.nasa.ui.adapters.NasaAdapter
import com.example.marsrobots.nasa.viewmodels.NasaViewModel
import com.example.marsrobots.utils.viewBinding
import com.example.utils.ui.BaseFragment
import com.example.utils.ui.ContextExtensions.dpToPx
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.viewmodel.observe

@AndroidEntryPoint
class NasaImagesFragment : BaseFragment() {

    private val viewModel: NasaViewModel by activityViewModels()
    private val binding by viewBinding(FragmentNasaImagesBinding::bind)
    private var adapter: NasaAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentNasaImagesBinding.inflate(inflater, container, false).root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        root = binding.root

        // Better suited in a fragment that acts as a container but theres no other screen to show
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        viewModel.dispatchInitialIntent()
        setupView()
        observeState()
    }

    /**
     * Gird layout needed to display two columns
     */
    private fun setupView() {
        adapter = NasaAdapter({ item ->
            viewModel.dispatch(NasaIntent.ProcessItem(item))
        }).also {
            binding.recyclerView.adapter = it
        }
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerView.addItemDecoration(GridSpacingItemDecoration(8.dpToPx))
    }

    private fun observeState() {
        viewModel.observe(viewLifecycleOwner, { state ->
            viewLifecycleOwner.lifecycleScope.launch {
                state.items?.collectLatest { paging ->
                    adapter?.submitData(paging)
                }
            }
        }) { sideEffect ->
            when (sideEffect) {
                is NasaSideEffect.DoSomethingWithItem -> {
                    displayMessage(sideEffect.itemHeading)
                }
            }
        }
    }

    private fun displayMessage(message: String) {
        Snackbar.make(
            binding.root,
            resources.getString(R.string.home_message_tapped, message),
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onStop() {
        super.onStop()
        adapter = null
    }
}