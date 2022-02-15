package com.example.cupofsugar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.cupofsugar.databinding.FragmentTopicBinding


class TopicFragment : Fragment() {
    private val binding get() = _binding!!

    private var _binding: FragmentTopicBinding? = null

    var arrayAdapter: ArrayAdapter<String>? = null

    override fun onResume() {
        super.onResume()

        val reasons = resources.getStringArray(R.array.support_reasons)
        arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, reasons)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTopicBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}