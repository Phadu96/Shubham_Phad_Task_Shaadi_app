package com.example.shubhamphad.shaadibandhanapp.presentation.ui.user

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.shubhamphad.shaadibandhanapp.R
import com.example.shubhamphad.shaadibandhanapp.databinding.ItemUserBinding
import com.example.shubhamphad.shaadibandhanapp.domain.model.User
import com.example.shubhamphad.shaadibandhanapp.presentation.base.BaseRecyclerViewAdapter

class UserAdapter(
    items: List<User>,
    private val onAccept: (User) -> Unit,
    private val onReject: (User) -> Unit
) : BaseRecyclerViewAdapter<User, ItemUserBinding>(items) {

    override fun createBinding(parent: ViewGroup): ItemUserBinding {
        val inflater = LayoutInflater.from(parent.context)
        return ItemUserBinding.inflate(inflater, parent, false)
    }

    @SuppressLint("SetTextI18n")
    override fun bindData(binding: ItemUserBinding, item: User, position: Int) {
        val context = binding.root.context

        Glide.with(binding.imageProfile)
            .load(item.pictureUrl)
            .placeholder(R.drawable.ic_profile)
            .error(R.drawable.ic_profile)
            .into(binding.imageProfile)

        binding.valueName.text = item.name
        binding.valueAge.text = item.age?.toString() ?: "N/A"
        binding.valueGender.text = item.gender ?: "N/A"
        binding.valueEmail.text = item.email ?: "N/A"
        binding.valuePhone.text = item.phone ?: "N/A"
        binding.valueLocation.text = item.location?.city ?: "N/A"
        binding.valueNationality.text = item.nationality ?: "N/A"

        binding.btnStatusBadge.apply {
            isEnabled = false
            visibility = if (item.status.isNullOrEmpty()) View.GONE else View.VISIBLE

            text = when (item.status?.lowercase()) {
                "accepted" -> context.getString(R.string.accepted)
                "rejected" -> context.getString(R.string.rejected)
                else -> item.status
            }


            when (item.status?.lowercase()) {
                "accepted" -> {
                    setBackgroundColor(context.getColor(R.color.status_accepted))
                    setIconResource(R.drawable.ic_accept)
                    setIconTintResource(android.R.color.white)
                    setTextColor(context.getColor(android.R.color.white))
                }
                "rejected" -> {
                    setBackgroundColor(context.getColor(R.color.red_500))
                    setIconResource(R.drawable.ic_reject)
                    setIconTintResource(android.R.color.white)
                    setTextColor(context.getColor(android.R.color.white))
                }
                else -> {
                    setBackgroundColor(context.getColor(R.color.background_grey))
                    icon = null
                }
            }
        }

        val showActions = item.status.isEmpty()
        binding.actionButtons.visibility = if (showActions) View.VISIBLE else View.GONE

        if (showActions) {
            binding.btnAccept.setOnClickListener {
                onAccept(item)
            }
            binding.btnReject.setOnClickListener {
                onReject(item)
            }
        }
    }

    override fun createLoadingView(parent: ViewGroup): ViewBinding {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_loading_footer, parent, false)
        return ViewBinding { view }
    }
}