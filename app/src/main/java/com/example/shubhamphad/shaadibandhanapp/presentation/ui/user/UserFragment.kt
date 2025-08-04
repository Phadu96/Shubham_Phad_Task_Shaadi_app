package com.example.shubhamphad.shaadibandhanapp.presentation.ui.user

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shubhamphad.shaadibandhanapp.R
import com.example.shubhamphad.shaadibandhanapp.databinding.FragmentUserBinding
import com.example.shubhamphad.shaadibandhanapp.domain.model.User
import com.example.shubhamphad.shaadibandhanapp.presentation.base.BaseFragment
import com.example.shubhamphad.shaadibandhanapp.presentation.ui.ShaadiBandhanMainActivity
import com.example.shubhamphad.shaadibandhanapp.utils.UiState
import com.example.shubhamphad.shaadibandhanapp.utils.showLoading
import com.example.shubhamphad.shaadibandhanapp.utils.showToast
import com.example.shubhamphad.shaadibandhanapp.utils.startShimmering
import com.example.shubhamphad.shaadibandhanapp.utils.stopShimmering
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFragment :
    BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {

    private val userViewModel by viewModel<UserViewModel>()

    private lateinit var userAdapter: UserAdapter
    private var isLoadingMore = false
    private val visibleThreshold = 2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar(getString(R.string.shadi_bandhan))

        setupRecyclerView()
        setupObservers()

        userViewModel.loadInitialUsers()
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter(
            emptyList(),
            { user ->
                userViewModel.updateUserStatus(user.id, "accepted")
            },
        ) { user -> userViewModel.updateUserStatus(user.id, "rejected") }
        binding.rvUsers.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        binding.rvUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                if (dy <= 0) return
                val layoutManager = rv.layoutManager as? LinearLayoutManager ?: return
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoadingMore && userViewModel.hasMorePages) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount - visibleThreshold) {
                        isLoadingMore = true
                        userAdapter.addLoadingFooter()
                        userViewModel.loadNextPage()
                    }
                }
            }
        })
    }

    private fun setupObservers() {
        userViewModel.userList.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    if (userViewModel.hasMorePages && userAdapter.itemCount > 0) {
                        // Show loading footer handled by addLoadingFooter()
                    } else {
                        binding.shimmerViewContainer.startShimmering()
                        (requireActivity() as ShaadiBandhanMainActivity).binding.progressBar.showLoading(
                            binding.rvUsers,
                            true
                        )
                    }
                }

                is UiState.Success -> {
                    binding.shimmerViewContainer.stopShimmering()
                    (requireActivity() as ShaadiBandhanMainActivity).binding.progressBar.showLoading(
                        binding.rvUsers,
                        false
                    )

                    if (isLoadingMore) {
                        userAdapter.removeLoadingFooter()
                        isLoadingMore = false
                    }

                    if (state.data.isEmpty()) {
                        binding.tvNoData.visibility = View.VISIBLE
                    } else {
                        binding.tvNoData.visibility = View.GONE
                        userAdapter.updateData(state.data, userViewModel.hasMorePages)
                    }
                }

                is UiState.Error -> {
                    binding.shimmerViewContainer.stopShimmering()
                    (requireActivity() as ShaadiBandhanMainActivity).binding.progressBar.showLoading(
                        binding.rvUsers,
                        false
                    )
                    if (isLoadingMore) {
                        userAdapter.removeLoadingFooter()
                        isLoadingMore = false
                    }
                    requireContext().showToast(state.message)
                }

                is UiState.Empty -> {
                    binding.shimmerViewContainer.stopShimmering()
                    binding.tvNoData.visibility = View.VISIBLE
                }
            }
        }

        userViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            if (!loading && isLoadingMore) {
                userAdapter.removeLoadingFooter()
                isLoadingMore = false
            }
        }
    }
}