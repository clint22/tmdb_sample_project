package com.clint.tmdb.others

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.clint.tmdb.databinding.CustomFilterDialogBinding


class CustomFilterDialog(private var mContext: Context, var onFilterItemClicked : (Int) -> Unit) : Dialog(mContext){

    lateinit var binding: CustomFilterDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = CustomFilterDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textViewRatingAscending.setOnClickListener {
            onFilterItemClicked(CUSTOM_DIALOG_RATING_ASC_ORDER_CLICKED)
        }

        binding.textViewRatingDescending.setOnClickListener {
            onFilterItemClicked(CUSTOM_DIALOG_RATING_DESC_ORDER_CLICKED)
        }

        binding.textViewReleaseDateAscending.setOnClickListener {
            onFilterItemClicked(CUSTOM_DIALOG_RELEASE_DATE_ASC_ORDER_CLICKED)
        }

        binding.textViewReleaseDateDescending.setOnClickListener {
            onFilterItemClicked(CUSTOM_DIALOG_RELEASE_DATE_DESC_ORDER_CLICKED)
        }
    }


}