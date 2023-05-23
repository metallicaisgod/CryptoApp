package com.example.cryptoapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.databinding.FragmentCoinDetailBinding
import com.squareup.picasso.Picasso

class CoinDetailFragment : Fragment() {

    private lateinit var viewModel: CoinViewModel
    private var _binding: FragmentCoinDetailBinding? = null
    private val binding: FragmentCoinDetailBinding
        get() = _binding ?: throw RuntimeException("FragmentCoinDetailBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fromSymbol = getSymbol()
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.getDetailInfo(fromSymbol).observe(viewLifecycleOwner) { coinInfo ->
            with(binding) {
                with(coinInfo) {
                    tvPrice.text = price
                    tvMinPrice.text = lowDay
                    tvMaxPrice.text = highDay
                    tvLastMarket.text = lastMarket
                    tvLastUpdate.text = lastUpdate
                    tvFromSymbol.text = fromSymbol
                    tvToSymbol.text = toSymbol
                    Picasso.get().load(imageUrl).into(ivLogoCoin)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getSymbol() = requireArguments().getString(EXTRA_FROM_SYMBOL, EMPTY_SYMBOL)

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
        private const val EMPTY_SYMBOL = ""


        fun newInstance(fromSymbol: String): CoinDetailFragment {
            return CoinDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_FROM_SYMBOL, fromSymbol)
                }
            }
        }
    }
}